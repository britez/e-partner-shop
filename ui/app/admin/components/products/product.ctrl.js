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
                    this.currentPrincipalPic = this.entity.principalImage;
                    this.principalPic = this.entity.principalImage.url;
                    if (this.entity.technicalSpecifications.length === 0) {
                        this.entity.technicalSpecifications.push({})
                    }
                    this.currentPics = this.entity
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
        if(!form.$valid || !this.principalPic){
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

    uploadProductAndPrincipalPicture(productId) {
        return this.uploader.upload({
            url: 'api/admin/me/products/' + productId + '/images',
            data: {file: this.principalPic},
            arrayKey: '',
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    uploadPrincipalPicture(productId) {
        return this.uploader.upload({
            url: 'api/admin/me/products/' + productId + '/principal-images',
            data: {file: this.principalPic},
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    uploadPictures(productId) {
        return this.uploader.upload({
            url: 'api/admin/me/products/' + productId + '/images',
            data: {files: this.pictures, toDelete: this.picsToDelete},
            arrayKey: '',
            headers: {'Authorization': this.OAuth.getAuthorizationHeader()}
        });
    }

    loadFile(pics, index) {
        let limit = index + pics.length;
        for(var i = index; i < limit; i++) {
            this.pictures[i] = pics.shift();

            let currentPic = this.currentPics[i];
            if(currentPic) {
                this.picsToDelete.push(currentPic.id);
                this.currentPics[i] = undefined;
            }
        }
    }

    addTechnicalSpecification() {
        this.entity.technicalSpecifications.push({});
    }

    removeTechnicalSpecification(position) {
        this.entity.technicalSpecifications.splice(position, 1);
    }

}