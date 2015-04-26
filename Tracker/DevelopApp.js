/***************************************************************************
*
*   SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*
*       JOB     - TRACKER APIs AND DATABASE
*
*
*       AUTHORS - ABHINAV M KULKARNI
*               - BHARGAV H S
*
*
*       TASK    - To create a backend storage along with an interface
*                 to the database. The Database Management System to be 
*                 used is PostGreSQL. The tables required are three in 
*                 number which include freesize (a table to store a list 
*                 of all the disks available at a particular IP address 
*                 and the freespace available at that disk), hooks (a 
*                 table to store the size of a particular disk at a given
*                 instant of time) and finally video (a table to store the
*                 audiobitrate and the videobitrate of the video that is 
*                 being streamed)
*
*
*       START   - February 13th
*
*
*
****************************************************************************/


/***************************************************************************
*
*   The following statements are used to import the various modules
*   which are required for the prooject.
*
****************************************************************************/


var     express         = require('express'),                   // The Express-JS framework
        bodyParser      = require('body-parser'),               // The body-parser from EJS
        methodOverride  = require('method-override'),           // Additional handlers for EJS
        errorHandler    = require('error-handler'),
        morgan          = require('morgan'),
        http            = require('http'),
        qs              = require('querystring'),
        pg              = require('pg'),                        //postgres
        q               = require('q'),
        path            = require('path'),                      
        formidable      = require('formidable'),
        util            = require('util'),
        fs              = require('fs-extra'),
        qt              = require('quickthumb')
        
;



/***************************************************************************
*
*   The following string is used to connect to the postgres server. It
*   contains the complete port along with password.
*
****************************************************************************/

//var conString = "postgres://abhinav:password@localhost:5432/mydb"
var conString = "postgres://bhargav:password@localhost:5432/mydb"

//   The following section is used to instantiate the express framework



var app = module.exports = express();
app.use(
            bodyParser.urlencoded
            (
            {
                    extended: true
            }
            )
       );

/***************************************************************************
*
*   The following set of functions are the standard configuration
*   headers that are required for any NodeJS application
*
****************************************************************************/

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


/***************************************************************************
*
*   The following set of functions are used as CORS Headers. This
*   allows all the users to have free access to our database APIs
*
****************************************************************************/

//global.accessToken = 404;

app.all('*',function(req, res, next) 
            {
              res.header('Access-Control-Allow-Origin', "*");
              res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
              res.header('Access-Control-Allow-Headers', 'Content-Type');
              next();
            }
       );
        
 
// This is to set up the environment of use

// development only
if (env === 'development') {
  app.use(express.errorHandler());
}
// production only
if (env === 'production') {
  // TODO
}


/***************************************************************************
*
*   The following three functions are use to fill the backend databases 
*   the sent data from the client side. These apis interface with the 
*   insert functions written. These functions are fired by the client
*   through hooks. The hooks send the data encoded in an HTTP Post 
*   request. These functions then call the respective insert functions
*   and insert the values into the database.
*
****************************************************************************/

app.post('/files/insert', function(req, res)
                        {
                            console.log('comes here to hooks');
                            var insert = insertFile(req.body.absolutepath,req._remoteAddress,req.body.timestamp,req.body.size,req.body.originip);
                            insert.then(function(data)
                            {
	                            res.send("success");
                            });
                        }
        );


app.post('/device/insert', function(req, res)
                        {
                            console.log('comes here to freesie');
                            var insert = insertDevice(req._remoteAddress, req.body.freesize);
                            insert.then(function(data)
                            {
	                            res.send("success");
                            });
                        }
        );
        
app.post('/video/insert', function(req, res)
                        {
                            var ip = req._remoteAddress;
                            var insert = insertVideo(req.body.absolutepath, ip, req.body.audiobitrate, req.body.videobitrate, req.body.server,req.body.originip);
                            insert.then(function(data)
                            {
	                            res.send("success");
                            });
                        }
         );        


/***************************************************************************
*
*   The following three functions are the requests to the wrapper functions
*   to get the data. This is fired upon request from the client side. On
*   call the get request fetches the data from the databases using the
*   other defined functions and returns the data only after stripping the
*   excess information. These calls interface with the hooks. The returned
*   data is in the format of a JSoN string.
*
****************************************************************************/

app.get('/files/get/', function(req, res)
                    {
                        var ipadr = req._remoteAddress;
                        var insert = getFile(ipadr);
                        insert.then(function(data)
                        {
	                        res.send(data.rows);
                        });
                    }
       );
       
