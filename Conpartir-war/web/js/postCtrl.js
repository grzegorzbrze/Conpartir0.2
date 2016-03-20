(function () {
  'use strict';

  var modPost = angular.module('postModule', ['ngRoute']);
  modPost.controller('PostController', ['$scope', '$route', '$routeParams','$location','$timeout' ,'login', 'shared',
      function($scope,$route, $routeParams, $location, $timeout,login, shared) {
          
          $scope.notLogged;
          
          
          $scope.check = function () {
              if(login.getAuth() == false) $scope.notLogged=true;
              console.log("risultato =" +$scope.notLogged);
             
              
          };
                
        }]);
    
    

})();
