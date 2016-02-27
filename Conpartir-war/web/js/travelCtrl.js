/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modTravel = angular.module('travelModule', ['ngRoute']);
        modTravel.controller('TravelController', ['$scope', '$http', '$soap',
        function($scope,$http,$soap) {
            
            $scope.SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
            
            //Metodo che prende il WSDL (descrittore di servizio) del SOAPService. 
            //Da invocare ogni volta che viene fatta una richiesta SOAP
            //TODO: Mettere il metodo in un service a parte
            var getWSDL = function () {  
                var xml = new XMLHttpRequest();
                xml.open('GET', "http://localhost:8080/Conpartir-war/SOAPServiceClient?wsdl", true); 
                 var s =
                        '<?xml version="1.0" encoding="utf-8"?>' +
                        
                        '<soap:Envelope ' + 
                        
                        'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ' + 
                        'xmlns:xsd="http://www.w3.org/2001/XMLSchema" ' +
                        'xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" '+
                        '>' +
                        
                        '<soap:Body>' +
                            
                        '</soap:Body>' +
                        '</soap:Envelope>';
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
            
            $scope.search = function(data) {
                $scope.query = data;
                //alert($scope.query.from + $scope.query.to);
                
                $scope.SOAPreq3(data);
            };
                     
          
            
            
     

            // build SOAP request
            $scope.SOAPreq3 = function (data) { 
                
               getWSDL();                
                
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open('POST', $scope.SOAPbase, true);     
                var sr = '<?xml version="1.0" encoding="utf-8"?>' +
                        '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+
                        '<soap:Body>' +
                        '<ns0:getClient xmlns:ns0="http://SOAPServer/">' +
                        '<email>'+ data +'</email>' +
                        '</ns0:getClient>'+
                        '</soap:Body>' +
                        '</soap:Envelope>';
                        
                       /* '<?xml version="1.0" encoding="utf-8"?>' +
                        
                        '<soap:Envelope ' + 
                        
                        'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ' + 
                        'xmlns:xsd="http://www.w3.org/2001/XMLSchema" ' +
                        'xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" '+
                        '>' +
                        
                        '<soap:Body xmlns:m="http://SOAPServer/">' +
                            '<m:getClient>' +
                                '<m:email xsi:type="xsd:string">mario.rossi@gmail.com</m:email>' +                    
                            '</m:getClient>' +
                        '</soap:Body>' +
                        '</soap:Envelope>'; */
                
                xmlhttp.onreadystatechange = function () {
                    if (xmlhttp.readyState == 4) {
                        if (xmlhttp.status == 200) {
                           // alert('done. use firebug/console to see network response');
                        }
                    }
                };
                
            // Send the POST request      
            
            var action = '"' + "http://SOAPServer" + "/getClient" + '"' ;
            xmlhttp.setRequestHeader('Content-Type', 'text/xml');
            xmlhttp.setRequestHeader('SOAPAction',action);           
            xmlhttp.send(sr);
            // send request
            // ...
        
                    
            };
            
          
     
        }]);

})();
