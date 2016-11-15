'use strict';
export default class HeaderController {

    /*@ngInject*/
    constructor(authService, $interval) {
        this.authService = authService;
        this.intervalCount = true;
        $interval(() => {
            this.intervalCount = !this.intervalCount;
        }, 3000)
    }

    logout() {
        this.authService.logout();
    }


}