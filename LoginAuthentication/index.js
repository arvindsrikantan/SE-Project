

function init()
{
password=document.getElementById("pwd").value;
username=document.getElementById("usr").value;

//a=murmurhash2_32_gc(password, 128);
//alert(a);
b=hashcode2(password)
alert(b);
sendTo(b,username);
}

function sendTo()
{
request=new XMLHttpRequest();
hash=arguments[0];
usrname=arguments[1];
var queryString="?hash="+hash+"&usr="+usrname;
request.open("POST","login.php"+queryString,true);
request.send();

request.onreadystatechange=function(){
	if(request.readyState==4)
	{
	t=request.responseText;
	alert(t);
	if(t=="OK"){
	alert("Login Successfull");
	window.location="new.php";
	}
	else
	alert("Check the password");
	}
	
}
}

function hashcode2(str){
    var hash = 0;
    if (str.length == 0) return hash;
    for (i = 0; i < str.length; i++) {
        char = str.charCodeAt(i);
        hash = ((hash<<5)-hash)+char;
        hash = hash & hash; // Convert to 32bit integer
    }
    return hash;
}
