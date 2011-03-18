M.core_dock={count:0,totalcount:0,items:[],earlybinds:[],Y:null,initialised:false,delayedevent:null,preventevent:null,holdingarea:null};M.core_dock.nodes={dock:null,body:null,panel:null};M.core_dock.cfg={buffer:10,position:'left',orientation:'vertical',spacebeforefirstitem:10,removeallicon:M.util.image_url('t/dock_to_block','moodle')};M.core_dock.css={dock:'dock',dockspacer:'dockspacer',controls:'controls',body:'has_dock',buttonscontainer:'buttons_container',dockeditem:'dockeditem',dockeditemcontainer:'dockeditem_container',dockedtitle:'dockedtitle',activeitem:'activeitem'};M.core_dock.init=function(Y){if(this.initialised){return true;}
var css=this.css;this.initialised=true;this.Y=Y;this.nodes.body=Y.one(document.body);Y.augment(this.item,Y.EventTarget);Y.augment(this,Y.EventTarget,true);this.publish('dock:beforedraw',{prefix:'dock'});this.publish('dock:beforeshow',{prefix:'dock'});this.publish('dock:shown',{prefix:'dock'});this.publish('dock:hidden',{prefix:'dock'});this.publish('dock:initialised',{prefix:'dock'});this.publish('dock:itemadded',{prefix:'dock'});this.publish('dock:itemremoved',{prefix:'dock'});this.publish('dock:itemschanged',{prefix:'dock'});this.publish('dock:panelgenerated',{prefix:'dock'});this.publish('dock:panelresizestart',{prefix:'dock'});this.publish('dock:resizepanelcomplete',{prefix:'dock'});this.publish('dock:starting',{prefix:'dock',broadcast:2,emitFacade:true});this.fire('dock:starting');this.applyBinds();if(typeof(customise_dock_for_theme)==='function'){try{customise_dock_for_theme();}catch(exception){}}
var dock=Y.one('#dock');if(!dock){dock=Y.Node.create('<div id="dock" class="'+css.dock+' '+css.dock+'_'+this.cfg.position+'_'+this.cfg.orientation+'"></div>').append(Y.Node.create('<div class="'+css.buttonscontainer+'"></div>').append(Y.Node.create('<div class="'+css.dockeditemcontainer+'"></div>')));this.nodes.body.append(dock);}else{dock.addClass(css.dock+'_'+this.cfg.position+'_'+this.cfg.orientation);}
this.holdingarea=Y.Node.create('<div></div>').setStyles({display:'none'});this.nodes.body.append(this.holdingarea);if(Y.UA.ie>0&&Y.UA.ie<7){dock.setStyle('height',dock.get('winHeight')+'px');}
this.nodes.dock=dock;this.nodes.buttons=dock.one('.'+css.buttonscontainer);this.nodes.container=this.nodes.buttons.one('.'+css.dockeditemcontainer);if(Y.all('.block.dock_on_load').size()==0){dock.addClass('nothingdocked');}else{this.nodes.body.addClass(this.css.body).addClass(this.css.body+'_'+this.cfg.position+'_'+this.cfg.orientation);}
this.fire('dock:beforedraw');var removeall=Y.Node.create('<img alt="'+M.str.block.undockall+'" title="'+M.str.block.undockall+'" />');removeall.setAttribute('src',this.cfg.removeallicon);removeall.on('removeall|click',this.remove_all,this);this.nodes.buttons.appendChild(Y.Node.create('<div class="'+css.controls+'"></div>').append(removeall));new(function(Y){return{enabled:false,init:function(){M.core_dock.on('dock:itemschanged',this.checkSizing,this);Y.on('windowresize',this.checkSizing,this);},checkSizing:function(){var dock=M.core_dock;var possibleheight=dock.nodes.dock.get('offsetHeight')-dock.nodes.dock.one('.controls').get('offsetHeight')-(dock.cfg.buffer*3)-(dock.items.length*2);var totalheight=0;for(var id in dock.items){var dockedtitle=Y.one(dock.items[id].title).ancestor('.'+dock.css.dockedtitle);if(dockedtitle){if(this.enabled){dockedtitle.setStyle('height','auto');}
totalheight+=dockedtitle.get('offsetHeight')||0;}}
if(totalheight>possibleheight){this.enable(possibleheight);}},enable:function(possibleheight){var dock=M.core_dock;var runningcount=0;var usedheight=0;this.enabled=true;for(var id in dock.items){var itemtitle=Y.one(dock.items[id].title).ancestor('.'+dock.css.dockedtitle);if(!itemtitle){continue;}
var itemheight=Math.floor((possibleheight-usedheight)/(dock.count-runningcount));var offsetheight=itemtitle.get('offsetHeight');itemtitle.setStyle('overflow','hidden');if(offsetheight>itemheight){itemtitle.setStyle('height',itemheight+'px');usedheight+=itemheight;}else{usedheight+=offsetheight;}
runningcount++;}}};})(Y).init();Y.delegate('click',this.handleEvent,this.nodes.dock,'.'+this.css.dockedtitle,this,{cssselector:'.'+this.css.dockedtitle,delay:0});Y.delegate('mouseenter',this.handleEvent,this.nodes.dock,'.'+this.css.dockedtitle,this,{cssselector:'.'+this.css.dockedtitle,delay:0.5,iscontained:true,preventevent:'click',preventdelay:3});this.nodes.dock.on('mouseleave',this.handleEvent,this,{cssselector:'#dock',delay:0.5,iscontained:false});this.nodes.body.on('click',this.handleEvent,this,{cssselector:'body',delay:0});this.on('dock:itemschanged',this.resizeBlockSpace,this);this.on('dock:itemschanged',this.checkDockVisibility,this);this.on('dock:itemschanged',this.resetFirstItem,this);this.fire('dock:initialised');return true;};M.core_dock.getPanel=function(){if(this.nodes.panel===null){this.nodes.panel=(function(Y,parent){var dockpanel=Y.Node.create('<div id="dockeditempanel" class="dockitempanel_hidden"><div class="dockeditempanel_content"><div class="dockeditempanel_hd"></div><div class="dockeditempanel_bd"></div></div></div>');Y.augment(dockpanel,Y.EventTarget);dockpanel.publish('dockpanel:beforeshow',{prefix:'dockpanel'});dockpanel.publish('dockpanel:shown',{prefix:'dockpanel'});dockpanel.publish('dockpanel:beforehide',{prefix:'dockpanel'});dockpanel.publish('dockpanel:hidden',{prefix:'dockpanel'});dockpanel.publish('dockpanel:visiblechange',{prefix:'dockpanel'});dockpanel.contentNode=dockpanel.one('.dockeditempanel_content');dockpanel.contentHeader=dockpanel.contentNode.one('.dockeditempanel_hd');dockpanel.contentBody=dockpanel.contentNode.one('.dockeditempanel_bd');dockpanel.visible=false;dockpanel.show=function(){this.fire('dockpanel:beforeshow');this.visible=true;this.removeClass('dockitempanel_hidden');this.fire('dockpanel:shown');this.fire('dockpanel:visiblechange');};dockpanel.hide=function(){this.fire('dockpanel:beforehide');this.visible=false;this.addClass('dockitempanel_hidden');this.fire('dockpanel:hidden');this.fire('dockpanel:visiblechange');};dockpanel.setHeader=function(content){this.contentHeader.setContent(content);if(arguments.length>1){for(var i=1;i<arguments.length;i++){this.contentHeader.append(arguments[i]);}}};dockpanel.setBody=function(content){this.contentBody.setContent(content);};dockpanel.setTop=function(newtop){if(Y.UA.ie>0&&Y.UA.ie<7){this.setY(newtop);}else{this.setStyle('top',newtop.toString()+'px');}
return;};dockpanel.correctWidth=function(){var bd=this.one('.dockeditempanel_bd');var w=bd.get('clientWidth');var s=bd.get('scrollWidth');var ow=this.get('offsetWidth');var nw=w;var mw=Math.round(this.get('winWidth')*0.8);if(s>w){nw=w+(s-w)+((ow-w)*2)+10;}
if(nw>mw){nw=mw;}
if(nw>ow){this.setStyle('width',nw+'px');}}
parent.append(dockpanel);return dockpanel;})(this.Y,this.nodes.dock);this.nodes.panel.on('panel:visiblechange',this.resize,this);this.Y.on('windowresize',this.resize,this);this.fire('dock:panelgenerated');}
return this.nodes.panel;};M.core_dock.handleEvent=function(e,options){var item=this.getActiveItem();if(options.cssselector=='body'){if(!this.nodes.dock.contains(e.target)){if(item){item.hide();}}}else{var target;if(e.target.test(options.cssselector)){target=e.target;}else{target=e.target.ancestor(options.cssselector);}
if(!target){return true;}
if(this.preventevent!==null&&e.type===this.preventevent){return true;}
if(options.preventevent){this.preventevent=options.preventevent;if(options.preventdelay){setTimeout(function(){M.core_dock.preventevent=null;},options.preventdelay*1000);}}
if(this.delayedevent&&this.delayedevent.timeout){clearTimeout(this.delayedevent.timeout);this.delayedevent.event.detach();this.delayedevent=null;}
if(options.delay>0){return this.delayEvent(e,options,target);}
var targetid=target.get('id');if(targetid.match(/^dock_item_(\d+)_title$/)){item=this.items[targetid.replace(/^dock_item_(\d+)_title$/,'$1')];if(item.active){item.hide();}else{item.show();}}else if(item){item.hide();}}
return true;};M.core_dock.delayEvent=function(event,options,target){var self=this;self.delayedevent=(function(){return{target:target,event:self.nodes.body.on('mousemove',function(e){self.delayedevent.target=e.target;}),timeout:null};})(self);self.delayedevent.timeout=setTimeout(function(){self.delayedevent.timeout=null;self.delayedevent.event.detach();if(options.iscontained==self.nodes.dock.contains(self.delayedevent.target)){self.handleEvent(event,{cssselector:options.cssselector,delay:0,iscontained:options.iscontained});}},options.delay*1000);return true;};M.core_dock.fixTitleOrientation=function(item,title,text){var Y=this.Y;var title=Y.one(title);if(M.core_dock.cfg.orientation!='vertical'){title.setContent(text);return title}
if(Y.UA.ie>0&&Y.UA.ie<8){M.str.langconfig.thisdirectionvertical='ver';}
var clockwise=false;switch(M.str.langconfig.thisdirectionvertical){case'ver':return title.setContent(text.split('').join('<br />'));case'ttb':clockwise=true;break;case'btt':clockwise=false;break;}
if(Y.UA.ie>7){title.setContent(text);title.setAttribute('style','writing-mode: tb-rl; filter: flipV flipH;display:inline;');title.addClass('filterrotate');return title;}
var test=Y.Node.create('<h2><span style="font-size:10px;">'+text+'</span></h2>');this.nodes.body.append(test);var height=test.one('span').get('offsetWidth')+4;var width=test.one('span').get('offsetHeight')*2;var qwidth=width/4;test.remove();var txt=document.createElementNS('http://www.w3.org/2000/svg','text');txt.setAttribute('font-size','10px');if(clockwise){txt.setAttribute('transform','rotate(90 '+(qwidth/2)+' '+qwidth+')');}else{txt.setAttribute('y',height);txt.setAttribute('transform','rotate(270 '+qwidth+' '+(height-qwidth)+')');}
txt.appendChild(document.createTextNode(text));var svg=document.createElementNS('http://www.w3.org/2000/svg','svg');svg.setAttribute('version','1.1');svg.setAttribute('height',height);svg.setAttribute('width',width);svg.appendChild(txt);title.append(svg);item.on('dockeditem:drawcomplete',function(txt,title){txt.setAttribute('fill',Y.one(title).getStyle('color'));},item,txt,title);return title;};M.core_dock.resizeBlockSpace=function(node){if(this.Y.all('.block.dock_on_load').size()>0){return;}
var blockregions=[];var populatedblockregions=0;this.Y.all('.block-region').each(function(region){var hasblocks=(region.all('.block').size()>0);if(hasblocks){populatedblockregions++;}
blockregions[region.get('id')]={hasblocks:hasblocks,bodyclass:region.get('id').replace(/^region\-/,'side-')+'-only'};});var bodynode=M.core_dock.nodes.body;var showregions=false;if(bodynode.hasClass('blocks-moving')){showregions=true;}
var noblocksbodyclass='content-only';var i=null;if(populatedblockregions==0&&showregions==false){bodynode.addClass(noblocksbodyclass);for(i in blockregions){bodynode.removeClass(blockregions[i].bodyclass);}}else if(populatedblockregions==1&&showregions==false){bodynode.removeClass(noblocksbodyclass);for(i in blockregions){if(!blockregions[i].hasblocks){bodynode.removeClass(blockregions[i].bodyclass);}else{bodynode.addClass(blockregions[i].bodyclass);}}}else{bodynode.removeClass(noblocksbodyclass);for(i in blockregions){bodynode.removeClass(blockregions[i].bodyclass);}}};M.core_dock.add=function(item){item.id=this.totalcount;this.count++;this.totalcount++;this.items[item.id]=item;this.items[item.id].draw();this.fire('dock:itemadded',item);this.fire('dock:itemschanged',item);};M.core_dock.append=function(docknode){this.nodes.container.append(docknode);};M.core_dock.init_genericblock=function(Y,id){if(!this.initialised){this.init(Y);}
new this.genericblock(id).initialise_block(Y,Y.one('#inst'+id));};M.core_dock.remove=function(uid){if(!this.items[uid]){return false;}
this.items[uid].remove();delete this.items[uid];this.count--;this.fire('dock:itemremoved',uid);this.fire('dock:itemschanged',uid);return true;};M.core_dock.resetFirstItem=function(){this.nodes.dock.all('.'+this.css.dockeditem+'.firstdockitem').removeClass('firstdockeditem');if(this.nodes.dock.one('.'+this.css.dockeditem)){this.nodes.dock.one('.'+this.css.dockeditem).addClass('firstdockitem');}};M.core_dock.remove_all=function(){for(var i in this.items){this.remove(i);}
return true;};M.core_dock.hideActive=function(){var item=this.getActiveItem();if(item){item.hide();}};M.core_dock.checkDockVisibility=function(){if(!this.count){this.nodes.dock.addClass('nothingdocked');this.nodes.body.removeClass(this.css.body).removeClass(this.css.body+'_'+this.cfg.position+'_'+this.cfg.orientation);this.fire('dock:hidden');}else{this.fire('dock:beforeshow');this.nodes.dock.removeClass('nothingdocked');this.nodes.body.addClass(this.css.body).addClass(this.css.body+'_'+this.cfg.position+'_'+this.cfg.orientation);this.fire('dock:shown');}};M.core_dock.on=function(event,callback){this.earlybinds.push({event:event,callback:callback});};M.core_dock.applyBinds=function(){for(var i in this.earlybinds){var bind=this.earlybinds[i];this.on(bind.event,bind.callback);}
this.earlybinds=[];};M.core_dock.resize=function(){this.fire('dock:panelresizestart');var panel=this.getPanel();var item=this.getActiveItem();if(!panel.visible||!item){return;}
if(this.cfg.orientation=='vertical'){var buffer=this.cfg.buffer;var screenheight=parseInt(this.nodes.body.get('winHeight'))-(buffer*2);var docky=this.nodes.dock.getY();var titletop=item.nodes.docktitle.getY()-docky-buffer;var containery=this.nodes.container.getY();var containerheight=containery-docky+this.nodes.buttons.get('offsetHeight');var scrolltop=panel.contentBody.get('scrollTop');panel.contentBody.setStyle('height','auto');panel.removeClass('oversized_content');var panelheight=panel.get('offsetHeight');if(this.Y.UA.ie>0&&this.Y.UA.ie<7){panel.setTop(item.nodes.docktitle.getY());}else if(panelheight>screenheight){panel.setTop(buffer-containerheight);panel.contentBody.setStyle('height',(screenheight-panel.contentHeader.get('offsetHeight'))+'px');panel.addClass('oversized_content');}else if(panelheight>(screenheight-(titletop-buffer))){var difference=panelheight-(screenheight-titletop);panel.setTop(titletop-containerheight-difference+buffer);}else{panel.setTop(titletop-containerheight+buffer);}
if(scrolltop){panel.contentBody.set('scrollTop',scrolltop);}}
if(this.cfg.position=='right'){panel.setStyle('left',-panel.get('offsetWidth')+'px');}else if(this.cfg.position=='top'){var dockx=this.nodes.dock.getX();var titleleft=item.nodes.docktitle.getX()-dockx;panel.setStyle('left',titleleft+'px');}
this.fire('dock:resizepanelcomplete');return;};M.core_dock.getActiveItem=function(){for(var i in this.items){if(this.items[i].active){return this.items[i];}}
return false;};M.core_dock.genericblock=function(id){if(id){this.id=id;}};M.core_dock.genericblock.prototype={Y:null,id:null,cachedcontentnode:null,blockspacewidth:null,skipsetposition:false,isdocked:false,initialise_block:function(Y,node){M.core_dock.init(Y);this.Y=Y;if(!node){return false;}
var commands=node.one('.header .title .commands');if(!commands){commands=this.Y.Node.create('<div class="commands"></div>');if(node.one('.header .title')){node.one('.header .title').append(commands);}}
var moveto=Y.Node.create('<input type="image" class="moveto customcommand requiresjs" alt="'+M.str.block.addtodock+'" title="'+M.str.block.addtodock+'" />');moveto.setAttribute('src',M.util.image_url('t/block_to_dock','moodle'));moveto.on('movetodock|click',this.move_to_dock,this,commands);var blockaction=node.one('.block_action');if(blockaction){blockaction.prepend(moveto);}else{commands.append(moveto);}
if(node.hasClass('dock_on_load')){node.removeClass('dock_on_load');this.skipsetposition=true;this.move_to_dock(null,commands);}
return this;},move_to_dock:function(e,commands){if(e){e.halt(true);}
var Y=this.Y;var dock=M.core_dock;var node=Y.one('#inst'+this.id);var blockcontent=node.one('.content');if(!blockcontent){return;}
var blockclass=(function(classes){var r=/(^|\s)(block_[a-zA-Z0-9_]+)(\s|$)/;var m=r.exec(classes);return(m)?m[2]:m;})(node.getAttribute('className').toString());this.cachedcontentnode=node;node.replace(Y.Node.getDOMNode(Y.Node.create('<div id="content_placeholder_'+this.id+'" class="block_dock_placeholder"></div>')));M.core_dock.holdingarea.append(node);node=null;var blocktitle=Y.Node.getDOMNode(this.cachedcontentnode.one('.title h2')).cloneNode(true);var blockcommands=this.cachedcontentnode.one('.title .commands');if(!blockcommands){blockcommands=Y.Node.create('<div class="commands"></div>');this.cachedcontentnode.one('.title').append(blockcommands);}
var movetoimg=Y.Node.create('<img alt="'+M.str.block.undockitem+'" title="'+M.str.block.undockitem+'" />');movetoimg.setAttribute('src',M.util.image_url('t/dock_to_block','moodle'));var moveto=Y.Node.create('<a class="moveto customcommand requiresjs"></a>').append(movetoimg);if(location.href.match(/\?/)){moveto.set('href',location.href+'&dock='+this.id);}else{moveto.set('href',location.href+'?dock='+this.id);}
blockcommands.append(moveto);var dockitem=new dock.item(Y,this.id,blocktitle,blockcontent,blockcommands,blockclass);dockitem.on('dockeditem:drawcomplete',function(e){this.contents.all('.moveto').on('returntoblock|click',function(e){e.halt();dock.remove(this.id);},this);this.commands.all('.moveto').on('returntoblock|click',function(e){e.halt();dock.remove(this.id);},this);var closeicon=Y.Node.create('<span class="hidepanelicon"><img alt="" style="width:11px;height:11px;cursor:pointer;" /></span>');closeicon.one('img').setAttribute('src',M.util.image_url('t/dockclose','moodle'));closeicon.on('forceclose|click',this.hide,this);this.commands.append(closeicon);},dockitem);dockitem.on('dockeditem:itemremoved',this.return_to_block,this,dockitem);dock.add(dockitem);if(!this.skipsetposition){M.util.set_user_preference('docked_block_instance_'+this.id,1);}else{this.skipsetposition=false;}
this.isdocked=true;},return_to_block:function(dockitem){var placeholder=this.Y.one('#content_placeholder_'+this.id);if(this.cachedcontentnode.one('.header')){this.cachedcontentnode.one('.header').insert(dockitem.contents,'after');}else{this.cachedcontentnode.insert(dockitem.contents);}
placeholder.replace(this.Y.Node.getDOMNode(this.cachedcontentnode));this.cachedcontentnode=this.Y.one('#'+this.cachedcontentnode.get('id'));var commands=this.cachedcontentnode.one('.title .commands');if(commands){commands.all('.hidepanelicon').remove();commands.all('.moveto').remove();commands.remove();}
this.cachedcontentnode.one('.title').append(commands);this.cachedcontentnode=null;M.util.set_user_preference('docked_block_instance_'+this.id,0);this.isdocked=false;return true;}};M.core_dock.item=function(Y,uid,title,contents,commands,blockclass){this.Y=Y;this.publish('dockeditem:drawstart',{prefix:'dockeditem'});this.publish('dockeditem:drawcomplete',{prefix:'dockeditem'});this.publish('dockeditem:showstart',{prefix:'dockeditem'});this.publish('dockeditem:showcomplete',{prefix:'dockeditem'});this.publish('dockeditem:hidestart',{prefix:'dockeditem'});this.publish('dockeditem:hidecomplete',{prefix:'dockeditem'});this.publish('dockeditem:itemremoved',{prefix:'dockeditem'});if(uid&&this.id==null){this.id=uid;}
if(title&&this.title==null){this.titlestring=title.cloneNode(true);this.title=document.createElement(title.nodeName);M.core_dock.fixTitleOrientation(this,this.title,this.titlestring.firstChild.nodeValue);}
if(contents&&this.contents==null){this.contents=contents;}
if(commands&&this.commands==null){this.commands=commands;}
if(blockclass&&this.blockclass==null){this.blockclass=blockclass;}
this.nodes=(function(){return{docktitle:null,dockitem:null,container:null};})();};M.core_dock.item.prototype={Y:null,id:null,name:null,title:null,titlestring:null,contents:null,commands:null,active:false,blockclass:null,nodes:null,draw:function(){this.fire('dockeditem:drawstart');var Y=this.Y;var css=M.core_dock.css;this.nodes.docktitle=Y.Node.create('<div id="dock_item_'+this.id+'_title" class="'+css.dockedtitle+'"></div>');this.nodes.docktitle.append(this.title);this.nodes.dockitem=Y.Node.create('<div id="dock_item_'+this.id+'" class="'+css.dockeditem+'"></div>');if(M.core_dock.count===1){this.nodes.dockitem.addClass('firstdockitem');}
this.nodes.dockitem.append(this.nodes.docktitle);M.core_dock.append(this.nodes.dockitem);this.fire('dockeditem:drawcomplete');return true;},show:function(){M.core_dock.hideActive();var Y=this.Y;var css=M.core_dock.css;var panel=M.core_dock.getPanel();this.fire('dockeditem:showstart');panel.setHeader(this.titlestring,this.commands);panel.setBody(Y.Node.create('<div class="'+this.blockclass+' block_docked"></div>').append(this.contents));panel.show();panel.correctWidth();this.active=true;this.nodes.docktitle.addClass(css.activeitem);this.fire('dockeditem:showcomplete');M.core_dock.resize();return true;},hide:function(){var css=M.core_dock.css;this.fire('dockeditem:hidestart');this.active=false;this.nodes.docktitle.removeClass(css.activeitem);M.core_dock.getPanel().hide();this.fire('dockeditem:hidecomplete');},remove:function(){this.hide();this.nodes.dockitem.remove();this.fire('dockeditem:itemremoved');}};



