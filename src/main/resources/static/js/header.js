function alarmsClick(){

    $('.modal').remove()

    $(".alarm_").append(
        "<div class='modal'>"+
        "<div class='alarm_modal' aria-hidden='true'>"+
        "<div class='modal_effect' style='left: 352.4px;'></div>"+
        "<div class='alarm_list'>"+
        "<div class='alarm_list_content'>"+
        "<div class='alarm_clear'>"+
        "<div class='alarm_clear_btn'>" +
        "<button class='alarm_clear_button' type='button' onclick='alarmClear()'>알림 전체 삭제</button>" +
        "</div>"+
        "<div class='alarm_check_btn'>" +
        "<button class='alarm_check_button' type='button' onclick='alarmAllCheck()'>알림 전체 읽음</button>" +
        "</div>"+
        "</div>"+
        "<ul class='alarms' style='padding: 0'>"+
        "</ul>"+
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>"
    )

    $.ajax({
        url: "/api/users/alarm",
        method: "get"
    }).done(res=>{
        console.log(res)
        res.data.forEach((data)=>{
            if(data.check === 0){
                $('.alarms').append(
                    "<div class='alarm' id='alarmId_"+ data.alarmId +"' style='cursor: pointer;'>" +
                    "<div class='alarm_click' onclick='alarmClick("+ data.alarmId +","+ data.boardId +")'>" +
                    "<div class='alarm_user'>" +
                    data.member.nickname +
                    "</div>"+
                    "<div class='alarm_message'>" +
                    data.message +
                    "</div>"+
                    "</div>"+
                    "<div class='alarm_delete'>" +
                    "<button type='button' class='alarm_delete_btn' onclick='alarmDelete("+ data.alarmId +")'>x</button>" +
                    "</div>"+
                    "</div>"
                )
            }else{
                $('.alarms').append(
                    "<div class='alarm checked' id='alarmId_"+ data.alarmId +"' style='cursor: pointer;'>" +
                    "<div class='alarm_click' onclick='alarmClick("+ data.alarmId +","+ data.boardId +")'>" +
                    "<div class='alarm_user'>" +
                    data.member.nickname +
                    "</div>"+
                    "<div class='alarm_message'>" +
                    data.message +
                    "</div>"+
                    "</div>"+
                    "<div class='alarm_delete'>" +
                    "<button type='button' class='alarm_delete_btn' onclick='alarmDelete("+ data.alarmId +")'>x</button>" +
                    "</div>"+
                    "</div>"
                )
            }
        })

    }).fail(err=>{
        console.log(err)
    })
}

function alarmClear(){
    $.ajax({
        url: "/api/alarm/all",
        method: "delete"
    }).done(res=>{
        $('.alarm').remove();
        $('.alarm_btn')[0].innerText = 0;
    }).fail(err=>{

    })
}

function alarmAllCheck(){
    $.ajax({
        url: "/api/alarm/all-check",
        method: "put"
    }).done(res=>{
        $('.alarm').addClass("checked");
        $('.alarm_btn')[0].innerText = 0;
    }).fail(err=>{

    })
}

window.onclick = function (e){
    if(!$(e.target).hasClass('alarm_btn')) {
        if ($(e.target).parents('.modal').length < 1) {
            $('.modal').remove();
        }
    }
}

function alarmDelete(alarmId){
    $.ajax({
        url: "/api/alarm/" + alarmId,
        method: "delete"
    }).done(res=>{
        $('#alarmId_'+alarmId).remove();
        $('.alarm_btn')[0].innerHTML = ($('.alarm_btn')[0].innerHTML) -1;
    }).fail(err=>{
        console.log("알림 삭제 오류")
    })
}

function alarmClick(alarmId, boardId){
    if($('#alarmId_'+alarmId).length !== 0){
        $.ajax({
            url: "/api/check/alarm/" + alarmId,
            method: "put"
        }).done(res=>{
            location.href = "/board/" + boardId
        }).fail(err=>{

        })
        console.log("check")
    }
}

