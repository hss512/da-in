const sort_btn = $('.sort_order')
const sort_list = $('.sort_optionItem');
const gender_btn = $('.gender_order')
const gender_optionItem = $('.gender_optionItem');

function developPageLoad(condition){
    console.log("loadPage")
    console.log(condition)

    if(condition.search("sort") === -1){
        condition = condition+"?sort=최신순&gender=전체&local=all&age=all";
    }
    console.log(condition)

    let sort = condition.substring(condition.search("sort=") + 5, condition.search("&g"));
    let gender = condition.substring(condition.search("gender=") + 7, condition.search("&l"));
    let local = condition.substring(condition.search("local=") + 6, condition.search("&a"));
    let age = condition.substr(condition.search("age=") + 4, condition.length);

    sort_btn.html(decodeURI(sort))
    sort_list.html(sessionStorage.sort)
    $('.change').html(decodeURI(gender))
    gender_optionItem.each(function (){
        if($(this).text() === decodeURI(gender)){
            $(this).html(sessionStorage.gender)
        }
    })

    if(gender === '전체'){
        gender = 'all'
    }else if(gender === '남성'){
        gender = 'male'
    }else{
        gender = 'female'
    }

    let url = "/develop?sort=" + sort + "&gender=" + gender + "&local=" + local + "&age=" + age;

    $.ajax({
        url: "/api" + url,
        type: "get",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json"
    }).done(res=>{

        console.log("res=", res)
        history.pushState(null, null, url);

    }).fail(error=>{
        console.log("error=", error)
    })
}

developPageLoad($(location).attr('href'));

const sort_change = function(item) {
    let tmp = sort_btn.text();
    sort_btn.html(item);

    $('.sort_optionItem').each(function (){
        if($(this).text() === sort_btn.text()){
            console.log($(this).text())
            $(this).html(tmp)
        }
    })
    sort_btn.parent().removeClass('active');

    sessionStorage.setItem("sort", tmp)

    let url = decodeURI($(location).attr('href'));
    console.log(url)
    if(url.substring(url.search("sort=") + 5, url.search("&g")) === "최신순"){
        url = url.replace("최신순", '좋아요순')
    }else if(url.substring(url.search("sort=") + 5, url.search("&g")) === "좋아요순"){
        url = url.replace("좋아요순", "최신순")
    }
    developPageLoad(url)
}
sort_list.each(function(){
    $(this).on('click', function (){
        sort_change($(this).text())
    })
})
sort_btn.on('click', function(){
    if(sort_btn.parent().hasClass('active')) {
        sort_btn.parent().removeClass('active');
    } else {
        sort_btn.parent().addClass('active');
    }
});

const gender_change = function(item) {
    let change = $('.change');
    let tmp = change.text();
    change.html(item);

    gender_optionItem.each(function (){
        if($(this).text() === change.text()){
            console.log($(this).text())
            $(this).html(tmp)
        }
    })
    gender_btn.parent().removeClass('active');

    sessionStorage.setItem("gender", tmp)

    let url = decodeURI($(location).attr('href'));

    url = url.replace(url.substring(url.search("gender=") + 7, url.search("&l")), change.text())

    console.log(url)

    developPageLoad(url);
}
gender_optionItem.each(function(){
    $(this).on('click', function (){
        gender_change($(this).text())
    })
})
gender_btn.on('click', function(){
    if(gender_btn.parent().hasClass('active')) {
        gender_btn.parent().removeClass('active');
    } else {
        gender_btn.parent().addClass('active');
    }
});