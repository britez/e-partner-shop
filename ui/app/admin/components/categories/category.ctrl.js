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

    isDisabled(category) {
        return category.totalProducts > 0;
    }

    expand(category) {
        if(CategoryController.isDisabled(category)){
            return;
        }
        category.show = true;
    }

    delete(category) {
        this.api.categories
            .remove({id: category.id})
            .$promise
            .then(() => {
                this.getAllCategories();
            })
    }

}