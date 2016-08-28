'use strict';

let dashboardApiCfg = () => {

  let apiConfig = ['apiProvider', (apiProvider) => {

    apiProvider.setBaseRoute('');

    apiProvider.endpoint('helloWorld')
      .route('/giu-api/hello');
  }];

  return apiConfig;
};

export default dashboardApiCfg;
