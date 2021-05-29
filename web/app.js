var express = require('express');
var app = express();

var mysql = require('mysql');
//配置模块
var settings = require('./settings');
//连接数据库
var connection = mysql.createConnection(settings.db);
connection.connect();

//查询
var sql = 'select * from user';

var result = "";
connection.query(sql, function(err, rows){
	if(err) throw err;
	result = rows[0].UserName;
	app.get('/', function(req, res){
		res.send(result);
	});
});
connection.end();
app.listen(8080);