'use strict';
export default class HeaderController {

    /*@ngInject*/
    constructor(authService) {
        this.authService = authService;
    }

    logout() {
        this.authService.logout();
    }


}