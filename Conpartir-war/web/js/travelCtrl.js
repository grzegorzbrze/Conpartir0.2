/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
  'use strict';

  var modTravel = angular.module('travelModule', ['ngRoute']);
        modTravel.controller('TravelController', ['$scope', '$http',
        function($scope,$http) {
            
            $scope.SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
            $scope.travelList;
            $scope.answer;
            var x2js = new X2JS();
            
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
                           alert( xmlhttp.responseText);
                           $scope.answer= xmlhttp.responseText;
                           
                           var jsonObj = x2js.xml_str2json( $scope.answer );
                           alert(jsonObj.return);
                           // alert('done. use firebug/console to see network response');
                        }
                    }
                };
                
                var sr;
                var action;
                if (data.when != null) {
                    sr = SOAPhead +
                            '<ns0:getTravelsFrom xmlns:ns0="http://SOAPServer/">' +
                            '<start>'+ data.from +'</start>' +
                            '<end>'+ data.to +'</end>' +
                            '<date>'+ data.when +'</date>' +
                            '</ns0:getTravelsFrom>'+
                            SOAPtail; 
                    action = '"' + "http://SOAPServer" + "/getTravelsFrom" + '"' ;
                    
                }
                else {
                    sr = SOAPhead +
                            '<ns0:getTravels xmlns:ns0="http://SOAPServer/">' +
                            '<start>'+ data.from +'</start>' +
                            '<end>'+ data.to +'</end>' +
                            '</ns0:getTravels>'+
                            SOAPtail;
                    action = '"' + "http://SOAPServer" + "/getTravels" + '"' ;
                    
                }
                 
            // Send the POST request      
            
            
            xmlhttp.setRequestHeader('Content-Type', 'text/xml');
            xmlhttp.setRequestHeader('SOAPAction',action);           
            xmlhttp.send(sr);
            // send request
            // ...
        
            };
            
          
     
        }]);

})();
