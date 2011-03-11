// 
//  custom.js
//  Giornale HTML
//  
//  Created by Mark Southard on 2010-09-10.
//  Copyright 2010. All rights reserved.
// 

// Tab function
function init_tabs() {
	// does element exist; if not exit.
	if (!jQuery('ul.tabs_nav').length) {
		return;
	}
	// Show first tab in each tab area
	jQuery('div.tab_content_wrap').each(function() {
		jQuery(this).find('div.tab_content:first').show();
	});
	// Listen for click
	jQuery('a', 'ul.tabs_nav').click(function() {
		if (!jQuery(this).hasClass('current')) {
			jQuery(this).addClass('current').parent('li').siblings('li').find('a.current').removeClass('current');
			jQuery(jQuery(this).attr('href')).show().siblings('div.tab_content').hide();
		}
		this.blur();
		return false;
	});
}

// Toggles function
function init_toggles() {
	// does the element exist? if not, exit.
	if (!jQuery('h4.toggle').length) {
		return;
	}
	
	// Hide toggle areas
	jQuery('div.toggle_content').each(function() {
		jQuery(this).hide();
	});
	
	// Listen for the click to open
	jQuery('h4.toggle').toggle(
		function() {
			jQuery(this).css({'background-image' : 'url(images/toggle_bg_close.png)'});
			jQuery(this).next('div.toggle_content').css('height', jQuery(this).height() + 'px').slideDown(600);
		},
		function() {
			jQuery(this).css({'background-image' : 'url(images/toggle_bg.png)'});
			jQuery(this).next('div.toggle_content').css('height', jQuery(this).height() + 'px').slideUp(200);
		});
	
}

// Call for Validation
function init_validation() {
	// does the contact form exist?
	if (!jQuery('#contact').length) {
		return;
	}
	
	// Validity for Email Validation
	// Call Validity for Contact Page
    jQuery(function() { 
		var sumTotal = num1 + num2;
		jQuery("form").validity(function() {
            jQuery("#name")                          
				.require("You are required to enter your name"); // update error message
			jQuery("#email")
				.match("email", "You must enter a valid Email address") // update error message
				.require("You are required to enter you Email address"); // update error message
			jQuery("#message")
				.require("You are required to enter a message"); // update error message
			jQuery("#spam_check")
				.match("number")
				.sum(sumTotal, "Incorrect addition. Please try again")
				.require("You must enter the sum of these numbers"); // update error message
        });
    });
}

// Cycle Slideshow function
function init_cycle() {
	// does the element exist? if not exit.
	if (!jQuery("#slideshow").length) {
		return;
	}
	
	// Cycle Plugin
	jQuery('#slideshow').cycle({ 
	    fx:             'scrollLeft', // name of transition effect (or comma separated names, ex: fade,scrollUp,shuffle) 
	    timeout:         6000,  // milliseconds between slide transitions (0 to disable auto advance) 
	    timeoutFn:       null,  // callback for determining per-slide timeout value:  function(currSlideElement, nextSlideElement, options, forwardFlag) 
	    continuous:      0,     // true to start next transition immediately after current one completes 
	    speed:           600,  // speed of the transition (any valid fx speed value) 
	    speedIn:         null,  // speed of the 'in' transition 
	    speedOut:        null,  // speed of the 'out' transition 
	    next:            null,  // selector for element to use as click trigger for next slide 
	    prev:            null,  // selector for element to use as click trigger for previous slide 
	    prevNextClick:   null,  // callback fn for prev/next clicks:    function(isNext, zeroBasedSlideIndex, slideElement) 
	    prevNextEvent:  'click.cycle',// event which drives the manual transition to the previous or next slide 
	    pager:           null,  // selector for element to use as pager container 
	    pagerClick:      null,  // callback fn for pager clicks:    function(zeroBasedSlideIndex, slideElement) 
	    pagerEvent:     'click.cycle', // name of event which drives the pager navigation 
	    allowPagerClickBubble: false,  // allows or prevents click event on pager anchors from bubbling 
	    pagerAnchorBuilder: null, // callback fn for building anchor links:  function(index, DOMelement) 
	    before:          null,  // transition callback (scope set to element to be shown):     function(currSlideElement, nextSlideElement, options, forwardFlag) 
	    after:           null,  // transition callback (scope set to element that was shown):  function(currSlideElement, nextSlideElement, options, forwardFlag) 
	    end:             null,  // callback invoked when the slideshow terminates (use with autostop or nowrap options): function(options) 
	    easing:          null,  // easing method for both in and out transitions 
	    easeIn:          null,  // easing for "in" transition 
	    easeOut:         null,  // easing for "out" transition 
	    shuffle:         null,  // coords for shuffle animation, ex: { top:15, left: 200 } 
	    animIn:          null,  // properties that define how the slide animates in 
	    animOut:         null,  // properties that define how the slide animates out 
	    cssBefore:       null,  // properties that define the initial state of the slide before transitioning in 
	    cssAfter:        null,  // properties that defined the state of the slide after transitioning out 
	    fxFn:            null,  // function used to control the transition: function(currSlideElement, nextSlideElement, options, afterCalback, forwardFlag) 
	    height:         'auto', // container height 
	    startingSlide:   0,     // zero-based index of the first slide to be displayed 
	    sync:            1,     // true if in/out transitions should occur simultaneously 
	    random:          0,     // true for random, false for sequence (not applicable to shuffle fx) 
	    fit:             0,     // force slides to fit container 
	    containerResize: 1,     // resize container to fit largest slide 
	    pause:           0,     // true to enable "pause on hover" 
	    pauseOnPagerHover: 0,   // true to pause when hovering over pager link 
	    autostop:        0,     // true to end slideshow after X transitions (where X == slide count) 
	    autostopCount:   0,     // number of transitions (optionally used with autostop to define X) 
	    delay:           0,     // additional delay (in ms) for first transition (hint: can be negative) 
	    slideExpr:       null,  // expression for selecting slides (if something other than all children is required) 
	    cleartype:       !$.support.opacity,  // true if clearType corrections should be applied (for IE) 
	    cleartypeNoBg:   false, // set to true to disable extra cleartype fixing (leave false to force background color setting on slides) 
	    nowrap:          0,     // true to prevent slideshow from wrapping 
	    fastOnEvent:     0,     // force fast transitions when triggered manually (via pager or prev/next); value == time in ms 
	    randomizeEffects:1,     // valid when multiple effects are used; true to make the effect sequence random 
	    rev:             0,     // causes animations to transition in reverse 
	    manualTrump:     true,  // causes manual transition to stop an active transition instead of being ignored 
	    requeueOnImageNotLoaded: true, // requeue the slideshow if any image slides are not yet loaded 
	    requeueTimeout:  250,   // ms delay for requeue 
	    activePagerClass: 'activeSlide', // class name used for the active pager link 
	    updateActivePagerLink: null // callback fn invoked to update the active pager link (adds/removes activePagerClass style) 
	});
}

