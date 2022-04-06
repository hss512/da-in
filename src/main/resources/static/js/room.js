console.log(chatUserId);
function userCheck(id){
    const checkBox=document.getElementById("userCheck"+id);
    let changeId = $("#userCheck"+id).attr("id");
    console.log(changeId);
    console.log(checkBox.checked);
    if(checkBox.checked==true){
        document.getElementById("userButton"+id).style.visibility="visible";
    }else if(checkBox.checked==false){
        document.getElementById("userButton"+id).style.visibility="hidden";
    }
}

function subKick(id) {
    console.log("subKIck");
    stomp.send("/pub/chat/kick/user/" + id + "/" + roomId);
    stomp.send("/pub/chat/kick/" + id + "/" + roomId);
}

console.log(typeof(username));
var sockJs = new SockJS("/stomp/chat", null, {transports: ["websocket", "xhr-streaming", "xhr-polling"]});
let stomp = Stomp.over(sockJs);

function connectChat(id){
    console.log("This is connectChat이 열린것입니다!!!!!!!!!!ㅈ1");
    console.log("connectChat id 들어오나요?",id);
    console.log(roomName + ", " + roomId + ", " + username);

    stomp.connect({}, function (frame){
        console.log("STOMP Connection")
        //4. subscribe(path, callback)으로 메세지를 받을 수 있음
        stomp.subscribe("/sub/chat/room/" + roomId, function (chat) {
            // 입장, 채팅
            let content = JSON.parse(chat.body);
            if(content.messageType === "KICK" && frame.headers['user-name'] === content.message){
                $.ajax({
                    type:"get",
                    url:"/api/chat/room/kick/"+roomId+"/"+chatUserId+"/exit",
                    dataType:"json"
                })
                    .done(res=>{
                        if (res===1){
                            alert("강퇴당하셨습니다");
                            stomp.disconnect();
                            location.href = "/chat/rooms";
                        }else {
                            console.log("api개같이 멸망");
                        }
                    }).fail(err=>{
                    console.log("개같이멸망");
                })
            }else {
                console.log(frame.headers);
                console.log(content);
                let chatRoom = content.chatRoomUserCount;
                let userCount = parseInt(chatRoom);

                var writer = content.writer;
                console.log(content);
                var message = content.message;
                let chatTime2 = content.chatTime;
                let messageType = content.messageType;
                stomp.send('/pub/chat/read/'+roomId,{},JSON.stringify({"messageId":content.id,"userId":chatUserId,"roomCode":roomId}))
                var str = '';

                if (writer === username) {
                    str = "<div>";
                    str += "<div id='writeByMe' style='color: blue'>";
                    str += "<b>" + "<span style='font-size:10px;'>" + chatTime2 + "</span>" + " " + userCount + " " + writer + " : " + message + "</b>";
                    str += "</div></div>";
                    $("#msgArea").append(str);
                } else {
                    if (messageType != "KICK") {
                        str = "<div>";
                        str += "<div id='writeByYou' style='color: red'>";
                        str += "<b>" + "<span style='font-size:12px;'>" + chatTime2 + "</span>" + " "+userCount+ " " + writer + " : " + message + "</b>";
                        str += "</div></div>";
                        $("#msgArea").append(str);
                    }
                }
            }
        });
        stomp.subscribe("/sub/chat/enter/" + roomId, function (chat) {
            // 입장, 채팅
            let content = JSON.parse(chat.body);
            console.log(content);
            let chatRoom = content.chatRoomUserCount;
            let userCount = parseInt(chatRoom);

            var writer = content.writer;
            console.log(content);
            var message = content.message;
            let chatTime2 = content.chatTime;
            let messageType = content.messageType;
            var str = '';

            if (writer === username) {
                str = "<div>";
                str += "<div id='writeByMe' style='color: blue'>";
                str += "<b>" + "<span style='font-size:10px;'>" + chatTime2 + "</span>" + " " + userCount + " " + writer + " : " + message + "</b>";
                str += "</div></div>";
                $("#msgArea").append(str);
            } else {
                if (messageType != "KICK") {
                    str = "<div>";
                    str += "<div id='writeByYou' style='color: red'>";
                    str += "<b>" + "<span style='font-size:12px;'>" + chatTime2 + "</span>" + " " + userCount + " " + writer + " : " + message + "</b>";
                    str += "</div></div>";
                    $("#msgArea").append(str);
                }
            }

        });
        stomp.subscribe('/sub/chat/leave/room/' + roomId, function (chat) {
            // 나감
            let content = JSON.parse(chat.body);
            var writer = content.writer;
            console.log(content);
            var message = content.message;
            let chatTime2 = content.chatTime;
            var str = '';

            str = "<div>";
            str += "<div style='color: black'>";
            str += "<b>" + "<span style='font-size:12px;'>" + chatTime2 + "</span>" + " " + writer + " : " + message + "</b>";
            str += "</div></div>";
            $("#msgArea").append(str);
        })
        stomp.subscribe('/sub/chat/kick/room/' + roomId, function (chat) {
            // 나감
            let content = JSON.parse(chat.body);
            var writer = content.writer;
            console.log(content);
            var message = content.message;
            let chatTime2 = content.chatTime;
            var str = '';


            str = "<div>";
            str += "<div style='color: black'>";
            str += "<b>" + "<span style='font-size:12px;'>" + chatTime2 + "</span>" +" "+ message + "</b>";
            str += "</div></div>";
            $("#msgArea").append(str);
        })

        stomp.send('/pub/chat/enter/'+roomId, {}, JSON.stringify({roomId: roomId, writer: username}))
    });
};
function sendMessage(){
    var msg = document.getElementById("msg");
    console.log(username + ":" + msg.value);
    stomp.send('/pub/chat/message/'+roomId, {}, JSON.stringify({roomId: roomId, message: msg.value, writer: username}));
    msg.value = '';
}
document.addEventListener("keypress", function(e){
    if(e.keyCode == 13){
        sendMessage();
    }
});

function disconnectChat() {
    stomp.send('/pub/chat/nochat/'+roomId);
    stomp.send('/pub/chat/leave/'+roomId,{},JSON.stringify({roomId:roomId,writer:username}));
    // unsubscribeStomp(content);
    $.ajax({
        type:"get",
        url:"/api/chat/room/leave/"+roomId+"/exit",
        dataType:"json"
    })
        .done(res=>{
            if(res===1){
                alert("채팅방에서 나가셨습니다");
                console.log("유저수 1감소")
                stomp.disconnect();
            }else {
                console.log("api실패");
            }
        }).fail(err=>{
        console.error(err);
    })
    location.href='/chat/rooms';
}
connectChat();