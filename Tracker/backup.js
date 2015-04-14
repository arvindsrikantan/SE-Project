
/**
 * Module dependencies
 */

var express = require('express'),
  bodyParser = require('body-parser'),
  methodOverride = require('method-override'),
  errorHandler = require('error-handler'),
  morgan = require('morgan'),
  routes = require('./routes'),
  api = require('./routes/api'),
  http = require('http'),
  qs = require('querystring'),
  pg = require('pg'),
  q = require('q'),
  path = require('path');

var app = module.exports = express();
app.use(bodyParser.urlencoded({
  extended: true
}));

/**
 * Configuration
 */

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', __dirname + '/views');
app.set('view engine', 'jade');
app.use(morgan('dev'));app.use(bodyParser.urlencoded({
  extended: true
}));
app.use(methodOverride());
app.use(express.static(path.join(__dirname, 'public')));
app.use( bodyParser.json() );
var env = process.env.NODE_ENV || 'development';
global.accessToken = 404;
app.all('*', function(req, res, next) {
  res.header('Access-Control-Allow-Origin', "*");
  res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
  res.header('Access-Control-Allow-Headers', 'Content-Type');
  next();
 });
// development only
if (env === 'development') {
  app.use(express.errorHandler());
}
// production only
if (env === 'production') {
  // TODO
}


// Handling requests
app.post('/login', function(req, res){

	var auth = auth_credentials();
	auth.then(function(data){
	if(data.rows[0].username == req.body.username && data.rows[0].password == req.body.password)
		{
			global.accessToken = getAccessToken();
    		res.send({'accessToken':global.accessToken})
		}
		else res.send({'accessToken':404})
	});
});
app.post('/projects/add', function(req, res){
	if(global.accessToken == req.body.accessToken)
	{
		var addpro = addProject(req.body.title,req.body.description,req.body.participants);
		addpro.then(function(data){
			res.send('success');
		});
	}
	else res.send('failed');
});
app.post('/projects/get',function(req, res){
	var getallprojects = getProjects();
	getallprojects.then(function(data){
		res.send('data:',data.rows);
	});	
});
/**
 * Start Server
 */

http.createServer(app).listen(app.get('port'), function () {
  console.log('Express server listening on port ' + app.get('port'));
});

// functions
// generate accesstoken
var getAccessToken = function() {
    return Math.floor(Math.random() * (100000000-10000000) + 1000);
}
// get the username and password
var auth_credentials = function(){
  	var constring = "pg://myuser:123456@localhost/mydb";
	var client = new pg.Client(constring);
	client.connect();
	var q1 = client.query("select * from authenticate");
	q1.on("row", function(row, result){
		result.addRow(row);
	});
	var deferred = q.defer();
	q1.on("end", function(result){
		client.end();
		deferred.resolve(result)
	});
	return deferred.promise;
}
// add a new project
var addProject = function(title,desc,part){
	var deferred = q.defer();
	var getId = getProjectId();
	getId.then(function(data){
		console.log('data ',data.rows[0].id);
		var constring = "pg://myuser:123456@localhost/mydb";
		var client = new pg.Client(constring);
		client.connect();
		client.query("insert into projects values("+(data.rows[0].id+1)+",'"+title+"','"+desc+"','"+part+"');");
		deferred.resolve('success');
	});
	return deferred.promise;
}
// get the last project id
var getProjectId = function(){
	var constring = "pg://myuser:123456@localhost/mydb";
	var client = new pg.Client(constring);
	client.connect();
	var q1 = client.query("select id from projects order by id desc limit 1");
	q1.on("row", function(row, result){
		result.addRow(row);
	});
	var deferred = q.defer();
	q1.on("end", function(result){
		client.end();
		deferred.resolve(result)
	});
	return deferred.promise;
}
// get all the projects
var getProjects = function(){
	var constring = "pg://myuser:123456@localhost/mydb";
	var client = new pg.Client(constring);
	client.connect();
	var q1 = client.query("select * from projects");
	q1.on("row", function(row, result){
		result.addRow(row);
	});
	var deferred = q.defer();
	q1.on("end", function(result){
		client.end();
		deferred.resolve(result)
	});
	return deferred.promise;
}