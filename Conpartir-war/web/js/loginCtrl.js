/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modLogin = angular.module('loginModule', ['ngRoute']);
        modLogin.controller('LoginController', ['$scope', '$http', '$routeParams', '$location',
        function($scope,$http,$routeParams,$location) {
            
            $scope.home = function () {
                
                
            };
            
            $scope.master = {};
            $scope.status = {};
            
            $scope.login = function(user) {
                $scope.master = user;
                $scope.master.use = "login";
                if (user.email == "", user.email == undefined || user.pass == "", user.pass == undefined ) alert("Per favore, completa i campi per effettuare il login!");
                else $scope.servletCall($scope.master);
            };      
              
            $scope.register = function (user) {
                $scope.master = user;
                $scope.master.use = "registration";
                var flag;
                flag = false;  
                
                if (user.pass !== user.passRe) { 
                    alert("Le password inserite sono diverse");
                    flag = true; 
                }           
                if (user.email === null) { 
                    alert("Prego, inserisci un'email valida");
                    flag = true; 
                }           
                if (user.name === null || user.surname === null) { 
                    alert("Prego, inserisci il tuo nome!");
                    flag = true; 
                }
                if (user.age === null || user.gender === null) { 
                    alert("Per favore, completa tutti i campi.");
                    flag = true; 
                }
                
                if (flag === false) $scope.servletCall($scope.master);  
            };  
            
            $scope.servletCall = function (data){ 
                $http({
                    method: 'POST',
                    url: 'Registration',
                    headers: {'Content-Type': 'application/json'},
                    data:  $scope.master
                }).success(function (data)
                {
                    $scope.status=data;
                    alert($scope.status);
                });         
            };
        
     
        }]);

})();
