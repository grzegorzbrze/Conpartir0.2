/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modLogin = angular.module('loginModule', ['ngRoute','ngCookies']);
        modLogin.controller('LoginController', ['$scope', '$http', '$routeParams', '$location','$cookies','auth',
        function($scope,$http,$routeParams,$location,$cookies,auth) {
            
            $scope.home = function () {
                
                
            };
            
            $scope.master = {};
            $scope.status = {};
            $scope.ifAlert = false;
            
            $scope.login = function(user) {
                $scope.master = user;
                $scope.master.use = "login";
                if (user.email == "", user.email == undefined || user.pass == "", user.pass == undefined ){
                    $scope.status = "Per favore, completa i campi per effettuare il login!";
                    //alert();
                    $scope.ifAlert = true;
                }
                else $scope.servletCall();
                
            };      
            
            $scope.FBLogin = function () {
                 $location.path("/FBLogin");
            };
              
            $scope.register = function (user) {
                $scope.master = user;
                $scope.master.use = "registration";
                var flag;
                flag = false; 
                if (user.pass == undefined) { 
                    $scope.status = "Inserisci una password";                    
                    $scope.ifAlert = true;
                    flag = true; 
                } else {
                    if (user.pass !== user.passRe) { 
                        $scope.status = "Le password inserite sono diverse";                    
                        $scope.ifAlert = true;
                        flag = true; 
                } 
                    
                }    
                                
                          
                if (user.email == undefined ) { 
                    $scope.status ="Prego, inserisci un'email valida";                    
                    $scope.ifAlert = true;
                    flag = true; 
                }           
                if (user.name === undefined || user.surname === undefined) { 
                    $scope.status ="Prego, inserisci il tuo nome!";                    
                    $scope.ifAlert = true;
                    flag = true; 
                }
                if (user.age === undefined || user.gender === undefined) { 
                    $scope.status ="Per favore, completa tutti i campi.";                    
                    $scope.ifAlert = true;
                    flag = true; 
                }
                
                if (flag === false) $scope.servletCall();  
            };  
            
                  
            // example of checking whether cookie is enabled
            var checkCookieEnabled = function () {
                if (navigator.cookieEnabled) {
                    alert("yes cookies");
                } else {
                    alert("no cookies");
                }
            };
            
            $scope.servletCall = function (){
                
                $scope.ifAlert = false;
                $http({
                    method: 'POST',
                    url: 'Registration',
                    headers: {'Content-Type': 'application/json'},
                    data:  $scope.master
                })
                        .success( function (data, status, header) {
                            //checkCookieEnabled();
                            
                            console.log("doc method for reading cookie " + document.cookie);
                           
                            var ckValue = $cookies.get('conpCookie');
                            auth.saveCookie('conpCookie',ckValue);
                            //sessionStorage.setItem('conpCookie', ckValue);
                            //console.log("session storage saved " + sessionStorage.getItem('conpCookie'));
                        })
                        
                        .then(function successCallback(data, status, header) {  
                    // this callback will be called asynchronously
                    // when the response is available
                    $scope.status=data.data;
                    console.log(data);
                    console.log($scope.status);
                    var flag = $scope.status.charAt(1);
                    if (flag == '1' || flag == '2' || flag == '3') $scope.ifAlert = true;
                    else { 
                        $location.path('/account');
                        $location.search('email',$scope.master.email);
                    }
                    //controllo del servizio auth
                    //console.log(auth.isAuth());
                    
                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                   // or server returns response with an error status.
                      });
                  };
            
     
        }]);

})();
