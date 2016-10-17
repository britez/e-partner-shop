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
                .then((response) => {
                    this.updated = true;
                    this.entity = response;
                    if(this.entity.highlight) {
                        let prodIds = this.categoryProducts
                            .filter(prod => prod.highlight);
                        this.api
                            .tags
                            .save({}, {isCategory: true, name: this.entity.name, products: prodIds})
                            .$promise
                            .then(response => {
                                //todo hacer algo
                            })
                    }
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
            });
    }

}