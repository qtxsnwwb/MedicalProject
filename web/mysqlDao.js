exports.db_doctor = function(fn) {
	var mysql = require('mysql');
	
	var connection = mysql.createConnection({
		host:"192.168.0.101",
		user:"root",
		password:"mysqladmin",
		database:"medical"
	});
	connection.connect();
	var sql = 'select * from user';
	var result = "";
	connection.query(sql, function(error, results, fields){
		if(error){
			console.log('SELECT ERROR - ', error.message);
			return;
		}
		result = results[0].UserName;
		fn(result);
	});	
}

