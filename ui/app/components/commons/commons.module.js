'use strict';
/**
 * Created by julian on 25/07/16.
 */

import angular from 'angular';

import { HeaderComponent } from './header/header.cmp';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { ItemComponent } from '../commons/home/components/item/item.component';
import { HomeCategoryComponent } from './home/home.category.component';

const common = angular
    .module('commons.module', [])
    .component('headerComponent', HeaderComponent)
    .component('footerComponent', FooterComponent)
    .component('homeComponent', HomeComponent)
    .component('homeCategoryComponent', HomeCategoryComponent)
    .component('itemComponent', ItemComponent)
    .config(($stateProvider, $urlRouterProvider) => {
        $stateProvider
            .state('home', {
                url: '/',
                component: 'homeComponent'
            })
            .state('home-categories', {
                url: '/categories/:id',
                component: 'homeCategoryComponent'
            });
        $stateProvider
            .state('item', {
                url: '/item',
                component: 'itemComponent'
            });
        $urlRouterProvider.otherwise('/');
    })
    .name;

export default common;