YUI.add('moodle-block_navigation-navigation', function(Y){

/**
 * Navigation tree class.
 *
 * This class establishes the tree initially, creating expandable branches as
 * required, and delegating the expand/collapse event.
 */
var TREE = function(config) {
    TREE.superclass.constructor.apply(this, arguments);
}
TREE.prototype = {
    /**
     * The tree's ID, normally its block instance id.
     */
    id : null,
    /**
     * Initialise the tree object when its first created.
     */
    initializer : function(config) {
        this.id = config.id;

        var node = Y.one('#inst'+config.id);

        // Can't find the block instance within the page
        if (node === null) {
            return;
        }

        // Delegate event to toggle expansion
        var self = this;
        Y.delegate('click', function(e){self.toggleExpansion(e);}, node.one('.block_tree'), '.tree_item.branch');

        // Gather the expandable branches ready for initialisation.
        var expansions = [];
        if (config.expansions) {
            expansions = config.expansions;
        } else if (window['navtreeexpansions'+config.id]) {
            expansions = window['navtreeexpansions'+config.id];
        }
        // Establish each expandable branch as a tree branch.
        for (var i in expansions) {
            new BRANCH({
                tree:this,
                branchobj:expansions[i],
                overrides : {
                    expandable : true,
                    children : [],
                    haschildren : true
                }
            }).wire();
            M.block_navigation.expandablebranchcount++;
        }

        // Call the generic blocks init method to add all the generic stuff
        if (this.get('candock')) {
            this.initialise_block(Y, node);
        }
    },
    /**
     * This is a callback function responsible for expanding and collapsing the
     * branches of the tree. It is delegated to rather than multiple event handles.
     */
    toggleExpansion : function(e) {
        // First check if they managed to click on the li iteslf, then find the closest
        // LI ancestor and use that

        if (e.target.test('a')) {
            // A link has been clicked don't fire any more events just do the default.
            e.stopPropagation();
            return;
        }

        // Makes sure we can get to the LI containing the branch.
        var target = e.target;
        if (!target.test('li')) {
            target = target.ancestor('li')
        }
        if (!target) {
            return;
        }

        // Toggle expand/collapse providing its not a root level branch.
        if (!target.hasClass('depth_1')) {
            target.toggleClass('collapsed');
        }

        // If the accordian feature has been enabled collapse all siblings.
        if (this.get('accordian')) {
            target.siblings('li').each(function(){
                if (this.get('id') !== target.get('id') && !this.hasClass('collapsed')) {
                    this.addClass('collapsed');
                }
            });
        }

        // If this block can dock tell the dock to resize if required and check
        // the width on the dock panel in case it is presently in use.
        if (this.get('candock')) {
            M.core_dock.resize();
            var panel = M.core_dock.getPanel();
            if (panel.visible) {
                panel.correctWidth();
            }
        }
    }
}
// The tree extends the YUI base foundation.
Y.extend(TREE, Y.Base, TREE.prototype, {
    NAME : 'navigation-tree',
    ATTRS : {
        instance : {
            value : null
        },
        candock : {
            validator : Y.Lang.isBool,
            value : false
        },
        accordian : {
            validator : Y.Lang.isBool,
            value : false
        }
    }
});
if (M.core_dock && M.core_dock.genericblock) {
    Y.augment(TREE, M.core_dock.genericblock);
}

/**
 * The tree branch class.
 * This class is used to manage a tree branch, in particular its ability to load
 * its contents by AJAX.
 */
var BRANCH = function(config) {
    BRANCH.superclass.constructor.apply(this, arguments);
}
BRANCH.prototype = {
    /**
     * The node for this branch (p)
     */
    node : null,
    /**
     * A reference to the ajax load event handle when created.
     */
    event_ajaxload : null,
    /**
     * Initialises the branch when it is first created.
     */
    initializer : function(config) {
        if (config.branchobj !== null) {
            // Construct from the provided xml
            for (var i in config.branchobj) {
                this.set(i, config.branchobj[i]);
            }
            var children = this.get('children');
            this.set('haschildren', (children.length > 0));
        }
        if (config.overrides !== null) {
            // Construct from the provided xml
            for (var i in config.overrides) {
                this.set(i, config.overrides[i]);
            }
        }
        this.node = Y.one('#', this.get('id'));
    },
    /**
     * Draws the branch within the tree.
     *
     * This function creates a DOM structure for the branch and then injects
     * it into the navigation tree at the correct point.
     */
    draw : function(element) {

        var isbranch = (this.get('expandable') || this.get('haschildren'));
        var branchli = Y.Node.create('<li></li>');
        var branchp = Y.Node.create('<p class="tree_item"></p>').setAttribute('id', this.get('id'));

        if (isbranch) {
            branchli.addClass('collapsed').addClass('contains_branch');
            branchp.addClass('branch');
        }

        // Prepare the icon, should be an object representing a pix_icon
        var branchicon = false;
        var icon = this.get('icon');
        if (icon && (!isbranch || this.get('type') == 40)) {
            branchicon = Y.Node.create('<img alt="" />');
            branchicon.setAttribute('src', M.util.image_url(icon.pix, icon.component));
            branchli.addClass('item_with_icon');
            if (icon.alt) {
                branchicon.setAttribute('alt', icon.alt);
            }
            if (icon.title) {
                branchicon.setAttribute('title', icon.title);
            }
            if (icon.classes) {
                for (var i in icon.classes) {
                    branchicon.addClass(icon.classes[i]);
                }
            }
        }

        var link = this.get('link');
        if (!link) {
            if (branchicon) {
                branchp.appendChild(branchicon);
            }
            branchp.append(this.get('name'));
        } else {
            var branchlink = Y.Node.create('<a title="'+this.get('title')+'" href="'+link+'"></a>');
            if (branchicon) {
                branchlink.appendChild(branchicon);
            }
            branchlink.append(this.get('name'));
            if (this.get('hidden')) {
                branchlink.addClass('dimmed');
            }
            branchp.appendChild(branchlink);
        }

        branchli.appendChild(branchp);
        element.appendChild(branchli);
        this.node = branchp;
        return this;
    },
    /**
     * Attaches required events to the branch structure.
     */
    wire : function() {
        this.node = this.node || Y.one('#'+this.get('id'));
        if (!this.node) {
            return false;
        }
        if (this.get('expandable')) {
            this.event_ajaxload = this.node.on('ajaxload|click', this.ajaxLoad, this);
        }
        return this;
    },
    /**
     * Gets the UL element that children for this branch should be inserted into.
     */
    getChildrenUL : function() {
        var ul = this.node.next('ul');
        if (!ul) {
            ul = Y.Node.create('<ul></ul>');
            this.node.ancestor().append(ul);
        }
        return ul;
    },
    /**
     * Load the content of the branch via AJAX.
     *
     * This function calls ajaxProcessResponse with the result of the AJAX
     * request made here.
     */
    ajaxLoad : function(e) {
        e.stopPropagation();

        if (this.node.hasClass('loadingbranch')) {
            return true;
        }

        this.node.addClass('loadingbranch');

        var params = {
            elementid : this.get('id'),
            id : this.get('key'),
            type : this.get('type'),
            sesskey : M.cfg.sesskey,
            instance : this.get('tree').get('instance')
        };

        Y.io(M.cfg.wwwroot+'/lib/ajax/getnavbranch.php', {
            method:'POST',
            data:  build_querystring(params),
            on: {
                complete: this.ajaxProcessResponse
            },
            context:this
        });
        return true;
    },
    /**
     * Processes an AJAX request to load the content of this branch through
     * AJAX.
     */
    ajaxProcessResponse : function(tid, outcome) {
        this.node.removeClass('loadingbranch');
        this.event_ajaxload.detach();
        try {
            var object = Y.JSON.parse(outcome.responseText);
            if (object.children && object.children.length > 0) {
                for (var i in object.children) {
                    if (typeof(object.children[i])=='object') {
                        this.addChild(object.children[i]);
                    }
                }
                this.get('tree').toggleExpansion({target:this.node});
                return true;
            }
        } catch (ex) {
            // If we got here then there was an error parsing the result
        }
        // The branch is empty so class it accordingly
        this.node.replaceClass('branch', 'emptybranch');
        return true;
    },
    /**
     * Turns the branch object passed to the method into a proper branch object
     * and then adds it as a child of this branch.
     */
    addChild : function(branchobj) {
        // Make the new branch into an object
        var branch = new BRANCH({tree:this.get('tree'), branchobj:branchobj});
        if (branch.draw(this.getChildrenUL())) {
            branch.wire();
            var count = 0, i, children = branch.get('children');
            for (i in children) {
                // Add each branch to the tree
                if (children[i].type == 20) {
                    count++;
                }
                if (typeof(children[i])=='object') {
                    branch.addChild(children[i]);
                }
            }
            if (branch.get('type') == 10 && count >= M.block_navigation.courselimit) {
                branch.addChild({
                    name : M.str.moodle.viewallcourses,
                    title : M.str.moodle.viewallcourses,
                    link : M.cfg.wwwroot+'/course/category.php?id='+branch.get('key'),
                    haschildren : false,
                    icon : {'pix':"i/navigationitem",'component':'moodle'}
                }, branch);
            }
        }
        return true;
    }
}
Y.extend(BRANCH, Y.Base, BRANCH.prototype, {
    NAME : 'navigation-branch',
    ATTRS : {
        tree : {
            validator : Y.Lang.isObject
        },
        name : {
            value : '',
            validator : Y.Lang.isString,
            setter : function(val) {
                return val.replace(/\n/g, '<br />');
            }
        },
        title : {
            value : '',
            validator : Y.Lang.isString
        },
        id : {
            value : '',
            validator : Y.Lang.isString,
            getter : function(val) {
                if (val == '') {
                    val = 'expandable_branch_'+M.block_navigation.expandablebranchcount;
                    M.block_navigation.expandablebranchcount++;
                }
                return val;
            }
        },
        key : {
            value : null
        },
        type : {
            value : null
        },
        link : {
            value : false
        },
        icon : {
            value : false,
            validator : Y.Lang.isObject
        },
        expandable : {
            value : false,
            validator : Y.Lang.isBool
        },
        hidden : {
            value : false,
            validator : Y.Lang.isBool
        },
        haschildren : {
            value : false,
            validator : Y.Lang.isBool
        },
        children : {
            value : [],
            validator : Y.Lang.isArray
        }
    }
});

/**
 * This namespace will contain all of the contents of the navigation blocks
 * global navigation and settings.
 * @namespace
 */
M.block_navigation = M.block_navigation || {
    /** The number of expandable branches in existence */
    expandablebranchcount:1,
    courselimit : 20,
    instance : null,
    /**
     * Add new instance of navigation tree to tree collection
     */
    init_add_tree:function(properties) {
        if (properties.courselimit) {
            this.courselimit = properties.courselimit;
        }
        if (M.core_dock) {
            M.core_dock.init(Y);
        }
        new TREE(properties);
    }
};

}, '@VERSION@', {requires:['base', 'core_dock', 'io', 'node', 'dom', 'event-custom', 'event-delegate', 'json-parse']});





