(function () {
  'use strict';

  var modDetail = angular.module('detailModule', ['ngRoute']);
        modDetail.controller('DetailController', ['$scope', '$http', '$route', '$routeParams','$location','$timeout' , 'shared',
        function($scope,$http, $route, $routeParams, $location, $timeout, shared) {
            
            $scope.SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
            $scope.travelList;
            $scope.lista;
            $scope.nome;
            $scope.email;
            
            
            $scope.prova;
            var x2js = new X2JS();
            
            $scope.getInfo = function () {
                var thisObjParam = $location.search();
              
                $scope.getDriver(thisObjParam.number);
                
                               
                                
                $scope.prova = shared.getData();
                console.log("after shared prova is " + $scope.prova);
               // $scope.email = shared.getData().return[0].email;
              /*  $scope.nome = $scope.prova.return[0].nome;
              
                $scope.lista.email = $scope.prova.return[0].email;
                $scope.lista.nome = $scope.prova.return[0].nome; */
              
            };

            //Header e footer di una richiesta Soap
            var SOAPhead = '<?xml version="1.0" encoding="utf-8"?>' +                        
                           '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+                        
                           '<soap:Body>';
            var SOAPtail =  '</soap:Body>' +
                            '</soap:Envelope>';
                         
            
             $scope.reload = function () {
                  $route.reload();
                 
              };
              
            $scope.getDriver = function (data) {
                var xmlhttp = new XMLHttpRequest();                  
                xmlhttp.open('POST', $scope.SOAPbase, true); 
                xmlhttp.onreadystatechange = function () {
                    if (xmlhttp.readyState == 4) {
                        if (xmlhttp.status == 200){  
                            var answer = xmlhttp.responseText;
                            var jsonObj = x2js.xml_str2json(answer );
                            //console.log("Check: jsonObj is " + jsonObj);
                            
                         $scope.$apply(function () { 
                                answer = jsonObj.Envelope.Body.getDriverFromTravelResponse;
                                //console.log("item is " +  jsonObj.Envelope.Body.getDriverFromTravelResponse.return);                                
                                //console.log("item is " +  jsonObj.Envelope.Body.getDriverFromTravelResponse.return[0].email);
                                shared.setData(answer.return[0].name, answer.return[0].surname, answer.return[0].email, answer.return[1].carModel);
                                console.log(answer);
                                console.log(answer.return[0]);
                            });
                            }
                        }
                    }; 
                    var sr;
                    var action;
                    var opName;
                    opName = "getDriverFromTravel";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<travelID>'+ data+'</travelID>' +
                            '</ns0:' + opName + '>'+
                            SOAPtail; 
                    action = '"' + "http://SOAPServer" + "/" + opName + '"' ; 
                    xmlhttp.setRequestHeader('Content-Type', 'text/xml');
                    xmlhttp.setRequestHeader('SOAPAction',action);
                    xmlhttp.send(sr);
                };
          
     
        }]);
    
    

})();
