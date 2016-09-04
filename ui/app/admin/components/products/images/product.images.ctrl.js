/**
 * Created by maty on 4/9/16.
 */
'use strict';

export default class ProductImagesController {

    /*ngInject*/
    constructor(api, $state, FileUploader, OAuth){

        this.api = api;

        this.$state = $state;

        this.uploader = new FileUploader({
            url: 'api/products/'+this.$state.params.id+'/images',
            headers : {'Authorization': OAuth.getAuthorizationHeader()}
        });

        this.init();
    }

    init() {



        this.entity = {};

        this.api.products
            .get({id: this.$state.params.id})
            .$promise
            .then(response => {
                this.entity = response;
            })

    }

    upload() {
        this.uploader.onCompleteAll = () => {this.$state.go('products')};
        this.uploader.uploadAll();
    }

    save(form) {
        if(!form.$valid){
            return;
        }
        let params = {};
        if(!this.isNew()){
            params.id = this.$state.params.id;
            this.api
                .productImages
                .update(params,this.entity)
                .$promise
                .then(() => {
                    this.$state.go('products');
                });
        } else {
            this.api
                .productImages
                .save(params,this.entity)
                .$promise
                .then(() => {
                    this.$state.go('products');
                });
        }

    }

}