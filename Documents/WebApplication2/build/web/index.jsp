<%-- 
    Document   : index
    Created on : Mar 18, 2011, 3:25:17 PM
    Author     : DINHDV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en" dir="ltr" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml" class="yui3-js-enabled">
    <head>
        <title>NewSchool Learning</title>
        <link href="css/base-min.css" type="text/css" rel="stylesheet">
        <link href="css/reset-min.css" type="text/css" rel="stylesheet">
        <link href="css/fonts-min.css" type="text/css" rel="stylesheet">
        <link href="css/grids-min.css" type="text/css" rel="stylesheet">
        <link href="css/skin.css" type="text/css" rel="stylesheet">
        <link href="css/admin.css" type="text/css" rel="stylesheet"/>
        <link href="css/block_blog_tags.css" type="text/css" rel="stylesheet"/>
        <link href="css/block_community.css" type="text/css" rel="stylesheet"/>
        <link href="css/block_course_list.css" type="text/css" rel="stylesheet"/>
        <link href="css/block_course_summary.css" type="text/css" rel="stylesheet"/>
        <link href="css/block_html.css" type="text/css" rel="stylesheet"/>
        <link href="css/block_messages.css" type="text/css" rel="stylesheet"/>
        <link href="css/block_myprofile.css" type="text/css" rel="stylesheet">
        <link href="css/block_navigation.css" type="text/css" rel="stylesheet">
        <link href="css/block_online_users.css" type="text/css" rel="stylesheet">
        <link href="css/block_quiz_results.css" type="text/css" rel="stylesheet">
        <link href="css/block_recent_activity.css" type="text/css" rel="stylesheet">
        <link href="css/block_search_forums.css" type="text/css" rel="stylesheet">
        <link href="css/block_settings.css" type="text/css" rel="stylesheet">
        <link href="css/block_tag_flickr.css" type="text/css" rel="stylesheet">
        <link href="css/block_tag_youtube.css" type="text/css" rel="stylesheet">
        <link href="css/block_tags.css" type="text/css" rel="stylesheet"/>
        <link href="css/blocks.css" type="text/css" rel="stylesheet"/>
        <link href="css/calendar.css" type="text/css" rel="stylesheet"/>
        <link href="css/workshopform_rubric.css" type="text/css" rel="stylesheet"/>
        <link href="css/workshopallocation_manual.css" type="text/css" rel="stylesheet"/>
        <link href="css/theme.css" type="text/css" rel="stylesheet"/>
        <link href="css/tabs.css" type="text/css" rel="stylesheet"/>
        <link href="css/tables.css" type="text/css" rel="stylesheet"/>
        <link href="css/skin.css" type="text/css" rel="stylesheet"/>
        <link href="css/report_profiling.css" type="text/css" rel="stylesheet"/>
        <link href="css/quiz_responses.css" type="text/css" rel="stylesheet"/>
        <link href="css/question.css" type="text/css" rel="stylesheet"/>
        <link href="css/popups.css" type="text/css" rel="stylesheet"/>
        <link href="css/pagelayout.css" type="text/css" rel="stylesheet"/>
        <link href="css/node-menunav.css" type="text/css" rel="stylesheet"/>
        <link href="css/mods.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_workshop.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_wiki.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_survey.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_scorm.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_resource.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_quiz.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_lesson.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_imscp.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_glossary.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_forum.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_feedback.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_data.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_choice.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_chat.css" type="text/css" rel="stylesheet"/>
        <link href="css/mod_assignment.css" type="text/css" rel="stylesheet"/>
        <link href="css/mnetservice_enrol.css" type="text/css" rel="stylesheet"/>
        <link href="css/gradereport_user.css" type="text/css" rel="stylesheet"/>
        <link href="css/gradereport_grader.css" type="text/css" rel="stylesheet"/>
        <link href="css/format_weeks.css" type="text/css" rel="stylesheet"/>
        <link href="css/format_topics.css" type="text/css" rel="stylesheet"/>
        <link href="css/course.css" type="text/css" rel="stylesheet"/>
        <link href="css/core.css" type="text/css" rel="stylesheet"/>
        <link href="css/sl.css" type="text/css" rel="stylesheet"/>
        <link href="css/blue.css" type="text/css" rel="stylesheet"/>
        <link href="css/green.css" type="text/css" rel="stylesheet"/>
        <link href="css/ie.css" type="text/css" rel="stylesheet"/>
        <link href="css/orange.css" type="text/css" rel="stylesheet"/>
        <script type="text/css" id="firstthemesheet">/** Required in order to fix style inclusion problems in IE with YUI **/</script>
        <script type="text/javascript" src="js/loader-min.js" ></script>
        <script type="text/javascript" src="js/connection-min.js"></script>
        <script type="text/javascript" src="js/event-resize-min.js" ></script>
        <script type="text/javascript" src="js/2Fblocks2Fdock.js" ></script>
        <script type="text/javascript" src="js/colourswitcher.js" ></script>
        <script type="text/javascript" src="js/jquery.js" ></script>
        <script type="text/javascript" src="js/styleswitch.js" ></script>
        <script id="firstthemesheet" type="text/css">/** Required in order to fix style inclusion problems in IE with YUI **/</script>
        <script type="text/javascript">
            //<![CDATA[
            var M = {}; M.yui = {}; var moodleConfigFn = function(me) {var p = me.path, b = me.name.replace(/^moodle-/,'').split('-', 3), n = b.pop();if (/(skin|core)/.test(n)) {n = b.pop();me.type = 'css';};me.path = b.join('-')+'/'+n+'/'+n+'.'+me.type;}; var galleryConfigFn = function(me) {var p = me.path,v=M.yui.galleryversion,f;if(/-(skin|core)/.test(me.name)) {me.type = 'css';p = p.replace(/-(skin|core)/, '').replace(/\.js/, '.css').split('/'), f = p.pop().replace(/(\-(min|debug))/, '');if (/-skin/.test(me.name)) {p.splice(p.length,0,v,'assets','skins','sam', f);} else {p.splice(p.length,0,v,'assets', f);};} else {p = p.split('/'), f = p.pop();p.splice(p.length,0,v, f);};me.path = p.join('/');};
            M.yui.loader =
                {"base":"http:\/\/www.newschoollearning.com\/moodle2\/lib\/yui\/3.2.0\/build\/","comboBase":"http:\/\/www.newschoollearning.com\/moodle2\/theme\/yui_combo.php?","combine":true,"filter":"","insertBefore":"firstthemesheet","modules":{"yui2-event":{"type":"js","requires":["yui2-yahoo"],"fullpath":"js/event-min.js"},"yui2-animation":{"type":"js","requires":["yui2-dom","yui2-event"],"fullpath":"js/animation-min.js"},"yui2-swfstore":{"type":"js","requires":["yui2-element","yui2-cookie","yui2-swf"],"fullpath":"js/swfstore-min.js"},"yui2-datatable":{"requires":["yui2-element","yui2-datasource"],"type":"js","optional":["yui2-calendar","yui2-dragdrop","yui2-paginator"],"fullpath":"js/datatable-min.js"},"yui2-swfdetect":{"type":"js","requires":["yui2-yahoo"],"fullpath":"js/swfdetect-min.js"},"yui2-menu":{"requires":["yui2-containercore"],"type":"js","fullpath":"js/menu-min.js"},"yui2-treeview":{"requires":["yui2-event","yui2-dom"],"type":"js","optional":["yui2-json","yui2-animation","yui2-calendar"],"fullpath":"js/treeview-min.js"},"yui2-get":{"type":"js","requires":["yui2-yahoo"],"fullpath":"js/get-min.js"},"yui2-progressbar":{"requires":["yui2-element"],"type":"js","optional":["yui2-animation"],"fullpath":"js/progressbar-min.js"},"yui2-uploader":{"type":"js","requires":["yui2-element"],"fullpath":"js/uploader-min.js"},"yui2-datasource":{"requires":["yui2-event"],"type":"js","optional":["yui2-connection"],"fullpath":"js/datasource-min.js"},"yui2-profiler":{"type":"js","requires":["yui2-yahoo"],"fullpath":"js/profiler-min.js"},"yui2-connection":{"supersedes":["yui2-connectioncore"],"requires":["yui2-event"],"type":"js","fullpath":"js/connection-min.js"},"yui2-json":{"type":"js","requires":["yui2-yahoo"],"fullpath":"js/json-min.js"},"yui2-datemath":{"type":"js","requires":["yui2-yahoo"],"fullpath":"js/datemath-min.js"},"yui2-calendar":{"supersedes":["yui2-datemath"],"requires":["yui2-event","yui2-dom"],"type":"js","fullpath":"js/calendar-min.js"},"yui2-simpleeditor":{"requires":["yui2-element"],"type":"js","optional":["yui2-containercore","yui2-menu","yui2-button","yui2-animation","yui2-dragdrop"],"pkg":"editor","fullpath":"js/simpleeditor-min.js"},"yui2-swf":{"supersedes":["yui2-swfdetect"],"requires":["yui2-element"],"type":"js","fullpath":"js/swf-min.js"},"yui2-event-simulate":{"type":"js","requires":["yui2-event"],"fullpath":"js/event-simulate-min.js"},"yui2-yuiloader-dom-event":{"supersedes":["yui2-yahoo","yui2-dom","yui2-event","yui2-get","yui2-yuiloader","yui2-yahoo-dom-event"],"rollup":5,"type":"js","fullpath":"js/yuiloader-dom-event.js"},"yui2-storage":{"requires":["yui2-yahoo","yui2-event","yui2-cookie"],"type":"js","optional":["yui2-swfstore"],"fullpath":"js/storage-min.js"},"yui2-container":{"supersedes":["yui2-containercore"],"requires":["yui2-dom","yui2-event"],"type":"js","optional":["yui2-dragdrop","yui2-animation","yui2-connection"],"fullpath":"js/container-min.js"},"yui2-profilerviewer":{"requires":["yui2-profiler","yui2-yuiloader","yui2-element"],"type":"js","fullpath":"js/profilerviewer-min.js"},"yui2-imagecropper":{"requires":["yui2-dragdrop","yui2-element","yui2-resize"],"type":"js","fullpath":"js/imagecropper-min.js"},"yui2-paginator":{"requires":["yui2-element"],"type":"js","fullpath":"js/paginator-min.js"},"yui2-tabview":{"requires":["yui2-element"],"type":"js","optional":["yui2-connection"],"fullpath":"js/tabview-min.js"},"yui2-layout":{"requires":["yui2-element"],"type":"js","optional":["yui2-animation","yui2-dragdrop","yui2-resize","yui2-selector"],"fullpath":"js/layout-min.js"},"yui2-imageloader":{"type":"js","requires":["yui2-event","yui2-dom"],"fullpath":"js/imageloader-min.js"},"yui2-containercore":{"requires":["yui2-dom","yui2-event"],"type":"js","pkg":"container","fullpath":"js/container_core-min.js"},"yui2-event-mouseenter":{"type":"js","requires":["yui2-dom","yui2-event"],"fullpath":"js/event-mouseenter-min.js"},"yui2-logger":{"requires":["yui2-event","yui2-dom"],"type":"js","optional":["yui2-dragdrop"],"fullpath":"js/logger-min.js"},"yui2-cookie":{"type":"js","requires":["yui2-yahoo"],"fullpath":"js/cookie-min.js"},"yui2-stylesheet":{"type":"js","requires":["yui2-yahoo"],"fullpath":"js/stylesheet-min.js"},"yui2-connectioncore":{"requires":["yui2-event"],"type":"js","pkg":"connection","fullpath":"js/connection_core-min.js"},"yui2-utilities":{"supersedes":["yui2-yahoo","yui2-event","yui2-dragdrop","yui2-animation","yui2-dom","yui2-connection","yui2-element","yui2-yahoo-dom-event","yui2-get","yui2-yuiloader","yui2-yuiloader-dom-event"],"rollup":8,"type":"js","fullpath":"js/utilities.js"},"yui2-dragdrop":{"type":"js","requires":["yui2-dom","yui2-event"],"fullpath":"js/dragdrop-min.js"},"yui2-colorpicker":{"requires":["yui2-slider","yui2-element"],"type":"js","optional":["yui2-animation"],"fullpath":"js/colorpicker-min.js"},"yui2-event-delegate":{"requires":["yui2-event"],"type":"js","optional":["yui2-selector"],"fullpath":"js/event-delegate-min.js"},"yui2-yuiloader":{"type":"js","supersedes":["yui2-yahoo","yui2-get"],"fullpath":"js/yuiloader-min.js"},"yui2-button":{"requires":["yui2-element"],"type":"js","optional":["yui2-menu"],"fullpath":"js/button-min.js"},"yui2-resize":{"requires":["yui2-dragdrop","yui2-element"],"type":"js","optional":["yui2-animation"],"fullpath":"js/resize-min.js"},"yui2-element":{"requires":["yui2-dom","yui2-event"],"type":"js","optional":["yui2-event-mouseenter","yui2-event-delegate"],"fullpath":"js/element-min.js"},"yui2-history":{"type":"js","requires":["yui2-event"],"fullpath":"js/history-min.js"},"yui2-yahoo":{"type":"js","fullpath":"js/yahoo-min.js"},"yui2-element-delegate":{"type":"js","requires":["yui2-element"],"fullpath":"js/element-delegate-min.js"},"yui2-charts":{"type":"js","requires":["yui2-element","yui2-json","yui2-datasource","yui2-swf"],"fullpath":"js/charts-min.js"},"yui2-slider":{"requires":["yui2-dragdrop"],"type":"js","optional":["yui2-animation"],"fullpath":"js/slider-min.js"},"yui2-selector":{"type":"js","requires":["yui2-yahoo","yui2-dom"],"fullpath":"js/selector-min.js"},"yui2-yuitest":{"requires":["yui2-logger"],"type":"js","optional":["yui2-event-simulate"],"fullpath":"js/yuitest-min.js"},"yui2-carousel":{"requires":["yui2-element"],"type":"js","optional":["yui2-animation"],"fullpath":"js/carousel-min.js"},"yui2-autocomplete":{"requires":["yui2-dom","yui2-event","yui2-datasource"],"type":"js","optional":["yui2-connection","yui2-animation"],"fullpath":"js/autocomplete-min.js"},"yui2-yahoo-dom-event":{"supersedes":["yui2-yahoo","yui2-event","yui2-dom"],"rollup":3,"type":"js","fullpath":"js/yahoo-dom-event.js"},"yui2-dom":{"type":"js","requires":["yui2-yahoo"],"fullpath":"js/dom-min.js"},"yui2-editor":{"supersedes":["yui2-simpleeditor"],"requires":["yui2-menu","yui2-element","yui2-button"],"type":"js","optional":["yui2-animation","yui2-dragdrop"],"fullpath":"js/editor-min.js"},"core_filepicker":{"name":"core_filepicker","fullpath":"js/2Frepository2Ffilepicker.js","requires":["base","node","node-event-simulate","json","async-queue","io","yui2-button","yui2-container","yui2-layout","yui2-menu","yui2-treeview","yui2-dragdrop","yui2-cookie"]},"core_dock":{"name":"core_dock","fullpath":"js/2Fblocks2Fdock.js","requires":["base","node","event-custom","event-mouseenter","event-resize"]}},"groups":{"moodle":{"name":"moodle","base":"http:\/\/www.newschoollearning.com\/moodle2\/theme\/yui_combo.php?moodle\/280\/","comboBase":"http:\/\/www.newschoollearning.com\/moodle2\/theme\/yui_combo.php?","combine":true,"filter":"","ext":false,"root":"moodle\/280\/","patterns":{"moodle-":{"group":"moodle","configFn":moodleConfigFn},"root":"moodle"}},"local":{"name":"gallery","base":"http:\/\/www.newschoollearning.com\/moodle2\/lib\/yui\/gallery\/","comboBase":"http:\/\/www.newschoollearning.com\/moodle2\/theme\/yui_combo.php?","combine":true,"filter":"","ext":false,"root":"gallery\/","patterns":{"gallery-":{"group":"gallery","configFn":galleryConfigFn},"root":"gallery"}}}};
            M.cfg = {"wwwroot":"http:\/\/www.newschoollearning.com\/moodle2","sesskey":"fy4d42dbeV","loadingicon":"button-min","themerev":-1,"theme":"splash"};
            //]]>
        </script>
        <script type="text/javascript" src="js/2Fli2Fjavascript-static.js" ></script>
        <!--        <script src="http://www.newschoollearning.com/moodle2/theme/javascript.php?theme=splash&amp;rev=-1&amp;type=head" type="text/javascript"></script>-->
    </head>
    <body class="course path-site gecko gecko19 dir-ltr lang-en yui-skin-sam yui3-skin-sam www-newschoollearning-com--moodle2 pagelayout-frontpage course-1 context-2 notloggedin splash-red jsenabled" id="page-site-index">
        <div class="skiplinks"><a href="#maincontent" class="skip">Skip to main content</a></div>
        <script type="text/javascript">
            //&lt;![CDATA[
            document.body.className += ' jsenabled';
            //]]&gt;
        </script>

        <div id="page">
            <div id="page-header">
                <div class="wrapper clearfix" id="page-header-wrapper">
                    <div id="headermenu">
                        <div id="userdetails_loggedout"><h1>Welcome, <a href="http://www.newschoollearning.com/moodle2/login/">Login here!</a></h1></div>                    <div class="clearer"></div>
                        <div id="colourswitcher">
                            <ul>
                                <li>
                                    <img alt="colour" src="images/colour.jpg"></li>
                                <li><a class="styleswitch colour-red" href="http://www.newschoollearning.com/moodle2/?splashcolour=red">
                                        <img alt="red" src="images/red-theme2.gif">
                                    </a>
                                </li>
                                <li><a class="styleswitch colour-green" href="http://www.newschoollearning.com/moodle2/?splashcolour=green">
                                        <img alt="green" src="images/green-theme2.gif"></a>
                                </li>
                                <li><a class="styleswitch colour-blue" href="http://www.newschoollearning.com/moodle2/?splashcolour=blue">
                                        <img alt="blue" src="images/blue-theme2.gif"></a></li>
                                <li><a class="styleswitch colour-orange" href="http://www.newschoollearning.com/moodle2/?splashcolour=orange">
                                        <img alt="orange" src="images/orange-theme2.gif"></a></li>
                            </ul>
                        </div>
                        <div class="langmenu">
                            <form id="single_select_f4d83186a45151" action="http://www.newschoollearning.com/moodle2/" method="get">
                                <div><label for="single_select4d83186a45920">
                                        <span class="accesshide ">Language</span>
                                    </label>
                                    <select name="lang" class="select menulang" id="single_select4d83186a45920">
                                        <option value="en" selected="selected">English (en)</option>
                                        <option value="it">Italiano (it)</option>
                                    </select>
                                    <noscript style="">&lt;div&gt;&lt;input type="submit" value="Go" /&gt;&lt;/div&gt;</noscript>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div id="logobox">
                        <a href="http://www.newschoollearning.com/moodle2/" class="nologoimage">NewSchool Learning</a>
                        <h4>Virtual Learning Center</h4>
                    </div>
                    <div class="clearer"></div>
                    <h4 class="headermain inside">NewSchool Learning</h4>
                    <!-- DROP DOWN MENU -->
                    <div class="clearer"></div>
                    <div id="dropdownmenu">
                        <div id="custommenu"><div class="yui3-menu yui3-menu-horizontal" id="custom_menu_1" role="menu">
                                <div class="yui3-menu-content" role="presentation">
                                    <ul role="presentation" class="first-of-type">
                                        <li role="presentation">
                                            <a href="http://moodle.org" title="Moodle community" class="yui3-menu-label" id="yui_3_2_0_1_1300437094748326" role="menuitem" aria-haspopup="true" tabindex="0">Moodle community</a>
                                            <div class="yui3-menu custom_menu_submenu yui3-menu-hidden" id="cm_submenu_1" role="menu" aria-labelledby="yui_3_2_0_1_1300437094748324" aria-hidden="true">
                                                <div class="yui3-menu-content" role="presentation"><ul role="presentation" class="first-of-type">
                                                        <li class="yui3-menuitem" role="presentation">
                                                            <a href="http://moodle.org/support" title="Moodle free support" class="yui3-menuitem-content" role="menuitem">Moodle free support</a>
                                                        </li>
                                                        <li role="presentation">
                                                            <a href="http://moodle.org/development" title="Moodle development" class="yui3-menu-label" id="yui_3_2_0_1_1300437094748331" role="menuitem" aria-haspopup="true">Moodle development</a>
                                                            <div class="yui3-menu custom_menu_submenu yui3-menu-hidden" id="cm_submenu_2" role="menu" aria-labelledby="yui_3_2_0_1_1300437094748329" aria-hidden="true">
                                                                <div class="yui3-menu-content" role="presentation">
                                                                    <ul role="presentation" class="first-of-type">
                                                                        <li class="yui3-menuitem" role="presentation">
                                                                            <a href="http://tracker.moodle.org" title="Moodle Tracker" class="yui3-menuitem-content" role="menuitem">Moodle Tracker</a>
                                                                        </li><li class="yui3-menuitem" role="presentation">
                                                                            <a href="http://docs.moodle.org" title="Moodle Docs" class="yui3-menuitem-content" role="menuitem">Moodle Docs</a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </li>
                                                        <li class="yui3-menuitem" role="presentation">
                                                            <a href="http://moodle.org/news" title="Moodle News" class="yui3-menuitem-content" role="menuitem">Moodle News</a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </li>
                                        <li role="presentation">
                                            <a href="#cm_submenu_3" title="Moodle company" class="yui3-menu-label" id="yui_3_2_0_1_1300437094748336" role="menuitem" aria-haspopup="true" tabindex="-1">Moodle company</a>
                                            <div class="yui3-menu custom_menu_submenu yui3-menu-hidden" id="cm_submenu_3" role="menu" aria-labelledby="yui_3_2_0_1_1300437094748334" aria-hidden="true">
                                                <div class="yui3-menu-content" role="presentation">
                                                    <ul role="presentation" class="first-of-type">
                                                        <li class="yui3-menuitem" role="presentation">
                                                            <a href="http://moodle.com/hosting" title="Moodle commercial hosting" class="yui3-menuitem-content" role="menuitem">Moodle commercial hosting</a>
                                                        </li>
                                                        <li class="yui3-menuitem" role="presentation">
                                                            <a href="http://moodle.com/support" title="Moodle commercial support" class="yui3-menuitem-content" role="menuitem">Moodle commercial support</a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="yui3-menuitem" role="presentation">
                                            <a href="http://moodle.org" title="Moodle home" class="yui3-menuitem-content" role="menuitem" tabindex="-1" id="yui_3_2_0_1_1300437094748370">Moodle home</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="navbar">
                            <div class="wrapper clearfix">
                                <div class="breadcrumb"></div>
                                <div class="navbutton"> </div>
                            </div>
                        </div>
                    </div>
                    <!-- END DROP DOWN MENU -->
                </div>
            </div>
            <!-- END OF HEADER -->
            <!-- START OF CONTENT -->
            <div id="page-content">
                <div id="region-main-box">
                    <div id="region-post-box">
                        <div id="region-main-wrap">
                            <div id="region-main">
                                <div class="region-content">
                                    <span id="maincontent"></span>
                                    <div class="box generalbox sitetopic">
                                        <div class="no-overflow">
                                            <h2>Welcome to our 2.0 Test Site</h2>
                                            <p>We're still working on creating a true demo experience here for the Moodle Theme 2.0 Contest. We still need to create a course and different demo accounts. While we're getting ourselves situated, feel free to login using one of these accounts to play around:</p>
                                            <h3>Teacher</h3>
                                            <p>Username: teacher</p>
                                            <p>Password: Teacher1;</p>
                                            <h3>Student</h3>
                                            <p>Username: student</p>
                                            <p>Password: Student1;</p>
                                            <p>In the meantime, we'll just keep working. Enjoy.</p>
                                        </div>
                                    </div>
                                    <a class="skip-block" href="#skipavailablecourses">Skip available courses</a>
                                    <h2 class="headingblock header">Available courses</h2>
                                    <ul class="unlist">
                                        <li>
                                            <div class="coursebox clearfix">
                                                <div class="info">
                                                    <h3 class="name">
                                                        <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=5" title="Click to enter this course">Moodle Features Demo</a>
                                                    </h3>
                                                    <ul class="teachers">
                                                        <li>Teacher: <a href="http://www.newschoollearning.com/moodle2/user/view.php?id=3&amp;course=1">Teacher Demo</a></li>
                                                    </ul>
                                                </div>
                                                <div class="summary">
                                                    <div class="no-overflow">This course outlines Moodle's features by providing examples of activities and resources.</div>
                                                    <div class="enrolmenticons">
                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=withoutpassword&amp;component=enrol_guest" title="Guest access" class="smallicon" alt="Guest access">
                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=withoutkey&amp;component=enrol_self" title="Self enrolment" class="smallicon" alt="Self enrolment">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="coursebox clearfix">
                                                <div class="info">
                                                    <h3 class="name">
                                                        <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=4" title="Click to enter this course">PHP/MySQL Course</a>
                                                    </h3>
                                                    <ul class="teachers">
                                                        <li>Teacher: <a href="http://www.newschoollearning.com/moodle2/user/view.php?id=3&amp;course=1">Teacher Demo</a></li>
                                                    </ul>
                                                </div>
                                                <div class="summary">
                                                    <div class="enrolmenticons">
                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=withoutpassword&amp;component=enrol_guest" title="Guest access" class="smallicon" alt="Guest access">
                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=withoutkey&amp;component=enrol_self" title="Self enrolment" class="smallicon" alt="Self enrolment">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="coursebox clearfix">
                                                <div class="info">
                                                    <h3 class="name">
                                                        <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=3" title="Click to enter this course">Test Course 2</a>
                                                    </h3>
                                                    <ul class="teachers">
                                                        <li>Teacher: <a href="http://www.newschoollearning.com/moodle2/user/view.php?id=3&amp;course=1">Teacher Demo</a></li>
                                                    </ul>
                                                </div>
                                                <div class="summary">
                                                    <div class="no-overflow"><p>Phosfluorescently e-enable multifunctional systems through installed base partnerships. Synergistically unleash competitive infrastructures via best-of-breed functionalities. Assertively fashion real-time best practices without fully researched scenarios.</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="coursebox clearfix">
                                                <div class="info">
                                                    <h3 class="name">
                                                        <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=2" title="Click to enter this course">Test Course 1</a>
                                                    </h3>
                                                    <ul class="teachers">
                                                        <li>Teacher: <a href="http://www.newschoollearning.com/moodle2/user/view.php?id=3&amp;course=1">Teacher Demo</a>
                                                        </li>
                                                    </ul>
                                                </div>
                                                <div class="summary">
                                                    <div class="no-overflow">
                                                        <p>Professionally formulate backend manufactured products after resource-leveling architectures. Rapidiously maximize distributed networks without wireless services. Collaboratively integrate technically sound outsourcing through accurate models.</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                    <span id="skipavailablecourses" class="skip-block-to"></span>
                                    <br><a class="skip-block" href="#skipcourses">Skip courses</a>
                                    <h2 class="headingblock header">Courses</h2>
                                    <div id="course_category_tree4d83186a4c0ae" class="course_category_tree">
                                        <div class="category with_children">
                                            <div class="category_label">
                                                <a href="http://www.newschoollearning.com/moodle2/course/category.php?id=1" class="category_link">Miscellaneous</a>
                                            </div>
                                            <div class="courses">
                                                <div class="course odd">
                                                    <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=5" class="course_link">Moodle Features Demo</a>
                                                    <div class="course_info clearfix">
                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=withoutpassword&amp;component=enrol_guest" title="Guest access" class="smallicon" alt="Guest access">
                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=withoutkey&amp;component=enrol_self" title="Self enrolment" class="smallicon" alt="Self enrolment">
                                                        <a href="http://www.newschoollearning.com/moodle2/course/info.php?id=5" title="Summary">
                                                            <img alt="Summary" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=i%2Finfo">
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="course even">
                                                    <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=4" class="course_link">PHP/MySQL Course</a>
                                                    <div class="course_info clearfix">
                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=withoutpassword&amp;component=enrol_guest" title="Guest access" class="smallicon" alt="Guest access">
                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=withoutkey&amp;component=enrol_self" title="Self enrolment" class="smallicon" alt="Self enrolment">
                                                    </div>
                                                </div>
                                                <div class="course odd">
                                                    <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=3" class="course_link">Test Course 2</a>
                                                    <div class="course_info clearfix">
                                                        <a href="http://www.newschoollearning.com/moodle2/course/info.php?id=3" title="Summary">
                                                            <img alt="Summary" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=i%2Finfo"></a>
                                                    </div>
                                                </div><div class="course even">
                                                    <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=2" class="course_link">Test Course 1</a>
                                                    <div class="course_info clearfix">
                                                        <a href="http://www.newschoollearning.com/moodle2/course/info.php?id=2" title="Summary">
                                                            <img alt="Summary" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=i%2Finfo"></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="controls">
                                            <div class="addtoall expandall">Collapse all</div>
                                            <div class="removefromall collapseall">Expand all</div>

                                        </div>

                                    </div>
                                    <form method="get" action="http://www.newschoollearning.com/moodle2/course/search.php" id="coursesearch">
                                        <fieldset class="coursesearchbox invisiblefieldset">
                                            <label for="shortsearchbox">Search courses: </label>
                                            <input type="text" value="" alt="Search courses" name="search" size="12" id="shortsearchbox">
                                            <input type="submit" value="Go">
                                        </fieldset>
                                    </form>
                                    <span id="skipcourses" class="skip-block-to"></span>
                                    <br>
                                    <a class="skip-block" href="#skipsitenews">Skip site news</a>
                                    <h2 class="headingblock header">Site news</h2>
                                    <a id="p2"></a>
                                    <div class="forumpost clearfix firstpost starter">
                                        <div class="row header clearfix">
                                            <div class="left picture">
                                                <a href="http://www.newschoollearning.com/moodle2/user/profile.php?id=2">
                                                    <img height="35" width="35" class="userpicture defaultuserpic" title="Picture of Admin Users Longnames" alt="Picture of Admin Users Longnames" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=u%2Ff2">
                                                </a>
                                            </div>
                                            <div class="topic firstpost starter">
                                                <div class="subject">Another test</div>
                                                <div class="author">by
                                                    <a href="http://www.newschoollearning.com/moodle2/user/view.php?id=2&amp;course=1">Admin Users Longnames</a> - Tuesday, 1 March 2011, 09:31 AM</div>
                                            </div>
                                        </div>
                                        <div class="row maincontent clearfix">
                                            <div class="left">
                                                <div class="grouppictures">&nbsp;</div>

                                            </div>
                                            <div class="no-overflow">
                                                <div class="content">
                                                    <div class="posting fullpost">
                                                        <p>
                                                            <span style="font-family: Helvetica,Arial,sans-serif;">Professionally formulate backend manufactured products after resource-leveling architectures. Rapidiously maximize distributed networks without wireless services. Collaboratively integrate technically sound outsourcing through accurate models.</span>
                                                        </p>
                                                        <blockquote>
                                                            <span style="font-family: Helvetica,Arial,sans-serif;">Phosfluorescently e-enable multifunctional systems through installed base partnerships. Synergistically unleash competitive infrastructures via best-of-breed functionalities. Assertively fashion real-time best practices without fully researched scenarios.</span>
                                                        </blockquote>
                                                        <p><span style="font-family: Helvetica,Arial,sans-serif;">Continually embrace user friendly leadership skills for functional alignments. Intrinsicly monetize principle-centered supply chains for alternative meta-services. Appropriately incubate progressive web services rather than scalable benefits.</span><br><span style="font-family: Helvetica,Arial,sans-serif;">Uniquely cultivate cross-media markets via revolutionary web services. Seamlessly envisioneer business portals for fully researched results. Uniquely procrastinate multidisciplinary data before cross functional e-markets.</span></p>
                                                        <p><span style="font-family: Helvetica,Arial,sans-serif;">(<span class="edited">Edited by <a href="http://www.newschoollearning.com/moodle2/user/view.php?id=5&amp;course=1">Jane Erlandson</a> - original submission Monday, 12 July 2010, 01:45 AM</span>)</span></p>
                                                        <p>(<span class="edited">Edited by <a href="http://www.newschoollearning.com/moodle2/user/view.php?id=5&amp;course=1">Jane Erlandson</a> - original submission Monday, 24 January 2011, 12:57 PM</span>)</p>
                                                        <div class="attachedimages">

                                                        </div>

                                                    </div>

                                                </div>

                                            </div>

                                        </div>
                                        <div class="row side">
                                            <div class="left">&nbsp;</div>
                                            <div class="options clearfix">
                                                <div class="commands"></div>

                                            </div>

                                        </div>

                                    </div>
                                    <a id="p1"></a>
                                    <div class="forumpost clearfix firstpost starter">
                                        <div class="row header clearfix">
                                            <div class="left picture">
                                                <a href="http://www.newschoollearning.com/moodle2/user/profile.php?id=2">
                                                    <img height="35" width="35" class="userpicture defaultuserpic" title="Picture of Admin Users Longnames" alt="Picture of Admin Users Longnames" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=u%2Ff2"></a>
                                            </div>
                                            <div class="topic firstpost starter">
                                                <div class="subject">Test some news</div>
                                                <div class="author">by <a href="http://www.newschoollearning.com/moodle2/user/view.php?id=2&amp;course=1">Admin Users Longnames</a> - Monday, 12 July 2010, 01:44 AM</div>
                                            </div>
                                        </div>
                                        <div class="row maincontent clearfix">
                                            <div class="left">
                                                <div class="grouppictures">&nbsp;</div>

                                            </div>
                                            <div class="no-overflow">
                                                <div class="content">
                                                    <div class="posting fullpost"><p>Professionally pontificate fully tested deliverables via prospective niches. Collaboratively generate resource sucking deliverables for robust supply chains. Holisticly underwhelm cutting-edge products for virtual technologies.</p>
                                                        <p>Monotonectally target superior data through resource sucking manufactured products. Proactively customize backend scenarios and process-centric relationships.</p>
                                                        <ul><li>Interactively evisculate intermandated resources and bricks-and-clicks relationships. </li>
                                                            <li>Energistically aggregate dynamic portals without cross-platform innovation. </li>
                                                            <li>Rapidiously fashion customer directed infrastructures through an expanded array of markets. </li>
                                                            <li>Authoritatively synergize global e-tailers rather than extensible imperatives. </li>
                                                        </ul><p>Here is an ordered list.</p>
                                                        <ol><li>Holisticly mesh cross-platform growth strategies via prospective customer service. Holisticly facilitate client-focused methodologies before robust mindshare. </li>
                                                            <li>Continually extend efficient content with reliable vortals. </li>
                                                        </ol><p>Authoritatively engage long-term high-impact human capital vis-a-vis user friendly collaboration and idea-sharing. Interactively exploit diverse technology rather than scalable schemas.</p>
                                                        <div class="attachedimages"></div>

                                                    </div>

                                                </div>

                                            </div>

                                        </div>
                                        <div class="row side">
                                            <div class="left">&nbsp;</div>
                                            <div class="options clearfix">
                                                <div class="commands"></div>

                                            </div>

                                        </div>

                                    </div>
                                    <span id="skipsitenews" class="skip-block-to"></span>
                                    <br>
                                </div>
                            </div>
                        </div>

                        <div class="block-region" id="region-pre">
                            <div class="region-content">
                                <a class="skip-block" href="#sb-1">Skip Latest news</a>
                                <div class="block_news_items  block" id="inst58">
                                    <div class="header">
                                        <div class="title" id="yui_3_2_0_1_1300437094748429">
                                            <div class="block_action">
                                                <img title="Hide Latest news block" alt="Hide Latest news block" class="block-hider-hide" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_minus">
                                                <img title="Show Latest news block" alt="Show Latest news block" class="block-hider-show" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_plus">
                                                <input type="image" title="Move this to the dock" alt="Move this to the dock" class="moveto customcommand requiresjs" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/block_to_dock">
                                            </div>
                                            <h2>Latest news</h2>
                                            <div class="commands"></div>

                                        </div>

                                    </div>
                                    <div class="content">
                                        <ul class="unlist">
                                            <li class="post">
                                                <div class="head clearfix">
                                                    <div class="date">1 Mar, 09:31</div>
                                                    <div class="name">Admin Users Longnames</div>

                                                </div>
                                                <div class="info">Another test <a href="http://www.newschoollearning.com/moodle2/mod/forum/discuss.php?d=2">more...</a>
                                                </div>
                                            </li>
                                            <li class="post">
                                                <div class="head clearfix">
                                                    <div class="date">12 Jul, 01:44</div>
                                                    <div class="name">Admin Users Longnames</div>

                                                </div>
                                                <div class="info">Test some news <a href="http://www.newschoollearning.com/moodle2/mod/forum/discuss.php?d=1">more...</a>
                                                </div>
                                            </li>
                                        </ul>
                                        <div class="footer"><a href="http://www.newschoollearning.com/moodle2/mod/forum/view.php?f=1">Older topics</a> ...</div>
                                    </div>
                                </div>
                                <span class="skip-block-to" id="sb-1"></span>
                                <a class="skip-block" href="#sb-2">Skip Main menu</a>
                                <div class="block_site_main_menu  block list_block" id="inst1">
                                    <div class="header">
                                        <div class="title" id="yui_3_2_0_1_1300437094748445">
                                            <div class="block_action">
                                                <img title="Hide Main menu block" alt="Hide Main menu block" class="block-hider-hide" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_minus">
                                                <img title="Show Main menu block" alt="Show Main menu block" class="block-hider-show" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_plus">
                                                <input type="image" title="Move this to the dock" alt="Move this to the dock" class="moveto customcommand requiresjs" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/block_to_dock">
                                            </div>
                                            <h2>Main menu</h2>
                                            <div class="commands"></div>

                                        </div>

                                    </div>
                                    <div class="content">
                                        <ul class="unlist">
                                            <li class="r0">
                                                <div class="column c1">
                                                    <a href="http://www.newschoollearning.com/moodle2/mod/forum/view.php?id=1" title="Forums">
                                                        <img alt="" class="icon" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=icon&amp;component=forum">&nbsp;Site news</a>
                                                </div>
                                            </li>
                                            <li class="r1">
                                                <div class="column c1">
                                                    <a href="http://www.newschoollearning.com/moodle2/mod/url/view.php?id=2" onclick="window.location.href ='http://www.newschoollearning.com/moodle2/mod/url/view.php?id=2&amp;redirect=1';return false;" title="URLs">
                                                        <img alt="" class="icon" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=f%2Fweb">&nbsp;NewSchool Learning</a>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <span class="skip-block-to" id="sb-2"></span>
                                <a class="skip-block" href="#sb-3">Skip Tags</a>
                                <div class="block_tags  block" id="inst14">
                                    <div class="header">
                                        <div class="title" id="yui_3_2_0_1_1300437094748461">
                                            <div class="block_action">
                                                <img title="Hide Tags block" alt="Hide Tags block" class="block-hider-hide" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_minus">
                                                <img title="Show Tags block" alt="Show Tags block" class="block-hider-show" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_plus">
                                                <input type="image" title="Move this to the dock" alt="Move this to the dock" class="moveto customcommand requiresjs" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/block_to_dock">
                                            </div>
                                            <h2>Tags</h2>
                                            <div class="commands"></div>

                                        </div>

                                    </div>
                                    <div class="content">
                                        <ul class="tag_cloud inline-list">
                                            <li>
                                                <a title="Entries: 2" class="default s20" href="http://www.newschoollearning.com/moodle2/tag/index.php?tag=editor">editor</a>
                                            </li>
                                            <li>
                                                <a title="Entries: 2" class="default s20" href="http://www.newschoollearning.com/moodle2/tag/index.php?tag=moodle">Moodle</a>
                                            </li>
                                            <li>
                                                <a title="Entries: 1" class="default s10" href="http://www.newschoollearning.com/moodle2/tag/index.php?tag=moodle2.0">Moodle2.0</a>
                                            </li>
                                            <li><a title="Entries: 1" class="default s10" href="http://www.newschoollearning.com/moodle2/tag/index.php?tag=rich%20text">rich text</a></li>
                                            <li>
                                                <a title="Entries: 2" class="default s20" href="http://www.newschoollearning.com/moodle2/tag/index.php?tag=test">test</a>
                                            </li>
                                            <li>
                                                <a title="Entries: 1" class="default s10" href="http://www.newschoollearning.com/moodle2/tag/index.php?tag=training">training</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <span class="skip-block-to" id="sb-3"></span>
                                <a class="skip-block" href="#sb-4">Skip Online users</a>
                                <div class="block_online_users  block" id="inst15">
                                    <div class="header">
                                        <div class="title" id="yui_3_2_0_1_1300437094748477">
                                            <div class="block_action">
                                                <img title="Hide Online users block" alt="Hide Online users block" class="block-hider-hide" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_minus">
                                                <img title="Show Online users block" alt="Show Online users block" class="block-hider-show" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_plus">
                                                <input type="image" title="Move this to the dock" alt="Move this to the dock" class="moveto customcommand requiresjs" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/block_to_dock">
                                            </div>
                                            <h2>Online users</h2>
                                            <div class="commands"></div>

                                        </div>

                                    </div>
                                    <div class="content">
                                        <div class="info">(last 5 minutes)</div>
                                        <div class="info">None</div>

                                    </div>

                                </div>
                                <span class="skip-block-to" id="sb-4"></span>
                            </div>
                        </div>

                        <div class="block-region" id="region-post">
                            <div class="region-content">
                                <a class="skip-block" href="#sb-5">Skip Login</a>
                                <div class="block_login  block" id="inst13">
                                    <div class="header">
                                        <div class="title" id="yui_3_2_0_1_1300437094748493">
                                            <div class="block_action">
                                                <img title="Hide Login block" alt="Hide Login block" class="block-hider-hide" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_minus">
                                                <img title="Show Login block" alt="Show Login block" class="block-hider-show" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_plus">
                                                <input type="image" title="Move this to the dock" alt="Move this to the dock" class="moveto customcommand requiresjs" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/block_to_dock">
                                            </div>
                                            <h2>Login</h2>
                                            <div class="commands"></div>

                                        </div>

                                    </div>
                                    <div class="content">
                                        <form action="http://www.newschoollearning.com/moodle2/login/index.php" method="post" id="login" class="loginform">
                                            <div class="c1 fld username">
                                                <label for="login_username">Username</label>
                                                <input type="text" value="student" id="login_username" name="username">
                                            </div>
                                            <div class="c1 fld password">
                                                <label for="login_password">Password</label>
                                                <input type="password" value="" id="login_password" name="password">
                                            </div>
                                            <div class="c1 btn">
                                                <input type="submit" value="Login">
                                            </div>
                                        </form>
                                        <div class="footer">
                                            <div>
                                                <a href="http://www.newschoollearning.com/moodle2/login/forgot_password.php">Lost password?</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <span class="skip-block-to" id="sb-5"></span>
                                <a class="skip-block" href="#sb-6">Skip Navigation</a>
                                <div class="block_navigation  block" id="inst4">
                                    <div class="header">
                                        <div class="title" id="yui_3_2_0_1_1300437094748509">
                                            <div class="block_action">
                                                <img title="Hide Navigation block" alt="Hide Navigation block" class="block-hider-hide" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_minus">
                                                <img title="Show Navigation block" alt="Show Navigation block" class="block-hider-show" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_plus">
                                                <input type="image" title="Move this to the dock" alt="Move this to the dock" class="moveto customcommand requiresjs" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/block_to_dock">
                                            </div>
                                            <h2>Navigation</h2>
                                            <div class="commands"></div>

                                        </div>

                                    </div>
                                    <div class="content">
                                        <ul class="block_tree list">
                                            <li class="type_unknown depth_1 contains_branch">
                                                <p class="tree_item branch navigation_node">
                                                    <a href="http://www.newschoollearning.com/moodle2/" title="Home">Home</a>
                                                </p>
                                                <ul>
                                                    <li class="type_system depth_2 collapsed contains_branch">
                                                        <p class="tree_item branch canexpand">
                                                            <span>Courses</span>
                                                        </p>
                                                        <ul>
                                                            <li class="type_course depth_3 collapsed item_with_icon">
                                                                <p id="expandable_branch_1" class="tree_item leaf hasicon">
                                                                    <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=5" title="Moodle Features Demo">
                                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=i%2Fnavigationitem" title="moodle" class="smallicon navicon" alt="moodle">Features Demo</a>
                                                                </p>
                                                            </li>
                                                            <li class="type_course depth_3 collapsed item_with_icon">
                                                                <p id="expandable_branch_2" class="tree_item leaf hasicon">
                                                                    <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=4" title="PHP/MySQL Course">
                                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=i%2Fnavigationitem" title="moodle" class="smallicon navicon" alt="moodle">PHP/MySQL</a>
                                                                </p>
                                                            </li>
                                                            <li class="type_course depth_3 collapsed item_with_icon">
                                                                <p id="expandable_branch_3" class="tree_item leaf hasicon">
                                                                    <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=3" title="Test Course 2">
                                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=i%2Fnavigationitem" title="moodle" class="smallicon navicon" alt="moodle">Test Course 2</a>
                                                                </p>
                                                            </li>
                                                            <li class="type_course depth_3 collapsed item_with_icon">
                                                                <p id="expandable_branch_4" class="tree_item leaf hasicon">
                                                                    <a href="http://www.newschoollearning.com/moodle2/course/view.php?id=2" title="Test Course 1">
                                                                        <img src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=i%2Fnavigationitem" title="moodle" class="smallicon navicon" alt="moodle">Test Course 1</a>
                                                                </p>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <span class="skip-block-to" id="sb-6"></span>
                                <a class="skip-block" href="#sb-7">Skip Calendar</a>
                                <div class="block_calendar_month  block" id="inst3">
                                    <div class="header">
                                        <div class="title" id="yui_3_2_0_1_1300437094748525">
                                            <div class="block_action">
                                                <img title="Hide Calendar block" alt="Hide Calendar block" class="block-hider-hide" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_minus">
                                                <img title="Show Calendar block" alt="Show Calendar block" class="block-hider-show" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_plus">
                                                <input type="image" title="Move this to the dock" alt="Move this to the dock" class="moveto customcommand requiresjs" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/block_to_dock">
                                            </div>
                                            <h2>Calendar</h2>
                                            <div class="commands"></div>

                                        </div>

                                    </div>
                                    <div class="content">
                                        <div class="calendar-controls">
                                            <a title="Previous month" href="index.php?cal_m=2&amp;cal_y=2011" class="arrow_link previous">
                                                <span class="arrow ">◄</span>
                                                <span class="accesshide ">&nbsp;<span class="arrow_text">Previous month</span>

                                                </span>
                                            </a>
                                            <span class="hide"> | </span>
                                            <span class="current">
                                                <a href="http://www.newschoollearning.com/moodle2/calendar/view.php?view=month&amp;cal_d=1&amp;cal_m=3&amp;cal_y=2011&amp;course=1" title="This month">March 2011</a>
                                            </span>
                                            <span class="hide"> | </span>
                                            <a title="Next month" href="index.php?cal_m=4&amp;cal_y=2011" class="arrow_link next">
                                                <span class="accesshide ">
                                                    <span class="arrow_text">Next month</span>&nbsp;</span>
                                                <span class="arrow ">►</span>
                                            </a>
                                            <span class="clearer"><!-- -->
                                            </span>
                                        </div>
                                        <table summary="Data table, March 2011 Calendar" class="minicalendar calendartable">
                                            <tbody>
                                                <tr class="weekdays">
                                                    <th scope="col">
                                                        <abbr title="Sunday">Sun</abbr>
                                                    </th>
                                                    <th scope="col"><abbr title="Monday">Mon</abbr></th>
                                                    <th scope="col"><abbr title="Tuesday">Tue</abbr></th>
                                                    <th scope="col"><abbr title="Wednesday">Wed</abbr></th>
                                                    <th scope="col"><abbr title="Thursday">Thu</abbr></th>
                                                    <th scope="col"><abbr title="Friday">Fri</abbr></th>
                                                    <th scope="col"><abbr title="Saturday">Sat</abbr></th>
                                                </tr><tr><td class="dayblank">&nbsp;</td>
                                                    <td class="dayblank">&nbsp;</td>
                                                    <td class="day">1</td>
                                                    <td class="day">2</td>
                                                    <td class="day">3</td>
                                                    <td class="day">4</td>
                                                    <td class="weekend day">5</td>
                                                </tr><tr><td class="weekend day">6</td>
                                                    <td class="day">7</td>
                                                    <td class="day">8</td>
                                                    <td class="day">9</td>
                                                    <td class="day">10</td>
                                                    <td class="day">11</td>
                                                    <td class="weekend day">12</td>
                                                </tr><tr><td class="weekend day">13</td>
                                                    <td class="day">14</td>
                                                    <td class="day">15</td>
                                                    <td class="day">16</td>
                                                    <td class="day">17</td>
                                                    <td class="day today eventnone" id="yui_3_2_0_1_1300437094748278">
                                                        <span class="accesshide ">Today Friday, 18 March </span>
                                                        <a id="calendar_tooltip_1" href="#">18</a>
                                                    </td>
                                                    <td class="weekend day">19</td>
                                                </tr>
                                                <tr><td class="weekend day">20</td>
                                                    <td class="day">21</td>
                                                    <td class="day">22</td>
                                                    <td class="day">23</td>
                                                    <td class="day">24</td>
                                                    <td class="day">25</td>
                                                    <td class="weekend day">26</td>
                                                </tr>
                                                <tr><td class="weekend day">27</td>
                                                    <td class="day">28</td>
                                                    <td class="day">29</td>
                                                    <td class="day">30</td>
                                                    <td class="day">31</td>
                                                    <td class="dayblank">&nbsp;</td>
                                                    <td class="dayblank">&nbsp;</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <span class="skip-block-to" id="sb-7"></span>
                                <div class="block_course_summary  block" id="inst89">
                                    <div class="content">
                                        <div class="block_action notitle"></div>
                                        <div class="no-overflow">
                                            <div class="text_to_html">
                                                <p>Energistically customize intuitive products and accurate mindshare. Professionally architect premier "outside the box" thinking via front-end meta-services. Continually restore ubiquitous experiences rather than turnkey e-commerce.</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <a class="skip-block" href="#sb-10">Skip HTML BLock</a>
                                <div class="block_html  block" id="inst90">
                                    <div class="header">
                                        <div class="title" id="yui_3_2_0_1_1300437094748541">
                                            <div class="block_action">
                                                <img title="Hide HTML BLock block" alt="Hide HTML BLock block" class="block-hider-hide" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_minus">
                                                <img title="Show HTML BLock block" alt="Show HTML BLock block" class="block-hider-show" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/switch_plus">
                                                <input type="image" title="Move this to the dock" alt="Move this to the dock" class="moveto customcommand requiresjs" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/block_to_dock">
                                            </div>
                                            <h2>HTML BLock</h2>
                                            <div class="commands"></div>

                                        </div>

                                    </div>
                                    <div class="content">
                                        <div class="no-overflow">
                                            <p>This is an html block</p>
                                        </div>
                                    </div>
                                </div>
                                <span class="skip-block-to" id="sb-10"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- END OF CONTENT -->
            <div class="clearfix"></div>
            <!-- END OF #Page -->
        </div>
        <!-- START OF FOOTER -->
        <div id="page-footer">
            <div id="footer-wrapper">
                <div id="footnote"><p>This is my footnote. There are several like it, but this one is mine.</p></div>
                <p class="helplink"></p>
                <div class="logininfo">You are not logged in. (<a href="http://www.newschoollearning.com/moodle2/login/index.php">Login</a>)</div>
                <div class="sitelink"><a href="http://moodle.org/" title="Moodle">
                        <img alt="moodlelogo" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=moodlelogo" style="width: 100px; height: 30px;"></a>
                </div>
            </div>
        </div>
        <script src="http://www.newschoollearning.com/moodle2/theme/javascript.php?theme=splash&amp;rev=-1&amp;type=footer" type="text/javascript"></script>
        <script type="text/javascript">
            //&lt;![CDATA[
            M.str = {"repository":{"add":"Add","back":"&amp;laquo; Back","close":"Close","cleancache":"Clean my cache files","copying":"Copying","date":"Date","downloadsucc":"The file has been downloaded successfully","emptylist":"Empty list","error":"An unknown error occurred!","federatedsearch":"Federated search","filenotnull":"You must select a file to upload.","getfile":"Select this file","iconview":"View as icons","invalidjson":"Invalid JSON string","linkexternal":"Link external","listview":"View as list","loading":"Loading...","login":"Login","logout":"Logout","noenter":"Nothing entered","noresult":"No search result","manageurl":"Manage","popup":"Click \"Login\" button to login","preview":"Preview","refresh":"Refresh","save":"Save","saveas":"Save as","saved":"Saved","saving":"Saving","search":"Search","searching":"Search in","size":"Size","submit":"Submit","sync":"Sync","title":"Choose a file...","upload":"Upload this file","uploading":"Uploading...","xhtmlerror":"You are probably using XHTML strict header, some YUI Component doesn't work in this mode, please turn it off in moodle","chooselicense":"Choose license","author":"Author","norepositoriesavailable":"Sorry, none of your current repositories can return files in the required format.","norepositoriesexternalavailable":"Sorry, none of your current repositories can return external files.","nofilesattached":"No files attached","filepicker":"File picker","nofilesavailable":"No files available"},"moodle":{"cancel":"Cancel","help":"Help","ok":"OK","error":"Error","info":"Information","yes":"Yes"},"quiz":{"xhtml":"XHTML format"},"block":{"addtodock":"Move this to the dock","undockitem":"Undock this item","undockall":"Undock all"},"langconfig":{"thisdirectionvertical":"btt"},"admin":{"confirmation":"Confirmation"}};
            //]]&gt;
        </script>
        <script type="text/javascript">
            //&lt;![CDATA[
            var navtreeexpansions4 = [{"id":"expandable_branch_1","key":"5","type":20},{"id":"expandable_branch_2","key":"4","type":20},{"id":"expandable_branch_3","key":"3","type":20},{"id":"expandable_branch_4","key":"2","type":20}];
            //]]&gt;
        </script>
        <script type="text/javascript">
            //&lt;![CDATA[
            YUI(M.yui.loader).use('node', function(Y) {
                setTimeout("fix_column_widths()", 20);
                Y.use('core_dock', function(Y) { M.core_dock.init_genericblock(Y, "58"); });
                Y.use('core_dock', function(Y) { M.core_dock.init_genericblock(Y, "1"); });
                Y.use('core_dock', function(Y) { M.core_dock.init_genericblock(Y, "14"); });
                Y.use('core_dock', function(Y) { M.core_dock.init_genericblock(Y, "15"); });
                Y.use('core_dock', function(Y) { M.core_dock.init_genericblock(Y, "13"); });
                M.yui.galleryversion="2010.04.08-12-35";Y.use("core_dock","moodle-block_navigation-navigation",function() {M.block_navigation.init_add_tree({"id":"4","instance":"4","candock":true,"courselimit":"20"});
                })
                M.yui.galleryversion="2010.04.08-12-35";Y.use("moodle-calendar-eventmanager",function() {M.core_calendar.add_event({"eventId":"calendar_tooltip_1","title":"Today Friday, 18 March","content":"No events"});
                })
                Y.use('core_dock', function(Y) { M.core_dock.init_genericblock(Y, "3"); });
                Y.use('core_dock', function(Y) { M.core_dock.init_genericblock(Y, "90"); });
                M.core_custom_menu.init(Y, "custom_menu_1");
                M.yui.galleryversion="2010.04.08-12-35";Y.use("moodle-theme_splash-colourswitcher",function() {M.theme_splash.initColourSwitcher({"div":"#colourswitcher"});
                })
                M.util.init_select_autosubmit(Y, "single_select_f4d83186a45151", "single_select4d83186a45920", false);
                M.util.init_block_hider(Y, {"id":"inst58","title":"Latest news","preference":"block58hidden","tooltipVisible":"Hide Latest news block","tooltipHidden":"Show Latest news block"});
                M.util.init_block_hider(Y, {"id":"inst1","title":"Main menu","preference":"block1hidden","tooltipVisible":"Hide Main menu block","tooltipHidden":"Show Main menu block"});
                M.util.init_block_hider(Y, {"id":"inst14","title":"Tags","preference":"block14hidden","tooltipVisible":"Hide Tags block","tooltipHidden":"Show Tags block"});
                M.util.init_block_hider(Y, {"id":"inst15","title":"Online users","preference":"block15hidden","tooltipVisible":"Hide Online users block","tooltipHidden":"Show Online users block"});
                M.util.init_block_hider(Y, {"id":"inst13","title":"Login","preference":"block13hidden","tooltipVisible":"Hide Login block","tooltipHidden":"Show Login block"});
                M.util.init_block_hider(Y, {"id":"inst4","title":"Navigation","preference":"block4hidden","tooltipVisible":"Hide Navigation block","tooltipHidden":"Show Navigation block"});
                M.util.init_block_hider(Y, {"id":"inst3","title":"Calendar","preference":"block3hidden","tooltipVisible":"Hide Calendar block","tooltipHidden":"Show Calendar block"});
                M.util.init_block_hider(Y, {"id":"inst90","title":"HTML BLock","preference":"block90hidden","tooltipVisible":"Hide HTML BLock block","tooltipHidden":"Show HTML BLock block"});
                M.util.init_toggle_class_on_click(Y, "course_category_tree4d83186a4c0ae", ".category.with_children .category_label", "collapsed", ".category.with_children");

            });
            //]]&gt;
        </script>
        <div class="dock dock_left_vertical nothingdocked" id="dock">
            <div class="buttons_container" id="yui_3_2_0_1_130043709474845">
                <div class="dockeditem_container"></div>
                <div class="controls">
                    <img title="Undock all" alt="Undock all" src="http://www.newschoollearning.com/moodle2/theme/image.php?theme=splash&amp;image=t/dock_to_block"></div>
            </div>
        </div>
        <div style="display: none;">

        </div>
    </body>
</html>