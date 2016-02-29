/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modTravel = angular.module('travelModule', ['ngRoute']);
        modTravel.controller('TravelController', ['$scope', '$http', '$route', '$routeParams','$location',
        function($scope,$http, $route, $routeParams, $location) {
            
            $scope.SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
            $scope.travelList;
            $scope.relatedDrivers;
            $scope.answer;
            $scope.showHead = false;
            var x2js = new X2JS();
            
            $scope.go = function (data) {
                $location.path("/detail");
                $location.search(data);
                $route.reload();
            };
            
            $scope.getDay = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(0,splitter);                
            };
            
             $scope.getTime = function (data) {
                var splitter = data.indexOf('T');
                return data.slice(splitter+1,splitter+6);
                
            };
            
            //Header e footer di una richiesta Soap
            var SOAPhead = '<?xml version="1.0" encoding="utf-8"?>' +                        
                           '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+                        
                           '<soap:Body>';
            var SOAPtail =  '</soap:Body>' +
                            '</soap:Envelope>';
              
            
            //Metodo che prende il WSDL (descrittore di servizio) del SOAPService. 
            //Da invocare ogni volta che viene fatta una richiesta SOAP
            //TODO: Mettere il metodo in un service a parte
            var getWSDL = function () {  
                var xml = new XMLHttpRequest();
                xml.open('GET', "http://localhost:8080/Conpartir-war/SOAPServiceClient?wsdl", true); 
                 var s = SOAPhead + SOAPtail;
                       
                xml.onreadystatechange = function () {
                   /* if (xmlhttp.readyState == 4) {
                        if (xmlhttp.status == 200) {
                            alert('done. use firebug/console to see network response');
                        }
                    }*/
                }; 
                xml.setRequestHeader('Content-Type', 'text/xml');
                xml.send(s);                
            };
            
             $scope.reload = function () {
                  $route.reload();
              };
            
            $scope.getInfo = function () {
                var thisObjParam = $location.search();
                var thisObj;
                var item;
                
                for (item in $scope.travelList.return) {
                    if (item.$$hashkey == thisObjParam) {
                        thisObj = item;
                    } ;
                };
                
            };
            
            $scope.search = function(data) {
                              
                $scope.SOAPtravels(data);
            };                
          
            
            
     

            // build SOAP request
            $scope.SOAPtravels = function (data) { 
                
               getWSDL();                
                
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open('POST', $scope.SOAPbase, true);  
                                
                                      
                xmlhttp.onreadystatechange = function () {
                    if (xmlhttp.readyState == 4) {
                        if (xmlhttp.status == 200) {                     
                           
                           $scope.answer= xmlhttp.responseText;
                           
                           var jsonObj = x2js.xml_str2json( $scope.answer );
                            //console.log(jsonObj);
                            $scope.$apply(function () {
                                $scope.travelList = jsonObj.Envelope.Body.getTravelsResponse;
                                console.log($scope.travelList);  
                               // console.log($scope.travelList.return[0].data);
                                $scope.getDrivers();
                                $scope.showHead = true;
                            });
                        }
                    }
                };
                
                var sr;
                var action;
                var opName;
                if (data.when != null) {
                    opName = "getTravelsFrom";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<start>'+ data.from +'</start>' +
                            '<end>'+ data.to +'</end>' +
                            '<date>'+ data.when +'</date>' +
                            '</ns0:' + opName + '>'+
                            SOAPtail; 
                    action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                    
                }
                else {
                    opName = "getTravels";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<start>'+ data.from +'</start>' +
                            '<end>'+ data.to +'</end>' +
                            '</ns0:' + opName + '>'+
                            SOAPtail;
                    action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                    
                }
                 
            // Send the POST request                 
            
            xmlhttp.setRequestHeader('Content-Type', 'text/xml');
            xmlhttp.setRequestHeader('SOAPAction',action);           
            xmlhttp.send(sr);
            // send request
            // ...
        
            };
            
            $scope.getDrivers = function () {
                var item;
                var i = 0;
                for (item in $scope.travelList) {
                    console.log("driver id cercato " + item);
                                       
                    var xmlhttp = new XMLHttpRequest();              
                    xmlhttp.open('POST', $scope.SOAPbase, true);  
                                
                                      
                    xmlhttp.onreadystatechange = function () {
                     if (xmlhttp.readyState == 4) {
                            if (xmlhttp.status == 200){                                
                                var jsonObj = x2js.xml_str2json(xmlhttp.responseText);  
                                                            
                                    $scope.relatedDrivers = jsonObj.Envelope.Body.getDriverFromTravelResponse; 
                                   
                                   console.log("check 1" + $scope.relatedDrivers.return[0].age);
                                    
                                  // //  console.log("check 1" + $scope.relatedDrivers.return[1].gender); 
                               //  $scope.$apply(function () { });
                                   // $scope.travelList.return[i].driver = jsonObj.Envelope.Body.getDriverFromTravelResponse;
                                
                            }
                        }
                    }; 
                    var sr;
                    var action;
                    var opName;
                    opName = "getDriverFromTravel";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<travelID>'+ item.return[i].travel_id +'</travelID>' +
                            '</ns0:' + opName + '>'+
                            SOAPtail; 
                    action = '"' + "http://SOAPServer" + "/" + opName + '"' ;   
                    xmlhttp.setRequestHeader('Content-Type', 'text/xml');
                    xmlhttp.setRequestHeader('SOAPAction',action);
                    xmlhttp.send(sr);
                    // send request
                    
                    i++;
                };             
                
                console.log( "check 2" +$scope.relatedDrivers);
            };
          
     
        }]);

})();
