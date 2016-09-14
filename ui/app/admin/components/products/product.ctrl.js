'use strict';

export default class ProductController {

    /*ngInject*/
    constructor(api, $state, Upload, OAuth){
        this.api = api;
        this.$state = $state;

        /*
        this.uploader = new FileUploader({
            url: 'api/products/'+this.$state.params.id+'/images',
            headers : {'Authorization': OAuth.getAuthorizationHeader()}
        });
        */

        this.init();
    }

    init(){
        this.entity = {};
        if(!this.isNew()){
            this.api.products
                .get({id: this.$state.params.id})
                .$promise
                .then(response => {
                    this.entity = response;
                })
        }
    }

    isNew(){
        return this.$state.params.id === '';
    }

    save(form) {
        if(!form.$valid){
            return;
        }
        let params = {};
        if(!this.isNew()){
            params.id = this.$state.params.id;
            this.api
                .products
                .update(params,this.entity)
                .$promise
                .then(() => {
                    this.$state.go('products');
                });
        } else {
            this.api
                .products
                .save(params,this.entity)
                .$promise
                .then((response) => {
                    console.log("sadsa",response);
                    this.$state.go('products-image',{id:response.id});
                });
        }

    }

}