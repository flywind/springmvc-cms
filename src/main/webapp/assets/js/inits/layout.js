require([ "jquery", "menu", "nanoScroller", "mresize", "bootstrap", "t5admin" ],
		function($, m, n, ms, bt, t5admin) {

			// Init layout
			t5admin.navgation();
			t5admin.aside();
			t5admin.scrollToTop();
			t5admin.megaDropdown();
			t5admin.langSelector();

			// Activate the Bootstrap tooltips
			var tooltip = $('.add-tooltip');
			if (tooltip.length)
				tooltip.tooltip();

			var popover = $('.add-popover');
			if (popover.length)
				popover.popover();

			// Update nancoscroller
			$('#navbar-container .navbar-top-links').on('shown.bs.dropdown',
					'.dropdown', function() {
						$(this).find('.nano').nanoScroller({
							preventPageScrolling : true
						});
					});

			$.flyNav('bind');
			$.flyAside('bind');

		})