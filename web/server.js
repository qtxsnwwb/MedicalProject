var http = require('http');
var express = require('express');
//var db_util = require('./mysqlDao');
//var createServer = http.createServer(onRequest);

//function onRequest(request, response){
    //response.writeHead(200, {
        //'Content-Type': 'text/plain',
        //'Access-Control-Allow-Origin': '*'
    //});
	//var str = '';
	//db_util.db_doctor(function(result){
	//	str = result;
	//});
	//for(var i=0; i<1000000000; i++){
	//	console.log(i);
	//	if(str.length != 0) break;
	//}
	//response.end();
    
    
//}
//createServer.listen(8080);
//console.log('Server running at http://127.0.0.1:8080');

var app = express();
app.post('/', function(req, res){
	res.send("hello world");
});
app.listen(8080);
