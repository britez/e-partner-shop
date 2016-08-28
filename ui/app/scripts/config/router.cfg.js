'use strict';

let mediaroutes = () => {

  let routerConfig = [
    '$stateProvider',
    '$urlRouterProvider',
    ($stateProvider, $urlRouterProvider) => {
        $urlRouterProvider.otherwise(($injector)=> {
            $injector.get('$state').go('home');
        });

      $stateProvider
        .state('home', {
            url: '/',
            component: 'principal'
        })

    }];

  return routerConfig;

};

export default mediaroutes;