/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("queue-promote",function(A){A.mix(A.Queue.prototype,{indexOf:function(B){return A.Array.indexOf(this._q,B);},promote:function(C){var B=this.indexOf(C);if(B>-1){this._q.unshift(this._q.splice(B,1));}},remove:function(C){var B=this.indexOf(C);if(B>-1){this._q.splice(B,1);}}});},"3.2.0",{requires:["yui-base"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("datatype-xml-parse",function(B){var A=B.Lang;B.mix(B.namespace("DataType.XML"),{parse:function(E){var D=null;if(A.isString(E)){try{if(!A.isUndefined(DOMParser)){D=new DOMParser().parseFromString(E,"text/xml");}}catch(F){try{if(!A.isUndefined(ActiveXObject)){D=new ActiveXObject("Microsoft.XMLDOM");D.async=false;D.loadXML(E);}}catch(C){}}}if((A.isNull(D))||(A.isNull(D.documentElement))||(D.documentElement.nodeName==="parsererror")){}return D;}});B.namespace("Parsers").xml=B.DataType.XML.parse;},"3.2.0");YUI.add("datatype-xml-format",function(B){var A=B.Lang;B.mix(B.namespace("DataType.XML"),{format:function(C){try{if(!A.isUndefined(XMLSerializer)){return(new XMLSerializer()).serializeToString(C);}}catch(D){if(C&&C.xml){return C.xml;}else{return(A.isValue(C)&&C.toString)?C.toString():"";}}}});},"3.2.0");YUI.add("datatype-xml",function(A){},"3.2.0",{use:["datatype-xml-parse","datatype-xml-format"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("io-base",function(D){var d="io:start",P="io:complete",B="io:success",F="io:failure",e="io:end",X=0,O={"X-Requested-With":"XMLHttpRequest"},Z={},K=D.config.win;function L(){return K.XMLHttpRequest?new XMLHttpRequest():new ActiveXObject("Microsoft.XMLHTTP");}function E(){var Y=X;X++;return Y;}function W(g,Y){var f={};f.id=D.Lang.isNumber(Y)?Y:E();g=g||{};if(!g.use&&!g.upload){f.c=L();}else{if(g.use){if(g.use==="native"){if(K.XDomainRequest){f.c=new XDomainRequest();f.t=g.use;}else{f.c=L();}}else{f.c=D.io._transport[g.use];f.t=g.use;}}else{f.c={};}}return f;}function I(Y){if(K&&K.XMLHttpRequest){if(Y.c){Y.c.onreadystatechange=null;}}Y.c=null;Y=null;}function Q(h,i){var g=new D.EventTarget().publish("transaction:"+h),Y=i.arguments,f=i.context||D;if(Y){g.on(i.on[h],f,Y);}else{g.on(i.on[h],f);}return g;}function U(g,f){var Y=f.arguments;if(Y){D.fire(d,g,Y);}else{D.fire(d,g);}if(f.on&&f.on.start){Q("start",f).fire(g);}}function G(g,h){var f=g.e?{status:0,statusText:g.e}:g.c,Y=h.arguments;if(Y){D.fire(P,g.id,f,Y);}else{D.fire(P,g.id,f);}if(h.on&&h.on.complete){Q("complete",h).fire(g.id,f);}}function J(f,g){var Y=g.arguments;if(Y){D.fire(e,f.id,Y);}else{D.fire(e,f.id);}if(g.on&&g.on.end){Q("end",g).fire(f.id);}I(f);}function T(f,g){var Y=g.arguments;if(Y){D.fire(B,f.id,f.c,Y);}else{D.fire(B,f.id,f.c);}if(g.on&&g.on.success){Q("success",g).fire(f.id,f.c);}J(f,g);}function H(g,h){var f=g.e?{status:0,statusText:g.e}:g.c,Y=h.arguments;if(Y){D.fire(F,g.id,f,Y);}else{D.fire(F,g.id,f);}if(h.on&&h.on.failure){Q("failure",h).fire(g.id,f);}J(g,h);}function A(g,Y,h,f){I(g);h.xdr.use="flash";h.data=h.form&&f?f:null;return D.io(Y,h,g.id);}function R(Y,f){Y+=((Y.indexOf("?")==-1)?"?":"&")+f;return Y;}function V(Y,f){if(f){O[Y]=f;}else{delete O[Y];}}function C(g,Y){var f;Y=Y||{};for(f in O){if(O.hasOwnProperty(f)){if(Y[f]){continue;}else{Y[f]=O[f];}}}for(f in Y){if(Y.hasOwnProperty(f)){g.setRequestHeader(f,Y[f]);}}}function N(f,Y){if(f&&f.c){f.e=Y;f.c.abort();}}function S(f,Y){Z[f.id]=K.setTimeout(function(){N(f,"timeout");},Y);}function M(Y){K.clearTimeout(Z[Y]);delete Z[Y];}function b(g,h){var Y;try{if(g.c.status&&g.c.status!==0){Y=g.c.status;}else{Y=0;}}catch(f){Y=0;}if(Y>=200&&Y<300||Y===1223){T(g,h);}else{H(g,h);}}function c(Y,f){if(Y.c.readyState===4){if(f.timeout){M(Y.id);}K.setTimeout(function(){G(Y,f);b(Y,f);},0);}}function a(h,w,p){var q,g,t,k,Y,AA,n,y,l,z=h;w=D.Object(w);g=W(w.xdr||w.form,p);k=w.method?w.method=w.method.toUpperCase():w.method="GET";AA=w.sync;n=w.data;if(D.Lang.isObject(w.data)&&D.QueryString){w.data=D.QueryString.stringify(w.data);}if(w.form){if(w.form.upload){return D.io.upload(g,h,w);}else{q=D.io._serialize(w.form,w.data);if(k==="POST"||k==="PUT"){w.data=q;}else{if(k==="GET"){h=R(h,q);}}}}if(w.data&&k==="GET"){h=R(h,w.data);}if(w.data&&k==="POST"){w.headers=D.merge({"Content-Type":"application/x-www-form-urlencoded; charset=UTF-8"},w.headers);}if(g.t){return D.io.xdr(h,g,w);}if(!AA){g.c.onreadystatechange=function(){c(g,w);};}try{g.c.open(k,h,AA?false:true);if(w.xdr&&w.xdr.credentials){g.c.withCredentials=true;}}catch(x){if(w.xdr){return A(g,z,w,n);}}C(g.c,w.headers);U(g.id,w);try{g.c.send(w.data||"");if(AA){t=g.c;y=["status","statusText","responseText","responseXML"];Y=w.arguments?{id:g.id,arguments:w.arguments}:{id:g.id};for(l=0;l<4;l++){Y[y[l]]=g.c[y[l]];}Y.getAllResponseHeaders=function(){return t.getAllResponseHeaders();};Y.getResponseHeader=function(f){return t.getResponseHeader(f);};G(g,w);b(g,w);return Y;}}catch(v){if(w.xdr){return A(g,z,w,n);}}if(w.timeout){S(g,w.timeout);}return{id:g.id,abort:function(){return g.c?N(g,"abort"):false;},isInProgress:function(){return g.c?g.c.readyState!==4&&g.c.readyState!==0:false;}};}a.start=U;a.complete=G;a.success=T;a.failure=H;a.end=J;a._id=E;a._timeout=Z;a.header=V;D.io=a;D.io.http=a;},"3.2.0",{optional:["querystring-stringify-simple"],requires:["event-custom-base"]});YUI.add("io-form",function(B){var A=encodeURIComponent;B.mix(B.io,{_serialize:function(M,R){var I=[],N=M.useDisabled||false,Q=0,C=(typeof M.id==="string")?M.id:M.id.getAttribute("id"),K,J,E,P,L,H,O,F,G,D;if(!C){C=B.guid("io:");M.id.setAttribute("id",C);}J=B.config.doc.getElementById(C);for(H=0,O=J.elements.length;H<O;++H){K=J.elements[H];L=K.disabled;E=K.name;if(N?E:E&&!L){E=A(E)+"=";P=A(K.value);switch(K.type){case"select-one":if(K.selectedIndex>-1){D=K.options[K.selectedIndex];I[Q++]=E+A(D.attributes.value&&D.attributes.value.specified?D.value:D.text);}break;case"select-multiple":if(K.selectedIndex>-1){for(F=K.selectedIndex,G=K.options.length;F<G;++F){D=K.options[F];if(D.selected){I[Q++]=E+A(D.attributes.value&&D.attributes.value.specified?D.value:D.text);}}}break;case"radio":case"checkbox":if(K.checked){I[Q++]=E+P;}break;case"file":case undefined:case"reset":case"button":break;case"submit":default:I[Q++]=E+P;}}}return R?I.join("&")+"&"+R:I.join("&");}},true);},"3.2.0",{requires:["io-base","node-base"]});YUI.add("io-xdr",function(C){var K=C.publish("io:xdrReady",{fireOnce:true}),F={},G={},B=L&&L.XDomainRequest,J=C.config.doc,L=C.config.win;function H(M,P){var N='<object id="yuiIoSwf" type="application/x-shockwave-flash" data="'+M+'" width="0" height="0">'+'<param name="movie" value="'+M+'">'+'<param name="FlashVars" value="yid='+P+'">'+'<param name="allowScriptAccess" value="always">'+"</object>",O=J.createElement("div");J.body.appendChild(O);O.innerHTML=N;}function A(M,N){M.c.onprogress=function(){G[M.id]=3;};M.c.onload=function(){G[M.id]=4;C.io.xdrResponse(M,N,"success");};M.c.onerror=function(){G[M.id]=4;C.io.xdrResponse(M,N,"failure");};if(N.timeout){M.c.ontimeout=function(){G[M.id]=4;C.io.xdrResponse(M,N,"timeout");};M.c.timeout=N.timeout;}}function D(Q,P,N){var O,M;if(!Q.e){O=P?decodeURI(Q.c.responseText):Q.c.responseText;M=N==="xml"?C.DataType.XML.parse(O):null;return{id:Q.id,c:{responseText:O,responseXML:M}};}else{return{id:Q.id,status:Q.e};}}function I(M,N){return M.c.abort(M.id,N);}function E(M){return B?G[M.id]!==4:M.c.isInProgress(M.id);}C.mix(C.io,{_transport:{},xdr:function(M,N,O){if(O.on&&O.xdr.use==="flash"){F[N.id]={on:O.on,context:O.context,arguments:O.arguments};
O.context=null;O.form=null;N.c.send(M,O,N.id);}else{if(B){A(N,O);N.c.open(O.method||"GET",M);N.c.send(O.data);}else{N.c.send(M,N,O);}}return{id:N.id,abort:function(){return N.c?I(N,O):false;},isInProgress:function(){return N.c?E(N.id):false;}};},xdrResponse:function(R,S,Q){var N,M=B?G:F,P=S.xdr.use==="flash"?true:false,O=S.xdr.dataType;S.on=S.on||{};if(P){N=F[R.id]?F[R.id]:null;if(N){S.on=N.on;S.context=N.context;S.arguments=N.arguments;}}switch(Q.toLowerCase()){case"start":C.io.start(R.id,S);break;case"complete":C.io.complete(R,S);break;case"success":C.io.success(O||P?D(R,P,O):R,S);delete M[R.id];break;case"timeout":case"abort":case"failure":if(Q===("abort"||"timeout")){R.e=Q;}C.io.failure(O||P?D(R,P,O):R,S);delete M[R.id];break;}},xdrReady:function(M){C.fire(K,M);},transport:function(M){var N=M.yid?M.yid:C.id;M.id=M.id||"flash";if(M.id==="native"||M.id==="flash"){H(M.src,N);this._transport.flash=J.getElementById("yuiIoSwf");}else{this._transport[M.id]=M.src;}}});},"3.2.0",{requires:["io-base","datatype-xml"]});YUI.add("io-upload-iframe",function(C){var L=C.config.win,G=C.config.doc,I=(G.documentMode&&G.documentMode===8);function E(S,R){var T=[],O=R.split("="),Q,P;for(Q=0,P=O.length-1;Q<P;Q++){T[Q]=G.createElement("input");T[Q].type="hidden";T[Q].name=O[Q].substring(O[Q].lastIndexOf("&")+1);T[Q].value=(Q+1===P)?O[Q+1]:O[Q+1].substring(0,(O[Q+1].lastIndexOf("&")));S.appendChild(T[Q]);}return T;}function H(Q,R){var P,O;for(P=0,O=R.length;P<O;P++){Q.removeChild(R[P]);}}function F(P,Q,O){P.setAttribute("action",O);P.setAttribute("method","POST");P.setAttribute("target","ioupload"+Q);P.setAttribute(C.UA.ie&&!I?"encoding":"enctype","multipart/form-data");}function N(P,O){var Q;for(Q in O){if(O.hasOwnProperty(O,Q)){if(O[Q]){P.setAttribute(Q,P[Q]);}else{P.removeAttribute(Q);}}}}function D(O,P){C.io._timeout[O.id]=L.setTimeout(function(){var Q={id:O.id,status:"timeout"};C.io.complete(Q,P);C.io.end(Q,P);},P.timeout);}function K(O){L.clearTimeout(C.io._timeout[O]);delete C.io._timeout[O];}function J(O){C.Event.purgeElement("#ioupload"+O,false);C.one("body").removeChild(C.one("#ioupload"+O));}function A(R,S){var Q=C.one("#ioupload"+R.id).get("contentWindow.document"),O=Q.one("body"),P;if(S.timeout){K(R.id);}if(O){P=O.query("pre:first-child");R.c.responseText=P?P.get("text"):O.get("text");}else{R.c.responseXML=Q._node;}C.io.complete(R,S);C.io.end(R,S);L.setTimeout(function(){J(R.id);},0);}function M(P,Q){var O=C.Node.create('<iframe id="ioupload'+P.id+'" name="ioupload'+P.id+'" />');O._node.style.position="absolute";O._node.style.top="-1000px";O._node.style.left="-1000px";C.one("body").appendChild(O);C.on("load",function(){A(P,Q);},"#ioupload"+P.id);}function B(S,Q,T){var R=(typeof T.form.id==="string")?G.getElementById(T.form.id):T.form.id,P,O={action:R.getAttribute("action"),target:R.getAttribute("target")};F(R,S.id,Q);if(T.data){P=E(R,T.data);}if(T.timeout){D(S,T);}R.submit();C.io.start(S.id,T);if(T.data){H(R,P);}N(R,O);return{id:S.id,abort:function(){var U={id:S.id,status:"abort"};if(C.one("#ioupload"+S.id)){J(S.id);C.io.complete(U,T);C.io.end(U,T);}else{return false;}},isInProgress:function(){return C.one("#ioupload"+S.id)?true:false;}};}C.mix(C.io,{upload:function(P,O,Q){M(P,Q);return B(P,O,Q);}});},"3.2.0",{requires:["io-base","node-base"]});YUI.add("io-queue",function(B){var A=new B.Queue(),G,L=1;function F(){var M=A.next();G=M.id;L=0;B.io(M.uri,M.cfg,M.id);}function D(M){A.promote(M);}function I(M,O){var N={uri:M,id:B.io._id(),cfg:O};A.add(N);if(L===1){F();}return N;}function C(M){L=1;if(G===M&&A.size()>0){F();}}function K(M){A.remove(M);}function E(){L=1;if(A.size()>0){F();}}function H(){L=0;}function J(){return A.size();}I.size=J;I.start=E;I.stop=H;I.promote=D;I.remove=K;B.on("io:complete",function(M){C(M);},B.io);B.mix(B.io,{queue:I},true);},"3.2.0",{requires:["io-base","queue-promote"]});YUI.add("io",function(A){},"3.2.0",{use:["io-base","io-form","io-xdr","io-upload-iframe","io-queue"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("json-parse",function(B){function K(Q){return(B.config.win||this||{})[Q];}var I=K("JSON"),J=K("eval"),L=(Object.prototype.toString.call(I)==="[object JSON]"&&I),E=!!L,O=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,M=/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,D=/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,F=/(?:^|:|,)(?:\s*\[)+/g,P=/[^\],:{}\s]/,N=function(Q){return"\\u"+("0000"+(+(Q.charCodeAt(0))).toString(16)).slice(-4);},C=function(S,Q){var R=function(X,V){var U,T,W=X[V];if(W&&typeof W==="object"){for(U in W){if(W.hasOwnProperty(U)){T=R(W,U);if(T===undefined){delete W[U];}else{W[U]=T;}}}}return Q.call(X,V,W);};return typeof Q==="function"?R({"":S},""):S;},G=function(R,Q){R=R.replace(O,N);if(!P.test(R.replace(M,"@").replace(D,"]").replace(F,""))){return C(J("("+R+")"),Q);}throw new SyntaxError("JSON.parse");};B.namespace("JSON").parse=function(R,Q){if(typeof R!=="string"){R+="";}return L&&B.JSON.useNativeParse?L.parse(R,Q):G(R,Q);};function A(R,Q){return R==="ok"?true:Q;}if(L){try{E=(L.parse('{"ok":false}',A)).ok;}catch(H){E=false;}}B.JSON.useNativeParse=E;},"3.2.0");




YUI.add('moodle-calendar-eventmanager', function(Y) {

    var ENAME = 'Calendar event',
        EVENTID = 'eventId',
        EVENTNODE = 'node',
        EVENTTITLE = 'title',
        EVENTCONTENT = 'content',
        EVENTDELAY = 'delay',
        SHOWTIMEOUT = 'showTimeout',
        HIDETIMEOUT = 'hideTimeout';

    var EVENT = function(config) {
        EVENT.superclass.constructor.apply(this, arguments);
    }
    Y.extend(EVENT, Y.Base, {
        initpanelcalled : false,
        initializer : function(config){
            var id = this.get(EVENTID), node = this.get(EVENTNODE);
            if (!node) {
                return false;
            }
            var td = node.ancestor('td');
            this.publish('showevent');
            this.publish('hideevent');
            td.on('mouseenter', this.startShow, this);
            td.on('mouseleave', this.startHide, this);
            return true;
        },
        initPanel : function() {
            if (!this.initpanelcalled) {
                this.initpanelcalled = true;
                var node = this.get(EVENTNODE),
                    td = node.ancestor('td'),
                    constraint = td.ancestor('div'),
                    panel;
                panel = new Y.Overlay({
                    constrain : constraint,
                    align : {
                        node : td,
                        points:[Y.WidgetPositionAlign.TL, Y.WidgetPositionAlign.BC]
                    },
                    headerContent : Y.Node.create('<h2 class="eventtitle">'+this.get(EVENTTITLE)+'</h2>'),
                    bodyContent : Y.Node.create('<div class="eventcontent">'+this.get(EVENTCONTENT)+'</div>'),
                    visible : false,
                    id : this.get(EVENTID)+'_panel',
                    width : Math.floor(constraint.get('offsetWidth')*0.9)+"px"
                });
                panel.render(td);
                panel.get('boundingBox').addClass('calendar-event-panel');
                this.on('showevent', panel.show, panel);
                this.on('hideevent', panel.hide, panel);
            }
        },
        startShow : function() {
            if (this.get(SHOWTIMEOUT) !== null) {
                this.cancelShow();
            }
            var self = this;
            this.set(SHOWTIMEOUT, setTimeout(function(){self.show();}, this.get(EVENTDELAY)));
        },
        cancelShow : function() {
            clearTimeout(this.get(SHOWTIMEOUT));
        },
        show : function() {
            this.initPanel();
            this.fire('showevent');
        },
        startHide : function() {
            if (this.get(HIDETIMEOUT) !== null) {
                this.cancelHide();
            }
            var self = this;
            this.set(HIDETIMEOUT, setTimeout(function(){self.hide();}, this.get(EVENTDELAY)));
        },
        hide : function() {
            this.fire('hideevent');
        },
        cancelHide : function() {
            clearTimeout(this.get(HIDETIMEOUT));
        }
    }, {
        NAME : ENAME,
        ATTRS : {
            eventId : {
                setter : function(nodeid) {
                    this.set(EVENTNODE, Y.one('#'+nodeid));
                    return nodeid;
                },
                validator : Y.Lang.isString
            },
            node : {
                setter : function(node) {
                    var n = Y.one(node);
                    if (!n) {
                        Y.fail(ENAME+': invalid event node set');
                    }
                    return n;
                }
            },
            title : {
                validator : Y.Lang.isString
            },
            content : {
                validator : Y.Lang.isString
            },
            delay : {
                value : 300,
                validator : Y.Lang.isNumber
            },
            showTimeout : {
                value : null
            },
            hideTimeout : {
                value : null
            }
        }
    });
    Y.augment(EVENT, Y.EventTarget);

    var EVENTMANAGER = {
        add_event : function(config) {
            new EVENT(config);
        },
        init_basic_export : function(allowthisweek, allownextweek, allownextmonth, username, authtoken) {
            var params = {
                preset_what : (Y.one('#pw_course').get('checked'))?'courses':'all',
                preset_time : 'recentupcoming',
                username : username,
                authtoken : authtoken

            }
            if (allowthisweek && Y.one('#pt_wknow').get('checked')) {
                params.presettime = 'weeknow';
            } else if (allownextweek && Y.one('#pt_wknext').get('checked')) {
                params.presettime = 'weeknext';
            } else if (allownextmonth && Y.one('#pt_monnext').get('checked')) {
                params.presettime = 'monthnext';
            } else if (Y.one('#pt_monnow').get('checked')) {
                params.presettime = 'monthnow';
            }
            Y.one('#url').setContent(M.cfg.wwwroot+'/calendar/export_execute.php?'+build_querystring(params));
            Y.one('#urlbox').setStyle('display', 'block');
        }
    }

    M.core_calendar = M.core_calendar || {}
    Y.mix(M.core_calendar, EVENTMANAGER);

}, '@VERSION@', {requires:['base', 'node', 'event-mouseenter', 'overlay', 'moodle-calendar-eventmanager-skin', 'test']});





/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("attribute-base",function(C){C.State=function(){this.data={};};C.State.prototype={add:function(O,Y,f){var e=this.data;e[Y]=e[Y]||{};e[Y][O]=f;},addAll:function(O,d){var Y;for(Y in d){if(d.hasOwnProperty(Y)){this.add(O,Y,d[Y]);}}},remove:function(O,Y){var e=this.data;if(e[Y]&&(O in e[Y])){delete e[Y][O];}},removeAll:function(O,e){var Y=this.data;C.each(e||Y,function(f,d){if(C.Lang.isString(d)){this.remove(O,d);}else{this.remove(O,f);}},this);},get:function(O,Y){var e=this.data;return(e[Y]&&O in e[Y])?e[Y][O]:undefined;},getAll:function(O){var e=this.data,Y;C.each(e,function(f,d){if(O in e[d]){Y=Y||{};Y[d]=f[O];}},this);return Y;}};var K=C.Object,F=C.Lang,L=C.EventTarget,X=".",U="Change",N="getter",M="setter",P="readOnly",Z="writeOnce",V="initOnly",c="validator",H="value",Q="valueFn",E="broadcast",S="lazyAdd",J="_bypassProxy",b="added",B="initializing",I="initValue",W="published",T="defaultValue",A="lazy",R="isLazyAdd",G,a={};a[P]=1;a[Z]=1;a[N]=1;a[E]=1;function D(){var d=this,O=this.constructor.ATTRS,Y=C.Base;d._ATTR_E_FACADE={};L.call(d,{emitFacade:true});d._conf=d._state=new C.State();d._stateProxy=d._stateProxy||null;d._requireAddAttr=d._requireAddAttr||false;if(O&&!(Y&&d instanceof Y)){d.addAttrs(this._protectAttrs(O));}}D.INVALID_VALUE={};G=D.INVALID_VALUE;D._ATTR_CFG=[M,N,c,H,Q,Z,P,S,E,J];D.prototype={addAttr:function(Y,O,e){var f=this,h=f._state,g,d;e=(S in O)?O[S]:e;if(e&&!f.attrAdded(Y)){h.add(Y,A,O||{});h.add(Y,b,true);}else{if(!f.attrAdded(Y)||h.get(Y,R)){O=O||{};d=(H in O);if(d){g=O.value;delete O.value;}O.added=true;O.initializing=true;h.addAll(Y,O);if(d){f.set(Y,g);}h.remove(Y,B);}}return f;},attrAdded:function(O){return !!this._state.get(O,b);},modifyAttr:function(Y,O){var d=this,f,e;if(d.attrAdded(Y)){if(d._isLazyAttr(Y)){d._addLazyAttr(Y);}e=d._state;for(f in O){if(a[f]&&O.hasOwnProperty(f)){e.add(Y,f,O[f]);if(f===E){e.remove(Y,W);}}}}},removeAttr:function(O){this._state.removeAll(O);},get:function(O){return this._getAttr(O);},_isLazyAttr:function(O){return this._state.get(O,A);},_addLazyAttr:function(Y){var d=this._state,O=d.get(Y,A);d.add(Y,R,true);d.remove(Y,A);this.addAttr(Y,O);},set:function(O,d,Y){return this._setAttr(O,d,Y);},reset:function(O){var d=this,Y;if(O){if(d._isLazyAttr(O)){d._addLazyAttr(O);}d.set(O,d._state.get(O,I));}else{Y=d._state.data.added;C.each(Y,function(e,f){d.reset(f);},d);}return d;},_set:function(O,d,Y){return this._setAttr(O,d,Y,true);},_getAttr:function(d){var e=this,i=d,f=e._state,g,O,h,Y;if(d.indexOf(X)!==-1){g=d.split(X);d=g.shift();}if(e._tCfgs&&e._tCfgs[d]){Y={};Y[d]=e._tCfgs[d];delete e._tCfgs[d];e._addAttrs(Y,e._tVals);}if(e._isLazyAttr(d)){e._addLazyAttr(d);}h=e._getStateVal(d);O=f.get(d,N);if(O&&!O.call){O=this[O];}h=(O)?O.call(e,h,i):h;h=(g)?K.getValue(h,g):h;return h;},_setAttr:function(d,g,O,e){var k=true,Y=this._state,h=this._stateProxy,m=Y.data,j,n,o,f,i,l;if(d.indexOf(X)!==-1){n=d;o=d.split(X);d=o.shift();}if(this._isLazyAttr(d)){this._addLazyAttr(d);}j=(!m.value||!(d in m.value));if(h&&d in h&&!this._state.get(d,J)){j=false;}if(this._requireAddAttr&&!this.attrAdded(d)){}else{i=Y.get(d,Z);l=Y.get(d,B);if(!j&&!e){if(i){k=false;}if(Y.get(d,P)){k=false;}}if(!l&&!e&&i===V){k=false;}if(k){if(!j){f=this.get(d);}if(o){g=K.setValue(C.clone(f),o,g);if(g===undefined){k=false;}}if(k){if(l){this._setAttrVal(d,n,f,g);}else{this._fireAttrChange(d,n,f,g,O);}}}}return this;},_fireAttrChange:function(h,g,e,d,O){var j=this,f=h+U,Y=j._state,i;if(!Y.get(h,W)){j.publish(f,{queuable:false,defaultTargetOnly:true,defaultFn:j._defAttrChangeFn,silent:true,broadcast:Y.get(h,E)});Y.add(h,W,true);}i=(O)?C.merge(O):j._ATTR_E_FACADE;i.type=f;i.attrName=h;i.subAttrName=g;i.prevVal=e;i.newVal=d;j.fire(i);},_defAttrChangeFn:function(O){if(!this._setAttrVal(O.attrName,O.subAttrName,O.prevVal,O.newVal)){O.stopImmediatePropagation();}else{O.newVal=this.get(O.attrName);}},_getStateVal:function(O){var Y=this._stateProxy;return Y&&(O in Y)&&!this._state.get(O,J)?Y[O]:this._state.get(O,H);},_setStateVal:function(O,d){var Y=this._stateProxy;if(Y&&(O in Y)&&!this._state.get(O,J)){Y[O]=d;}else{this._state.add(O,H,d);}},_setAttrVal:function(m,l,i,g){var o=this,j=true,d=o._state,e=d.get(m,c),h=d.get(m,M),k=d.get(m,B),n=this._getStateVal(m),Y=l||m,f,O;if(e){if(!e.call){e=this[e];}if(e){O=e.call(o,g,Y);if(!O&&k){g=d.get(m,T);O=true;}}}if(!e||O){if(h){if(!h.call){h=this[h];}if(h){f=h.call(o,g,Y);if(f===G){j=false;}else{if(f!==undefined){g=f;}}}}if(j){if(!l&&(g===n)&&!F.isObject(g)){j=false;}else{if(d.get(m,I)===undefined){d.add(m,I,g);}o._setStateVal(m,g);}}}else{j=false;}return j;},setAttrs:function(O,Y){return this._setAttrs(O,Y);},_setAttrs:function(Y,d){for(var O in Y){if(Y.hasOwnProperty(O)){this.set(O,Y[O]);}}return this;},getAttrs:function(O){return this._getAttrs(O);},_getAttrs:function(e){var g=this,j={},f,Y,O,h,d=(e===true);e=(e&&!d)?e:K.keys(g._state.data.added);for(f=0,Y=e.length;f<Y;f++){O=e[f];h=g.get(O);if(!d||g._getStateVal(O)!=g._state.get(O,I)){j[O]=g.get(O);}}return j;},addAttrs:function(O,Y,d){var e=this;if(O){e._tCfgs=O;e._tVals=e._normAttrVals(Y);e._addAttrs(O,e._tVals,d);e._tCfgs=e._tVals=null;}return e;},_addAttrs:function(Y,d,e){var g=this,O,f,h;for(O in Y){if(Y.hasOwnProperty(O)){f=Y[O];f.defaultValue=f.value;h=g._getAttrInitVal(O,f,g._tVals);if(h!==undefined){f.value=h;}if(g._tCfgs[O]){delete g._tCfgs[O];}g.addAttr(O,f,e);}}},_protectAttrs:function(Y){if(Y){Y=C.merge(Y);for(var O in Y){if(Y.hasOwnProperty(O)){Y[O]=C.merge(Y[O]);}}}return Y;},_normAttrVals:function(O){return(O)?C.merge(O):null;},_getAttrInitVal:function(O,Y,e){var f,d;if(!Y[P]&&e&&e.hasOwnProperty(O)){f=e[O];}else{f=Y[H];d=Y[Q];if(d){if(!d.call){d=this[d];}if(d){f=d.call(this);}}}return f;},_getAttrCfg:function(O){var d,Y=this._state.data;if(Y){d={};C.each(Y,function(e,f){if(O){if(O in e){d[f]=e[O];}}else{C.each(e,function(h,g){d[g]=d[g]||{};d[g][f]=h;});}});}return d;}};C.mix(D,L,false,null,1);C.Attribute=D;},"3.2.0",{requires:["event-custom"]});YUI.add("attribute-complex",function(B){var A=B.Object,C=".";
B.Attribute.Complex=function(){};B.Attribute.Complex.prototype={_normAttrVals:function(G){var I={},H={},J,D,F,E;if(G){for(E in G){if(G.hasOwnProperty(E)){if(E.indexOf(C)!==-1){J=E.split(C);D=J.shift();F=H[D]=H[D]||[];F[F.length]={path:J,value:G[E]};}else{I[E]=G[E];}}}return{simple:I,complex:H};}else{return null;}},_getAttrInitVal:function(K,I,N){var E=I.value,M=I.valueFn,D,F,H,G,O,L,J;if(M){if(!M.call){M=this[M];}if(M){E=M.call(this);}}if(!I.readOnly&&N){D=N.simple;if(D&&D.hasOwnProperty(K)){E=D[K];}F=N.complex;if(F&&F.hasOwnProperty(K)){J=F[K];for(H=0,G=J.length;H<G;++H){O=J[H].path;L=J[H].value;A.setValue(E,O,L);}}}return E;}};B.mix(B.Attribute,B.Attribute.Complex,true,null,1);},"3.2.0",{requires:["attribute-base"]});YUI.add("attribute",function(A){},"3.2.0",{use:["attribute-base","attribute-complex"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("event-focus",function(e){var d=e.Event,c=e.Lang,a=c.isString,b=c.isFunction(e.DOM.create('<p onbeforeactivate=";">').onbeforeactivate);function f(h,g,j){var i="_"+h+"Notifiers";e.Event.define(h,{_attach:function(l,m,k){if(e.DOM.isWindow(l)){return d._attach([h,function(n){m.fire(n);},l]);}else{return d._attach([g,this._proxy,l,this,m,k],{capture:true});}},_proxy:function(o,s,p){var m=o.target,q=m.getData(i),t=e.stamp(o.currentTarget._node),k=(b||o.target!==o.currentTarget),l=s.handle.sub,r=[m,o].concat(l.args||[]),n;s.currentTarget=(p)?m:o.currentTarget;s.container=(p)?o.currentTarget:null;if(!l.filter||l.filter.apply(m,r)){if(!q){q={};m.setData(i,q);if(k){n=d._attach([j,this._notify,m._node]).sub;n.once=true;}}if(!q[t]){q[t]=[];}q[t].push(s);if(!k){this._notify(o);}}},_notify:function(p,l){var m=p.currentTarget,r=m.getData(i),s=m.get("ownerDocument")||m,q=m,k=[],t,n,o;if(r){while(q&&q!==s){k.push.apply(k,r[e.stamp(q)]||[]);q=q.get("parentNode");}k.push.apply(k,r[e.stamp(s)]||[]);for(n=0,o=k.length;n<o;++n){t=k[n];p.currentTarget=k[n].currentTarget;if(t.container){p.container=t.container;}else{delete p.container;}t.fire(p);}m.clearData(i);}},on:function(m,k,l){k.onHandle=this._attach(m._node,l);},detach:function(l,k){k.onHandle.detach();},delegate:function(n,l,m,k){if(a(k)){l.filter=e.delegate.compileFilter(k);}l.delegateHandle=this._attach(n._node,m,true);},detachDelegate:function(l,k){k.delegateHandle.detach();}},true);}if(b){f("focus","beforeactivate","focusin");f("blur","beforedeactivate","focusout");}else{f("focus","focus","focus");f("blur","blur","blur");}},"3.2.0",{requires:["event-synthetic"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("classnamemanager",function(C){var B="classNamePrefix",D="classNameDelimiter",A=C.config;A[B]=A[B]||"yui3";A[D]=A[D]||"-";C.ClassNameManager=function(){var E=A[B],F=A[D];return{getClassName:C.cached(function(){var G=C.Array(arguments);if(G[G.length-1]!==true){G.unshift(E);}else{G.pop();}return G.join(F);})};}();},"3.2.0");/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("widget-base",function(B){var G=B.Lang,U=B.Node,E=B.ClassNameManager,b=E.getClassName,s,V=B.cached(function(L){return L.substring(0,1).toUpperCase()+L.substring(1);}),l="content",w="visible",r="hidden",d="disabled",h="focused",D="width",f="height",t="boundingBox",a="contentBox",K="parentNode",O="ownerDocument",m="offsetHeight",c="auto",J="srcNode",p="body",o="tabIndex",T="id",I="render",q="rendered",P="destroyed",A="strings",Q="<div></div>",e="Change",R="loading",k="_uiSet",j="",n=function(){},M=/(\w+):(\w+)/,Z="$2",X=true,u=false,W,N={},F=[w,d,f,D,h],i=B.UA.webkit,S=B.UA.ie,v="contentUpdate",g={},H={};function C(Y){this._strs={};this._cssPrefix=this.constructor.CSS_PREFIX||b(this.constructor.NAME.toLowerCase());C.superclass.constructor.apply(this,arguments);var x=this.get(I),L;if(x){if(x!==X){L=x;}this.render(L);}}C.NAME="widget";W=C.UI_SRC="ui";C.ATTRS=N;N[T]={valueFn:"_guid",writeOnce:X};N[q]={value:u,readOnly:X};N[t]={value:null,setter:"_setBB",writeOnce:X};N[a]={valueFn:"_defaultCB",setter:"_setCB",writeOnce:X};N[o]={value:null,validator:"_validTabIndex"};N[h]={value:u,readOnly:X};N[d]={value:u};N[w]={value:X};N[f]={value:j};N[D]={value:j};N[A]={value:{},setter:"_strSetter",getter:"_strGetter"};N[I]={value:u,writeOnce:X};C.CSS_PREFIX=b(C.NAME.toLowerCase());C.getClassName=function(){return b.apply(E,[C.CSS_PREFIX].concat(B.Array(arguments),true));};s=C.getClassName;C.getByNode=function(L){var x,Y=s();L=U.one(L);if(L){L=L.ancestor("."+Y,true);if(L){x=H[B.stamp(L,X)];}}return x||null;};B.extend(C,B.Base,{getClassName:function(){return b.apply(E,[this._cssPrefix].concat(B.Array(arguments),true));},getSkinName:function(){var L=this.get(a)||this.get(t),x=new RegExp("\\b"+b("skin")+"-(\\S+)"),Y;if(L){L.ancestor(function(y){Y=y.get("className").match(x);return Y;});}return(Y)?Y[1]:null;},initializer:function(L){H[B.stamp(this.get(t))]=this;this.publish(v,{preventable:u});if(this._applyParser){this._applyParser(L);}},destructor:function(){var L=this.get(t),x=B.stamp(L,X),Y=B.stamp(this,X);if(x in H){delete H[x];}B.each(g,function(z,y){if(z.instances[Y]){delete z.instances[Y];if(B.Object.isEmpty(z.instances)){z.handle.detach();if(g[y]){delete g[y];}}}});this._unbindUI(L);L.remove(X);},render:function(L){if(!this.get(P)&&!this.get(q)){this.publish(I,{queuable:u,fireOnce:X,defaultTargetOnly:X,defaultFn:this._defRenderFn});this.fire(I,{parentNode:(L)?U.one(L):null});}return this;},_defRenderFn:function(L){this._parentNode=L.parentNode;this.renderer();this._set(q,X);this._removeLoadingClassNames();},renderer:function(){this._renderUI();this.renderUI();this._bindUI();this.bindUI();this._syncUI();this.syncUI();},bindUI:n,renderUI:n,syncUI:n,hide:function(){return this.set(w,u);},show:function(){return this.set(w,X);},focus:function(){return this._set(h,X);},blur:function(){return this._set(h,u);},enable:function(){return this.set(d,u);},disable:function(){return this.set(d,X);},_uiSizeCB:function(x){var z=this.get(t),Y=this.get(a),L=s("tmp","forcesize"),y=this._bbs,AA=S&&S<7;if(y){Y.toggleClass(s(l,"expanded"),x);}else{if(x){if(AA){z.addClass(L);}Y.set(m,z.get(m));if(AA){z.removeClass(L);}}else{Y.setStyle(f,j);}}},_renderBox:function(L){var Y=this.get(a),x=this.get(t),AA=this.get(J),y=this.DEF_PARENT_NODE,z=(AA&&AA.get(O))||x.get(O)||Y.get(O);if(AA&&!AA.compareTo(Y)&&!Y.inDoc(z)){AA.replace(Y);}if(!x.compareTo(Y.get(K))&&!x.compareTo(Y)){if(Y.inDoc(z)){Y.replace(x);}x.appendChild(Y);}L=L||(y&&U.one(y));if(L){L.appendChild(x);}else{if(!x.inDoc(z)){U.one(p).insert(x,0);}}this._bbs=!(S&&S<8&&z.compatMode!="BackCompat");},_setBB:function(L){return this._setBox(this.get(T),L,this.BOUNDING_TEMPLATE);},_setCB:function(L){return(this.CONTENT_TEMPLATE===null)?this.get(t):this._setBox(null,L,this.CONTENT_TEMPLATE);},_defaultCB:function(L){return this.get(J)||null;},_setBox:function(x,Y,L){Y=U.one(Y)||U.create(L);if(!Y.get(T)){Y.set(T,x||B.guid());}return Y;},_renderUI:function(){this._renderBoxClassNames();this._renderBox(this._parentNode);},_renderBoxClassNames:function(){var y=this._getClasses(),L,Y=this.get(t),x;Y.addClass(s());for(x=y.length-3;x>=0;x--){L=y[x];Y.addClass(L.CSS_PREFIX||b(L.NAME.toLowerCase()));}this.get(a).addClass(this.getClassName(l));},_removeLoadingClassNames:function(){var Y=this.get(t),L=this.get(a);Y.removeClass(s(R));Y.removeClass(this.getClassName(R));L.removeClass(s(R));L.removeClass(this.getClassName(R));},_bindUI:function(){this._bindAttrUI(this._BIND_UI_ATTRS);this._bindDOM();},_unbindUI:function(L){this._unbindDOM(L);},_bindDOM:function(){var L=this.get(t).get(O);this._hDocFocus=L.on("focus",this._onDocFocus,this);if(i){this._hDocMouseDown=L.on("mousedown",this._onDocMouseDown,this);}},_unbindDOM:function(L){if(this._hDocFocus){this._hDocFocus.detach();}if(i&&this._hDocMouseDown){this._hDocMouseDown.detach();}},_syncUI:function(){this._syncAttrUI(this._SYNC_UI_ATTRS);},_uiSetHeight:function(L){this._uiSetDim(f,L);this._uiSizeCB((L!==j&&L!==c));},_uiSetWidth:function(L){this._uiSetDim(D,L);},_uiSetDim:function(L,Y){this.get(t).setStyle(L,G.isNumber(Y)?Y+this.DEF_UNIT:Y);},_uiSetVisible:function(L){this.get(t).toggleClass(this.getClassName(r),!L);},_uiSetDisabled:function(L){this.get(t).toggleClass(this.getClassName(d),L);},_uiSetFocused:function(x,Y){var L=this.get(t);L.toggleClass(this.getClassName(h),x);if(Y!==W){if(x){L.focus();}else{L.blur();}}},_uiSetTabIndex:function(Y){var L=this.get(t);if(G.isNumber(Y)){L.set(o,Y);}else{L.removeAttribute(o);}},_onDocMouseDown:function(L){if(this._hasDOMFocus){this._onDocFocus(L);}},_onDocFocus:function(Y){var L=this.get(t).contains(Y.target);this._hasDOMFocus=L;this._set(h,L,{src:W});},toString:function(){return this.constructor.NAME+"["+this.get(T)+"]";},DEF_UNIT:"px",DEF_PARENT_NODE:null,CONTENT_TEMPLATE:Q,BOUNDING_TEMPLATE:Q,_guid:function(){return B.guid();},_validTabIndex:function(L){return(G.isNumber(L)||G.isNull(L));},_bindAttrUI:function(Y){var x,L=Y.length;for(x=0;x<L;x++){this.after(Y[x]+e,this._setAttrUI);}},_syncAttrUI:function(x){var y,Y=x.length,L;
for(y=0;y<Y;y++){L=x[y];this[k+V(L)](this.get(L));}},_setAttrUI:function(L){this[k+V(L.attrName)](L.newVal,L.src);},_strSetter:function(L){return B.merge(this.get(A),L);},getString:function(L){return this.get(A)[L];},getStrings:function(){return this.get(A);},_BIND_UI_ATTRS:F,_SYNC_UI_ATTRS:F.concat(o),UI_EVENTS:B.Node.DOM_EVENTS,_getUIEventNode:function(){return this.get(t);},_createUIEvent:function(x){var AA=this._getUIEventNode(),L=AA.get(K),Y=(B.stamp(L)+x),z=g[Y],y;if(!z){y=L.delegate(x,function(AB){var AC=C.getByNode(this);AC.fire(AB.type,{domEvent:AB});},"."+s());g[Y]=z={instances:{},handle:y};}z.instances[B.stamp(this)]=1;},_getUIEvent:function(Y){if(G.isString(Y)){var x=Y.replace(M,Z),L;if(this.UI_EVENTS[x]){L=x;}return L;}},_initUIEvent:function(Y){var x=this._getUIEvent(Y),L=this._uiEvtsInitQueue||{};if(x&&!L[x]){this._uiEvtsInitQueue=L[x]=1;this.after(I,function(){this._createUIEvent(x);delete this._uiEvtsInitQueue[x];});}},on:function(L){this._initUIEvent(L);return C.superclass.on.apply(this,arguments);},after:function(L){this._initUIEvent(L);return C.superclass.after.apply(this,arguments);},publish:function(Y,L){var x=this._getUIEvent(Y);if(x&&L&&L.defaultFn){this._initUIEvent(x);}return C.superclass.publish.apply(this,arguments);}});B.Widget=C;},"3.2.0",{requires:["attribute","event-focus","base-base","base-pluginhost","node-base","node-style","node-event-delegate","classnamemanager"]});YUI.add("widget-htmlparser",function(F){var E=F.Widget,C=F.Node,D=F.Lang,A="srcNode",B="contentBox";E.HTML_PARSER={};E._buildCfg={aggregates:["HTML_PARSER"]};E.ATTRS[A]={value:null,setter:C.one,getter:"_getSrcNode",writeOnce:true};F.mix(E.prototype,{_getSrcNode:function(G){return G||this.get(B);},_applyParsedConfig:function(I,G,H){return(H)?F.mix(G,H,false):G;},_applyParser:function(G){var I=this,J=I.get(A),H=I._getHtmlParser(),L,K;if(H&&J){F.Object.each(H,function(N,M,O){K=null;if(D.isFunction(N)){K=N.call(I,J);}else{if(D.isArray(N)){K=J.all(N[0]);}else{K=J.one(N);}}if(K!==null&&K!==undefined){L=L||{};L[M]=K;}});}G=I._applyParsedConfig(J,G,L);},_getHtmlParser:function(){var H=this._getClasses(),J={},G,I;for(G=H.length-1;G>=0;G--){I=H[G].HTML_PARSER;if(I){F.mix(J,I,true);}}return J;}});},"3.2.0",{requires:["widget-base"]});YUI.add("widget",function(A){},"3.2.0",{use:["widget-base","widget-htmlparser"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("widget-stdmod",function(B){var F=B.Lang,S=B.Node,f=B.NodeList,a=B.UA,E=B.Widget,D="",n="hd",k="bd",K="ft",h="header",p="body",o="footer",s="fillHeight",O="stdmod",W="Node",m="Content",r="innerHTML",g="firstChild",I="childNodes",P="ownerDocument",X="contentBox",c="height",j="offsetHeight",b="auto",N="headerContentChange",e="bodyContentChange",Q="footerContentChange",U="fillHeightChange",V="heightChange",t="contentUpdate",Z="renderUI",i="bindUI",G="syncUI",l="_applyParsedConfig",T=B.Widget.UI_SRC;function u(L){this._stdModNode=this.get(X);B.before(this._renderUIStdMod,this,Z);B.before(this._bindUIStdMod,this,i);B.before(this._syncUIStdMod,this,G);}u.HEADER=h;u.BODY=p;u.FOOTER=o;u.AFTER="after";u.BEFORE="before";u.REPLACE="replace";var M=u.HEADER,d=u.BODY,R=u.FOOTER,A=M+m,C=R+m,J=d+m,q=u.AFTER,H=u.BEFORE;u.ATTRS={headerContent:{value:null},footerContent:{value:null},bodyContent:{value:null},fillHeight:{value:u.BODY,validator:function(L){return this._validateFillHeight(L);}}};u.HTML_PARSER={headerContent:function(L){return this._parseStdModHTML(M);},bodyContent:function(L){return this._parseStdModHTML(d);},footerContent:function(L){return this._parseStdModHTML(R);}};u.SECTION_CLASS_NAMES={header:E.getClassName(n),body:E.getClassName(k),footer:E.getClassName(K)};u.TEMPLATES={header:'<div class="'+u.SECTION_CLASS_NAMES[M]+'"></div>',body:'<div class="'+u.SECTION_CLASS_NAMES[d]+'"></div>',footer:'<div class="'+u.SECTION_CLASS_NAMES[R]+'"></div>'};u.prototype={_syncUIStdMod:function(){var L=this._stdModParsed;if(!L||!L[A]){this._uiSetStdMod(M,this.get(A));}if(!L||!L[J]){this._uiSetStdMod(d,this.get(J));}if(!L||!L[C]){this._uiSetStdMod(R,this.get(C));}this._uiSetFillHeight(this.get(s));},_renderUIStdMod:function(){this._stdModNode.addClass(E.getClassName(O));this._renderStdModSections();},_renderStdModSections:function(){if(F.isValue(this.get(A))){this._renderStdMod(M);}if(F.isValue(this.get(J))){this._renderStdMod(d);}if(F.isValue(this.get(C))){this._renderStdMod(R);}},_bindUIStdMod:function(){this.after(N,this._afterHeaderChange);this.after(e,this._afterBodyChange);this.after(Q,this._afterFooterChange);this.after(U,this._afterFillHeightChange);this.after(V,this._fillHeight);this.after(t,this._fillHeight);},_afterHeaderChange:function(L){if(L.src!==T){this._uiSetStdMod(M,L.newVal,L.stdModPosition);}},_afterBodyChange:function(L){if(L.src!==T){this._uiSetStdMod(d,L.newVal,L.stdModPosition);}},_afterFooterChange:function(L){if(L.src!==T){this._uiSetStdMod(R,L.newVal,L.stdModPosition);}},_afterFillHeightChange:function(L){this._uiSetFillHeight(L.newVal);},_validateFillHeight:function(L){return !L||L==u.BODY||L==u.HEADER||L==u.FOOTER;},_uiSetFillHeight:function(v){var Y=this.getStdModNode(v);var L=this._currFillNode;if(L&&Y!==L){L.setStyle(c,D);}if(Y){this._currFillNode=Y;}this._fillHeight();},_fillHeight:function(){if(this.get(s)){var L=this.get(c);if(L!=D&&L!=b){this.fillHeight(this._currFillNode);}}},_uiSetStdMod:function(w,v,L){if(F.isValue(v)){var Y=this.getStdModNode(w)||this._renderStdMod(w);if(v instanceof S||v instanceof f){this._addNodeRef(Y,v,L);}else{this._addNodeHTML(Y,v,L);}this.set(w+m,this._getStdModContent(w),{src:T});}else{this._eraseStdMod(w);}this.fire(t);},_renderStdMod:function(v){var L=this.get(X),Y=this._findStdModSection(v);if(!Y){Y=this._getStdModTemplate(v);}this._insertStdModSection(L,v,Y);this[v+W]=Y;return this[v+W];},_eraseStdMod:function(Y){var L=this.getStdModNode(Y);if(L){L.remove(true);delete this[Y+W];}},_insertStdModSection:function(L,w,v){var Y=L.get(g);if(w===R||!Y){L.appendChild(v);}else{if(w===M){L.insertBefore(v,Y);}else{var x=this[R+W];if(x){L.insertBefore(v,x);}else{L.appendChild(v);}}}},_getStdModTemplate:function(L){return S.create(u.TEMPLATES[L],this._stdModNode.get(P));},_addNodeHTML:function(v,Y,L){if(L==q){v.append(Y);}else{if(L==H){v.prepend(Y);}else{v.setContent(Y);}}},_addNodeRef:function(y,w,Y){var L=true,v,x;if(Y==H){var z=y.get(g);if(z){if(w instanceof f){for(v=w.size()-1;v>=0;--v){y.insertBefore(w.item(v),z);}}else{y.insertBefore(w,z);}L=false;}}else{if(Y!=q){y.set(r,D);}}if(L){if(w instanceof f){for(v=0,x=w.size();v<x;++v){y.appendChild(w.item(v));}}else{y.appendChild(w);}}},_getPreciseHeight:function(v){var L=(v)?v.get(j):0,w="getBoundingClientRect";if(v&&v.hasMethod(w)){var Y=v.invoke(w);if(Y){L=Y.bottom-Y.top;}}return L;},_findStdModSection:function(L){return this.get(X).one("> ."+u.SECTION_CLASS_NAMES[L]);},_parseStdModHTML:function(Y){var L=this._findStdModSection(Y);if(L){if(!this._stdModParsed){this._stdModParsed={};B.before(this._applyStdModParsedConfig,this,l);}this._stdModParsed[Y+m]=1;return L.get("innerHTML");}return null;},_applyStdModParsedConfig:function(w,L,v){var Y=this._stdModParsed;if(Y){Y[A]=!(A in L)&&(A in Y);Y[J]=!(J in L)&&(J in Y);Y[C]=!(C in L)&&(C in Y);}},_getStdModContent:function(L){return(this[L+W])?this[L+W].get(I):null;},setStdModContent:function(v,Y,L){this.set(v+m,Y,{stdModPosition:L});},getStdModNode:function(L){return this[L+W]||null;},fillHeight:function(Y){if(Y){var z=this.get(X),AA=[this.headerNode,this.bodyNode,this.footerNode],L,AB,AC=0,x=0,w=false;for(var y=0,v=AA.length;y<v;y++){L=AA[y];if(L){if(L!==Y){AC+=this._getPreciseHeight(L);}else{w=true;}}}if(w){if(a.ie||a.opera){Y.set(j,0);}AB=z.get(j)-parseInt(z.getComputedStyle("paddingTop"),10)-parseInt(z.getComputedStyle("paddingBottom"),10)-parseInt(z.getComputedStyle("borderBottomWidth"),10)-parseInt(z.getComputedStyle("borderTopWidth"),10);if(F.isNumber(AB)){x=AB-AC;if(x>=0){Y.set(j,x);}}}}}};B.WidgetStdMod=u;},"3.2.0",{requires:["base-build","widget"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("widget-position",function(A){var I=A.Lang,L=A.Widget,N="xy",J="position",G="positioned",K="boundingBox",H="relative",M="renderUI",F="bindUI",D="syncUI",C=L.UI_SRC,E="xyChange";function B(O){this._posNode=this.get(K);A.after(this._renderUIPosition,this,M);A.after(this._syncUIPosition,this,D);A.after(this._bindUIPosition,this,F);}B.ATTRS={x:{setter:function(O){this._setX(O);},getter:function(){return this._getX();},lazyAdd:false},y:{setter:function(O){this._setY(O);},getter:function(){return this._getY();},lazyAdd:false},xy:{value:[0,0],validator:function(O){return this._validateXY(O);}}};B.POSITIONED_CLASS_NAME=L.getClassName(G);B.prototype={_renderUIPosition:function(){this._posNode.addClass(B.POSITIONED_CLASS_NAME);},_syncUIPosition:function(){var O=this._posNode;if(O.getStyle(J)===H){this.syncXY();}this._uiSetXY(this.get(N));},_bindUIPosition:function(){this.after(E,this._afterXYChange);},move:function(){var O=arguments,P=(I.isArray(O[0]))?O[0]:[O[0],O[1]];this.set(N,P);},syncXY:function(){this.set(N,this._posNode.getXY(),{src:C});},_validateXY:function(O){return(I.isArray(O)&&I.isNumber(O[0])&&I.isNumber(O[1]));},_setX:function(O){this.set(N,[O,this.get(N)[1]]);},_setY:function(O){this.set(N,[this.get(N)[0],O]);},_getX:function(){return this.get(N)[0];},_getY:function(){return this.get(N)[1];},_afterXYChange:function(O){if(O.src!=C){this._uiSetXY(O.newVal);}},_uiSetXY:function(O){this._posNode.setXY(O);}};A.WidgetPosition=B;},"3.2.0",{requires:["base-build","node-screen","widget"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("widget-position-align",function(A){var H=A.Lang,D="align",F="bindUI",B="syncUI",E="offsetWidth",I="offsetHeight",K="viewportRegion",G="region",J="alignChange";function C(L){if(!this._posNode){A.error("WidgetPosition needs to be added to the Widget, before WidgetPositionAlign is added");}A.after(this._syncUIPosAlign,this,B);A.after(this._bindUIPosAlign,this,F);}C.ATTRS={align:{value:null},centered:{setter:"_setAlignCenter",lazyAdd:false,value:false}};C.TL="tl";C.TR="tr";C.BL="bl";C.BR="br";C.TC="tc";C.RC="rc";C.BC="bc";C.LC="lc";C.CC="cc";C.prototype={_syncUIPosAlign:function(){var L=this.get(D);if(L){this._uiSetAlign(L.node,L.points);}},_bindUIPosAlign:function(){this.after(J,this._afterAlignChange);},_setAlignCenter:function(L){if(L){this.set(D,{node:L===true?null:L,points:[C.CC,C.CC]});}return L;},_afterAlignChange:function(L){if(L.newVal){this._uiSetAlign(L.newVal.node,L.newVal.points);}},_uiSetAlign:function(O,N){if(!H.isArray(N)||N.length!=2){A.error("align: Invalid Points Arguments");return;}var M=this._getRegion(O),L,P,Q;if(M){L=N[0];P=N[1];switch(P){case C.TL:Q=[M.left,M.top];break;case C.TR:Q=[M.right,M.top];break;case C.BL:Q=[M.left,M.bottom];break;case C.BR:Q=[M.right,M.bottom];break;case C.TC:Q=[M.left+Math.floor(M.width/2),M.top];break;case C.BC:Q=[M.left+Math.floor(M.width/2),M.bottom];break;case C.LC:Q=[M.left,M.top+Math.floor(M.height/2)];break;case C.RC:Q=[M.right,M.top+Math.floor(M.height/2),L];break;case C.CC:Q=[M.left+Math.floor(M.width/2),M.top+Math.floor(M.height/2),L];break;default:break;}if(Q){this._doAlign(L,Q[0],Q[1]);}}},_doAlign:function(M,L,P){var O=this._posNode,N;switch(M){case C.TL:N=[L,P];break;case C.TR:N=[L-O.get(E),P];break;case C.BL:N=[L,P-O.get(I)];break;case C.BR:N=[L-O.get(E),P-O.get(I)];break;case C.TC:N=[L-(O.get(E)/2),P];break;case C.BC:N=[L-(O.get(E)/2),P-O.get(I)];break;case C.LC:N=[L,P-(O.get(I)/2)];break;case C.RC:N=[(L-O.get(E)),P-(O.get(I)/2)];break;case C.CC:N=[L-(O.get(E)/2),P-(O.get(I)/2)];break;default:break;}if(N){this.move(N);}},_getRegion:function(M){var L;if(!M){L=this._posNode.get(K);}else{M=A.Node.one(M);if(M){L=M.get(G);}}return L;},align:function(M,L){this.set(D,{node:M,points:L});},centered:function(L){this.align(L,[C.CC,C.CC]);}};A.WidgetPositionAlign=C;},"3.2.0",{requires:["widget-position"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("widget-stack",function(E){var N=E.Lang,T=E.UA,d=E.Node,F=E.Widget,c="zIndex",P="shim",a="visible",e="boundingBox",W="renderUI",G="bindUI",S="syncUI",Q="offsetWidth",D="offsetHeight",M="parentNode",A="firstChild",X="ownerDocument",H="width",V="height",K="px",O="shimdeferred",f="shimresize",Z="visibleChange",C="widthChange",J="heightChange",b="shimChange",B="zIndexChange",I="contentUpdate",R="stacked";function U(L){this._stackNode=this.get(e);this._stackHandles={};E.after(this._renderUIStack,this,W);E.after(this._syncUIStack,this,S);E.after(this._bindUIStack,this,G);}U.ATTRS={shim:{value:(T.ie==6)},zIndex:{value:0,setter:function(L){return this._setZIndex(L);}}};U.HTML_PARSER={zIndex:function(L){return L.getStyle(c);}};U.SHIM_CLASS_NAME=F.getClassName(P);U.STACKED_CLASS_NAME=F.getClassName(R);U.SHIM_TEMPLATE='<iframe class="'+U.SHIM_CLASS_NAME+'" frameborder="0" title="Widget Stacking Shim" src="javascript:false" tabindex="-1" role="presentation"></iframe>';U.prototype={_syncUIStack:function(){this._uiSetShim(this.get(P));this._uiSetZIndex(this.get(c));},_bindUIStack:function(){this.after(b,this._afterShimChange);this.after(B,this._afterZIndexChange);},_renderUIStack:function(){this._stackNode.addClass(U.STACKED_CLASS_NAME);},_setZIndex:function(L){if(N.isString(L)){L=parseInt(L,10);}if(!N.isNumber(L)){L=0;}return L;},_afterShimChange:function(L){this._uiSetShim(L.newVal);},_afterZIndexChange:function(L){this._uiSetZIndex(L.newVal);},_uiSetZIndex:function(L){this._stackNode.setStyle(c,L);},_uiSetShim:function(L){if(L){if(this.get(a)){this._renderShim();}else{this._renderShimDeferred();}}else{this._destroyShim();}},_renderShimDeferred:function(){this._stackHandles[O]=this._stackHandles[O]||[];var Y=this._stackHandles[O],L=function(g){if(g.newVal){this._renderShim();}};Y.push(this.on(Z,L));},_addShimResizeHandlers:function(){this._stackHandles[f]=this._stackHandles[f]||[];var Y=this.sizeShim,L=this._stackHandles[f];this.sizeShim();L.push(this.after(Z,Y));L.push(this.after(C,Y));L.push(this.after(J,Y));L.push(this.after(I,Y));},_detachStackHandles:function(L){var Y=this._stackHandles[L],g;if(Y&&Y.length>0){while((g=Y.pop())){g.detach();}}},_renderShim:function(){var L=this._shimNode,Y=this._stackNode;if(!L){L=this._shimNode=this._getShimTemplate();Y.insertBefore(L,Y.get(A));if(T.ie==6){this._addShimResizeHandlers();}this._detachStackHandles(O);}},_destroyShim:function(){if(this._shimNode){this._shimNode.get(M).removeChild(this._shimNode);this._shimNode=null;this._detachStackHandles(O);this._detachStackHandles(f);}},sizeShim:function(){var Y=this._shimNode,L=this._stackNode;if(Y&&T.ie===6&&this.get(a)){Y.setStyle(H,L.get(Q)+K);Y.setStyle(V,L.get(D)+K);}},_getShimTemplate:function(){return d.create(U.SHIM_TEMPLATE,this._stackNode.get(X));}};E.WidgetStack=U;},"3.2.0",{requires:["base-build","widget"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("widget-position-constrain",function(C){var F="constrain",D="constrain|xyChange",B="constrainChange",N="preventOverlap",E="align",O="",G="bindUI",I="xy",A="x",M="y",J=C.Node,P="viewportRegion",L="region",K;function H(Q){if(!this._posNode){C.error("WidgetPosition needs to be added to the Widget, before WidgetPositionConstrain is added");}C.after(this._bindUIPosConstrained,this,G);}H.ATTRS={constrain:{value:null,setter:"_setConstrain"},preventOverlap:{value:false}};K=H._PREVENT_OVERLAP={x:{"tltr":1,"blbr":1,"brbl":1,"trtl":1},y:{"trbr":1,"tlbl":1,"bltl":1,"brtr":1}};H.prototype={getConstrainedXY:function(T,S){S=S||this.get(F);var R=this._getRegion((S===true)?null:S),Q=this._posNode.get(L);return[this._constrain(T[0],A,Q,R),this._constrain(T[1],M,Q,R)];},constrain:function(U,R){var T,Q,S=R||this.get(F);if(S){T=U||this.get(I);Q=this.getConstrainedXY(T,S);if(Q[0]!==T[0]||Q[1]!==T[1]){this.set(I,Q,{constrained:true});}}},_setConstrain:function(Q){return(Q===true)?Q:J.one(Q);},_constrain:function(Q,R,Y,S){if(S){if(this.get(N)){Q=this._preventOverlap(Q,R,Y,S);}var V=(R==A),X=(V)?S.width:S.height,U=(V)?Y.width:Y.height,T=(V)?S.left:S.top,W=(V)?S.right-U:S.bottom-U;if(Q<T||Q>W){if(U<X){if(Q<T){Q=T;}else{if(Q>W){Q=W;}}}else{Q=T;}}}return Q;},_preventOverlap:function(R,S,b,T){var W=this.get(E),a=(S===A),Y,V,U,X,Z,Q;if(W&&W.points&&K[S][W.points.join(O)]){V=this._getRegion(W.node);if(V){Y=(a)?b.width:b.height;U=(a)?V.left:V.top;X=(a)?V.right:V.bottom;Z=(a)?V.left-T.left:V.top-T.top;Q=(a)?T.right-V.right:T.bottom-V.bottom;}if(R>U){if(Q<Y&&Z>Y){R=U-Y;}}else{if(Z<Y&&Q>Y){R=X;}}}return R;},_bindUIPosConstrained:function(){this.after(B,this._afterConstrainChange);this._enableConstraints(this.get(F));},_afterConstrainChange:function(Q){this._enableConstraints(Q.newVal);},_enableConstraints:function(Q){if(Q){this.constrain();this._cxyHandle=this._cxyHandle||this.on(D,this._constrainOnXYChange);}else{if(this._cxyHandle){this._cxyHandle.detach();this._cxyHandle=null;}}},_constrainOnXYChange:function(Q){if(!Q.constrained){Q.newVal=this.getConstrainedXY(Q.newVal);}},_getRegion:function(Q){var R;if(!Q){R=this._posNode.get(P);}else{Q=J.one(Q);if(Q){R=Q.get(L);}}return R;}};C.WidgetPositionConstrain=H;},"3.2.0",{requires:["widget-position"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("overlay",function(A){A.Overlay=A.Base.create("overlay",A.Widget,[A.WidgetStdMod,A.WidgetPosition,A.WidgetStack,A.WidgetPositionAlign,A.WidgetPositionConstrain]);},"3.2.0",{requires:["widget","widget-stdmod","widget-position","widget-stack","widget-position-align","widget-position-constrain"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("substitute",function(G){var B=G.Lang,D="dump",F=" ",C="{",E="}",A=function(U,I,O,H){var M,L,K,S,R,T,Q=[],J,N,P=U.length;for(;;){M=U.lastIndexOf(C,P);if(M<0){break;}L=U.indexOf(E,M);if(M+1>=L){break;}J=U.substring(M+1,L);S=J;T=null;K=S.indexOf(F);if(K>-1){T=S.substring(K+1);S=S.substring(0,K);}R=I[S];if(O){R=O(S,R,T);}if(B.isObject(R)){if(!G.dump){R=R.toString();}else{if(B.isArray(R)){R=G.dump(R,parseInt(T,10));}else{T=T||"";N=T.indexOf(D);if(N>-1){T=T.substring(4);}if(R.toString===Object.prototype.toString||N>-1){R=G.dump(R,parseInt(T,10));}else{R=R.toString();}}}}else{if(!B.isString(R)&&!B.isNumber(R)){R="~-"+Q.length+"-~";Q[Q.length]=J;}}U=U.substring(0,M)+R+U.substring(L+1);if(!H){P=M-1;}}for(M=Q.length-1;M>=0;M=M-1){U=U.replace(new RegExp("~-"+M+"-~"),C+Q[M]+E,"g");}return U;};G.substitute=A;B.substitute=A;},"3.2.0",{optional:["dump"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("json-parse",function(B){function K(Q){return(B.config.win||this||{})[Q];}var I=K("JSON"),J=K("eval"),L=(Object.prototype.toString.call(I)==="[object JSON]"&&I),E=!!L,O=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,M=/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,D=/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,F=/(?:^|:|,)(?:\s*\[)+/g,P=/[^\],:{}\s]/,N=function(Q){return"\\u"+("0000"+(+(Q.charCodeAt(0))).toString(16)).slice(-4);},C=function(S,Q){var R=function(X,V){var U,T,W=X[V];if(W&&typeof W==="object"){for(U in W){if(W.hasOwnProperty(U)){T=R(W,U);if(T===undefined){delete W[U];}else{W[U]=T;}}}}return Q.call(X,V,W);};return typeof Q==="function"?R({"":S},""):S;},G=function(R,Q){R=R.replace(O,N);if(!P.test(R.replace(M,"@").replace(D,"]").replace(F,""))){return C(J("("+R+")"),Q);}throw new SyntaxError("JSON.parse");};B.namespace("JSON").parse=function(R,Q){if(typeof R!=="string"){R+="";}return L&&B.JSON.useNativeParse?L.parse(R,Q):G(R,Q);};function A(R,Q){return R==="ok"?true:Q;}if(L){try{E=(L.parse('{"ok":false}',A)).ok;}catch(H){E=false;}}B.JSON.useNativeParse=E;},"3.2.0");YUI.add("json-stringify",function(B){var I=(B.config.win||{}).JSON,k=B.Lang,M=k.isFunction,f=k.isObject,R=k.isArray,J=Object.prototype.toString,Z=(J.call(I)==="[object JSON]"&&I),c=!!Z,a="undefined",O="object",W="null",i="string",X="number",S="boolean",K="date",b={"undefined":a,"string":i,"[object String]":i,"number":X,"[object Number]":X,"boolean":S,"[object Boolean]":S,"[object Date]":K,"[object RegExp]":O},F="",N="{",A="}",U="[",H="]",P=",",C=",\n",L="\n",d=":",G=": ",Q='"',E=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,D={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"};function l(e){var Y=typeof e;return b[Y]||b[J.call(e)]||(Y===O?(e?O:W):a);}function h(Y){if(!D[Y]){D[Y]="\\u"+("0000"+(+(Y.charCodeAt(0))).toString(16)).slice(-4);}return D[Y];}function T(Y){return Q+Y.replace(E,h)+Q;}function V(Y,e){return Y.replace(/^/gm,e);}function g(e,u,Y){if(e===undefined){return undefined;}var n=M(u)?u:null,t=J.call(Y).match(/String|Number/)||[],v=B.JSON.dateToString,s=[],q,p,r;if(n||!R(u)){u=undefined;}if(u){q={};for(p=0,r=u.length;p<r;++p){q[u[p]]=true;}u=q;}Y=t[0]==="Number"?new Array(Math.min(Math.max(0,Y),10)+1).join(" "):(Y||F).slice(0,10);function m(x,AD){var AB=x[AD],AF=l(AB),AA=[],z=Y?G:d,y,w,AE,o,AC;if(f(AB)&&M(AB.toJSON)){AB=AB.toJSON(AD);}else{if(AF===K){AB=v(AB);}}if(M(n)){AB=n.call(x,AD,AB);}if(AB!==x[AD]){AF=l(AB);}switch(AF){case K:case O:break;case i:return T(AB);case X:return isFinite(AB)?AB+F:W;case S:return AB+F;case W:return W;default:return undefined;}for(w=s.length-1;w>=0;--w){if(s[w]===AB){throw new Error("JSON.stringify. Cyclical reference");}}y=R(AB);s.push(AB);if(y){for(w=AB.length-1;w>=0;--w){AA[w]=m(AB,w)||W;}}else{AE=u||AB;w=0;for(o in AE){if(AE.hasOwnProperty(o)){AC=m(AB,o);if(AC){AA[w++]=T(o)+z+AC;}}}}s.pop();if(Y&&AA.length){return y?U+L+V(AA.join(C),Y)+L+H:N+L+V(AA.join(C),Y)+L+A;}else{return y?U+AA.join(P)+H:N+AA.join(P)+A;}}return m({"":e},"");}if(Z){try{c=("0"===Z.stringify(0));}catch(j){c=false;}}B.mix(B.namespace("JSON"),{useNativeStringify:c,dateToString:function(e){function Y(m){return m<10?"0"+m:m;}return e.getUTCFullYear()+"-"+Y(e.getUTCMonth()+1)+"-"+Y(e.getUTCDate())+"T"+Y(e.getUTCHours())+d+Y(e.getUTCMinutes())+d+Y(e.getUTCSeconds())+"Z";},stringify:function(m,Y,e){return Z&&B.JSON.useNativeStringify?Z.stringify(m,Y,e):g(m,Y,e);}});},"3.2.0");YUI.add("json",function(A){},"3.2.0",{use:["json-parse","json-stringify"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("event-simulate",function(A){(function(){var K=A.Lang,J=A.Array,F=K.isFunction,D=K.isString,G=K.isBoolean,P=K.isObject,O=K.isNumber,N=A.config.doc,Q={click:1,dblclick:1,mouseover:1,mouseout:1,mousedown:1,mouseup:1,mousemove:1},M={keydown:1,keyup:1,keypress:1},C={blur:1,change:1,focus:1,resize:1,scroll:1,select:1},E={scroll:1,resize:1,reset:1,submit:1,change:1,select:1,error:1,abort:1};A.mix(E,Q);A.mix(E,M);function I(V,Z,U,S,b,R,L,a,X,d,c){if(!V){A.error("simulateKeyEvent(): Invalid target.");}if(D(Z)){Z=Z.toLowerCase();switch(Z){case"textevent":Z="keypress";break;case"keyup":case"keydown":case"keypress":break;default:A.error("simulateKeyEvent(): Event type '"+Z+"' not supported.");}}else{A.error("simulateKeyEvent(): Event type must be a string.");}if(!G(U)){U=true;}if(!G(S)){S=true;}if(!P(b)){b=window;}if(!G(R)){R=false;}if(!G(L)){L=false;}if(!G(a)){a=false;}if(!G(X)){X=false;}if(!O(d)){d=0;}if(!O(c)){c=0;}var Y=null;if(F(N.createEvent)){try{Y=N.createEvent("KeyEvents");Y.initKeyEvent(Z,U,S,b,R,L,a,X,d,c);}catch(W){try{Y=N.createEvent("Events");}catch(T){Y=N.createEvent("UIEvents");}finally{Y.initEvent(Z,U,S);Y.view=b;Y.altKey=L;Y.ctrlKey=R;Y.shiftKey=a;Y.metaKey=X;Y.keyCode=d;Y.charCode=c;}}V.dispatchEvent(Y);}else{if(P(N.createEventObject)){Y=N.createEventObject();Y.bubbles=U;Y.cancelable=S;Y.view=b;Y.ctrlKey=R;Y.altKey=L;Y.shiftKey=a;Y.metaKey=X;Y.keyCode=(c>0)?c:d;V.fireEvent("on"+Z,Y);}else{A.error("simulateKeyEvent(): No event simulation framework present.");}}}function B(a,f,X,U,g,Z,W,V,T,R,S,L,e,c,Y,b){if(!a){A.error("simulateMouseEvent(): Invalid target.");}if(D(f)){f=f.toLowerCase();if(!Q[f]){A.error("simulateMouseEvent(): Event type '"+f+"' not supported.");}}else{A.error("simulateMouseEvent(): Event type must be a string.");}if(!G(X)){X=true;}if(!G(U)){U=(f!="mousemove");}if(!P(g)){g=window;}if(!O(Z)){Z=1;}if(!O(W)){W=0;}if(!O(V)){V=0;}if(!O(T)){T=0;}if(!O(R)){R=0;}if(!G(S)){S=false;}if(!G(L)){L=false;}if(!G(e)){e=false;}if(!G(c)){c=false;}if(!O(Y)){Y=0;}var d=null;if(F(N.createEvent)){d=N.createEvent("MouseEvents");if(d.initMouseEvent){d.initMouseEvent(f,X,U,g,Z,W,V,T,R,S,L,e,c,Y,b);}else{d=N.createEvent("UIEvents");d.initEvent(f,X,U);d.view=g;d.detail=Z;d.screenX=W;d.screenY=V;d.clientX=T;d.clientY=R;d.ctrlKey=S;d.altKey=L;d.metaKey=c;d.shiftKey=e;d.button=Y;d.relatedTarget=b;}if(b&&!d.relatedTarget){if(f=="mouseout"){d.toElement=b;}else{if(f=="mouseover"){d.fromElement=b;}}}a.dispatchEvent(d);}else{if(P(N.createEventObject)){d=N.createEventObject();d.bubbles=X;d.cancelable=U;d.view=g;d.detail=Z;d.screenX=W;d.screenY=V;d.clientX=T;d.clientY=R;d.ctrlKey=S;d.altKey=L;d.metaKey=c;d.shiftKey=e;switch(Y){case 0:d.button=1;break;case 1:d.button=4;break;case 2:break;default:d.button=0;}d.relatedTarget=b;a.fireEvent("on"+f,d);}else{A.error("simulateMouseEvent(): No event simulation framework present.");}}}function H(W,V,S,R,L,U){if(!W){A.error("simulateUIEvent(): Invalid target.");}if(D(V)){V=V.toLowerCase();if(!C[V]){A.error("simulateUIEvent(): Event type '"+V+"' not supported.");}}else{A.error("simulateUIEvent(): Event type must be a string.");}var T=null;if(!G(S)){S=(V in E);}if(!G(R)){R=(V=="submit");}if(!P(L)){L=window;}if(!O(U)){U=1;}if(F(N.createEvent)){T=N.createEvent("UIEvents");T.initUIEvent(V,S,R,L,U);W.dispatchEvent(T);}else{if(P(N.createEventObject)){T=N.createEventObject();T.bubbles=S;T.cancelable=R;T.view=L;T.detail=U;W.fireEvent("on"+V,T);}else{A.error("simulateUIEvent(): No event simulation framework present.");}}}A.Event.simulate=function(S,R,L){L=L||{};if(Q[R]){B(S,R,L.bubbles,L.cancelable,L.view,L.detail,L.screenX,L.screenY,L.clientX,L.clientY,L.ctrlKey,L.altKey,L.shiftKey,L.metaKey,L.button,L.relatedTarget);}else{if(M[R]){I(S,R,L.bubbles,L.cancelable,L.view,L.ctrlKey,L.altKey,L.shiftKey,L.metaKey,L.keyCode,L.charCode);}else{if(C[R]){H(S,R,L.bubbles,L.cancelable,L.view,L.detail);}else{A.error("simulate(): Event '"+R+"' can't be simulated.");}}}};})();},"3.2.0",{requires:["event-base"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("test",function(B){B.namespace("Test");B.Test.Case=function(C){this._should={};for(var D in C){this[D]=C[D];}if(!B.Lang.isString(this.name)){this.name="testCase"+B.guid();}};B.Test.Case.prototype={resume:function(C){B.Test.Runner.resume(C);},wait:function(E,D){var C=arguments;if(B.Lang.isFunction(C[0])){throw new B.Test.Wait(C[0],C[1]);}else{throw new B.Test.Wait(function(){B.Assert.fail("Timeout: wait() called but resume() never called.");},(B.Lang.isNumber(C[0])?C[0]:10000));}},setUp:function(){},tearDown:function(){}};B.Test.Wait=function(D,C){this.segment=(B.Lang.isFunction(D)?D:null);this.delay=(B.Lang.isNumber(C)?C:0);};B.namespace("Test");B.Test.Suite=function(C){this.name="";this.items=[];if(B.Lang.isString(C)){this.name=C;}else{if(B.Lang.isObject(C)){B.mix(this,C,true);}}if(this.name===""){this.name="testSuite"+B.guid();}};B.Test.Suite.prototype={add:function(C){if(C instanceof B.Test.Suite||C instanceof B.Test.Case){this.items.push(C);}return this;},setUp:function(){},tearDown:function(){}};B.Test.Runner=(function(){function D(E){this.testObject=E;this.firstChild=null;this.lastChild=null;this.parent=null;this.next=null;this.results={passed:0,failed:0,total:0,ignored:0,duration:0};if(E instanceof B.Test.Suite){this.results.type="testsuite";this.results.name=E.name;}else{if(E instanceof B.Test.Case){this.results.type="testcase";this.results.name=E.name;}}}D.prototype={appendChild:function(E){var F=new D(E);if(this.firstChild===null){this.firstChild=this.lastChild=F;}else{this.lastChild.next=F;this.lastChild=F;}F.parent=this;return F;}};function C(){C.superclass.constructor.apply(this,arguments);this.masterSuite=new B.Test.Suite("yuitests"+(new Date()).getTime());this._cur=null;this._root=null;this._log=true;this._waiting=false;this._running=false;this._lastResults=null;var F=[this.TEST_CASE_BEGIN_EVENT,this.TEST_CASE_COMPLETE_EVENT,this.TEST_SUITE_BEGIN_EVENT,this.TEST_SUITE_COMPLETE_EVENT,this.TEST_PASS_EVENT,this.TEST_FAIL_EVENT,this.TEST_IGNORE_EVENT,this.COMPLETE_EVENT,this.BEGIN_EVENT];for(var E=0;E<F.length;E++){this.on(F[E],this._logEvent,this,true);}}B.extend(C,B.Event.Target,{TEST_CASE_BEGIN_EVENT:"testcasebegin",TEST_CASE_COMPLETE_EVENT:"testcasecomplete",TEST_SUITE_BEGIN_EVENT:"testsuitebegin",TEST_SUITE_COMPLETE_EVENT:"testsuitecomplete",TEST_PASS_EVENT:"pass",TEST_FAIL_EVENT:"fail",TEST_IGNORE_EVENT:"ignore",COMPLETE_EVENT:"complete",BEGIN_EVENT:"begin",disableLogging:function(){this._log=false;},enableLogging:function(){this._log=true;},_logEvent:function(G){var F="";var E="";switch(G.type){case this.BEGIN_EVENT:F="Testing began at "+(new Date()).toString()+".";E="info";break;case this.COMPLETE_EVENT:F="Testing completed at "+(new Date()).toString()+".\nPassed:"+G.results.passed+" Failed:"+G.results.failed+" Total:"+G.results.total;E="info";break;case this.TEST_FAIL_EVENT:F=G.testName+": failed.\n"+G.error.getMessage();E="fail";break;case this.TEST_IGNORE_EVENT:F=G.testName+": ignored.";E="ignore";break;case this.TEST_PASS_EVENT:F=G.testName+": passed.";E="pass";break;case this.TEST_SUITE_BEGIN_EVENT:F='Test suite "'+G.testSuite.name+'" started.';E="info";break;case this.TEST_SUITE_COMPLETE_EVENT:F='Test suite "'+G.testSuite.name+'" completed.\nPassed:'+G.results.passed+" Failed:"+G.results.failed+" Total:"+G.results.total;E="info";break;case this.TEST_CASE_BEGIN_EVENT:F='Test case "'+G.testCase.name+'" started.';E="info";break;case this.TEST_CASE_COMPLETE_EVENT:F='Test case "'+G.testCase.name+'" completed.\nPassed:'+G.results.passed+" Failed:"+G.results.failed+" Total:"+G.results.total;E="info";break;default:F="Unexpected event "+G.type;F="info";}if(this._log){B.log(F,E,"TestRunner");}},_addTestCaseToTestTree:function(F,G){var H=F.appendChild(G),I,E;for(I in G){if((I.indexOf("test")===0||(I.toLowerCase().indexOf("should")>-1&&I.indexOf(" ")>-1))&&B.Lang.isFunction(G[I])){H.appendChild(I);}}},_addTestSuiteToTestTree:function(E,H){var G=E.appendChild(H);for(var F=0;F<H.items.length;F++){if(H.items[F] instanceof B.Test.Suite){this._addTestSuiteToTestTree(G,H.items[F]);}else{if(H.items[F] instanceof B.Test.Case){this._addTestCaseToTestTree(G,H.items[F]);}}}},_buildTestTree:function(){this._root=new D(this.masterSuite);for(var E=0;E<this.masterSuite.items.length;E++){if(this.masterSuite.items[E] instanceof B.Test.Suite){this._addTestSuiteToTestTree(this._root,this.masterSuite.items[E]);}else{if(this.masterSuite.items[E] instanceof B.Test.Case){this._addTestCaseToTestTree(this._root,this.masterSuite.items[E]);}}}},_handleTestObjectComplete:function(E){if(B.Lang.isObject(E.testObject)){if(E.parent){E.parent.results.passed+=E.results.passed;E.parent.results.failed+=E.results.failed;E.parent.results.total+=E.results.total;E.parent.results.ignored+=E.results.ignored;E.parent.results[E.testObject.name]=E.results;}if(E.testObject instanceof B.Test.Suite){E.testObject.tearDown();E.results.duration=(new Date())-E._start;this.fire(this.TEST_SUITE_COMPLETE_EVENT,{testSuite:E.testObject,results:E.results});}else{if(E.testObject instanceof B.Test.Case){E.results.duration=(new Date())-E._start;this.fire(this.TEST_CASE_COMPLETE_EVENT,{testCase:E.testObject,results:E.results});}}}},_next:function(){if(this._cur===null){this._cur=this._root;}else{if(this._cur.firstChild){this._cur=this._cur.firstChild;}else{if(this._cur.next){this._cur=this._cur.next;}else{while(this._cur&&!this._cur.next&&this._cur!==this._root){this._handleTestObjectComplete(this._cur);this._cur=this._cur.parent;}this._handleTestObjectComplete(this._cur);if(this._cur==this._root){this._cur.results.type="report";this._cur.results.timestamp=(new Date()).toLocaleString();this._cur.results.duration=(new Date())-this._cur._start;this._lastResults=this._cur.results;this._running=false;this.fire(this.COMPLETE_EVENT,{results:this._lastResults});this._cur=null;}else{this._cur=this._cur.next;}}}}return this._cur;},_run:function(){var G=false;var F=this._next();if(F!==null){this._running=true;this._lastResult=null;var E=F.testObject;
if(B.Lang.isObject(E)){if(E instanceof B.Test.Suite){this.fire(this.TEST_SUITE_BEGIN_EVENT,{testSuite:E});F._start=new Date();E.setUp();}else{if(E instanceof B.Test.Case){this.fire(this.TEST_CASE_BEGIN_EVENT,{testCase:E});F._start=new Date();}}if(typeof setTimeout!="undefined"){setTimeout(function(){B.Test.Runner._run();},0);}else{this._run();}}else{this._runTest(F);}}},_resumeTest:function(J){var E=this._cur;this._waiting=false;if(!E){return;}var K=E.testObject;var H=E.parent.testObject;if(H.__yui_wait){clearTimeout(H.__yui_wait);delete H.__yui_wait;}var N=(H._should.fail||{})[K];var F=(H._should.error||{})[K];var I=false;var L=null;try{J.apply(H);if(N){L=new B.Assert.ShouldFail();I=true;}else{if(F){L=new B.Assert.ShouldError();I=true;}}}catch(M){if(H.__yui_wait){clearTimeout(H.__yui_wait);delete H.__yui_wait;}if(M instanceof B.Assert.Error){if(!N){L=M;I=true;}}else{if(M instanceof B.Test.Wait){if(B.Lang.isFunction(M.segment)){if(B.Lang.isNumber(M.delay)){if(typeof setTimeout!="undefined"){H.__yui_wait=setTimeout(function(){B.Test.Runner._resumeTest(M.segment);},M.delay);this._waiting=true;}else{throw new Error("Asynchronous tests not supported in this environment.");}}}return;}else{if(!F){L=new B.Assert.UnexpectedError(M);I=true;}else{if(B.Lang.isString(F)){if(M.message!=F){L=new B.Assert.UnexpectedError(M);I=true;}}else{if(B.Lang.isFunction(F)){if(!(M instanceof F)){L=new B.Assert.UnexpectedError(M);I=true;}}else{if(B.Lang.isObject(F)){if(!(M instanceof F.constructor)||M.message!=F.message){L=new B.Assert.UnexpectedError(M);I=true;}}}}}}}}if(I){this.fire(this.TEST_FAIL_EVENT,{testCase:H,testName:K,error:L});}else{this.fire(this.TEST_PASS_EVENT,{testCase:H,testName:K});}H.tearDown();var G=(new Date())-E._start;E.parent.results[K]={result:I?"fail":"pass",message:L?L.getMessage():"Test passed",type:"test",name:K,duration:G};if(I){E.parent.results.failed++;}else{E.parent.results.passed++;}E.parent.results.total++;if(typeof setTimeout!="undefined"){setTimeout(function(){B.Test.Runner._run();},0);}else{this._run();}},_handleError:function(E){if(this._waiting){this._resumeTest(function(){throw E;});}else{throw E;}},_runTest:function(H){var E=H.testObject;var F=H.parent.testObject;var I=F[E];var G=(F._should.ignore||{})[E];if(G){H.parent.results[E]={result:"ignore",message:"Test ignored",type:"test",name:E};H.parent.results.ignored++;H.parent.results.total++;this.fire(this.TEST_IGNORE_EVENT,{testCase:F,testName:E});if(typeof setTimeout!="undefined"){setTimeout(function(){B.Test.Runner._run();},0);}else{this._run();}}else{H._start=new Date();F.setUp();this._resumeTest(I);}},getName:function(){return this.masterSuite.name;},setName:function(E){this.masterSuite.name=E;},fire:function(E,F){F=F||{};F.type=E;C.superclass.fire.call(this,E,F);},add:function(E){this.masterSuite.add(E);return this;},clear:function(){this.masterSuite=new B.Test.Suite("yuitests"+(new Date()).getTime());},isWaiting:function(){return this._waiting;},isRunning:function(){return this._running;},getResults:function(E){if(!this._running&&this._lastResults){if(B.Lang.isFunction(E)){return E(this._lastResults);}else{return this._lastResults;}}else{return null;}},getCoverage:function(E){if(!this._running&&typeof _yuitest_coverage=="object"){if(B.Lang.isFunction(E)){return E(_yuitest_coverage);}else{return _yuitest_coverage;}}else{return null;}},resume:function(E){this._resumeTest(E||function(){});},run:function(E){var F=B.Test.Runner;if(!E&&this.masterSuite.items.length==1&&this.masterSuite.items[0] instanceof B.Test.Suite){this.masterSuite=this.masterSuite.items[0];}F._buildTestTree();F._root._start=new Date();F.fire(F.BEGIN_EVENT);F._run();}});return new C();})();B.Assert={_asserts:0,_formatMessage:function(D,C){var E=D;if(B.Lang.isString(D)&&D.length>0){return B.Lang.substitute(D,{message:C});}else{return C;}},_getCount:function(){return this._asserts;},_increment:function(){this._asserts++;},_reset:function(){this._asserts=0;},fail:function(C){throw new B.Assert.Error(B.Assert._formatMessage(C,"Test force-failed."));},areEqual:function(D,E,C){B.Assert._increment();if(D!=E){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,"Values should be equal."),D,E);}},areNotEqual:function(C,E,D){B.Assert._increment();if(C==E){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(D,"Values should not be equal."),C);}},areNotSame:function(C,E,D){B.Assert._increment();if(C===E){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(D,"Values should not be the same."),C);}},areSame:function(D,E,C){B.Assert._increment();if(D!==E){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,"Values should be the same."),D,E);}},isFalse:function(D,C){B.Assert._increment();if(false!==D){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,"Value should be false."),false,D);}},isTrue:function(D,C){B.Assert._increment();if(true!==D){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,"Value should be true."),true,D);}},isNaN:function(D,C){B.Assert._increment();if(!isNaN(D)){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,"Value should be NaN."),NaN,D);}},isNotNaN:function(D,C){B.Assert._increment();if(isNaN(D)){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(C,"Values should not be NaN."),NaN);}},isNotNull:function(D,C){B.Assert._increment();if(B.Lang.isNull(D)){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(C,"Values should not be null."),null);}},isNotUndefined:function(D,C){B.Assert._increment();if(B.Lang.isUndefined(D)){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(C,"Value should not be undefined."),undefined);}},isNull:function(D,C){B.Assert._increment();if(!B.Lang.isNull(D)){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,"Value should be null."),null,D);}},isUndefined:function(D,C){B.Assert._increment();if(!B.Lang.isUndefined(D)){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,"Value should be undefined."),undefined,D);
}},isArray:function(D,C){B.Assert._increment();if(!B.Lang.isArray(D)){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(C,"Value should be an array."),D);}},isBoolean:function(D,C){B.Assert._increment();if(!B.Lang.isBoolean(D)){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(C,"Value should be a Boolean."),D);}},isFunction:function(D,C){B.Assert._increment();if(!B.Lang.isFunction(D)){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(C,"Value should be a function."),D);}},isInstanceOf:function(D,E,C){B.Assert._increment();if(!(E instanceof D)){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,"Value isn't an instance of expected type."),D,E);}},isNumber:function(D,C){B.Assert._increment();if(!B.Lang.isNumber(D)){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(C,"Value should be a number."),D);}},isObject:function(D,C){B.Assert._increment();if(!B.Lang.isObject(D)){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(C,"Value should be an object."),D);}},isString:function(D,C){B.Assert._increment();if(!B.Lang.isString(D)){throw new B.Assert.UnexpectedValue(B.Assert._formatMessage(C,"Value should be a string."),D);}},isTypeOf:function(C,E,D){B.Assert._increment();if(typeof E!=C){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(D,"Value should be of type "+C+"."),expected,typeof E);}}};B.assert=function(D,C){B.Assert._increment();if(!D){throw new B.Assert.Error(B.Assert._formatMessage(C,"Assertion failed."));}};B.fail=B.Assert.fail;B.Assert.Error=function(C){arguments.callee.superclass.constructor.call(this,C);this.message=C;this.name="Assert Error";};B.extend(B.Assert.Error,Error,{getMessage:function(){return this.message;},toString:function(){return this.name+": "+this.getMessage();},valueOf:function(){return this.toString();}});B.Assert.ComparisonFailure=function(D,C,E){arguments.callee.superclass.constructor.call(this,D);this.expected=C;this.actual=E;this.name="ComparisonFailure";};B.extend(B.Assert.ComparisonFailure,B.Assert.Error,{getMessage:function(){return this.message+"\nExpected: "+this.expected+" ("+(typeof this.expected)+")"+"\nActual: "+this.actual+" ("+(typeof this.actual)+")";}});B.Assert.UnexpectedValue=function(D,C){arguments.callee.superclass.constructor.call(this,D);this.unexpected=C;this.name="UnexpectedValue";};B.extend(B.Assert.UnexpectedValue,B.Assert.Error,{getMessage:function(){return this.message+"\nUnexpected: "+this.unexpected+" ("+(typeof this.unexpected)+") ";}});B.Assert.ShouldFail=function(C){arguments.callee.superclass.constructor.call(this,C||"This test should fail but didn't.");this.name="ShouldFail";};B.extend(B.Assert.ShouldFail,B.Assert.Error);B.Assert.ShouldError=function(C){arguments.callee.superclass.constructor.call(this,C||"This test should have thrown an error but didn't.");this.name="ShouldError";};B.extend(B.Assert.ShouldError,B.Assert.Error);B.Assert.UnexpectedError=function(C){arguments.callee.superclass.constructor.call(this,"Unexpected error: "+C.message);this.cause=C;this.name="UnexpectedError";this.stack=C.stack;};B.extend(B.Assert.UnexpectedError,B.Assert.Error);B.ArrayAssert={contains:function(E,D,C){B.Assert._increment();if(B.Array.indexOf(D,E)==-1){B.Assert.fail(B.Assert._formatMessage(C,"Value "+E+" ("+(typeof E)+") not found in array ["+D+"]."));}},containsItems:function(E,F,D){B.Assert._increment();for(var C=0;C<E.length;C++){if(B.Array.indexOf(F,E[C])==-1){B.Assert.fail(B.Assert._formatMessage(D,"Value "+E[C]+" ("+(typeof E[C])+") not found in array ["+F+"]."));}}},containsMatch:function(E,D,C){B.Assert._increment();if(typeof E!="function"){throw new TypeError("ArrayAssert.containsMatch(): First argument must be a function.");}if(!B.Array.some(D,E)){B.Assert.fail(B.Assert._formatMessage(C,"No match found in array ["+D+"]."));}},doesNotContain:function(E,D,C){B.Assert._increment();if(B.Array.indexOf(D,E)>-1){B.Assert.fail(B.Assert._formatMessage(C,"Value found in array ["+D+"]."));}},doesNotContainItems:function(E,F,D){B.Assert._increment();for(var C=0;C<E.length;C++){if(B.Array.indexOf(F,E[C])>-1){B.Assert.fail(B.Assert._formatMessage(D,"Value found in array ["+F+"]."));}}},doesNotContainMatch:function(E,D,C){B.Assert._increment();if(typeof E!="function"){throw new TypeError("ArrayAssert.doesNotContainMatch(): First argument must be a function.");}if(B.Array.some(D,E)){B.Assert.fail(B.Assert._formatMessage(C,"Value found in array ["+D+"]."));}},indexOf:function(G,F,C,E){B.Assert._increment();for(var D=0;D<F.length;D++){if(F[D]===G){if(C!=D){B.Assert.fail(B.Assert._formatMessage(E,"Value exists at index "+D+" but should be at index "+C+"."));}return;}}B.Assert.fail(B.Assert._formatMessage(E,"Value doesn't exist in array ["+F+"]."));},itemsAreEqual:function(E,F,D){B.Assert._increment();if(E.length!=F.length){B.Assert.fail(B.Assert._formatMessage(D,"Array should have a length of "+E.length+" but has a length of "+F.length));}for(var C=0;C<E.length;C++){if(E[C]!=F[C]){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(D,"Values in position "+C+" are not equal."),E[C],F[C]);}}},itemsAreEquivalent:function(F,G,C,E){B.Assert._increment();if(typeof C!="function"){throw new TypeError("ArrayAssert.itemsAreEquivalent(): Third argument must be a function.");}if(F.length!=G.length){B.Assert.fail(B.Assert._formatMessage(E,"Array should have a length of "+F.length+" but has a length of "+G.length));}for(var D=0;D<F.length;D++){if(!C(F[D],G[D])){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(E,"Values in position "+D+" are not equivalent."),F[D],G[D]);}}},isEmpty:function(D,C){B.Assert._increment();if(D.length>0){B.Assert.fail(B.Assert._formatMessage(C,"Array should be empty."));}},isNotEmpty:function(D,C){B.Assert._increment();if(D.length===0){B.Assert.fail(B.Assert._formatMessage(C,"Array should not be empty."));}},itemsAreSame:function(E,F,D){B.Assert._increment();if(E.length!=F.length){B.Assert.fail(B.Assert._formatMessage(D,"Array should have a length of "+E.length+" but has a length of "+F.length));
}for(var C=0;C<E.length;C++){if(E[C]!==F[C]){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(D,"Values in position "+C+" are not the same."),E[C],F[C]);}}},lastIndexOf:function(G,F,C,E){for(var D=F.length;D>=0;D--){if(F[D]===G){if(C!=D){B.Assert.fail(B.Assert._formatMessage(E,"Value exists at index "+D+" but should be at index "+C+"."));}return;}}B.Assert.fail(B.Assert._formatMessage(E,"Value doesn't exist in array."));}};B.ObjectAssert={areEqual:function(D,E,C){B.Assert._increment();B.Object.each(D,function(G,F){if(D[F]!=E[F]){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,"Values should be equal for property "+F),D[F],E[F]);}});},hasKey:function(C,D,E){B.Assert._increment();if(!(C in D)){B.fail(B.Assert._formatMessage(E,"Property '"+C+"' not found on object."));}},hasKeys:function(E,C,F){B.Assert._increment();for(var D=0;D<E.length;D++){if(!(E[D] in C)){B.fail(B.Assert._formatMessage(F,"Property '"+E[D]+"' not found on object."));}}},ownsKey:function(C,D,E){B.Assert._increment();if(!D.hasOwnProperty(C)){B.fail(B.Assert._formatMessage(E,"Property '"+C+"' not found on object instance."));}},ownsKeys:function(E,C,F){B.Assert._increment();for(var D=0;D<E.length;D++){if(!C.hasOwnProperty(E[D])){B.fail(B.Assert._formatMessage(F,"Property '"+E[D]+"' not found on object instance."));}}},ownsNoKeys:function(C,E){B.Assert._increment();var D=B.Object.keys(C);if(D.length>0){B.fail(B.Assert._formatMessage(E,"Object owns "+D.length+" properties but should own none."));}}};B.DateAssert={datesAreEqual:function(D,F,C){B.Assert._increment();if(D instanceof Date&&F instanceof Date){var E="";if(D.getFullYear()!=F.getFullYear()){E="Years should be equal.";}if(D.getMonth()!=F.getMonth()){E="Months should be equal.";}if(D.getDate()!=F.getDate()){E="Days of month should be equal.";}if(E.length){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,E),D,F);}}else{throw new TypeError("Y.Assert.datesAreEqual(): Expected and actual values must be Date objects.");}},timesAreEqual:function(D,F,C){B.Assert._increment();if(D instanceof Date&&F instanceof Date){var E="";if(D.getHours()!=F.getHours()){E="Hours should be equal.";}if(D.getMinutes()!=F.getMinutes()){E="Minutes should be equal.";}if(D.getSeconds()!=F.getSeconds()){E="Seconds should be equal.";}if(E.length){throw new B.Assert.ComparisonFailure(B.Assert._formatMessage(C,E),D,F);}}else{throw new TypeError("DateY.AsserttimesAreEqual(): Expected and actual values must be Date objects.");}}};B.namespace("Test.Format");function A(C){return C.replace(/[<>"'&]/g,function(D){switch(D){case"<":return"&lt;";case">":return"&gt;";case'"':return"&quot;";case"'":return"&apos;";case"&":return"&amp;";}});}B.Test.Format.JSON=function(C){return B.JSON.stringify(C);};B.Test.Format.XML=function(D){function C(G){var E=B.Lang,F="<"+G.type+' name="'+A(G.name)+'"';if(E.isNumber(G.duration)){F+=' duration="'+G.duration+'"';}if(G.type=="test"){F+=' result="'+G.result+'" message="'+A(G.message)+'">';}else{F+=' passed="'+G.passed+'" failed="'+G.failed+'" ignored="'+G.ignored+'" total="'+G.total+'">';B.Object.each(G,function(H){if(E.isObject(H)&&!E.isArray(H)){F+=C(H);}});}F+="</"+G.type+">";return F;}return'<?xml version="1.0" encoding="UTF-8"?>'+C(D);};B.Test.Format.JUnitXML=function(C){function D(G){var E=B.Lang,F="";switch(G.type){case"test":if(G.result!="ignore"){F='<testcase name="'+A(G.name)+'" time="'+(G.duration/1000)+'">';if(G.result=="fail"){F+='<failure message="'+A(G.message)+'"><![CDATA['+G.message+"]]></failure>";}F+="</testcase>";}break;case"testcase":F='<testsuite name="'+A(G.name)+'" tests="'+G.total+'" failures="'+G.failed+'" time="'+(G.duration/1000)+'">';B.Object.each(G,function(H){if(E.isObject(H)&&!E.isArray(H)){F+=D(H);}});F+="</testsuite>";break;case"testsuite":B.Object.each(G,function(H){if(E.isObject(H)&&!E.isArray(H)){F+=D(H);}});break;case"report":F="<testsuites>";B.Object.each(G,function(H){if(E.isObject(H)&&!E.isArray(H)){F+=D(H);}});F+="</testsuites>";}return F;}return'<?xml version="1.0" encoding="UTF-8"?>'+D(C);};B.Test.Format.TAP=function(D){var E=1;function C(G){var F=B.Lang,H="";switch(G.type){case"test":if(G.result!="ignore"){H="ok "+(E++)+" - "+G.name;if(G.result=="fail"){H="not "+H+" - "+G.message;}H+="\n";}else{H="#Ignored test "+G.name+"\n";}break;case"testcase":H="#Begin testcase "+G.name+"("+G.failed+" failed of "+G.total+")\n";B.Object.each(G,function(I){if(F.isObject(I)&&!F.isArray(I)){H+=C(I);}});H+="#End testcase "+G.name+"\n";break;case"testsuite":H="#Begin testsuite "+G.name+"("+G.failed+" failed of "+G.total+")\n";B.Object.each(G,function(I){if(F.isObject(I)&&!F.isArray(I)){H+=C(I);}});H+="#End testsuite "+G.name+"\n";break;case"report":B.Object.each(G,function(I){if(F.isObject(I)&&!F.isArray(I)){H+=C(I);}});}return H;}return"1.."+D.total+"\n"+C(D);};B.namespace("Coverage.Format");B.Coverage.Format.JSON=function(C){return B.JSON.stringify(C);};B.Coverage.Format.XdebugJSON=function(D){var C={};B.Object.each(D,function(F,E){C[E]=D[E].lines;});return B.JSON.stringify(C);};B.namespace("Test");B.Test.Reporter=function(C,D){this.url=C;this.format=D||B.Test.Format.XML;this._fields=new Object();this._form=null;this._iframe=null;};B.Test.Reporter.prototype={constructor:B.Test.Reporter,addField:function(C,D){this._fields[C]=D;},clearFields:function(){this._fields=new Object();},destroy:function(){if(this._form){this._form.parentNode.removeChild(this._form);this._form=null;}if(this._iframe){this._iframe.parentNode.removeChild(this._iframe);this._iframe=null;}this._fields=null;},report:function(C){if(!this._form){this._form=document.createElement("form");this._form.method="post";this._form.style.visibility="hidden";this._form.style.position="absolute";this._form.style.top=0;document.body.appendChild(this._form);if(B.UA.ie){this._iframe=document.createElement('<iframe name="yuiTestTarget" />');}else{this._iframe=document.createElement("iframe");this._iframe.name="yuiTestTarget";}this._iframe.src="javascript:false";
this._iframe.style.visibility="hidden";this._iframe.style.position="absolute";this._iframe.style.top=0;document.body.appendChild(this._iframe);this._form.target="yuiTestTarget";}this._form.action=this.url;while(this._form.hasChildNodes()){this._form.removeChild(this._form.lastChild);}this._fields.results=this.format(C);this._fields.useragent=navigator.userAgent;this._fields.timestamp=(new Date()).toLocaleString();B.Object.each(this._fields,function(E,F){if(typeof E!="function"){var D=document.createElement("input");D.type="hidden";D.name=F;D.value=E;this._form.appendChild(D);}},this);delete this._fields.results;delete this._fields.useragent;delete this._fields.timestamp;if(arguments[1]!==false){this._form.submit();}}};B.Mock=function(E){E=E||{};var C=null;try{C=B.Object(E);}catch(D){C={};B.log("Couldn't create mock with prototype.","warn","Mock");}B.Object.each(E,function(F){if(B.Lang.isFunction(E[F])){C[F]=function(){B.Assert.fail("Method "+F+"() was called but was not expected to be.");};}});return C;};B.Mock.expect=function(D,H){if(!D.__expectations){D.__expectations={};}if(H.method){var G=H.method,F=H.args||H.arguments||[],C=H.returns,J=B.Lang.isNumber(H.callCount)?H.callCount:1,E=H.error,I=H.run||function(){};D.__expectations[G]=H;H.callCount=J;H.actualCallCount=0;B.Array.each(F,function(K,L,M){if(!(M[L] instanceof B.Mock.Value)){M[L]=B.Mock.Value(B.Assert.areSame,[K],"Argument "+L+" of "+G+"() is incorrect.");}});if(J>0){D[G]=function(){try{H.actualCallCount++;B.Assert.areEqual(F.length,arguments.length,"Method "+G+"() passed incorrect number of arguments.");for(var M=0,K=F.length;M<K;M++){F[M].verify(arguments[M]);}I.apply(this,arguments);if(E){throw E;}}catch(L){B.Test.Runner._handleError(L);}return C;};}else{D[G]=function(){try{B.Assert.fail("Method "+G+"() should not have been called.");}catch(K){B.Test.Runner._handleError(K);}};}}else{if(H.property){D.__expectations[G]=H;}}};B.Mock.verify=function(C){try{B.Object.each(C.__expectations,function(E){if(E.method){B.Assert.areEqual(E.callCount,E.actualCallCount,"Method "+E.method+"() wasn't called the expected number of times.");}else{if(E.property){B.Assert.areEqual(E.value,C[E.property],"Property "+E.property+" wasn't set to the correct value.");}}});}catch(D){B.Test.Runner._handleError(D);}};B.Mock.Value=function(E,C,D){if(this instanceof B.Mock.Value){this.verify=function(G){var F=[].concat(C||[]);F.push(G);F.push(D);E.apply(null,F);};}else{return new B.Mock.Value(E,C,D);}};B.Mock.Value.Any=B.Mock.Value(function(){});B.Mock.Value.Boolean=B.Mock.Value(B.Assert.isBoolean);B.Mock.Value.Number=B.Mock.Value(B.Assert.isNumber);B.Mock.Value.String=B.Mock.Value(B.Assert.isString);B.Mock.Value.Object=B.Mock.Value(B.Assert.isObject);B.Mock.Value.Function=B.Mock.Value(B.Assert.isFunction);if(typeof YUITest=="undefined"||!YUITest){YUITest={TestRunner:B.Test.Runner,ResultsFormat:B.Test.Format,CoverageFormat:B.Coverage.Format};}},"3.2.0",{requires:["substitute","event-base","json-stringify"]});




