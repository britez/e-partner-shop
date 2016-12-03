'use strict';
export default class BoughtController {

    /*@ngInject*/
    constructor(authService, $state) {
        this.authService = authService;
        this.params = $state.params;
    }
}