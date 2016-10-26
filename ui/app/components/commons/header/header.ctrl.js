'use strict';
export default class HeaderController {

    /*@ngInject*/
    constructor(authService, api) {
        this.authService = authService;
        this.api = api;
    }


    search(){


        this
            .api
            .products
            .get({
                filter:this.filter
            });
    }

    onEnter(keyEvent) {

        if (keyEvent.which === 13)
            this.search();
    }

}