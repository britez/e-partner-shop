'use strict';

export default class ItemCtrl{

    /*@ngInject*/
    constructor($http,$state) {
        this.$state = $state;
        this.http = $http;
        this.init();
    }

    init() {
        this.entity = {};
        this.entity.images = [
            'http://mla-s1-p.mlstatic.com/785221-MLA20748837164_062016-O.jpg',
            'http://mla-d1-p.mlstatic.com/tv-led-32-tcl-sint-digital-hd-usb-hdmi-mod-tc-32d2700-214221-MLA20748835912_062016-O.webp?square=false'
        ];
        this.entity.currentImage = this.entity.images[0];
    }
}