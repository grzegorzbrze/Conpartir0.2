/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modTravel = angular.module('travelModule', ['ngRoute']);
        modTravel.controller('TravelController', ['$scope', '$http', '$route', '$routeParams','$location','$timeout','shared',
        function($scope,$http, $route, $routeParams, $location,$timeout, shared) {
            
            $(document).ready(function() {
                $( "#datepicker" ).datepicker({
                    dateFormat: "yyyy-mm-dd"
                });    
                $('#timepicker').timepicker({
                    showMeridian : false,
                    defaultTime : false,
                    maxHours : 24
                });
            });
            
            $scope.SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
            $scope.travelList;
            $scope.showHead = false;
            $scope.relatedDriver;
            $scope.prova;
            $scope.showCar = true;
            $scope.showTaxi = false;           
            
            var gotWSDL = false;
            
            var callback = function(){
                $scope.$apply();
            };
            
            $scope.go = function (data) {
                shared.setData($scope.travelList);
                $location.path("/detail");
                $location.search("number",data);
                shared.setData($scope.travelList);
                $route.reload();
            };
            
            $scope.tab = function(data) {
                if (data=="car") {$scope.showCar = true, $scope.showTaxi = false; };
                if (data=="taxi") {$scope.showTaxi = true, $scope.showCar = false; }
            };
            
            $scope.getBack = function () {
                alert(shared.getData());                
            };
            
            $scope.getDay = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(0,splitter);                
            };
            
             $scope.getTime = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(splitter+1,splitter+6);                
            };
                    
             $scope.reload = function () {
                  $route.reload();
              };
              
            $scope.search = function(data) { 
                $scope.prova =null;
                $scope.showHead = false;
            
                var when = $('#datepicker').datepicker({dateFormat: "yyyy-mm-dd" }).val();
                
                if (when !== null && when != "") {             
                    var month = when.slice(0,2);
                    var day = when.slice(3,5);
                    var year= when.slice(6,10);
                    when = day + '-' + month + '-' + year; }
                
                    data.when = when;
               
                
                var time =  $('#timepicker').timepicker().val();
                if (time !== null && time !== "") { 
                    var ind = time.indexOf(" ");
                    var hour = time.slice(0,time.indexOf(':'));
                    var minutes = time.slice(time.indexOf(":"),ind+1);
                    if (minutes === null || minutes === "") { minutes = "00";};
                    //var hourMinutes = time.slice(0,ind);
                    time = hour +':'+ minutes + ":00";
                    
                 data.when = data.when +":"+ time;
                }
                else {
                    data.when = data.when +":00:00:00";
                }
                
                  
                console.log("formato richiesta " + data.to + data.from + " il giorno " + data.when);  
              
              
                shared.getTravels(data).then(function(promise) {
                     var prova = shared.getData();                      
                     if(isArray(prova.return)) {                       
                         $scope.prova = prova.return;
                        console.log($scope.prova);
                         console.log("is Array");
                         $scope.isArray = true;
                         var item;
                         
                         var k = prova.return.length;
                         var i;
                        console.log(k);
                         for (i=0;i<k;i++) {
                             console.log($scope.prova[i]);
                             shared.getDriverFromTravel($scope.prova[i].travel_id).then(function(promise) {
                                 $scope.relatedDriver = shared.getData();
                                 //console.log("scope relatedDriver is " ); 
                                 //console.log($scope.relatedDriver);
                                 showList();
                             });
                         }
                     }
                     else {
                         $scope.isSingleElement = true;
                         console.log($scope.prova);
                         console.log("is Single Element");                         
                         $scope.prova=  prova;  
                         
                         
                         shared.getDriverFromTravel($scope.prova.return.travel_id).then(function(promise) {
                             $scope.relatedDriver = shared.getData();   
                             //console.log("scope relatedDriver is " ); 
                             //console.log($scope.relatedDriver);
                             showList();
                         });
                     }                     
                     
                     
                    
                });
                
               
               
          
            };   
           $scope.isArray = false; 
           $scope.isSingleElement = false;
            
            var isArray = function(what) {              
                return Object.prototype.toString.call(what) === '[object Array]';

            };
            
            var showList = function() {                
                 //$scope.prova = shared.getData2(callback);                 
                
                    $scope.showHead = true;         
                    
                   // console.log("contenuto dello scope dopo la showlist " + $scope.prova); 
            //$timeout(function() { },60);
            };
     

            
            
          
     
        }]);
    
    

})();
