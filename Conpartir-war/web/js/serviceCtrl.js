(function () {
  'use strict';

var modService = angular.module('serviceModule', ['ngRoute']);

    //Factory per dati condivisi
   modService.factory('shared', [ '$http','$location' ,'auth',
   function ($http, $location, auth) {
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
          
            
            getClient: function (email) {                
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "getClient";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<email>'+ email +'</email>' +
                           '</ns0:' + opName + '>'+
                           SOAPtail; 
                action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                
                promise = $http.post(SOAPbase, sr, { "headers": {
                        'Content-Type' : "text/xml;charset=utf-8",
                        'SOAPAction': action
                    }                  
                })
                        .success(function (data, status, headers, config) {
                            var jsonObj = x2js.xml_str2json( data );
                    res = jsonObj.Envelope.Body.getClientResponse;
                    //console.log("oggetto ottenuto = " );
                    //console.log(res);
                    delete res["_xmlns:ns2"];
                    delete res["__prefix"];
                    obj = res;           
                    return res;
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;                     
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
                                 console.log("read obj "+jsonObj);
                   
                    if (input.when != null) { 
                                    res = jsonObj.Envelope.Body.getTravelsFromResponse;
                                  //  console.log("oggetto ottenuto = " );
                                  //  console.log(res);
                                   // obj = res;
                                   // return obj;
                                    
                                }
                                else {
                                    res = jsonObj.Envelope.Body.getTravelsResponse; 
                                   //console.log("oggetto ottenuto = " );
                                   // console.log(res);
                                   // obj = res;
                                   // return obj;
                                }
                                  delete res["_xmlns:ns2"];
                                  delete res["__prefix"];
                                  
                            
                                obj = res;
                                
                                
                                //return res;
                     })
                             .error(function (data, status, headers, config) {
                                 return {"status": false};
                     });
                     
                     return promise;
            }, 
            
            getDrivers: function (email) {                
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "getDrivers";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<clientEmail>'+ email +'</clientEmail>' +
                           '</ns0:' + opName + '>'+
                           SOAPtail; 
                action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                
                promise = $http.post(SOAPbase, sr, { "headers": {
                        'Content-Type' : "text/xml;charset=utf-8",
                        'SOAPAction': action
                    }                  
                })
                        .success(function (data, status, headers, config) {
                            var jsonObj = x2js.xml_str2json( data );
                    res = jsonObj.Envelope.Body.getDriversResponse;
                    console.log("oggetto ottenuto = " );
                    console.log(res);
                    delete res["_xmlns:ns2"];
                    delete res["__prefix"];
                    obj = res;           
                    return res;
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;                     
            },
            
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
