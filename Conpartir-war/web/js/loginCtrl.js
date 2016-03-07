/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modLogin = angular.module('loginModule', ['ngRoute']);
        modLogin.controller('LoginController', ['$scope', '$http', '$routeParams', '$location', '$window',
        function($scope,$http,$routeParams,$location,$window) {
            
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
            
      /*      function parseCookies (request) {
                var list = {},
                rc = request.headers.cookie;
                console.log('req head cookie' + request.headers);
                console.log('req head cookie' + rc);
                rc && rc.split(';').forEach(function( cookie ) {
                    var parts = cookie.split('=');
                    list[parts.shift().trim()] = decodeURI(parts.join('='));
                });
                return list;
            } */
            
            $scope.servletCall = function (){
                
                $scope.ifAlert = false;
                $http({
                    method: 'POST',
                    url: 'Registration',
                    headers: {'Content-Type': 'application/json'},
                    data:  $scope.master
                }).then(function successCallback(response) {  
                    // this callback will be called asynchronously
                    // when the response is available
                    $scope.status=response.data;
                    console.log(response);
                    console.log($scope.status);
                    var flag = $scope.status.charAt(1);
                    if (flag == '1' || flag == '2' || flag == '3') $scope.ifAlert = true;
                    
                    var cookieName = "somecookie";
                    var cookies = []; 
                    cookies = parseCookies(response);
                    
                    console.log('cookies' + cookies);
                    //console.log(cookies[0].name + cookies[0].value);
                   /* if (cookies != null)
                    {
                        for(item in cookies)
                        {
                            Cookie cookie = cookies[i];
                            if (cookieName.equals(cookie.getName()))
                            {
                                doSomethingWith(cookie.getValue());
                            }
                            else  {     }
                        }
                    }
                   */
                    
                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                   // or server returns response with an error status.
                      });
                  };
            //$window.sessionStorage.token = data.token;
          
     
        }]);

})();
