'use strict';

export default class HomeCategoryCtrl {

    /*@ngInject*/
    constructor(api,$stateParams) {
        this.stateParams = $stateParams;
        this.api = api;
        this.init()

    }


    init() {
        this.api
            .tags
            .get({id: this.stateParams.id})
            .$promise
            .then(response => {
                this.entity = response;
                this.entity.name = this.entity.name;
            });
    }
}