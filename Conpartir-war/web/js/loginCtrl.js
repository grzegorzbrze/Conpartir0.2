/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modLogin = angular.module('loginModule', ['ngRoute','ngCookies']);
        modLogin.controller('LoginController', ['$scope', '$http','$route' ,'$routeParams','$location','$cookies','$timeout','$window','shared','auth',
        function($scope,$http,$route,$routeParams,$location,$cookies,$timeout,$window,shared,auth) {
            
            $scope.master = {};
            $scope.status = {};
            $scope.ifAlert = false;
            $scope.isAuthorized = false;
            
            //Da rivedere ...
            $scope.checkAuth = function () {
                
                auth.checkAuth().then(function (promise) {
//                    if (promise.status==200) {
////                        var cookie = sessionStorage.getItem('conpCookie');
////                        if(cookie) {   $scope.isAuthorized = true;
////                        };   
//                    }
                   if (promise.status == 200 )  $scope.isAuthorized = true;
                    else $scope.isAuthorized = false;                    
                });
    
            };
            
            //non aggiorna la pagina
            $scope.login = function(user) {
                $scope.master = user;
                $scope.master.use = "login";
                if (user.email == "", user.email == undefined || user.pass == "", user.pass == undefined ){
                    $scope.status = "Per favore, completa i campi per effettuare il login!";                    
                    $scope.ifAlert = true;
                }
                else $scope.servletCall();
                 
               //window.location.reload();
            };      
            
            $scope.FBLogin = function () {
                 $location.path("/FBLogin");
            };
            
            $scope.servletCall = function (){                
                $scope.ifAlert = false;
                auth.doLogin($scope.master).then(function (data, status, headers, config) {                    
                    // this callback will be called asynchronously
                    // when the response is available
                    $scope.status=data.data;
                    var flag = data.data.charAt(1);
                    if (flag == '1' || flag == '2' || flag == '3') $scope.ifAlert = true;
                    else {
                        auth.checkAuth().then(function (promise) {  
                           
                            if (promise.status == 200 )  $scope.isAuthorized = true;
                            else $scope.isAuthorized = false;                              
                            //console.log("2" + $scope.isAuthorized);
                            
                            sessionStorage.setItem("email",$scope.master.email);
                            
                            if ($location.search().from == "detail") {
                                $window.history.back();
                                $route.reload();
                                return;                            
                            };
                            
                            $location.path('/account');                             
                            $scope.$on('$locationChangeSuccess', function() {
                               window.location.reload();
                           }); 
                       });
                   }
               });
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
                if (flag === false) {        
                    
                    $scope.ifAlert = false;
                auth.doRegister($scope.master).then(function (data, status, headers, config) {                    
                    // this callback will be called asynchronously
                    // when the response is available
                    $scope.status=data.data;
                    var flag = data.data.charAt(1);
                    if (flag == '1' || flag == '2' || flag == '3') $scope.ifAlert = true;
                    else {
                        
                            sessionStorage.setItem("email",$scope.master.email);
                            
                            $location.path('/');         
                       
                   }
               });
                    
                    
                }
            }; 
            
            $scope.logout = function () {
                auth.doLogout();
                $scope.isAuthorized= false;
                $location.path("/");
                $location.url($location.path());
//                $scope.$on('$locationChangeSuccess', function() {
//                                console.log("here");
//                                window.location.reload();
//                       });             
                };
            
     
        }]);
   
})();