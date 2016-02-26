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
            $scope.query =  {};
            
            $scope.search = function(data) {
                $scope.query = data;
                //alert($scope.query.from + $scope.query.to);
                
                $scope.SOAPreq3();
            };
            
        /*    $scope.SOAPreq = function(data) {
               
                $http({
                    method: 'POST',
                    url: $scope.SOAPbase ,
                    headers: {'Content-Type': 'text/xml'},
                    data:  data
                }).success(function (data)
                {
                    $scope.status=data;
                    alert($scope.status);
                });           
                
            }; */
            
            $scope.response = {};
            
          /*  $scope.SOAPreq2 = function () {
                var result;
                 var base = "http://SOAPServer" ;
                 var action = '"' +"getClient" + '"';
                //$soap.post($scope.SOAPbase,"getTravels", {from: "Torino", to: "Milano"});
                
               
                
                 $soap.post($scope.SOAPbase,action, {email: "mario.rossi@gmail.com"}).then(
                        function(response){
                            $scope.response = response;
                            console.log("ALERT HERE" + response);                           
                        });
                    
                
               // alert(result);
                return result;
            }; */
            
            
     

            // build SOAP request
            $scope.SOAPreq3 = function () { 
                
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
                    if (xmlhttp.readyState == 4) {
                        if (xmlhttp.status == 200) {
                            alert('done. use firebug/console to see network response');
                        }
                    }
                }; 
                xml.setRequestHeader('Content-Type', 'text/xml');
                xml.send(s);
                
                
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open('POST', $scope.SOAPbase, true);     
                var sr =
                        '<?xml version="1.0" encoding="utf-8"?>' +
                        
                        '<soap:Envelope ' + 
                        
                        'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ' + 
                        'xmlns:xsd="http://www.w3.org/2001/XMLSchema" ' +
                        'xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" '+
                        '>' +
                        
                        '<soap:Body>' +
                            '<getClient  xmlns="http://SOAPServer/">' +
                                '<email xsi:type="xsd:string">mario.rossi@gmail.com</email>' +                    
                            '</getClient>' +
                        '</soap:Body>' +
                        '</soap:Envelope>';
                xmlhttp.onreadystatechange = function () {
                    if (xmlhttp.readyState == 4) {
                        if (xmlhttp.status == 200) {
                            alert('done. use firebug/console to see network response');
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
