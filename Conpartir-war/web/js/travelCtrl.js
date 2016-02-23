/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modTravel = angular.module('travelModule', ['ngRoute']);
        modTravel.controller('TravelController', ['$scope', '$http', '$soap',
        function($scope,$http,$soap) {
            
            $scope.SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient" ;
            
            $scope.query =  {};
            
            $scope.search = function(data) {
                $scope.query = data;
                //alert($scope.query.from + $scope.query.to);
                
                $scope.SOAPreq2();
            };
            
            $scope.SOAPreq = function(data) {
               
                $http({
                    method: 'POST',
                    url: $scope.SOAPbase + 'getTravels',
                    headers: {'Content-Type': 'application/json'},
                    data:  data
                }).success(function (data)
                {
                    $scope.status=data;
                    alert($scope.status);
                });           
                
            };
            
            $scope.response = {};
            
            $scope.SOAPreq2 = function () {
                var result;
                //$soap.post($scope.SOAPbase,"getTravels", {from: "Torino", to: "Milano"});
                
                /*result = */ $soap.post($scope.SOAPbase,"getClient", {email: "mario.rossi@gmail.com"}).then(
                        function(response){
                            $scope.response = response;
                            
                        });
                    
                
                alert($scope.response.name);
               // alert(result);
                return result;
            };
            
          
     
        }]);

})();
