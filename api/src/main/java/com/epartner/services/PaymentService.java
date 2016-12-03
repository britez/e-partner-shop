package com.epartner.services;

import com.epartner.converters.PaymentConverter;
import com.epartner.domain.Category;
import com.epartner.domain.Payment;
import com.epartner.domain.Product;
import com.epartner.exceptions.InvalidPaymentTypeException;
import com.epartner.exceptions.NoAvailableStockException;
import com.epartner.payment.PaymentStrategy;
import com.epartner.repositories.PaymentRepository;
import com.epartner.repositories.ProductRepository;
import com.epartner.representations.PaymentRepresentation;
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
    List<PaymentStrategy> paymentStrategyList;

    private static final Integer PAGE = 0;
    private static final Integer MAX = 10;

    @Autowired
    public PaymentService(ProductService productService,
                          PaymentRepository paymentRepository,
                          PaymentConverter paymentConverter,
                          ProductRepository productRepository,
                          List<PaymentStrategy> paymentStrategyList) {
        this.productService = productService;
        this.paymentRepository = paymentRepository;
        this.paymentConverter = paymentConverter;
        this.productRepository = productRepository;
        this.paymentStrategyList = paymentStrategyList;
    }

    public PaymentRepresentation create(PaymentRepresentation paymentRepresentation) {


        paymentStrategyList
                .stream()
                .filter(paymentStrategy -> paymentStrategy.apply(paymentRepresentation.getPaymentType()))
                .findFirst()
                .orElseThrow(InvalidPaymentTypeException::new);

        //check if product exists
        Product storedProduct = productRepository.findOne(paymentRepresentation.getProductId());
        //check stock availability
        if( noHayStockDisponible(paymentRepresentation, storedProduct) ) {
            throw new NoAvailableStockException();
        }

        Payment payment = this.paymentConverter.convert(paymentRepresentation);
        payment.setProduct(storedProduct);

        return this.paymentConverter.convert(
            this.paymentRepository.save(payment)
        );
    }

    private boolean noHayStockDisponible(PaymentRepresentation paymentRepresentation, Product storedProduct) {
        return storedProduct.getStock() <= 0 ||
        storedProduct.getStock() < paymentRepresentation.getQuantity();
    }

    public Page<PaymentRepresentation> getAllPayments(Optional<Integer> max, Optional<Integer> page) {
        PageRequest pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));
        Page<Payment> stored = paymentRepository.findAll(pageRequest);
        return new PageImpl<>(this.paymentConverter.convert(stored), pageRequest, stored.getTotalElements());

    }


    public Page<PaymentRepresentation> getAllPaidPayments(Optional<Integer> max, Optional<Integer> page) {

        return getAllPayments(true, max, page);

    }

    public Page<PaymentRepresentation> getAllUnpaidPayments(Optional<Integer> max, Optional<Integer> page) {

        return getAllPayments(false, max, page);
    }

    public Page<PaymentRepresentation> getAllPayments(Boolean isPaid, Optional<Integer> max, Optional<Integer> page) {

        PageRequest pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));
        Page<Payment> stored = paymentRepository.findAllByIsPaid(isPaid);
        return new PageImpl<>(this.paymentConverter.convert(stored), pageRequest, stored.getTotalElements());

    }

}
