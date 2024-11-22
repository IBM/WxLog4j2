app.service('myserv', function() {
          this.getServiceList = function () {
    return [{"serviceName":"wx.log4j2.pub:addLog4j2Configuration","description":"This service has to be used to register your log4j2.xml of your package. You can invoke it from your startup service.\r\n\r\nYou can use the predefined appender \"WxLog4j2RollingFile\" in that file.","serviceinputs":[{"inputName":"configFilePathname","inputType":"String","inputComments":"no comment","required":true,"wrapperType":"--"}],"serviceoutputs":[]},{"serviceName":"wx.log4j2.pub:closeLoggerContexts","description":"This is closing active Loggers. It is automatically invoked by the shutdown service","serviceinputs":[],"serviceoutputs":[]},{"serviceName":"wx.log4j2.pub:log","description":"Invoke this service from your code and log into your logfile by using the input \"function\" in order to define your Logger defined in your log4j2.xml.","serviceinputs":[{"inputName":"message","inputType":"String","inputComments":"no comment","required":false,"wrapperType":"--"},{"inputName":"function","inputType":"String","inputComments":"no comment","required":false,"wrapperType":"--"},{"inputName":"level","inputType":"String","inputComments":"no comment","required":false,"wrapperType":"--"}],"serviceoutputs":[]}];
}
this.getISEndpoint = function() { 
 return 'http://IBM-PF3F0527.fritz.box:10150/';
}
this.getAPIList = function() { 
 return [];
}
this.getCreatedTime = function() { 
 return "22-11-2024 15:45:21 MEZ";
}
this.getPackageInfo = function(){
 return{"packageName":"WxLog4j2","displayName":"","status":"Development","tags":[""],"version":"10.15.0.1","buildNumber":"","description":""};
}
});
