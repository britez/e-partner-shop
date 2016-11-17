'use strict';

export default class SearchCtrl {
    /*@ngInject*/


    constructor($injector, $state, api) {

        this.api = api;
        this.state = $state;

       this.products =  this
            .api
            .publicProducts
            .get({
                query:this.
                    state.params.query
            });

    }


}