app.get('/device/get', function(req, res)
                    {
                        var insert = getDevice();
                        insert.then(function(data)
                        {
	                        res.send(data.rows);
                        });
                    }
       );
       
app.get('/video/get/:ip', function(req, res)
                        {
	                        var insert = getVideo(req.params.ip);
	                        insert.then(function(data)
	                        {
		                        res.send(data.rows);
	                        });
                        }
       );

/***************************************************************************
*
*   The following two functions are wrapper classes to delete the data from
*   the database. The functions are fired upon a post request from the client
*   side. Upon request, it forwards the it to the other functions that take 
*   care of the deletion. The parameters required are the IP adress and the 
*   absolute path of the file to be deleted. The other feature of this is 
*   that it removes the original table entry of the device if all the files 
*   in that are deleted. Like wise the call to delete a video also deletes 
*   the entry from the file table and the hooks table if there are no more 
*   available files.
*
****************************************************************************/


app.post('/files/delete', function(req, res)
                        {
	                        var absolutepath = req.body.absolutepath;
	                        var ip = req._remoteAddress;
	                        var deletefile = deleteFile(absolutepath, ip);
	                        deletefile.then(function(data)
	                        {
		                        res.send('success')
	                        });
                        }
        );

app.post('/video/delete', function(req, res)
                        {
	                        var absolutepath = req.body.absolutepath;
	                        var ip = req.body.ip;
	                        var deletevideo = deleteVideo(absolutepath, ip);
	                        deletevideo.then(function(req, res)
	                        {
		                        var deletefile = deleteFile(absolutepath, ip);
		                        deletefile.then(function(data)
		                        {
			                        res.send('success');
		                        });
		                        res.send("deleted video")
	                        });
                        }
         );        

/***************************************************************************
*
*   The update function is a wrapper that is used to update the list of
*   devices that are connected to the network at present. The function
*   uses the other mentioned function to insert a row into the freesize
*   table based on the request parameters accepted on call from the 
*   client side. The parameters include the IP adress of the device and 
*   the amount of size present in the disk that can be used for storage 
*   across the network.
*
****************************************************************************/

app.post('/device/update', function(req, res)
                        {
                            var insert = insertDevice(req._remoteAddress, req.body.freesize);
                            insert.then(function(data)
                            {
	                            res.send("success");
                            });
                        }
        );

/***************************************************************************
*
*   This function is used to rename files. The function is fired upon 
*   request from the client side. The function calls the subfunctions 
*   to change the name of the file from the old name to the new name. 
*   Thus the function takes three parameters from the request. That is
*   the original name of the file, the new name of the file and the IP 
*   adress of it's location. It then will do as required that is change 
*   the name of the file from old to new. 
*
****************************************************************************/

app.post('/files/rename', function(req, res)
                        {
	                        var oldpath = req.body.oldpath;
	                        var newpath = req.body.newpath;
	                        var ip = req._remoteAddress;
	                        var renamefile = renameFile(ip, oldpath, newpath);
	                        renamefile.then(function(data)
	                        {
		                        res.send('success');
	                        });
                        }
        );

/***************************************************************************
*
*   The update function is a wrapper function to update the size of the
*   device present. This change is made only to the freesize table and is
*   thus the only wrapper function available. There are no equivalent 
*   functions for the other two tables. On request the function uses the
*   backgroung tables to update the entry of the size of the device in the
*   freesize table. 
*
****************************************************************************/

app.post('/files/updatesize', function(req, res)
                            {
                                var absolutepath = req.body.absolutepath;
                                var ip = req._remoteAddress;
                                var newsize = req.body.size;
                                var updatesize = updateSize(ip, absolutepath, newsize); 
                            }
        );

/***************************************************************************
*   
*   This function is a unique function present only as a interface to 
*   the freesize table. This function, upon request from the client side,
*   processes the request and forwards it to the other wrapper functions.
*   The functions return the value of the attribute sze, present in the 
*   table freesize. It in other words accepts the IP adress of the device 
*   that is to be tracked and returns only the amount of free size present 
*   in the device as mentioned in the table.
*
****************************************************************************/

app.get('/getfreesize/:size', function(req, res)
                            {
                                var size = req.params.size;
                                var getip = getIp(size);
                                getip.then(function(data)
                                {
	                                res.send(data.rows[0].ip);
                                });
                            }
       );

