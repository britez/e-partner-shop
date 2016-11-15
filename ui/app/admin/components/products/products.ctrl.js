'use strict';

export default class ProductsController {

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
        this.products = [];
        this.last = false;

        this.getAllProducts();
    }

    getAllProducts(){

        let params = {
            max: this.max,
            page: this.number
        };

        this.api.products
            .get(params)
            .$promise
            .then(response => {
                this.last = response.last;
                this.number = response.number;
                this.products = this.products.concat(response.content);
            })
    }

    loadMoreProducts() {
        if(this.last){
            return;
        }

        this.number = this.number + 1;
        this.getAllProducts();
    }

    deleteProduct(product) {
        this.api.products
            .remove({id: product.id})
            .$promise
            .then(() => {
                this.getAllProducts();
            })
    }

}