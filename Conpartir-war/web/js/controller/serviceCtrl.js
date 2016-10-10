(function () {
  'use strict';

var modService = angular.module('serviceModule', ['ngRoute']);

    //Factory per dati condivisi
   modService.factory('shared', [ '$http','$location' ,'auth',
   function ($http, $location, auth) {
        var data;
        var obj = {};
        var driversObj = {};
        var clientObj = {};
        var travelObj = {};
        var commentObj = {};
        var passengersObj = {};
        var historyTravelsObj = {};
        var bookedTravelsObj = {};
        
        
        //variabili per le richieste SOAP
       // var SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
       var SOAPbase = "http://conpartir03.northeurope.cloudapp.azure.com:8080/Conpartir-war/SOAPServiceClient"; 
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
                //xml.open('GET', "http://localhost:8080/Conpartir-war/SOAPServiceClient?wsdl", true); 
                xml.open('GET', "http://conpartir03.northeurope.cloudapp.azure.com:8080/Conpartir-war/SOAPServiceClient?wsdl", true);
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
            
            createCarTravel: function (input) { 
               var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "createCarTravel";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<email>'+ input.email +'</email>' +
                           '<id>'+ input.id +'</id>' +
                           '<from>' +input.from + '</from>' +
                           '<to>' +input.to +'</to>' +
                           '<when>' + input.when+'</when>' +
                           '<freeSeats>' + input.freeSeats + '</freeSeats>' +
                           '</ns0:' + opName + '>'+
                           SOAPtail; 
                action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                
                promise = $http.post(SOAPbase, sr, { "headers": {
                        'Content-Type' : "text/xml;charset=utf-8",
                        'SOAPAction': action
                    }                  
                })
                        .success(function (data, status, headers, config) {
                    
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;             
           
           },
           
            createDriver: function (input) { 
               var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "createDriver";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<clientEmail>'+ input.email +'</clientEmail>' +
                           '<carModel>'+ input.carModel +'</carModel>' +
                           '<carYear>' +input.carYear + '</carYear>' +                        
                           '</ns0:' + opName + '>'+
                           SOAPtail; 
                action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                
                promise = $http.post(SOAPbase, sr, { "headers": {
                        'Content-Type' : "text/xml;charset=utf-8",
                        'SOAPAction': action
                    }                  
                })
                        .success(function (data, status, headers, config) {
                    
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;             
           
           },
                       
            bookTravel: function (input) { 
               var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "addPassenger";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<travel_id>'+ input.travelId +'</travel_id>' +
                           '<email>'+ input.email +'</email>' +
                           '</ns0:' + opName + '>'+
                           SOAPtail; 
                action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                
                promise = $http.post(SOAPbase, sr, { "headers": {
                        'Content-Type' : "text/xml;charset=utf-8",
                        'SOAPAction': action
                    }                  
                })
                        .success(function (data, status, headers, config) {
                    
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;             
           
           },
           
           createTaxiTravel: function (input) { 
               var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "createTaxiTravel";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<email>'+ input.email +'</email>' +
                           '<origin>' +input.from + '</origin>' +
                           '<destination>' +input.to +'</destination>' +
                           '<freeSeats>' + input.freeSeats + '</freeSeats>' +
                           '<when>' + input.when+'</when>' +                           
                           '</ns0:' + opName + '>'+
                           SOAPtail; 
                action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                
                promise = $http.post(SOAPbase, sr, { "headers": {
                        'Content-Type' : "text/xml;charset=utf-8",
                        'SOAPAction': action
                    }                  
                })
                        .success(function (data, status, headers, config) {
                         
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;             
           
           },
           
            createComment: function (input) { 
               var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "createComment";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<author_email>'+ input.author_email +'</author_email>' +
                           '<clientJudged_email>'+ input.clientJudged_email +'</clientJudged_email>' +
                           '<travel_id>' +input.travel_id + '</travel_id>' + 
                           '<comment>' + input.comment + '</comment>' + 
                           '<feedback>' + input.feedback + '</feedback>' +
                           '<when>' + input.when + '</when>' +
                           '</ns0:' + opName + '>'+
                           SOAPtail; 
                action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                
                promise = $http.post(SOAPbase, sr, { "headers": {
                        'Content-Type' : "text/xml;charset=utf-8",
                        'SOAPAction': action
                    }                  
                })
                        .success(function (data, status, headers, config) {
                    
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;             
           
           },
           
            editClient: function (input) { 
                 var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "editClient";           
                sr = SOAPhead +
                        '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<email>'+ input.email +'</email>' +
                           '<name>' +input.name + '</name>' +
                           '<surname>' +input.surname +'</surname>' +
                           '<gender>' + input.gender + '</gender>' +
                           '<age>' + input.age+'</age>' +
                           '<urlPhoto>' + input.urlPhoto+'</urlPhoto>' + 
                           '<oldPass>' + input.oldPass+'</oldPass>' + 
                           '<newPass>' + input.newPass+'</newPass>' + 
                           '<gmail>' + input.gmail + '</gmail>' +
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
                            res = jsonObj.Envelope.Body.editClientResponse;
                            delete res["_xmlns:ns2"];
                            delete res["__prefix"];
                            obj = res;
                         
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;   
               
           },
           
           
           
           setClientGmail: function(input) {                
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "setGmail";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<email>'+ input.email +'</email>' +
                           '<gmailValue>'+ input.gmailValue +'</gmailValue>' +
                           '<gmail>'+ input.gmail +'</gmail>' +
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
                    res = jsonObj.Envelope.Body.setGmailResponse;
                    delete res["_xmlns:ns2"];
                    delete res["__prefix"];
                    obj = res;
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;                     
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
                    delete res["_xmlns:ns2"];
                    delete res["__prefix"];
                    clientObj = res;
                    //return res;
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;                     
            },   
            
           getPassengersTravel: function (input){
               var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "getClientsRelatedToTravel";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<travelID>'+ input +'</travelID>' +
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
                    res = jsonObj.Envelope.Body.getClientsRelatedToTravelResponse;
                    delete res["_xmlns:ns2"];
                    delete res["__prefix"];
                    passengersObj = res;
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;                   
           },
           
            getPassengersTaxi: function (input){
               var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "getClientsRelatedToTaxi";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<taxiID>'+ input +'</taxiID>' +
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
                    res = jsonObj.Envelope.Body.getClientsRelatedToTaxiResponse;
                    //console.log("oggetto ottenuto = " );
                    //console.log(res);
                    delete res["_xmlns:ns2"];
                    delete res["__prefix"];
                    //obj = res;
                    passengersObj = res;
                    //return res;
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
                opName = "getTravelsFrom";
                sr = SOAPhead +
                        '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                        '<start>'+ input.from +'</start>' +
                        '<end>'+ input.to +'</end>' +
                        '<date>'+ input.when +'</date>' +
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
                                 res = jsonObj.Envelope.Body.getTravelsFromResponse;                            
                                 delete res["_xmlns:ns2"];
                                 delete res["__prefix"];   
                                 obj = res;
                     })
                             .error(function (data, status, headers, config) {
                                 return {"status": false};
                     });
                     
                     return promise;
            }, 
            
           getTaxiTravels: function (input) {
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                //if (input.when != null) {
                    opName = "getTaxiTravelsFrom";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<from>'+ input.from +'</from>' +
                            '<to>'+ input.to +'</to>' +
                            '<dateTime>'+ input.when +'</dateTime>' +
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
                                 res = jsonObj.Envelope.Body.getTaxiTravelsFromResponse; 
                                 delete res["_xmlns:ns2"];
                                 delete res["__prefix"];   
                                 obj = res;
                                
                     })
                             .error(function (data, status, headers, config) {
                                 return {"status": false};
                     });                     
                     return promise;
            }, 
            
            getSpecificCarTravel: function (travel_id) {
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                opName = "getSpecificCarTravel";
                sr = SOAPhead +
                        '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                        '<travel_id>'+ travel_id +'</travel_id>' +
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
                                 res = jsonObj.Envelope.Body.getSpecificCarTravelResponse;                            
                                 delete res["_xmlns:ns2"];
                                 delete res["__prefix"];   
                                 travelObj = res;
                     })
                             .error(function (data, status, headers, config) {
                                 return {"status": false};
                     });
                     
                     return promise;
                
            },
            
            getSpecificTaxiTravel : function (taxi_id) {
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                opName = "getSpecificTaxiTravel";
                sr = SOAPhead +
                        '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                        '<taxi_id>'+ taxi_id +'</taxi_id>' +
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
                                 res = jsonObj.Envelope.Body.getSpecificTaxiTravelResponse;                            
                                 delete res["_xmlns:ns2"];
                                 delete res["__prefix"];   
                                 travelObj = res;
                     })
                             .error(function (data, status, headers, config) {
                                 return {"status": false};
                     });
                     
                     return promise;
                
            },
            
            getClientTravel: function (email) { 
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "getClientTravel";           
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
                            res = jsonObj.Envelope.Body.getClientTravelResponse;
                            delete res["_xmlns:ns2"];
                            delete res["__prefix"];
                            bookedTravelsObj = res;
                        })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise; 
            },
            
            getDriverFromTravel: function (travelID) { 
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                var opName = "getDriverFromTravel";           
                sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<travelID>'+ travelID +'</travelID>' +
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
                    
                    res = jsonObj.Envelope.Body.getDriverFromTravelResponse;
                    
                    delete res["_xmlns:ns2"];
                    delete res["__prefix"];
                    obj = res.return;
                })
                        .error(function (data, status, headers, config) {
                            console.log("errore in serviceCtrl nel metodo getDriverFromTravel");
                            return {"status": false};
                });
                
                return promise; 
            },
            
            getHistoryTravels: function (email) {
                var res;
                var sr;
                var action;
                var opName;
                var promise;
                opName = "getHistoryTravels";
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
                                 res = jsonObj.Envelope.Body.getHistoryTravelsResponse;                            
                                 delete res["_xmlns:ns2"];
                                 delete res["__prefix"];   
                                 historyTravelsObj = res;
                     })
                             .error(function (data, status, headers, config) {
                                 return {"status": false};
                     });
                     
                     return promise;
            }, 
            
            getLatestReceivedComments: function(email, numMax){
                var res;
                var opName = "getLatestReceivedComments";
                var sr = SOAPhead +
                           '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                           '<clientEmail>' + email + '</clientEmail>' +
                           '<numMax>' + numMax + '</numMax>'+
                           '</ns0:' + opName + '>'+
                           SOAPtail; 
                var action = '"' + "http://SOAPServer" + "/" + opName + '"' ;
                var promise = $http.post(SOAPbase, sr, { "headers": {
                        'Content-Type' : "text/xml;charset=utf-8",
                        'SOAPAction': action
                    }                  
                }).success(function (data, status, headers, config) {
                            var jsonObj = x2js.xml_str2json( data );
                    res = jsonObj.Envelope.Body.getLatestReceivedCommentsResponse;
                  
                    delete res["_xmlns:ns2"];
                    delete res["__prefix"];
                    
                    commentObj = res.return;           
                    return res;
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
                  
                    delete res["_xmlns:ns2"];
                    delete res["__prefix"];
                    
                    driversObj = res.return;           
                    return res;
                })
                        .error(function (data, status, headers, config) {
                            return {"status": false};
                });
                
                return promise;                     
            },
            
            getData: function () {                
              console.log("sto passando un oggetto obj = " );
              console.log(obj);
                return obj;
            },
            
            getCars: function () {
                return driversObj;
            },
            
            setCars: function (item) {
                driversObj = item;
            },
            
            getClientInfo: function () {
                return clientObj;
            },
            
            getTravelInfo: function () {
                return travelObj;
            },
            
            setTravelInfo: function (object) {
                travelObj = object;                
            },
            
            setData: function (object) {
                obj = object;
            },
            
            getComments: function(){
                return commentObj;
            },
            
           getPassengersObject: function() {
              return passengersObj;  
            },
            
           getBookedTravels: function(){
                return bookedTravelsObj;
            },
            
            getHistory: function(){
                return historyTravelsObj;
            }
             
           
        };
    }]);
       
    
    

})();