/***************************************************************************
*
*   The hash function is used to for security reasons. The getHash
*   function returns a hash of the IP address. This is matched 
*   along with the hash value sent by the request. If the values 
*   are the same then true is returned else false is returned. Thus 
*   this function is used for security purposes and prevents the 
*   usage of the device from an illegal IP address. 
*
****************************************************************************/

app.get('/auth/:hash', function(req, res)
                    {
                        var ip = req._remoteAddress;
                        var gethash = getHash(ip);
                        gethash.then(function(data)
                        {
	                        if(data == req.params.hash)
	                        {
		                        res.send('true');
	                        }
	                        else
	                        {
		                        res.send('false');
	                        }
                        });
                    }
       );



/***************************************************************************
*
*   This is the core of the Server. This functions sets the server
*   running at the specified port to accept all incoming requests on 
*   the port. As the CORS headers are set the server also accepts 
*   requests from cross domain sites. The server started is a simple 
*   Http Server and is bound to the port as mentioned. The port number 
*   that the server is listening to is kept at 3000 which is the 
*   default port that any NodeJS server would listen on. The default 
*   port is maintained as such to keep simplicity in our project, else 
*   we would need to quote the changed server port each and every time 
*   we use the port. 
*
****************************************************************************/


http.createServer(app).listen(app.get('port'), function () 
{
   console.log('Express server listening on port ' + app.get('port'));
});



/***************************************************************************
*
*   The getHash function returns a JSON String of the value of the
*   attribute hash from the table freesize wherever the IP address
*   attribute is the same as the ip as mentioned by the request. It  
*   is a very low level call to the database to retrieve information 
*   and returns a complete dump of data. The function accepts 1 
*   cleaned parameter and returns all entries in the database 
*   where the IP attribute is the same as that specific parameter. 
*   The parameter is
*       ip text
*
****************************************************************************/

var getHash = function(ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
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

/***************************************************************************
*
*   The getIp function returns a JSON String of all those IP 
*   adresses present in the freesize table whereever the size is 
*   more than the required size as mentioned by the request. It is a 
*   very low level call to the database to retrieve information 
*   and returns a complete dump of data. The function accepts 1 
*   cleaned parameter and returns all entries in the database 
*   that are greater than that specific parameter. 
*   The parameter is
*       size number
*
****************************************************************************/

var getIp = function(size)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
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
       	}
       	);
	}
	);
	return deferred.promise;
}

/***************************************************************************
*
*   The deleteFile function takes is a wrapper to connect to the 
*   database and delete the entry from the table hooks where the IP 
*   adress and the absolute path is present. The function accepts 2 
*   cleaned parameters and updates their respective entries in the 
*   database. The parameters are
*        ip text, 
*        absolutepath text 
*
****************************************************************************/

var deleteFile = function(path, ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
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
       	}
       	);
	}
	);
	return deferred.promise;	
}

/***************************************************************************
*
*   The deleteVideo function takes is a wrapper to connect to the 
*   database and delete the entry from the table Video where the IP 
*   adress and the absolute path is present. The function accepts 2 
*   cleaned parameters and updates their respective entries in the 
*   database. The parameters are
*        ip text, 
*        absolutepath text, 
*
****************************************************************************/


var deleteVideo = function(path, ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
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
       	}
       	);
	}
	);
	return deferred.promise;	
}


/***************************************************************************
*
*   The deleteIp function takes is a wrapper to connect to the database
*   and delete the entry from the table freesize where the IP adress 
*   is present. The function accepts 1 cleaned parameter and deletes 
*   the respective entry in the database. The parameter is
*        ip text, 
*
****************************************************************************/

var deleteIp = function(ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
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
           	}
       	);
	}
	);
	return deferred.promise;	
}



/***************************************************************************
*
*   The insertFile function is a wrapper to connect to the database and 
*   store the values into the table hooks. The function accepts 4 cleaned
*   parameters and stores them into the database. The parameters are
*        absolutepath text, 
*        ip text, 
*        timestamp text, 
*        size integer,
*        originip text 
*
****************************************************************************/



var insertFile = function(path, ip, timestamp, size, originip)
{
	var deferred = q.defer();//wait until query is completed
console.log(path,ip,timestamp,size,originip);
	pg.connect(conString, function(err, client, done) 
	{
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
       	}
       	);
	}
	);
	return deferred.promise;	
}


/***************************************************************************
*
*   The insertDevice function is a wrapper to connect to the database and 
*   store the values into the table freesize. The function accepts 2 cleaned
*   parameters and stores them into the database. The parameters are
*       ip text , 
*       freesize integer
*
****************************************************************************/

