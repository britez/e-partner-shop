'use strict';
export default class HeaderController {

    /*@ngInject*/
    constructor(OAuth) {
        this.oauth = OAuth;
        console.log('header');
    }
}