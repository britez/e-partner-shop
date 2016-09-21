'use strict';

export default class CategoryDetailController {

    /*ngInject*/
    constructor(api, $state){
        this.api = api;
        this.$state = $state;
        this.init();
    }

    init(){
        this.entity = {};
        if(!this.isNew()){
            this.api.categories
                .get({id: this.$state.params.id})
                .$promise
                .then(response => {
                    this.entity = response;
                    this.loadCategoryProducts();
                })
        }
    }

    isNew(){
        return this.$state.params.id === '';
    }

    save(form) {
        if(!form.$valid){
            form.$touched = true;
            return;
        }
        let params = {};
        if(!this.isNew()){
            params.id = this.$state.params.id;
            this.api
                .categories
                .update(params,this.entity)
                .$promise
                .then(() => {
                    this.updated = true;
                    this.init();
                });
        } else {
            this.api
                .categories
                .save(params,this.entity)
                .$promise
                .then(() => {
                    this.$state.go('categories', {created: true});
                });
        }

    }

    loadCategoryProducts() {
        this.api
            .categoryProducts
            .get({id: this.$state.params.id})
            .$promise
            .then(response => {
                this.categoryProducts = response.content;
                this.categoryProducts.map(categoryProduct => {
                    categoryProduct.principalImage = categoryProduct.images.find(image => image.principal);
                })
            });
    }

}