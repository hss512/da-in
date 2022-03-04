function doScrap(userId, boardId){

    $.ajax({
        url: "/api/scrap/"+ userId + "/" + boardId,
        method: "post"
    }).done(res=>{
        alert("스크랩 성공")
        console.log(res)
    }).fail(err=>{
        alert("스크랩 실패")
        console.log(err)
    })
}