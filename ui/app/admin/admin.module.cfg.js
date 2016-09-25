'use strict';
import angular from 'angular';
import { AdminComponent } from '../admin/components/admin.cmp';
import { CategoryComponent } from '../admin/components/categories/category.cmp';
import { CategoryDetailComponent } from '../admin/components/categories/category.detail.cmp';
import { ProductsComponent } from '../admin/components/products/products.cmp';
import { ProductComponent } from '../admin/components/products/product.cmp';
import { HighlightComponent } from '../admin/components/highlight/highlight.cmp';

const admin = angular
    .module('admin.module', [])
    .component('adminComponent', AdminComponent)
    .component('categoryComponent', CategoryComponent)
    .component('categoryDetailComponent', CategoryDetailComponent)
    .component('productsComponent', ProductsComponent)
    .component('productComponent', ProductComponent)
    .component('highlightComponent', HighlightComponent)
    .config(($stateProvider) => {
        $stateProvider
            .state('admin', {
                url: '/admin',
                component: 'adminComponent'
            })
            .state('categories', {
                url: '/admin/categories?created',
                component: 'categoryComponent'
            })
            .state('categories-detail', {
                url: '/admin/categories/:id',
                component: 'categoryDetailComponent'
            })
            .state('products', {
                url: '/admin/products?created',
                component: 'productsComponent'
            })
            .state('products-detail', {
                url: '/admin/products/:id',
                component: 'productComponent'
            })
            .state('highlights', {
                url: '/admin/highlight',
                component: 'highlightComponent'
            })
    })
    .name;

export default admin;