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
        this.deletedBackgroundPic = undefined;
        this.deletedPrincipalPic = undefined;

        if(!this.isNew()){
            this.api.carousels
                .get({id: this.$state.params.id})
                .$promise
                .then(response => {
                    this.entity = response;
                    this.backgroundPic = this.entity.backgroundImage.url;
                    this.currentBackgroundPic = undefined;
                    this.principalPic = this.entity.principalImage.url;
                    this.currentPrincipalPic = undefined;
                }, error => {
                    this.error = 'El carrusel seleccionado no existe';
                })
        }
    }

    isNew(){
        return this.$state.params.id === '';
    }

    save(form) {
        if(!form.$valid
            || (!this.currentPrincipalPic && !this.principalPic)
            || (!this.currentBackgroundPic && !this.backgroundPic)){
            return;
        }
        let params = {};
        if(!this.isNew()){
            params.id = this.$state.params.id;
            this.api
                .carousels
                .update(params,this.entity)
                .$promise
                .then((response) => {
                    let carouselId = response.id;
                    let promises = [];
                    promises.push(this.uploadPrincipalPicture(carouselId));
                    promises.push(this.uploadBackgroundPicture(carouselId));
                    this.$q.all(promises).then(() => {
                        this.init();
                    })
                });
        } else {
            this.api
                .carousels
                .save(params,this.entity)
                .$promise
                .then(response => {
                    let carouselId = response.id;
                    let promises = [];
                    promises.push(this.uploadPrincipalPicture(carouselId));
                    promises.push(this.uploadBackgroundPicture(carouselId));
                    this.$q.all(promises).then(() => {
                       this.$state.go('carousels', {created: true});
                    });
                });
        }

    }

    uploadPrincipalPicture(carouselId) {
        if(!this.currentPrincipalPic) {
            return;
        }

        return this.uploader.upload({
            url: 'api/admin/me/carousels/' + carouselId + '/principal-images',
            data: {file: this.currentPrincipalPic},
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    loadBackgroundPic() {
        this.deletedBackgroundPic = this.backgroundPic;
        this.backgroundPic = undefined;
    }

    deleteBackgroundPic() {
        this.currentBackgroundPic = undefined;
        this.backgroundPic = this.deletedBackgroundPic;
    }

    loadPic() {
        this.deletedPrincipalPic = this.principalPic;
        this.principalPic = undefined;
    }

    deletePrincipalPic() {
        this.currentPrincipalPic = undefined;
        this.principalPic = this.deletedPrincipalPic;
    }

    uploadBackgroundPicture(carouselId) {
        if(!this.currentBackgroundPic) {
            return;
        }

        return this.uploader.upload({
            url: 'api/admin/me/carousels/' + carouselId + '/background-images',
            data: {file: this.currentBackgroundPic},
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

}