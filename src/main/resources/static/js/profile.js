function updateUser(){
    var nickname=$("#nickname").val();
    var local=$("#sido2").val()+"_"+$("#gugun2").val();
    console.log(local);
    $.ajax({
        type:"get",
        url:"/api/profile/member/update/updateMember",
        data:{
            "nickname":nickname,
            "local":local
        },
        dataType:"json"
    }).done(res=>{
        if(res===1){
            alert("회원정보가 수정완료되었습니다");
            window.location.href="/member/myprofile";
        }else {
            console.log("수정에러");
        }
        console.log("됨");
    }).fail(error=>{
        console.log(error);
    })
}