function ch_open(){
    $('.ch-plugin-script').css("display", "")
    $.ajax({
        url: "/api/chat/room",
        method: "get",
        async: false
    }).done(res=>{
        console.log(res.data)
        ch_clear()
        res.data.forEach(data=>{

            let title = data.title.split("-")


            title.forEach(t=>{
                if((data.myNickname !== t) && t !== ""){
                    title = t;
                }
            })

            $(".ch-main-container-body").append(
                "<ul class='chat_rooms'>" +
                "<div class='chat_room' id='roomCode_" + data.roomCode + "' style='cursor: pointer'  onclick='room_enter("+ data.id +")'>" +
                "<div class='chat_room_title'>" +
                title +
                "</div>" +
                "<div class='chat_room_exit'>" +
                "<button class='room_exit_btn' onclick='room_exit("+ data.id + ", " + data.title + ")'>x</button>" +
                "</div>" +
                "</ul>"
            )
        })
    }).fail(err=>{
        console.log(err)
    })
}

function ch_close(){
    $('#chat_main').css("display", "none")
}

function ch_clear(){
    $(".chat_rooms").remove();
}

function room_enter(roomId){

    $.ajax({
        url: "/api/chat/room/" + roomId,
        method: "get"
    }).done(res=>{

        console.log("room_enter")
        let myNick = res.data.myNickname;

        let title = res.data.title.split("-")

        title.forEach(t=>{
            if((res.data.myNickname !== t) && (t !== "")){
                title = t;
            }
        })

        $("#talk-plugin").append(
            "<div id='chat_room_main' class='ch-plugin-script rightPosition'>"+
            "<div class='ch-plugin-script-iframe' style='position:relative!important;height:100%;width:100%!important;border:none!important;'>" +
            "<div class='ch-main'>" +
            "<div class='ch-main-container'>" +
            "<div class='ch-main-container-inner'>" +
            "<div class='ch-main-container-header'>" +
            "<div class='ch-main-container-title'>" +
            title +
            "<div class='ch-close-btn'>" +
            "<button type='button' class='ch-btn' onClick='chat_room_close(" + roomId + ")'>x</button>" +
            "</div>" +
            "</div>" +
            "<div class='ch-main-container-header_'>실시간 채팅</div>" +
            "</div>" +
            "<div class='ch-main-container-body'>" +
            "<ul class='chat_message'>" +

            "</ul>" +
            "</div>" +
            "<div class='ch-main-container-footer'>" +
            "<div class='chat_input'>" +
            "<input type='text' class='ch_input' name='send_chat_message'/>"+
            "</div>"+
            "<div class='chat_input_btn'>" +
            "<button type='button' class='ch_input_btn' onclick='sendMessage(" + roomId + ")'>전송</button> " +
            "</div>"+
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>"
        )
        connectChat(roomId);

        res.data.chatList.forEach(data=>{

            if(data.memberDTO.nickname === myNick){
                if(data.chatCheck !== 0){
                    $(".chat_message").append(
                        "<div class='message my_message'>" +
                        "<div class='message_content my_message_content'>" +
                        "<div class='message_check my_message_check'>" +
                        data.chatCheck +
                        "</div>" +
                        "<div class='message_msg my_message_msg'>" +
                        data.message +
                        "</div>" +
                        "</div>" +
                        "</div>"
                    )
                }else{
                    $(".chat_message").append(
                        "<div class='message my_message'>" +
                        "<div class='message_content my_message_content'>" +
                        "<div class='message_check my_message_check'>" +
                        "</div>" +
                        "<div class='message_msg my_message_msg'>" +
                        data.message +
                        "</div>" +
                        "</div>" +
                        "</div>"
                    )
                }
            }else{
                $(".chat_message").append(
                    "<div class='message'>" +
                    "<div class='message_member'>" +
                    data.memberDTO.nickname +
                    "</div>" +
                    "<div class='message_content'>" +
                    "<div class='message_msg'>" +
                    data.message +
                    "</div>" +
                    "</div>"+
                    "</div>"
                )
            }
        })

        chatRead(roomId);

    }).fail(err=>{
        console.log(err)
    })
}

