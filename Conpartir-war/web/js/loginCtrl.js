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
            
            $scope.login = function(user) {
                
                //console.log($scope.master);
                var prova = auth.getGmailData();
                //console.log(prova);
                if (!jQuery.isEmptyObject(prova)) {
                    if(prova.use==="gmail") {
                        $scope.master = prova;
                        
                        if (!jQuery.isEmptyObject(user)) {
                            if(user.email !== prova.email && user.email !== "undefined") { 
                                alert("Attenzione, l'email che hai indicato è diversa da quella usata per gmail. \n\
                                       Continuando accederai all'account relativo a gmail.");
                                user = {};
                                
                            } 
                        }
                        $scope.servletCall();
                        return;
                    }
                }
                
               
                
                $scope.master = user;
                $scope.master.use = "login";
                if (user.email === "", user.email === undefined || user.pass === "", user.pass === undefined ){
                    $scope.status = "Per favore, completa i campi per effettuare il login!";                    
                    $scope.ifAlert = true;
                }
                else $scope.servletCall();
            };   
            
             
            function onSignIn(googleUser) {
                var profile = googleUser.getBasicProfile();
               /* console.log('ID: ' + profile.getId());
                console.log('Name: ' + profile.getGivenName());
                console.log('Surname: ' + profile.getFamilyName());
                console.log('Email: ' + profile.getEmail());
                
                console.log(profile); */                
                auth.isGmailOn(profile.getEmail()).then(function (promise) {
                     if (promise.status === 200 )  {
                        var result = auth.getGmailValue();
                        if (result.return === "true") {
                             //l'utente ha abilitato il login via gmail
                             $scope.master.email = profile.getEmail();
                             $scope.master.use = "gmail";                   
                             
                             
                             console.log('check delay');
                            console.log($scope.master);
                            auth.setGmailData($scope.master);
                        }
                        else {
                            // l'utente non ha abilitato il login via gmail      
                            alert("devi prima entrare con il tuo account Conpartir e abilitare il login via gmail");
                        }
                     }
                
                
                });
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
                            else {
                                $scope.isAuthorized = false;
                                signOut();
                                window.signOut();
                                return;
                            }                                   
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
                        $scope.ifSuccess = true;
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
                window.signOut();
                
                var auth2 = gapi.auth2.getAuthInstance();
                  auth2.signOut().then(function () {
                      console.log('User signed out.');
                  });
            };
            
            function signOut() {
                var auth2 = gapi.auth2.getAuthInstance();
                auth2.signOut().then(function () {
                    console.log('User signed out.');
                });
            };
            
            window.onSignIn = onSignIn;        
            window.signOut = signOut;  
            
            
            // FACEBOOK LOGIN
            
              // This is called with the results from from FB.getLoginStatus().
            function statusChangeCallback(response) {
                console.log('statusChangeCallback');
                console.log(response);
                // The response object is returned with a status field that lets the
                // app know the current login status of the person.
                // Full docs on the response object can be found in the documentation
                // for FB.getLoginStatus().
                 if (response.status === 'connected') {
                // Logged into your app and Facebook.
                testAPI();
                } else if (response.status === 'not_authorized') {
                // The person is logged into Facebook, but not your app.
                document.getElementById('status').innerHTML = 'Please log '+'into this app.';
            } else {
                // The person is not logged into Facebook, so we're not sure if
                // they are logged into this app or not.
                document.getElementById('status').innerHTML = 'Please log ' +'into Facebook.';
            }
        }

        // This function is called when someone finishes with the Login
        // Button.  See the onlogin handler attached to it in the sample
        // code below.
        function checkLoginState() {
            FB.getLoginStatus(function(response) {
                statusChangeCallback(response);
            });
        }
        window.fbAsyncInit = function() {
            FB.init({
                appId      : '{your-app-id}',
                cookie     : true,  // enable cookies to allow the server to access 
                        // the session
                xfbml      : true,  // parse social plugins on this page
                version    : 'v2.5' // use graph api version 2.5
            });

            // Now that we've initialized the JavaScript SDK, we call 
            // FB.getLoginStatus().  This function gets the state of the
            // person visiting this page and can return one of three states to
            // the callback you provide.  They can be:
            //
            // 1. Logged into your app ('connected')
            // 2. Logged into Facebook, but not your app ('not_authorized')
            // 3. Not logged into Facebook and can't tell if they are logged into
            //    your app or not.
            //
            // These three cases are handled in the callback function.

            FB.getLoginStatus(function(response) {
                statusChangeCallback(response);
            });
        };
        
        // Load the SDK asynchronously
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk.js";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));

        // Here we run a very simple test of the Graph API after login is
        // successful.  See statusChangeCallback() for when this call is made.
        function testAPI() {
            console.log('Welcome!  Fetching your information.... ');
            FB.api('/me', function(response) {
                console.log('Successful login for: ' + response.name);
                document.getElementById('status').innerHTML =
                        'Thanks for logging in, ' + response.name + '!';
            });
        }
    }]);
   
})();