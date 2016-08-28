'use strict';

let giuOAuthCfg = () => {

    let oauthCfg = (OAuthProvider) => {
      OAuthProvider.configure({
          clientId: 'epartner-ui',
          revokePath: '/logout'
      });
    };

    return oauthCfg;

};

export default giuOAuthCfg;