function room_exit(roomId, nickname){
    $.ajax({
        url: "/api/chat/room/exit/" + roomId,
        method: "post"
    }).done(res=>{
        sendMessage(roomId, nickname + "님이 채팅방에서 퇴장하셨습니다.");
    }).fail(err=>{

    })
}

function chat_room_close(roomId){
    $('#chat_room_main').remove();
    disconnectChat(roomId);
}

function connectChat(roomId){
    let chatSock = new SockJS("/ws/chat");
    let chatClient = Stomp.over(chatSock);
    chatClient.debug = null;
    chatSocket = chatClient;

    chatClient.connect({}, function (frame){
        console.log("채팅 소켓 연결", frame);

        chatClient.subscribe('/topic/chat/inRoom/' + roomId + "/" + frame.headers['user-name'], function (check){
            console.log("응애요..")
            console.log(check)
            console.log(check.body)
            if(check.body === "1"){
                console.log("호출되냐? 제발요..")
                $(".my_message_check").empty();
            }
        })

        chatClient.subscribe('/topic/chat/room/' + roomId, function (chat){
            console.log('=======subscribe 호출========')
            let data = JSON.parse(chat.body);
            console.log(data)

            if(data.status === 10){
                console.log("일단 입장")
                $.ajax({
                    url: "/api/chat/room/" + roomId + "/member/" + frame.headers['user-name'] +"/readChat",
                    method: "post",
                    async: false
                }).done(res=>{

                }).fail(err={

                })
                if(frame.headers['user-name'] !== data.data){
                    console.log(data.message)
                    $(".my_message_check").empty();
                    $.ajax({
                        url: "/api/chat/room/" + roomId + "/member/" + frame.headers['user-name'] +"/readChat",
                        method: "post",
                        async: false
                    }).done(res=>{

                    }).fail(err={

                    })
                }
            }else{
                if(data.memberDTO.username !== frame.headers['user-name']){
                    $.ajax({
                        url: "/api/chat/" + data.id +"/read/realTime",
                        method: "post",
                        async: false
                    }).done(res=>{
                        console.log("reaTime")
                        chatSocket.send("/chat/room/inRoom/" + roomId + "/" + data.memberDTO.username)
                    })
                }
                console.log(chat.body)
                if(frame.headers['user-name'] !== data.memberDTO.username){
                    $('.chat_message').append(
                        "<div class='message'>" +
                        "<div class='message_member'>" +
                        data.memberDTO.nickname +
                        "</div>" +
                        "<div class='message_content'>" +
                        "<div class='message_msg'>" +
                        data.message +
                        "</div>" +
                        "</div>" +
                        "</div>"
                    )
                }
            }
        })
    })
}

function sendMessage(roomId){

    let message = $("input[name=send_chat_message]").val();

    let chat = {"content": message}

    chatSocket.send('/chat/room/' + roomId, {}, JSON.stringify(chat))

    $('.chat_message').append(
        "<div class='message my_message'>" +
        "<div class='message_content my_message_content'>" +
        "<div class='message_check my_message_check'>" +
        1+
        "</div>" +
        "<div class='message_msg my_message_msg'>" +
        message +
        "</div>" +
        "</div>" +
        "</div>"
    )

    $(".ch_input").val("");
}

function disconnectChat(roomId){
    console.log(roomId)
    chatSocket.disconnect();
}

function waitForConnection(stompClient, callback) {
    setTimeout(
        function () {
            // 연결되었을 때 콜백함수 실행
            if (stompClient.ws.readyState === 1) {
                callback();
                // 연결이 안 되었으면 재호출
            } else {
                waitForConnection(stompClient, callback);
            }
        },
        1 // 밀리초 간격으로 실행
    );
}

function chatRead(roomId){
    waitForConnection(chatSocket,function() {
        chatSocket.send("/chat/room/" + roomId + "/enter", {}, JSON.stringify())
    })
}