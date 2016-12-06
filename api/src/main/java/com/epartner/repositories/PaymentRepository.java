package com.epartner.repositories;

import com.epartner.domain.Payment;
import com.epartner.domain.PaymentState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mapsi on 11/26/16.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {


    Page<Payment> findAllByState(PaymentState state, Pageable pageRequest);

    Page<Payment> findAllByProduct_nameContaining(String s, Pageable pageRequest);
}
