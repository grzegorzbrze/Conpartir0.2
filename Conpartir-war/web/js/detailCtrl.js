(function () {
  'use strict';

  var modDetail = angular.module('detailModule', ['ngRoute']);
        modDetail.controller('DetailController', ['$scope', '$http', '$route', '$routeParams','$location','$timeout' , 'shared',
        function($scope,$http, $route, $routeParams, $location, $timeout, shared) {
            
            $scope.SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
            $scope.travelList;
            $scope.answer;
            $scope.driver;
            var x2js = new X2JS();
            
            $scope.getInfo = function () {
                var thisObjParam = $location.search();
               $scope.getDriver(thisObjParam.number);
               //$timeout( $scope.reload() , 40);
                console.log("check 2 result "+ $scope.driver);
                
            };

            //Header e footer di una richiesta Soap
            var SOAPhead = '<?xml version="1.0" encoding="utf-8"?>' +                        
                           '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+                        
                           '<soap:Body>';
            var SOAPtail =  '</soap:Body>' +
                            '</soap:Envelope>';
                         
            
             $scope.reload = function () {
                  $route.reload();
                  alert(shared.getData());
              };
              
            $scope.getDriver = function (data) {
                var xmlhttp = new XMLHttpRequest();                  
                xmlhttp.open('POST', $scope.SOAPbase, true); 
                xmlhttp.onreadystatechange = function () {
                    if (xmlhttp.readyState == 4) {
                        if (xmlhttp.status == 200){  
                             $scope.answer = xmlhttp.responseText;
                            var jsonObj = x2js.xml_str2json($scope.answer );
                            console.log("Check: jsonObj is " + jsonObj);
                            
                         $scope.$apply(function () { 
                                $scope.driver = jsonObj.Envelope.Body.getDriverFromTravelResponse.return;
                                console.log("item is " +  jsonObj.Envelope.Body.getDriverFromTravelResponse.return);                                
                                console.log("item is " +  jsonObj.Envelope.Body.getDriverFromTravelResponse.return[0].email);
                                shared.setData(jsonObj.Envelope.Body.getDriverFromTravelResponse.return[0]);
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