/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("plugin",function(B){function A(C){if(!(this.hasImpl&&this.hasImpl(B.Plugin.Base))){A.superclass.constructor.apply(this,arguments);}else{A.prototype.initializer.apply(this,arguments);}}A.ATTRS={host:{writeOnce:true}};A.NAME="plugin";A.NS="plugin";B.extend(A,B.Base,{_handles:null,initializer:function(C){this._handles=[];},destructor:function(){if(this._handles){for(var D=0,C=this._handles.length;D<C;D++){this._handles[D].detach();}}},doBefore:function(G,D,C){var E=this.get("host"),F;if(G in E){F=this.beforeHostMethod(G,D,C);}else{if(E.on){F=this.onHostEvent(G,D,C);}}return F;},doAfter:function(G,D,C){var E=this.get("host"),F;if(G in E){F=this.afterHostMethod(G,D,C);}else{if(E.after){F=this.afterHostEvent(G,D,C);}}return F;},onHostEvent:function(E,D,C){var F=this.get("host").on(E,D,C||this);this._handles.push(F);return F;},afterHostEvent:function(E,D,C){var F=this.get("host").after(E,D,C||this);this._handles.push(F);return F;},beforeHostMethod:function(F,D,C){var E=B.Do.before(D,this.get("host"),F,C||this);this._handles.push(E);return E;},afterHostMethod:function(F,D,C){var E=B.Do.after(D,this.get("host"),F,C||this);this._handles.push(E);return E;},toString:function(){return this.constructor.NAME+"["+this.constructor.NS+"]";}});B.namespace("Plugin").Base=A;},"3.2.0",{requires:["base-base"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("node-event-simulate",function(A){A.Node.prototype.simulate=function(C,B){A.Event.simulate(A.Node.getDOMNode(this),C,B);};},"3.2.0",{requires:["node-base","event-simulate"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("event-key",function(a){a.Env.evt.plugins.key={on:function(e,g,b,k,c){var i=a.Array(arguments,0,true),f,j,h,d;f=k&&k.split(":");if(!k||k.indexOf(":")==-1||!f[1]){i[0]="key"+((f&&f[0])||"press");return a.on.apply(a,i);}j=f[0];h=(f[1])?f[1].split(/,|\+/):null;d=(a.Lang.isString(b)?b:a.stamp(b))+k;d=d.replace(/,/g,"_");if(!a.getEvent(d)){a.on(e+j,function(p){var q=false,m=false,n,l,o;for(n=0;n<h.length;n=n+1){l=h[n];o=parseInt(l,10);if(a.Lang.isNumber(o)){if(p.charCode===o){q=true;}else{m=true;}}else{if(q||!m){q=(p[l+"Key"]);m=!q;}}}if(q){a.fire(d,p);}},b);}i.splice(2,2);i[0]=d;return a.on.apply(a,i);}};},"3.2.0",{requires:["node-base"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("node-focusmanager",function(B){var J="activeDescendant",L="id",I="disabled",N="tabIndex",E="focused",A="focusClass",Q="circular",C="UI",F="key",G=J+"Change",O="host",P={37:true,38:true,39:true,40:true},M={"a":true,"button":true,"input":true,"object":true},H=B.Lang,K=B.UA,D=function(){D.superclass.constructor.apply(this,arguments);};D.ATTRS={focused:{value:false,readOnly:true},descendants:{getter:function(R){return this.get(O).all(R);}},activeDescendant:{setter:function(V){var T=H.isNumber,S=B.Attribute.INVALID_VALUE,R=this._descendantsMap,Y=this._descendants,X,U,W;if(T(V)){X=V;U=X;}else{if((V instanceof B.Node)&&R){X=R[V.get(L)];if(T(X)){U=X;}else{U=S;}}else{U=S;}}if(Y){W=Y.item(X);if(W&&W.get("disabled")){U=S;}}return U;}},keys:{value:{next:null,previous:null}},focusClass:{},circular:{value:true}};B.extend(D,B.Plugin.Base,{_stopped:true,_descendants:null,_descendantsMap:null,_focusedNode:null,_lastNodeIndex:0,_eventHandlers:null,_initDescendants:function(){var Y=this.get("descendants"),R={},W=-1,V,U=this.get(J),X,S,T=0;if(H.isUndefined(U)){U=-1;}if(Y){V=Y.size();for(T=0;T<V;T++){X=Y.item(T);if(W===-1&&!X.get(I)){W=T;}if(U<0&&parseInt(X.getAttribute(N,2),10)===0){U=T;}if(X){X.set(N,-1);}S=X.get(L);if(!S){S=B.guid();X.set(L,S);}R[S]=T;}if(U<0){U=0;}X=Y.item(U);if(!X||X.get(I)){X=Y.item(W);U=W;}this._lastNodeIndex=V-1;this._descendants=Y;this._descendantsMap=R;this.set(J,U);if(X){X.set(N,0);}}},_isDescendant:function(R){return(R.get(L) in this._descendantsMap);},_removeFocusClass:function(){var S=this._focusedNode,T=this.get(A),R;if(T){R=H.isString(T)?T:T.className;}if(S&&R){S.removeClass(R);}},_detachKeyHandler:function(){var S=this._prevKeyHandler,R=this._nextKeyHandler;if(S){S.detach();}if(R){R.detach();}},_preventScroll:function(R){if(P[R.keyCode]&&this._isDescendant(R.target)){R.preventDefault();}},_fireClick:function(S){var R=S.target,T=R.get("nodeName").toLowerCase();if(S.keyCode===13&&(!M[T]||(T==="a"&&!R.getAttribute("href")))){R.simulate("click");}},_attachKeyHandler:function(){this._detachKeyHandler();var U=this.get("keys.next"),S=this.get("keys.previous"),T=this.get(O),R=this._eventHandlers;if(S){this._prevKeyHandler=B.on(F,B.bind(this._focusPrevious,this),T,S);}if(U){this._nextKeyHandler=B.on(F,B.bind(this._focusNext,this),T,U);}if(K.opera){R.push(T.on("keypress",this._preventScroll,this));}if(!K.opera){R.push(T.on("keypress",this._fireClick,this));}},_detachEventHandlers:function(){this._detachKeyHandler();var R=this._eventHandlers;if(R){B.Array.each(R,function(S){S.detach();});this._eventHandlers=null;}},_attachEventHandlers:function(){var U=this._descendants,R,S,T;if(U&&U.size()){R=this._eventHandlers||[];S=this.get(O).get("ownerDocument");if(R.length===0){R.push(S.on("focus",this._onDocFocus,this));R.push(S.on("mousedown",this._onDocMouseDown,this));R.push(this.after("keysChange",this._attachKeyHandler));R.push(this.after("descendantsChange",this._initDescendants));R.push(this.after(G,this._afterActiveDescendantChange));T=this.after("focusedChange",B.bind(function(V){if(V.newVal){this._attachKeyHandler();T.detach();}},this));R.push(T);}this._eventHandlers=R;}},_onDocMouseDown:function(U){var W=this.get(O),R=U.target,V=W.contains(R),T,S=function(Y){var X=false;if(!Y.compareTo(W)){X=this._isDescendant(Y)?Y:S.call(this,Y.get("parentNode"));}return X;};if(V){T=S.call(this,R);if(T){R=T;}else{if(!T&&this.get(E)){this._set(E,false);this._onDocFocus(U);}}}if(V&&this._isDescendant(R)){this.focus(R);}else{if(K.webkit&&this.get(E)&&(!V||(V&&!this._isDescendant(R)))){this._set(E,false);this._onDocFocus(U);}}},_onDocFocus:function(W){var U=this._focusTarget||W.target,S=this.get(E),V=this.get(A),T=this._focusedNode,R;if(this._focusTarget){this._focusTarget=null;}if(this.get(O).contains(U)){R=this._isDescendant(U);if(!S&&R){S=true;}else{if(S&&!R){S=false;}}}else{S=false;}if(V){if(T&&(!T.compareTo(U)||!S)){this._removeFocusClass();}if(R&&S){if(V.fn){U=V.fn(U);U.addClass(V.className);}else{U.addClass(V);}this._focusedNode=U;}}this._set(E,S);},_focusNext:function(S,T){var R=T||this.get(J),U;if(this._isDescendant(S.target)&&(R<=this._lastNodeIndex)){R=R+1;if(R===(this._lastNodeIndex+1)&&this.get(Q)){R=0;}U=this._descendants.item(R);if(U&&U.get(I)){this._focusNext(S,R);}else{this.focus(R);}}this._preventScroll(S);},_focusPrevious:function(S,T){var R=T||this.get(J),U;if(this._isDescendant(S.target)&&R>=0){R=R-1;if(R===-1&&this.get(Q)){R=this._lastNodeIndex;}U=this._descendants.item(R);if(U&&U.get(I)){this._focusPrevious(S,R);}else{this.focus(R);}}this._preventScroll(S);},_afterActiveDescendantChange:function(R){var S=this._descendants.item(R.prevVal);if(S){S.set(N,-1);}S=this._descendants.item(R.newVal);if(S){S.set(N,0);}},initializer:function(R){this.start();},destructor:function(){this.stop();this.get(O).focusManager=null;},focus:function(R){if(H.isUndefined(R)){R=this.get(J);}this.set(J,R,{src:C});var S=this._descendants.item(this.get(J));if(S){S.focus();if(K.opera&&S.get("nodeName").toLowerCase()==="button"){this._focusTarget=S;}}},blur:function(){var R;if(this.get(E)){R=this._descendants.item(this.get(J));if(R){R.blur();this._removeFocusClass();}this._set(E,false,{src:C});}},start:function(){if(this._stopped){this._initDescendants();this._attachEventHandlers();this._stopped=false;}},stop:function(){if(!this._stopped){this._detachEventHandlers();this._descendants=null;this._focusedNode=null;this._lastNodeIndex=0;this._stopped=true;}},refresh:function(){this._initDescendants();if(!this._eventHandlers){this._attachEventHandlers();}}});D.NAME="nodeFocusManager";D.NS="focusManager";B.namespace("Plugin");B.Plugin.NodeFocusManager=D;},"3.2.0",{requires:["attribute","node","plugin","node-event-simulate","event-key","event-focus"]});/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 3.2.0
build: 2676
*/
YUI.add("node-menunav",function(D){var m=D.UA,u=D.later,AM=D.ClassNameManager.getClassName,R="menu",G="menuitem",AI="hidden",S="parentNode",V="children",AA="offsetHeight",AD="offsetWidth",AO="px",g="id",I=".",E="handledMouseOut",s="handledMouseOver",a="active",AK="label",d="a",x="mousedown",AP="keydown",AC="click",Q="",U="first-of-type",AQ="role",N="presentation",AE="descendants",j="UI",v="activeDescendant",J="useARIA",y="aria-hidden",z="content",c="host",h=v+"Change",w="autoSubmenuDisplay",T="mouseOutHideDelay",l=AM(R),AG=AM(R,AI),Z=AM(R,"horizontal"),AJ=AM(R,AK),k=AM(R,AK,a),X=AM(R,AK,(R+"visible")),K=AM(G),A=AM(G,a),i=I+l,AH=(I+AM(R,"toggle")),n=I+AM(R,z),AR=I+AJ,AN=">"+n+">ul>li>a",O=">"+n+">ul>li>"+AR+">a:first-child";var L=function(Y){var AT=Y.previous(),AS;if(!AT){AS=Y.get(S).get(V);AT=AS.item(AS.size()-1);}return AT;};var b=function(Y){var AS=Y.next();if(!AS){AS=Y.get(S).get(V).item(0);}return AS;};var F=function(Y){var AS=false;if(Y){AS=Y.get("nodeName").toLowerCase()===d;}return AS;};var P=function(Y){return Y.hasClass(K);};var t=function(Y){return Y.hasClass(AJ);};var r=function(Y){return Y.hasClass(Z);};var o=function(Y){return Y.hasClass(X);};var q=function(Y){return F(Y)?Y:Y.one(d);};var AB=function(AT,AS,Y){var AU;if(AT){if(AT.hasClass(AS)){AU=AT;}if(!AU&&Y){AU=AT.ancestor((I+AS));}}return AU;};var M=function(Y){return Y.ancestor(i);};var W=function(AS,Y){return AB(AS,l,Y);};var AF=function(AS,Y){var AT;if(AS){AT=AB(AS,K,Y);}return AT;};var p=function(AS,Y){var AT;if(AS){if(Y){AT=AB(AS,AJ,Y);}else{AT=AB(AS,AJ)||AS.one((I+AJ));}}return AT;};var B=function(AS,Y){var AT;if(AS){AT=AF(AS,Y)||p(AS,Y);}return AT;};var C=function(Y){return B(Y.one("li"));};var f=function(Y){return P(Y)?A:k;};var e=function(Y,AS){return Y&&!Y[s]&&(Y.compareTo(AS)||Y.contains(AS));};var H=function(AS,Y){return AS&&!AS[E]&&(!AS.compareTo(Y)&&!AS.contains(Y));};var AL=function(){AL.superclass.constructor.apply(this,arguments);};AL.NAME="nodeMenuNav";AL.NS="menuNav";AL.SHIM_TEMPLATE_TITLE="Menu Stacking Shim";AL.SHIM_TEMPLATE='<iframe frameborder="0" tabindex="-1" class="'+AM("shim")+'" title="'+AL.SHIM_TEMPLATE_TITLE+'" src="javascript:false;"></iframe>';AL.ATTRS={useARIA:{value:true,writeOnce:true,lazyAdd:false,setter:function(AV){var AS=this.get(c),AW,Y,AU,AT;if(AV){AS.set(AQ,R);AS.all("ul,li,"+n).set(AQ,N);AS.all((I+AM(G,z))).set(AQ,G);AS.all((I+AJ)).each(function(AX){AW=AX;Y=AX.one(AH);if(Y){Y.set(AQ,N);AW=Y.previous();}AW.set(AQ,G);AW.set("aria-haspopup",true);AU=AX.next();if(AU){AU.set(AQ,R);AW=AU.previous();Y=AW.one(AH);if(Y){AW=Y;}AT=D.stamp(AW);if(!AW.get(g)){AW.set(g,AT);}AU.set("aria-labelledby",AT);AU.set(y,true);}});}}},autoSubmenuDisplay:{value:true,writeOnce:true},submenuShowDelay:{value:250,writeOnce:true},submenuHideDelay:{value:250,writeOnce:true},mouseOutHideDelay:{value:750,writeOnce:true}};D.extend(AL,D.Plugin.Base,{_rootMenu:null,_activeItem:null,_activeMenu:null,_hasFocus:false,_blockMouseEvent:false,_currentMouseX:0,_movingToSubmenu:false,_showSubmenuTimer:null,_hideSubmenuTimer:null,_hideAllSubmenusTimer:null,_firstItem:null,initializer:function(AT){var AU=this,AV=this.get(c),AS=[],Y;if(AV){AU._rootMenu=AV;AV.all("ul:first-child").addClass(U);AV.all(i).addClass(AG);AS.push(AV.on("mouseover",AU._onMouseOver,AU));AS.push(AV.on("mouseout",AU._onMouseOut,AU));AS.push(AV.on("mousemove",AU._onMouseMove,AU));AS.push(AV.on(x,AU._toggleSubmenuDisplay,AU));AS.push(D.on("key",AU._toggleSubmenuDisplay,AV,"down:13",AU));AS.push(AV.on(AC,AU._toggleSubmenuDisplay,AU));AS.push(AV.on("keypress",AU._onKeyPress,AU));AS.push(AV.on(AP,AU._onKeyDown,AU));Y=AV.get("ownerDocument");AS.push(Y.on(x,AU._onDocMouseDown,AU));AS.push(Y.on("focus",AU._onDocFocus,AU));this._eventHandlers=AS;AU._initFocusManager();}},destructor:function(){var Y=this._eventHandlers;if(Y){D.Array.each(Y,function(AS){AS.detach();});this._eventHandlers=null;}this.get(c).unplug("focusManager");},_isRoot:function(Y){return this._rootMenu.compareTo(Y);},_getTopmostSubmenu:function(AU){var AT=this,Y=M(AU),AS;if(!Y){AS=AU;}else{if(AT._isRoot(Y)){AS=AU;}else{AS=AT._getTopmostSubmenu(Y);}}return AS;},_clearActiveItem:function(){var AS=this,Y=AS._activeItem;if(Y){Y.removeClass(f(Y));}AS._activeItem=null;},_setActiveItem:function(AS){var Y=this;if(AS){Y._clearActiveItem();AS.addClass(f(AS));Y._activeItem=AS;}},_focusItem:function(AT){var AS=this,Y,AU;if(AT&&AS._hasFocus){Y=M(AT);AU=q(AT);if(Y&&!Y.compareTo(AS._activeMenu)){AS._activeMenu=Y;AS._initFocusManager();}AS._focusManager.focus(AU);}},_showMenu:function(AU){var Y=M(AU),AT=AU.get(S),AS=AT.getXY();if(this.get(J)){AU.set(y,false);}if(r(Y)){AS[1]=AS[1]+AT.get(AA);}else{AS[0]=AS[0]+AT.get(AD);}AU.setXY(AS);if(m.ie<8){if(m.ie===6&&!AU.hasIFrameShim){AU.appendChild(D.Node.create(AL.SHIM_TEMPLATE));AU.hasIFrameShim=true;}AU.setStyles({height:Q,width:Q});AU.setStyles({height:(AU.get(AA)+AO),width:(AU.get(AD)+AO)});}AU.previous().addClass(X);AU.removeClass(AG);},_hideMenu:function(AU,AS){var AT=this,AV=AU.previous(),Y;AV.removeClass(X);if(AS){AT._focusItem(AV);AT._setActiveItem(AV);}Y=AU.one((I+A));if(Y){Y.removeClass(A);}AU.setStyles({left:Q,top:Q});AU.addClass(AG);if(AT.get(J)){AU.set(y,true);}},_hideAllSubmenus:function(AS){var Y=this;AS.all(i).each(D.bind(function(AT){Y._hideMenu(AT);},Y));},_cancelShowSubmenuTimer:function(){var AS=this,Y=AS._showSubmenuTimer;if(Y){Y.cancel();AS._showSubmenuTimer=null;}},_cancelHideSubmenuTimer:function(){var Y=this,AS=Y._hideSubmenuTimer;if(AS){AS.cancel();Y._hideSubmenuTimer=null;}},_initFocusManager:function(){var AU=this,AW=AU._rootMenu,AS=AU._activeMenu||AW,AV=AU._isRoot(AS)?Q:("#"+AS.get("id")),Y=AU._focusManager,AT,AX,AY;if(r(AS)){AX=AV+AN+","+AV+O;AT={next:"down:39",previous:"down:37"};}else{AX=AV+AN;AT={next:"down:40",previous:"down:38"};}if(!Y){AW.plug(D.Plugin.NodeFocusManager,{descendants:AX,keys:AT,circular:true});Y=AW.focusManager;AY="#"+AW.get("id")+i+" a,"+AH;AW.all(AY).set("tabIndex",-1);Y.on(h,this._onActiveDescendantChange,Y,this);Y.after(h,this._afterActiveDescendantChange,Y,this);
AU._focusManager=Y;}else{Y.set(v,-1);Y.set(AE,AX);Y.set("keys",AT);}},_onActiveDescendantChange:function(AS,Y){if(AS.src===j&&Y._activeMenu&&!Y._movingToSubmenu){Y._hideAllSubmenus(Y._activeMenu);}},_afterActiveDescendantChange:function(AS,Y){var AT;if(AS.src===j){AT=B(this.get(AE).item(AS.newVal),true);Y._setActiveItem(AT);}},_onDocFocus:function(AV){var AU=this,Y=AU._activeItem,AT=AV.target,AS;if(AU._rootMenu.contains(AT)){if(AU._hasFocus){AS=M(AT);if(!AU._activeMenu.compareTo(AS)){AU._activeMenu=AS;AU._initFocusManager();AU._focusManager.set(v,AT);AU._setActiveItem(B(AT,true));}}else{AU._hasFocus=true;Y=B(AT,true);if(Y){AU._setActiveItem(Y);}}}else{AU._clearActiveItem();AU._cancelShowSubmenuTimer();AU._hideAllSubmenus(AU._rootMenu);AU._activeMenu=AU._rootMenu;AU._initFocusManager();AU._focusManager.set(v,0);AU._hasFocus=false;}},_onMenuMouseOver:function(AU,AT){var AS=this,Y=AS._hideAllSubmenusTimer;if(Y){Y.cancel();AS._hideAllSubmenusTimer=null;}AS._cancelHideSubmenuTimer();if(AU&&!AU.compareTo(AS._activeMenu)){AS._activeMenu=AU;if(AS._hasFocus){AS._initFocusManager();}}if(AS._movingToSubmenu&&r(AU)){AS._movingToSubmenu=false;}},_hideAndFocusLabel:function(){var AT=this,AS=AT._activeMenu,Y;AT._hideAllSubmenus(AT._rootMenu);if(AS){Y=AT._getTopmostSubmenu(AS);AT._focusItem(Y.previous());}},_onMenuMouseOut:function(AY,AW){var AV=this,AT=AV._activeMenu,AX=AW.relatedTarget,Y=AV._activeItem,AU,AS;if(AT&&!AT.contains(AX)){AU=M(AT);if(AU&&!AU.contains(AX)){if(AV.get(T)>0){AV._cancelShowSubmenuTimer();AV._hideAllSubmenusTimer=u(AV.get(T),AV,AV._hideAndFocusLabel);}}else{if(Y){AS=M(Y);if(!AV._isRoot(AS)){AV._focusItem(AS.previous());}}}}},_onMenuLabelMouseOver:function(AU,Y){var AS=this,AV=AS._activeMenu,AY=AS._isRoot(AV),AT=(AS.get(w)&&AY||!AY),AW=AS.get("submenuShowDelay"),AX;var AZ=function(Aa){AS._cancelHideSubmenuTimer();AS._cancelShowSubmenuTimer();if(!o(AU)){AX=AU.next();if(AX){AS._hideAllSubmenus(AV);AS._showSubmenuTimer=u(Aa,AS,AS._showMenu,AX);}}};AS._focusItem(AU);AS._setActiveItem(AU);if(AT){if(AS._movingToSubmenu){D.message("Pause path");AS._hoverTimer=u(AW,AS,function(){AZ(0);});}else{AZ(AW);}}},_onMenuLabelMouseOut:function(AV,AX){var AW=this,AS=AW._isRoot(AW._activeMenu),AU=(AW.get(w)&&AS||!AS),AY=AX.relatedTarget,AT=AV.next(),Y=AW._hoverTimer;if(Y){Y.cancel();}AW._clearActiveItem();if(AU){if(AW._movingToSubmenu&&!AW._showSubmenuTimer&&AT){AW._hideSubmenuTimer=u(AW.get("submenuHideDelay"),AW,AW._hideMenu,AT);}else{if(!AW._movingToSubmenu&&AT&&(!AY||(AY&&!AT.contains(AY)&&!AY.compareTo(AT)))){AW._cancelShowSubmenuTimer();AW._hideMenu(AT);}}}},_onMenuItemMouseOver:function(AU,AW){var AV=this,AT=AV._activeMenu,Y=AV._isRoot(AT),AS=(AV.get(w)&&Y||!Y);AV._focusItem(AU);AV._setActiveItem(AU);if(AS&&!AV._movingToSubmenu){AV._hideAllSubmenus(AT);}},_onMenuItemMouseOut:function(Y,AS){this._clearActiveItem();},_onVerticalMenuKeyDown:function(Y){var AS=this,AW=AS._activeMenu,Ab=AS._rootMenu,AT=Y.target,AV=false,Aa=Y.keyCode,AY,AU,AX,AZ;switch(Aa){case 37:AU=M(AW);if(AU&&r(AU)){AS._hideMenu(AW);AX=L(AW.get(S));AZ=B(AX);if(AZ){if(t(AZ)){AY=AZ.next();if(AY){AS._showMenu(AY);AS._focusItem(C(AY));AS._setActiveItem(C(AY));}else{AS._focusItem(AZ);AS._setActiveItem(AZ);}}else{AS._focusItem(AZ);AS._setActiveItem(AZ);}}}else{if(!AS._isRoot(AW)){AS._hideMenu(AW,true);}}AV=true;break;case 39:if(t(AT)){AY=AT.next();if(AY){AS._showMenu(AY);AS._focusItem(C(AY));AS._setActiveItem(C(AY));}}else{if(r(Ab)){AY=AS._getTopmostSubmenu(AW);AX=b(AY.get(S));AZ=B(AX);AS._hideAllSubmenus(Ab);if(AZ){if(t(AZ)){AY=AZ.next();if(AY){AS._showMenu(AY);AS._focusItem(C(AY));AS._setActiveItem(C(AY));}else{AS._focusItem(AZ);AS._setActiveItem(AZ);}}else{AS._focusItem(AZ);AS._setActiveItem(AZ);}}}}AV=true;break;}if(AV){Y.preventDefault();}},_onHorizontalMenuKeyDown:function(AX){var AW=this,AU=AW._activeMenu,AS=AX.target,Y=B(AS,true),AV=false,AY=AX.keyCode,AT;if(AY===40){AW._hideAllSubmenus(AU);if(t(Y)){AT=Y.next();if(AT){AW._showMenu(AT);AW._focusItem(C(AT));AW._setActiveItem(C(AT));}AV=true;}}if(AV){AX.preventDefault();}},_onMouseMove:function(AS){var Y=this;u(10,Y,function(){Y._currentMouseX=AS.pageX;});},_onMouseOver:function(AV){var AU=this,AS,Y,AX,AT,AW;if(AU._blockMouseEvent){AU._blockMouseEvent=false;}else{AS=AV.target;Y=W(AS,true);AX=p(AS,true);AW=AF(AS,true);if(e(Y,AS)){AU._onMenuMouseOver(Y,AV);Y[s]=true;Y[E]=false;AT=M(Y);if(AT){AT[E]=true;AT[s]=false;}}if(e(AX,AS)){AU._onMenuLabelMouseOver(AX,AV);AX[s]=true;AX[E]=false;}if(e(AW,AS)){AU._onMenuItemMouseOver(AW,AV);AW[s]=true;AW[E]=false;}}},_onMouseOut:function(AS){var AT=this,AV=AT._activeMenu,Aa=false,AU,AW,AY,Y,AX,AZ;AT._movingToSubmenu=(AV&&!r(AV)&&((AS.pageX-5)>AT._currentMouseX));AU=AS.target;AW=AS.relatedTarget;AY=W(AU,true);Y=p(AU,true);AZ=AF(AU,true);if(H(Y,AW)){AT._onMenuLabelMouseOut(Y,AS);Y[E]=true;Y[s]=false;}if(H(AZ,AW)){AT._onMenuItemMouseOut(AZ,AS);AZ[E]=true;AZ[s]=false;}if(Y){AX=Y.next();if(AX&&AW&&(AW.compareTo(AX)||AX.contains(AW))){Aa=true;}}if(H(AY,AW)||Aa){AT._onMenuMouseOut(AY,AS);AY[E]=true;AY[s]=false;}},_toggleSubmenuDisplay:function(AT){var AU=this,AV=AT.target,AS=p(AV,true),Y=AT.type,AZ,AY,AX,Aa,Ab,AW;if(AS){AZ=F(AV)?AV:AV.ancestor(F);if(AZ){AX=AZ.getAttribute("href",2);Aa=AX.indexOf("#");Ab=AX.length;if(Aa===0&&Ab>1){AW=AX.substr(1,Ab);AY=AS.next();if(AY&&(AY.get(g)===AW)){if(Y===x||Y===AP){if((m.opera||m.gecko||m.ie)&&Y===AP&&!AU._preventClickHandle){AU._preventClickHandle=AU._rootMenu.on("click",function(Ac){Ac.preventDefault();AU._preventClickHandle.detach();AU._preventClickHandle=null;});}if(Y==x){AT.preventDefault();AT.stopImmediatePropagation();AU._hasFocus=true;}if(AU._isRoot(M(AV))){if(o(AS)){AU._hideMenu(AY);AU._focusItem(AS);AU._setActiveItem(AS);}else{AU._hideAllSubmenus(AU._rootMenu);AU._showMenu(AY);AU._focusItem(C(AY));AU._setActiveItem(C(AY));}}else{if(AU._activeItem==AS){AU._showMenu(AY);AU._focusItem(C(AY));AU._setActiveItem(C(AY));}else{if(!AS._clickHandle){AS._clickHandle=AS.on("click",function(){AU._hideAllSubmenus(AU._rootMenu);
AU._hasFocus=false;AU._clearActiveItem();AS._clickHandle.detach();AS._clickHandle=null;});}}}}if(Y===AC){AT.preventDefault();}}}}}},_onKeyPress:function(Y){switch(Y.keyCode){case 37:case 38:case 39:case 40:Y.preventDefault();break;}},_onKeyDown:function(AW){var AV=this,Y=AV._activeItem,AS=AW.target,AU=M(AS),AT;if(AU){AV._activeMenu=AU;if(r(AU)){AV._onHorizontalMenuKeyDown(AW);}else{AV._onVerticalMenuKeyDown(AW);}if(AW.keyCode===27){if(!AV._isRoot(AU)){if(m.opera){u(0,AV,function(){AV._hideMenu(AU,true);});}else{AV._hideMenu(AU,true);}AW.stopPropagation();AV._blockMouseEvent=m.gecko?true:false;}else{if(Y){if(t(Y)&&o(Y)){AT=Y.next();if(AT){AV._hideMenu(AT);}}else{AV._focusManager.blur();AV._clearActiveItem();AV._hasFocus=false;}}}}}},_onDocMouseDown:function(AU){var AT=this,AS=AT._rootMenu,Y=AU.target;if(!(AS.compareTo(Y)||AS.contains(Y))){AT._hideAllSubmenus(AS);if(m.webkit){AT._hasFocus=false;AT._clearActiveItem();}}}});D.namespace("Plugin");D.Plugin.NodeMenuNav=AL;},"3.2.0",{requires:["node","classnamemanager","node-focusmanager"]});	