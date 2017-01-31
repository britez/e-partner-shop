'use strict';

export default class CategoryDetailController {

    /*ngInject*/
    constructor(api, $state, $q){
        this.api = api;
        this.$state = $state;
        this.$q = $q;
        this.init();
        this.productSelect = false;
    
    }

    init(){
        this.max = 5;
        this.page = 0;
        this.last = false;
        this.categoryProducts = [];

        this.entity = {};
        if(!this.isNew()){
            this.loadCategory();
        }
    }

    loadCategory() {
        this.api.categories
            .get({id: this.$state.params.id})
            .$promise
            .then(response => {
                this.entity = response;
                this.loadCategoryProducts();
            }, error => { this.error = 'No existe la categoría seleccionada.'})
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
                    this.productSelect = false;
    
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
        let params = {
            id: this.$state.params.id,
            max: this.max,
            page: this.page
        };

        this.api
            .categoryProducts
            .get(params)
            .$promise
            .then(response => {
                this.last = response.last;
                this.categoryProducts = this.categoryProducts.concat(response.content);
                this.loadTag();
            });
    }

    loadMoreProducts() {
        if(this.last) {
            return;
        }

        this.page = this.page + 1;
        this.loadCategoryProducts();
    }

    saveOrUpdateTags() {
            if (this.entity.highlight) {
                let prodIds = this.categoryProducts
                    .filter(prod => prod.highlight);
        
                if (this.entity.tag) {
                    this.entity.tag.products = prodIds;
                    this.api
                        .tags
                        .update({id: this.entity.tag.id}, this.entity.tag)
                        .$promise
                        .then((response) => {
                            this.init();
                           
                        },(error)=>{
                        if(error.status === 409){
                            this.productSelect  = true;
                        }
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