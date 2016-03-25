(function () {
  'use strict';

  var modAccount = angular.module('accountModule', ['ngRoute']);
        modAccount.controller('AccountController', ['$scope', '$http', '$location','auth','shared',
        function($scope,$http,$location,auth,shared) {
            
            $scope.clientInfo;
            $scope.driverInfo;
            $scope.show = [true, false, false];
            
            $scope.check = function() {
                auth.checkAuth(sessionStorage.getItem("conpCookie"));
            };
            
           $scope.onLoad = function() {
             if (auth.isAuthenticated() == false) {
                 alert("Per favore, effettua il login");
                 $location.path('/login');
                }
             else {
                 var self= $location.search();
                 if(self.email === undefined) self.email = sessionStorage.getItem('email');
                   $scope.loadDrivers();      
                 shared.getClient(self.email).then(function(promise) {
                     var prova = shared.getData(); 
                     $scope.clientInfo = prova.return;
                     //console.log($scope.clientInfo);
                     
                 });
                 
                 
             };   
           };      
           
           $scope.loadDrivers = function () {
               var self= $location.search(); 
                 if(self.email === undefined) self.email = sessionStorage.getItem('email');
                        
                 shared.getDrivers(self.email).then(function(promise) {
                     var prova = shared.getData(); 
                     $scope.driversInfo = prova.return;
                     console.log($scope.driversInfo);
                     
                 });
               
               
           }, 
           
           $scope.tab = function(data) {
                if (data=="self") {$scope.show[0] = true, $scope.show[1] = false; $scope.show[2] = false; };
                if (data=="cars") {$scope.show[0] = false, $scope.show[1] = true; $scope.show[2] = false; };
                if (data=="feed") {$scope.show[0] = false, $scope.show[1] = false; $scope.show[2] = true; };
                
            };
            
            $scope.alert = function() {
              alert("Aggiungere una macchina al tuo profilo ti permetterà di offrire viaggi con quella macchina. \n\
                     Se non l'hai mai fatto, inizia subito per poter offrire un passaggio!");  
            };
     
        }]);

})();
