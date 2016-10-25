'use strict';

export default class HomeCtrl{

    /*@ngInject*/
    constructor($state, api) {
        this.myInterval = 5000;
        this.active = 0;
        this.slides = [
            {
                id: 0,
                text: 'test',
                image: '/cdn/cdn-epartner-shop-ui/images/black-bg.jpg'
            },
            {
                id: 1,
                text: 'test2',
                image: '/cdn/cdn-epartner-shop-ui/images/white-bg.jpg'
            }
        ];
        this.state = $state;
        this.api = api;
        this.getTags();
        this.getCarousels();
    }

    getCarousels() {
        this.api
            .carousels
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