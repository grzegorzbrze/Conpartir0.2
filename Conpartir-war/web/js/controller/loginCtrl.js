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
            $scope.ifSuccess = false;
            $scope.isAuthorized = false;
            $scope.ifExternalLogin = false;
            
            //inizializzazione del servizio OAuth
            OAuth.initialize('jwDEjpUY8CHTgtz-Mx5DbPCdZSY');
            OAuth.create('twitter');
            
            //metodo che controlla se l'utente è autenticato
            //interrogando auth
            $scope.checkAuth = function () {
                
                auth.checkAuth().then(function (promise) {
//                    if (promise.status==200) {
////                        var cookie = sessionStorage.getItem('conpCookie');
////                        if(cookie) {   $scope.isAuthorized = true;
////                        };   
//                    }
                   if (promise.status === 200 )  $scope.isAuthorized = true;
                    else $scope.isAuthorized = false;                    
                });
    
            };
            
            $scope.login = function (user) {
                var prova = auth.getExternalLoginData();
                if (!jQuery.isEmptyObject(prova)) {
                    if (prova.use === "gmail" || prova.use === "twitter") {
                        $scope.master = prova;

                        if (!jQuery.isEmptyObject(user)) {
                            if (user.email !== prova.email && user.email !== "undefined") {
                                alert("Attenzione, stai facendo l'account con i dati di alcuni servizi esterni (gmail, twitter)");
                                user = {};
                            }
                        }                      
                        
                        $scope.servletCall();
                        return;
                    }
                }



                $scope.master = user;
                $scope.master.use = "login";
                if (user.email === "", user.email === undefined || user.pass === "", user.pass === undefined) {
                    $scope.status = "Per favore, completa i campi per effettuare il login!";
                    $scope.ifAlert = true;
                }
                else
                    $scope.servletCall();
            };

           
            //login tramite twitter, parte quando l'utente clicca sul relativo bottone
            //e usa il servizio Oauth.io
            $scope.onTwitterSignIn = function() {             
                OAuth.popup('twitter').done(function (result) {
                    //console.log(result);
                    // do some stuff with result  
                    result.me().done(function (data) {
                        // do something with `data`, e.g. print data.name
                        console.log(data);

                        auth.isTwitterThere(data.alias).then(function (promise) {
                            var accountEmail = auth.getTwitterValue().return;
                            if (jQuery.isEmptyObject(accountEmail)) {
                                alert("L'account di twitter da te specificato non è associato ad alcun account Conpartir");
                            }
                            else {
                                $scope.master.email = accountEmail;
                                $scope.master.use = "twitter";
                                auth.setExternalLoginData($scope.master);
                                $scope.ifExternalLogin = true;
                            }
                        });
                    });
                });

 
            };
            
            //login tramite google, parte quando l'utente clicca sul relativo bottone
            //e usa il servizio Oauth.io
            $scope.onGoogleSignIn = function() {             
                OAuth.popup('google').done(function (result) {
                    console.log(result);
                    result.me().done(function (data) {
                        // do something with `data`, e.g. print data.name
                        console.log(data);

                        auth.isGmailThere(data.email).then(function (promise) {
                            var accountEmail = auth.getGmailValue().return;
                            if (jQuery.isEmptyObject(accountEmail)) {
                                alert("L'account di google da te specificato non è associato ad alcun account Conpartir");
                            }
                            else {
                                $scope.master.email = accountEmail;
                                $scope.master.use = "gmail";
                                auth.setExternalLoginData($scope.master);
                                $scope.ifExternalLogin = true;
                            }

                        });
                    });
                });

 
            };
            
            //Funzione handler che "chiama" la servlet registration per il login
            //in realtà si interfaccia con auth
            $scope.servletCall = function (){                
                $scope.ifAlert = false;
                auth.doLogin($scope.master).then(function (data, status, headers, config) {                    
                    // this callback will be called asynchronously
                    // when the response is available
                    $scope.status=data.data;
                    console.log(status);
                    var flag = data.data.charAt(1);
                    if (flag === '1' || flag === '2' || flag === '3') $scope.ifAlert = true;
                    else {
                        auth.checkAuth().then(function (promise) {                             
                            if (promise.status === 200 )  $scope.isAuthorized = true;
                            else {
                                $scope.isAuthorized = false;
                                signOut();
                                window.signOut();
                                return;
                            }                                   
                            sessionStorage.setItem("email",$scope.master.email);
                            
                            if ($location.search().from === "detail") {
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
           
            //Registrazione di un utente
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
                if (user.email === undefined ) { 
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
                        $scope.ifSuccess = true;
                        $location.path('/');  
                   }
               });
                    
                    
                }
            }; 
            
            //Logout
            $scope.logout = function () {
                
                auth.doLogout();
                $scope.isAuthorized= false;                
                $location.path("/");
                $location.url($location.path());    
                window.signOut;
                
                var auth2 = gapi.auth2.getAuthInstance();
                  auth2.signOut().then(function () {
                      console.log('User signed out.');
                  });
            };
            
            //Versione precedente del login di google
            function onSignIn(googleUser) {
                var profile = googleUser.getBasicProfile();

                auth.isGmailOn(profile.getEmail()).then(function (promise) {
                    if (promise.status === 200) {
                        var result = auth.getGmailValue();
                        if (result.return === "true") {
                            //l'utente ha abilitato il login via gmail
                            $scope.master.email = profile.getEmail();
                            $scope.master.use = "gmail";
                            //console.log('check delay');
                            //console.log($scope.master);
                            auth.setExternalLoginData($scope.master);
                        }
                        else {
                            // l'utente non ha abilitato il login via gmail      
                            alert("devi prima entrare con il tuo account Conpartir e abilitare il login via gmail");
                        }
                    }


                });
            };            
            
            //Versione precedente del logout di google
            function signOut() {
                var auth2 = gapi.auth2.getAuthInstance();
                auth2.signOut().then(function () {
                    console.log('User signed out.');
                });
            };
            
            window.onSignIn = onSignIn;        
            window.signOut = signOut;  
    
    }]);
   
})();