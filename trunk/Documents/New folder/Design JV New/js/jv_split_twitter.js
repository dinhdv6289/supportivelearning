JVSplitTwitter = new Class({
    options:{        
    },
    initialize:function(options){       
        this.setOptions(options);         
        this.getTwitter();     
    },
    getTwitter:function(){    	
         urlAjax = this.options.urlAjax+'?moduleId='+this.options.moduleId+'&isMerge='+this.options.isMerge;           
        var request = new Json.Remote(urlAjax,{            
        }).send();     
         request.addEvent('onComplete', function(jsonObj){
        if(jsonObj.twitter) {
	           var divWrap = $(this.options.twitterWrap);   
	           divWrap.removeClass('twitter-ajax-loading');
	           divWrap.innerHTML = jsonObj.twitter;
	           if(this.options.isAccord == 1){
	           var twitterAccordion = new JVAccordion('div.jv_header_wrapper','div.twitter_content',{
	               opacity: false,
	               alwaysHide:true,
	               firstItem:0,                                                    
	               onActive:function(toggler,element){ 
	                   toggler.setStyle('opacity','1');                               
	               },
	               onBackground:function(toggler,element){
	                   toggler.setStyle('opacity','0.5');
	               }                            
	           },
	           $(this.options.twitterWrap))};
	        $('jv_twitter_follow'+this.options.moduleId).setStyle('display','none');
        }
         }.bind(this));   
    }    
});
JVSplitTwitter.implement(new Options);
JVSplitTwitter.implement(new Events);