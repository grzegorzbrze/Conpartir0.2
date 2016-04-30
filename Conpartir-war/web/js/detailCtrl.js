(function () {
  'use strict';

  var modDetail = angular.module('detailModule', ['ngRoute']);
        modDetail.controller('DetailController', ['$scope', '$http', '$route', '$routeParams','$location','$timeout' , 'shared',
        function($scope,$http, $route, $routeParams, $location, $timeout, shared) {
           
            $scope.travel;
            $scope.detail;
            
            $scope.getDay = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(0,splitter);                
            };
            
            $scope.getTime = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(splitter+1,splitter+6);                
            };
                        
            $scope.getInfo = function () {
                var driverIdParam = $location.search().number;
                $scope.travel = shared.getData();
                console.log($scope.travel);
                shared.getDriverFromTravel( driverIdParam).then(function(promise){
                    $scope.detail = shared.getData();
                });                
            };

                        
             $scope.reload = function () {
                  $route.reload();
                 
              };
     
        }]);
    
    

})();
