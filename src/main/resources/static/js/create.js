function create(userId, category){
    let nickname = $("input[name=username]").val();
    let ltAge = $("input[name=ltAge]").val();
    let rtAge = $("input[name=rtAge]").val();
    let gender = $("input[name=gender]").val();
    let local = $("input[name=local]").val();
    let content = $("textarea[name=content]").val();
    let title = $("input[name=title]").val();

    console.log(nickname)
    let data = {
        "nickname": nickname,
        "content": content,
        "title": title,
        "ltAge": ltAge,
        "rtAge": rtAge,
        "gender": gender,
        "local": local
    }

    $.ajax({
        type: "post",
        url: "/api/" + category + "/board/create",
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json"
    }).done(res=>{
        console.log(res.data)
    }).fail(err=>{
        console.log(err)
    })
}