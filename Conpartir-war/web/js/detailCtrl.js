(function () {
  'use strict';

  var modDetail = angular.module('detailModule', ['ngRoute']);
        modDetail.controller('DetailController', ['$scope', '$route', '$location', 'shared', 'auth',
        function($scope, $route, $location, shared, auth) {
           
            $scope.travel;
            $scope.detail;
            $scope.isAuthorized;
            $scope.allowBooking = true;
            $scope.ifAlert = false;
            $scope.ifTimeAlert = false;
            $scope.alertMsg = "";
            $scope.isCarTravel;
            $scope.isTaxiTravel;
            
            $scope.passengerList = [];
            $scope.comment;
            var feedRate;
            var clientToFeed;
            var today= new Date ();
             
            $scope.getDay = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(0,splitter);                
            };
            
            $scope.getTime = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(splitter+1,splitter+6);                
            };
            
            $scope.checkAuth = function () {    
                auth.checkAuth(sessionStorage.getItem("conpCookie")).then(function (promise){
                    if (promise.status===200)   { 
                        $scope.isAuthorized = true; 
                        $scope.allowBooking = true;
                    }
                    else  { 
                        $scope.isAuthorized=false;
                        $scope.allowBooking = false;    
                    }     
                });
            };
    
                        
            $scope.getInfo = function () {
                var travelIdParam = $location.search().number;
                var type = $location.search().type;
                
                //controllo che l'utente sia loggato 
                $scope.checkAuth();   
                if(type===1) $scope.isCarTravel = true;
                if(type===2) $scope.isTaxiTravel = true;
                
                $scope.travel = shared.getTravelInfo();
                //gli If seguenti ricaricano i dati del travel dalla SOAP nel caso non fossero presenti in shared.getTravelInfo
                //Questo può accadere se per esempio si ritorna a questa pagina dopo che la si è abbandonata
                if($scope.isCarTravel === true) {
                    if (jQuery.isEmptyObject($scope.travel)) {                     
                        shared.getSpecificCarTravel( travelIdParam).then(function (promise) {
                            $scope.travel = shared.getTravelInfo().return;
                        });
                    };
                    shared.getPassengersTravel(travelIdParam).then(function(promise){
                        $scope.detail = shared.getPassengersObject().return; 
                    
                        if (isArray($scope.detail.passengers)=== true) {
                            $scope.passengerList = $scope.detail.passengers;
                        }
                        else  $scope.passengerList[0] = $scope.detail.passengers;
                    
                        var i;
                        for (i=0;i<$scope.passengerList.length;i++) {
                            if ($scope.passengerList[i].email === $scope.detail.driverInfo.email ) {
                                $scope.passengerList[i].role = "Guidatore";
                            }
                            if ($scope.passengerList[i].email !== $scope.detail.driverInfo.email ) {
                                $scope.passengerList[i].role = "Passeggero";
                            }
                        if ($scope.isTaxiTravel === true && $scope.passengerList[i].email === $scope.detail.driverInfo.email ) {
                            $scope.passengerList[i].role = "Creatore Taxi";
                            console.log("taxi da aggiungere!");
                        }
                        if ($scope.isTaxiTravel === true && $scope.passengerList[i].email !== $scope.detail.driverInfo.email ) {
                            $scope.passengerList[i].role = "Passeggero Taxi";
                            console.log("taxi da aggiungere!");
                        }
                    };
                });
                    
                }   
                if ($scope.isTaxiTravel === true) {
                    if (jQuery.isEmptyObject($scope.travel)) {                     
                        shared.getSpecificTaxiTravel(travelIdParam).then(function (promise) {
                            $scope.travel = shared.getTravelInfo().return;
                        });                     
                    };
                    shared.getPassengersTaxi(travelIdParam).then(function(promise){
                        $scope.detail = shared.getPassengersObject().return; 
                    
                        if (isArray($scope.detail.passengers)=== true) {
                            $scope.passengerList = $scope.detail.passengers;
                        }
                        else  $scope.passengerList[0] = $scope.detail.passengers;
                    
                        var i;
                        for (i=0;i<$scope.passengerList.length;i++) {
                            
                            if ($scope.passengerList[i].email === $scope.detail.driverInfo.email ) {
                                $scope.passengerList[i].role = "Creatore Taxi";
                                console.log("taxi da aggiungere!");
                            }
                            if ($scope.passengerList[i].email !== $scope.detail.driverInfo.email ) {
                                $scope.passengerList[i].role = "Passeggero Taxi";
                                console.log("taxi da aggiungere!");
                            }
                        };
                    });                    
                }
                                 
                // A questo punto sono sicuro di avere tutti i dati
                            
                var data = $scope.getDay($scope.travel.data) + 'T' + $scope.getTime($scope.travel.time) + ':00';
                var travelDataCompleta = new Date(data);
                
                if (today > travelDataCompleta) {
                    $scope.allowBooking = false;
                    $scope.alertMsg = "Non puoi prenotare un viaggio avvenuto nel passato ...";
                    $scope.ifAlert = true;
                }
                else {
                    var timeDiff = (travelDataCompleta.getTime() - today.getTime())/1000/60;      
                    if (timeDiff <= 30 &&  $scope.isCarTravel===true){
                        $scope.ifTimeAlert = true;
                        $scope.allowBooking = false;
                        console.log("meno di trenta minuti al viaggio");
                    } 
                    if (timeDiff <= 15 &&  $scope.isTaxiTravel===true){
                        $scope.ifTimeAlert = true;                     
                        $scope.allowBooking = false;
                        console.log("meno di quindici minuti al viaggio");
                    }                
                };                
            };
           
            $scope.book = function () {
                var input = {};
                input.travelId = $scope.travel.travel_id;
                input.email = sessionStorage.getItem("email");
                
                if (input.email === $scope.detail.driverInfo.email) {  
                    alert('Non puoi prenotare un tuo stesso viaggio');
                    return;
                };                    
                shared.bookTravel(input).then(function (promise) {
                    if (promise.status === 200) {
                        $scope.alert = "Prenotazione effettuata con successo!";
                    }
                    else {
                        $scope.alert = "Qualcosa è andato storto con la prenotazione.";
                    }
                });                  
            };
            
             $scope.rate = function (number){
                feedRate = number;
            };
            
            $scope.selectClient = function (client) {
                clientToFeed = client.email;
            };
                       
            // Manca il controllo per evitare che estranei al viaggio lascino un feed
            $scope.sendFeed = function () {
        
                var input = {};
               
                input.author_email = sessionStorage.getItem("email");;
                input.clientJudged_email = clientToFeed ;
                input.comment = $scope.comment;
                input.feedback = feedRate;
                if ($scope.isCarTravel === true){  input.travel_id = $scope.travel.travel_id; }
                else input.travel_id = $scope.travel.taxi_id; 
                input.when = today;
                
                
                console.log (input);
                 if (sessionStorage.getItem("email") === input.clientJudged_email) { alert( "Non puoi lasciare un feedback a te stesso!"); }
                 else{                shared.createComment(input);};
            };
            
            
            $scope.reload = function () {
                $route.reload();
              };
              
            var isArray = function(what) {              
                return Object.prototype.toString.call(what) === '[object Array]';
            };
     
        }]);
    
})();
