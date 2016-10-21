'use strict';

export default class ProductsController {

    /*ngInject*/
    constructor(api, $q){
        this.api = api;
        this.q = $q;
        this.offset = 0;
        this.limit = 50;
        this.products = [];
        this.getAllProducts();
    }

    getAllProducts(){

        let params = {
            seller_id: 165975732,
            offset: this.offset,
            limit: this.limit
        };

        if(this.offset > this.total) {
            return;
        }

        this.api.meli
            .get(params)
            .$promise
            .then((response) => {
                this.total = response.paging.total;
                this.limit = response.paging.limit;
                this.offset = this.offset + this.limit;
                this.products = this.products.concat(response.results);
            })
    }

    toggleProduct(product){
        product.highlight = !product.highlight;
    }

    loadProducts(){
        let products = this.products
            .filter(prod => prod.highlight)
            .map(prod => this.fetch(prod));

        products.forEach(prod => {
            this.api.products.save({},prod)
        })

    }

    fetch(prod) {
        return {
            name: prod.title,
            description: prod.subtitle,
            price: prod.installments.amount,
            stock: prod.installments.quantity,
            category: {id: 1},
            importId: prod.id,
            published: false,
            principalImage: {
              url: prod.thumbnail
            },
            technicalSpecifications: []
        }

    }

}