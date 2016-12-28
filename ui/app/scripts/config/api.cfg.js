'use strict';

let dashboardApiCfg = () => {

  let apiConfig = ['apiProvider', (apiProvider) => {

    apiProvider.endpoint('categories')
      .route('/api/admin/me/categories/:id');

    apiProvider.endpoint('products')
      .route('/api/admin/me/products/:id');

    apiProvider.endpoint('publicProducts')
        .route('/api/products/:id');

    apiProvider.endpoint('productImages')
      .route('/api/admin/me/products/:id/images');

    apiProvider.endpoint('carousels')
      .route('/api/admin/me/carousels/:id');

    apiProvider.endpoint('publicCarousels')
      .route('api/carousels/:id');

    apiProvider.endpoint('categoryProducts')
      .route('/api/admin/me/categories/:id/products');

    apiProvider.endpoint('tags')
        .route('/api/admin/me/tags/:id');

    apiProvider.endpoint('productImports')
      .route('api/admin/me/product-imports');

    apiProvider.endpoint('productImportsConfig')
        .route('api/admin/me/product-imports/current/config');

    apiProvider.endpoint('payments')
        .route('api/admin/me/payments/:id');

    apiProvider.endpoint('publicPayments')
        .route('api/payments/:id');

    apiProvider.endpoint('mercadoPago')
        .route('api/me/mercado-pago');

    apiProvider.endpoint('buys')
        .route('api/me/buys/:id');

    apiProvider.endpoint('user')
      .route('/oauth/user');

    apiProvider.endpoint('settings')
        .route('/oauth/settings');
  }];

  return apiConfig;
};

export default dashboardApiCfg;
