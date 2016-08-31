'use strict';

let dashboardApiCfg = () => {

  let apiConfig = ['apiProvider', (apiProvider) => {

    apiProvider.endpoint('categories')
      .route('/api/categories/:id');

  }];

  return apiConfig;
};

export default dashboardApiCfg;
