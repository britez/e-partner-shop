'use strict';
export default class BuysController {

    /*@ngInject*/
    constructor(api, $stateParams) {
        this.api = api;
        this.params = $stateParams;
        this.init();
    }

    init() {
        this.number = 0;
        this.max = 5;
        this.last = false;
        this.content = [];
        this.last = false;

        this.paymentTypes = {
            'PAID': 'Pagado',
            'NOT_PAID': 'No pagado',
            'CANCELED': 'Cancelado'
        }

        this.getAllBuys();
    }

    getPaymentState(payment) {
        return this.paymentTypes[payment.state];
    }

    getAllBuys(){

        let params = {
            max: this.max,
            page: this.number
        };

        if(this.query) {
            params.query = this.query;
        }

        this.api.buys
            .get(params)
            .$promise
            .then(response => {
                this.last = response.last;
                this.number = response.number;
                this.content = this.content.concat(response.content);
            })
    }

    loadMoreBuys() {
        if(this.last){
            return;
        }

        this.number = this.number + 1;
        this.getAllBuys();
    }

}