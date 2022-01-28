const label = $('.sort_order')
const options = $('.sort_optionItem');

function developPageLoad(sort, gender, local, age){
    console.log("loadPage")

    if(sort === undefined){
        sort = label.text();
    }

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

const handleSelect = function(item) {
    let tmp = label.text();
    label.html(item);

    $('.sort_optionItem').each(function (){
        if($(this).text() === label.text()){
            console.log($(this).text())
            $(this).html(tmp)
        }
    })
    label.parent().removeClass('active');

    developPageLoad(label.text())
}
options.each(function(){
    $(this).on('click', function (){
        handleSelect($(this).text())
    })
})
label.on('click', function(){
    if(label.parent().hasClass('active')) {
        label.parent().removeClass('active');
    } else {
        label.parent().addClass('active');
    }
});