'use strict';

export default class CategoryController {

    /*ngInject*/
    constructor(api, $stateParams){
        this.api = api;
        this.params = $stateParams;
        this.init();
    }

    init() {
        this.max = 20;
        this.number = 0;
        this.last = false;
        this.categories = [];

        this.getAllCategories();
    }

    getAllCategories(){

        let params = {
            page: this.number,
            max: this.max
        };

        if(this.query) {
            params.query = this.query;
        }

        this.api.categories
            .get(params)
            .$promise
            .then(response => {
                this.number = response.number;
                this.last = response.last;
                this.categories = this.categories.concat(response.content);
            });
    }

    loadMoreCategories() {
        if(this.last) {
            return;
        }

        this.number = this.number + 1;

        this.getAllCategories();
    }

    isDisabled(category) {
        return category.totalProducts > 0;
    }

    expand(category) {
        if(this.isDisabled(category)){
            return;
        }
        category.show = true;
    }

    delete(category) {
        this.api.categories
            .remove({id: category.id})
            .$promise
            .then(() => {
                this.init();
            });
    }

}