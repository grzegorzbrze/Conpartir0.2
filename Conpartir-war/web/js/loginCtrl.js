/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modLogin = angular.module('loginModule', ['ngRoute','ngCookies']);
        modLogin.controller('LoginController', ['$scope', '$http','$route' ,'$routeParams','$location','$cookies','$timeout','$window','shared','login','auth',
        function($scope,$http,$route,$routeParams,$location,$cookies,$timeout,$window,shared,login,auth) {
            
            $scope.master = {};
            $scope.status = {};
            $scope.ifAlert = false;
            $scope.isAuthorized = false;
            
            //Da rivedere ...
            $scope.checkAuth = function () {
                
                if (auth.isAuthenticated()==false) {
                    $scope.isAuthorized = false;
                }
                else $scope.isAuthorized = true;
                
//                login.getAuth().then(function (data) { 
//                    if (data.status==200)  {                        
//                        $scope.isAuthorized = true; 
//                    }
//                    else {                        
//                        $scope.isAuthorized = false;
//                    };
//                });
    
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
                 console.log("1" + $scope.isAuthorized);
                
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
                    var flag = data.data.charAt(1);
                    if (flag == '1' || flag == '2' || flag == '3') $scope.ifAlert = true;
                    else {
                        
                        $scope.isAuthorized = login.getData(); 
                       // console.log("2" + $scope.isAuthorized);
                        sessionStorage.setItem("email",$scope.master.email);
                        
                        if ($location.search().from == "detail") {
                          $window.history.back();
                            $route.reload();
                            return;
                            
                        };  
                        $location.path('/account'); 
                        //var x =$route.current.templateUrl + $location.url();
                        //console.log(x);
                        $route.reload();
                      //$window.location.replace(x);
                        //$window.location.search('email',$scope.master.email);
                      
                    }
                 });
            };
            
            //Non cancella il cookie
            $scope.logout = function () {
                login.doLogout();
                $scope.isAuthorized= false;
                $location.path("/"); 
                $location.url($location.path());
              
//                $timeout(function() {
//                   // $route.reload();
//                    
//                        console.log("reloaded");
//                    },20 ); 
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
                            obj = true;
                        })
                                .error(function (data, status, headers, config) {
                                      obj = false;
                                 return {"status": false};
                     });
                     return promise;
                
            },   
            
           doLogout: function () {
                auth.delCookie('conpCookie');
                sessionStorage.clear();                              
            },
            
            getAuth: function() {
                var isAuth;
                cookieAuth = auth.isAuthenticated();
                
                //qui ci va una promise ....
               var promise;
                promise = auth.checkAuth(cookieAuth);
                return promise;                
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
