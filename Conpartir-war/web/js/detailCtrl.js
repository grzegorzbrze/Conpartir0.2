(function () {
  'use strict';

  var modDetail = angular.module('detailModule', ['ngRoute']);
        modDetail.controller('DetailController', ['$scope', '$route', '$location', 'shared', 'auth',
        function($scope, $route, $location, shared, auth) {
           
            $scope.travel;
            $scope.detail;
            $scope.allowBooking = true;
            $scope.ifAlert = false;
            $scope.ifTimeAlert = false;
            $scope.alertMsg = "";
            var isCarTravel;
            var isTaxiTravel;
             
            $scope.getDay = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(0,splitter);                
            };
            
            $scope.getTime = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(splitter+1,splitter+6);                
            };
            
             $scope.checkAuth = function () {                
                auth.checkAuth().then(function (promise) {
                   if (promise.status == 200 )  $scope.isAuthorized = true;
                    else $scope.isAuthorized = false;                    
                });
    
            };
                        
            $scope.getInfo = function () {
                var travelIdParam = $location.search().number;
                var type = $location.search().type;
                
                //controllo che l'utente sia loggato
                
                     console.log(auth.isAuthenticated);
                if (auth.isAuthenticated()===false) {
                     $scope.allowBooking = false;    
                }
                else { 
                    $scope.allowBooking = true;
                };
                
                if(type==1) isCarTravel = true;
                if(type==2) isTaxiTravel = true;
                
             //   console.log(type); console.log(isCarTravel);
                
                $scope.travel = shared.getTravelInfo();
                //console.log(jQuery.isEmptyObject($scope.travel));                 console.log(isCarTravel);
                //gli If seguenti ricaricano i dati del travel dalla SOAP nel caso non fossero presenti in shared.getTravelInfo
                //Questo può accadere se per esempio si ritorna a questa pagina dopo che la si è abbandonata
                if (jQuery.isEmptyObject($scope.travel) && isCarTravel == true) {                     
                    shared.getSpecificCarTravel( travelIdParam).then(function (promise) {
                        $scope.travel = shared.getTravelInfo().return;
                    });
                };
                if (jQuery.isEmptyObject($scope.travel) && isTaxiTravel == true) {                     
                    shared.getSpecificTaxiTravel( travelIdParam).then(function (promise) {
                         $scope.travel = shared.getTravelInfo().return;
                     });
                 };                
                shared.getDriverFromTravel( travelIdParam).then(function(promise){
                    $scope.detail = shared.getData();
                });
                // A questo punto sono sicuro di avere tutti i dati
                
                var today = new Date();                
                var data = $scope.getDay($scope.travel.data) + 'T' + $scope.getTime($scope.travel.time) + ':00';
                var travelDataCompleta = new Date(data);
                
                var timeDiff = (today.getTime() - travelDataCompleta.getTime())/1000/60;
                
                if (timeDiff <= 30 && isCarTravel===true){
                    $scope.ifTimeAlert = true;
                    $scope.allowBooking = false;
                    console.log("meno di trenta minuti al viaggio");
                } 
                 if (timeDiff <= 15 && isTaxiTravel===true){
                     $scope.ifTimeAlert = true;                     
                    $scope.allowBooking = false;
                     console.log("meno di trenta minuti al viaggio");
                 }
                
               
                
            };
            
            $scope.login = function () {
                $location.path("/login");
                $location.search("from","detail");
                $route.reload();
            },
            
            $scope.book = function () {
                var input = {};
                input.travelId = $scope.travel.travel_id;
                input.email = sessionStorage.getItem("email");
                
                if (input.email === $scope.detail.return[0].email) {  
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
            },

                        
             $scope.reload = function () {
                  $route.reload();
                 
              };
     
        }]);
    
    

})();
