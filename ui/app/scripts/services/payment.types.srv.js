'use strict';

export default class PaymentService {

    /*ngInject*/
    constructor() {
        this.paymentOptions = [
            {
                id: 1,
                code: 'CASH',
                title: 'Pago en efectivo.',
                icon: 'fa fa-credit-card'
            },
            {
                id: 2,
                code: 'DEBIT',
                title: 'Pago con tarjeta de crédito/débito.',
                icon: 'fa fa-credit-card'
            },
            {
                id: 3,
                code: 'MERCADO_PAGO',
                title: 'Pago con MercadoPago.',
                icon: 'pf pf-mercado-pago-sign'
            }
        ];
    }

    getPaymentOptions() {
        return this.paymentOptions;
    }

    getPaymentOption(id) {
        return this.paymentOptions.find(it => it.id == id);
    }

}