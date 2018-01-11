$(function() {
    $(window).scroll(function(){
        if($(window).scrollTop() == $(document).height() - $(window).height()){
            findList();
        }
    });
    /* 点击搜索图标 */
    $('.search_icon').click(function() {
        queryList(1);
    });

});
function queryList(page) {
    var homeType =$("#homeType").val();
    if(page==1){
        $("#hair_list2").html("");
    }else{
        var nextPage=parseInt($("#page1").val())+1;
        page=nextPage;
        var pages=parseInt($("#pages1").val());
        if(nextPage>pages){
            return;
        }
    }
    var title = $.trim($('.search_input').find('input').val());
    if(title == "") {
        alert("请输入案例名称");
        return;
    }
    $.post(_ctxPath + "/w/home/queryCase",{'page':page,'title':title,"homeType":homeType},
        function(data){
            var obj=eval(data);
            $("#pages1").val(data.pages);
            $("#page1").val(data.page);
            if(obj.data.length == 0) {
                $(".hair_list").css("backgroundColor","#f8f8f8");
                var html ='<div style="float: none;border: none;"><div class="notOrder"></div><div ' +
                    'class="notOrderTitle">暂无相关数据</div></div>';
                $("#hair_list2").html($("#hair_list2").html()+ html);
                return;
            }else {
                for(var i=0;i<obj.data.length;i++){
                    var html ='<div onclick="goCaseDes(this)"><input type="hidden" value="'+obj.data[i]._id+'">'
                        +'<div class="hair_pic"><img src="'+_ossImageHost + obj.data[i].logo[0]+'@!avatar'+'"/></div><div><strong>'+
                        obj.data[i].title+'</strong></div><div class="margin_btm"><div class="hair_price_div">'+
                        '<span>&yen;</span><span>'+obj.data[i].price+'</span></div><div class="attention_font">'
                        +'<span>关注</span><span>' +obj.data[i].followCount +'</span></div></div></div>';
                    $("#hair_list2").html($("#hair_list2").html()+ html);}
            }

        },"json");//这里返回的类型有：json,html,xml,text
}
function findList(page) {
    var homeType =$("#homeType").val();
    if(page==1){
        $("#hair_list2").html("");
    }else{
        var nextPage=parseInt($("#page1").val())+1;
        page=nextPage;
        var pages=parseInt($("#pages1").val());
        if(nextPage>pages){
            return;
        }
    }
    var title = $.trim($('.search_input').find('input').val());

    $.post(_ctxPath + "/w/home/queryCase",{'page':page,'title':title,"homeType":homeType},
        function(data){
            var obj=eval(data);
            $("#pages1").val(data.pages);
            $("#page1").val(data.page);
            if(obj.data.length == 0) {
                $(".hair_list").css("backgroundColor","#f8f8f8");
                var html ='<div style="float: none;border: none;"><div class="notOrder"></div><div ' +
                    'class="notOrderTitle">暂无相关数据</div></div>';
                $("#hair_list2").html($("#hair_list2").html()+ html);
                return;
            }else {
                for(var i=0;i<obj.data.length;i++){
                    var html ='<div onclick="goCaseDes(this)"><input type="hidden" value="'+obj.data[i]._id+'">'
                        +'<div class="hair_pic"><img src="'+_ossImageHost + obj.data[i].logo[0]+'@!avatar'+'"/></div><div><strong>'+
                        obj.data[i].title+'</strong></div><div class="margin_btm"><div class="hair_price_div">'+
                        '<span>&yen;</span><span>'+obj.data[i].price+'</span></div><div class="attention_font">'
                        +'<span>关注</span><span>' +obj.data[i].followCount +'</span></div></div></div>';
                    $("#hair_list2").html($("#hair_list2").html()+ html);}
            }

        },"json");//这里返回的类型有：json,html,xml,text
}
/* 去案例详情 */
function goCaseDes(thisli){
    var caseId = $(thisli).find('input').val();
    var homeType = $.trim($('#homeType').val());
    window.location.href = _ctxPath+'/w/home/toCaseDes?caseId='+caseId + '&homeType=' + encodeURIComponent(encodeURIComponent(homeType));
}
