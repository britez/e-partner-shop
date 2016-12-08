'use strict';

export default class PaymentController {

    /*ngInject*/
    constructor(api, $stateParams){
        this.api = api;
        this.params = $stateParams;
        this.init();

        this.states = {
            'PAID': 'Pagado',
            'NOT_PAID': 'No pagado',
            'CANCELED': 'Cancelado'
        };

        this.paymentTypes = {
            'CASH': 'Efectivo',
            'DEBIT': 'Tarjeta de débito',
            'MERCADO_PAGO': 'Mercado Pago'
        }
    }

    init() {
        this
            .api
            .payments
            .get({id: this.params.id})
            .$promise
            .then(response => {
                this.entity = response;
            }, error => {
                this.error = 'El pago seleccionado no existe';
            })
    }

    getStateId() {
        if(!this.entity) {
            return;
        }
        return this.entity.state.toLowerCase();
    }

    getState() {
        if(!this.entity) {
            return;
        }
        return this.states[this.entity.state];
    }

    getPaymentType() {
        if(!this.entity) {
            return;
        }
        return this.paymentTypes[this.entity.paymentType];
    }

    isNotPaid(){
        if(!this.entity){
            return false;
        }
        return this.entity.state === 'NOT_PAID';
    }

    isPaid(){
        if(!this.entity){
            return false;
        }
        return this.entity.state === 'PAID';
    }

    cancel() {
        this.entity.state = 'CANCELED';
        this._update();
    }

    paid() {
        this.entity.state = 'PAID';
        this._update();
    }

    _update() {
        this
            .api
            .payments
            .update({id: this.entity.id}, this.entity)
            .$promise
            .then(response => {
                this.updated = true;
                this.init();
            })
    }

}