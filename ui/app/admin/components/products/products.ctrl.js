'use strict';

export default class ProductsController {

    /*ngInject*/
    constructor(api, $stateParams){
        this.api = api;
        this.params = $stateParams;
        this.getAllProducts();
    }

    getAllProducts(){
        this.api.products
            .get()
            .$promise
            .then((response) => {
                this.products = response.content;
            })
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