const username = document.getElementById("username");


const form = document.getElementById("form");
const green = '#4CAF50';
const red = '#F44336';
function validateForm(){
    var confirm = document.getElementById("confirm").checked;
    return validateName()&& validatePassword() && validateRealName() && validateTele()
            && validateEmail() && validateEmID()&&confirm;
}
function validateName(){
    var name = document.getElementById("username").value;
    document.getElementById("errorname").innerHTML="";
    if (name === ''){
        document.getElementById("errorname").innerHTML="User Name shouldn't be empty";
        document.getElementById("errorname").style.color=red;
        return false;
    }
    if (name.length<3){
        document.getElementById("errorname").innerHTML="Must longer than 3 characters";
        document.getElementById("errorname").style.color=red;
        return false;
    }
    if (name.length>25){
        document.getElementById("errorname").innerHTML="Must shorter than 25 characters";
        document.getElementById("errorname").style.color=red;
        return false;
    }
    if (!(/^[a-zA-Z0-9]+$/.test(name))){
        document.getElementById("errorname").innerHTML="Characters and Numbers only";
        document.getElementById("errorname").style.color=red;
        return false;
    }
    return true;
}

function validatePassword(){
    var pwd = document.getElementById("password").value;
    document.getElementById("errorpassword").innerHTML="";
    if (pwd ===''){
        document.getElementById("errorpassword").innerHTML="Shouldn't be empty";
        document.getElementById("errorpassword").style.color=red;
        return false;
    }
    if (pwd.length<3){
        document.getElementById("errorpassword").innerHTML="Must longer than 3 characters";
        document.getElementById("errorpassword").style.color=red;
        return false;
    }
    if (rname.length>20){
        document.getElementById("errorpassword").innerHTML="Must shorter than 20 characters";
        document.getElementById("errorpassword").style.color=red;
        return false;
    }
    if (!(/^[a-zA-Z0-9]+$/.test(pwd))){
        document.getElementById("errorpassword").innerHTML="Characters and Numbers only";
        document.getElementById("errorpassword").style.color=red;
        return false;
    }
    return true;
}
function validateRealName(){
    var rname = document.getElementById("realname").value;
    document.getElementById("errorrealname").innerHTML="";
    if (rname ===''){
        document.getElementById("errorrealname").innerHTML="Shouldn't be empty";
        document.getElementById("errorrealname").style.color=red;
        return false;
    }
    if (rname.length<3){
        document.getElementById("errorrealname").innerHTML="Must longer than 3 characters";
        document.getElementById("errorrealname").style.color=red;
        return false;
    }
    if (rname.length>25){
        document.getElementById("errorrealname").innerHTML="Must shorter than 25 characters";
        document.getElementById("errorrealname").style.color=red;
        return false;
    }
    if (!( /^[A-Z][a-z]+ ?[A-Z]+$/.test(rname))){
        document.getElementById("errorrealname").innerHTML="Enter in format like Wangkai JIN";
        document.getElementById("errorrealname").style.color=red;
        return false;
    }
    return true;
}
function validateEmID(){
    var emid = document.getElementById("emid").value;
    document.getElementById("errorid").innerHTML="";
    if (emid ===''){
        document.getElementById("errorid").innerHTML="Shouldn't be empty";
        document.getElementById("errorid").style.color=red;
        return false;
    }    
    if (emid.length>20){
        document.getElementById("errorid").innerHTML="Must shorter than 25 characters";
        document.getElementById("errorid").style.color=red;
        return false;
    }   

    if (!(/^[a-zA-Z0-9]+$/.test(emid))){
        document.getElementById("errorid").innerHTML="Characters and Numbers only";
        document.getElementById("errorid").style.color=red;
        return false;
    }
    
    return true;
}

function validateTele(){
    var tele = document.getElementById("tele").value;
    document.getElementById("errortele").innerHTML="";
    if (tele ===''){
        document.getElementById("errortele").innerHTML="Shouldn't be empty";
        document.getElementById("errortele").style.color=red;
        return false;
    }    
    
    if (!(/^[0-9]+$/.test(tele))){
        document.getElementById("errortele").innerHTML="Enter valid number!";
        document.getElementById("errortele").style.color=red;
        return false;
    }
    return true;
}

function validateEmail(){
    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    var email = document.getElementById("email").value;
    document.getElementById("erroremail").innerHTML="";
    if (email ===''){
        document.getElementById("erroremail").innerHTML="Shouldn't be empty";
        document.getElementById("erroremail").style.color=red;
        return false;
    }
    if (!myreg.test(email)){
        document.getElementById("erroremail").innerHTML="Enter valid email!";
        document.getElementById("erroremail").style.color=red;
        return false;
    }
    return true;
}
function gotosignin() {
    window.top.location.href = "login.html";
}


