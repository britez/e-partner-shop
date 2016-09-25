'use strict';

export default class HomeCtrl{

    /*@ngInject*/
    constructor($state) {
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
    }

    redirect(){
        this.state.go('item')

    }
}