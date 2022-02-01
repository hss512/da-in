function updateUser(){
    var nickname=$("#nickname").val();
    var local=$("#sido").val()+"_"+$("#gugun").val();
    $.ajax({
        type:"post",
        url:"/member/profileUpdate",
        data:{
            "nickname":nickname,
            "local":local
        }
    }).done(res=>{
        alert("회원정보가 수정완료되었습니다");
        console.log("됨");
    }).fail(error=>{
        console.log(error);
    })
}