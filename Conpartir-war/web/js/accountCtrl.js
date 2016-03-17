(function () {
  'use strict';

  var modAccount = angular.module('accountModule', ['ngRoute']);
        modAccount.controller('AccountController', ['$scope', '$http', '$location','auth','shared',
        function($scope,$http,$location,auth,shared) {
            
            $scope.clientInfo;
            
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
                     var prova = shared.getData(); 
                     $scope.clientInfo = prova.return;
                     //console.log($scope.clientInfo);
                     
                 });
             };   
           };            
     
        }]);

})();
