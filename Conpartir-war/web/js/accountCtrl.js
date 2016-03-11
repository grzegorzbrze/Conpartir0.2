(function () {
  'use strict';

  var modAccount = angular.module('accountModule', ['ngRoute']);
        modAccount.controller('AccountController', ['$scope', '$http', '$location','auth','shared',
        function($scope,$http,$location,auth,shared) {
            
            $scope.clientInfo;
            
           $scope.onLoad = function() {
             if (auth.isAuthenticated() == false) {
                 alert("Per favore, effettua il login");
                 $location.path('/login');
                }
             else {
                 var self = $location.search();
                 shared.getClient(self.email).then(function(promise) {
                     var prova = shared.getData(); 
                     $scope.clientInfo = prova.return;
                     console.log($scope.clientInfo);
                     
                 });
             };   
           };            
     
        }]);

})();