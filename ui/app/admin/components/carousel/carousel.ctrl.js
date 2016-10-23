'use strict';

export default class CarouselController {

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

        if(!this.isNew()){
            this.api.carousels
                .get({id: this.$state.params.id})
                .$promise
                .then(response => {
                    this.entity = response;
                    //this.currentPrincipalPic = this.entity.principalImage;
                    //this.principalPic = this.entity.principalImage.url;
                }, error => {
                    this.error = 'El carrusel seleccionado no existe';
                })
        }
    }

    isNew(){
        return this.$state.params.id === '';
    }

    save(form) {
        if(!form.$valid){ // || !this.principalPic){
            return;
        }
        let params = {};
        if(!this.isNew()){
            params.id = this.$state.params.id;
            this.api
                .carousels
                .update(params,this.entity)
                .$promise
                .then(() => {
                    this.updated = true;
                    this.init();
                });
        } else {
            this.api
                .carousels
                .save(params,this.entity)
                .$promise
                .then(response => {
                    let carouselId = response.id;
                    var promises = [];
                    //promises.push(this.uploadPrincipalPicture(productId));
                    //promises.push(this.uploadPictures(productId));
                    this.$q
                        .all(promises)
                        .then(() => {
                           this.$state.go('carousels', {created: true});
                        });
                });
        }

    }

    uploadProductAndPrincipalPicture(productId) {
        return this.uploader.upload({
            url: 'api/products/' + productId + '/product-images',
            data: {file: this.pictures},
            arrayKey: '',
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    uploadPrincipalPicture(productId) {
        return this.uploader.upload({
            url: 'api/products/' + productId + '/principal-images',
            data: {file: this.principalPic},
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    uploadPictures(productId) {
        return this.uploader.upload({
            url: 'api/products/' + productId + '/images',
            data: {files: this.pictures},
            arrayKey: '',
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

}