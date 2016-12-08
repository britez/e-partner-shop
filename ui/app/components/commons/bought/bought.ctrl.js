'use strict';
export default class BoughtController {

    /*@ngInject*/
    constructor(authService, $state, api, paymentService) {
        this.authService = authService;
        this.api = api;
        this.params = $state.params;
        this.paymentService = paymentService;
        this.init();
    }

    init() {

        this
            .authService
            .getUser()
            .then(user => {
               this.user = user;
            });
        this
            .api
            .publicProducts
            .get({id: this.params.id})
            .$promise
            .then(response => {
                this.product = response;
        });

        this.payment = this.paymentService.getPaymentOption(this.params.payment);

    }

    confirm() {

        let payment = {
            product: {
                id: this.product.id
            },
            paymentType: this.payment.code,
            quantity: this.params.quantity
        };

        this
            .api
            .publicPayments
            .save({},payment)
            .$promise
            .then(()=> {
                console.log('COMPRADO!')
            })
    }


}