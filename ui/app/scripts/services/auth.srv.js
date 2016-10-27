'use strict';

export default class AuthService {

    /*ngInject*/
    constructor(OAuth, OAuthToken) {
        //TODO: obtener desde la api de sso
        this.AUTH_URL = 'http://localhost:18080/oauth/authorize?client_id=CLIENT_ID&response_type=token&redirect_uri=REDIRECT_URI';
        this.REDIRECT_URI = 'http://localhost:18110/';
        this.oauth = OAuth;
        this.oauthToken = OAuthToken;
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