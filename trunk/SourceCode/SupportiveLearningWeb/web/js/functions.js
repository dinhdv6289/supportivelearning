// Initialize jQuery Visualise
jQuery(function(){
	jQuery('#stats').visualize({type: 'line', height: '300px', width: '600px'});
});

// Sidebar Toggle
var fluid = {
Toggle : function(){
	var default_hide = {"grid": true };
	jQuery.each(
		["pagesnav", "commentsnav", "userssnav", "imagesnav"],
		function() {
			var el = jQuery("#" + (this == 'accordon' ? 'accordion-block' : this) );
			if (default_hide[this]) {
				el.hide();
				jQuery("[id='toggle-"+this+"']").addClass("hidden")
			}
			jQuery("[id='toggle-"+this+"']")
			.bind("click", function(e) {
				if (jQuery(this).hasClass('hidden')){
					jQuery(this).removeClass('hidden').addClass('visible');
					el.slideDown();
				} else {
					jQuery(this).removeClass('visible').addClass('hidden');
					el.slideUp();
				}
				e.preventDefault();
			});
		}
	);
}
}
jQuery(function (jQuery) {
	if(jQuery("[id^='toggle']").length){fluid.Toggle();}
});

// Notification Animations
jQuery(function () { 
jQuery('.notification').hide().append('<span class="close" title="Dismiss"></span>').fadeIn('slow');
jQuery('.notification .close').hover(
function() { jQuery(this).addClass('hover'); },
function() { jQuery(this).removeClass('hover'); }
);
jQuery('.notification .close').click(function() {
jQuery(this).parent().fadeOut('slow', function() { jQuery(this).remove(); });
}); 

});



// jQuery UI - Live Search
jQuery(function() {
		var availableTags = ["dashboard", "pages", "manage pages", "edit pages", "delete pages", "users", "manage users", "edit users", "delete users", "settings", "system settings", "server settings", "documentation", "help", "community forums", "contact"];
		jQuery("#livesearch").autocomplete({
			source: availableTags
		});
	});



// jQuery UI - Dialog Box
	jQuery(function() {
		jQuery('#dialog').dialog({
			autoOpen: false,
			modal: true,
			width: 500
		})
		jQuery('#opener').click(function() {
			jQuery('#dialog').dialog('open');
			return false;
		});

	});
	
	
