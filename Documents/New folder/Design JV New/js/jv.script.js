//JS script for Joomla template
var siteurl = '';

function switchFontSize (ckname,val){
	var bd = $E('body');
	switch (val) {
		case 'inc':
		if (CurrentFontSize+1 < 7) {
			bd.removeClass('fs'+CurrentFontSize);
			CurrentFontSize++;
			bd.addClass('fs'+CurrentFontSize);
		}
		break;
		case 'dec':
		if (CurrentFontSize-1 > 0) {
			bd.removeClass('fs'+CurrentFontSize);
			CurrentFontSize--;
			bd.addClass('fs'+CurrentFontSize);
		}
		break;
		default:
		bd.removeClass('fs'+CurrentFontSize);
		CurrentFontSize = val;
		bd.addClass('fs'+CurrentFontSize);
	}
	Cookie.set(ckname, CurrentFontSize,{duration:365});
}

window.addEvent('load', function(){

	var StyleCookie = new Hash.Cookie('JVNews2StyleCookieSite');
	var settings = { colors: '' };
	var style_1, style_2, style_3;
	//new Asset.css(StyleCookie.get('colors'));

	/* Style 1 */
	if($('jvcolor1')){$('jvcolor1').addEvent('click', function(e) {
		e = new Event(e).stop();
		if (style_1) style_1.remove();
		new Asset.css(jvpathcolor + 'red.css', {id: 'red'});
		style_1 = $('red');
		settings['colors'] = jvpathcolor + 'red.css';
		StyleCookie.empty();
		StyleCookie.extend(settings);
	});}

	/* Style 2 */
	if($('jvcolor2')){$('jvcolor2').addEvent('click', function(e) {
		e = new Event(e).stop();
		if (style_2) style_2.remove();
		new Asset.css(jvpathcolor + 'purple.css', {id: 'purple'});
		style_2 = $('purple');
		settings['colors'] = jvpathcolor + 'purple.css';
		StyleCookie.empty();
		StyleCookie.extend(settings);
	});}

	/* Style 3 */
	if($('jvcolor3')){$('jvcolor3').addEvent('click', function(e) {
		e = new Event(e).stop();
		if (style_3) style_3.remove();
		new Asset.css(jvpathcolor + 'black.css', {id: 'black'});
		style_3 = $('black');
		settings['colors'] = jvpathcolor + 'black.css';
		StyleCookie.empty();
		StyleCookie.extend(settings);
	});}

});

