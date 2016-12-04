package com.epartner.services;

import com.epartner.converters.PaymentConverter;
import com.epartner.domain.*;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by mapsi on 11/26/16.
 */
@Service
@Transactional
public class PaymentService {

    private ProductService productService;
    private ProductRepository productRepository;
    private PaymentRepository paymentRepository;
    private PaymentConverter paymentConverter;
    private List<PaymentChangedState> changedStates;


    private static final Integer PAGE = 0;
    private static final Integer MAX = 10;

    @Autowired
    public PaymentService(ProductService productService,
                          PaymentRepository paymentRepository,
                          PaymentConverter paymentConverter,
                          ProductRepository productRepository,
                          List<PaymentChangedState> changedStates) {
        this.productService = productService;
        this.paymentRepository = paymentRepository;
        this.paymentConverter = paymentConverter;
        this.productRepository = productRepository;
        this.changedStates = changedStates;
    }

    public PaymentRepresentation create(PaymentRepresentation paymentRepresentation) {

        //check if product exists
        ProductRepresentation productRepresentation = productService.show(paymentRepresentation.getProductId());



        //check stock availability
        if( noHayStockDisponible(paymentRepresentation, productRepresentation) ) {
            throw new NoAvailableStockException();
        }

        productRepresentation.removeStock(paymentRepresentation.getQuantity());

        productService.update(productRepresentation, paymentRepresentation.getProductId());


        Payment payment = this.paymentConverter.convert(paymentRepresentation);

        //hay que buscar el producto para ponerselo al payment :(
        Product product = productRepository.findOne(productRepresentation.getId());
        payment.setProduct(product);

        return this.paymentConverter.convert(
            this.paymentRepository.save(payment)
        );
    }

    private boolean noHayStockDisponible(PaymentRepresentation paymentRepresentation, ProductRepresentation productRepresentation) {
        return productRepresentation.getStock() <= 0 ||
                productRepresentation.getStock() < paymentRepresentation.getQuantity();
    }

    public Page<PaymentRepresentation> getAllPayments(Optional<Integer> max, Optional<Integer> page) {
        PageRequest pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));
        Page<Payment> stored = paymentRepository.findAll(pageRequest);
        return new PageImpl<>(this.paymentConverter.convert(stored), pageRequest, stored.getTotalElements());

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

    public Page<PaymentRepresentation> getAllPayments(PaymentState state, Optional<Integer> max, Optional<Integer> page) {

        PageRequest pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));
        Page<Payment> stored = paymentRepository.findAllByState(state, pageRequest);
        return new PageImpl<>(this.paymentConverter.convert(stored), pageRequest, stored.getTotalElements());

    }


    public PaymentRepresentation update(PaymentRepresentation paymentRepresentation){

        Payment payment = paymentRepository.getOne(paymentRepresentation.getId());

        isValidStateForPayment(paymentRepresentation, payment);

        PaymentChangedState changedState = findChangedState(paymentRepresentation);
        payment = changedState.execute(payment);

        paymentRepository.save(payment);
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


        if(paymentRepresentation.getState().equals(PaymentState.NOT_PAID)){
            // no se puede poner un pago como no pago??
        }

        if(paymentRepresentation.getState().equals(PaymentState.CANCELED) && payment.getState().equals(PaymentState.PAID)){
            // no se puede poner un pago como no pago
        }
    }

}
