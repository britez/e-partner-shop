'use strict';
export default class BoughtController {

    /*@ngInject*/
    constructor(authService, $state, api, paymentService) {
        this.authService = authService;
        this.api = api;
        this.$state = $state;
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

                if(this.product.stock <= this.params.quantity) {
                    this.$state.go('item', {id: this.product.id})
                }

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

        if (this.paymentService.isMercadoPago(this.payment)){
            this.api
                .mercadoPago
                .save({}, payment)
                .$promise
                .then(response => {
                    window.location.replace(response.paymentUrl);
                })
        } else {
            this
                .api
                .publicPayments
                .save({},payment)
                .$promise
                .then(()=> {
                    this.$state.go('bought-success')
                })
        }

    }


}