function getReplyList(replyNum){

    let boardId = $(location).attr("href").substring($(location).attr("href").search("board/") + 6)
    console.log(replyNum)
    console.log("boardId =", boardId)

    $.ajax({
        url: "/api/board/" + boardId + "/reply?page=" + replyNum,
        method: "get"
    }).done(res=>{
        console.log(res);
        $(".reply").remove()
        res.data.content.forEach(reply=>{
            if(reply.equal === 1){
                $(".reply_list").append(
                    "<div class='reply'>" +
                    "<div class='reply_user'>" +
                    reply.memberDTO.nickname +
                    "<div class='reply_delete'>" +
                    "<button class='reply_delete_btn' type='button' onclick='reply_delete("+ reply.id + "," + reply.memberDTO.id +")'>x</button>" +
                    "</div>" +
                    "</div>" +
                    "<div class='reply_content'>" +
                    reply.content +
                    "</div>" +
                    "</div>"
                )
            }else{
                $(".reply_list").append(
                    "<div class='reply'>" +
                    "<div class='reply_user'>" +
                    reply.memberDTO.nickname +
                    "</div>" +
                    "<div class='reply_content'>" +
                    reply.content +
                    "</div>" +
                    "</div>"
                )
            }
        })
        $(".reply_btn").remove()
        for (let i = 1; i <= res.data.totalPages ; i++) {
            $(".reply_page").append(
                "<div class='reply_btn'>" +
                "<button id='reply_" + i +"' type='button' name='reply_page_button' onclick='getReply(" + i + ")'>"+
                i +
                "</button>" +
                "</div>"
            )
        }
        if(replyNum === undefined){
            $("#reply_1").attr("aria-selected", true);
        }else{
            let n = replyNum+1;
            $("#reply_"+ n).attr("aria-selected", true);
        }
    }).fail(err=>{
        console.log(err);
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

    let alarm = {
        "content": reply,
        "userId": userId
    }

    if(!isStomp && socket.readyState !== 1) return;

    if(isStomp)
        socket.send('/TTT/' + boardId, {}, JSON.stringify(alarm));
    else
        socket.send(JSON.stringify(alarm));

    $.ajax({
        url: "/api/board/" + boardId + "/user/" + userId,
        method: "post",
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json"
    }).done(res=>{
        console.log(res.data)
        $("#reply_text_value").val("");
        getReplyList();
    }).fail(err=>{
        console.log(err)
    })

}

function getReply(replyNum){
    getReplyList(replyNum - 1)
}

function reply_delete(replyId, memberId){
    $.ajax({
        url: "/api/reply/" + replyId + "/member/" + memberId,
        method: "delete"
    }).done(res=>{
        if(res === 1){
            getReplyList();
        }
        console.log(res.data)
    }).fail(err=>{
        console.log(err)
    })
}