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
            $scope.postedTravels;
            $scope.bookedTravels;
            $scope.bookedTaxis;
            $scope.postedTaxis;
            
            $scope.showNextTravels;
            $scope.showPastTravels;
            $scope.gmailValue;
            $scope.twitterValue;
            
            $scope.ready = false;
            $scope.isAuth;
            $scope.isGmail;
            $scope.isTwitter;
            $scope.selectedCar;
            $scope.isArray = false;
            $scope.closeTravels;
            $scope.changePass = false;
            $scope.showModal;
            $scope.mod;
            $scope.history;
            $scope.show = [true, false, false];
            $scope.commentAlert;
            
            var myDriverIds = [];
            var self = $location.search();
            var today = new Date();
            
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
                if(int === 1) { $location.search("number", data.travel_id); }
                else $location.search("number", data.taxi_id);
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
                    if (item.driver_id == data) {
                        $scope.selectedCar = item;
                       };
                   }
                // console.log("selected car " + $scope.selectedCar.carModel);
                //  $window.location.reload();
            };
             
            // Carica i primi contenuti necessari alla pagina, tra cui info sull'utente
            // sulle macchine, i feedback, i viaggi
            // *___________LOADING&TRAVELS

            $scope.onLoad = function () {
                $scope.check();

                if (self.email === undefined)
                    self.email = sessionStorage.getItem('email');
             
                
                shared.getClient(self.email).then(function (promise) {
                    var prova = shared.getClientInfo();
                    $scope.clientInfo = prova.return;
                    console.log($scope.clientInfo);
                    
                    if($scope.clientInfo.gmail==="false") $scope.isGmail = false;
                    if($scope.clientInfo.gmail==="true") $scope.isGmail = true;                    
                    
                    if($scope.clientInfo.twitter==="false") $scope.isTwitter = false;
                    if($scope.clientInfo.twitter==="true") $scope.isTwitter = true;

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

                        };
                        // console.log('isArray');                       
                    };
                
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
                        //checkCloseTravels($scope.bookedTaxisInfo);
                        $scope.showBooked = true;
                    }
                    
                    $scope.postedTravels = $scope.postedTravelsInfo;
                    $scope.bookedTravels = $scope.bookedTravelsInfo;
                    $scope.bookedTaxis = $scope.bookedTaxisInfo;
                    $scope.postedTaxis = $scope.postedTaxisInfo;
                    
                    
                    $scope.ready = true;
                });
                              

                var checkCloseTravels = function (item) {
                /*    var today = new Date();
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
                    
                    console.log("close travels = " + $scope.closeTravels); */
                }; 
                $scope.getLatestComment();
            };
            
             // Cambia i viaggi mostrati, mostrando passati e/o prossimi a seconda della scelta dell'utente
             // data dai valori showNextTravels e showPastTravels
            $scope.changeTravelsView= function () {
                                
                $scope.postedTravels = [];
                $scope.bookedTravels = [];
                $scope.bookedTaxis = [];
                $scope.postedTaxis = [];            
                
                $scope.postedTravels = checkDate($scope.postedTravelsInfo,"all");
                $scope.bookedTravels = checkDate($scope.bookedTravelsInfo,"all");
                $scope.bookedTaxis = checkDate($scope.bookedTaxisInfo,"all");
                $scope.postedTaxis = checkDate($scope.postedTaxisInfo,"all");
            }; 
            
            var checkDate = function (array,mode) {
                var i;
                var retArray = [];
                
                if(jQuery.isEmptyObject(array) === false){
                    if (mode == "all"){
                        for (i=0;i<array.length;i++) {
                            
                            var date = new Date(array[i].data);
                            if ($scope.showPastTravels === true && $scope.showNextTravels === true) {
                                retArray.push(array[i]);
                            }
                            else
                            {                        
                                if ($scope.showNextTravels === true && date > today) {
                                    retArray.push(array[i]);                         
                                }
                                if ($scope.showPastTravels === true && date < today) {
                                    retArray.push(array[i]);
                                }
                            }                    
                        }  
                    }
                    if (mode=="past") {
                        for (i=0;i<array.length;i++) {
                            var date = new Date(array[i].data);
                            if (date < today) {
                                    retArray.push(array[i]);
                                }
                            }
                        }
                }
                return retArray;   
            };
            
            
            $scope.getLatestComment = function () {
                if (self.email === undefined)
                    self.email = sessionStorage.getItem('email');
                shared.getLatestReceivedComments(self.email, 10).then(function (promise) {
                    var prova = shared.getComments();
                    $scope.commentInfo = prova;
                    if (jQuery.isEmptyObject($scope.commentInfo)) $scope.commentAlert = true; 
                });
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
            
            $scope.editGmail = function (input) {
                if (self.email === undefined)self.email = sessionStorage.getItem('email');  
                input.email = self.email;
                
                var res = $scope.checkGmail();
                if (res==="ok"){ 
                    //TOGLIERE QUESTO CONTROLLO
                    /* if(input.gmailValue !== input.email && input.gmailValue !== "") {
                        alert("La mail usata per il tuo account principale è già una gmail valida. \n\
                               Non c'é bisogno di inserirne un'altra");
                    } */
                    input.gmailValue = input.email; 
                }
                
                shared.setClientGmail(input).then(function (promise) {

                    $scope.modalInfo = shared.getData();

                    //manca un avviso all'utente
                    $location.path('/account');
                });
            };
            
            $scope.gmailUnlink = function () {
                var input = {
                    email: sessionStorage.getItem('email'),
                    secondaryEmail: "gmail"
                };

                shared.deleteEmail(input).then(function (promise) {
                    $scope.isGmail = false;
                    //manca un avviso all'utente
                    $location.path('/account');
                });

            };


            $scope.editTwitter = function (input) {
                if (self.email === undefined)
                    self.email = sessionStorage.getItem('email');
                input.email = self.email;
                shared.setClientTwitter(input).then(function (promise) {

                    $scope.modalInfo = shared.getData();

                    //manca un avviso all'utente
                    $location.path('/account');
                });
            };

            $scope.twitterUnlink = function () {
                var input = {
                    email: sessionStorage.getItem('email'),
                    secondaryEmail: "twitter"
                };
                shared.deleteEmail(input).then(function (promise) {
                    $scope.isTwitter = false;
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

            $scope.checkGmail = function () {
                var emailProvider = self.email.slice(self.email.indexOf('@') + 1);
                if (emailProvider === "gmail.com") {
                    // console.log(emailProvider);
                    $scope.gmailValue = self.email;
                    return "ok";
                }
                ;
                //console.log(emailProvider);
                return "notOk";
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
