(function ($) {
	var audio = $('#audio')[0]
	var body = $('body')
	var galMenu = $('.gal-menu')

	body.on('mousedown', function (e) {
		if(e.which !== 3 && $(e.target).parents('.gal-menu').length < 1) {
			body.find('.gal-menu').stop(true, false).animate({
				opacity: 0
			}, {
				duration: 100,
				queue: false,
				complete: function() {
					$(this).css('display', 'none')
				}
			})
			$(".circle").removeClass("open")
			$(".GalMenu").delay(400).hide(0)
			audio.pause()
			audio.currentTime = 0
		}
	})

	body.on('contextmenu', function (e) {
		e.preventDefault()
		e.stopPropagation()

		var target = e || window.event;
		var clickX = 0
		var docEl = document.documentElement
		if ((target.clientX || target.clientY) && document.body && document.body.scrollLeft !== null) {
			clickX= target.clientX + document.body.scrollLeft
		}

		// 确定标准兼容模式开启
		if ((target.clientX || target.clientY) && document.compatMode === 'CSS1Compat' && docEl && docEl.scrollLeft !== null) {
			clickX = target.clientX + docEl.scrollLeft
		}
		if (target.pageX || target.pageY) {
			clickX = target.pageX
		}

		var boundary = 150
		var top = target.clientY - boundary
		var left = (body[0] === e.target) ? clickX - boundary : target.clientX - boundary
		var clientHeight = docEl.clientHeight
		var clientWidth = docEl.clientWidth
		if (top < 0) {
			top = 0
		}
		if (clientHeight - target.clientY < 150) {
			top = clientHeight - 300
		}
		if (left < 0) {
			left = 0
		}
		if (body[0] === e.target) {
			if (clientWidth - clickX < 150) {
				left = clientWidth - 300
			}
		} else {
			if (clientWidth - target.clientX < 150) {
				left = clientWidth - 300
			}
		}

		galMenu.css({
			top: top + 'px',
			left: left + 'px',
			display: 'block'
		}).stop(true, false).animate({
			opacity: 1
		}, {
			duration: 100,
			queue: false
		})

		if ($("#gal").hasClass("open")) {
			$(".circle").removeClass("open")
			$(".GalMenu").delay(400).hide(0)
			audio.pause();
			audio.currentTime = 0
		} else {
			$(".circle").addClass("open")
			audio.play()
		}

	})

})($)