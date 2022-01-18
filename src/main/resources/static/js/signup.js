function checkId(){

    var nickname=$("#nickname").val();

    console.log(nickname)//커밋용 주석

    $.ajax({
        type:"get",
        url:'/api/signup/'+nickname+'/exist',
        dataType: "json"
    })
        .done(res=>{
            console.log(res, "중복 api 성공")
            if(res === 1){
                alert("사용하실수 있는 닉네임입니다.")
            }else{
                alert("이미 존재하는 닉네임입니다.")
            }

        }).fail(error=>{
        console.log(error, "중복 api 오류")
    });
}