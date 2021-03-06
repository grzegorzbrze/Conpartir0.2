(function () {
  'use strict';

  var modPost = angular.module('postModule', ['ngRoute']);
  modPost.controller('PostController', ['$scope', '$route', '$routeParams','$location','$timeout' , 'shared', 'auth',
      function($scope,$route, $routeParams, $location, $timeout, shared, auth) {
          
          $scope.notLogged;
          $scope.isCarTravel = false;
          $scope.clientInfo;
          $scope.cars;
          $scope.travel = {};
          
            $(document).ready(function() {
                $( "#datepicker" ).datepicker({
                    dateFormat: "yyyy-mm-dd"
                });    
                $('#timepicker').timepicker({
                    showMeridian : false,
                    defaultTime : false,
                    maxHours : 24                    
                });    
                $('#timepicker').timepicker().on('changeTime.timepicker', function(e) {
                    //console.log('The time is ' + e.time.value);
                    $scope.travel.hour = e.time.value;
                });
            });
            
            var isArray = function(what) {     
                return Object.prototype.toString.call(what) === '[object Array]';
            };
            
            $scope.check = function () {
                var flag;
                
                auth.checkAuth().then(function (promise) {
                    if (promise.status===200) {  flag = true; }; 
                    if (flag === true){ 
                        $scope.notLogged = false;                        
                       }
                    else {
                        $scope.notLogged = true;
                    }          
                });          
             // console.log("risultato =" +$scope.notLogged);
            };
          
            $scope.mode = function () {
                if($scope.isCarTravel===true){                  
                    if (!isArray(shared.getCars())) {
                      $scope.cars = [shared.getCars()]; 
                    }
                    else $scope.cars = shared.getCars();
                }
                else {
                };
            };
          
            $scope.post = function (input) {
                var when = $('#datepicker').datepicker({dateFormat: "yyyy-mm-dd" }).val();                     
                if (when !== null && when !== "") {             
                    var month = when.slice(0,2);
                    var day = when.slice(3,5);
                    var year= when.slice(6,10);
                    when = day + '-' + month + '-' + year;                    
                }
                $scope.travel.when = when +':'+ $scope.travel.hour+ ":00";                
                $scope.travel.email = sessionStorage.getItem('email');
                console.log($scope.travel);
                if( $scope.isCarTravel === true) { 
                    shared.createCarTravel($scope.travel).then(function (promise) {
                    });
                }
                else {
                    shared.getGeoJson($scope.parseStreet(input.from), "Torino", 1).then(function (promise) {
                        var geolocStart = shared.getData();
                        //console.log(geolocStart);

                        $scope.travel.coordStart = geolocStart[0].lat.toString() + "+" + geolocStart[0].lon.toString();
                        
                        shared.getGeoJson($scope.parseStreet(input.to), "Torino", 1).then(function (promise) {
                            var geolocEnd = shared.getData();
                            //console.log(geolocEnd);
                            $scope.travel.coordEnd = geolocEnd[0].lat.toString() + "+" + geolocEnd[0].lon.toString();
                            $timeout(
                                    shared.createTaxiTravel($scope.travel).then(function (promise) {
                            }), 10);
                        });
                    });                   
                  
                }                
                $('#modalPost').modal('hide');
                $('#modalTravelAdded').modal('show');                
            };
          
            $scope.select = function (dataId,dataModel) {
              $scope.travel.id = dataId;
              $scope.travel.carModel = dataModel;
            };
          
            $scope.parseStreet =  function (streetString) {
              var parsedString = "";
              parsedString= streetString.replace(' ','+');              
              return parsedString;
            };
      }]);  
    
   
    

})();
