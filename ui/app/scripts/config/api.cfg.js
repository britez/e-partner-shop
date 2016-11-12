'use strict';

let dashboardApiCfg = () => {

  let apiConfig = ['apiProvider', (apiProvider) => {

    apiProvider.endpoint('categories')
      .route('/api/admin/me/categories/:id');

    apiProvider.endpoint('products')
      .route('/api/admin/me/products/:id');

    apiProvider.endpoint('productImages')
      .route('/api/admin/me/products/:id/images');

    apiProvider.endpoint('carousels')
      .route('/api/admin/me/carousels/:id');

    apiProvider.endpoint('publicCarousels')
      .route('api/admin/me/carousels/:id');

    apiProvider.endpoint('categoryProducts')
      .route('/api/admin/me/categories/:id/products');

    apiProvider.endpoint('tags')
      .route('/api/admin/me/tags/:id');

    apiProvider.endpoint('meli')
      .route('/sites/MLA/search');
  }];

  return apiConfig;
};

export default dashboardApiCfg;
