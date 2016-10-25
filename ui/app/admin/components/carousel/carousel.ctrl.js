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
                    this.backgroundPic = this.entity.backgroundImage.url;
                    this.currentBackgroundPic = this.entity.backgroundImage.url;
                    this.principalPic = this.entity.principalImage.url;
                    this.currentPrincipalPic = this.entity.principalImage.url;
                }, error => {
                    this.error = 'El carrusel seleccionado no existe';
                })
        }
    }

    isNew(){
        return this.$state.params.id === '';
    }

    save(form) {
        if(!form.$valid || !this.principalPic || !this.backgroundPic){
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
                    this.uploadPrincipalPicture(carouselId)
                        .then(() => {
                        this.uploadBackgroundPicture(carouselId)
                            .then(() => {
                           this.$state.go('carousels', {created: true});
                        });
                    });
                });
        }

    }

    uploadPrincipalPicture(carouselId) {
        return this.uploader.upload({
            url: 'api/carousels/' + carouselId + '/principal-images',
            data: {file: this.principalPic},
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    uploadBackgroundPicture(carouselId) {
        return this.uploader.upload({
            url: 'api/carousels/' + carouselId + '/background-images',
            data: {file: this.backgroundPic},
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

}