'use strict';

let dashboardApiCfg = () => {

  let apiConfig = ['apiProvider', (apiProvider) => {

    apiProvider.endpoint('categories')
      .route('/api/categories/:id');

    apiProvider.endpoint('products')
        .route('/api/products/:id');

    apiProvider.endpoint('productImages')
        .route('/api/products/:id/images');

  }];

  return apiConfig;
};

export default dashboardApiCfg;
