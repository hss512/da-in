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
            url: "api/check/alarm/" + alarmId,
            method: "put"
        }).done(res=>{
            location.href = "/board/" + boardId
        }).fail(err=>{

        })
        console.log("check")
    }
}

function ch_open(){
    $('#ch-plugin-script').css("display", "")
    $.ajax({
        url: "/api/chat/room",
        method: "get"
    }).done(res=>{
        console.log(res.data)
        ch_clear()
        res.data.forEach(data=>{
            $(".ch-main-container-body").append(
                "<ul class='chat_rooms'>" +
                "<div class='chat_room' id='roomCode_" + data.roomCode + "' style='cursor: pointer'  onclick='room_enter()'>" +
                "<div class='chat_room_title'>" +
                data.title +
                "</div>" +
                "</div>" +
                "</ul>"
            )
        })
    }).fail(err=>{
        console.log(err)
    })
}

function ch_close(){
    $('#ch-plugin-script').css("display", "none")
}

function ch_clear(){
    $(".chat_rooms").remove();
}