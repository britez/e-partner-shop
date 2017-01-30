'use strict';

export default class ItemCtrl{

    /*@ngInject*/
    constructor($http,$state, $stateParams, api, paymentService) {
        this.$state = $state;
        this.paymentService = paymentService;
        this.http = $http;
        this.stateParams = $stateParams;
        this.api = api;
        this.quantity = 1;
        this.init();
    }

    canBuy() {
        if(!this.entity) {
            return false;
        }

        return this.entity.imported && this.entity.stock > 1;
    }

    addItem() {
        if(this.quantity >= this.entity.stock - 1) {
            return;
        }
        this.quantity = this.quantity + 1;
    }

    removeItem() {
        if(this.quantity === 1) {
            return;
        }
        this.quantity = this.quantity - 1;
    }

    selectPayment(option) {
        this.selectedPayment = option;
        this.editPayment = false;
    }

    init() {
        this.editPayment = false;
        this.paymentOptions = this.paymentService.getPaymentOptions();

        this.selectedPayment = this.paymentOptions[0];

        this.api
            .publicProducts
            .get({id:this.stateParams.id})
            .$promise
            .then(response => {
                this.entity = response;
                this.entity.principalImage = this.entity.principalImage.url;
                this.entity.images = this.entity.images.map(img => img.url);
                this.entity.images.push(this.entity.principalImage);
            });
    }

    pay() {
        this.buyService.setQuantity(this.quantity);
        this.buyService.setProductId(this.entity.id);
        this.buyService.setPaymentOption(this.selectedPayment);
        this.$state.go('buy');
    }
}