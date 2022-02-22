function memberDelete(){
    var password=$("#password").val();

    $.ajax({
        type:"get",
        url:"/api/profile/password/"+password+"/delete",
        dataType:"json"
    }).done(res=>{
        if(res===1){
            console.log("button 활성화");
            document.getElementById('area').innerHTML="<button type='submit' name='deleteButton' id='deleteButton' onclick='deletePost()' style='background-color: red;color: white;width: 100px; height: 50px;'>회원탈퇴</button>"
        }else {
            document.getElementById('area').innerHTML="<div id='area'></div>"
            console.log("api실패");
        }
    }).fail(err=>{
        console.log(err);
    })

}

function deletePost(){
    console.log("post 날라감");
    $.ajax({
        type: "delete",
        url: "/api/profile/member/delete/deleteMember",
        dataType: "json"
    }).done(res=>{
        if(res===1){
            alert("계정이삭제되었습니다.");
            window.location.href="/member/signin";
        }else{
            console.log("삭제 api 실패");
        }
    }).fail(error=>{
        console.log(error);
    })
}