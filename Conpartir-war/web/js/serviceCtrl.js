(function () {
  'use strict';

var modService = angular.module('serviceModule', ['ngRoute']);

    //Factory per dati condivisi
   modService.factory('shared', [ '$http',
   function ($http) {
        var data;
        var obj = {};
        
        //variabili per le richieste SOAP
        var SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
        var SOAPhead = '<?xml version="1.0" encoding="utf-8"?>' +   
                '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+
                '<soap:Body>';
        var SOAPtail =  '</soap:Body>' +
                '</soap:Envelope>';
        var x2js = new X2JS();
         
        return {
            
            //Metodo che prende il WSDL (descrittore di servizio) del SOAPService. 
            //Da invocare ogni volta che viene fatta una richiesta SOAP
            getWSDL: function () {                
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
            
            },
            
            getTravels: function (input) {
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                if (input.when != null) {
                    opName = "getTravelsFrom";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<start>'+ input.from +'</start>' +
                            '<end>'+ input.to +'</end>' +
                            '<date>'+ input.when +'</date>' +
                            '</ns0:' + opName + '>'+
                            SOAPtail; 
                    action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                    
                }
                else {
                    opName = "getTravels";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<start>'+ input.from +'</start>' +
                            '<end>'+ input.to +'</end>' +
                            '</ns0:' + opName + '>'+
                            SOAPtail;
                    action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                    
                }
                
                promise = $http.post(SOAPbase, sr, { "headers": {
                         'Content-Type' : "text/xml;charset=utf-8",
                         'SOAPAction': action
                     }                  
                     })
                             .success(function (data, status, headers, config) {
                                 var jsonObj = x2js.xml_str2json( data );
                    
                    if (input.when != null) { 
                                    res = jsonObj.Envelope.Body.getTravelsFromResponse;
                                    console.log("oggetto ottenuto = " );
                                    console.log(res);
                                   // obj = res;
                                   // return obj;
                                    
                                }
                                else {
                                    res = jsonObj.Envelope.Body.getTravelsResponse; 
                                     console.log("oggetto ottenuto = " );
                                    console.log(res);
                                   // obj = res;
                                   // return obj;
                                }
                                 
                                obj = res; 
                                return res;
                     })
                             .error(function (data, status, headers, config) {
                                 return {"status": false};
                     });
                     
                     return promise;
  
                
                
           
            }, /*    .then(function successCallback(response) {
                    // this callback will be called asynchronously
                    //     // when the response is available
                     
                           
                           var jsonObj = x2js.xml_str2json( response );
                    
                    if (data.when != null) { 
                                    res = jsonObj.Envelope.Body.getTravelsFromResponse.return;
                                    console.log("oggetto ottenuto = " );
                                    console.log(res);
                                    obj = res;
                                    return obj;
                                    
                                }
                                else {
                                    res = jsonObj.Envelope.Body.getTravelsResponse.return; 
                                     console.log("oggetto ottenuto = " );
                                    console.log(res);
                                    obj = res;
                                    return obj;
                                }
                 }, function errorCallback(response) {
                     // called asynchronously if an error occurs
                     //     // or server returns response with an error status.
                 });
                                
                
                
                
           
            }, 
            
            
            
            getTravels2: function (data) {
                var res;
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open('POST', SOAPbase, true);  
                                
                                      
                xmlhttp.onreadystatechange = function () {
                    if (xmlhttp.readyState == 4) {
                        if (xmlhttp.status == 200) {                     
                           
                           var answer= xmlhttp.responseText;
                           
                           var jsonObj = x2js.xml_str2json( answer );
                         
                                if (data.when != null) { 
                                    res = jsonObj.Envelope.Body.getTravelsFromResponse.return;
                                    console.log("oggetto ottenuto = " );
                                    console.log(res);
                                    obj = res;
                                    return obj;
                                    
                                }
                                else {
                                    res = jsonObj.Envelope.Body.getTravelsResponse.return; 
                                     console.log("oggetto ottenuto = " );
                                    console.log(res);
                                    obj = res;
                                    return obj;
                                }
                               // console.log($scope.travelList);
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
            }, */ /*  */
            
            getData: function () {
                // console.log(obj + ' was returned as data');
                 console.log("sto passando un oggetto obj = " );
                 console.log(obj);
                return obj;
            },
            
            
            setData: function (name,surname,email,carModel) {
                // console.log('setting ' + data + ' as data');
                obj.name = name;
                obj.surname = surname;
                obj.email = email;
                obj.carModel = carModel;
            }



        };
    }]);
       
    
    

})();
