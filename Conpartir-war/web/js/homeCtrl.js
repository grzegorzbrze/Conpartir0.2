/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var myapp = angular.module('myApp', ['ngRoute', 'ngTouch' , 'ngAnimate', 'ui.bootstrap']);
  
  myapp.config(function($routeProvider){
  $routeProvider.when("/",
    {
      templateUrl: "pages/home.html",
      controller: "MainController"
     // controllerAs: "app"
    });
    
    $routeProvider.when("/about",
    {
      templateUrl: "pages/about.html",
      controller: "MainController"
     // controllerAs: "app"
    });
    $routeProvider.when("/taxis",{
        templateUrl: "pages/taxis.html",
        controller: "PostController"
        // controllerAs: "app"
    });
    $routeProvider.when("/cars",{
        templateUrl: "pages/cars.html",
        controller: "PostController"
        // controllerAs: "app"
    });
    $routeProvider.when("/signup",{
        templateUrl: "pages/signup.html",
        controller: "MainController"
        // controllerAs: "app"
    });
    $routeProvider.when("/login",{
        templateUrl: "pages/login.html",
        controller: "MainController"
        // controllerAs: "app"
    });
    $routeProvider.when("/createpost",{
        templateUrl: "pages/createpost.html",
        controller: "MainController"
        // controllerAs: "app"
    });
    $routeProvider.when("/postview",{
        templateUrl: "pages/postview.html",
        controller: "MainController"
        // controllerAs: "app"
    });    
});
  myapp.controller("MainController", ['$scope', 
      function($scope) {
         
      $scope.hello = "Powered by AngularJs";
      $scope.hasFooter = true;
      $scope.cities = ["Bologna", "Firenze", "Genova", "Milano", "Napoli", 
          "Padova", "Pisa", "Roma", "Siena", "Torino", "Venezia", "Verona"];
      
     
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
  

  

  

          
      
      
   }]);
   
   
 
   
