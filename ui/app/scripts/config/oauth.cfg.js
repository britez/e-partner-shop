'use strict';

let OAuthCfg = () => {

    let oauthCfg = (OAuthProvider) => {
      OAuthProvider.configure({
          clientId: 'epartner-ui',
          revokePath: '/logout'
      });
    };

    return oauthCfg;

};

export default OAuthCfg;
