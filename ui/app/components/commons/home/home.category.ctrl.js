'use strict';

export default class HomeCategoryCtrl {

    /*@ngInject*/
    constructor() {
        this.init();

    }

    init() {
        this.entity = {};
        this.entity.destacados = [{
            
            title: 'SMART TV Led 32',
            price: '4000',
            image: 'http://mla-s1-p.mlstatic.com/785221-MLA20748837164_062016-O.jpg'
        },
        {
            title: 'Baby Call',
            price: '299',
            image: 'http://mla-s2-p.mlstatic.com/313121-MLA20717004267_052016-O.jpg'
        },
        {
            title: 'Masajeador',
            price: '560',
            image: 'http://mla-s2-p.mlstatic.com/754101-MLA20278315510_042015-O.jpg'
        },
        {
            title: 'Otro TV',
            price: '40077',
            image: 'http://mla-s1-p.mlstatic.com/504711-MLA20609465620_022016-O.jpg'
        }
        ];
      
    }
}