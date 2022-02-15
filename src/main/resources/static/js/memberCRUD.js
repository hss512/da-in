function memberDelete(){
    var password=$("#password").val();

    $.ajax({
        type:"get",
        url:"/api/profile/password/"+password+"/delete",
        dataType:"json"
    }).done(res=>{
        if(res===1){
            console.log("button 활성화");
            document.getElementById('password').clickable
        }
    })

}