require(['jquery'],function($){
	var $imgHolder 	= $('#signup-bg-list');
	var $bgBtn 		= $imgHolder.find('.signup-chg-bg');
	var $target 	= $('#bg-overlay');

	$bgBtn.on('click', function(e){
		e.preventDefault();
		e.stopPropagation();

		var $el = $(this);
		if ($el.hasClass('active') || $imgHolder.hasClass('disabled'))return;

		$imgHolder.addClass('disabled');
		var url = $el.attr('src').replace('/thumbs','');

		$('<img/>').attr('src' , url).load(function(){
			$target.css('background-image', 'url("' + url + '")').addClass('bg-img');
			$imgHolder.removeClass('disabled');
			$bgBtn.removeClass('active');
			$el.addClass('active');

			$(this).remove();
		});

	});
})