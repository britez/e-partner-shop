'use strict';

export default class ProductController {

    /*ngInject*/
    constructor(api, $state, Upload, OAuth, $q){
        this.api = api;
        this.$state = $state;
        this.uploader = Upload;
        this.OAuth = OAuth;
        this.$q = $q;
        this.init();
    }

    init(){
        this.entity = {};
        this.pictures = [];
        this.currentPics = [];

        this.api.categories
            .get()
            .$promise
            .then((response) => {
                this.categories = response.content;
            });


        if(!this.isNew()){
            this.api.products
                .get({id: this.$state.params.id})
                .$promise
                .then(response => {
                    this.entity = response;
                    this.currentPrincipalPic = this.entity
                        .images
                        .find(img => img.principal);
                    this.currentPics = this.entity
                        .images
                        .filter(img => !img.principal);
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
                    this.updated = true;
                    this.init();
                });
        } else {
            this.api
                .products
                .save(params,this.entity)
                .$promise
                .then(response => {
                    let productId = response.id;
                    var promises = [];
                    promises.push(this.uploadPrincipalPicture(productId));
                    promises.push(this.uploadPictures(productId));
                    this.$q
                        .all(promises)
                        .then(() => {
                           this.$state.go('products', {created: true});
                        });
                });
        }

    }

    uploadPrincipalPicture(productId) {
        return this.uploader.upload({
            url: 'api/products/' + productId + '/principal-images',
            data: {file: this.principalPic},
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    uploadPictures(productId) {
        let promises = [];
        this.pictures.forEach(picture => {
            promises.push(this.uploader.upload({
                url: 'api/products/' + productId + '/images',
                data: {file: picture},
                headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
            }))
        });
        return promises;
    }

}