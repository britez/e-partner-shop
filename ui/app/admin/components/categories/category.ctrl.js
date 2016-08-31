'use strict';

export default class CategoryController {

    /*ngInject*/
    constructor(api){
        this.api = api;
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