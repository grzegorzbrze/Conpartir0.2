/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var myapp = angular.module('myApp', ['ngRoute', 'ngTouch' , 'ngAnimate', 'ui.bootstrap', 'loginModule', 'sliderModule', 'postModule']);
  
  myapp.config(function($routeProvider){
  $routeProvider
          .when("/", "/home",
  {   templateUrl: "pages/home.html", controller: "MainController" })    
          .when("/about",
  {   templateUrl: "pages/about.html", controller: "MainController" })
          .when("/account",
  {   templateUrl: "pages/account.html", controller: "MainController" })
          .when("/taxis", 
  {   templateUrl: "pages/taxis.html", controller: "PostController" })
          .when("/cars",
  {   templateUrl: "pages/cars.html", controller: "PostController"   })
          .when("/signup",
  {   templateUrl: "pages/signup.html", controller: "LoginController" })
          .when("/login",
  {   templateUrl: "pages/login.html", controller: "LoginController" })
          .when("/list",
  {   templateUrl: "pages/list.html",  controller: "PostController"  })
          .when("/post",
  {   templateUrl: "pages/post.html",  controller: "MainController"  });  
  
  
});
  myapp.controller("MainController", ['$scope', '$http', '$uibModal',
      function($scope, $http, $uibModal) {
         
      $scope.hello = "Powered by AngularJs";
      $scope.hasFooter = true;
      
      //Inizio carousel della home
     
       $scope.myInterval = 5000;
       $scope.noWrapSlides = false;
       var slides = $scope.slides = [];
       var currIndex = 0;

        $scope.addSlide = function(i) {
         slides.push({
             image: 'prova' + i + ".png",
             text: ['Nice image','Awesome photograph','That is so cool','I love that'][slides.length % 4],
             id: currIndex++
           });
        }; 

        for (var i = 0; i < 4; i++) {
            $scope.addSlide(i+1);
        }
        
        // Fine Carousel
        
    
        
  
      
   }]);
   
  
 
   
