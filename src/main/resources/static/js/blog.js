(function ($) {
	// ------- 处理搜索侧边栏 -----------

    $('#search-form').submit(function () {
        event.preventDefault();
        var keyword=this.keyword.value;
        if(keyword === null || keyword === '') {
            $('#searchInput').focus();
        }else{
            window.location='/search/'+keyword+'/';
        }
    })

	var panelToggle = $('.panel-toggle')
	var panelRemove = $('.panel-remove')
	panelToggle.on('click', function () {
		var that = $(this)
		var panelGal = that.parents('.panel-gal')
		if(that.hasClass('fa-chevron-circle-up')) {
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

    $(".tag-cloud-link").each(function(){
        var rand = Math.floor(Math.random() * 11 + 10);
        $(this).css("font-size",rand);
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
		if($(this).scrollTop() > 200) {
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

    // ------- 处理评论 -------------

    $('#commentform').submit(function () {
        event.preventDefault();
        var content=this.content.value;
        if(content === null || content === '') {
            $('#content').focus();
            $('#content').attr('placeholder','评论内容不能为空');
        }else{
            $('#comment-submit').hide();
            $('#loading').show();
            $.post("comment/article",$(this).serialize(),function(response){
                if(response.code===1){
                    toastr.success(response.msg);
                    setTimeout(function(){location.reload()},1000);
                }else{
                    toastr.error(response.msg);
                }
                $('#comment-submit').show();
                $('#loading').hide();
            });
        }
    })

    // ------- 处理评论结束 ----------

})($)

