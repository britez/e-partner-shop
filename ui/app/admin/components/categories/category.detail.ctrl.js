'use strict';

export default class CategoryDetailController {

    /*ngInject*/
    constructor(api, $state){
        this.api = api;
        this.$state = $state;
        this.init();
    }

    init(){
        this.entity = {};
        if(!this.isNew()){
            this.api.categories
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
                .categories
                .update(params,this.entity)
                .$promise
                .then(() => {
                    this.$state.go('categories');
                });
        } else {
            this.api
                .categories
                .save(params,this.entity)
                .$promise
                .then(() => {
                    this.$state.go('categories');
                });
        }

    }
}