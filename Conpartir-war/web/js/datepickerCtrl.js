
(function () {
  'use strict';


var modDate = angular.module('dateModule', ['ngRoute']);

        modDate.controller('DatepickerCtrl', ['$scope',
        function ($scope) {
             $(document).ready(function() {
                $( "#datepicker" ).datepicker();
            });     
        }]);


})();