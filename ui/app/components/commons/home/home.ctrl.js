'use strict';

export default class HomeCtrl{

    /*@ngInject*/
    constructor($state, api) {
        this.state = $state;
        this.api = api;
        this.getTags();
        this.getCarousels();
    }

    getCarousels() {
        this.api
            .publicCarousels
            .get()
            .$promise
            .then(response => {
                this.carousels = response.content;
            })
    }

    getUrl(carousel) {
        return "background-image: url('" + carousel.backgroundImage.url + "')";
    }

    getTags(){
        this.api
            .tags
            .get()
            .$promise
            .then(response => {
                this.tags = response.content;
            })
    }
}