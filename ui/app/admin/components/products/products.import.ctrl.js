'use strict';

export default class ProductsController {

    /*ngInject*/
    constructor(api, $q, $state){
        this.api = api;
        this.state = $state;
        this.q = $q;

        this.init();
    }

    init() {
        this.max = 5;
        this.page = 0;
        this.products = [];

        this.getAllProducts();
        this.api.categories
            .get()
            .$promise
            .then(response => {
                this.categories = response.content;
            });

    }

    getAllProducts(){

        let params = {
            max: this.max,
            page: this.page
        };

        if(this.query) {
            params.query = this.query;
        }

        this.api.productImports
            .get(params)
            .$promise
            .then(response => {
                this.last = response.last;
                this.products = this.products.concat(response.content);
            }, error => {
                if(error.status === 409) {
                    this.state.go('meliConfig');
                }
            })
    }

    loadMoreProducts() {
        if(this.last)
            return;

        this.page = this.page + 1;

        this.getAllProducts();
    }

    toggleProduct(product){
        if(product.imported) {
            return;
        }
        product.highlight = !product.highlight;
    }

    loadProducts(){
        let products = this.products
            .filter(prod => prod.highlight);

        this.api
            .productImports
            .save({categoryId: this.category.id}, products)
            .$promise
            .then(() => {
                this.state.go('products', {imported: true})
            });

    }

}