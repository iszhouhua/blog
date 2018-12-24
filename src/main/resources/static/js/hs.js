(function ($) {
	$('#article').find('.content-article').each(function () {
		$(this).find('img').each(function () {
			if($(this).parent().prop('tagName') !== 'A') {
				$(this).wrap(`<a href="${this.src}" class="highslide-image" onclick="return hs.expand(this);"></a>`)
				$(this).parent().wrap(`<p class="gal-image"></p>`)
				$(this).parent().parent('.gal-image').wrap(`<div class="gal-image-container"></div>`)
				$(this).attr('width', 650);
				$(this).attr('title', '点击放大');
				$(this).attr('alt', '');
			}
		})
	})
})($)