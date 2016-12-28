package com.epartner.repositories;

import com.epartner.domain.Payment;
import com.epartner.domain.PaymentState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by mapsi on 11/26/16.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {


    Page<Payment> findAllByState(PaymentState state, Pageable pageRequest);

    Page<Payment> findAllByProduct_nameContainingAndUser(String s, String principal, Pageable pageable);

    Page<Payment> findAllByUser(String principal, Pageable pageRequest);

    Page<Payment> findAllByProduct_nameContainingOrUserContaining(String s, String s1, Pageable pageRequest);

    Payment findOneByUserAndId(String principal, Long id);
}
