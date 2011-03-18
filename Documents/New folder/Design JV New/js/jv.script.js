window.addEvent('domready', function(){
	var box = $('typeCity');
	if(box){
		var fx = new Fx.Styles(box, {
			duration: 600,
			wait: false,
			transition: Fx.Transitions.Quad.easeOut
		});
		box.setStyles({
			opacity: 0
		});
		
		$('toggler').addEvent('click', function() {
			fx.start({
				opacity: [0, 1],
				bottom: [-72, -4]
			});	
		});	
		$('togglerhide').addEvent('click', function() {
			fx.start({
				opacity: [1, 0],
				bottom: [-4, -72]
			});	
		});	
	}
});
function request_city() {
	var log = $('cuccess').empty().addClass('ajax-loading');
	var cityname = $('cityname').value;
    var lang = $('lang').value;
	var url = "modules/mod_jv_gweather/tmpl/city.php?cityname="+ cityname +"&lang="+ lang;
	var query = new Ajax(url, {
		method: 'get',
		onComplete: function(response) {
			log.removeClass('ajax-loading');
			if($('sugcity').value){
				reqcity = $('sugcity').value;
				do_set(reqcity);
			}
		},
		update: log
	});
	query.request();	
}
function do_set(cityname) {
    document.cookie = 'cityname=' + cityname;
    document.location.reload();   
}
function set_temprature(temp) {
    document.cookie = 'default_temperature=' + temp;
    document.location.reload();   
}