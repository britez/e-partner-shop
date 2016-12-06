package com.epartner.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by maty on 3/12/16.
 */
public enum PaymentState {

    PAID, NOT_PAID, CANCELED;

    public List<PaymentState> getNextState(){
        if(CANCELED.equals(this)){
            return Collections.emptyList();
        }

        List<PaymentState> result = new ArrayList<>();

        if(NOT_PAID.equals(this)){
            result.add(PAID);
            result.add(CANCELED);
        }

        if(PAID.equals(this)) {
            result.add(CANCELED);
        }
        return result;
    }
}
