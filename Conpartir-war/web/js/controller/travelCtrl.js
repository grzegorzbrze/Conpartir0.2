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
                $("#datepicker" ).datepicker({
                    dateFormat: "yyyy-mm-dd"
                });    
                $("#datepicker2" ).datepicker({
                    dateFormat: "yyyy-mm-dd"
                });    
                $('#timepicker').timepicker({
                    showMeridian : false,
                    defaultTime : false,
                    maxHours : 24
                });
                $('#timepicker2').timepicker({
                    showMeridian : false,
                    defaultTime : false,
                    maxHours : 24
                });
            });
            
            $scope.SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
            $scope.travelList;
            $scope.showHead = false;
            $scope.relatedDriver;
            $scope.showCar;
            $scope.showTaxi;
            $scope.empty = false;
            $scope.ifAlert = false;
            $scope.alert;
                        
            var gotWSDL = false;
            
            var callback = function(){
                $scope.$apply();
            };
            
            $scope.go = function (data) {
                shared.setTravelInfo(data);
                var type;
                if($scope.showCar === true)  type = 1;
                else type = 2;
                //sessionStorage.setItem('number&type',data.travel_id + '_' + type);
                $location.path("/detail");
                $location.search("number",data.travel_id);
                $location.search("type",type);
                $route.reload();
            };
            
            $scope.switch = function(data) {
                if (data === 'car') {
                    $scope.showCar = true;
                    $scope.showTaxi = false;  };
                if (data === 'taxi') {
                    $scope.showTaxi = true;
                    $scope.showCar = false;   };
                $scope.showHead = false;
                $scope.ifAlert = false;
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
                $scope.ifAlert = false;
                $scope.travelList =null;
                $scope.showHead = false;
                $scope.empty = false;
                
                  if(jQuery.isEmptyObject( data )) {
                    $scope.alert = "Per favore, compila il form."; 
                    $scope.ifAlert = true;
                    return;
                }      
             
                if($scope.showCar===true) searchCar(data);
                else searchTaxi(data);
            };
            
            var searchCar = function (data) {                            
                
                if(jQuery.isEmptyObject( data.from )) { 
                    $scope.alert = "Per favore, inserisci una località di partenza."; 
                    $scope.ifAlert = true;
                    return;
                } 
                
                 if(jQuery.isEmptyObject( data.to )) { 
                    $scope.alert = "Per favore, inserisci una località di arrivo."; 
                    $scope.ifAlert = true;
                    return;
                    
                } 
                
                var when = $('#datepicker').datepicker({dateFormat: "yyyy-mm-dd" }).val();
                
                if(when === null || when === "") { 
                    $scope.alert = "Per favore, inserisci una data."; 
                    $scope.ifAlert = true;
                    return;
                } 
                var month = when.slice(0,2);
                var day = when.slice(3,5);
                var year= when.slice(6,10);
                when = day + '-' + month + '-' + year;
                
                data.when = when; 
                
                var time =  $('#timepicker').timepicker().val();
                if (time !== null && time !== "") {
                    var hour = time.slice(0,time.indexOf(':'));
                    var minutes = time.slice(time.indexOf(":"));
                    if (minutes === null || minutes === "") { minutes = "00";};
                    time = hour + minutes + ":00";
                    
                 data.when = data.when +":"+ time;
                }
                else {
                    data.when = data.when +":00:00:00";
                }
                console.log("formato richiesta a " + data.to +" da " + data.from + " il giorno " + data.when);  
                
                
                shared.getTravels(data).then(function(promise) {
                    var prova = shared.getData(); 
                    if (jQuery.isEmptyObject( prova )){ emptyResult(); }
                    else {  
                        if(isArray(prova.return)) {  
                            $scope.travelList = prova.return;  
                            $scope.showHead = true;   
                        }
                        else {
                            //console.log("is Single Element");  
                            $scope.travelList=  prova; 
                            $scope.showHead = true;   
                        }  
                    }  
                });
            };   
            
            var searchTaxi = function (data) {
                
                if(jQuery.isEmptyObject( data.from )) { 
                    $scope.alert = "Per favore, inserisci una via di partenza."; 
                    $scope.ifAlert = true;
                    return;
                } 
                
                var when = $('#datepicker2').datepicker({dateFormat: "yyyy-mm-dd" }).val();
                
                if(when == null || when == "") { 
                    $scope.alert = "Per favore, inserisci una data."; 
                    $scope.ifAlert = true;
                    return;
                } 
                var month = when.slice(0,2);
                var day = when.slice(3,5);
                var year= when.slice(6,10);
                when = day + '-' + month + '-' + year;
//                if (when !== null && when !== "") {
//                    var month = when.slice(0,2);
//                    var day = when.slice(3,5);
//                    var year= when.slice(6,10);
//                    when = day + '-' + month + '-' + year; 
//                }  
                data.when = when;               
                
                var time =  $('#timepicker2').timepicker().val();
                if (time !== null && time !== "") {
                    var hour = time.slice(0,time.indexOf(':'));
                    var minutes = time.slice(time.indexOf(":"));
                    if (minutes === null || minutes === "") { minutes = "00";};
                    time = hour + minutes + ":00";    
                    data.when = data.when +":"+ time;
                }
                else { data.when = data.when +":00:00:00"; }
                console.log("formato richiesta a " + data.to +" da " + data.from + " il giorno " + data.when);  
                 
                shared.getTaxiTravels(data).then(function(promise) {
                    var prova = shared.getData(); 
                    if (jQuery.isEmptyObject( prova )){ emptyResult(); }
                    else {  
                        if(isArray(prova.return)) {  
                            $scope.travelList = prova.return;  
                            //console.log($scope.travelList);
                            console.log("is Array");
                            var item; 
                            var k = prova.return.length;
                            var i;         
                            $scope.showHead = true;   
                        }
                        else {
                            console.log("is Single Element");  
                            $scope.travelList=prova;                                
                            $scope.showHead = true;   
                        }  
                    }  
                });
            };   
                
            var isArray = function(what) {              
                return Object.prototype.toString.call(what) === '[object Array]';
            };
            
            var emptyResult = function () {
                $scope.empty = true;
            };          
        }]);
})();
