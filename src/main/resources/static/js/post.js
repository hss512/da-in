function getReplyList(){

    let boardId = $(location).attr("href").substring($(location).attr("href").search("board/") + 6)

    console.log("boardId =", boardId)

    $.ajax({
        url: "/api/board/" + boardId + "/reply",
        method: "get"
    }).done(res=>{
        console.log(res)
    }).fail(err=>{

    })

}

getReplyList();

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

function reply_write(userId, boardId){

    let reply = $("input[name=reply_text]").val();

    let data = {"content": reply}

    $.ajax({
        url: "/api/board/" + boardId + "/user/" + userId,
        method: "post",
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json"
    }).done(res=>{
        console.log(res.data)
        $(".reply_list").append(
            "<div class='reply'>" +
            "<div class='reply_user'>" +
            res.data.memberDTO.nickname +
            "</div>" +
            "<div class='reply_content'>" +
            res.data.content +
            "</div>" +
            "</div>"
        );
    }).fail(err=>{
        console.log(err)
    })

}