var insertDevice = function(ip, freesize)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
	    console.log('line 655');
        client.query("insert into freesize values('"+ip+"',"+freesize+");", function(err, result) 
        {
          done();
          	if (err)
           	{
           	 	deferred.reject(err);
           	}
          	else
           	{
           	 	deferred.resolve('success');
           	}
       	}
       	);
	}
	);
	return deferred.promise;	
}



/***************************************************************************
*
*   The updateDevice function is a wrapper to connect to the database 
*   and update the values present in the the table freesize. The function
*   accepts 2 cleaned parameters and updates their respective entries
*   in the database. The parameters are
*       ip text , 
*       freesize integer
*
****************************************************************************/

var updateDevice = function(ip, freesize)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
        client.query("update freesize set freesize="+freesize+" where ip='"+ip+"';", function(err, result) 
        {
          done();
          	if (err)
           	{
           	 	deferred.reject(err);
           	}
          	else
           	{
           	 	deferred.resolve('success');
           	}
       	}
       	);
	}
	);
	return deferred.promise;	
}


/***************************************************************************
*
*   The insertVideo function is a wrapper to connect to the database and 
*   store the values into the table video. The function accepts 5 cleaned
*   parameters and stores them into the database. The parameters are
*       absolutepath text, 
*       ip text, 
*       audiobitrate integer, 
*       videobitrate integer, 
*       server text, 
*       originip text
*
****************************************************************************/

var insertVideo = function(absolutepath, ip, audiobitrate, videobitrate, server, originip)
{
	var deferred = q.defer();
	console.log("abhinav")
	pg.connect(conString, function(err, client, done) 
	{
        client.query("insert into video values('"+absolutepath+"','"+ip+"',"+audiobitrate+","+videobitrate+",'"+server+"','"+originip+"');", function(err, result) 
        {
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
       	}
       	);
	}
	);
	console.log("before return");
	return deferred.promise;	
}


/***************************************************************************
*
*   The getVideo function returns a JSON String of all the deails 
*   of the video table present in the backend database. It is a 
*   very low level call to the database to retrieve information 
*   and returns a complete dump of data. The function accepts 1 
*   cleaned parameter The parameter is
*       ip text
*
****************************************************************************/

var getVideo = function(ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
        client.query("select * from video where originip='"+ip+"';", function(err, result) 
        {
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
       	}
       	);
	}
	);
	return deferred.promise;	
}



/***************************************************************************
*
*   The getDevice function returns a JSON String of all the deails 
*   of the freesize table present in the backend database. It is a 
*   very low level call to the database to retrieve information 
*   and returns a complete dump of data. The function does not 
*   accept any parameters.
*
****************************************************************************/

var getDevice = function()
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
        client.query("select * from freesize;", function(err, result) 
        {
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
       	}
       	);
	}
	);
	return deferred.promise;	
}

/***************************************************************************
*
*   The getFile function returns a JSON String of all the deails 
*   of the hooks table present in the backend database. It is a 
*   very low level call to the database to retrieve information 
*   and returns a complete dump of data. The function accepts 1 
*   cleaned parameter The parameter is
*       ip text
*
****************************************************************************/

var getFile = function(ip)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
        client.query("select * from hooks where originip='"+ip+"';", function(err, result) 
        {
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
       	}
       	);
	}
	);
	return deferred.promise;	
}


/***************************************************************************
*
*   The renameFile function is a wrapper to connect to the database 
*   and rename the file present at that location to the newlocation 
*   mentioned. The function accepts 3 cleaned parameters and renames 
*   their respective entries in the database. The parameters are
*       ip text , 
*       oldpath text, 
*       newpath text   
*
****************************************************************************/

var renameFile = function(ip, oldpath, newpath)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
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
       	}
       	);
	}
	);
	return deferred.promise;
}


/***************************************************************************
*
*   The updateSize function is a wrapper to connect to the database 
*   and update the values present in the the table hooks. The function
*   accepts 2 cleaned parameters and updates their respective entries
*   in the database. The parameters are
*       ip text , 
*       absolutepath text, 
*       newsize integer
*
****************************************************************************/

var updateSize = function(ip, absolutepath, newsize)
{
	var deferred = q.defer();
	pg.connect(conString, function(err, client, done) 
	{
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
       	}
       	);
	}
	);
	return deferred.promise;
}
