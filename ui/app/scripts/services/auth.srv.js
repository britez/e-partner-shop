'use strict';

export default class AuthService {

    /*ngInject*/
    constructor(OAuth, OAuthToken, api, $location) {
        this.REDIRECT_URI = $location.absUrl();

        this.api = api;
        this.oauth = OAuth;
        this.oauthToken = OAuthToken;
        this.init();
    }

    getUser() {
        var promise = this.api.user.get().$promise;
        promise.then(response => {
            this.user = response;
        });
        return promise;
    }

    init() {
        if(this.isAuthenticated()){
            this.api
                .user
                .get()
                .$promise
                .then(response => {
                    this.user = response;
                });
        }

        this.api
            .settings
            .get()
            .$promise
            .then(response => {
                this.AUTH_URL = response.auth_url;
            });
    }

    isAdmin() {
        if(!this.user) {
            return false;
        }
        return this.user.authorities.filter(it => it.authority === 'ROLE_admin').length != 0;
    }

    login() {
        this.oauth.login(this.AUTH_URL, this.REDIRECT_URI);
    }

    isAuthenticated(){
        let token = this.oauthToken.getToken();
        return typeof token !== 'undefined';
    }

    logout() {
        this.oauth.logout();
    }
}