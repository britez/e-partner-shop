'use strict';

let dashboardApiCfg = () => {

  let apiConfig = ['apiProvider', (apiProvider) => {

    apiProvider.endpoint('categories')
      .route('/api/categories/:id');

    apiProvider.endpoint('categoryProducts')
      .route('/api/categories/:id/products');

    apiProvider.endpoint('products')
      .route('/api/products/:id');

    apiProvider.endpoint('productImages')
      .route('/api/products/:id/images');

    apiProvider.endpoint('tags')
      .route('/api/tags/:id');

    apiProvider.endpoint('tagsProducts')
      .route('/api/tags/:id/product/:productId')
  }];

  return apiConfig;
};

export default dashboardApiCfg;
