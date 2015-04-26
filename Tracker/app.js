

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

var conString = "postgres://abhinav:password@localhost:5432/mydb"

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
////keshav
app.post('/files/insert', function(req, res){
	var insert = insertFile(req.body.absolutepath,req._remoteAddress,req.body.timestamp,req.body.size,req.body.originip);
	insert.then(function(data){
		res.send("success");
	});
});
////akash
app.post('/device/insert', function(req, res){
	var insert = insertDevice(req._remoteAddress, req.body.freesize,req.body.hash);
	insert.then(function(data){
		res.send("success");
	});
});
////akash
app.post('/device/update', function(req, res){
	var insert = insertDevice(req._remoteAddress, req.body.freesize);
	insert.then(function(data){
		res.send("success");
	});
});
////   donno
app.post('/video/insert', function(req, res){
	var ip = req._remoteAddress;
	var insert = insertVideo(req.body.absolutepath, ip, req.body.audiobitrate, req.body.videobitrate, req.body.server,req.body.originip);
	insert.then(function(data){
		res.send("success");
	});
});
////keshav-modify
app.get('/files/get', function(req, res){
	var ipadr = req._remoteAddress;
	var insert = getFile(ipadr);
	insert.then(function(data){
		res.send(data.rows);
	});
});
//// donno
app.get('/device/get', function(req, res){
	var insert = getDevice();
	insert.then(function(data){
		res.send(data.rows);
	});
});
//// donno
app.get('/video/get/:ip', function(req, res){
	var insert = getVideo(req.params.ip);
	insert.then(function(data){
		res.send(data.rows);
	});
});
//// akash
app.post('/files/delete', function(req, res){
	var absolutepath = req.body.absolutepath;
	var ip = req._remoteAddress;
	var deletefile = deleteFile(absolutepath, ip);
	deletefile.then(function(data){
		res.send('success')
	});
});
//// akash
app.post('/files/rename', function(req, res){
	var oldpath = req.body.oldpath;
	var newpath = req.body.newpath;
	var ip = req._remoteAddress;
	var renamefile = renameFile(ip, oldpath, newpath);
	renamefile.then(function(data){
		res.send('success');
	});
});
//// akash
app.post('/files/updatesize', function(req, res){
	var absolutepath = req.body.absolutepath;
	var ip = req._remoteAddress;
	var newsize = req.body.size;
	var updatesize = updateSize(ip, absolutepath, newsize); 
});
//// donno
app.post('/video/delete', function(req, res){
	var absolutepath = req.body.absolutepath;
	var ip = req.body.ip;
	var deletevideo = deleteVideo(absolutepath, ip);
	deletevideo.then(function(req, res){
		var deletefile = deleteFile(absolutepath, ip);
		deletefile.then(function(data){
			res.send('success');
		});
		res.send("deleted video")
	});
});
//// keshav
app.get('/getfreesize/:size', function(req, res){
	var size = req.params.size;
	var getip = getIp(size);
	getip.then(function(data){
		res.send(data.rows[0].ip);
	});
});
//// abhishek
app.get('/auth/:hash', function(req, res){
	var ip = req._remoteAddress;
	var gethash = getHash(ip);
	gethash.then(function(data){
		if(data == req.params.hash)
		{
			res.send('true');
		}
		else
		{
			res.send('false');
		}
	});
});
var getHash = function(ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("select hash from freesize where ip='"+ip+"'", function(err, result) 
    {
      done();
      	if (err)
       	{
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
var getIp = function(size)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("select ip from freesize where freesize>"+size+";", function(err, result) 
    {
      done();
      	if (err)
       	{
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
var deleteVideo = function(path, ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("delete from video where ip='"+ip+"' and absolutepath='"+path+"';", function(err, result) 
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





var insertFile = function(path, ip, timestamp, size, originip)
{
	var deferred = q.defer();//wait until query is completed
console.log(path,ip,timestamp,size,originip);
	pg.connect(conString, function(err, client, done) {
    client.query("insert into hooks values('"+path+"','"+ip+"','"+timestamp+"',"+size+",'"+originip+"');", function(err, result) 
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
var insertDevice = function(ip, freesize,hash)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("delete from freesize where ip='"+ip+"';insert into freesize values('"+ip+"',"+freesize+");", function(err, result) {
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
var updateDevice = function(ip, freesize)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("update freesize set freesize="+freesize+" where ip='"+ip+"';", function(err, result) {
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

var insertVideo = function(absolutepath, ip, audiobitrate, videobitrate, server, originip)
{
	var deferred = q.defer();
	console.log("abhinav")
	pg.connect(conString, function(err, client, done) {
    client.query("insert into video values('"+absolutepath+"','"+ip+"',"+audiobitrate+","+videobitrate+",'"+server+"','"+originip+"');", function(err, result) {
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
var getVideo = function(ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("select * from video where originip='"+ip+"';", function(err, result) {
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
var getFile = function(ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("select * from hooks where originip='"+ip+"';", function(err, result) {
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
var renameFile = function(ip, oldpath, newpath)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("update hooks set absolutepath='"+newpath+"' where absolutepath='"+oldpath+"' and ip='"+ip+"';", function(err, result) 
    {
      done();
      	if (err)
       	{
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

var updateSize = function(ip, absolutepath, newsize)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) {
    client.query("update hooks set size='"+newsize+"' where absolutepath='"+absolutepath+"' and ip='"+ip+"';", function(err, result) 
    {
      done();
      	if (err)
       	{
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
