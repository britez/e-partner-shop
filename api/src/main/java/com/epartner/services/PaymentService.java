package com.epartner.services;

import com.epartner.converters.PaymentConverter;
import com.epartner.domain.*;
import com.epartner.exceptions.InvalidPaymentStateException;
import com.epartner.exceptions.InvalidPaymentTypeException;
import com.epartner.exceptions.NoAvailableStockException;
import com.epartner.payment.PaymentChangedState;
import com.epartner.repositories.PaymentRepository;
import com.epartner.repositories.ProductRepository;
import com.epartner.representations.PaymentRepresentation;
import com.epartner.representations.ProductRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by mapsi on 11/26/16.
 */
@Service
@Transactional
public class PaymentService {

    private ProductRepository productRepository;
    private PaymentRepository paymentRepository;
    private PaymentConverter paymentConverter;
    private List<PaymentChangedState> changedStates;
    private ProductService productService;
    private Map<String, PaymentState> stateFromPaymentType;
    private ProductImportService productImportService;

    private static final Integer PAGE = 0;
    private static final Integer MAX = 10;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                          PaymentConverter paymentConverter,
                          ProductRepository productRepository,
                          ProductService productService,
                          List<PaymentChangedState> changedStates,
                          ProductImportService productImportService) {

        this.productService = productService;
        this.paymentRepository = paymentRepository;
        this.paymentConverter = paymentConverter;
        this.productRepository = productRepository;
        this.changedStates = changedStates;
        this.productImportService = productImportService;

        this.stateFromPaymentType = new HashMap<>();
        this.stateFromPaymentType.put(PaymentType.CASH, PaymentState.NOT_PAID);
        this.stateFromPaymentType.put(PaymentType.DEBIT, PaymentState.NOT_PAID);
        this.stateFromPaymentType.put(PaymentType.MERCADO_PAGO, PaymentState.NOT_PAID);
    }

    public PaymentRepresentation create(PaymentRepresentation paymentRepresentation) {
        //check if payment type is valid
        if(PaymentType.MERCADO_PAGO.equals(paymentRepresentation.getPaymentType())){
            throw new InvalidPaymentTypeException();
        }
        return createAllowed(paymentRepresentation);
    }

    public PaymentRepresentation createAllowed(PaymentRepresentation paymentRepresentation) {
        //check if product exists
        ProductRepresentation productRepresentation = productService.show(paymentRepresentation.getProduct().getId());
        //check stock availability
        if(isNotAvailableStock(paymentRepresentation, productRepresentation) ) {
            throw new NoAvailableStockException();
        }
        //update stock
        this.removeStock(productRepresentation, paymentRepresentation);
        productService.update(productRepresentation, paymentRepresentation.getProduct().getId());
        Payment payment = this.paymentConverter.convert(paymentRepresentation);
        //hay que buscar el producto para ponerselo al payment :(
        Product product = productRepository.findOne(productRepresentation.getId());
        payment.setProduct(product);
        payment.setUser(this.getPrincipal());
        //set the payment state from payment type
        payment.setState(this.stateFromPaymentType.get(paymentRepresentation.getPaymentType()));
        return this.paymentConverter.convert(
                this.paymentRepository.save(payment)
        );
    }

    private void removeStock(ProductRepresentation productRepresentation, PaymentRepresentation paymentRepresentation) {
        productRepresentation.removeStock(paymentRepresentation.getQuantity());
        if(productRepresentation.getImported()) {
            this.productImportService.updateStock(productRepresentation);
        }
    }

    private boolean isNotAvailableStock(PaymentRepresentation paymentRepresentation, ProductRepresentation storedProduct) {
        Integer minimStock = 1;
        if(storedProduct.getImported()) {
            minimStock = 2;
        }
        return storedProduct.getStock() <= minimStock ||
        storedProduct.getStock() <= paymentRepresentation.getQuantity();
    }

    public Page<PaymentRepresentation> getAllPayments(
            Optional<Integer> max,
            Optional<Integer> page,
            Optional<String> query) {
        PageRequest pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX), new Sort(Sort.Direction.DESC, "id"));
        Page<Payment> stored;
        if(query.isPresent()) {
            stored = paymentRepository.findAllByProduct_nameContainingOrUserContaining(query.get(), query.get(), pageRequest);
        } else {
            stored = paymentRepository.findAll(pageRequest);
        }
        return new PageImpl<>(this.paymentConverter.convert(stored), pageRequest, stored.getTotalElements());
    }

    public Page<PaymentRepresentation> getAllPaymentsByUser(
            Optional<Integer> max,
            Optional<Integer> page,
            Optional<String> query) {
        PageRequest pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX), new Sort(Sort.Direction.DESC, "id"));
        Page<Payment> stored;
        if(query.isPresent()) {
            stored = paymentRepository.findAllByProduct_nameContainingAndUser(query.get(), getPrincipal(), pageRequest);
        } else {
            stored = paymentRepository.findAllByUser(getPrincipal(), pageRequest);
        }
        return new PageImpl<>(this.paymentConverter.convert(stored), pageRequest, stored.getTotalElements());
    }

    private String getPrincipal() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Page<PaymentRepresentation> getAllPaidPayments(Optional<Integer> max, Optional<Integer> page) {
        return getAllPayments(PaymentState.PAID, max, page);
    }

    public Page<PaymentRepresentation> getAllUnpaidPayments(Optional<Integer> max, Optional<Integer> page) {
        return getAllPayments(PaymentState.NOT_PAID, max, page);
    }

    public Page<PaymentRepresentation> getAllCanceledPayments(Optional<Integer> max, Optional<Integer> page) {
        return getAllPayments(PaymentState.CANCELED, max, page);
    }

    public Page<PaymentRepresentation> getAllPayments(
            PaymentState state,
            Optional<Integer> max,
            Optional<Integer> page) {

        PageRequest pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));
        Page<Payment> stored = paymentRepository.findAllByState(state, pageRequest);
        return new PageImpl<>(this.paymentConverter.convert(stored), pageRequest, stored.getTotalElements());

    }


    public PaymentRepresentation update(Long id, PaymentRepresentation paymentRepresentation){
        Payment payment = paymentRepository.getOne(id);
        isValidStateForPayment(paymentRepresentation, payment);

        PaymentChangedState changedState = findChangedState(paymentRepresentation);

        paymentRepository.save(changedState.execute(payment));
        productRepository.save(payment.getProduct());

        return paymentConverter.convert(payment);
    }

    private PaymentChangedState findChangedState(PaymentRepresentation payment) {
        final PaymentState state = payment.getState();

        return changedStates
                .stream()
                .filter(cs-> cs.apply(state))
                .findFirst()
                .orElseThrow(InvalidPaymentTypeException::new);
    }

    private void isValidStateForPayment(PaymentRepresentation paymentRepresentation, Payment payment) {
        List<PaymentState> validStates = payment.getState().getNextState();

        if(!validStates.contains(paymentRepresentation.getState())) {
            throw new InvalidPaymentStateException();
        }
    }

    public PaymentRepresentation show(Long id) {
        return this.paymentConverter
                .convert(Optional.ofNullable(paymentRepository.findOne(id))
                                .orElseThrow(EntityNotFoundException::new));
    }

    public PaymentRepresentation showByUser(Long id) {
        return this.paymentConverter
                .convert(Optional.ofNullable(paymentRepository.findOneByUserAndId(getPrincipal(), id))
                        .orElseThrow(EntityNotFoundException::new));
    }
}
