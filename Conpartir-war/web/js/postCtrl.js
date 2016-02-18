/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function () {
  'use strict';

  var modPost = angular.module('postModule', ['ngRoute']);
        modPost.controller('PostController', ['$scope', '$routeParams', '$location',
        function($scope,$routeParams,$location) {
      
      //Change page url, adding a given parameter. 
      //Look to .config in homeCtrl for info on route params settings. e.g. "/postview/:Id"
      $scope.go = function (dest,param) {
          var newDest = dest + '/' + param; 
          $location.path(newDest);
      };
      
      //Returns Url Param Id from service  $routeParams
      $scope.getParamId = function () {
          return $routeParams.Id;
      };
      
      //Array of JSON objects to show "posts" on the page. TODO: import posts from DB       
      $scope.samplePosts =[ 
        { name: "Matteo Rossi",
          car: "Fiat Punto 2014",
          age: 25 ,
          from: "Torino" ,
          to: "Milano"          
      },
      { name: "Marco Verdi",
          car: "Citroen C4 2014",
          age: 33 ,
          from: "Torino" ,
          to: "Milano"          
      },
      { name: "Monica Marconi",
          car: "Citroen Berlingo 2004",
          age: 20 ,
          from: "Torino" ,
          to: "Padova"          
      }, 
      { name: "Michele Ventura",
          car: "Renault kangoo 2004",
          age: 27 ,
          from: "Torino" ,
          to: "Palermo"          
      }  ];
}]);

})();