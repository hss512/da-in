if($('#userCheck').is(":checked")===true){
    document.getElementById("userButton").innerHTML="<button type='button' id='userButton'>강퇴</button>"
}else {
    document.getElementById("userButton").innerHTML="<div class='hidden' id='userButton'></div>"
}