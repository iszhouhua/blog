(function ($) {
    $.ajaxSetup({
        contentType: "application/json;charset=UTF-8"
    });
    // ------- 处理搜索侧边栏 -----------

    $('#search-form').submit(function () {
        event.preventDefault();
        var keyword = this.keyword.value;
        if (keyword === null || keyword === '') {
            $('#searchInput').focus();
        } else {
            window.location = '/search/' + keyword + '/';
        }
    })

    var panelToggle = $('.panel-toggle')
    var panelRemove = $('.panel-remove')
    panelToggle.on('click', function () {
        var that = $(this)
        var panelGal = that.parents('.panel-gal')
        if (that.hasClass('fa-chevron-circle-up')) {
            that.removeClass('fa-chevron-circle-up').addClass('fa-chevron-circle-down')
            panelGal.addClass('toggled')
        } else {
            that.removeClass('fa-chevron-circle-down').addClass('fa-chevron-circle-up')
            panelGal.removeClass('toggled')
        }
    })
    panelRemove.on('click', function () {
        var that = $(this)
        that.parents('.panel').animate({
            opacity: 0
        }, 1000, function () {
            $(this).css('display', 'none')
            // $(this).css('opacity', 1)
        })
    })

    $(".tag-cloud-link").each(function () {
        var rand = Math.floor(Math.random() * 11 + 10);
        $(this).css("font-size", rand);
    });

    var tagsTab = $('#tags-tab')
    var friendLinksTab = $('#friend-links-tab')
    var linksTab = $('#links-tab')

    friendLinksTab.tab('show')

    tagsTab.on('click', function (e) {
        e.preventDefault()
        $(this).tab('show')
    })

    friendLinksTab.on('click', function (e) {
        e.preventDefault()
        $(this).tab('show')
    })

    linksTab.on('click', function (e) {
        e.preventDefault()
        $(this).tab('show')
    })

    // ------- 处理返回顶端 -------------

    var goTop = $('#gal-gotop')
    goTop.css('bottom', '-40px')
    goTop.css('display', 'block')
    $(window).on('scroll', function () {
        if ($(this).scrollTop() > 200) {
            goTop.css('bottom', '20px')
        } else {
            goTop.css('bottom', '-40px')
        }
    })
    goTop.on('click', function () {
        $('body,html').animate({
            scrollTop: 0
        }, 800)
    })

    // ------- 处理返回顶端结束 ----------

    // ------- 处理注册 -------------

    $('#registerform').submit(function (event) {
        event.preventDefault();
        let formdata = $(this).serializeArray();
        let data = {};
        $(formdata).each(function (index, obj) {
            data[obj.name] = obj.value;
        });
        $.post("api/register", JSON.stringify(data), function (response) {
            if (response.code === 1) {
                toastr.success(response.msg);
                setTimeout(function () {
                    let redirect_to = location.search + location.hash;
                    if (redirect_to) window.location = redirect_to.substring(13);
                    else window.location = '/';
                }, 1000);
            } else {
                toastr.error(response.msg);
            }
        });
    })

    // ------- 处理注册结束 ----------

    // ------- 处理登录 -------------

    $('#loginform').submit(function (event) {
        event.preventDefault();
        let formdata = $(this).serializeArray();
        let data = {};
        $(formdata).each(function (index, obj) {
            data[obj.name] = obj.value;
        });
        $.post("api/login", JSON.stringify(data), function (response) {
            if (response.code === 1) {
                toastr.success(response.msg);
                setTimeout(function () {
                    let redirect_to = location.search + location.hash;
                    if (redirect_to) window.location = redirect_to.substring(13);
                    else window.location = '/';
                }, 1000);
            } else {
                toastr.error(response.msg);
            }
        });
    })

    // ------- 处理登录结束 ----------

    // ------- 处理评论框 -------------

    $(".comment-reply-link").click(function (event) {
        let obj = $(this).parent().parent();
        obj.after($("#respond"));
        $("#targetType").val(2);
        let parentObj = obj.parent();
        let parentId = parentObj.attr("id");
        let targetId = parentObj.parent().parent().attr("id");
        if (targetId) {
            $("#parentId").val(parentId);
            $("#targetId").val(targetId);
        } else {
            $("#parentId").val(null);
            $("#targetId").val(parentId);
        }
        $("#cancel-comment-reply-link").show();
    });

    $("#cancel-comment-reply-link").click(function (event) {
        $("#targetType").val(1);
        $("#parentId").val(null);
        $("#targetId").val($("#articleId").val());
        $("#cancel-comment-reply-link").hide();
        $("#comments-content").append($("#respond"))
    });
    // ------- 处理评论框 -------------

    // ------- 处理评论 -------------

    $('#commentform').submit(function (event) {
        event.preventDefault();
        let formdata = $(this).serializeArray();
        let data = {};
        $(formdata).each(function (index, obj) {
            if (obj.value) {
                data[obj.name] = obj.value;
            }
        });
        var content = data.content;
        if (content === null || content === '') {
            $('#content').focus();
            $('#content').attr('placeholder', '评论内容不能为空');
        } else {
            $('#comment-submit').hide();
            $('#loading').show();
            $.post("api/comment/save", JSON.stringify(data), function (response) {
                if (response.code === 1) {
                    toastr.success(response.msg);
                    setTimeout(function () {
                        location.reload()
                    }, 1000);
                } else {
                    toastr.error(response.msg);
                }
                $('#comment-submit').show();
                $('#loading').hide();
            });
        }
    })

    // ------- 处理评论结束 ----------
})($)

