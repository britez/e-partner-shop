'use strict';
import angular from 'angular';
import { AdminComponent } from '../admin/components/admin.cmp';
import { CategoryComponent } from '../admin/components/categories/category.cmp';
import { CategoryDetailComponent } from '../admin/components/categories/category.detail.cmp';

const admin = angular
    .module('admin.module', [])
    .component('adminComponent', AdminComponent)
    .component('categoryComponent', CategoryComponent)
    .component('categoryDetailComponent', CategoryDetailComponent)
    .config(($stateProvider) => {
        $stateProvider
            .state('admin', {
                url: '/admin',
                component: 'adminComponent'
            })
            .state('categories', {
                url: '/admin/categories',
                component: 'categoryComponent'
            })
            .state('categories-detail', {
                url: '/admin/categories/:id',
                component: 'categoryDetailComponent'
            })
    })
    .name;

export default admin;