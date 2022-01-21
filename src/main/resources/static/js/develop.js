function developPageLoad(){
    console.log("loadPage")

    let sort = $("#sort option:selected").text();
    let gender = $("#gender option:selected").text();
    let local = $("#local option:selected").text();
    let age = $("#age option:selected").text();
    console.log("sort =", sort)
    console.log("gender =", gender)
    console.log("local =", local)
    console.log("age =", age)

    $.ajax({
        url: "api/category/develop?sort="+sort+"&gender="+gender+"&local="+local+"&age="+age,
        type: "get",
        dataType: "json"
    }).done(res=>{
        console.log("res=", res)
    }).fail(error=>{
        console.log("error=", error)
    })
}

developPageLoad();