function userCountCheck(id,countUser,userLimit,e){
    console.log(countUser);
    console.log(userLimit);
    console.log(id);
    $.ajax({
        type:"GET",
        url:"/api/chat/room/"+id+"/userCountCheck",
        data:{
            "countUser":countUser,
            "userLimit":userLimit,
        }
    })
        .done(res=>{
            console.log(res);
            if (res===1){
                console.log("채팅방꽉참");
                /*location.href = "/chat/rooms"*/
                alert("정원이 꽉차 이용하실수없습니다.");
            }else if (res===0) {
                console.log("채팅방입장");
            }else {
                console.log("api실패");
            }
        }).fail(err=>{
            console.log("api실패");
            console.log(err);
    });
}