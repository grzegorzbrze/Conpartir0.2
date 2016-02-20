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
        
        
        $scope.master = {};
        $scope.status = {};

      $scope.login = function(user) {
        $scope.master = user;
        $scope.master.use = "login";
        
        if (user.email == "", user.email == undefined || user.pass == "", user.pass == undefined ) alert("Per favore, completa i campi per effettuare il login!");
        else $scope.servletCall($scope.master);
       };
       
              
       $scope.register = function (user) {
           $scope.master = user;
           $scope.master.use = "registration";
           flag = false;
           
           if (user.pass !== user.passRe) { 
               alert("Le password inserite sono diverse");
               flag = true; 
           }           
           if (user.email === null) { 
               alert("Prego, inserisci un'email valida");
               flag = true; 
           }           
           if (user.name === null || user.surname === null) { 
               alert("Prego, inserisci il tuo nome!");
               flag = true; 
           }
           if (user.age === null || user.gender === null) { 
               alert("Per favore, completa tutti i campi.");
               flag = true; 
           }
              
            if (flag === false) $scope.servletCall($scope.master);
            
       };       
        
        $scope.servletCall = function (data){ 
            $http({
                method: 'POST',
                url: 'Registration',
                headers: {'Content-Type': 'application/json'},
                data:  $scope.master
            }).success(function (data)
            {
                $scope.status=data;
                alert($scope.status);
                
            });         
        };
        
        
  
      
   }]);
   
  
 
   
