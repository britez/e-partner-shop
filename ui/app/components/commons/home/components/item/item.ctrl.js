'use strict';

export default class ItemCtrl{

    /*@ngInject*/
    constructor($http,$state, $stateParams, api) {
        this.$state = $state;
        this.http = $http;
        this.stateParams = $stateParams;
        this.api = api;
        this.quantity = 0;
        this.init();
    }

    addItem() {
        if(this.quantity === this.entity.stock) {
            return;
        }
        this.quantity = this.quantity + 1;
    }

    removeItem() {
        if(this.quantity === 0) {
            return;
        }
        this.quantity = this.quantity - 1;
    }

    init() {

        this.paymentOptions = [
            {
                id: 1,
                title: 'Pago en efectivo.',
                icon: 'fa fa-credit-card'
            },
            {
                id: 2,
                title: 'Pago con tarjeta de crédito/débito.',
                icon: 'fa fa-credit-card'
            },
            {
                id: 3,
                title: 'Pago con MercadoPago.',
                icon: 'pf pf-mercado-pago-sign'
            }
        ];

        this.selectedPayment = this.paymentOptions[0];

        if(!this.stateParams.id) {
            this.entity = {};
            this.entity.price = '4000';
            this.entity.name = 'Tv Led 32 Tcl Sint Digital Hd Usb Hdmi Mod. Tc32d2700';
            this.entity.description = 'Breve descripcion del Producto';
            this.entity.images = [
                'http://mla-s1-p.mlstatic.com/785221-MLA20748837164_062016-O.jpg',
                'http://mla-d1-p.mlstatic.com/tv-led-32-tcl-sint-digital-hd-usb-hdmi-mod-tc-32d2700-214221-MLA20748835912_062016-O.webp?square=false'
            ];
            this.entity.technicalSpecifications = [
                {key:'ORIGEN', value: 'ARGENTINA'},
                {key:'ALTO EN CM', value: '48.20'},
                {key:'ANCHO EN CM', value: '73.10'},
                {key:'HDMI', value: '3'}
            ];
            this.entity.currentImage = this.entity.images[0];
        } else {
            this.api
                .products
                .get({id:this.stateParams.id})
                .$promise
                .then(response => {
                    this.entity = response;
                    this.entity.principalImage = this.entity.principalImage.url;
                    this.entity.images = this.entity.images.map(img => img.url);
                    this.entity.images.push(this.entity.principalImage);
                });
        }
    }
}