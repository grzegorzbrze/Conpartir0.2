(function () {
  'use strict';

  var modHistory = angular.module('historyModule', ['ngRoute']);
        modHistory.controller('HistoryController', ['$scope', '$http', '$location','$route' ,'$window','auth','shared',
        function($scope,$http,$location,$route,$window,auth,shared) {
            
            $scope.clientInfo;
            $scope.driversInfo;
            $scope.bookedTravelsInfo;
            $scope.postedTravelsInfo;
            $scope.bookedTaxisInfo;
            $scope.postedTaxisInfo;
            $scope.ready = false;
            $scope.isAuth;
            $scope.selectedCar;
            $scope.closeTravels;
            $scope.changePass = false;
            $scope.showModal;
            $scope.mod;
            $scope.history
            
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
                        
            $scope.go = function (data,int) {
                shared.setTravelInfo(data);                
                //sessionStorage.setItem('number&type',data.travel_id + '_' + type);
                $location.path("/detail");
                $location.url($location.path());
                $location.search("number",data.travel_id);
                $location.search("type",int);
                  $route.reload();
            };
            
            
            $scope.loadHistory = function () {                
                 if(self.email === undefined) self.email = sessionStorage.getItem('email');    
                 shared.getHistoryTravels(self.email).then(function(promise) {
                    var prova = shared.getHistory(); 
                    $scope.history = prova.return;
                   
                /*    if (jQuery.isEmptyObject( prova )){ console.log('prova is empty') }
                    else {  
                        if(isArray(prova.return)) {  
                            $scope.history = prova.return;  
                            showList();
                        }
                        else {
                            //console.log("is Single Element");  
                            $scope.travelList=  prova.return; 
                            showList();
                        }  
                    }  */
                    console.log($scope.history);
                 });
            };
            
            $scope.alert = function() {
              $scope.modalInfo = "Aggiungere una macchina al tuo profilo ti permetterà di offrire viaggi con quella macchina. \n\
                     Se non l'hai mai fatto, inizia subito per poter offrire un passaggio!";  
            };
            
            $scope.edit = function(input) {
                if(self.email === undefined) self.email = sessionStorage.getItem('email'); 
                input.email = self.email;
                shared.editClient(input).then(function(promise) {
                    
                    $scope.modalInfo = shared.getData();                    
                 });
            };
     
        }]);


modHistory.directive('modalDirective', function(){
    return{
        restrict : 'A',
        link: function(scope, element,attrs) {
            scope.$watch(attrs.modalDirective, function(value) {
                if(value) element.modal('show');
                      else element.modal('hide');
            });
        }
        
    };
});

})();
