/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modTravel = angular.module('travelModule', ['ngRoute']);
        modTravel.controller('TravelController', ['$scope', '$http', '$route', '$routeParams','$location','$timeout','$q','shared',
        function($scope,$http, $route, $routeParams, $location,$timeout,$q, shared) {
            
             $(document).ready(function() {
                $( "#datepicker" ).datepicker({
                    dateFormat: "yyyy-mm-dd"
                });
            });
            
            $scope.SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
            $scope.travelList;
            $scope.relatedDrivers;
         
            $scope.showHead = false;
            $scope.new;
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
            //Header e footer di una richiesta Soap
            var SOAPhead = '<?xml version="1.0" encoding="utf-8"?>' +                        
                           '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+                        
                           '<soap:Body>';
            var SOAPtail =  '</soap:Body>' +
                            '</soap:Envelope>';
             
             $scope.reload = function () {
                  $route.reload();
              };
              
           var okToGreet = function (name) {
                  return true;
              };
              
              $scope.asyncGreet = function(name) {
                  var deferred = $q.defer();
                  setTimeout(function() {
                      deferred.notify('About to greet ' + name + '.');
                      if (okToGreet(name)) {
                          deferred.resolve('Hello, ' + name + '!');
                      } else {
                          deferred.reject('Greeting ' + name + ' is not allowed.');
                      }                     
                  }, 1000);
                  return deferred.promise;
              };
              
              $scope.name;

              $scope.callAsyncGreet = function (data) {
                  $scope.promise = $scope.asyncGreet(data);
                  $scope.promise.then(function(greeting) {
                      alert('Success: ' + greeting);
                  }, function(reason) {
                      alert('Failed: ' + reason);
                      $scope.nome = "niente";
                  }, function(update) {
                      alert('Got notification: ' + update);
                  });
              };

     
              
           
            
            $scope.search = function(data) { 
                $scope.prova =null;
                $scope.showHead = false;
              //  $( "#datepicker" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
             
               // var when = $('#datepicker').datepicker({ dateFormat: "yy-mm-dd" }).val();
               // when = when.replace('/','-');
               // when = when.replace('/','-');
                var when = $('#datepicker').datepicker({dateFormat: "yyyy-mm-dd" }).val();
                console.log("data formattata " +when);
                if (when !== null && when != "") {             
                    var month = when.slice(0,2);
                    var day = when.slice(3,5);
                    var year= when.slice(6,10);
                    when = day + '-' + month + '-' + year;
             
                    data.when = when;
                }
                
                console.log("formato richiesta " + data.to + data.from + " " + data.when);  
               
                   
                 //   console.log("contenuto dello scope prima della showlist " + $scope.prova);
                 //   showList();
                shared.getTravels(data).then(function(promise) {
                     var prova = shared.getData(); 
                     
                     if(isArray(prova.return)) {
                         $scope.prova = prova.return;   
                         console.log("is Array");
                         $scope.isArray = true;
                     }
                     else {
                         $scope.isSingleElement = true;
                         console.log("is Single Element");
                         
                         $scope.prova=  prova;
                         
                         }
                     
                     console.log("scope prova is " + $scope.prova);
                     showList();
                });
               
          
            };   
           $scope.isArray = false; 
           $scope.isSingleElement = false;
            
            var isArray = function(what) {              
                return Object.prototype.toString.call(what) === '[object Array]';

            };
            
            var showList = function() {
                
                 //$scope.prova = shared.getData2(callback);
                 
                $timeout(function() { $scope.showHead = true;
                
                console.log("contenuto dello scope dopo la showlist " + $scope.prova); 
            },60);
            };
     

            
            
          
     
        }]);
    
    

})();
