(function () {
  'use strict';

  var modDetail = angular.module('detailModule', ['ngRoute']);
        modDetail.controller('DetailController', ['$scope', '$route', '$location', 'shared', 'auth',
        function($scope, $route, $location, shared, auth) {
           
            $scope.travel;
            $scope.detail;
            $scope.ifAlert = false;
            $scope.alert;
            
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
                //console.log($scope.travel);
                shared.getDriverFromTravel( driverIdParam).then(function(promise){
                    $scope.detail = shared.getData();
                });                
            };
            
            $scope.book = function () {
                var input = {};
                if (auth.isAuthenticated()==false) {
                     $scope.ifAlert = true;
                     $scope.alert = "Devi fare il login per prenotare un viaggio.";                    
                }
                else { 
                    input.travelId = $scope.travel.travel_id;
                    input.email = sessionStorage.getItem("email");
                    shared.bookTravel(input).then(function (promise) {
                        console.log(promise.status);
                        //status? 
                    });  
                }
               
            },

                        
             $scope.reload = function () {
                  $route.reload();
                 
              };
     
        }]);
    
    

})();
