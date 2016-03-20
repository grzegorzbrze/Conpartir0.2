/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modLogin = angular.module('loginModule', ['ngRoute','ngCookies']);
        modLogin.controller('LoginController', ['$scope', '$http', '$routeParams', '$location','$cookies','$timeout','$window','shared','login','auth',
        function($scope,$http,$routeParams,$location,$cookies,$timeout,$window,shared,login,auth) {
            
            $scope.home = function () { 
            };            
            $scope.master = {};
            $scope.status = {};
            $scope.ifAlert = false;
            $scope.loginShow = true;
            $scope.isAuthorized = false;
      
      //Non funziona, il problema sembra essere auth.Autenticated
      //TODO: CHECK
      $scope.checkAuth = function () {
          var flag;
          $timeout (function() {} , 50 );
              login.getAuth().then(
                      function successCallback(data) {  
                    if (data.status==200) 
                    {
                        flag = true;                     
                       //console.log("flag vale" + flag);
                      
                    }; 
                    if (flag == true){ 
                           $scope.isAuthorized = true; 
                           $scope.loginShow = false;
                       }
                       else {
                           $scope.loginShow = true;
                           $scope.isAuthorized = false;
                       }           
                });
            };
            
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
            
            $scope.servletCall = function (){
                
                $scope.ifAlert = false;
                login.doLogin($scope.master).then(function (data, status, headers, config) {
                    
                    // this callback will be called asynchronously
                    // when the response is available
                    $scope.status=data.data;
                    console.log(data.status);
                    console.log($scope.status);
                    var flag = data.data.charAt(1);
                    if (flag == '1' || flag == '2' || flag == '3') $scope.ifAlert = true;
                    else { 
                        $location.path('/account');
                        $location.search('email',$scope.master.email);
                        sessionStorage.setItem("email",$scope.master.email);                        
                        $scope.checkAuth();
                        $timeout( 
                                function() {
                                    $window.location.reload();
                                },
                                10
                                        ); 
                        
                    }
                    
                     
                 });
                
            };
            
            $scope.logout = function () {
                login.logout();
                $location.path("/"); 
                $scope.loginShow= true;
                $scope.isAuthorized= false;
                //$scope.checkAuth(); 
                $timeout( 
                                function() {
                                    $window.location.reload();
                                    console.log("reloaded");
                                    
                                },
                                10
                                        ); 
              
      };
            
     
        }]);
    
    modLogin.factory('login', [ '$http','$location', '$cookies' ,'auth',
   function ($http, $location, $cookies, auth) {
        var data;
        var obj = {};
        var cookieAuth;
        
        return {
            
            doLogin: function (input) {
                var promise;
               promise = $http({
                    method: 'POST',
                    url: 'Registration',
                    headers: {'Content-Type': 'application/json'},
                    data:  input
                })
                        .success( function (data, status, header) {
                            //checkCookieEnabled();
                            
                            console.log("doc method for reading cookie " + document.cookie);
                           
                            var ckValue = $cookies.get('conpCookie');
                            auth.saveCookie('conpCookie',ckValue);
                            //sessionStorage.setItem('conpCookie', ckValue);
                            //console.log("session storage saved " + sessionStorage.getItem('conpCookie'));
                        })
                                .error(function (data, status, headers, config) {
                                 return {"status": false};
                     });
                        
                       
                      return promise;
                
            },
            
            getAuth: function() {
                var isAuth;
                cookieAuth = auth.isAuthenticated();
                
                //qui ci va una promise ....
               var promise;
                promise = auth.checkAuth(cookieAuth);
                return promise;
                
            },
            
            logout: function () {
                auth.delCookie('conpCookie');
                sessionStorage.clear();
                              
            },
            
            getData: function () {
                // console.log(obj + ' was returned as data');                
                return obj;
            },            
            
            setData: function (item) {
                // console.log('setting ' + data + ' as data');
                obj = item;
            }



        };
    }]);
       
    

})();
