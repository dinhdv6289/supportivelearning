jQuery(document).ready(function() {

		//BOX LOGIN ERROR TEST//
		jQuery("#error").click(function() {
			jQuery("#box-login").show('shake', 55);
			jQuery(".header-login").show('shake', 55);
			return false;
		});
		
		//LANGUAGE //
		jQuery(".flag").hide();
		jQuery(".language_button").click(function() {
			jQuery(".flag").toggle('drop');
		});
		
//		//BOX SORTABLE //
//		jQuery(".column.half").sortable({
//			connectWith: '.column.half',
//			handle: '.box-header'
//		});
//		jQuery(".column.full").sortable({
//			connectWith: '.column.full',
//			handle: '.box-header'
//		});
//		jQuery(".box").find(".box-header").prepend('<span class="close"></span>').end();
//		jQuery(".box-header .close ").click(function() {
//			jQuery(this).parents(".box .box-header").toggleClass("box-header closed").toggleClass("box-header");
//			jQuery(this).parents(".box:first").find(".box-content").toggle();
//			jQuery(this).parents(".box:first").find(".example").toggle();
//		});
		
		//MESSAGE - TAG HIDE //
		jQuery(".message").click(function() {
                      jQuery(this).hide('blind', 500);
                      return false;
        });
		jQuery(".tag").click(function() {
                      jQuery(this).hide('highlight', 500);
                      return false;
        });	
		
		//SEARCH INPUT//
		jQuery("#search_input").focusin(
		function() {
			jQuery('#search_input').val('');
		});
		jQuery("#search_input").focusout(
		function() {
			jQuery('#search_input').val('Search...');
		});
		
		//VALIDATION FORM//
		var validator = jQuery("#formtest").validate({ 
        rules: { 
            firstname: {
                required: true, 
                minlength: 2
			},
            lastname: {
			    required: true, 
                minlength: 2
			},
            username: { 
                required: true, 
                minlength: 2
            }, 
            password: { 
                required: true, 
                minlength: 5 
            }, 
            password_confirm: { 
                required: true, 
                minlength: 5, 
                equalTo: "#form-password" 
            }, 
            email: { 
                required: true, 
                email: true
            }, 
			email_confirm: { 
                required: true, 
                minlength: 5, 
                equalTo: "#form-email" 
            }, 
            dateformat: "required", 
            terms: "required" 
        }, 
        messages: { 
            firstname: "Enter your firstname", 
            lastname: "Enter your lastname", 
            username: { 
                required: "Enter a username", 
                minlength: jQuery.format("Enter at least {0} characters"), 
                remote: jQuery.format("{0} is already in use") 
            }, 
            password: { 
                required: "Provide a password", 
                rangelength: jQuery.format("Enter at least {0} characters") 
            }, 
            password_confirm: { 
                required: "Repeat your password", 
                minlength: jQuery.format("Enter at least {0} characters"), 
                equalTo: "Enter the same password as above" 
            }, 
            email: { 
                required: "Please enter a valid email address", 
                minlength: "Please enter a valid email address", 
                remote: jQuery.format("{0} is already in use") 
            }, 
            dateformat: "Choose your preferred dateformat", 
            terms: "Please accept terms of use" 
        }, 
        errorPlacement: function(error, element) { 
            if ( element.is(":radio") ) 
                error.appendTo( element.parent().prev() ); 
            else if ( element.is(":checkbox") ) 
                error.appendTo ( element.parent().prev() ); 
            else 
                error.appendTo( element.prev() ); 
        }, 
        submitHandler: function() { 
            alert("Validate!"); 
        }, 
        success: function(label) { 
            label.html("&nbsp;").addClass("valid_small"); 
        } 
		}); 
		jQuery("#form-username").focus(function() { 
			var firstname = jQuery("#form-firstname").val(); 
			var lastname = jQuery("#form-lastname").val(); 
			if(firstname && lastname && !this.value) { 
				this.value = firstname + "." + lastname; 
			} 
		}); 
		jQuery("#reset").click (function(){
			jQuery("#formtest .form-field").val ("");
		});
		
		//TEXTAREA INPUT//
			jQuery("#form-message").resizable({
			handle: "se",
			containment: '#formtest'
			});
			jQuery("textarea.form-field").resizable({
			handle: "se",
			containment: '.box-content'
			});
			
		//CHECKBOX //
		jQuery(".checkbox").button();
		jQuery(".radiocheck").buttonset();
		
		//WYSIWYG//
		jQuery('#wysiwyg').wysiwyg();
		
		//TABLE//
		oTable = jQuery('#tabledata').dataTable({
				"bJQueryUI": true,
				"sPaginationType": "full_numbers"
			});
		jQuery("#checkboxall").click(function()				
		{
			var checked_status = this.checked;
			jQuery("input[name=checkall]").each(function()
			{
				this.checked = checked_status;
			});
		});
		jQuery("#checkboxalltabs").click(function()				
		{
			var checked_status = this.checked;
			jQuery("input[name=checkalltabs]").each(function()
			{
				this.checked = checked_status;
			});
		});
		jQuery("#checkboxalltabs2").click(function()				
		{
			var checked_status = this.checked;
			jQuery("input[name=checkalltabs2]").each(function()
			{
				this.checked = checked_status;
			});
		});
		
		//ACCORDION//
		jQuery(".accordion").accordion();
		
		//DIALOG//
		jQuery('.dialog').dialog({
			autoOpen: false,
			width: 800,
			height: 260,
			modal: true
		});
		jQuery('.opener').click(function() {
			jQuery('.dialog').dialog('open');
		});
		
		//DATAPICKER//
		jQuery(".datepicker").datepicker();
		
		//TABS - SORTABLE//
		jQuery(".tabs").tabs();
		jQuery(".tabs.sortable").tabs().find(".ui-tabs-nav").sortable({axis:'x'});
		
		//SKIN//
		jQuery(".skin_block").hide();
		jQuery('.skin_button').click(function() {
			jQuery(".skin_block").toggle('drop');
		});
		
		//SLIDER//
		jQuery(".slider-vertical").slider({
			orientation: "vertical",
			range: "min",
			min: 0,
			max: 100,
			value: 60,
			slide: function(event, ui) {
				jQuery(".amount-vert").val(ui.value);
			}
		});
		jQuery(".amount-vert").val(jQuery(".slider-vertical").slider("value"));
		
		jQuery(".slider-horizontal").slider({
			range: true,
			min: 0,
			max: 500,
			values: [75, 300],
			slide: function(event, ui) {
				jQuery(".amount-hor").val('jQuery' + ui.values[0] + ' - jQuery' + ui.values[1]);
			}
		});
		jQuery(".amount-hor").val('jQuery' + jQuery(".slider-horizontal").slider("values", 0) + ' - jQuery' + jQuery(".slider-horizontal").slider("values", 1));
		
		//PROGRESSBAR//
		
		jQuery(".progressbar").progressbar({value:0});
		jQuery(".progressbar .ui-progressbar-value").animate({width:'5%'}, 1500);
		
		jQuery("#prog-10").click(function() {
			jQuery(".progressbar .ui-progressbar-value").animate({width:'10%'}, 1500);
		});
		jQuery("#prog-30").click(function() {
			jQuery(".progressbar .ui-progressbar-value").animate({width:'30%'}, 1500);
		});
		jQuery("#prog-50").click(function() {
			jQuery(".progressbar .ui-progressbar-value").animate({width:'50%'}, 1500);
		});
		jQuery("#prog-70").click(function() {
			jQuery(".progressbar .ui-progressbar-value").animate({width:'70%'}, 1500);
		});
		jQuery("#prog-100").click(function() {
			jQuery(".progressbar .ui-progressbar-value").animate({width:'100%'}, 1500);
		});
		
		jQuery(".progressbaractive").progressbar({value: 0});
		jQuery(".progressbarpending").progressbar({value: 0});
		jQuery(".progressbarsuspended").progressbar({value: 0});
		
		jQuery(".progressbaractive .ui-progressbar-value").animate({width:'60%'}, 1500);
		jQuery(".progressbarpending .ui-progressbar-value").animate({width:'30%'}, 1500);
		jQuery(".progressbarsuspended .ui-progressbar-value").animate({width:'10%'}, 1500);
});