'use strict';

export default class CarouselsController {

    /*ngInject*/
    constructor(api, $stateParams){
        this.api = api;
        this.params = $stateParams;
        this.getAllCarousels();
    }

    getAllCarousels(){
        this.api.carousels
            .get()
            .$promise
            .then((response) => {
                this.carousels = response.content;
            })
    }

    deleteCarousel(carousel) {
        this.api.carousels
            .remove({id: carousel.id})
            .$promise
            .then(() => {
                this.getAllCarousels();
            })
    }

}