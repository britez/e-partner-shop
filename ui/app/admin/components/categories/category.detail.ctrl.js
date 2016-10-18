'use strict';

export default class CategoryDetailController {

    /*ngInject*/
    constructor(api, $state, $q){
        this.api = api;
        this.$state = $state;
        this.$q = $q;
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
                }, error => { this.error = 'No existe la categoría seleccionada.'})
        }
    }

    loadTag() {
        if(this.entity.highlight) {
            this.categoryProducts.map(it => {
                it.highlight = typeof this.entity.tag.products.find(tag => tag.id === it.id) !== 'undefined';
            })
        }
    }

    getSelectedProducts() {
        if(!this.categoryProducts) {
            return [];
        }
        return this.categoryProducts.filter(it => it.highlight);
    }

    isNew(){
        return this.$state.params.id === '';
    }

    toggleProduct(product){
        product.highlight = !product.highlight;

        if(this.getSelectedProducts().length > 4) {
            product.highlight = false;
        }
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
                .then((response) => {
                    this.updated = true;
                    this.entity = response;
                    this.saveOrUpdateTags();
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
                this.loadTag();
            });
    }

    saveOrUpdateTags() {
        if(this.entity.highlight) {
            let prodIds = this.categoryProducts
                .filter(prod => prod.highlight);

            if(this.entity.tag) {
                this.entity.tag.products = prodIds;
                this.api
                    .tags
                    .update({id: this.entity.tag.id}, this.entity.tag)
                    .$promise
                    .then(()=> {
                        this.init();
                    })
            } else {
                this.api
                    .tags
                    .save({}, {isCategory: true, name: this.entity.name, products: prodIds})
                    .$promise
                    .then(() => {
                        this.init()
                    })
            }
        }
    }

}