jQuery(document).ready(function() {
	
	// Initialize the custom functions
	init_tabs();
	init_toggles();
	init_cycle();
	init_validation();
	
	// Fix IE quirkiness
	jQuery.each(jQuery.browser, function(i, val) {
		if(i=="msie") {
			jQuery('#footer').find('ul').find('li:last-child').css({'border-bottom' : 'none'});
			jQuery('li:last-child', 'ul.social_icons').css({'margin-right' : '0'});
			jQuery('#upper_menu li:last-child').find('a').css({'border-right' : 'none'});
		}
		
		if(i=="msie" && parseInt(jQuery.browser.version)==7) {
			var zIndexNumber = 1000;
			jQuery('div').each(function() {
			$(this).css('zIndex', zIndexNumber);
			zIndexNumber -= 10;
			});
		}
	});
	
	// Slide down menus
	jQuery('ul', '#upper_menu').css({display: 'none'});
	jQuery('li', '#upper_menu').hover(function() {
		jQuery(this).find('ul').css({'visibility' : 'visible', 'display' : 'none'}).slideDown(400).css({'display' : 'block'});
	},function() {
		jQuery(this).find('ul').stop(true, true).css({'visibility' : 'visible', 'display' : 'none'});
	});
	
	jQuery('ul', '#menu').css({display:'none'});
	jQuery('li', '#menu').hover(function() {
		jQuery(this).find('ul').css({'visibility' : 'visible', 'display' : 'none'}).slideDown(400);
	},function(){
		jQuery(this).find('ul').stop(true, true).css({'visibility' : 'hidden', 'display' : 'none'});
	});
	
	// Scroll to top
	jQuery('small.scroll_top').click(function(){
		jQuery(document).scrollTo( {top:0, left:0}, 600);
	});
		
	// Pretty Photo
	jQuery("a[rel^='prettyPhoto']").prettyPhoto();
	
	// Gallery Enhancement : On hover show zoom image
	jQuery('.gallery img').hover(function(){
		jQuery(this).stop().animate({opacity:0.3}, 400);
	}, function(){
		jQuery(this).stop().animate({opacity:1}, 400);
	});
	
	// Call Video JS
	VideoJS.setupAllWhenReady();
		
	// Coin Slider
	jQuery('#coin-slider').coinslider({
		width: 940, // width of slider panel
		height: 296, // height of slider panel
		spw: 7, // squares per width
		sph: 5, // squares per height
		delay: 3000, // delay between images in ms
		sDelay: 30, // delay beetwen squares in ms
		opacity: 0.9, // opacity of title and navigation
		titleSpeed: 500, // speed of title appereance in ms
		effect: '', // random, swirl, rain, straight
		navigation: true, // prev next and buttons
		links : true, // show images as links 
		hoverPause: true // pause on hover
	});
});