'use strict';

export default class MeliConfigController {

    /*ngInject*/
    constructor(api, $window){
        this.api = api;
        this.window = $window;
    }

    go() {
        this.window.location.replace('http://auth.mercadolibre.com.ar/authorization?response_type=code&client_id=8319105886566033')
    }

}