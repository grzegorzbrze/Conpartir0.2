(function () {
  'use strict';

var modService = angular.module('serviceModule', ['ngRoute']);

    //Factory per dati condivisi
   modService.factory('shared', 
   function () {
        var data;
        var obj = {};

        return {
            getData: function () {
                 console.log(obj + ' was returned as data');
                return obj;
            },
            setData: function (name,surname,email,carModel) {
                // console.log('setting ' + data + ' as data');
                obj.name = name;
                obj.surname = surname;
                obj.email = email;
                obj.carModel = carModel;
            }



        };
    });
       
    
    

})();
