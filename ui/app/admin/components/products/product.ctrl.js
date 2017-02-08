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
        this.picsToDelete = [];

        this.api.categories
            .get({max: 100})
            .$promise
            .then(response => {
                this.categories = response.content;
            });


        if(!this.isNew()){
            this.api.products
                .get({id: this.$state.params.id})
                .$promise
                .then(response => {
                    this.entity = response;
                    this.currentPrincipalPic = undefined;
                    this.principalPic = this.entity.principalImage.url;
                    if (this.entity.technicalSpecifications.length === 0) {
                        this.entity.technicalSpecifications.push({})
                    }
                    this.currentPics = this.entity.images
                        .filter(img => !img.principal)
                        .map(img => undefined);
                    this.deletedPics = this.entity.images
                        .filter(img => !img.principal)
                        .map(img => undefined);
                    this.pictures = this.entity
                        .images
                        .filter(img => !img.principal);
                }, error => {
                    this.error = 'El producto seleccionado no existe';
                })
        } else {
            this.entity.isImported = false;
            this.entity.technicalSpecifications = [{}];
        }
    }

    isNew(){
        return this.$state.params.id === '';
    }

    shouldSelect($event){
        console.log($event);
        return typeof this.entity.category == 'object';
    }

    save(form) {
        if(!form.$valid || (!this.principalPic && !this.currentPrincipalPic)){
            return;
        }
        let params = {};
        if(!this.isNew()){
            params.id = this.$state.params.id;
            this.api
                .products
                .update(params,this.entity)
                .$promise
                .then(response => {
                 let productId = response.id;
                    var promises = [];
                    this.updated = true;
                    promises.push(this.uploadPictures(productId));
                    promises.push(this.uploadPrincipalPicture(productId));
                    this.$q
                        .all(promises)
                        .then(() => {
                            this.init();
                        });
                });
        } else {
            this.api
                .products
                .save(params,this.entity)
                .$promise
                .then(response => {
                    let productId = response.id;
                    var promises = [];
                    promises.push(this.uploadPictures(productId));
                    promises.push(this.uploadPrincipalPicture(productId));
                    this.$q
                        .all(promises)
                        .then(() => {
                           this.$state.go('products', {created: true});
                        });
                });
        }

    }

    uploadPrincipalPicture(productId) {
        if(!this.currentPrincipalPic) {
            return;
        }

        return this.uploader.upload({
            url: 'api/admin/me/products/' + productId + '/principal-images',
            data: {file: this.currentPrincipalPic},
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    uploadPictures(productId) {
        let toCreate = this.currentPics.filter(it => typeof it !== 'undefined');
        if(toCreate.length === 0
            && this.picsToDelete.length === 0) {
            return;
        }

        return this.uploader.upload({
            url: 'api/admin/me/products/' + productId + '/images',
            data: {files: this.currentPics, toDelete: this.picsToDelete},
            arrayKey: '',
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    loadFile(pics, index) {
        let limit = index + pics.length;
        for(var i = index; i < limit; i++) {
            this.currentPics[i] = pics.shift();

            let currentPic = this.pictures[i];
            if(currentPic) {
                this.picsToDelete.push(currentPic.id);
                this.pictures[i] = undefined;
            }
        }
    }

    loadPrincipalPic() {
        this.deletedPrincipalPic = this.principalPic;
        this.principalPic = undefined;
    }

    deletePrincipal() {
        this.currentPrincipalPic = undefined;
        this.principalPic = this.deletedPrincipalPic;
    }

    deleteCurrentPicture(index) {
        this.currentPics[index] = undefined;
        this.picsToDelete.push(this.pictures[index].id);
        this.pictures[index] = this.deletedPics[index];
    }

    addTechnicalSpecification() {
        this.entity.technicalSpecifications.push({});
    }

    removeTechnicalSpecification(position) {
        this.entity.technicalSpecifications.splice(position, 1);
    }

}