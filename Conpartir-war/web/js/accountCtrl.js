(function () {
    'use strict';

    var modAccount = angular.module('accountModule', ['ngRoute']);
    modAccount.controller('AccountController', ['$scope', '$http', '$location', '$route', '$window', 'auth', 'shared',
        function ($scope, $http, $location, $route, $window, auth, shared) {

            $scope.clientInfo;
            $scope.driversInfo;
            $scope.bookedTravelsInfo;
            $scope.postedTravelsInfo;
            $scope.bookedTaxisInfo;
            $scope.postedTaxisInfo;
            $scope.ready = false;
            $scope.isAuth;
            $scope.selectedCar;
            $scope.isArray = false;
            $scope.closeTravels;
            $scope.changePass = false;
            $scope.showModal;
            $scope.mod;
            $scope.history;

            $scope.show = [true, false, false];
            var myDriverIds = [];
            var self = $location.search();


            var isArray = function (what) {
                return Object.prototype.toString.call(what) === '[object Array]';
            };

            $scope.getDay = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(0, splitter);
            };

            $scope.getTime = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(splitter + 1, splitter + 6);
            };

            $scope.go = function (data, int) {
                shared.setTravelInfo(data);
                //sessionStorage.setItem('number&type',data.travel_id + '_' + type);
                $location.path("/detail");
                $location.url($location.path());
                $location.search("number", data.travel_id);
                $location.search("type", int);
                $route.reload();
            };

            $scope.check = function () {
                auth.checkAuth(sessionStorage.getItem("conpCookie")).then(function (promise) {
                    if (promise.status == 200)
                        $scope.isAuth = true;
                    else
                        $scope.isAuth = false;

                    if ($scope.isAuth == false) {
                        alert("Per favore, effettua il login");
                        $location.path('/login');
                        $location.url($location.path());

                        return;
                    }
                });
            };

            $scope.tab = function (data) {
                if (data == "self") {
                    $scope.show[0] = true, $scope.show[1] = false;
                    $scope.show[2] = false;
                }
                ;
                if (data == "cars") {
                    $scope.show[0] = false, $scope.show[1] = true;
                    $scope.show[2] = false;
                }
                ;
                if (data == "feed") {
                    $scope.show[0] = false, $scope.show[1] = false;
                    $scope.show[2] = true;
                }
                ;

            };

            $scope.carInfo = function (data) {
                var item;
                var i;
                for (i = 0; i < $scope.driversInfo.length; i++) {
                    item = $scope.driversInfo[i];
                    //  console.log(item);
                    // console.log("driver_id "+item.driver_id +" is equal to "+ data +  " ?");
                    if (item.driver_id == data) {
                        $scope.selectedCar = item;
                        // console.log("data is ");
                        // console.log($scope.selectedCar);
                    }
                    ;

                }
                // console.log("selected car " + $scope.selectedCar.carModel);
                //  $window.location.reload();

            };

            $scope.onLoad = function () {
                $scope.check();

                if (self.email === undefined)
                    self.email = sessionStorage.getItem('email');

                shared.getClient(self.email).then(function (promise) {
                    var prova = shared.getClientInfo();
                    $scope.clientInfo = prova.return;
                    console.log($scope.clientInfo);

                    $scope.driversInfo = $scope.clientInfo.drivers;
                    shared.setCars($scope.driversInfo);

                    if (!isArray($scope.driversInfo)) {
                        $scope.selectedCar = $scope.driversInfo;
                        myDriverIds[0] = $scope.driversInfo.driver_id;
                        // console.log('isSingleEl');
                    }
                    else {
                        $scope.selectedCar = $scope.driversInfo[0];
                        $scope.isArray = true;
                        var k;
                        for (k = 0; k < $scope.driversInfo.length; k++) {
                            myDriverIds[k] = $scope.driversInfo[k].driver_id;

                        }
                        ;
                        // console.log('isArray');                       
                    }
                    ;

                    /*      if ($location.search().mode==="offer") {
                     $('#modalPost').modal('show');
                     }
                     */


                    $scope.showPosted = false;
                    if (jQuery.isEmptyObject($scope.clientInfo.postedTravels) == false) {
                        if (!isArray($scope.clientInfo.postedTravels))
                            $scope.postedTravelsInfo = [$scope.clientInfo.postedTravels];
                        else
                            $scope.postedTravelsInfo = $scope.clientInfo.postedTravels;
                        checkCloseTravels($scope.postedTravelsInfo);
                        $scope.showPosted = true;
                    }
                    if (jQuery.isEmptyObject($scope.clientInfo.postedTaxis) == false) {
                        if (!isArray($scope.clientInfo.postedTaxis))
                            $scope.postedTaxisInfo = [$scope.clientInfo.postedTaxis];
                        else
                            $scope.postedTaxisInfo = $scope.clientInfo.postedTaxis;
                        checkCloseTravels($scope.postedTaxisInfo);
                        $scope.showPosted = true;
                    }

                    $scope.showBooked = false;
                    if (jQuery.isEmptyObject($scope.clientInfo.bookedTravels) == false) {
                        if (!isArray($scope.clientInfo.bookedTravels))
                            $scope.bookedTravelsInfo = [$scope.clientInfo.bookedTravels];
                        else
                            $scope.bookedTravelsInfo = $scope.clientInfo.bookedTravels;
                        checkCloseTravels($scope.bookedTravelsInfo);
                        $scope.showBooked = true;
                    }
                    if (jQuery.isEmptyObject($scope.clientInfo.bookedTaxis) == false) {
                        if (!isArray($scope.clientInfo.bookedTaxis))
                            $scope.bookedTaxisInfo = [$scope.clientInfo.bookedTaxis];
                        else
                            $scope.bookedTaxisInfo = $scope.clientInfo.bookedTaxis;
                        checkCloseTravels($scope.bookedTaxisInfo);
                        $scope.showBooked = true;
                    }

                    $scope.ready = true;
                });

                var checkCloseTravels = function (item) {
                    var today = new Date();
                    var obj;
                    if (jQuery.isEmptyObject(item))
                        return;

                    var i;
                    for (i = 0; i < item.length; i++) {
                        var data = $scope.getDay(item[i].data) + 'T' + $scope.getTime(item[i].time) + ':00';
                        var travelDataCompleta = new Date(data);
                        var timeDiff = (today.getTime() - travelDataCompleta.getTime()) / 1000 / 60 / 24;
                        if (timeDiff <= 2)
                            $scope.closeTravels = true;
                    }


                    console.log("close travels = " + $scope.closeTravels);
                };
                $scope.getLatestComment();
            };

            $scope.getLatestComment = function () {
                if (self.email === undefined)
                    self.email = sessionStorage.getItem('email');
                shared.getLatestReceivedComments(self.email, 10).then(function (promise) {
                    var prova = shared.getComments();
                    $scope.commentInfo = prova;
                });
            };

            $scope.goHistory = function () {
                $location.path("/history");
            };

            $scope.alert = function () {
                $scope.modalInfo = "Aggiungere una macchina al tuo profilo ti permetterà di offrire viaggi con quella macchina. \n\
                     Se non l'hai mai fatto, inizia subito per poter offrire un passaggio!";
            };

            $scope.edit = function (input) {
                if (self.email === undefined)
                    self.email = sessionStorage.getItem('email');
                input.email = self.email;
                shared.editClient(input).then(function (promise) {

                    $scope.modalInfo = shared.getData();

                    //manca un avviso all'utente
                    $location.path('/account');
                });
            };

            $scope.addCar = function (input) {
                if (self.email === undefined)
                    self.email = sessionStorage.getItem('email');
                input.email = self.email;
                shared.createDriver(input).then(function (promise) {
                });
            };

        }]);


    modAccount.directive('modalDirective', function () {
        return{
            restrict: 'A',
            link: function (scope, element, attrs) {
                scope.$watch(attrs.modalDirective, function (value) {
                    if (value)
                        element.modal('show');
                    else
                        element.modal('hide');
                });
            }

        };
    });

})();
