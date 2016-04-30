(function () {
  'use strict';

var modAuthenticator = angular.module('authModule', ['ngRoute','ngCookies']);

    //Factory per dati condivisi
   modAuthenticator.factory('auth', [ '$http', '$cookies',
   function ($http,$cookies) {
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
            
            //controlla se l'utente è autenticato, verificando se esiste un cookie nella sessionStorage
            isAuthenticated: function() {
                
                var cookie = sessionStorage.getItem('conpCookie');
                if(cookie) { 
                    //console.log("recuperato cookie " + cookie + "dalla sessionstorage");
                    return cookie;
                }
                else {return false;};
                
                
            },
            
            saveCookie: function(ckName,ckValue) {
                sessionStorage.setItem(ckName,ckValue);
            },
            
            delCookie: function(ckName) {
                sessionStorage.removeItem(ckName);
            },
            
            //controlla se l'utente è loggato, interrogando la servlet
            checkAuth: function (cookie) {                
                var promise; 
                promise =
                $http({
                    method: 'GET',
                    url: 'Registration',
                    headers: {'Content-Type': 'application/text'},
                    Cookie: cookie                    
                })
                        .success( function (data, status, header) {
                            //checkCookieEnabled();
                            
                            console.log("servlet response " + data + status);                         
                             //sessionStorage.setItem('conpCookie', ckValue);
                            //console.log("session storage saved " + sessionStorage.getItem('conpCookie'));
                        })
                                .error(function (data, status, headers, config) {
                                 return {"status": false};
                     });
                        
                return promise;
            },
            
            getData: function () {
                return obj;
            },
            
            
            setData: function (data) {
                obj = data;
            }



        };
    }]);
       
    
    

})();
