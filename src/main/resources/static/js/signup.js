let join_submit;
let nicknamecheck;
let passwordcheck;
let idcheck;


function checkNickname(){

    var nickname=$("#nickname").val();

    console.log(nickname)//커밋용 주석

    $.ajax({
        type:"get",
        url:'/api/signup/nickname/'+nickname+'/exist',
        dataType: "json"
    })
        .done(res=>{
            console.log(res, "중복 api 성공")
            if(res === 1){
                alert("사용하실수 있는 닉네임입니다.")
                nicknamecheck=1;
            }else{
                alert("이미 존재하는 닉네임입니다.")
            }

        }).fail(error=>{
        console.log(error, "중복 api 오류")
    });
}

function checkId(){

    var username=$("#username").val();

    console.log(username)//커밋용 주석

    $.ajax({
        type:"get",
        url:'/api/signup/username/'+username+'/exist',
        dataType: "json"
    })
        .done(res=>{
            console.log(res, "중복 api 성공")
            if(res === 1){
                alert("사용하실수 있는 아이디입니다.")
                idcheck=1;
            }else{
                alert("이미 존재하는 아이디입니다.")
            }

        }).fail(error=>{
        console.log(error, "중복 api 오류")
    });
}

$(document).ready(function(){
    var now = new Date();
    var year = now.getFullYear();
    var mon = (now.getMonth() + 1) > 9 ? ''+(now.getMonth() + 1) : '0'+(now.getMonth() + 1);
    var day = (now.getDate()) > 9 ? ''+(now.getDate()) : '0'+(now.getDate());
    //년도 selectbox만들기
    for(var i = 1900 ; i <= year ; i++) {
        $('#year').append('<option value="' + i + '">' + i + '년</option>');
    }
     // 월별 selectbox 만들기
    for(var i=1; i <= 12; i++) {
         var mm = i > 9 ? i : "0"+i ; $('#month').append('<option value="' + mm + '">' + mm + '월</option>');
     }
    // 일별 selectbox 만들기
    for(var i=1; i <= 31; i++) {
        var dd = i > 9 ? i : "0"+i ; $('#day').append('<option value="' + dd + '">' + dd+ '일</option>');
    }
    $("#year > option[value="+year+"]").attr("selected", "true");
    $("#month > option[value="+mon+"]").attr("selected", "true");
    $("#day > option[value="+day+"]").attr("selected", "true");
})

var check = function() {

    if (document.getElementById('password').value ==

        document.getElementById('repassword').value) {

        document.getElementById('message').style.color = 'green';

        document.getElementById('message').innerHTML = '비밀번호가 일치합니다';

        passwordcheck=1;

    } else {

        document.getElementById('message').style.color = 'red';

        document.getElementById('message').innerHTML = '비밀번호가 일치하지 않습니다';

    }

}




function submitCheck(){
    join_submit=passwordcheck+nicknamecheck+idcheck;

    if(join_submit==3){
        alert("회원가입이 완료되었습니다")
    }else {
        if(nicknamecheck!=1){
            alert("닉네임중복 체크를 해주세요");
        }else if(passwordcheck!=1){
            alert("비밀번호 확인을 해주세요");
        }else if(idcheck!=1){
            alert("아이디 중복 체크를 해주세요");
        } else {
            alert("회원가입 필수 사항을 진행하여주세요");
        }

    }
    //커밋용주석
}