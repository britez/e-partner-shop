'use strict';
/**
 * Created by julian on 25/07/16.
 */

import angular from 'angular';

import { HeaderComponent } from './header/header.cmp';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';

const common = angular
    .module('commons.module', [])
    .component('headerComponent', HeaderComponent)
    .component('footerComponent', FooterComponent)
    .component('homeComponent', HomeComponent)
    .config(($stateProvider, $urlRouterProvider) => {
        $stateProvider
            .state('home', {
                url: '/',
                component: 'homeComponent'
            });
        $urlRouterProvider.otherwise('/');
    })
    .name;

export default common;