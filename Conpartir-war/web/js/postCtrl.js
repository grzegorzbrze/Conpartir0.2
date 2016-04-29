(function () {
  'use strict';

  var modPost = angular.module('postModule', ['ngRoute']);
  modPost.controller('PostController', ['$scope', '$route', '$routeParams','$location','$timeout' ,'login', 'shared',
      function($scope,$route, $routeParams, $location, $timeout,login, shared) {
          
          $scope.notLogged = true;
          $scope.isCarTravel = false;
          $scope.clientInfo;
          $scope.cars;
          $scope.travel;
          
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
          
          $scope.check = function () {
              var flag;
              login.getAuth().then(
                      function successCallback(data) {  
                    if (data.status==200) 
                    {
                        flag = true;                     
                       //console.log("flag vale" + flag);
                    }; 
                    if (flag == true){ 
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
                  $scope.cars = shared.getCars();
                  //$scope.clientInfo = shared.getClientInfo();
                  //console.log("macchine" +$scope.cars);
              }
              else {
              };
             
          };
          
          $scope.post = function (input) {
               var when = $('#datepicker').datepicker({dateFormat: "yyyy-mm-dd" }).val();
                     
                if (when !== null && when != "") {             
                    var month = when.slice(0,2);
                    var day = when.slice(3,5);
                    var year= when.slice(6,10);
                    when = day + '-' + month + '-' + year;
                    
                }
                $scope.travel.when = when +':'+ $scope.travel.hour+ ":00";
                
                $scope.travel.email = sessionStorage.getItem('email');
                console.log($scope.travel);
                if( $scope.isCarTravel === true) { 
                    shared.createCarTravel($scope.travel).then(function(promise) {
                    });
                }
                else {
                   shared.createTaxiTravel($scope.travel).then(function(promise) {
                    }); 
                }
                
                $location.path('/');
                alert ("viaggio postato con successo!");
          };
          
          $scope.select = function (dataId,dataModel) {
              $scope.travel.id = dataId;
              $scope.travel.carModel = dataModel;
          }; 
          
    
    
                
        }]);  
    
   
    

})();
