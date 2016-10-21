'use strict';

export default class HighlightsController {

    /*ngInject*/
    constructor(api, $stateParams){
        this.api = api;
        this.params = $stateParams;
        this.init();
    }

    init() {
        this.api
            .tags
            .get({isCategory:false})
            .$promise
            .then(response => {
                this.highlights = response.content;
            })
    }

}