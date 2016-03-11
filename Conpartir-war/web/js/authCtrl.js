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
            
            isAuthenticated: function() {
                var res=false;
                var cookie = sessionStorage.getItem('conpCookie');
                if(cookie !== null) res=true;
                return res; 
            },
            
            saveCookie: function(ckName,ckValue) {
                sessionStorage.setItem(ckName,ckValue);
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
