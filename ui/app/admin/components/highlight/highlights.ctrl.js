'use strict';

export default class HighlightsController {

    /*ngInject*/
    constructor(api, $stateParams){
        this.api = api;
        this.params = $stateParams;
        this.init();
    }

    init() {
        this.max = 5;
        this.page = 0;
        this.last = false;
        this.highlights = [];

        this.getAllTags();
    }


    getAllTags() {

        let params = {
            isCategory: false,
            max: this.max,
            page: this.page
        };

        if(this.query) {
            params.query = this.query;
        }

        this.api
            .tags
            .get(params)
            .$promise
            .then(response => {
                this.page = response.number;
                this.last = response.last;
                this.highlights = this.highlights.concat(response.content);
            })
    }

    loadMoreTags() {
        this.page = this.page + 1;
        this.getAllTags();
    }
}