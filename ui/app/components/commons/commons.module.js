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
import { SearchComponent } from './search/search.component';
import { BoughtComponent } from './bought/bought.component';
import { BoughtSuccessComponent } from './bought/bought.success.component';

const common = angular
    .module('commons.module', [])
    .component('headerComponent', HeaderComponent)
    .component('footerComponent', FooterComponent)
    .component('homeComponent', HomeComponent)
    .component('homeCategoryComponent', HomeCategoryComponent)
    .component('itemComponent', ItemComponent)
    .component('searchComponent', SearchComponent)
    .component('buyComponent', BoughtComponent)
    .component('boughtSuccessComponent', BoughtSuccessComponent)
    .config(($stateProvider, $urlRouterProvider) => {
        $stateProvider
            .state('home', {
                url: '/',
                component: 'homeComponent'
            })
            .state('home-categories', {
                url: '/categories/:id',
                component: 'homeCategoryComponent'
            })
            .state('item', {
                url: '/item/:id',
                component: 'itemComponent'
            })
            .state('search', {
                url: '/search/:query',
                component: 'searchComponent'
            })
            .state('buy', {
                url: '/buy/:id?payment&quantity',
                component: 'buyComponent'
            })
            .state('bought-success', {
                url: '/buy-success',
                component: 'boughtSuccessComponent'
            });
        $urlRouterProvider.otherwise('/');
    })
    .name;

export default common;