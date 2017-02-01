'use strict';

export default class CarouselsController {

    /*ngInject*/
    constructor(api, $stateParams){
        this.api = api;
        this.params = $stateParams;
        this.init();
    }

    init() {
        this.max = 5;
        this.number = 0;
        this.last = false;
        this.carousels = [];
        this.getAllCarousels();
    }

    getAllCarousels(){

        let params = {
            max: this.max,
            page: this.number
        };

        if(this.query) {
            params.query = this.query;
        }

        this.api.carousels
            .get(params)
            .$promise
            .then(response => {
                this.last = response.last;
                this.carousels = this.carousels.concat(response.content);
            })
    }

    loadMoreCarousels() {
        this.number = this.number + 1;
        this.getAllCarousels();
    }

    deleteCarousel(carousel) {
        this.api.carousels
            .remove({id: carousel.id})
            .$promise
            .then(() => {
                this.init();
            })
    }

}