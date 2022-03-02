const sort_btn = $('.sort_order')
const sort_item = $('.sort_optionItem');
const gender_btn = $('.gender_order')
const gender_item = $('.gender_optionItem')
const local_btn = $('.local_order');
const local_item = $('.local_optionItem');
const age_btn = $('.age_order');
const age_item = $('.age_optionItem');

function developPageLoad(condition){
    console.log("loadPage")
    console.log(condition)
    if(condition.search("sort") === -1){
        sessionStorage.clear();
        condition = condition+"?sort=최신순&gender=성별_전체&local=지역_전체&age=나이_전체";
    }

    let sort = decodeURI(condition.substring(condition.search("sort=") + 5, condition.search("&g")));
    let gender = decodeURI(condition.substring(condition.search("gender=") + 7, condition.search("&l")));
    let local = decodeURI(condition.substring(condition.search("local=") + 6, condition.search("&a")));
    let age = decodeURI(condition.substr(condition.search("age=") + 4, condition.length));

    sort_btn.html(sort)
    sort_item.html(sessionStorage.sort)
    local_btn.html(local)
    local_item.html(sessionStorage.local)
    age_btn.html(age)
    age_item.html(sessionStorage.age)
    gender_btn.html(gender)
    gender_item.html(sessionStorage.gender)

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
sort_item.each(function(){
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
    let tmp = gender_btn.text();
    gender_btn.html(item);

    $('.gender_optionItem').each(function (){
        if($(this).text() === gender_btn.text()){
            console.log($(this).text())
            $(this).html(tmp)
        }
    })
    gender_btn.parent().removeClass('active');

    sessionStorage.setItem("gender", tmp)

    let url = decodeURI($(location).attr('href'));
    console.log(url)
    if(url.substring(url.search("gender=") + 7, url.search("&l")) === "성별_전체"){
        url = url.replace("성별_전체", '내_성별')
    }else if(url.substring(url.search("gender=") + 7, url.search("&l")) === "내_성별"){
        url = url.replace("내_성별", "성별_전체")
    }
    developPageLoad(url)
}
gender_item.each(function(){
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

const local_change = function(item) {
    let tmp = local_btn.text();
    local_btn.html(item);

    $('.local_optionItem').each(function (){
        if($(this).text() === local_btn.text()){
            console.log($(this).text())
            $(this).html(tmp)
        }
    })
    local_btn.parent().removeClass('active');

    sessionStorage.setItem("local", tmp)

    let url = decodeURI($(location).attr('href'));
    console.log(url)
    if(url.substring(url.search("local=") + 6, url.search("&a")) === "지역_전체"){
        url = url.replace("지역_전체", '내_지역')
    }else if(url.substring(url.search("local=") + 6, url.search("&a")) === "내_지역"){
        url = url.replace("내_지역", "지역_전체")
    }
    developPageLoad(url)
}
local_item.each(function(){
    $(this).on('click', function (){
        local_change($(this).text())
    })
})
local_btn.on('click', function(){
    if(local_btn.parent().hasClass('active')) {
        local_btn.parent().removeClass('active');
    } else {
        local_btn.parent().addClass('active');
    }
});

const age_change = function(item) {
    let tmp = age_btn.text();
    age_btn.html(item);

    $('.age_optionItem').each(function (){
        if($(this).text() === age_btn.text()){
            console.log($(this).text())
            $(this).html(tmp)
        }
    })
    age_btn.parent().removeClass('active');

    sessionStorage.setItem("age", tmp)

    let url = decodeURI($(location).attr('href'));
    console.log(url)
    if(url.substring(url.search("age=") + 4, url.length) === "나이_전체"){
        url = url.replace("나이_전체", '내_나이')
    }else if(url.substring(url.search("age=") + 4, url.length) === "내_나이"){
        url = url.replace("내_나이", "나이_전체")
    }
    developPageLoad(url)
}
age_item.each(function(){
    $(this).on('click', function (){
        age_change($(this).text())
    })
})
age_btn.on('click', function(){
    if(age_btn.parent().hasClass('active')) {
        age_btn.parent().removeClass('active');
    } else {
        age_btn.parent().addClass('active');
    }
});

function createBoard(){
    location.href = "/develop/write";
}