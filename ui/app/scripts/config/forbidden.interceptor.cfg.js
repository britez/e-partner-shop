'use strict';

/**
 * Created by julian on 22/08/16.
 */
function forbiddenInterceptor($q, $state) {
    return {
        request: function (config) {
            return config;
        },
        responseError: function (rejection) {
            // Catch `invalid_request` and `invalid_grant` errors and ensure that the `token` is removed.
            if (403 === rejection.status ){
                console.log("FORBIDDEN INTERCEPTOR");
            }

            return $q.reject(rejection);
        }
    };
}

export default forbiddenInterceptor;
