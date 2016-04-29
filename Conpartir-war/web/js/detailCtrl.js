(function () {
  'use strict';

  var modDetail = angular.module('detailModule', ['ngRoute']);
        modDetail.controller('DetailController', ['$scope', '$http', '$route', '$routeParams','$location','$timeout' , 'shared',
        function($scope,$http, $route, $routeParams, $location, $timeout, shared) {
           
           
            $scope.dettagli;
                        
            $scope.getInfo = function () {
                var driverIdParam = $location.search().number;               
                shared.getDriverFromTravel( driverIdParam).then(function(promise){
                    $scope.dettagli = shared.getData();
                });                
            };

                        
             $scope.reload = function () {
                  $route.reload();
                 
              };
     
        }]);
    
    

})();
