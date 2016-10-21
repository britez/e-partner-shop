'use strict';

export default class HighLightDetailController {

    /*ngInject*/
    constructor(api, $state){
        this.api = api;
        this.$state = $state;
        this.init();
    }

    init(){
        this.entity = {};

        if(!this.isNew()){
            this.api.tags
                .get({id: this.$state.params.id})
                .$promise
                .then(response => {
                    this.entity = response;
                    this.loadProducts();
                }, () => {
                    this.error = 'El destacado seleccionado no existe';
                })
        } else {
            this.entity.isCategory = false;
            this.loadProducts();
        }

    }

    loadProducts() {
        this.api.products
            .get({isPublished: true})
            .$promise
            .then(response => {
                this.products = response.content;
                if(this.entity.products) {
                    this.products.map(product => {
                        product.highlight = typeof this.entity.products.find(prod => prod.id === product.id) !== 'undefined'
                    })
                }
            })
    }

    toggleProduct(product){
        product.highlight = !product.highlight;

        if(this.getSelectedProducts().length > 4) {
            product.highlight = false;
        }
    }

    getSelectedProducts() {
        if(!this.products) {
            return [];
        }
        return this.products.filter(it => it.highlight);
    }

    isNew(){
        return this.$state.params.id === '';
    }

    save(form) {
        if(!form.$valid){
            return;
        }
        let params = {};
        this.entity.products = this.getSelectedProducts();
        if(!this.isNew()){
            params.id = this.$state.params.id;
            this.api
                .tags
                .update(params,this.entity)
                .$promise
                .then(() => {
                    this.updated = true;
                    this.init();
                });
        } else {
            this.api
                .tags
                .save(params,this.entity)
                .$promise
                .then(() => {
                    this.$state.go('highlights', {created: true});
                });
        }

    }

}