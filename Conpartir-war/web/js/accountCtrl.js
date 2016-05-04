(function () {
  'use strict';

  var modAccount = angular.module('accountModule', ['ngRoute']);
        modAccount.controller('AccountController', ['$scope', '$http', '$location','$window','auth','shared',
        function($scope,$http,$location,$window,auth,shared) {
            
            $scope.clientInfo;
            $scope.driversInfo;
            $scope.bookedTravelsInfo = { items: []};
            $scope.postedTravelsInfo = {items: []};
            $scope.isAuth;
            $scope.selectedCar;
            $scope.closeTravels;
            
            $scope.show = [true, false, false];  
            var myDriverIds = [];           
            var self= $location.search(); 
            
            var isArray = function(what) {              
                return Object.prototype.toString.call(what) === '[object Array]';
            };
            
             $scope.getDay = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(0,splitter);                
            };
            
             $scope.getTime = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(splitter+1,splitter+6);                
            };        
                        
            
            $scope.check = function() {
                auth.checkAuth(sessionStorage.getItem("conpCookie")).then(function (promise){
                    if (promise.status==200) $scope.isAuth = true;
                    else $scope.isAuth=false;
                    
                     if ($scope.isAuth == false) {
                         alert("Per favore, effettua il login");
                         $location.path('/login');
                         return;
                     }
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
                    if (item.driver_id === data) { 
                        $scope.selectedCar = item;                         
                    }; 
                };
                console.log("selected car " + $scope.selectedCar.carModel);
              //  $window.location.reload();
                
            };
            
           $scope.onLoad = function() {
               $scope.check();
               
               if(self.email === undefined) self.email = sessionStorage.getItem('email');
               
               shared.getClient(self.email).then(function(promise) {
                   var prova = shared.getClientInfo(); 
                   $scope.clientInfo = prova.return;
               });
               $scope.loadDrivers();  
               $scope.getLatestComment();
               $scope.loadBookedTravels();
           };   
           
            $scope.loadDrivers = function () {
                  if(self.email === undefined) self.email = sessionStorage.getItem('email');                     
                 shared.getDrivers(self.email).then(function(promise) {
                     var prova = shared.getCars(); 
                     $scope.driversInfo = prova;
                     
                     if(!isArray($scope.driversInfo)) {
                          $scope.selectedCar = $scope.driversInfo;
                          myDriverIds[0] = $scope.driversInfo.driver_id;
                         
                     }
                     else{
                         $scope.selectedCar = $scope.driversInfo[0]; 
                         var k;
                         for (k=0;k<$scope.driversInfo.length;k++) { 
                             myDriverIds[k] = $scope.driversInfo[k].driver_id;
                         };     
                     };             
                     // console.log(myDriverIds);
                 });
             }; 
             
            
            $scope.loadBookedTravels = function () {
                if(self.email === undefined) self.email = sessionStorage.getItem('email');
                shared.getClientTravel(self.email).then (function(promise) {
                    var res = shared.getBookedTravels();
                    
                     if(isArray(res.return)) {  
                         var i;                         
                        for (i=0;i<res.return.length;i++) {
                             if(myDriverIds.includes(res.return[i].driver_id)) { $scope.postedTravelsInfo.items.push(res.return[i]); }
                             else { $scope.bookedTravelsInfo.items.push(res.return[i]); };                         
                         };
                     }
                     else {
                         if(myDriverIds.includes(res.driver_id)) { $scope.postedTravelsInfo.items.push(res.return); }
                         else { $scope.bookedTravelsInfo.items.push(res.return); }
                         
                     };
                     
                     
                     //manca per i postedTravels
                  var today = new Date();
                  var j;
                  for (j=0;j<$scope.bookedTravelsInfo.items.length;j++) {
                       var data = $scope.getDay($scope.bookedTravelsInfo.items[j].data) + 'T' + $scope.getTime($scope.bookedTravelsInfo.items[j].time) + ':00';
                        var travelDataCompleta = new Date(data);
                        var timeDiff = (today.getTime() - travelDataCompleta.getTime())/1000/60/24;
                      
                      if( timeDiff <= 2) $scope.closeTravels = true;
                  }
                  
                 });
                 
                
            };
            
            $scope.getLatestComment = function(){
                if(self.email === undefined) self.email = sessionStorage.getItem('email');
                        
                 shared.getLatestReceivedComments(self.email,10).then(function(promise) {
                     var prova = shared.getComments(); 
                     $scope.commentInfo = prova;
                 });
            };
            
            $scope.alert = function() {
              alert("Aggiungere una macchina al tuo profilo ti permetterà di offrire viaggi con quella macchina. \n\
                     Se non l'hai mai fatto, inizia subito per poter offrire un passaggio!");  
            };
     
        }]);

})();
