/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var myapp = angular.module('myApp', 
['ngRoute', 'ngTouch' , 'ngAnimate', 'ngCookies',
    'loginModule', 'sliderModule', 'travelModule', 'dateModule', 'detailModule', 'postModule' ,'accountModule' ,'historyModule', 'serviceModule', 'authModule']);
  
  myapp.config(function($routeProvider){
  $routeProvider
          .when( "/", 
  {   templateUrl: "pages/home.html", controller: "MainController" })   
          .when ( "/home",  
  {   templateUrl: "pages/home.html", controller: "MainController" })  
          .when ("#/carousel-example-generic",
  {   templateUrl: "pages/home.html", controller: "MainController" })    
 //         .when("/about",
 // {   templateUrl: "pages/old/about.html", controller: "MainController" })
          .when("/account",
  {   templateUrl: "pages/account.html", controller: "AccountController" })
          .when("/signup",
  {   templateUrl: "pages/signup.html", controller: "LoginController" })
          .when("/login",
  {   templateUrl: "pages/login.html", controller: "LoginController" })
     .when("/FBLogin",
  {   templateUrl: "pages/FBLogin.html", controller: "LoginController" })
          .when("/search",
  {   templateUrl: "pages/list.html",  controller: "TravelController"  })
          .when("/detail",
  {   templateUrl: "pages/travelDetail.html",  controller: "DetailController"  })
          .when("/post",
  {   templateUrl: "pages/post.html",  controller: "PostController"  })
          .when( "/history",
  {   templateUrl: "pages/history.html", controller: "HistoryController" });
  
  
});


  myapp.controller("MainController", ['$scope', '$http','$route','$location' , 'shared', 'auth',
      function($scope, $http, $route,$location,shared ,auth) {
         
      $scope.hello = "Powered by AngularJs";
      $scope.hasFooter = true;
      $scope.loginShow;      
      $scope.isAuthorized =false;
      
      $scope.about = false;
      $scope.show = function () {
          $scope.about = true;
      };
      
      $scope.go = function (where) {
          $location.path(where);
      };
      
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
            
        
        /*    
       
          $(function() {
              $( "#datepicker" ).datepicker();
          });
        
  */
      
   }]);
   
  
 
   
