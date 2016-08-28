/*jshint strict:false */
'use strict';

import angular from 'angular';
import 'giu-module-security/app/scripts/sec.module.js';
import 'angular-resource';
import 'angular-ui-router';
import 'angular-translate';
import 'angular-translate-loader-static-files';
import 'angular-messages';
import 'bower:angular-bootstrap@1.3.3';

//Todo: Revisar
import 'module-crud-ui/app/scripts/crud.module.js';

import Routes from './scripts/config/router.cfg';
import ApiCfg from './scripts/config/api.cfg';
import OAuthCfg from './scripts/config/oauth.cfg.js';
import ForbiddenInterceptor from './scripts/config/forbidden.interceptor.cfg.js';
import ForbiddenConfig from './scripts/config/forbidden.config.js';

import components from './components/components.js';
import { AppComponent } from './app.component';

import './templates';

const app = angular.module('giu-ui.app',
  [
    'ui.router',
    'security.module',
    'crud.module',
    'ui.bootstrap',
    'epartner-shop-ui-templates',
    'ngMessages',
    components
  ]);
/* global System, document */

System.import('jquery').then(function () {
  angular.element(document).ready(function () {
    angular.bootstrap(document.body, [app.name], {
      // strictDi: trueSegment
    });
  });
});

app.run((OAuthToken, $location) => {

    let token = $location.path().substr(1);
    if(typeof token !== 'undefined' && token !== '' && token.indexOf('access_token')===0 ){
        OAuthToken.setToken(token);
        window.location.replace(OAuthToken.getLogin().redirect);
    }
});

app
    .config(ApiCfg())
    .config(OAuthCfg())
    .factory('forbiddenInterceptor', ForbiddenInterceptor)
    .config(ForbiddenConfig)
    .component('epartnerApp', AppComponent);

export default app;
