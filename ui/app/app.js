/*jshint strict:false */
'use strict';

import angular from 'angular';
import 'angular-loading-bar';
import 'giu-module-security/app/scripts/sec.module.js';
import 'angular-resource';
import 'angular-ui-router';
import 'angular-messages';
import 'angular-animate';
import 'bower:angular-bootstrap@1.3.3';
import 'github:jotielim/ng-magnify@master/src/js/ng-magnify';

//Todo: Revisar
import 'module-crud-ui/app/scripts/crud.module.js';

import Routes from './scripts/config/router.cfg';
import ApiCfg from './scripts/config/api.cfg';
import OAuthCfg from './scripts/config/oauth.cfg.js';
import ForbiddenInterceptor from './scripts/config/forbidden.interceptor.cfg.js';
import ForbiddenConfig from './scripts/config/forbidden.config.js';

import { AppComponent } from './app.component';

//Create modules
import './components/commons/commons.module';
import './admin/admin.module.cfg';
import 'ng-file-upload/dist/ng-file-upload';

import AuthService from './scripts/services/auth.srv';

import './templates';

const app = angular.module('giu-ui.app',
  [
    'ui.router',
      'angular-loading-bar',    
      'security.module',
    'crud.module',
    'ui.bootstrap',
    'ngAnimate',
    'epartner-shop-ui-templates',
    'ngMessages',
    'commons.module',
    'admin.module',
    'ngMagnify',
    'ngFileUpload'
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
    .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
        cfpLoadingBarProvider.spinnerTemplate =
            '<div class="loading-container">' +
                '<div class="animated fadeInLeft" style="width: 100%; height: 100%;">'+
                    '<img class="animated wobble" src="/cdn/cdn-epartner-shop-ui/images/icon2.png"/>' +
                '</div>' +
            '</div>';
    }])
    .component('epartnerApp', AppComponent)
    .service('authService', AuthService);

export default app;
