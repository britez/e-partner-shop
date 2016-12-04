'use strict';
export default class HeaderController {

    /*@ngInject*/
    constructor(authService, $interval, $state) {
        this.authService = authService;
        this.intervalCount = true;
        this.state = $state;
        $interval(() => {
            this.intervalCount = !this.intervalCount;
        }, 3000)

    }

    isAdminUser() {
        return this.authService.isAdmin();
    }

    getUser() {
        return this.authService.user;
    }

    logout() {
        this.authService.logout();
    }

    onEnter(keyEvent) {

        if (keyEvent.which === 13)
            this.state.go('search', {query: this.filter});
    }
}