
var JvTask = new Class({

	toggler : null, //holds the active toggler
	page    : null, //holds the active page

	options : {
		cookieName: 'switcher'
	},

	initialize: function(toggler, element, options)
	{
		this.setOptions(options);

		var self = this;

		togglers = $ES('li', toggler);
		for (i=0; i < togglers.length; i++) {
			togglers[i].addEvent( 'click', function() { self.switchTo(this); } );
		}

		//hide all
		elements = element.getElements('div[id^=page_]');
		for (i=0; i < elements.length; i++) {
			this.hide(elements[i])
		}

		this.toggler = $E('li.active', toggler);
		this.page    = $('page_'+ this.toggler.id);

		this.show(this.page);
		if (this.options.cookieName)
		{
			if((page = Cookie.get(this.options.cookieName))) {
				this.switchTo($(page));
			}
		}
	},

	switchTo: function(toggler)
	{
		page = $chk(toggler) ? $('page_'+toggler.id) : null;
		if(page && page != this.page)
		{
			//hide old element
			if(this.page) {
				this.hide(this.page);
			}

			//show new element
			this.show(page);

			toggler.addClass('active');
			if (this.toggler) {
				this.toggler.removeClass('active');
			}
			this.page    = page;
			this.toggler = toggler;
			Cookie.set(this.options.cookieName, toggler.id);
		}
	},

	hide: function(element) {
		element.setStyle('display', 'none');
	},

	show: function (element) {
		element.setStyle('display', 'block');
	}
});

JvTask.implement(new Options);

document.switcher = null;
window.addEvent('domready', function(){

 	toggler = $('jv_market_submenu');
 	
  	element = $('jv_market_content');
  	
  	if(element) {
  		document.switcher = new JvTask(toggler, element, {cookieName: toggler.getAttribute('class')});
  	}
});
