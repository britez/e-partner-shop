'use strict';
/**
 * Created by julian on 25/07/16.
 */

import angular from 'angular';

import { HeaderComponent } from './header/header.cmp';
import { FooterComponent } from './footer/footer.component';

const common = angular
    .module('commons.module', [])
    .component('headerComponent', HeaderComponent)
    .component('footerCmp', FooterComponent)
    .name;

export default common;