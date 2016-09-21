'use strict';

export default class CategoryController {

    /*ngInject*/
    constructor(api, $stateParams){
        this.api = api;
        this.params = $stateParams;
        this.init();
    }

    init() {
        this.getAllCategories();
    }

    getAllCategories(){
        this.api.categories
            .get()
            .$promise
            .then((response) => {
                this.categories = response.content;
            })
    }

}