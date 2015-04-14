

/**
 * Module dependencies
 */

var express = require('express'),//exprss js framewrk
  bodyParser = require('body-parser'),
  methodOverride = require('method-override'),
  errorHandler = require('error-handler'),
  morgan = require('morgan'),
  //routes = require('./routes'),
  //api = require('./routes/api'),
  http = require('http'),
  qs = require('querystring'),
  pg = require('pg'),//postgres
  q = require('q'),
  path = require('path'),
  formidable = require('formidable'),
    util = require('util'),
    fs   = require('fs-extra'),
    qt   = require('quickthumb');

var conString = "postgres://bhargav:password@localhost:5432/mydb"

var app = module.exports = express();
app.use(bodyParser.urlencoded({
  extended: true
}));

/**
 * Configuration
 */

// all environments
//app.use(qt.static(__dirname + '/'));
app.set('port', 3000);
//app.set('views', __dirname + '/views');
app.set('view engine', 'jade');
app.use(morgan('dev'));app.use(bodyParser.urlencoded({
  extended: true
}));
app.use(methodOverride());
app.use(express.static(path.join(__dirname, 'public')));
app.use( bodyParser.json() );
var env = process.env.NODE_ENV || 'development';
//global.accessToken = 404;
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
app.post('/files/insert', function(req, res){
	var insert = insertFile(req.body.absolutepath,req.body.ip,req.body.timestamp,req.body.size);
	insert.then(function(data){
		res.send("success");
	});
});
app.post('/device/insert', function(req, res){
	var insert = insertDevice(req.body.ip, req.body.freesize);
	insert.then(function(data){
		res.send("success");
	});
});
app.post('/video/insert', function(req, res){
	var insert = insertVideo(req.body.absolutepath, req.body.ip, req.body.audiobitrate, req.body.videobitrate, req.body.server);
	insert.then(function(data){
		res.send("success");
	});
});
app.get('/files/get', function(req, res){
	var insert = getFile();
	insert.then(function(data){
		res.send(data.rows);
	});
});
app.get('/device/get', function(req, res){
	var insert = getDevice();
	insert.then(function(data){
		res.send(data.rows);
	});
});
app.get('/video/get', function(req, res){
	var insert = getVideo();
	insert.then(function(data){
		res.send(data.rows);
	});
});
app.post('/files/delete', function(req, res){
	var absolutepath = req.body.absolutepath;
	var ip = req.body.ip;
	var deletefile = deleteFile(absolutepath, ip);
	deletefile.then(function(data){
		var seekip = seekIp(ip);
		seekip.then(function(data1){
			console.log("data1:"+JSON.stringify(data1));
			if(data1.rows.length==0)
			{
				cosole.log('yayayay!!!')
				var deleteip = deleteIp(ip);
				deleteip.then(function(data){
					res.send('success2');
				});
			}
			//res.send('success1')
		});
	});
});
var deleteFile = function(path, ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("delete from hooks where ip='"+ip+"' and absolutepath='"+path+"';", function(err, result) 
    {
      done();
      console.log("fffff"+(String)(err));
      	if (err)
       	{
       		console.log("comes here");
       	 	deferred.reject(err);
       	}
      	else
       	{
       	 	deferred.resolve('success');
       	}
       	});
	});
	return deferred.promise;	
}
var seekIp = function(ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("select * from hooks where ip='"+ip+"';", function(err, result) 
    {
      done();
      console.log((String)(err));
      	if (err)
       	{
       		console.log("comes here");
       	 	deferred.reject(err);
       	}
      	else
       	{
       	 	deferred.resolve(result);
       	}
       	});
	});
	return deferred.promise;	
}
var deleteIp = function(ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("delete from freesize where ip='"+ip+"';", function(err, result) 
    {
      done();
      console.log((String)(err));
      	if (err)
       	{
       		console.log("comes here");
       	 	deferred.reject(err);
       	}
      	else
       	{
       	 	deferred.resolve('success');
       	}
       	});
	});
	return deferred.promise;	
}





var insertFile = function(path, ip, timestamp, size)
{
	var deferred = q.defer();//wait until query is completed
console.log(path,ip,timestamp,size);
	pg.connect(conString, function(err, client, done) {
    client.query("insert into hooks values('"+path+"','"+ip+"','"+timestamp+"',"+size+");", function(err, result) 
    {
      done();
      console.log((String)(err));
      	if (err)
       	{
       		console.log("comes here");
       	 	deferred.reject(err);
       	}
      	else
       	{
       	 	deferred.resolve('success');
       	}
       	});
	});
	return deferred.promise;	
}
var insertDevice = function(ip, freesize)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("insert into freesize values('"+ip+"',"+freesize+");", function(err, result) {
      done();
      	if (err)
       	{
       	 	deferred.reject(err);
       	}
      	else
       	{
       	 	deferred.resolve('success');
       	}
       	});
	});
	return deferred.promise;	
}
 http.createServer(app).listen(app.get('port'), function () {
   console.log('Express server listening on port ' + app.get('port'));
 });

var insertVideo = function(absolutepath, ip, audiobitrate, videobitrate, server)
{
	var deferred = q.defer();
	console.log("abhinav")
	pg.connect(conString, function(err, client, done) {
    client.query("insert into video values('"+absolutepath+"','"+ip+"',"+audiobitrate+","+videobitrate+",'"+server+"');", function(err, result) {
      done();
      	if (err)
       	{
       		console.log("inside if")
       	 	deferred.reject(err);
       	}
      	else
       	{
       		console.log("outside if")
       	 	deferred.resolve('success');
       	}
       	});
	});
	console.log("before return");
	return deferred.promise;	
}
var getVideo = function()
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("select * from video;", function(err, result) {
      done();
      	if (err)
       	{
       		console.log("comes here");
       	 	deferred.reject(err);
       	}
      	else
       	{
       	 	deferred.resolve(result);
       	}
       	});
	});
	return deferred.promise;	
}
var getDevice = function()
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("select * from freesize;", function(err, result) {
      done();
      	if (err)
       	{
       		console.log("comes here");
       	 	deferred.reject(err);
       	}
      	else
       	{
       	 	deferred.resolve(result);
       	}
       	});
	});
	return deferred.promise;	
}
var getFile = function()
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("select * from hooks;", function(err, result) {
      done();
      	if (err)
       	{
       		console.log(err);
       	 	deferred.reject(err);
       	}
      	else
       	{
       	 	deferred.resolve(result);
       	}
       	});
	});
	return deferred.promise;	
}
