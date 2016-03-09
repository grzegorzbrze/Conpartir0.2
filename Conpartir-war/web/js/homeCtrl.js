/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var myapp = angular.module('myApp', 
['ngRoute', 'ngTouch' , 'ngAnimate',
    'loginModule', 'sliderModule', 'travelModule', 'dateModule', 'detailModule', 'serviceModule']);
  
  myapp.config(function($routeProvider){
  $routeProvider
          .when( "/",
  {   templateUrl: "pages/home.html", controller: "MainController" })
          .when( "/home",
  {   templateUrl: "pages/home.html", controller: "MainController" })    
          .when("/about",
  {   templateUrl: "pages/about.html", controller: "MainController" })
          .when("/account",
  {   templateUrl: "pages/account.html", controller: "MainController" })
          .when("/signup",
  {   templateUrl: "pages/signup.html", controller: "LoginController" })
          .when("/login",
  {   templateUrl: "pages/login.html", controller: "LoginController" })
     .when("/FBLogin",
  {   templateUrl: "pages/FBLogin.html", controller: "LoginController" })
          .when("/list",
  {   templateUrl: "pages/list.html",  controller: "TravelController"  })
          .when("/detail",
  {   templateUrl: "pages/travelDetail.html",  controller: "DetailController"  })
          .when("/post",
  {   templateUrl: "pages/post.html",  controller: "MainController"  });  
  
  
});




  myapp.controller("MainController", ['$scope', '$http', 
      function($scope, $http) {
         
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
       
          $(function() {
              $( "#datepicker" ).datepicker();
          });
        
  
      
   }]);
   
  
 
   
