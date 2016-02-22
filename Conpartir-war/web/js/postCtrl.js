/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modPost = angular.module('postModule', ['ngRoute']);
        modPost.controller('PostController', ['$scope', '$http',
        function($scope,$http) {
            
            $scope.query =  {};
            
            $scope.search = function(data) {
                $scope.query = data;
                alert($scope.query.from + $scope.query.to);
                
                
            };
            
          
     
        }]);

})();
