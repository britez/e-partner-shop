'use strict';

export default class AuthService {

    /*ngInject*/
    constructor(OAuth, OAuthToken, api) {
        //TODO: obtener desde la api de sso
        this.AUTH_URL = 'http://localhost:18080/oauth/authorize?client_id=CLIENT_ID&response_type=token&redirect_uri=REDIRECT_URI';
        this.REDIRECT_URI = 'http://localhost:18110/';

        this.api = api;
        this.oauth = OAuth;
        this.oauthToken = OAuthToken;
        this.init();
    }

    init() {
        this.api
            .user
            .get()
            .$promise
            .then(response => {
            this.user = response;
        })
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