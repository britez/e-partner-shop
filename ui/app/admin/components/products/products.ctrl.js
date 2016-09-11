'use strict';

export default class ProductsController {

    /*ngInject*/
    constructor(api){
        this.api = api;
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

}