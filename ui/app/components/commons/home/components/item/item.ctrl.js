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
        this.entity.price = '4000';
        this.entity.title = 'Tv Led 32 Tcl Sint Digital Hd Usb Hdmi Mod. Tc32d2700';
        this.entity.description = 'Breve descripcion del Producto';
        this.entity.images = [
            'http://mla-s1-p.mlstatic.com/785221-MLA20748837164_062016-O.jpg',
            'http://mla-d1-p.mlstatic.com/tv-led-32-tcl-sint-digital-hd-usb-hdmi-mod-tc-32d2700-214221-MLA20748835912_062016-O.webp?square=false'
        ];
        this.entity.technicalSpecifications = [
            'ORIGEN:	ARGENTINA',
             'ALTO EN CM:	48.20',
            'ANCHO EN CM:	73.10',
            'HDMI:	3'
        ];
        this.entity.currentImage = this.entity.images[0];
    }
}