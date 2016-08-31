'use strict';
import angular from 'angular';
import { AdminComponent } from '../admin/components/admin.cmp';

const admin = angular
    .module('admin.module', [])
    .component('adminComponent', AdminComponent)
    .config(($stateProvider, $urlRouterProvider) => {
        $stateProvider
            .state('admin', {
                url: '/admin',
                component: 'adminComponent'
            });
    })
    .name;

export default admin;