(function () {
  'use strict';

var modAuthenticator = angular.module('authModule', ['ngRoute','ngCookies']);

    //Factory per dati condivisi
   modAuthenticator.factory('auth', [ '$http', '$cookies',
   function ($http,$cookies) {
        var data;
        var obj = {};
            var isGmail;
            var isTwitter;
            var exLoginData;

            //variabili per le richieste SOAP
            var SOAPbase = "http://localhost:8080/Conpartir-war/SOAPServiceClient";
            //var SOAPbase = "http://conpartir03.northeurope.cloudapp.azure.com:8080/Conpartir-war/SOAPServiceClient";

            var SOAPhead = '<?xml version="1.0" encoding="utf-8"?>' +
                    '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">' +
                    '<soap:Body>';
            var SOAPtail = '</soap:Body>' +
                    '</soap:Envelope>';
            var x2js = new X2JS();

            function getCookie(name) {
                var value = "; " + document.cookie;
                var parts = value.split("; " + name + "=");
                var ret;
                if (parts.length === 2)
                    ret = "conpCookie=" + parts.pop().split(";").shift();
                return ret;
            }

            return {
                doLogin: function (input) {
                    var promise;
                    promise = $http({
                        method: 'POST',
                        url: 'Registration',
                        headers: {'Content-Type': 'application/json'},
                        data: input
                    })
                            .success(function (data, status, header) {
                                //checkCookieEnabled();                            
                                console.log("doc method for reading cookie " + document.cookie);

                                // var ckValue = getCookie('conpCookie');
                                var ckValue = $cookies.get('conpCookie');
                                console.log("doc method for reading cookie " + ckValue);
                                sessionStorage.setItem("conpCookie", ckValue);
                                //sessionStorage.setItem('conpCookie', ckValue);
                                //console.log("session storage saved " + sessionStorage.getItem('conpCookie'));
                                obj = true;
                            })
                            .error(function (data, status, headers, config) {
                                obj = false;
                                return {"status": false};
                            });
                    return promise;

              },
                doRegister: function (input) {
                    var promise;
                    promise = $http({
                        method: 'POST',
                        url: 'Registration',
                        headers: {'Content-Type': 'application/json'},
                        data: input
                    })
                            .success(function (data, status, header) {
                                //checkCookieEnabled();                            

                                //sessionStorage.setItem('conpCookie', ckValue);
                                //console.log("session storage saved " + sessionStorage.getItem('conpCookie'));
                                obj = true;
                            })
                            .error(function (data, status, headers, config) {
                                obj = false;
                                return {"status": false};
                            });
                    return promise;

                },
//            //controlla se l'utente è autenticato, verificando se esiste un cookie nella sessionStorage
                isAuthenticated: function () {

                    var cookie = sessionStorage.getItem('conpCookie');
                    if (cookie) {
                        //console.log("recuperato cookie " + cookie + "dalla sessionstorage");
                        return cookie;
                    }
                    else {
                        return false;
                    }
                    ;

                },
                doLogout: function () {
                    sessionStorage.removeItem('conpCookie');
                    sessionStorage.clear();
                    console.log(sessionStorage.getItem("conpCookie"));
                    exLoginData = {};
                    obj = false;
                },
                saveCookie: function (ckName, ckValue) {
                    sessionStorage.setItem(ckName, ckValue);

                },
                delCookie: function (ckName) {
                    sessionStorage.removeItem(ckName);
                },
                //controlla se l'utente è loggato, interrogando la servlet
                checkAuth: function (cookie) {
                    //console.log('checkAuth ' + cookie);
                    var promise;
                    promise =
                            $http({
                                method: 'GET',
                                url: 'Registration',
                                headers: {'Content-Type': 'application/text'},
                                Cookie: cookie
                            })
                            .success(function (data, status, header) {
                                //checkCookieEnabled();                            
                                console.log("servlet response " + data + status);
                                obj = true;
                                //sessionStorage.setItem('conpCookie', ckValue);
                                //console.log("session storage saved " + sessionStorage.getItem('conpCookie'));
                            })
                            .error(function (data, status, headers, config) {
                                return {"status": false};
                            });

                    return promise;
                },
                
                //Vecchia versione di un metodo, presupponeva che l'email dell'account e della gmail
                //fossero le stesse. Vedere "isGmailThere"
                isGmailOn: function (email) {
                    var res;
                    var sr;
                    var action;
                    var opName;
                    var promise;
                    var opName = "isGmailOn";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<email>' + email + '</email>' +
                            '</ns0:' + opName + '>' +
                            SOAPtail;
                    action = '"' + "http://SOAPServer" + "/" + opName + '"';

                    promise = $http.post(SOAPbase, sr, {"headers": {
                            'Content-Type': "text/xml;charset=utf-8",
                            'SOAPAction': action
                        }
                    })
                            .success(function (data, status, headers, config) {
                                var jsonObj = x2js.xml_str2json(data);
                                res = jsonObj.Envelope.Body.isGmailOnResponse;
                                delete res["_xmlns:ns2"];
                                delete res["__prefix"];
                                isGmail = res;
                            })
                            .error(function (data, status, headers, config) {
                                return {"status": false};
                            });

                    return promise;
                },
                //Controlla se qualche utente ha usato la gmail per collegarla al suo account
                isGmailThere: function (gmail) {
                    var res;
                    var sr;
                    var action;
                    var opName;
                    var promise;
                    var opName = "isGmailThere";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<gmailValue>' + gmail + '</gmailValue>' +
                            '</ns0:' + opName + '>' +
                            SOAPtail;
                    action = '"' + "http://SOAPServer" + "/" + opName + '"';

                    promise = $http.post(SOAPbase, sr, {"headers": {
                            'Content-Type': "text/xml;charset=utf-8",
                            'SOAPAction': action
                        }
                    })
                            .success(function (data, status, headers, config) {
                                var jsonObj = x2js.xml_str2json(data);
                                res = jsonObj.Envelope.Body.isGmailThereResponse;
                                delete res["_xmlns:ns2"];
                                delete res["__prefix"];
                                isGmail = res;
                            })
                            .error(function (data, status, headers, config) {
                                return {"status": false};
                            });

                    return promise;
                },
                //Deprecato, vedere  isTwitterThere
                isTwitterOn: function (email) {
                    var res;
                    var sr;
                    var action;
                    var opName;
                    var promise;
                    var opName = "isTwitterOn";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<email>' + email + '</email>' +
                            '</ns0:' + opName + '>' +
                            SOAPtail;
                    action = '"' + "http://SOAPServer" + "/" + opName + '"';

                    promise = $http.post(SOAPbase, sr, {"headers": {
                            'Content-Type': "text/xml;charset=utf-8",
                            'SOAPAction': action
                        }
                    })
                            .success(function (data, status, headers, config) {
                                var jsonObj = x2js.xml_str2json(data);
                                res = jsonObj.Envelope.Body.isTwitterOnResponse;
                                delete res["_xmlns:ns2"];
                                delete res["__prefix"];
                                isTwitter = res;
                            })
                            .error(function (data, status, headers, config) {
                                return {"status": false};
                            });

                    return promise;
                },
                //Controlla se c'è un utente che ha quel valore di twitter account
                //collegato col proprio account conpartir
                isTwitterThere: function (twitterAccount) {
                    var res;
                    var sr;
                    var action;
                    var opName;
                    var promise;
                    var opName = "isTwitterThere";
                    sr = SOAPhead +
                            '<ns0:' + opName + ' xmlns:ns0="http://SOAPServer/">' +
                            '<twitterValue>' + twitterAccount + '</twitterValue>' +
                            '</ns0:' + opName + '>' +
                            SOAPtail;
                    action = '"' + "http://SOAPServer" + "/" + opName + '"';

                    promise = $http.post(SOAPbase, sr, {"headers": {
                            'Content-Type': "text/xml;charset=utf-8",
                            'SOAPAction': action
                        }
                    })
                            .success(function (data, status, headers, config) {
                                var jsonObj = x2js.xml_str2json(data);
                                res = jsonObj.Envelope.Body.isTwitterThereResponse;
                                delete res["_xmlns:ns2"];
                                delete res["__prefix"];
                                isTwitter = res;
                            })
                            .error(function (data, status, headers, config) {
                                return {"status": false};
                            });

                    return promise;
                },
                
                getData: function () {
                    return obj;
                },
                getGmailValue: function () {
                    return isGmail;
                },
                getTwitterValue: function () {
                    return isTwitter;
                },
                getExternalLoginData: function () {
                    return exLoginData;
                },
                setData: function (data) {
                    obj = data;
                },
                setExternalLoginData: function (data) {
                    exLoginData = data;
                }



            };
        }]);




})();
