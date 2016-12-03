'use strict';

export default class PaymentsController {

    /*ngInject*/
    constructor(api, $stateParams){
        this.api = api;
        this.params = $stateParams;
        this.init();
    }

    init() {
        this.number = 0;
        this.max = 5;
        this.last = false;
        this.payments = [];
        this.last = false;

        this.getAllPayments();
    }

    getAllPayments(){

        let params = {
            max: this.max,
            page: this.number
        };

        if(this.query) {
            params.query = this.query;
        }

        this.api.payments
            .get(params)
            .$promise
            .then(response => {
                this.last = response.last;
                this.number = response.number;
                this.payments = this.payments.concat(response.content);
            })
    }

    loadMorePayments() {
        if(this.last){
            return;
        }

        this.number = this.number + 1;
        this.getAllPayments();
    }

}