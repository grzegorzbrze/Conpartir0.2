(function () {
  'use strict';

  var modAccount = angular.module('accountModule', ['ngRoute']);
        modAccount.controller('AccountController', ['$scope', '$http', '$location','$window','auth','shared',
        function($scope,$http,$location,$window,auth,shared) {
            
            $scope.clientInfo;
            $scope.driversInfo;
            $scope.selectedCar;
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
                      
                 shared.getClient(self.email).then(function(promise) {
                     var prova = shared.getClientInfo(); 
                     $scope.clientInfo = prova.return;
                 });
                 $scope.loadDrivers();  
             };   
           };      
           
           $scope.loadDrivers = function () {
               var self= $location.search(); 
                 if(self.email === undefined) self.email = sessionStorage.getItem('email');
                        
                 shared.getDrivers(self.email).then(function(promise) {
                     var prova = shared.getCars(); 
                     $scope.driversInfo = prova;
                     //console.log($scope.driversInfo);
                     $scope.selectedCar = $scope.driversInfo[0];
                 });
             }; 
           
           $scope.tab = function(data) {
                if (data=="self") {$scope.show[0] = true, $scope.show[1] = false; $scope.show[2] = false; };
                if (data=="cars") {$scope.show[0] = false, $scope.show[1] = true; $scope.show[2] = false; };
                if (data=="feed") {$scope.show[0] = false, $scope.show[1] = false; $scope.show[2] = true; };
                
            };
            
            $scope.carInfo = function(data) {
                var item; 
                  //console.log("data is " + data);
                for (item in $scope.driversInfo) {
                  //console.log("driver_id "+item.driver_id +" is equal to "+ data +  " ?");
                    if (item.driver_id == data) { 
                        $scope.selectedCar = item;                         
                    }; 
                };
                console.log("selected car " + $scope.selectedCar.carModel);
              //  $window.location.reload();
                
            };
            
            $scope.alert = function() {
              alert("Aggiungere una macchina al tuo profilo ti permetterà di offrire viaggi con quella macchina. \n\
                     Se non l'hai mai fatto, inizia subito per poter offrire un passaggio!");  
            };
     
        }]);

})();
