'use strict';

export default class HomeCtrl{

    /*@ngInject*/
    constructor() {
        this.myInterval = 5000;
        this.active = 0;
        this.slides = [
            {
                id: 0,
                text: 'test',
                image: 'http://www.planwallpaper.com/static/images/518164-backgrounds.jpg'
            },
            {
                id: 1,
                text: 'test2',
                image: 'http://feelgrafix.com/data/background/background-8.jpg'
            }
        ];
    }
}