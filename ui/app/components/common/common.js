'use strict';
/**
 * Created by julian on 25/07/16.
 */

import angular from 'angular';

import { NavBarComponent } from './navBar/navBar.component';
import { FooterComponent } from './footer/footer.component';

const common = angular
    .module('app.common', [])
    .component('navBar', NavBarComponent)
    .component('footerCmp', FooterComponent)
    .name;

export default common;