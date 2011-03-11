/*
VideoJS - HTML5 Video Player
v1.1.5

This file is part of VideoJS. Copyright 2010 Zencoder, Inc.

VideoJS is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

VideoJS is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with VideoJS.  If not, see <http://www.gnu.org/licenses/>.
*/

// Using jresig's Class implementation http://ejohn.org/blog/simple-javascript-inheritance/
(function(){var initializing=false, fnTest=/xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/; this.JRClass = function(){}; JRClass.extend = function(prop) { var _super = this.prototype; initializing = true; var prototype = new this(); initializing = false; for (var name in prop) { prototype[name] = typeof prop[name] == "function" && typeof _super[name] == "function" && fnTest.test(prop[name]) ? (function(name, fn){ return function() { var tmp = this._super; this._super = _super[name]; var ret = fn.apply(this, arguments); this._super = tmp; return ret; }; })(name, prop[name]) : prop[name]; } function JRClass() { if ( !initializing && this.init ) this.init.apply(this, arguments); } JRClass.prototype = prototype; JRClass.constructor = JRClass; JRClass.extend = arguments.callee; return JRClass;};})();

// Video JS Player Class
var VideoJS = JRClass.extend({

  // Initialize the player for the supplied video tag element
  // element: video tag
  init: function(element, setOptions){

    // Allow an ID string or an element
    if (typeof element == 'string') {
      this.video = document.getElementById(element);
    } else {
      this.video = element;
    }
    // Store reference to player on the video element.
    // So you can acess the player later: document.getElementById("video_id").player.play();
    this.video.player = this;

    // Default Options
    this.options = {
      controlsBelow: false, // Display control bar below video vs. in front of
      showControlsAtStart: false, // Make controls visible when page loads
      controlsHiding: true, // Hide controls when not over the video
      defaultVolume: 0.85, // Will be overridden by localStorage volume if available
      flashVersion: 9, // Required flash version for fallback
      linksHiding: true, // Hide download links when video is supported
      flashIsDominant: false, // Always use Flash when available
      useBrowserControls: false, // Dont' use the video JS controls (iPhone)
      players: ["html5", "flashObject", "links"] // Players and order to use them
    };
    // Override default options with global options
    if (typeof VideoJS.options == "object") { _V_.merge(this.options, VideoJS.options); }
    // Override default & global options with options specific to this player
    if (typeof setOptions == "object") { _V_.merge(this.options, setOptions); }

    // Store reference to embed code pieces
    this.box = this.video.parentNode;
    this.flashObject = this.getFlashObject();
    this.linksFallback = this.getLinksFallback();
    this.hideLinksFallback(); // Will be shown again if "links" player is used

    // Loop through the player names list in options, "html5" etc.
    // For each player name, initialize the player with that name under VideoJS.players
    // If the player successfully initializes, we're done
    // If not, try the next player in the list
    for (var i=0,players=this.options.players,j=players.length; i<j; i++) {
      if((VideoJS.players[players[i]].init.context(this))()) {
        break;
      }
    }
  },

  html5Init: function(){
    this.fixPreloading(); // Support older browsers that used autobuffer

    if (VideoJS.isIOS()) {
      this.options.useBrowserControls = true;
      if(VideoJS.iOSVersion() < 4) {
        this.forceTheSource();
      }
    }

    if (VideoJS.isAndroid()) {
      this.options.useBrowserControls = true;
      this.forceTheSource();
      this.video.addEventListener("click", function(){ this.play(); }, false);
    }

    // Add VideoJS Controls
    if (!this.options.useBrowserControls) {
      this.video.controls = false;

      if (this.options.controlsBelow) { _V_.addClass(this.box, "vjs-controls-below"); }
      this.percentLoaded = 0; // Store amount of video loaded

      // Build Interface
      this.buildStylesCheckDiv(); // Used to check if style are loaded
      this.buildPoster();
      this.buildBigPlayButton();
      this.buildSpinner();
      this.buildControlBar();
      this.loadInterface(); // Show everything once styles are loaded
      this.getSubtitles();

      /* Removeable Event Listeners with Context
      ================================================================================ */
      // These event listeners are attached to global elements like document/window.
      // They are also temporary, which means they need to be removed.
      // They also need context (this) so they can call functions on their specific player.
      // Adding context on initialization allows them to referenced and removed after being attached.
      this.onEscKey = this.onEscKey.context(this);
      this.onWindowResize = this.onWindowResize.context(this);
      this.onProgressMouseMove = this.onProgressMouseMove.context(this);
      this.onProgressMouseUp = this.onProgressMouseUp.context(this);
      this.onVolumeMouseMove = this.onVolumeMouseMove.context(this);
      this.onVolumeMouseUp = this.onVolumeMouseUp.context(this);
    }
  },

  canPlaySource: function(){
    // Cache Result
    if (this.canPlaySourceResult) { return this.canPlaySourceResult; }
    // Loop through sources and check if any can play
    var children = this.video.children;
    for (var i=0,j=children.length; i<j; i++) {
      if (children[i].tagName.toUpperCase() == "SOURCE") {
        var canPlay = this.video.canPlayType(children[i].type);
        if(canPlay == "probably" || canPlay == "maybe"
          || (VideoJS.isAndroid() && children[i].src.match(/\.(mp4|m4v)$/i))) {
          this.firstPlayableSource = children[i];
          this.canPlaySourceResult = true;
          return true;
        }
      }
    }
    this.canPlaySourceResult = false;
    return false;
  },

  // Force the video source - Helps fix loading bugs in a handful of devices, like the iPad/iPhone poster bug
  // And iPad/iPhone javascript include location bug. And Android type attribute bug
  forceTheSource: function(){
    this.video.src = this.firstPlayableSource.src; // From canPlaySource()
    this.video.load();
  },

  loadInterface: function(){
    if(!this.stylesHaveLoaded()) {
      // Don't want to create an endless loop either.
      if (!this.positionRetries) { this.positionRetries = 1; }
      if (this.positionRetries++ < 100) {
        setTimeout(this.loadInterface.context(this),0);
        return;
      }
    }
    this.hideStylesCheckDiv();
    this.showPoster();
    if (this.video.paused !== false) { this.showBigPlayButton(); }
    if (this.options.showControlsAtStart) { this.showControlBar(); }
    this.positionBox();
  },

  /* VideoJS Box - Holds all elements
  ================================================================================ */
  positionBox: function(){
    // Set width based on fullscreen or not.
    if (this.videoIsFullScreen) {
      this.box.style.width = "";
      if (this.options.controlsBelow) {
        this.box.style.height = "";
        this.video.style.height = (this.box.offsetHeight - this.controls.offsetHeight) + "px";
      }
    } else {
      this.box.style.width = this.video.offsetWidth + "px";
      if (this.options.controlsBelow) {
        this.video.style.height = "";
        this.box.style.height = this.video.offsetHeight + this.controls.offsetHeight + "px";
      }
    }
    this.positionControlBar();
    this.positionPoster();
  },

  /* Control Bar
  ================================================================================ */
  buildControlBar: function(){
    /* Creating this HTML
      <ul class="vjs-controls">
        <li class="vjs-play-control vjs-play">
          <span></span>
        </li>
        <li class="vjs-progress-control">
          <ul class="vjs-progress-holder">
            <li class="vjs-load-progress"></li>
            <li class="vjs-play-progress"></li>
          </ul>
        </li>
        <li class="vjs-time-control">
          <span class="vjs-current-time-display">00:00</span><span> / </span><span class="vjs-duration-display">00:00</span>
        </li>
        <li class="vjs-volume-control">
          <ul>
            <li></li><li></li><li></li><li></li><li></li><li></li>
          </ul>
        </li>
        <li class="vjs-fullscreen-control">
          <ul>
            <li></li><li></li><li></li><li></li>
          </ul>
        </li>
      </ul>
    */

    // Create a list element to hold the different controls
    this.controls = _V_.createElement("ul", { className: "vjs-controls" });
    // Add the controls to the video's container
    this.video.parentNode.appendChild(this.controls);

    // Build the play control
    this.playControl = _V_.createElement("li", { className: "vjs-play-control vjs-play", innerHTML: "<span></span>" });
    this.controls.appendChild(this.playControl);

    // Build the progress control
    this.progressControl = _V_.createElement("li", { className: "vjs-progress-control" });
    this.controls.appendChild(this.progressControl);

    // Create a holder for the progress bars
    this.progressHolder = _V_.createElement("ul", { className: "vjs-progress-holder" });
    this.progressControl.appendChild(this.progressHolder);

    // Create the loading progress display
    this.loadProgress = _V_.createElement("li", { className: "vjs-load-progress" });
    this.progressHolder.appendChild(this.loadProgress);

    // Create the playing progress display
    this.playProgress = _V_.createElement("li", { className: "vjs-play-progress" });
    this.progressHolder.appendChild(this.playProgress);

    // Create the progress time display (00:00 / 00:00)
    this.timeControl = _V_.createElement("li", { className: "vjs-time-control" });
    this.controls.appendChild(this.timeControl);

    // Create the current play time display
    this.currentTimeDisplay = _V_.createElement("span", { className: "vjs-current-time-display", innerHTML: "00:00" });
    this.timeControl.appendChild(this.currentTimeDisplay);

    // Add time separator
    this.timeSeparator = _V_.createElement("span", { innerHTML: " / " });
    this.timeControl.appendChild(this.timeSeparator);

    // Create the total duration display
    this.durationDisplay = _V_.createElement("span", { className: "vjs-duration-display", innerHTML: "00:00" });
    this.timeControl.appendChild(this.durationDisplay);

    // Create the volumne control
    this.volumeControl = _V_.createElement("li", {
      className: "vjs-volume-control",
      innerHTML: "<ul><li></li><li></li><li></li><li></li><li></li><li></li></ul>"
    });
    this.controls.appendChild(this.volumeControl);
    this.volumeDisplay = this.volumeControl.children[0];

    // Crete the fullscreen control
    this.fullscreenControl = _V_.createElement("li", {
      className: "vjs-fullscreen-control",
      innerHTML: "<ul><li></li><li></li><li></li><li></li></ul>"
    });
    this.controls.appendChild(this.fullscreenControl);

    this.initializeControls();
  },
  // Set up Event Listeners
  initializeControls: function(){
    /* Initialize Errors
    ================================================================================ */
    this.video.addEventListener('error',this.onError.context(this),false);

    /* Initialize Play/Pause
    ================================================================================ */
    // Listen for when the video is played
    this.video.addEventListener("play", this.onPlay.context(this), false);
    // Listen for when the video is paused
    this.video.addEventListener("pause", this.onPause.context(this), false);
    // Listen for when the video ends
    this.video.addEventListener("ended", this.onEnded.context(this), false);
    // Listen for clicks on the play/pause button
    this.playControl.addEventListener("click", this.onPlayControlClick.context(this), false);
    // Make a click on the video act like a click on the play button.
    this.video.addEventListener("click", this.onPlayControlClick.context(this), false);

    /* Initialize Play Progress
    ================================================================================ */
    // Listen for drags on the progress bar
    this.progressHolder.addEventListener("mousedown", this.onProgressHolderMouseDown.context(this), false);
    // Listen for a release on the progress bar
    this.progressHolder.addEventListener("mouseup", this.onProgressHolderMouseUp.context(this), false);

    /* Initialize Buffering Progress
    ================================================================================ */
    // Listen for Video Load Progress (currently does not if html file is local)
    this.video.addEventListener('progress', this.onProgress.context(this), false);
    // Set interval for load progress using buffer watching method
    this.watchBuffer = setInterval(this.updateBufferedTotal.context(this), 33);

    /* Initialize Volume
    ================================================================================ */
    // Set to stored volume OR 85%
    this.setVolume(localStorage.volume || this.options.defaultVolume);
    // Set the display to the initial volume
    this.updateVolumeDisplay();
    // Listen for a volume change
    this.video.addEventListener('volumechange',this.onVolumeChange.context(this),false);
    // Listen for a drag on the volume control
    this.volumeControl.addEventListener("mousedown", this.onVolumeControlMouseDown.context(this), false);
    // Listen for a release on the volume control
    this.volumeControl.addEventListener("mouseup", this.onVolumeControlMouseUp.context(this), false);

    /* Initialize Fullscreen
    ================================================================================ */
    // Listen for clicks on the button
    this.fullscreenControl.addEventListener("click", this.onFullscreenControlClick.context(this), false);

    /* Initialize Controls Movement
    ================================================================================ */
    // Block hiding when over controls
    this.controls.addEventListener("mousemove", this.onControlsMouseMove.context(this), false);
    // Release controls hiding block, and call VideoMouseOut
    this.controls.addEventListener("mouseout", this.onControlsMouseOut.context(this), false);
    // Listen for the mouse move the video. Used to reveal the controller.
    this.box.addEventListener("mousemove", this.onVideoMouseMove.context(this), false);
    // Listen for the mouse moving out of the video. Used to hide the controller.
    this.box.addEventListener("mouseout", this.onVideoMouseOut.context(this), false);
  },
  showControlBar: function(){
    if (!this.options.showControlsAtStart && !this.hasPlayed) { return; }
    this.controls.style.display = "block";
    this.positionControlBar();
  },
  // Place controller relative to the video's position
  positionControlBar: function(){
    // Make sure the controls are visible
    if (this.controls.style.display == 'none') { return; }

    if (this.options.controlsBelow) {
      this.controls.style.top = this.video.offsetHeight + "px";
    } else {
      this.controls.style.top = (this.video.offsetHeight - this.controls.offsetHeight) + "px";
    }
    this.updatePlayProgress();
    this.updateLoadProgress();
  },
  hideControlBar: function(){
    if (this.options.controlsHiding && !this.mouseIsOverControls) { this.controls.style.display = "none"; }
  },
  onControlsMouseMove: function(){
    // Block controls from hiding when mouse is over them.
    this.mouseIsOverControls = true;
  },
  onControlsMouseOut: function(event){
    this.mouseIsOverControls = false;
    // Have to add the video mouseout to the controller too or it may not hide.
    this.onVideoMouseOut(event);
  },
  onVideoMouseMove: function(){
    this.showControlBar();
    clearInterval(this.mouseMoveTimeout);
    this.mouseMoveTimeout = setTimeout(function(){ this.hideControlBar(); }.context(this), 4000);
  },
  onVideoMouseOut: function(event){
    // Prevent flicker by making sure mouse hasn't left the video
    var parent = event.relatedTarget;
    while (parent && parent !== this.video && parent !== this.controls) {
      parent = parent.parentNode;
    }
    if (parent !== this.video && parent !== this.controls) {
      this.hideControlBar();
    }
  },

  /* Errors
  ================================================================================ */
  errors: [], // Array to track errors
  onError: function(event){ this.log(this.video.error); },

  /* Play/Pause
  ================================================================================ */
  // React to clicks on the play/pause button
  onPlayControlClick: function(event){
    if (this.video.paused) {
      this.video.play();
    } else {
      // Android has a problem with "paused" not returning correctly.
      if (!this.hasPlayed && VideoJS.isAndroid) {
        this.video.play(); return;
      } else {
        this.video.pause();
      }
    }
  },
  // When the video is played
  onPlay: function(event){
    this.hasPlayed = true;
    this.playControl.className = "vjs-play-control vjs-pause";
    this.trackPlayProgress();
  },
  // When the video is paused
  onPause: function(event){
    this.playControl.className = "vjs-play-control vjs-play";
    this.stopTrackingPlayProgress();
  },
  // When the video ends
  onEnded: function(event){
    this.video.currentTime = 0;
    this.video.pause();
  },

  /* Play Progress
  ================================================================================ */
  // Track & display the current play progress
  trackPlayProgress: function(){
    if(this.playProgressInterval) { clearInterval(this.playProgressInterval); }
    this.playProgressInterval = setInterval(function(){ this.updatePlayProgress(); }.context(this), 33);
  },
  // Turn off play progress tracking (when paused)
  stopTrackingPlayProgress: function(){ clearInterval(this.playProgressInterval); },
  // Ajust the play progress bar's width based on the current play time
  updatePlayProgress: function(){
    if (this.controls.style.display == 'none') { return; }
    this.playProgress.style.width = ((this.video.currentTime / this.video.duration) * (_V_.getComputedStyleValue(this.progressHolder, "width").replace("px", ""))) + "px";
    this.updateTimeDisplay();
  },
  // Update the play position based on where the user clicked on the progresss bar
  setPlayProgress: function(newProgress){
    try { this.video.currentTime = newProgress * this.video.duration; }
      catch(e) {
        if (e.code == 11) { this.errors.push(VideoJS.errorCodes.videoNotReady); }
      }
    this.playProgress.style.width = newProgress * (_V_.getComputedStyleValue(this.progressHolder, "width").replace("px", "")) + "px";
    this.updateTimeDisplay();
    // currentTime changed, reset subtitles
    if (!this.subtitles) { this.currentSubtitlePosition = 0; }
  },
  setPlayProgressWithEvent: function(event){
    var newProgress = _V_.getRelativePosition(event.pageX, this.progressHolder);
    this.setPlayProgress(newProgress);
  },
  // Adjust the play position when the user drags on the progress bar
  onProgressHolderMouseDown: function(event){
    this.stopTrackingPlayProgress();

    if (this.video.paused) {
      this.videoWasPlaying = false;
    } else {
      this.videoWasPlaying = true;
      this.video.pause();
    }
    _V_.blockTextSelection();
    this.setPlayProgressWithEvent(event);
    document.addEventListener("mousemove", this.onProgressMouseMove, false);
    document.addEventListener("mouseup", this.onProgressMouseUp, false);
  },
  onProgressMouseMove: function(event){ // Removeable
    this.setPlayProgressWithEvent(event);
  },
  onProgressMouseUp: function(event){ // Removeable
    _V_.unblockTextSelection();
    document.removeEventListener("mousemove", this.onProgressMouseMove, false);
    document.removeEventListener("mouseup", this.onProgressMouseUp, false);
    if (this.videoWasPlaying) {
      this.video.play();
      this.trackPlayProgress();
    }
  },
  // When the user stops dragging on the progress bar, update play position
  // Backup for when the user only clicks and doesn't drag
  onProgressHolderMouseUp: function(event){
    // Removed. Chrome breaks (shows poster, plays audio) if you set currentTime rapidly.
    // this.setPlayProgressWithEvent(event);

    // Fix for a play button state issue.
    if (this.video.paused) {
      this.onPause();
    } else {
      this.onPlay();
    }
  },

  // Update the displayed time (00:00)
  updateTimeDisplay: function(){
    this.currentTimeDisplay.innerHTML = _V_.formatTime(this.video.currentTime);
    if (this.video.duration) { this.durationDisplay.innerHTML = _V_.formatTime(this.video.duration); }
  },

  /* Load Progress
  ================================================================================ */
  // When the video's load progress is updated
  // Does not work in all browsers (Safari/Chrome 5)
  onProgress: function(event){
    if(event.total > 0) {
      this.setLoadProgress(event.loaded / event.total);
    }
  },
  // Buffer watching method for load progress.
  // Used for browsers that don't support the progress event
  updateBufferedTotal: function(){
    if (this.video.buffered) {
      if (this.video.buffered.length >= 1) {
        this.setLoadProgress(this.video.buffered.end(0) / this.video.duration);
        if (this.video.buffered.end(0) == this.video.duration) {
          clearInterval(this.watchBuffer);
        }
      }
    } else {
      clearInterval(this.watchBuffer);
    }
  },
  setLoadProgress: function(percentAsDecimal){
    if (percentAsDecimal > this.percentLoaded) {
      this.percentLoaded = percentAsDecimal;
      this.updateLoadProgress();
    }
  },
  updateLoadProgress: function(){
    if (this.controls.style.display == 'none') { return; }
    this.loadProgress.style.width = (this.percentLoaded * (_V_.getComputedStyleValue(this.progressHolder, "width").replace("px", ""))) + "px";
  },

  /* Volume
  ================================================================================ */
  onVolumeChange: function(event){ this.updateVolumeDisplay(); },
  // Adjust the volume when the user drags on the volume control
  onVolumeControlMouseDown: function(event){
    _V_.blockTextSelection();
    this.setVolumeWithEvent(event);
    document.addEventListener("mousemove", this.onVolumeMouseMove, false);
    document.addEventListener("mouseup", this.onVolumeMouseUp, false);
  },
  onVolumeMouseMove: function(event){
    this.setVolumeWithEvent(event);
  },
  onVolumeMouseUp: function(event){
    _V_.unblockTextSelection();
    document.removeEventListener("mousemove", this.onVolumeMouseMove, false);
    document.removeEventListener("mouseup", this.onVolumeMouseUp, false);
  },
  // When the user stops dragging, set a new volume
  // Backup for when the user only clicks and doesn't drag
  onVolumeControlMouseUp: function(event){
    this.setVolumeWithEvent(event);
  },

  // Set a new volume based on where the user clicked on the volume control
  setVolume: function(newVol){
    this.video.volume = parseFloat(newVol);
    localStorage.volume = this.video.volume;
  },

  setVolumeWithEvent: function(event){
    var newVol = _V_.getRelativePosition(event.pageX, this.volumeControl.children[0]);
    this.setVolume(newVol);
  },

  // Update the volume control display
  // Unique to these default controls. Uses borders to create the look of bars.
  updateVolumeDisplay: function(){
    var volNum = Math.ceil(this.video.volume * 6);
    for(var i=0; i<6; i++) {
      if (i < volNum) {
        _V_.addClass(this.volumeDisplay.children[i], "vjs-volume-level-on");
      } else {
        _V_.removeClass(this.volumeDisplay.children[i], "vjs-volume-level-on");
      }
    }
  },

  /* Fullscreen / Full-window
  ================================================================================ */
  // When the user clicks on the fullscreen button, update fullscreen setting
  onFullscreenControlClick: function(event){
    if (!this.videoIsFullScreen) {
      this.fullscreenOn();
    } else {
      this.fullscreenOff();
    }
  },
  // Turn on fullscreen (window) mode
  // Real fullscreen isn't available in browsers quite yet.
  fullscreenOn: function(){
    if (!this.nativeFullscreenOn()) {
      this.videoIsFullScreen = true;
      // Storing original doc overflow value to return to when fullscreen is off
      this.docOrigOverflow = document.documentElement.style.overflow;
      // Add listener for esc key to exit fullscreen
      document.addEventListener("keydown", this.onEscKey, false);
      // Add listener for a window resize
      window.addEventListener("resize", this.onWindowResize, false);
      // Hide any scroll bars
      document.documentElement.style.overflow = 'hidden';
      // Apply fullscreen styles
      _V_.addClass(this.box, "vjs-fullscreen");
      // Resize the box, controller, and poster
      this.positionBox();
    }
  },
  // If available use the native fullscreen
  nativeFullscreenOn: function(){
    if(typeof this.video.webkitEnterFullScreen == 'function') {
      // Seems to be broken in Chromium/Chrome
      if (!navigator.userAgent.match("Chrome")) {
        try {
          this.video.webkitEnterFullScreen();
        } catch (e) {
          if (e.code == 11) { this.errors.push(VideoJS.errorCodes.videoNotReady); }
        }
        return true;
      }
    }
  },
  // Turn off fullscreen (window) mode
  fullscreenOff: function(){
    this.videoIsFullScreen = false;
    document.removeEventListener("keydown", this.onEscKey, false);
    window.removeEventListener("resize", this.onWindowResize, false);
    // Unhide scroll bars.
    document.documentElement.style.overflow = this.docOrigOverflow;
    // Remove fullscreen styles
    _V_.removeClass(this.box, "vjs-fullscreen");
    // Resize the box, controller, and poster to original sizes
    this.positionBox();
  },
  onWindowResize: function(event){ // Removeable
    this.positionControlBar();
  },
  // Create listener for esc key while in full screen mode
  onEscKey: function(event){ // Removeable
    if (event.keyCode == 27) {
      this.fullscreenOff();
    }
  },

  /* Big Play Button
  ================================================================================ */
  buildBigPlayButton: function(){
    /* Creating this HTML
      <div class="vjs-big-play-button"><span></span></div>
    */
    this.bigPlayButton = _V_.createElement("div", {
      className: "vjs-big-play-button",
      innerHTML: "<span></span>"
    });
    this.video.parentNode.appendChild(this.bigPlayButton);
    this.initializeBigPlayButton();
  },
  initializeBigPlayButton: function(){
    this.bigPlayButton.addEventListener("click", this.onPlayControlClick.context(this), false);
    this.video.addEventListener("play", this.bigPlayButtonOnPlay.context(this), false);
    this.video.addEventListener("ended", this.bigPlayButtonOnEnded.context(this), false);
  },
  showBigPlayButton: function(){ this.bigPlayButton.style.display = "block"; },
  hideBigPlayButton: function(){ this.bigPlayButton.style.display = "none"; },
  bigPlayButtonOnPlay: function(event){ this.hideBigPlayButton(); },
  bigPlayButtonOnEnded: function(event){ this.showBigPlayButton(); },

  /* Spinner (Loading)
  ================================================================================ */
  buildSpinner: function(){
    this.spinner = _V_.createElement("div", {
      className: "vjs-spinner",
      innerHTML: "<div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div>"
    });
    this.video.parentNode.appendChild(this.spinner);
    this.initializeSpinner();
  },
  initializeSpinner: function(){
    this.video.addEventListener("loadeddata", this.spinnerOnLoadedData.context(this), false);
    this.video.addEventListener("loadstart", this.spinnerOnLoadStart.context(this), false);
    this.video.addEventListener("seeking", this.spinnerOnSeeking.context(this), false);
    this.video.addEventListener("seeked", this.spinnerOnSeeked.context(this), false);
    this.video.addEventListener("canplay", this.spinnerOnCanPlay.context(this), false);
    this.video.addEventListener("canplaythrough", this.spinnerOnCanPlayThrough.context(this), false);
    this.video.addEventListener("waiting", this.spinnerOnWaiting.context(this), false);
    this.video.addEventListener("stalled", this.spinnerOnStalled.context(this), false);
    this.video.addEventListener("suspend", this.spinnerOnSuspend.context(this), false);
    this.video.addEventListener("playing", this.spinnerOnPlaying.context(this), false);
    this.video.addEventListener("timeupdate", this.spinnerOnTimeUpdate.context(this), false);
  },
  showSpinner: function(){
    this.spinner.style.display = "block";
    clearInterval(this.spinnerInterval);
    this.spinnerInterval = setInterval(function(){ this.rotateSpinner(); }.context(this), 100);
  },
  hideSpinner: function(){
    this.spinner.style.display = "none";
    clearInterval(this.spinnerInterval);
  },
  spinnerRotated: 0,
  rotateSpinner: function(){
    // this.spinner.style.transform =       'scale(0.5) rotate('+this.spinnerRotated+'deg)';
    this.spinner.style.WebkitTransform = 'scale(0.5) rotate('+this.spinnerRotated+'deg)';
    this.spinner.style.MozTransform =    'scale(0.5) rotate('+this.spinnerRotated+'deg)';
    if (this.spinnerRotated == 360) { this.spinnerRotated = 0; }
    this.spinnerRotated += 45;
  },
  spinnerOnLoadedData: function(event){ this.hideSpinner(); },
  spinnerOnLoadStart: function(event){ this.showSpinner(); },
  spinnerOnSeeking: function(event){ /* this.showSpinner(); */ },
  spinnerOnSeeked: function(event){ /* this.hideSpinner(); */ },
  spinnerOnCanPlay: function(event){ /* this.hideSpinner(); */ },
  spinnerOnCanPlayThrough: function(event){ this.hideSpinner(); },
  spinnerOnWaiting: function(event){
    // Safari sometimes triggers waiting inappropriately
    // Like after video has played, any you play again.
    this.showSpinner();
  },
  spinnerOnStalled: function(event){},
  spinnerOnSuspend: function(event){},
  spinnerOnPlaying: function(event){ this.hideSpinner(); },
  spinnerOnTimeUpdate: function(event){
    // Safari sometimes calls waiting and doesn't recover
    if(this.spinner.style.display == "block") { this.hideSpinner(); }
  },

  /* Styles Check - Check if styles are loaded
  ================================================================================ */
  // Sometimes the CSS styles haven't been applied to the controls yet
  // when we're trying to calculate the height and position them correctly.
  // This causes a flicker where the controls are out of place.
  buildStylesCheckDiv: function(){
    this.stylesCheckDiv = _V_.createElement("div", { className: "vjs-styles-check" });
    this.stylesCheckDiv.style.position = "absolute";
    this.box.appendChild(this.stylesCheckDiv);
  },
  hideStylesCheckDiv: function(){ this.stylesCheckDiv.style.display = "none"; },
  stylesHaveLoaded: function(){
    if (this.stylesCheckDiv.offsetHeight != 5) {
       return false;
    } else {
      return true;
    }
  },

  /* Poster Image
  ================================================================================ */
  buildPoster: function(){
    this.updatePosterSource();
    if (this.video.poster) {
      this.poster = document.createElement("img");
      // Add poster to video box
      this.video.parentNode.appendChild(this.poster);

      // Add poster image data
      this.poster.src = this.video.poster;
      // Add poster styles
      this.poster.className = "vjs-poster";
      this.initializePoster();
    } else {
      this.poster = false;
    }
  },
  initializePoster: function(){
    if (this.poster) {
      // Listen for the mouse move the poster image. Used to reveal the controller.
      this.poster.addEventListener("mousemove", this.onVideoMouseMove.context(this), false);
      // Listen for the mouse moving out of the poster image. Used to hide the controller.
      this.poster.addEventListener("mouseout", this.onVideoMouseOut.context(this), false);
      // Make a click on the poster act like a click on the play button.
      this.poster.addEventListener("click", this.onPlayControlClick.context(this), false);
      // Hide/Show poster on video events
      this.video.addEventListener("play", this.posterOnPlay.context(this), false);
      this.video.addEventListener("ended", this.posterOnEnded.context(this), false);
    }
  },
  // Add the video poster to the video's container, to fix autobuffer/preload bug
  showPoster: function(){
    if (!this.poster) { return; }
    this.poster.style.display = "block";
    this.positionPoster();
  },
  // Size the poster image
  positionPoster: function(){
    // Only if the poster is visible
    if (!this.poster || this.poster.style.display == 'none') { return; }
    this.poster.style.height = this.video.offsetHeight + "px";
    this.poster.style.width = this.video.offsetWidth + "px";
  },
  hidePoster: function(){
    if (!this.poster) { return; }
    this.poster.style.display = "none";
  },
  // Update poster source from attribute or fallback image
  // iPad breaks if you include a poster attribute, so this fixes that
  updatePosterSource: function(){
    if (!this.video.poster) {
      var images = this.video.getElementsByTagName("img");
      if (images.length > 0) { this.video.poster = images[0].src; }
    }
  },
  posterOnEnded: function(){ this.showPoster(); },
  posterOnPlay: function(){ this.hidePoster(); },

  /* Subtitles
  ================================================================================ */
  getSubtitles: function(){
    var tracks = this.video.getElementsByTagName("TRACK");
    for (var i=0,j=tracks.length; i<j; i++) {
      if (tracks[i].getAttribute("kind") == "subtitles") {
        this.subtitlesSource = tracks[i].getAttribute("src");
      }
    }
    if (this.subtitlesSource !== undefined) {
      this.loadSubtitles();
      this.buildSubtitles();
    }
  },

  loadSubtitles: function() {
    _V_.get(this.subtitlesSource, this.parseSubtitles.context(this));
  },

  parseSubtitles: function(subText) {
    var lines = subText.replace("\r",'').split("\n");
    this.subtitles = [];
    this.currentSubtitlePosition = 0;

    var i = 0;
    while(i<lines.length) {
      // define the current subtitle object
      var subtitle = {};
      // get the number
      subtitle.id = lines[i++];
      if (!subtitle.id) {
        break;
      }

      // get time
      var time = lines[i++].split(" --> ");
      subtitle.startTime = this.parseSubtitleTime(time[0]);
      subtitle.endTime = this.parseSubtitleTime(time[1]);

      // get subtitle text
      var text = [];
      while(lines[i].length>0 && lines[i]!="\r") {
        text.push(lines[i++]);
      }
      subtitle.text = text.join('<br/>');

      // add this subtitle
      this.subtitles.push(subtitle);

      // ignore the blank line
      i++;
    }
  },

  parseSubtitleTime: function(timeText) {
    var parts = timeText.split(':');
    var time = 0;
    // hours => seconds
    time += parseFloat(parts[0])*60*60;
    // minutes => seconds
    time += parseFloat(parts[1])*60;
    // get seconds
    var seconds = parts[2].split(',');
    time += parseFloat(seconds[0]);
    // add miliseconds
    time = time + parseFloat(seconds[1])/1000;
    return time;
  },

  buildSubtitles: function(){
    /* Creating this HTML
      <div class="vjs-subtitles"></div>
    */
    this.subtitlesDiv = _V_.createElement("div", { className: 'vjs-subtitles' });
    this.box.appendChild(this.subtitlesDiv);
    this.initializeSubtitles();
  },

  initializeSubtitles: function(){
    this.video.addEventListener('timeupdate', this.subtitlesOnTimeUpdate.context(this), false);
  },

  subtitlesOnTimeUpdate: function(){
    // show the subtitles
    if (this.subtitles) {
      var x = this.currentSubtitlePosition;

      while (x<this.subtitles.length && this.video.currentTime>this.subtitles[x].endTime) {
        if (this.subtitles[x].showing) {
          this.subtitles[x].showing = false;
          this.subtitlesDiv.innerHTML = "";
        }
        this.currentSubtitlePosition++;
        x = this.currentSubtitlePosition;
      }

      if (this.currentSubtitlePosition>=this.subtitles.length) { return; }

      if (this.video.currentTime>=this.subtitles[x].startTime && this.video.currentTime<=this.subtitles[x].endTime) {
        this.subtitlesDiv.innerHTML = this.subtitles[x].text;
        this.subtitles[x].showing = true;
      }
    }
  },


  /* Device Fixes
  ================================================================================ */
  // Support older browsers that used "autobuffer"
  fixPreloading: function(){
    if (typeof this.video.hasAttribute == "function" && this.video.hasAttribute("preload") && this.video.preload != "none") {
      this.video.autobuffer = true; // Was a boolean
    } else {
      this.video.autobuffer = false;
      this.video.preload = "none";
    }
  },

  /* Using Default Controls for iPad now. Can't do native fullscreen through the iPad API */
  // For iPads, controls need to always show because there's no hover
  // The controls also have to be below for the full-window mode to work.
  // iPadFix: function(){
  //   this.options.controlsBelow = true;
  //   this.options.controlsHiding = false;
  // },

  history: [],
  log: function(event){
    if (!event) { return; }
    if (typeof event == "string") { event = { type: event }; }
    if (event.type) { this.history.push(event.type); }
    if (this.history.length >= 50) { this.history.shift(); }
    if (this.options.debug === true) { console.log(event.type); }
  },

  /* Flash Object Fallback
  ================================================================================ */
  // Get Flash Fallback object element from Embed Code
  getFlashObject: function(){
    var objects = this.video.getElementsByTagName("OBJECT");
    for (var i=0,j=objects.length; i<j; i++) {
      if (objects[i].className == "vjs-flash-fallback") {
        return  objects[i];
      }
    }
  },
  replaceWithFlash: function(){
    // this.flashObject = this.video.removeChild(this.flashObject);
    if (this.flashObject) {
      this.box.insertBefore(this.flashObject, this.video);
      this.video.style.display = "none"; // Removing it was breaking later players
    }
  },
  // Check if browser can use this flash player
  flashVersionSupported: function(){ return VideoJS.getFlashVersion() >= this.options.flashVersion; },

  /* Download Links Fallback
  ================================================================================ */
  // Get the download links block element
  getLinksFallback: function(){ return this.box.getElementsByTagName("P")[0]; },
  // Hide no-video download paragraph
  hideLinksFallback: function(){
    if (this.options.linksHiding && this.linksFallback) { this.linksFallback.style.display = "none"; }
  },
  // Hide no-video download paragraph
  showLinksFallback: function(){
    if (this.linksFallback) { this.linksFallback.style.display = "block"; }
  },

  /* Player API - Translate functionality from player to video
  ================================================================================ */
  play: function(){ this.video.play(); },
  pause: function(){ this.video.pause(); },
  width: function(width){
    this.video.width = width;
    this.box.width = width;
    // Width isn't working for the poster
    this.poster.style.width = width+"px";
    this.positionControlBar();
    return this;
  },
  height: function(height){
    this.video.height = height;
    this.box.height = height;
    this.poster.style.height = height+"px";
    this.positionControlBar();
    return this;
  },
  volume: function(newVolume){
    if (newVolume !== undefined) { this.setVolume(newVolume); }
    return this.video.volume;
  }
});

////////////////////////////////////////////////////////////////////////////////
// Convenience Functions (mini library)
// Functions not specific to video or VideoJS and could probably be replaced with a library like jQuery
////////////////////////////////////////////////////////////////////////////////
var _V_ = {
  addClass: function(element, classToAdd){
    if (element.className.split(/\s+/).lastIndexOf(classToAdd) == -1) { element.className = element.className === "" ? classToAdd : element.className + " " + classToAdd; }
  },

  removeClass: function(element, classToRemove){
    if (element.className.indexOf(classToRemove) == -1) { return; }
    var classNames = element.className.split(/\s+/);
    classNames.splice(classNames.lastIndexOf(classToRemove),1);
    element.className = classNames.join(" ");
  },

  merge: function(obj1, obj2){
    for(var attrname in obj2){
      if (obj2.hasOwnProperty(attrname)) {
        obj1[attrname]=obj2[attrname];
      }
    }
    return obj1;
  },

  createElement: function(tagName, attributes){
    return _V_.merge(document.createElement(tagName), attributes);
  },

  // Attempt to block the ability to select text while dragging controls
  blockTextSelection: function(){
    document.body.focus();
    document.onselectstart = function () { return false; };
  },

  // Turn off text selection blocking
  unblockTextSelection: function(){
    document.onselectstart = function () { return true; };
  },

  // Return seconds as MM:SS
  formatTime: function(secs) {
    var seconds = Math.round(secs);
    var minutes = Math.floor(seconds / 60);
    minutes = (minutes >= 10) ? minutes : "0" + minutes;
    seconds = Math.floor(seconds % 60);
    seconds = (seconds >= 10) ? seconds : "0" + seconds;
    return minutes + ":" + seconds;
  },

  // Return the relative horizonal position of an event as a value from 0-1
  getRelativePosition: function(x, relativeElement){
    return Math.max(0, Math.min(1, (x - _V_.findPosX(relativeElement)) / relativeElement.offsetWidth));
  },

  // Get an objects position on the page
  findPosX: function(obj) {
    var curleft = obj.offsetLeft;
    while(obj = obj.offsetParent) {
      curleft += obj.offsetLeft;
    }
    return curleft;
  },

  getComputedStyleValue: function(element, style){
    return window.getComputedStyle(element, null).getPropertyValue(style);
  },

  get: function(url, onSuccess){
    if (typeof XMLHttpRequest == "undefined") {
      XMLHttpRequest = function () {
        try { return new ActiveXObject("Msxml2.XMLHTTP.6.0"); } catch (e) {}
        try { return new ActiveXObject("Msxml2.XMLHTTP.3.0"); } catch (f) {}
        try { return new ActiveXObject("Msxml2.XMLHTTP"); } catch (g) {}
        //Microsoft.XMLHTTP points to Msxml2.XMLHTTP.3.0 and is redundant
        throw new Error("This browser does not support XMLHttpRequest.");
      };
    }
    var request = new XMLHttpRequest();
    request.open("GET",url);
    request.onreadystatechange = function() {
      if (request.readyState == 4 && request.status == 200) {
        onSuccess(request.responseText);
      }
    }.context(this);
    request.send();
  },

  // DOM Ready functionality adapted from jQuery. http://jquery.com/
  bindDOMReady: function(){
    if (document.readyState === "complete") {
      return _V_.DOMReady();
    }
    if (document.addEventListener) {
      document.addEventListener("DOMContentLoaded", _V_.DOMContentLoaded, false);
      window.addEventListener("load", _V_.DOMReady, false);
    } else if (document.attachEvent) {
      document.attachEvent("onreadystatechange", _V_.DOMContentLoaded);
      window.attachEvent("onload", _V_.DOMReady);
    }
  },

  DOMContentLoaded: function(){
    if (document.addEventListener) {
      document.removeEventListener( "DOMContentLoaded", _V_.DOMContentLoaded, false);
      _V_.DOMReady();
    } else if ( document.attachEvent ) {
      if ( document.readyState === "complete" ) {
        document.detachEvent("onreadystatechange", _V_.DOMContentLoaded);
        _V_.DOMReady();
      }
    }
  },

  // Functions to be run once the DOM is loaded
  DOMReadyList: [],
  addToDOMReady: function(fn){
    if (_V_.DOMIsReady) {
      fn.call(document);
    } else {
      _V_.DOMReadyList.push(fn);
    }
  },

  DOMIsReady: false,
  DOMReady: function(){
    if (_V_.DOMIsReady) { return; }
    if (!document.body) { return setTimeout(_V_.DOMReady, 13); }
    _V_.DOMIsReady = true;
    if (_V_.DOMReadyList) {
      for (var i=0; i<_V_.DOMReadyList.length; i++) {
        _V_.DOMReadyList[i].call(document);
      }
      _V_.DOMReadyList = null;
    }
  }
};
_V_.bindDOMReady();

////////////////////////////////////////////////////////////////////////////////
// Class Methods
// Functions that don't apply to individual videos.
////////////////////////////////////////////////////////////////////////////////

VideoJS.players = {
  html5: {
    init: function(){
      if (VideoJS.browserSupportsVideo() && this.canPlaySource()) {
        this.html5Init();
        return true;
      } else {
        return false;
      }
    }
  },

  flashObject: {
    init: function(){
      // Check if object exists & Flash Player version is supported
      if (this.flashObject && this.flashVersionSupported()) {
        this.replaceWithFlash();
        return true;
      } else {
        return false;
      }
    }
  },

  links: {
    init: function(){
      this.showLinksFallback();
      return true;
    }
  }
}

// Add VideoJS to all video tags with the video-js class when the DOM is ready
VideoJS.setupAllWhenReady = function(options){
  // Options is stored globally, and added ot any new player on init
  VideoJS.options = options;
  VideoJS.DOMReady(VideoJS.setup);
};

// Run the supplied function when the DOM is ready
VideoJS.DOMReady = function(fn){
  _V_.addToDOMReady(fn);
};

// Set up a specific video or array of video elements
// "video" can be:
//    false, undefined, or "All": set up all videos with the video-js class
//    A video tag ID or video tag element: set up one video and return one player
//    An array of video tag elements/IDs: set up each and return an array of players
VideoJS.setup = function(videos, options){

  var returnSingular = false,
  playerList = [],
  videoElement;

  // If videos is undefined or "All", set up all videos with the video-js class
  if (!videos || videos == "All") {
    videos = VideoJS.getVideoJSTags();
  // If videos is not an array, add to an array
  } else if (typeof videos != 'object' || videos.nodeType == 1) {
    videos = [videos];
    returnSingular = true;
  }

  // Loop through videos and create players for them
  for (var i=0; i<videos.length; i++) {
    if (typeof videos[i] == 'string') {
      videoElement = document.getElementById(videos[i]);
    } else { // assume DOM object
      videoElement = videos[i];
    }
    playerList.push(new VideoJS(videoElement, options));
  }

  // Return one or all depending on what was passed in
  return (returnSingular) ? playerList[0] : playerList;
};

// Find video tags with the video-js class
VideoJS.getVideoJSTags = function() {
  var videoTags = document.getElementsByTagName("video"),
  videoJSTags = [], videoTag;

  for (var i=0,j=videoTags.length; i<j; i++) {
    videoTag = videoTags[i];
    if (videoTag.className.indexOf("video-js") != -1) {
      videoJSTags.push(videoTag);
    }
  }

  return videoJSTags;
};

// Check if the browser supports video.
VideoJS.browserSupportsVideo = function() {
  if (typeof VideoJS.videoSupport != "undefined") { return VideoJS.videoSupport; }
  VideoJS.videoSupport = !!document.createElement('video').canPlayType;
  return VideoJS.videoSupport;
};

VideoJS.getFlashVersion = function(){
  // Cache Version
  if (typeof VideoJS.flashVersion != "undefined") { return VideoJS.flashVersion; }
  var version = 0, desc;
  if (typeof navigator.plugins != "undefined" && typeof navigator.plugins["Shockwave Flash"] == "object") {
    desc = navigator.plugins["Shockwave Flash"].description;
    if (desc && !(typeof navigator.mimeTypes != "undefined" && navigator.mimeTypes["application/x-shockwave-flash"] && !navigator.mimeTypes["application/x-shockwave-flash"].enabledPlugin)) {
      version = parseInt(desc.match(/^.*\s+([^\s]+)\.[^\s]+\s+[^\s]+$/)[1], 10);
    }
  } else if (typeof window.ActiveXObject != "undefined") {
    try {
      var testObject = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
      if (testObject) {
        version = parseInt(testObject.GetVariable("$version").match(/^[^\s]+\s(\d+)/)[1], 10);
      }
    }
    catch(e) {}
  }
  VideoJS.flashVersion = version;
  return VideoJS.flashVersion;
};

// Browser & Device Checks
VideoJS.isIE = function(){ return !+"\v1"; };
VideoJS.isIPad = function(){ return navigator.userAgent.match(/iPad/i) !== null; };
VideoJS.isIPhone = function(){ return navigator.userAgent.match(/iPhone/i) !== null; };
VideoJS.isIOS = function(){ return VideoJS.isIPhone() || VideoJS.isIPad(); };
VideoJS.iOSVersion = function() {
  var match = navigator.userAgent.match(/OS (\d+)_/i);
  if (match && match[1]) { return match[1]; }
};
VideoJS.isAndroid = function(){ return navigator.userAgent.match(/Android/i) !== null; };
VideoJS.androidVersion = function() {
  var match = navigator.userAgent.match(/Android (\d+)\./i);
  if (match && match[1]) { return match[1]; }
};

VideoJS.errorCodes = {
  // Safari errors if you call functions on a video that hasn't loaded yet
  videoNotReady: "Video is not ready yet (try playing the video first)."
};

// Allows for binding context to functions
// when using in event listeners and timeouts
Function.prototype.context = function(obj){
  var method = this, temp;
  temp = function(){
    return method.apply(obj, arguments);
  };
  return temp;
};

// Shim to make Video tag valid in IE
if(VideoJS.isIE()) { document.createElement("video"); }

// jQuery Plugin
if (window.jQuery) {
  (function($) {
    $.fn.VideoJS = function(options) {
      this.each(function() {
        VideoJS.setup(this, options);
      });
      return this;
    };
    $.fn.player = function() {
      return this[0].player;
    };
  })(jQuery);
}


/*
 * Twitter's Blogger.js javascript file
 */
function twitterCallback2(twitters) {
  var statusHTML = [];
  for (var i=0; i<twitters.length; i++){
    var username = twitters[i].user.screen_name;
    var status = twitters[i].text.replace(/((https?|s?ftp|ssh)\:\/\/[^"\s\<\>]*[^.,;'">\:\s\<\>\)\]\!])/g, function(url) {
      return '<a href="'+url+'">'+url+'</a>';
    }).replace(/\B@([_a-z0-9]+)/ig, function(reply) {
      return  reply.charAt(0)+'<a href="http://twitter.com/'+reply.substring(1)+'">'+reply.substring(1)+'</a>';
    });
    statusHTML.push('<li>'+status+' <a href="http://twitter.com/'+username+'/statuses/'+twitters[i].id+'">'+relative_time(twitters[i].created_at)+'</a></li>');
  }
  document.getElementById('twitter_update_list').innerHTML = statusHTML.join('');
}

function relative_time(time_value) {
  var values = time_value.split(" ");
  time_value = values[1] + " " + values[2] + ", " + values[5] + " " + values[3];
  var parsed_date = Date.parse(time_value);
  var relative_to = (arguments.length > 1) ? arguments[1] : new Date();
  var delta = parseInt((relative_to.getTime() - parsed_date) / 1000);
  delta = delta + (relative_to.getTimezoneOffset() * 60);

  if (delta < 60) {
    return 'less than a minute ago';
  } else if(delta < 120) {
    return 'about a minute ago';
  } else if(delta < (60*60)) {
    return (parseInt(delta / 60)).toString() + ' minutes ago';
  } else if(delta < (120*60)) {
    return 'about an hour ago';
  } else if(delta < (24*60*60)) {
    return 'about ' + (parseInt(delta / 3600)).toString() + ' hours ago';
  } else if(delta < (48*60*60)) {
    return '1 day ago';
  } else {
    return (parseInt(delta / 86400)).toString() + ' days ago';
  }
}


/* ------------------------------------------------------------------------
 * Class: prettyPhoto
 * Use: Lightbox clone for jQuery
 * Author: Stephane Caron (http://www.no-margin-for-errors.com)
 * Version: 3.0
 * ------------------------------------------------------------------------- */

(function($){$.prettyPhoto={version:'3.0'};$.fn.prettyPhoto=function(pp_settings){pp_settings=jQuery.extend({animation_speed:'fast',slideshow:false,autoplay_slideshow:false,opacity:0.80,show_title:true,allow_resize:true,default_width:500,default_height:344,counter_separator_label:'/',theme:'facebook',hideflash:false,wmode:'opaque',autoplay:true,modal:false,overlay_gallery:true,keyboard_shortcuts:true,changepicturecallback:function(){},callback:function(){},markup:'<div class="pp_pic_holder"><div class="ppt">&nbsp;</div><div class="pp_top"><div class="pp_left"></div><div class="pp_middle"></div><div class="pp_right"></div></div><div class="pp_content_container"><div class="pp_left"><div class="pp_right"><div class="pp_content"><div class="pp_loaderIcon"></div><div class="pp_fade"><a href="#" class="pp_expand" title="Expand the image">Expand</a><div class="pp_hoverContainer"><a class="pp_next" href="#">next</a><a class="pp_previous" href="#">previous</a></div><div id="pp_full_res"></div><div class="pp_details clearfix"><p class="pp_description"></p><a class="pp_close" href="#">Close</a><div class="pp_nav"><a href="#" class="pp_arrow_previous">Previous</a><p class="currentTextHolder">0/0</p><a href="#" class="pp_arrow_next">Next</a></div></div></div></div></div></div></div><div class="pp_bottom"><div class="pp_left"></div><div class="pp_middle"></div><div class="pp_right"></div></div></div><div class="pp_overlay"></div>',gallery_markup:'<div class="pp_gallery"><a href="#" class="pp_arrow_previous">Previous</a><ul>{gallery}</ul><a href="#" class="pp_arrow_next">Next</a></div>',image_markup:'<img id="fullResImage" src="" />',flash_markup:'<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="{width}" height="{height}"><param name="wmode" value="{wmode}" /><param name="allowfullscreen" value="true" /><param name="allowscriptaccess" value="always" /><param name="movie" value="{path}" /><embed src="{path}" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="{width}" height="{height}" wmode="{wmode}"></embed></object>',quicktime_markup:'<object classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab" height="{height}" width="{width}"><param name="src" value="{path}"><param name="autoplay" value="{autoplay}"><param name="type" value="video/quicktime"><embed src="{path}" height="{height}" width="{width}" autoplay="{autoplay}" type="video/quicktime" pluginspage="http://www.apple.com/quicktime/download/"></embed></object>',iframe_markup:'<iframe src ="{path}" width="{width}" height="{height}" frameborder="no"></iframe>',inline_markup:'<div class="pp_inline clearfix">{content}</div>',custom_markup:''},pp_settings);var matchedObjects=this,percentBased=false,correctSizes,pp_open,pp_contentHeight,pp_contentWidth,pp_containerHeight,pp_containerWidth,windowHeight=$(window).height(),windowWidth=$(window).width(),pp_slideshow;doresize=true,scroll_pos=_get_scroll();$(window).unbind('resize').resize(function(){_center_overlay();_resize_overlay();});if(pp_settings.keyboard_shortcuts){$(document).unbind('keydown').keydown(function(e){if(typeof $pp_pic_holder!='undefined'){if($pp_pic_holder.is(':visible')){switch(e.keyCode){case 37:$.prettyPhoto.changePage('previous');break;case 39:$.prettyPhoto.changePage('next');break;case 27:if(!settings.modal)
$.prettyPhoto.close();break;};return false;};};});}
$.prettyPhoto.initialize=function(){settings=pp_settings;if($.browser.msie&&parseInt($.browser.version)==6)settings.theme="light_square";_buildOverlay(this);if(settings.allow_resize)
$(window).scroll(function(){_center_overlay();});_center_overlay();set_position=jQuery.inArray($(this).attr('href'),pp_images);$.prettyPhoto.open();return false;}
$.prettyPhoto.open=function(){if(typeof settings=="undefined"){settings=pp_settings;if($.browser.msie&&$.browser.version==6)settings.theme="light_square";_buildOverlay(this);pp_images=$.makeArray(arguments[0]);pp_titles=(arguments[1])?$.makeArray(arguments[1]):$.makeArray("");pp_descriptions=(arguments[2])?$.makeArray(arguments[2]):$.makeArray("");isSet=(pp_images.length>1)?true:false;set_position=0;}
if($.browser.msie&&$.browser.version==6)$('select').css('visibility','hidden');if(settings.hideflash)$('object,embed').css('visibility','hidden');_checkPosition($(pp_images).size());$('.pp_loaderIcon').show();if($ppt.is(':hidden'))$ppt.css('opacity',0).show();$pp_overlay.show().fadeTo(settings.animation_speed,settings.opacity);$pp_pic_holder.find('.currentTextHolder').text((set_position+1)+settings.counter_separator_label+$(pp_images).size());$pp_pic_holder.find('.pp_description').show().html(unescape(pp_descriptions[set_position]));(settings.show_title&&pp_titles[set_position]!="")?$ppt.html(unescape(pp_titles[set_position])):$ppt.html('&nbsp;');movie_width=(parseFloat(grab_param('width',pp_images[set_position])))?grab_param('width',pp_images[set_position]):settings.default_width.toString();movie_height=(parseFloat(grab_param('height',pp_images[set_position])))?grab_param('height',pp_images[set_position]):settings.default_height.toString();if(movie_width.indexOf('%')!=-1||movie_height.indexOf('%')!=-1){movie_height=parseFloat(($(window).height()*parseFloat(movie_height)/100)-150);movie_width=parseFloat(($(window).width()*parseFloat(movie_width)/100)-150);percentBased=true;}else{percentBased=false;}
$pp_pic_holder.fadeIn(function(){imgPreloader="";switch(_getFileType(pp_images[set_position])){case'image':imgPreloader=new Image();nextImage=new Image();if(isSet&&set_position>$(pp_images).size())nextImage.src=pp_images[set_position+1];prevImage=new Image();if(isSet&&pp_images[set_position-1])prevImage.src=pp_images[set_position-1];$pp_pic_holder.find('#pp_full_res')[0].innerHTML=settings.image_markup;$pp_pic_holder.find('#fullResImage').attr('src',pp_images[set_position]);imgPreloader.onload=function(){correctSizes=_fitToViewport(imgPreloader.width,imgPreloader.height);_showContent();};imgPreloader.onerror=function(){alert('Image cannot be loaded. Make sure the path is correct and image exist.');$.prettyPhoto.close();};imgPreloader.src=pp_images[set_position];break;case'youtube':correctSizes=_fitToViewport(movie_width,movie_height);movie='http://www.youtube.com/v/'+grab_param('v',pp_images[set_position]);if(settings.autoplay)movie+="&autoplay=1";toInject=settings.flash_markup.replace(/{width}/g,correctSizes['width']).replace(/{height}/g,correctSizes['height']).replace(/{wmode}/g,settings.wmode).replace(/{path}/g,movie);break;case'vimeo':correctSizes=_fitToViewport(movie_width,movie_height);movie_id=pp_images[set_position];var regExp=/http:\/\/(www\.)?vimeo.com\/(\d+)/;var match=movie_id.match(regExp);movie='http://player.vimeo.com/video/'+match[2]+'?title=0&amp;byline=0&amp;portrait=0';if(settings.autoplay)movie+="&autoplay=1;";vimeo_width=correctSizes['width']+'/embed/?moog_width='+correctSizes['width'];toInject=settings.iframe_markup.replace(/{width}/g,vimeo_width).replace(/{height}/g,correctSizes['height']).replace(/{path}/g,movie);break;case'quicktime':correctSizes=_fitToViewport(movie_width,movie_height);correctSizes['height']+=15;correctSizes['contentHeight']+=15;correctSizes['containerHeight']+=15;toInject=settings.quicktime_markup.replace(/{width}/g,correctSizes['width']).replace(/{height}/g,correctSizes['height']).replace(/{wmode}/g,settings.wmode).replace(/{path}/g,pp_images[set_position]).replace(/{autoplay}/g,settings.autoplay);break;case'flash':correctSizes=_fitToViewport(movie_width,movie_height);flash_vars=pp_images[set_position];flash_vars=flash_vars.substring(pp_images[set_position].indexOf('flashvars')+10,pp_images[set_position].length);filename=pp_images[set_position];filename=filename.substring(0,filename.indexOf('?'));toInject=settings.flash_markup.replace(/{width}/g,correctSizes['width']).replace(/{height}/g,correctSizes['height']).replace(/{wmode}/g,settings.wmode).replace(/{path}/g,filename+'?'+flash_vars);break;case'iframe':correctSizes=_fitToViewport(movie_width,movie_height);frame_url=pp_images[set_position];frame_url=frame_url.substr(0,frame_url.indexOf('iframe')-1);toInject=settings.iframe_markup.replace(/{width}/g,correctSizes['width']).replace(/{height}/g,correctSizes['height']).replace(/{path}/g,frame_url);break;case'custom':correctSizes=_fitToViewport(movie_width,movie_height);toInject=settings.custom_markup;break;case'inline':myClone=$(pp_images[set_position]).clone().css({'width':settings.default_width}).wrapInner('<div id="pp_full_res"><div class="pp_inline clearfix"></div></div>').appendTo($('body'));correctSizes=_fitToViewport($(myClone).width(),$(myClone).height());$(myClone).remove();toInject=settings.inline_markup.replace(/{content}/g,$(pp_images[set_position]).html());break;};if(!imgPreloader){$pp_pic_holder.find('#pp_full_res')[0].innerHTML=toInject;_showContent();};});return false;};$.prettyPhoto.changePage=function(direction){currentGalleryPage=0;if(direction=='previous'){set_position--;if(set_position<0){set_position=0;return;};}else if(direction=='next'){set_position++;if(set_position>$(pp_images).size()-1){set_position=0;}}else{set_position=direction;};if(!doresize)doresize=true;$('.pp_contract').removeClass('pp_contract').addClass('pp_expand');_hideContent(function(){$.prettyPhoto.open();});};$.prettyPhoto.changeGalleryPage=function(direction){if(direction=='next'){currentGalleryPage++;if(currentGalleryPage>totalPage){currentGalleryPage=0;};}else if(direction=='previous'){currentGalleryPage--;if(currentGalleryPage<0){currentGalleryPage=totalPage;};}else{currentGalleryPage=direction;};itemsToSlide=(currentGalleryPage==totalPage)?pp_images.length-((totalPage)*itemsPerPage):itemsPerPage;$pp_pic_holder.find('.pp_gallery li').each(function(i){$(this).animate({'left':(i*itemWidth)-((itemsToSlide*itemWidth)*currentGalleryPage)});});};$.prettyPhoto.startSlideshow=function(){if(typeof pp_slideshow=='undefined'){$pp_pic_holder.find('.pp_play').unbind('click').removeClass('pp_play').addClass('pp_pause').click(function(){$.prettyPhoto.stopSlideshow();return false;});pp_slideshow=setInterval($.prettyPhoto.startSlideshow,settings.slideshow);}else{$.prettyPhoto.changePage('next');};}
$.prettyPhoto.stopSlideshow=function(){$pp_pic_holder.find('.pp_pause').unbind('click').removeClass('pp_pause').addClass('pp_play').click(function(){$.prettyPhoto.startSlideshow();return false;});clearInterval(pp_slideshow);pp_slideshow=undefined;}
$.prettyPhoto.close=function(){clearInterval(pp_slideshow);$pp_pic_holder.stop().find('object,embed').css('visibility','hidden');$('div.pp_pic_holder,div.ppt,.pp_fade').fadeOut(settings.animation_speed,function(){$(this).remove();});$pp_overlay.fadeOut(settings.animation_speed,function(){if($.browser.msie&&$.browser.version==6)$('select').css('visibility','visible');if(settings.hideflash)$('object,embed').css('visibility','visible');$(this).remove();$(window).unbind('scroll');settings.callback();doresize=true;pp_open=false;delete settings;});};_showContent=function(){$('.pp_loaderIcon').hide();$ppt.fadeTo(settings.animation_speed,1);projectedTop=scroll_pos['scrollTop']+((windowHeight/2)-(correctSizes['containerHeight']/2));if(projectedTop<0)projectedTop=0;$pp_pic_holder.find('.pp_content').animate({'height':correctSizes['contentHeight']},settings.animation_speed);$pp_pic_holder.animate({'top':projectedTop,'left':(windowWidth/2)-(correctSizes['containerWidth']/2),'width':correctSizes['containerWidth']},settings.animation_speed,function(){$pp_pic_holder.find('.pp_hoverContainer,#fullResImage').height(correctSizes['height']).width(correctSizes['width']);$pp_pic_holder.find('.pp_fade').fadeIn(settings.animation_speed);if(isSet&&_getFileType(pp_images[set_position])=="image"){$pp_pic_holder.find('.pp_hoverContainer').show();}else{$pp_pic_holder.find('.pp_hoverContainer').hide();}
if(correctSizes['resized'])$('a.pp_expand,a.pp_contract').fadeIn(settings.animation_speed);if(settings.autoplay_slideshow&&!pp_slideshow&&!pp_open)$.prettyPhoto.startSlideshow();settings.changepicturecallback();pp_open=true;});_insert_gallery();};function _hideContent(callback){$pp_pic_holder.find('#pp_full_res object,#pp_full_res embed').css('visibility','hidden');$pp_pic_holder.find('.pp_fade').fadeOut(settings.animation_speed,function(){$('.pp_loaderIcon').show();callback();});};function _checkPosition(setCount){if(set_position==setCount-1){$pp_pic_holder.find('a.pp_next').css('visibility','hidden');$pp_pic_holder.find('a.pp_next').addClass('disabled').unbind('click');}else{$pp_pic_holder.find('a.pp_next').css('visibility','visible');$pp_pic_holder.find('a.pp_next.disabled').removeClass('disabled').bind('click',function(){$.prettyPhoto.changePage('next');return false;});};if(set_position==0){$pp_pic_holder.find('a.pp_previous').css('visibility','hidden').addClass('disabled').unbind('click');}else{$pp_pic_holder.find('a.pp_previous.disabled').css('visibility','visible').removeClass('disabled').bind('click',function(){$.prettyPhoto.changePage('previous');return false;});};(setCount>1)?$('.pp_nav').show():$('.pp_nav').hide();};function _fitToViewport(width,height){resized=false;_getDimensions(width,height);imageWidth=width,imageHeight=height;if(((pp_containerWidth>windowWidth)||(pp_containerHeight>windowHeight))&&doresize&&settings.allow_resize&&!percentBased){resized=true,fitting=false;while(!fitting){if((pp_containerWidth>windowWidth)){imageWidth=(windowWidth-200);imageHeight=(height/width)*imageWidth;}else if((pp_containerHeight>windowHeight)){imageHeight=(windowHeight-200);imageWidth=(width/height)*imageHeight;}else{fitting=true;};pp_containerHeight=imageHeight,pp_containerWidth=imageWidth;};_getDimensions(imageWidth,imageHeight);};return{width:Math.floor(imageWidth),height:Math.floor(imageHeight),containerHeight:Math.floor(pp_containerHeight),containerWidth:Math.floor(pp_containerWidth)+40,contentHeight:Math.floor(pp_contentHeight),contentWidth:Math.floor(pp_contentWidth),resized:resized};};function _getDimensions(width,height){width=parseFloat(width);height=parseFloat(height);$pp_details=$pp_pic_holder.find('.pp_details');$pp_details.width(width);detailsHeight=parseFloat($pp_details.css('marginTop'))+parseFloat($pp_details.css('marginBottom'));$pp_details=$pp_details.clone().appendTo($('body')).css({'position':'absolute','top':-10000});detailsHeight+=$pp_details.height();detailsHeight=(detailsHeight<=34)?36:detailsHeight;if($.browser.msie&&$.browser.version==7)detailsHeight+=8;$pp_details.remove();pp_contentHeight=height+detailsHeight;pp_contentWidth=width;pp_containerHeight=pp_contentHeight+$ppt.height()+$pp_pic_holder.find('.pp_top').height()+$pp_pic_holder.find('.pp_bottom').height();pp_containerWidth=width;}
function _getFileType(itemSrc){if(itemSrc.match(/youtube\.com\/watch/i)){return'youtube';}else if(itemSrc.match(/vimeo\.com/i)){return'vimeo';}else if(itemSrc.indexOf('.mov')!=-1){return'quicktime';}else if(itemSrc.indexOf('.swf')!=-1){return'flash';}else if(itemSrc.indexOf('iframe')!=-1){return'iframe';}else if(itemSrc.indexOf('custom')!=-1){return'custom';}else if(itemSrc.substr(0,1)=='#'){return'inline';}else{return'image';};};function _center_overlay(){if(doresize&&typeof $pp_pic_holder!='undefined'){scroll_pos=_get_scroll();titleHeight=$ppt.height(),contentHeight=$pp_pic_holder.height(),contentwidth=$pp_pic_holder.width();projectedTop=(windowHeight/2)+scroll_pos['scrollTop']-(contentHeight/2);$pp_pic_holder.css({'top':projectedTop,'left':(windowWidth/2)+scroll_pos['scrollLeft']-(contentwidth/2)});};};function _get_scroll(){if(self.pageYOffset){return{scrollTop:self.pageYOffset,scrollLeft:self.pageXOffset};}else if(document.documentElement&&document.documentElement.scrollTop){return{scrollTop:document.documentElement.scrollTop,scrollLeft:document.documentElement.scrollLeft};}else if(document.body){return{scrollTop:document.body.scrollTop,scrollLeft:document.body.scrollLeft};};};function _resize_overlay(){windowHeight=$(window).height(),windowWidth=$(window).width();if(typeof $pp_overlay!="undefined")$pp_overlay.height($(document).height());};function _insert_gallery(){if(isSet&&settings.overlay_gallery&&_getFileType(pp_images[set_position])=="image"){itemWidth=52+5;navWidth=(settings.theme=="facebook")?58:38;itemsPerPage=Math.floor((correctSizes['containerWidth']-100-navWidth)/itemWidth);itemsPerPage=(itemsPerPage<pp_images.length)?itemsPerPage:pp_images.length;totalPage=Math.ceil(pp_images.length/itemsPerPage)-1;if(totalPage==0){navWidth=0;$pp_pic_holder.find('.pp_gallery .pp_arrow_next,.pp_gallery .pp_arrow_previous').hide();}else{$pp_pic_holder.find('.pp_gallery .pp_arrow_next,.pp_gallery .pp_arrow_previous').show();};galleryWidth=itemsPerPage*itemWidth+navWidth;$pp_pic_holder.find('.pp_gallery').width(galleryWidth).css('margin-left',-(galleryWidth/2));$pp_pic_holder.find('.pp_gallery ul').width(itemsPerPage*itemWidth).find('li.selected').removeClass('selected');goToPage=(Math.floor(set_position/itemsPerPage)<=totalPage)?Math.floor(set_position/itemsPerPage):totalPage;if(itemsPerPage){$pp_pic_holder.find('.pp_gallery').hide().show().removeClass('disabled');}else{$pp_pic_holder.find('.pp_gallery').hide().addClass('disabled');}
$.prettyPhoto.changeGalleryPage(goToPage);$pp_pic_holder.find('.pp_gallery ul li:eq('+set_position+')').addClass('selected');}else{$pp_pic_holder.find('.pp_content').unbind('mouseenter mouseleave');$pp_pic_holder.find('.pp_gallery').hide();}}
function _buildOverlay(caller){theRel=$(caller).attr('rel');galleryRegExp=/\[(?:.*)\]/;isSet=(galleryRegExp.exec(theRel))?true:false;pp_images=(isSet)?jQuery.map(matchedObjects,function(n,i){if($(n).attr('rel').indexOf(theRel)!=-1)return $(n).attr('href');}):$.makeArray($(caller).attr('href'));pp_titles=(isSet)?jQuery.map(matchedObjects,function(n,i){if($(n).attr('rel').indexOf(theRel)!=-1)return($(n).find('img').attr('alt'))?$(n).find('img').attr('alt'):"";}):$.makeArray($(caller).find('img').attr('alt'));pp_descriptions=(isSet)?jQuery.map(matchedObjects,function(n,i){if($(n).attr('rel').indexOf(theRel)!=-1)return($(n).attr('title'))?$(n).attr('title'):"";}):$.makeArray($(caller).attr('title'));$('body').append(settings.markup);$pp_pic_holder=$('.pp_pic_holder'),$ppt=$('.ppt'),$pp_overlay=$('div.pp_overlay');if(isSet&&settings.overlay_gallery){currentGalleryPage=0;toInject="";for(var i=0;i<pp_images.length;i++){var regex=new RegExp("(.*?)\.(jpg|jpeg|png|gif)$");var results=regex.exec(pp_images[i]);if(!results){classname='default';}else{classname='';}
toInject+="<li class='"+classname+"'><a href='#'><img src='"+pp_images[i]+"' width='50' alt='' /></a></li>";};toInject=settings.gallery_markup.replace(/{gallery}/g,toInject);$pp_pic_holder.find('#pp_full_res').after(toInject);$pp_pic_holder.find('.pp_gallery .pp_arrow_next').click(function(){$.prettyPhoto.changeGalleryPage('next');$.prettyPhoto.stopSlideshow();return false;});$pp_pic_holder.find('.pp_gallery .pp_arrow_previous').click(function(){$.prettyPhoto.changeGalleryPage('previous');$.prettyPhoto.stopSlideshow();return false;});$pp_pic_holder.find('.pp_content').hover(function(){$pp_pic_holder.find('.pp_gallery:not(.disabled)').fadeIn();},function(){$pp_pic_holder.find('.pp_gallery:not(.disabled)').fadeOut();});itemWidth=52+5;$pp_pic_holder.find('.pp_gallery ul li').each(function(i){$(this).css({'position':'absolute','left':i*itemWidth});$(this).find('a').unbind('click').click(function(){$.prettyPhoto.changePage(i);$.prettyPhoto.stopSlideshow();return false;});});};if(settings.slideshow){$pp_pic_holder.find('.pp_nav').prepend('<a href="#" class="pp_play">Play</a>')
$pp_pic_holder.find('.pp_nav .pp_play').click(function(){$.prettyPhoto.startSlideshow();return false;});}
$pp_pic_holder.attr('class','pp_pic_holder '+settings.theme);$pp_overlay.css({'opacity':0,'height':$(document).height(),'width':$(document).width()}).bind('click',function(){if(!settings.modal)$.prettyPhoto.close();});$('a.pp_close').bind('click',function(){$.prettyPhoto.close();return false;});$('a.pp_expand').bind('click',function(e){if($(this).hasClass('pp_expand')){$(this).removeClass('pp_expand').addClass('pp_contract');doresize=false;}else{$(this).removeClass('pp_contract').addClass('pp_expand');doresize=true;};_hideContent(function(){$.prettyPhoto.open();});return false;});$pp_pic_holder.find('.pp_previous, .pp_nav .pp_arrow_previous').bind('click',function(){$.prettyPhoto.changePage('previous');$.prettyPhoto.stopSlideshow();return false;});$pp_pic_holder.find('.pp_next, .pp_nav .pp_arrow_next').bind('click',function(){$.prettyPhoto.changePage('next');$.prettyPhoto.stopSlideshow();return false;});_center_overlay();};return this.unbind('click').click($.prettyPhoto.initialize);};function grab_param(name,url){name=name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");var regexS="[\\?&]"+name+"=([^&#]*)";var regex=new RegExp(regexS);var results=regex.exec(url);return(results==null)?"":results[1];}})(jQuery);

/*
 * jQuery Cycle Plugin (with Transition Definitions)
 * Examples and documentation at: http://jquery.malsup.com/cycle/
 * Copyright (c) 2007-2010 M. Alsup
 * Version: 2.88 (08-JUN-2010)
 * Dual licensed under the MIT and GPL licenses.
 * http://jquery.malsup.com/license.html
 * Requires: jQuery v1.2.6 or later
 */
(function($){var ver="2.88";if($.support==undefined){$.support={opacity:!($.browser.msie)};}function debug(s){if($.fn.cycle.debug){log(s);}}function log(){if(window.console&&window.console.log){window.console.log("[cycle] "+Array.prototype.join.call(arguments," "));}}$.fn.cycle=function(options,arg2){var o={s:this.selector,c:this.context};if(this.length===0&&options!="stop"){if(!$.isReady&&o.s){log("DOM not ready, queuing slideshow");$(function(){$(o.s,o.c).cycle(options,arg2);});return this;}log("terminating; zero elements found by selector"+($.isReady?"":" (DOM not ready)"));return this;}return this.each(function(){var opts=handleArguments(this,options,arg2);if(opts===false){return;}opts.updateActivePagerLink=opts.updateActivePagerLink||$.fn.cycle.updateActivePagerLink;if(this.cycleTimeout){clearTimeout(this.cycleTimeout);}this.cycleTimeout=this.cyclePause=0;var $cont=$(this);var $slides=opts.slideExpr?$(opts.slideExpr,this):$cont.children();var els=$slides.get();if(els.length<2){log("terminating; too few slides: "+els.length);return;}var opts2=buildOptions($cont,$slides,els,opts,o);if(opts2===false){return;}var startTime=opts2.continuous?10:getTimeout(els[opts2.currSlide],els[opts2.nextSlide],opts2,!opts2.rev);if(startTime){startTime+=(opts2.delay||0);if(startTime<10){startTime=10;}debug("first timeout: "+startTime);this.cycleTimeout=setTimeout(function(){go(els,opts2,0,(!opts2.rev&&!opts.backwards));},startTime);}});};function handleArguments(cont,options,arg2){if(cont.cycleStop==undefined){cont.cycleStop=0;}if(options===undefined||options===null){options={};}if(options.constructor==String){switch(options){case"destroy":case"stop":var opts=$(cont).data("cycle.opts");if(!opts){return false;}cont.cycleStop++;if(cont.cycleTimeout){clearTimeout(cont.cycleTimeout);}cont.cycleTimeout=0;$(cont).removeData("cycle.opts");if(options=="destroy"){destroy(opts);}return false;case"toggle":cont.cyclePause=(cont.cyclePause===1)?0:1;checkInstantResume(cont.cyclePause,arg2,cont);return false;case"pause":cont.cyclePause=1;return false;case"resume":cont.cyclePause=0;checkInstantResume(false,arg2,cont);return false;case"prev":case"next":var opts=$(cont).data("cycle.opts");if(!opts){log('options not found, "prev/next" ignored');return false;}$.fn.cycle[options](opts);return false;default:options={fx:options};}return options;}else{if(options.constructor==Number){var num=options;options=$(cont).data("cycle.opts");if(!options){log("options not found, can not advance slide");return false;}if(num<0||num>=options.elements.length){log("invalid slide index: "+num);return false;}options.nextSlide=num;if(cont.cycleTimeout){clearTimeout(cont.cycleTimeout);cont.cycleTimeout=0;}if(typeof arg2=="string"){options.oneTimeFx=arg2;}go(options.elements,options,1,num>=options.currSlide);return false;}}return options;function checkInstantResume(isPaused,arg2,cont){if(!isPaused&&arg2===true){var options=$(cont).data("cycle.opts");if(!options){log("options not found, can not resume");return false;}if(cont.cycleTimeout){clearTimeout(cont.cycleTimeout);cont.cycleTimeout=0;}go(options.elements,options,1,(!opts.rev&&!opts.backwards));}}}function removeFilter(el,opts){if(!$.support.opacity&&opts.cleartype&&el.style.filter){try{el.style.removeAttribute("filter");}catch(smother){}}}function destroy(opts){if(opts.next){$(opts.next).unbind(opts.prevNextEvent);}if(opts.prev){$(opts.prev).unbind(opts.prevNextEvent);}if(opts.pager||opts.pagerAnchorBuilder){$.each(opts.pagerAnchors||[],function(){this.unbind().remove();});}opts.pagerAnchors=null;if(opts.destroy){opts.destroy(opts);}}function buildOptions($cont,$slides,els,options,o){var opts=$.extend({},$.fn.cycle.defaults,options||{},$.metadata?$cont.metadata():$.meta?$cont.data():{});if(opts.autostop){opts.countdown=opts.autostopCount||els.length;}var cont=$cont[0];$cont.data("cycle.opts",opts);opts.$cont=$cont;opts.stopCount=cont.cycleStop;opts.elements=els;opts.before=opts.before?[opts.before]:[];opts.after=opts.after?[opts.after]:[];opts.after.unshift(function(){opts.busy=0;});if(!$.support.opacity&&opts.cleartype){opts.after.push(function(){removeFilter(this,opts);});}if(opts.continuous){opts.after.push(function(){go(els,opts,0,(!opts.rev&&!opts.backwards));});}saveOriginalOpts(opts);if(!$.support.opacity&&opts.cleartype&&!opts.cleartypeNoBg){clearTypeFix($slides);}if($cont.css("position")=="static"){$cont.css("position","relative");}if(opts.width){$cont.width(opts.width);}if(opts.height&&opts.height!="auto"){$cont.height(opts.height);}if(opts.startingSlide){opts.startingSlide=parseInt(opts.startingSlide);}else{if(opts.backwards){opts.startingSlide=els.length-1;}}if(opts.random){opts.randomMap=[];for(var i=0;i<els.length;i++){opts.randomMap.push(i);}opts.randomMap.sort(function(a,b){return Math.random()-0.5;});opts.randomIndex=1;opts.startingSlide=opts.randomMap[1];}else{if(opts.startingSlide>=els.length){opts.startingSlide=0;}}opts.currSlide=opts.startingSlide||0;var first=opts.startingSlide;$slides.css({position:"absolute",top:0,left:0}).hide().each(function(i){var z;if(opts.backwards){z=first?i<=first?els.length+(i-first):first-i:els.length-i;}else{z=first?i>=first?els.length-(i-first):first-i:els.length-i;}$(this).css("z-index",z);});$(els[first]).css("opacity",1).show();removeFilter(els[first],opts);if(opts.fit&&opts.width){$slides.width(opts.width);}if(opts.fit&&opts.height&&opts.height!="auto"){$slides.height(opts.height);}var reshape=opts.containerResize&&!$cont.innerHeight();if(reshape){var maxw=0,maxh=0;for(var j=0;j<els.length;j++){var $e=$(els[j]),e=$e[0],w=$e.outerWidth(),h=$e.outerHeight();if(!w){w=e.offsetWidth||e.width||$e.attr("width");}if(!h){h=e.offsetHeight||e.height||$e.attr("height");}maxw=w>maxw?w:maxw;maxh=h>maxh?h:maxh;}if(maxw>0&&maxh>0){$cont.css({width:maxw+"px",height:maxh+"px"});}}if(opts.pause){$cont.hover(function(){this.cyclePause++;},function(){this.cyclePause--;});}if(supportMultiTransitions(opts)===false){return false;}var requeue=false;options.requeueAttempts=options.requeueAttempts||0;$slides.each(function(){var $el=$(this);this.cycleH=(opts.fit&&opts.height)?opts.height:($el.height()||this.offsetHeight||this.height||$el.attr("height")||0);this.cycleW=(opts.fit&&opts.width)?opts.width:($el.width()||this.offsetWidth||this.width||$el.attr("width")||0);if($el.is("img")){var loadingIE=($.browser.msie&&this.cycleW==28&&this.cycleH==30&&!this.complete);var loadingFF=($.browser.mozilla&&this.cycleW==34&&this.cycleH==19&&!this.complete);var loadingOp=($.browser.opera&&((this.cycleW==42&&this.cycleH==19)||(this.cycleW==37&&this.cycleH==17))&&!this.complete);var loadingOther=(this.cycleH==0&&this.cycleW==0&&!this.complete);if(loadingIE||loadingFF||loadingOp||loadingOther){if(o.s&&opts.requeueOnImageNotLoaded&&++options.requeueAttempts<100){log(options.requeueAttempts," - img slide not loaded, requeuing slideshow: ",this.src,this.cycleW,this.cycleH);setTimeout(function(){$(o.s,o.c).cycle(options);},opts.requeueTimeout);requeue=true;return false;}else{log("could not determine size of image: "+this.src,this.cycleW,this.cycleH);}}}return true;});if(requeue){return false;}opts.cssBefore=opts.cssBefore||{};opts.animIn=opts.animIn||{};opts.animOut=opts.animOut||{};$slides.not(":eq("+first+")").css(opts.cssBefore);if(opts.cssFirst){$($slides[first]).css(opts.cssFirst);}if(opts.timeout){opts.timeout=parseInt(opts.timeout);if(opts.speed.constructor==String){opts.speed=$.fx.speeds[opts.speed]||parseInt(opts.speed);}if(!opts.sync){opts.speed=opts.speed/2;}var buffer=opts.fx=="shuffle"?500:250;while((opts.timeout-opts.speed)<buffer){opts.timeout+=opts.speed;}}if(opts.easing){opts.easeIn=opts.easeOut=opts.easing;}if(!opts.speedIn){opts.speedIn=opts.speed;}if(!opts.speedOut){opts.speedOut=opts.speed;}opts.slideCount=els.length;opts.currSlide=opts.lastSlide=first;if(opts.random){if(++opts.randomIndex==els.length){opts.randomIndex=0;}opts.nextSlide=opts.randomMap[opts.randomIndex];}else{if(opts.backwards){opts.nextSlide=opts.startingSlide==0?(els.length-1):opts.startingSlide-1;}else{opts.nextSlide=opts.startingSlide>=(els.length-1)?0:opts.startingSlide+1;}}if(!opts.multiFx){var init=$.fn.cycle.transitions[opts.fx];if($.isFunction(init)){init($cont,$slides,opts);}else{if(opts.fx!="custom"&&!opts.multiFx){log("unknown transition: "+opts.fx,"; slideshow terminating");return false;}}}var e0=$slides[first];if(opts.before.length){opts.before[0].apply(e0,[e0,e0,opts,true]);}if(opts.after.length>1){opts.after[1].apply(e0,[e0,e0,opts,true]);}if(opts.next){$(opts.next).bind(opts.prevNextEvent,function(){return advance(opts,opts.rev?-1:1);});}if(opts.prev){$(opts.prev).bind(opts.prevNextEvent,function(){return advance(opts,opts.rev?1:-1);});}if(opts.pager||opts.pagerAnchorBuilder){buildPager(els,opts);}exposeAddSlide(opts,els);return opts;}function saveOriginalOpts(opts){opts.original={before:[],after:[]};opts.original.cssBefore=$.extend({},opts.cssBefore);opts.original.cssAfter=$.extend({},opts.cssAfter);opts.original.animIn=$.extend({},opts.animIn);opts.original.animOut=$.extend({},opts.animOut);$.each(opts.before,function(){opts.original.before.push(this);});$.each(opts.after,function(){opts.original.after.push(this);});}function supportMultiTransitions(opts){var i,tx,txs=$.fn.cycle.transitions;if(opts.fx.indexOf(",")>0){opts.multiFx=true;opts.fxs=opts.fx.replace(/\s*/g,"").split(",");for(i=0;i<opts.fxs.length;i++){var fx=opts.fxs[i];tx=txs[fx];if(!tx||!txs.hasOwnProperty(fx)||!$.isFunction(tx)){log("discarding unknown transition: ",fx);opts.fxs.splice(i,1);i--;}}if(!opts.fxs.length){log("No valid transitions named; slideshow terminating.");return false;}}else{if(opts.fx=="all"){opts.multiFx=true;opts.fxs=[];for(p in txs){tx=txs[p];if(txs.hasOwnProperty(p)&&$.isFunction(tx)){opts.fxs.push(p);}}}}if(opts.multiFx&&opts.randomizeEffects){var r1=Math.floor(Math.random()*20)+30;for(i=0;i<r1;i++){var r2=Math.floor(Math.random()*opts.fxs.length);opts.fxs.push(opts.fxs.splice(r2,1)[0]);}debug("randomized fx sequence: ",opts.fxs);}return true;}function exposeAddSlide(opts,els){opts.addSlide=function(newSlide,prepend){var $s=$(newSlide),s=$s[0];if(!opts.autostopCount){opts.countdown++;}els[prepend?"unshift":"push"](s);if(opts.els){opts.els[prepend?"unshift":"push"](s);}opts.slideCount=els.length;$s.css("position","absolute");$s[prepend?"prependTo":"appendTo"](opts.$cont);if(prepend){opts.currSlide++;opts.nextSlide++;}if(!$.support.opacity&&opts.cleartype&&!opts.cleartypeNoBg){clearTypeFix($s);}if(opts.fit&&opts.width){$s.width(opts.width);}if(opts.fit&&opts.height&&opts.height!="auto"){$slides.height(opts.height);}s.cycleH=(opts.fit&&opts.height)?opts.height:$s.height();s.cycleW=(opts.fit&&opts.width)?opts.width:$s.width();$s.css(opts.cssBefore);if(opts.pager||opts.pagerAnchorBuilder){$.fn.cycle.createPagerAnchor(els.length-1,s,$(opts.pager),els,opts);}if($.isFunction(opts.onAddSlide)){opts.onAddSlide($s);}else{$s.hide();}};}$.fn.cycle.resetState=function(opts,fx){fx=fx||opts.fx;opts.before=[];opts.after=[];opts.cssBefore=$.extend({},opts.original.cssBefore);opts.cssAfter=$.extend({},opts.original.cssAfter);opts.animIn=$.extend({},opts.original.animIn);opts.animOut=$.extend({},opts.original.animOut);opts.fxFn=null;$.each(opts.original.before,function(){opts.before.push(this);});$.each(opts.original.after,function(){opts.after.push(this);});var init=$.fn.cycle.transitions[fx];if($.isFunction(init)){init(opts.$cont,$(opts.elements),opts);}};function go(els,opts,manual,fwd){if(manual&&opts.busy&&opts.manualTrump){debug("manualTrump in go(), stopping active transition");$(els).stop(true,true);opts.busy=false;}if(opts.busy){debug("transition active, ignoring new tx request");return;}var p=opts.$cont[0],curr=els[opts.currSlide],next=els[opts.nextSlide];if(p.cycleStop!=opts.stopCount||p.cycleTimeout===0&&!manual){return;}if(!manual&&!p.cyclePause&&!opts.bounce&&((opts.autostop&&(--opts.countdown<=0))||(opts.nowrap&&!opts.random&&opts.nextSlide<opts.currSlide))){if(opts.end){opts.end(opts);}return;}var changed=false;if((manual||!p.cyclePause)&&(opts.nextSlide!=opts.currSlide)){changed=true;var fx=opts.fx;curr.cycleH=curr.cycleH||$(curr).height();curr.cycleW=curr.cycleW||$(curr).width();next.cycleH=next.cycleH||$(next).height();next.cycleW=next.cycleW||$(next).width();if(opts.multiFx){if(opts.lastFx==undefined||++opts.lastFx>=opts.fxs.length){opts.lastFx=0;}fx=opts.fxs[opts.lastFx];opts.currFx=fx;}if(opts.oneTimeFx){fx=opts.oneTimeFx;opts.oneTimeFx=null;}$.fn.cycle.resetState(opts,fx);if(opts.before.length){$.each(opts.before,function(i,o){if(p.cycleStop!=opts.stopCount){return;}o.apply(next,[curr,next,opts,fwd]);});}var after=function(){$.each(opts.after,function(i,o){if(p.cycleStop!=opts.stopCount){return;}o.apply(next,[curr,next,opts,fwd]);});};debug("tx firing; currSlide: "+opts.currSlide+"; nextSlide: "+opts.nextSlide);opts.busy=1;if(opts.fxFn){opts.fxFn(curr,next,opts,after,fwd,manual&&opts.fastOnEvent);}else{if($.isFunction($.fn.cycle[opts.fx])){$.fn.cycle[opts.fx](curr,next,opts,after,fwd,manual&&opts.fastOnEvent);}else{$.fn.cycle.custom(curr,next,opts,after,fwd,manual&&opts.fastOnEvent);}}}if(changed||opts.nextSlide==opts.currSlide){opts.lastSlide=opts.currSlide;if(opts.random){opts.currSlide=opts.nextSlide;if(++opts.randomIndex==els.length){opts.randomIndex=0;}opts.nextSlide=opts.randomMap[opts.randomIndex];if(opts.nextSlide==opts.currSlide){opts.nextSlide=(opts.currSlide==opts.slideCount-1)?0:opts.currSlide+1;}}else{if(opts.backwards){var roll=(opts.nextSlide-1)<0;if(roll&&opts.bounce){opts.backwards=!opts.backwards;opts.nextSlide=1;opts.currSlide=0;}else{opts.nextSlide=roll?(els.length-1):opts.nextSlide-1;opts.currSlide=roll?0:opts.nextSlide+1;}}else{var roll=(opts.nextSlide+1)==els.length;if(roll&&opts.bounce){opts.backwards=!opts.backwards;opts.nextSlide=els.length-2;opts.currSlide=els.length-1;}else{opts.nextSlide=roll?0:opts.nextSlide+1;opts.currSlide=roll?els.length-1:opts.nextSlide-1;}}}}if(changed&&opts.pager){opts.updateActivePagerLink(opts.pager,opts.currSlide,opts.activePagerClass);}var ms=0;if(opts.timeout&&!opts.continuous){ms=getTimeout(els[opts.currSlide],els[opts.nextSlide],opts,fwd);}else{if(opts.continuous&&p.cyclePause){ms=10;}}if(ms>0){p.cycleTimeout=setTimeout(function(){go(els,opts,0,(!opts.rev&&!opts.backwards));},ms);}}$.fn.cycle.updateActivePagerLink=function(pager,currSlide,clsName){$(pager).each(function(){$(this).children().removeClass(clsName).eq(currSlide).addClass(clsName);});};function getTimeout(curr,next,opts,fwd){if(opts.timeoutFn){var t=opts.timeoutFn.call(curr,curr,next,opts,fwd);while((t-opts.speed)<250){t+=opts.speed;}debug("calculated timeout: "+t+"; speed: "+opts.speed);if(t!==false){return t;}}return opts.timeout;}$.fn.cycle.next=function(opts){advance(opts,opts.rev?-1:1);};$.fn.cycle.prev=function(opts){advance(opts,opts.rev?1:-1);};function advance(opts,val){var els=opts.elements;var p=opts.$cont[0],timeout=p.cycleTimeout;if(timeout){clearTimeout(timeout);p.cycleTimeout=0;}if(opts.random&&val<0){opts.randomIndex--;if(--opts.randomIndex==-2){opts.randomIndex=els.length-2;}else{if(opts.randomIndex==-1){opts.randomIndex=els.length-1;}}opts.nextSlide=opts.randomMap[opts.randomIndex];}else{if(opts.random){opts.nextSlide=opts.randomMap[opts.randomIndex];}else{opts.nextSlide=opts.currSlide+val;if(opts.nextSlide<0){if(opts.nowrap){return false;}opts.nextSlide=els.length-1;}else{if(opts.nextSlide>=els.length){if(opts.nowrap){return false;}opts.nextSlide=0;}}}}var cb=opts.onPrevNextEvent||opts.prevNextClick;if($.isFunction(cb)){cb(val>0,opts.nextSlide,els[opts.nextSlide]);}go(els,opts,1,val>=0);return false;}function buildPager(els,opts){var $p=$(opts.pager);$.each(els,function(i,o){$.fn.cycle.createPagerAnchor(i,o,$p,els,opts);});opts.updateActivePagerLink(opts.pager,opts.startingSlide,opts.activePagerClass);}$.fn.cycle.createPagerAnchor=function(i,el,$p,els,opts){var a;if($.isFunction(opts.pagerAnchorBuilder)){a=opts.pagerAnchorBuilder(i,el);debug("pagerAnchorBuilder("+i+", el) returned: "+a);}else{a='<a href="#">'+(i+1)+"</a>";}if(!a){return;}var $a=$(a);if($a.parents("body").length===0){var arr=[];if($p.length>1){$p.each(function(){var $clone=$a.clone(true);$(this).append($clone);arr.push($clone[0]);});$a=$(arr);}else{$a.appendTo($p);}}opts.pagerAnchors=opts.pagerAnchors||[];opts.pagerAnchors.push($a);$a.bind(opts.pagerEvent,function(e){e.preventDefault();opts.nextSlide=i;var p=opts.$cont[0],timeout=p.cycleTimeout;if(timeout){clearTimeout(timeout);p.cycleTimeout=0;}var cb=opts.onPagerEvent||opts.pagerClick;if($.isFunction(cb)){cb(opts.nextSlide,els[opts.nextSlide]);}go(els,opts,1,opts.currSlide<i);});if(!/^click/.test(opts.pagerEvent)&&!opts.allowPagerClickBubble){$a.bind("click.cycle",function(){return false;});}if(opts.pauseOnPagerHover){$a.hover(function(){opts.$cont[0].cyclePause++;},function(){opts.$cont[0].cyclePause--;});}};$.fn.cycle.hopsFromLast=function(opts,fwd){var hops,l=opts.lastSlide,c=opts.currSlide;if(fwd){hops=c>l?c-l:opts.slideCount-l;}else{hops=c<l?l-c:l+opts.slideCount-c;}return hops;};function clearTypeFix($slides){debug("applying clearType background-color hack");function hex(s){s=parseInt(s).toString(16);return s.length<2?"0"+s:s;}function getBg(e){for(;e&&e.nodeName.toLowerCase()!="html";e=e.parentNode){var v=$.css(e,"background-color");if(v.indexOf("rgb")>=0){var rgb=v.match(/\d+/g);return"#"+hex(rgb[0])+hex(rgb[1])+hex(rgb[2]);}if(v&&v!="transparent"){return v;}}return"#ffffff";}$slides.each(function(){$(this).css("background-color",getBg(this));});}$.fn.cycle.commonReset=function(curr,next,opts,w,h,rev){$(opts.elements).not(curr).hide();opts.cssBefore.opacity=1;opts.cssBefore.display="block";if(w!==false&&next.cycleW>0){opts.cssBefore.width=next.cycleW;}if(h!==false&&next.cycleH>0){opts.cssBefore.height=next.cycleH;}opts.cssAfter=opts.cssAfter||{};opts.cssAfter.display="none";$(curr).css("zIndex",opts.slideCount+(rev===true?1:0));$(next).css("zIndex",opts.slideCount+(rev===true?0:1));};$.fn.cycle.custom=function(curr,next,opts,cb,fwd,speedOverride){var $l=$(curr),$n=$(next);var speedIn=opts.speedIn,speedOut=opts.speedOut,easeIn=opts.easeIn,easeOut=opts.easeOut;$n.css(opts.cssBefore);if(speedOverride){if(typeof speedOverride=="number"){speedIn=speedOut=speedOverride;}else{speedIn=speedOut=1;}easeIn=easeOut=null;}var fn=function(){$n.animate(opts.animIn,speedIn,easeIn,cb);};$l.animate(opts.animOut,speedOut,easeOut,function(){if(opts.cssAfter){$l.css(opts.cssAfter);}if(!opts.sync){fn();}});if(opts.sync){fn();}};$.fn.cycle.transitions={fade:function($cont,$slides,opts){$slides.not(":eq("+opts.currSlide+")").css("opacity",0);opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);opts.cssBefore.opacity=0;});opts.animIn={opacity:1};opts.animOut={opacity:0};opts.cssBefore={top:0,left:0};}};$.fn.cycle.ver=function(){return ver;};$.fn.cycle.defaults={fx:"fade",timeout:4000,timeoutFn:null,continuous:0,speed:1000,speedIn:null,speedOut:null,next:null,prev:null,onPrevNextEvent:null,prevNextEvent:"click.cycle",pager:null,onPagerEvent:null,pagerEvent:"click.cycle",allowPagerClickBubble:false,pagerAnchorBuilder:null,before:null,after:null,end:null,easing:null,easeIn:null,easeOut:null,shuffle:null,animIn:null,animOut:null,cssBefore:null,cssAfter:null,fxFn:null,height:"auto",startingSlide:0,sync:1,random:0,fit:0,containerResize:1,pause:0,pauseOnPagerHover:0,autostop:0,autostopCount:0,delay:0,slideExpr:null,cleartype:!$.support.opacity,cleartypeNoBg:false,nowrap:0,fastOnEvent:0,randomizeEffects:1,rev:0,manualTrump:true,requeueOnImageNotLoaded:true,requeueTimeout:250,activePagerClass:"activeSlide",updateActivePagerLink:null,backwards:false};})(jQuery);
/*
 * jQuery Cycle Plugin Transition Definitions
 * This script is a plugin for the jQuery Cycle Plugin
 * Examples and documentation at: http://malsup.com/jquery/cycle/
 * Copyright (c) 2007-2010 M. Alsup
 * Version:	 2.72
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 */
(function($){$.fn.cycle.transitions.none=function($cont,$slides,opts){opts.fxFn=function(curr,next,opts,after){$(next).show();$(curr).hide();after();};};$.fn.cycle.transitions.scrollUp=function($cont,$slides,opts){$cont.css("overflow","hidden");opts.before.push($.fn.cycle.commonReset);var h=$cont.height();opts.cssBefore={top:h,left:0};opts.cssFirst={top:0};opts.animIn={top:0};opts.animOut={top:-h};};$.fn.cycle.transitions.scrollDown=function($cont,$slides,opts){$cont.css("overflow","hidden");opts.before.push($.fn.cycle.commonReset);var h=$cont.height();opts.cssFirst={top:0};opts.cssBefore={top:-h,left:0};opts.animIn={top:0};opts.animOut={top:h};};$.fn.cycle.transitions.scrollLeft=function($cont,$slides,opts){$cont.css("overflow","hidden");opts.before.push($.fn.cycle.commonReset);var w=$cont.width();opts.cssFirst={left:0};opts.cssBefore={left:w,top:0};opts.animIn={left:0};opts.animOut={left:0-w};};$.fn.cycle.transitions.scrollRight=function($cont,$slides,opts){$cont.css("overflow","hidden");opts.before.push($.fn.cycle.commonReset);var w=$cont.width();opts.cssFirst={left:0};opts.cssBefore={left:-w,top:0};opts.animIn={left:0};opts.animOut={left:w};};$.fn.cycle.transitions.scrollHorz=function($cont,$slides,opts){$cont.css("overflow","hidden").width();opts.before.push(function(curr,next,opts,fwd){$.fn.cycle.commonReset(curr,next,opts);opts.cssBefore.left=fwd?(next.cycleW-1):(1-next.cycleW);opts.animOut.left=fwd?-curr.cycleW:curr.cycleW;});opts.cssFirst={left:0};opts.cssBefore={top:0};opts.animIn={left:0};opts.animOut={top:0};};$.fn.cycle.transitions.scrollVert=function($cont,$slides,opts){$cont.css("overflow","hidden");opts.before.push(function(curr,next,opts,fwd){$.fn.cycle.commonReset(curr,next,opts);opts.cssBefore.top=fwd?(1-next.cycleH):(next.cycleH-1);opts.animOut.top=fwd?curr.cycleH:-curr.cycleH;});opts.cssFirst={top:0};opts.cssBefore={left:0};opts.animIn={top:0};opts.animOut={left:0};};$.fn.cycle.transitions.slideX=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$(opts.elements).not(curr).hide();$.fn.cycle.commonReset(curr,next,opts,false,true);opts.animIn.width=next.cycleW;});opts.cssBefore={left:0,top:0,width:0};opts.animIn={width:"show"};opts.animOut={width:0};};$.fn.cycle.transitions.slideY=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$(opts.elements).not(curr).hide();$.fn.cycle.commonReset(curr,next,opts,true,false);opts.animIn.height=next.cycleH;});opts.cssBefore={left:0,top:0,height:0};opts.animIn={height:"show"};opts.animOut={height:0};};$.fn.cycle.transitions.shuffle=function($cont,$slides,opts){var i,w=$cont.css("overflow","visible").width();$slides.css({left:0,top:0});opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,true,true);});if(!opts.speedAdjusted){opts.speed=opts.speed/2;opts.speedAdjusted=true;}opts.random=0;opts.shuffle=opts.shuffle||{left:-w,top:15};opts.els=[];for(i=0;i<$slides.length;i++){opts.els.push($slides[i]);}for(i=0;i<opts.currSlide;i++){opts.els.push(opts.els.shift());}opts.fxFn=function(curr,next,opts,cb,fwd){var $el=fwd?$(curr):$(next);$(next).css(opts.cssBefore);var count=opts.slideCount;$el.animate(opts.shuffle,opts.speedIn,opts.easeIn,function(){var hops=$.fn.cycle.hopsFromLast(opts,fwd);for(var k=0;k<hops;k++){fwd?opts.els.push(opts.els.shift()):opts.els.unshift(opts.els.pop());}if(fwd){for(var i=0,len=opts.els.length;i<len;i++){$(opts.els[i]).css("z-index",len-i+count);}}else{var z=$(curr).css("z-index");$el.css("z-index",parseInt(z)+1+count);}$el.animate({left:0,top:0},opts.speedOut,opts.easeOut,function(){$(fwd?this:curr).hide();if(cb){cb();}});});};opts.cssBefore={display:"block",opacity:1,top:0,left:0};};$.fn.cycle.transitions.turnUp=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,false);opts.cssBefore.top=next.cycleH;opts.animIn.height=next.cycleH;});opts.cssFirst={top:0};opts.cssBefore={left:0,height:0};opts.animIn={top:0};opts.animOut={height:0};};$.fn.cycle.transitions.turnDown=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,false);opts.animIn.height=next.cycleH;opts.animOut.top=curr.cycleH;});opts.cssFirst={top:0};opts.cssBefore={left:0,top:0,height:0};opts.animOut={height:0};};$.fn.cycle.transitions.turnLeft=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,true);opts.cssBefore.left=next.cycleW;opts.animIn.width=next.cycleW;});opts.cssBefore={top:0,width:0};opts.animIn={left:0};opts.animOut={width:0};};$.fn.cycle.transitions.turnRight=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,true);opts.animIn.width=next.cycleW;opts.animOut.left=curr.cycleW;});opts.cssBefore={top:0,left:0,width:0};opts.animIn={left:0};opts.animOut={width:0};};$.fn.cycle.transitions.zoom=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,false,true);opts.cssBefore.top=next.cycleH/2;opts.cssBefore.left=next.cycleW/2;opts.animIn={top:0,left:0,width:next.cycleW,height:next.cycleH};opts.animOut={width:0,height:0,top:curr.cycleH/2,left:curr.cycleW/2};});opts.cssFirst={top:0,left:0};opts.cssBefore={width:0,height:0};};$.fn.cycle.transitions.fadeZoom=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,false);opts.cssBefore.left=next.cycleW/2;opts.cssBefore.top=next.cycleH/2;opts.animIn={top:0,left:0,width:next.cycleW,height:next.cycleH};});opts.cssBefore={width:0,height:0};opts.animOut={opacity:0};};$.fn.cycle.transitions.blindX=function($cont,$slides,opts){var w=$cont.css("overflow","hidden").width();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);opts.animIn.width=next.cycleW;opts.animOut.left=curr.cycleW;});opts.cssBefore={left:w,top:0};opts.animIn={left:0};opts.animOut={left:w};};$.fn.cycle.transitions.blindY=function($cont,$slides,opts){var h=$cont.css("overflow","hidden").height();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);opts.animIn.height=next.cycleH;opts.animOut.top=curr.cycleH;});opts.cssBefore={top:h,left:0};opts.animIn={top:0};opts.animOut={top:h};};$.fn.cycle.transitions.blindZ=function($cont,$slides,opts){var h=$cont.css("overflow","hidden").height();var w=$cont.width();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);opts.animIn.height=next.cycleH;opts.animOut.top=curr.cycleH;});opts.cssBefore={top:h,left:w};opts.animIn={top:0,left:0};opts.animOut={top:h,left:w};};$.fn.cycle.transitions.growX=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,true);opts.cssBefore.left=this.cycleW/2;opts.animIn={left:0,width:this.cycleW};opts.animOut={left:0};});opts.cssBefore={width:0,top:0};};$.fn.cycle.transitions.growY=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,false);opts.cssBefore.top=this.cycleH/2;opts.animIn={top:0,height:this.cycleH};opts.animOut={top:0};});opts.cssBefore={height:0,left:0};};$.fn.cycle.transitions.curtainX=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,true,true);opts.cssBefore.left=next.cycleW/2;opts.animIn={left:0,width:this.cycleW};opts.animOut={left:curr.cycleW/2,width:0};});opts.cssBefore={top:0,width:0};};$.fn.cycle.transitions.curtainY=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,false,true);opts.cssBefore.top=next.cycleH/2;opts.animIn={top:0,height:next.cycleH};opts.animOut={top:curr.cycleH/2,height:0};});opts.cssBefore={left:0,height:0};};$.fn.cycle.transitions.cover=function($cont,$slides,opts){var d=opts.direction||"left";var w=$cont.css("overflow","hidden").width();var h=$cont.height();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);if(d=="right"){opts.cssBefore.left=-w;}else{if(d=="up"){opts.cssBefore.top=h;}else{if(d=="down"){opts.cssBefore.top=-h;}else{opts.cssBefore.left=w;}}}});opts.animIn={left:0,top:0};opts.animOut={opacity:1};opts.cssBefore={top:0,left:0};};$.fn.cycle.transitions.uncover=function($cont,$slides,opts){var d=opts.direction||"left";var w=$cont.css("overflow","hidden").width();var h=$cont.height();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,true,true);if(d=="right"){opts.animOut.left=w;}else{if(d=="up"){opts.animOut.top=-h;}else{if(d=="down"){opts.animOut.top=h;}else{opts.animOut.left=-w;}}}});opts.animIn={left:0,top:0};opts.animOut={opacity:1};opts.cssBefore={top:0,left:0};};$.fn.cycle.transitions.toss=function($cont,$slides,opts){var w=$cont.css("overflow","visible").width();var h=$cont.height();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,true,true);if(!opts.animOut.left&&!opts.animOut.top){opts.animOut={left:w*2,top:-h/2,opacity:0};}else{opts.animOut.opacity=0;}});opts.cssBefore={left:0,top:0};opts.animIn={left:0};};$.fn.cycle.transitions.wipe=function($cont,$slides,opts){var w=$cont.css("overflow","hidden").width();var h=$cont.height();opts.cssBefore=opts.cssBefore||{};var clip;if(opts.clip){if(/l2r/.test(opts.clip)){clip="rect(0px 0px "+h+"px 0px)";}else{if(/r2l/.test(opts.clip)){clip="rect(0px "+w+"px "+h+"px "+w+"px)";}else{if(/t2b/.test(opts.clip)){clip="rect(0px "+w+"px 0px 0px)";}else{if(/b2t/.test(opts.clip)){clip="rect("+h+"px "+w+"px "+h+"px 0px)";}else{if(/zoom/.test(opts.clip)){var top=parseInt(h/2);var left=parseInt(w/2);clip="rect("+top+"px "+left+"px "+top+"px "+left+"px)";}}}}}}opts.cssBefore.clip=opts.cssBefore.clip||clip||"rect(0px 0px 0px 0px)";var d=opts.cssBefore.clip.match(/(\d+)/g);var t=parseInt(d[0]),r=parseInt(d[1]),b=parseInt(d[2]),l=parseInt(d[3]);opts.before.push(function(curr,next,opts){if(curr==next){return;}var $curr=$(curr),$next=$(next);$.fn.cycle.commonReset(curr,next,opts,true,true,false);opts.cssAfter.display="block";var step=1,count=parseInt((opts.speedIn/13))-1;(function f(){var tt=t?t-parseInt(step*(t/count)):0;var ll=l?l-parseInt(step*(l/count)):0;var bb=b<h?b+parseInt(step*((h-b)/count||1)):h;var rr=r<w?r+parseInt(step*((w-r)/count||1)):w;$next.css({clip:"rect("+tt+"px "+rr+"px "+bb+"px "+ll+"px)"});(step++<=count)?setTimeout(f,13):$curr.css("display","none");})();});opts.cssBefore={display:"block",opacity:1,top:0,left:0};opts.animIn={left:0};opts.animOut={left:0};};})(jQuery);

/**
 * jQuery.ScrollTo - Easy element scrolling using jQuery.
 * Copyright (c) 2007-2009 Ariel Flesler - aflesler(at)gmail(dot)com | http://flesler.blogspot.com
 * Dual licensed under MIT and GPL.
 * Date: 5/25/2009
 * @author Ariel Flesler
 * @version 1.4.2
 *
 * http://flesler.blogspot.com/2007/10/jqueryscrollto.html
 */
;(function(d){var k=d.scrollTo=function(a,i,e){d(window).scrollTo(a,i,e)};k.defaults={axis:'xy',duration:parseFloat(d.fn.jquery)>=1.3?0:1};k.window=function(a){return d(window)._scrollable()};d.fn._scrollable=function(){return this.map(function(){var a=this,i=!a.nodeName||d.inArray(a.nodeName.toLowerCase(),['iframe','#document','html','body'])!=-1;if(!i)return a;var e=(a.contentWindow||a).document||a.ownerDocument||a;return d.browser.safari||e.compatMode=='BackCompat'?e.body:e.documentElement})};d.fn.scrollTo=function(n,j,b){if(typeof j=='object'){b=j;j=0}if(typeof b=='function')b={onAfter:b};if(n=='max')n=9e9;b=d.extend({},k.defaults,b);j=j||b.speed||b.duration;b.queue=b.queue&&b.axis.length>1;if(b.queue)j/=2;b.offset=p(b.offset);b.over=p(b.over);return this._scrollable().each(function(){var q=this,r=d(q),f=n,s,g={},u=r.is('html,body');switch(typeof f){case'number':case'string':if(/^([+-]=)?\d+(\.\d+)?(px|%)?$/.test(f)){f=p(f);break}f=d(f,this);case'object':if(f.is||f.style)s=(f=d(f)).offset()}d.each(b.axis.split(''),function(a,i){var e=i=='x'?'Left':'Top',h=e.toLowerCase(),c='scroll'+e,l=q[c],m=k.max(q,i);if(s){g[c]=s[h]+(u?0:l-r.offset()[h]);if(b.margin){g[c]-=parseInt(f.css('margin'+e))||0;g[c]-=parseInt(f.css('border'+e+'Width'))||0}g[c]+=b.offset[h]||0;if(b.over[h])g[c]+=f[i=='x'?'width':'height']()*b.over[h]}else{var o=f[h];g[c]=o.slice&&o.slice(-1)=='%'?parseFloat(o)/100*m:o}if(/^\d+$/.test(g[c]))g[c]=g[c]<=0?0:Math.min(g[c],m);if(!a&&b.queue){if(l!=g[c])t(b.onAfterFirst);delete g[c]}});t(b.onAfter);function t(a){r.animate(g,j,b.easing,a&&function(){a.call(this,n,b)})}}).end()};k.max=function(a,i){var e=i=='x'?'Width':'Height',h='scroll'+e;if(!d(a).is('html,body'))return a[h]-d(a)[e.toLowerCase()]();var c='client'+e,l=a.ownerDocument.documentElement,m=a.ownerDocument.body;return Math.max(l[h],m[h])-Math.min(l[c],m[c])};function p(a){return typeof a=='object'?a:{top:a,left:a}}})(jQuery);

/**
 * Coin Slider - Unique jQuery Image Slider
 * @version: 1.0 - (2010/04/04)
 * @requires jQuery v1.2.2 or later 
 * @author Ivan Lazarevic
 * Examples and documentation at: http://workshop.rs/projects/coin-slider/
 
 * Licensed under MIT licence:
 *   http://www.opensource.org/licenses/mit-license.php
**/

(function($){var params=new Array;var order=new Array;var images=new Array;var links=new Array;var linksTarget=new Array;var titles=new Array;var interval=new Array;var imagePos=new Array;var appInterval=new Array;var squarePos=new Array;var reverse=new Array;$.fn.coinslider=$.fn.CoinSlider=function(options){init=function(el){order[el.id]=new Array();images[el.id]=new Array();links[el.id]=new Array();linksTarget[el.id]=new Array();titles[el.id]=new Array();imagePos[el.id]=0;squarePos[el.id]=0;reverse[el.id]=1;params[el.id]=$.extend({},$.fn.coinslider.defaults,options);$.each($('#'+el.id+' img'),function(i,item){images[el.id][i]=$(item).attr('src');links[el.id][i]=$(item).parent().is('a')?$(item).parent().attr('href'):'';linksTarget[el.id][i]=$(item).parent().is('a')?$(item).parent().attr('target'):'';titles[el.id][i]=$(item).next().is('span')?$(item).next().html():'';$(item).hide();$(item).next().hide();});$(el).css({'background-image':'url('+images[el.id][0]+')','width':params[el.id].width,'height':params[el.id].height,'position':'relative','background-position':'top left'}).wrap("<div class='coin-slider' id='coin-slider-"+el.id+"' />");$('#'+el.id).append("<div class='cs-title' id='cs-title-"+el.id+"' style='position: absolute; bottom:0; left: 0; z-index: 1000;'></div>");$.setFields(el);if(params[el.id].navigation)
$.setNavigation(el);$.transition(el,0);$.transitionCall(el);}
$.setFields=function(el){tWidth=sWidth=parseInt(params[el.id].width/params[el.id].spw);tHeight=sHeight=parseInt(params[el.id].height/params[el.id].sph);counter=sLeft=sTop=0;tgapx=gapx=params[el.id].width-params[el.id].spw*sWidth;tgapy=gapy=params[el.id].height-params[el.id].sph*sHeight;for(i=1;i<=params[el.id].sph;i++){gapx=tgapx;if(gapy>0){gapy--;sHeight=tHeight+1;}else{sHeight=tHeight;}
for(j=1;j<=params[el.id].spw;j++){if(gapx>0){gapx--;sWidth=tWidth+1;}else{sWidth=tWidth;}
order[el.id][counter]=i+''+j;counter++;if(params[el.id].links)
$('#'+el.id).append("<a href='"+links[el.id][0]+"' class='cs-"+el.id+"' id='cs-"+el.id+i+j+"' style='width:"+sWidth+"px; height:"+sHeight+"px; float: left; position: absolute;'></a>");else
$('#'+el.id).append("<div class='cs-"+el.id+"' id='cs-"+el.id+i+j+"' style='width:"+sWidth+"px; height:"+sHeight+"px; float: left; position: absolute;'></div>");$("#cs-"+el.id+i+j).css({'background-position':-sLeft+'px '+(-sTop+'px'),'left':sLeft,'top':sTop});sLeft+=sWidth;}
sTop+=sHeight;sLeft=0;}
$('.cs-'+el.id).mouseover(function(){$('#cs-navigation-'+el.id).show();});$('.cs-'+el.id).mouseout(function(){$('#cs-navigation-'+el.id).hide();});$('#cs-title-'+el.id).mouseover(function(){$('#cs-navigation-'+el.id).show();});$('#cs-title-'+el.id).mouseout(function(){$('#cs-navigation-'+el.id).hide();});if(params[el.id].hoverPause){$('.cs-'+el.id).mouseover(function(){params[el.id].pause=true;});$('.cs-'+el.id).mouseout(function(){params[el.id].pause=false;});$('#cs-title-'+el.id).mouseover(function(){params[el.id].pause=true;});$('#cs-title-'+el.id).mouseout(function(){params[el.id].pause=false;});}};$.transitionCall=function(el){clearInterval(interval[el.id]);delay=params[el.id].delay+params[el.id].spw*params[el.id].sph*params[el.id].sDelay;interval[el.id]=setInterval(function(){$.transition(el)},delay);}
$.transition=function(el,direction){if(params[el.id].pause==true)return;$.effect(el);squarePos[el.id]=0;appInterval[el.id]=setInterval(function(){$.appereance(el,order[el.id][squarePos[el.id]])},params[el.id].sDelay);$(el).css({'background-image':'url('+images[el.id][imagePos[el.id]]+')'});if(typeof(direction)=="undefined")
imagePos[el.id]++;else
if(direction=='prev')
imagePos[el.id]--;else
imagePos[el.id]=direction;if(imagePos[el.id]==images[el.id].length){imagePos[el.id]=0;}
if(imagePos[el.id]==-1){imagePos[el.id]=images[el.id].length-1;}
$('.cs-button-'+el.id).removeClass('cs-active');$('#cs-button-'+el.id+"-"+(imagePos[el.id]+1)).addClass('cs-active');if(titles[el.id][imagePos[el.id]]){$('#cs-title-'+el.id).css({'opacity':0}).animate({'opacity':params[el.id].opacity},params[el.id].titleSpeed);$('#cs-title-'+el.id).html(titles[el.id][imagePos[el.id]]);}else{$('#cs-title-'+el.id).css('opacity',0);}};$.appereance=function(el,sid){$('.cs-'+el.id).attr('href',links[el.id][imagePos[el.id]]).attr('target',linksTarget[el.id][imagePos[el.id]]);if(squarePos[el.id]==params[el.id].spw*params[el.id].sph){clearInterval(appInterval[el.id]);return;}
$('#cs-'+el.id+sid).css({opacity:0,'background-image':'url('+images[el.id][imagePos[el.id]]+')'});$('#cs-'+el.id+sid).animate({opacity:1},300);squarePos[el.id]++;};$.setNavigation=function(el){$(el).append("<div id='cs-navigation-"+el.id+"'></div>");$('#cs-navigation-'+el.id).hide();$('#cs-navigation-'+el.id).append("<a href='#' id='cs-prev-"+el.id+"' class='cs-prev'>prev</a>");$('#cs-navigation-'+el.id).append("<a href='#' id='cs-next-"+el.id+"' class='cs-next'>next</a>");$('#cs-prev-'+el.id).css({'position':'absolute','top':params[el.id].height/2-15,'left':0,'z-index':1001,'line-height':'30px','opacity':params[el.id].opacity}).click(function(e){e.preventDefault();$.transition(el,'prev');$.transitionCall(el);}).mouseover(function(){$('#cs-navigation-'+el.id).show()});$('#cs-next-'+el.id).css({'position':'absolute','top':params[el.id].height/2-15,'right':0,'z-index':1001,'line-height':'30px','opacity':params[el.id].opacity}).click(function(e){e.preventDefault();$.transition(el);$.transitionCall(el);}).mouseover(function(){$('#cs-navigation-'+el.id).show()});$("<div id='cs-buttons-"+el.id+"' class='cs-buttons'></div>").appendTo($('#coin-slider-'+el.id));for(k=1;k<images[el.id].length+1;k++){$('#cs-buttons-'+el.id).append("<a href='#' class='cs-button-"+el.id+"' id='cs-button-"+el.id+"-"+k+"'>"+k+"</a>");}
$.each($('.cs-button-'+el.id),function(i,item){$(item).click(function(e){$('.cs-button-'+el.id).removeClass('cs-active');$(this).addClass('cs-active');e.preventDefault();$.transition(el,i);$.transitionCall(el);})});$('#cs-navigation-'+el.id+' a').mouseout(function(){$('#cs-navigation-'+el.id).hide();params[el.id].pause=false;});$("#cs-buttons-"+el.id).css({'left':'50%','margin-left':-images[el.id].length*15/2-5,'position':'relative'});}
$.effect=function(el){effA=['random','swirl','rain','straight'];if(params[el.id].effect=='')
eff=effA[Math.floor(Math.random()*(effA.length))];else
eff=params[el.id].effect;order[el.id]=new Array();if(eff=='random'){counter=0;for(i=1;i<=params[el.id].sph;i++){for(j=1;j<=params[el.id].spw;j++){order[el.id][counter]=i+''+j;counter++;}}
$.random(order[el.id]);}
if(eff=='rain'){$.rain(el);}
if(eff=='swirl')
$.swirl(el);if(eff=='straight')
$.straight(el);reverse[el.id]*=-1;if(reverse[el.id]>0){order[el.id].reverse();}}
$.random=function(arr){var i=arr.length;if(i==0)return false;while(--i){var j=Math.floor(Math.random()*(i+1));var tempi=arr[i];var tempj=arr[j];arr[i]=tempj;arr[j]=tempi;}}
$.swirl=function(el){var n=params[el.id].sph;var m=params[el.id].spw;var x=1;var y=1;var going=0;var num=0;var c=0;var dowhile=true;while(dowhile){num=(going==0||going==2)?m:n;for(i=1;i<=num;i++){order[el.id][c]=x+''+y;c++;if(i!=num){switch(going){case 0:y++;break;case 1:x++;break;case 2:y--;break;case 3:x--;break;}}}
going=(going+1)%4;switch(going){case 0:m--;y++;break;case 1:n--;x++;break;case 2:m--;y--;break;case 3:n--;x--;break;}
check=$.max(n,m)-$.min(n,m);if(m<=check&&n<=check)
dowhile=false;}}
$.rain=function(el){var n=params[el.id].sph;var m=params[el.id].spw;var c=0;var to=to2=from=1;var dowhile=true;while(dowhile){for(i=from;i<=to;i++){order[el.id][c]=i+''+parseInt(to2-i+1);c++;}
to2++;if(to<n&&to2<m&&n<m){to++;}
if(to<n&&n>=m){to++;}
if(to2>m){from++;}
if(from>to)dowhile=false;}}
$.straight=function(el){counter=0;for(i=1;i<=params[el.id].sph;i++){for(j=1;j<=params[el.id].spw;j++){order[el.id][counter]=i+''+j;counter++;}}}
$.min=function(n,m){if(n>m)return m;else return n;}
$.max=function(n,m){if(n<m)return m;else return n;}
this.each(function(){init(this);});};$.fn.coinslider.defaults={width:565,height:290,spw:7,sph:5,delay:3000,sDelay:30,opacity:0.7,titleSpeed:500,effect:'',navigation:true,links:true,hoverPause:true};})(jQuery);

/*
 * jQuery.validity v1.0.2
 * http://validity.thatscaptaintoyou.com/
 * http://code.google.com/p/validity/
 * 
 * Copyright (c) 2009 Wyatt Allen
 * Dual licensed under the MIT and GPL licenses.
 *
 * Date: 2009-10-12 (Monday, 12 October 2009)
 * Revision: 127
 */
(function($){var
defaults={outputMode:"label",scrollTo:false,modalErrorsClickable:true,defaultFieldName:"This field",elementSupport:":text, :password, textarea, select, :radio, :checkbox",argToString:function(val){return val.getDate?(val.getMonth()+1)+"/"+val.getDate()+"/"+val.getFullYear():val;}};$.validity={settings:$.extend(defaults,{}),patterns:{integer:/^\d+$/,date:/^([01]?\d)\/([012]?\d|30|31)\/\d{1,4}$/,email:/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,usd:/^\$?(\d{1,3},?(\d{3},?)*\d{3}(\.\d{0,2})?|\d{1,3}(\.\d{0,2})?|\.\d{1,2}?)$/,url:/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i,number:/^[+-]?(\d+(\.\d*)?|\.\d+)([Ee]\d+)?$/,zip:/^\d{5}(-\d{4})?$/,phone:/^[2-9]\d{2}-\d{3}-\d{4}$/,guid:/^(\{?([0-9a-fA-F]){8}-(([0-9a-fA-F]){4}-){3}([0-9a-fA-F]){12}\}?)$/,time12:/^[01]?\d:[0-5]\d?\s?[aApP]\.?[mM]\.?$/,time24:/^(20|21|22|23|[01]\d|\d)(([:][0-5]\d){1,2})$/,nonHtml:/^[^<>]*$/},messages:{require:"#{field} is required.",match:"#{field} is in an invalid format.",integer:"#{field} must be a positive, whole number.",date:"#{field} must be formatted as a date.",email:"#{field} must be formatted as an email.",usd:"#{field} must be formatted as a US Dollar amount.",url:"#{field} must be formatted as a URL.",number:"#{field} must be formatted as a number.",zip:"#{field} must be formatted as a zipcode ##### or #####-####.",phone:"#{field} must be formatted as a phone number ###-###-####.",guid:"#{field} must be formatted as a guid like {3F2504E0-4F89-11D3-9A0C-0305E82C3301}.",time24:"#{field} must be formatted as a 24 hour time: 23:00.",time12:"#{field} must be formatted as a 12 hour time: 12:00 AM/PM",lessThan:"#{field} must be less than #{max}.",lessThanOrEqualTo:"#{field} must be less than or equal to #{max}.",greaterThan:"#{field} must be greater than #{min}.",greaterThanOrEqualTo:"#{field} must be greater than or equal to #{min}.",range:"#{field} must be between #{min} and #{max}.",tooLong:"#{field} cannot be longer than #{max} characters.",tooShort:"#{field} cannot be shorter than #{min} characters.",equal:"Values don't match.",distinct:"A value was repeated.",sum:"Values don't add to #{sum}.",sumMax:"The sum of the values must be less than #{max}.",sumMin:"The sum of the values must be greater than #{min}.",nonHtml:"#{field} cannot contain HTML characters.",generic:"Invalid."},outputs:{},setup:function(options){this.settings=$.extend(this.settings,options);},report:null,isValidating:function(){return!!this.report;},start:function(){if(this.outputs[this.settings.outputMode]&&this.outputs[this.settings.outputMode].start){this.outputs[this.settings.outputMode].start();}
this.report={errors:0,valid:true};},end:function(){var results=this.report||{errors:0,valid:true};this.report=null;if(this.outputs[this.settings.outputMode]&&this.outputs[this.settings.outputMode].end){this.outputs[this.settings.outputMode].end(results);}
return results;},clear:function(){this.start();this.end();}};$.fn.extend({validity:function(arg){return this.each(function(){if(this.tagName.toLowerCase()=="form"){var f=null;if(typeof(arg)=="string"){f=function(){$(arg).require();};}
else if($.isFunction(arg)){f=arg;}
if(arg){$(this).bind("submit",function(){$.validity.start();f();return $.validity.end().valid;});}}});},require:function(msg){return validate(this,function(obj){return obj.value.length;},msg||$.validity.messages.require);},match:function(rule,msg){if(!msg){msg=$.validity.messages.match;if(typeof(rule)==="string"&&$.validity.messages[rule]){msg=$.validity.messages[rule];}}
if(typeof(rule)=="string"){rule=$.validity.patterns[rule];}
return validate(this,$.isFunction(rule)?function(obj){return!obj.value.length||rule(obj.value);}:function(obj){if(rule.global){rule.lastIndex=0;}
return!obj.value.length||rule.test(obj.value);},msg);},range:function(min,max,msg){return validate(this,min.getTime&&max.getTime?function(obj){var d=new Date(obj.value);return d>=new Date(min)&&d<=new Date(max);}:function(obj){var f=parseFloat(obj.value);return f>=min&&f<=max;},msg||format($.validity.messages.range,{min:$.validity.settings.argToString(min),max:$.validity.settings.argToString(max)}));},greaterThan:function(min,msg){return validate(this,min.getTime?function(obj){return new Date(obj.value)>min;}:function(obj){return parseFloat(obj.value)>min;},msg||format($.validity.messages.greaterThan,{min:$.validity.settings.argToString(min)}));},greaterThanOrEqualTo:function(min,msg){return validate(this,min.getTime?function(obj){return new Date(obj.value)>=min;}:function(obj){return parseFloat(obj.value)>=min;},msg||format($.validity.messages.greaterThanOrEqualTo,{min:$.validity.settings.argToString(min)}));},lessThan:function(max,msg){return validate(this,max.getTime?function(obj){return new Date(obj.value)<max;}:function(obj){return parseFloat(obj.value)<max;},msg||format($.validity.messages.lessThan,{max:$.validity.settings.argToString(max)}));},lessThanOrEqualTo:function(max,msg){return validate(this,max.getTime?function(obj){return new Date(obj.value)<=max;}:function(obj){return parseFloat(obj.value)<=max;},msg||format($.validity.messages.lessThanOrEqualTo,{max:$.validity.settings.argToString(max)}));},maxLength:function(max,msg){return validate(this,function(obj){return obj.value.length<=max;},msg||format($.validity.messages.tooLong,{max:max}));},minLength:function(min,msg){return validate(this,function(obj){return obj.value.length>=min;},msg||format($.validity.messages.tooShort,{min:min}));},equal:function(arg0,arg1){var
$reduction=(this.reduction||this).filter($.validity.settings.elementSupport),transform=function(val){return val;},msg=$.validity.messages.equal;if($reduction.length){if($.isFunction(arg0)){transform=arg0;if(typeof(arg1)=="string"){msg=arg1;}}
else if(typeof(arg0)=="string"){msg=arg0;}
var
map=$.map($reduction,function(obj){return transform(obj.value);}),first=map[0],valid=true;for(var i in map){if(map[i]!=first){valid=false;}}
if(!valid){raiseAggregateError($reduction,msg);this.reduction=$([]);}}
return this;},distinct:function(arg0,arg1){var
$reduction=(this.reduction||this).filter($.validity.settings.elementSupport),transform=function(val){return val;},msg=$.validity.messages.distinct,subMap=[],valid=true;if($reduction.length){if($.isFunction(arg0)){transform=arg0;if(typeof(arg1)=="string"){msg=arg1;}}
else if(typeof(arg0)=="string"){msg=arg0;}
var map=$.map($reduction,function(obj){return transform(obj.value);});for(var i1=0;i1<map.length;i1++){if(map[i1].length){for(var i2=0;i2<subMap.length;i2++){if(subMap[i2]==map[i1]){valid=false;}}
subMap.push(map[i1]);}}
if(!valid){raiseAggregateError($reduction,msg);this.reduction=$([]);}}
return this;},sum:function(sum,msg){var $reduction=(this.reduction||this).filter($.validity.settings.elementSupport);if($reduction.length&&sum!=numericSum($reduction)){raiseAggregateError($reduction,msg||format($.validity.messages.sum,{sum:sum}));this.reduction=$([]);}
return this;},sumMax:function(max,msg){var $reduction=(this.reduction||this).filter($.validity.settings.elementSupport);if($reduction.length&&max<numericSum($reduction)){raiseAggregateError($reduction,msg||format($.validity.messages.sumMax,{max:max}));this.reduction=$([]);}
return this;},sumMin:function(min,msg){var $reduction=(this.reduction||this).filter($.validity.settings.elementSupport);if($reduction.length&&min<numericSum($reduction)){raiseAggregateError($reduction,msg||format($.validity.messages.sumMin,{min:min}));this.reduction=$([]);}
return this;},nonHtml:function(msg){return validate(this,function(obj){return $.validity.patterns.nonHtml.test(obj.value);},msg||$.validity.messages.nonHtml);},assert:function(expression,msg){var $reduction=this.reduction||this;if($reduction.length){if($.isFunction(expression)){return validate(this,expression,msg||$.validity.messages.generic);}
else if(!expression){raiseAggregateError($reduction,msg||$.validity.messages.generic);this.reduction=$([]);}}
return this;}});function validate($obj,regimen,message){var
$reduction=($obj.reduction||$obj).filter($.validity.settings.elementSupport),elements=[];$reduction.each(function(){if(regimen(this)){elements.push(this);}
else{raiseError(this,format(message,{field:infer(this)}));}});$obj.reduction=$(elements);return $obj;}
function addToReport(){if($.validity.isValidating()){$.validity.report.errors++;$.validity.report.valid=false;}}
function raiseError(obj,msg){addToReport();if($.validity.outputs[$.validity.settings.outputMode]&&$.validity.outputs[$.validity.settings.outputMode].raise){$.validity.outputs[$.validity.settings.outputMode].raise($(obj),msg);}}
function raiseAggregateError($obj,msg){addToReport();if($.validity.outputs[$.validity.settings.outputMode]&&$.validity.outputs[$.validity.settings.outputMode].raiseAggregate){$.validity.outputs[$.validity.settings.outputMode].raiseAggregate($obj,msg);}}
function numericSum(obj){var accumulator=0;obj.each(function(){var n=parseFloat(this.value);accumulator+=isNaN(n)?0:n;});return accumulator;}
function format(str,obj){for(var p in obj){str=str.replace("#{"+p+"}",obj[p]);}
return capitalize(str);}
function infer(field){var
$f=$(field),ret=$.validity.settings.defaultFieldName;if($f.attr("title").length){ret=$f.attr("title");}
else if(/^([A-Z0-9][a-z]*)+$/.test(field.id)){ret=field.id.replace(/([A-Z0-9])[a-z]*/g," $&");}
else if(/^[a-z0-9_]*$/.test(field.id)){var arr=field.id.split("_");for(var i=0;i<arr.length;i++){arr[i]=capitalize(arr[i]);}
ret=arr.join(" ");}
return ret;}
function capitalize(sz){return sz.substring?sz.substring(0,1).toUpperCase()+sz.substring(1,sz.length):sz;}})(jQuery);(function($){function getIdentifier($obj){return $obj.attr('id').length?$obj.attr('id'):$obj.attr('name');}
$.validity.outputs.label={start:function(){$("label.error").remove();},end:function(results){if(!results.valid&&$.validity.settings.scrollTo){location.hash=$("label.error:eq(0)").attr('for');}},raise:function($obj,msg){var
labelSelector="label.error[for='"+getIdentifier($obj)+"']";if($(labelSelector).length){$(labelSelector).text(msg);}
else{$("<label/>").attr("for",getIdentifier($obj)).addClass("error").text(msg).click(function(){if($obj.length){$obj[0].select();}}).insertAfter($obj);}},raiseAggregate:function($obj,msg){if($obj.length){this.raise($($obj.get($obj.length-1)),msg);}}};})(jQuery);(function($){var
errorClass="validity-modal-msg",container="body";$.validity.outputs.modal={start:function(){$("."+errorClass).remove();},end:function(results){if(!results.valid&&$.validity.settings.scrollTo){location.hash=$("."+errorClass+":eq(0)").attr('id');}},raise:function($obj,msg){if($obj.length){var
off=$obj.offset(),obj=$obj.get(0),errorStyle={left:parseInt(off.left+$obj.width()+4,10)+"px",top:parseInt(off.top-10,10)+"px"};$("<div/>").addClass(errorClass).css(errorStyle).text(msg).click($.validity.settings.modalErrorsClickable?function(){$(this).remove();}:null).appendTo(container);}},raiseAggregate:function($obj,msg){if($obj.length){this.raise($($obj.get($obj.length-1)),msg);}}};})(jQuery);(function($){var
container=".validity-summary-container",erroneous="validity-erroneous",errors="."+erroneous,wrapper="<li/>",buffer=[];$.validity.outputs.summary={start:function(){$(errors).removeClass(erroneous);buffer=[];},end:function(results){$(container).hide().find("ul").html('');if(buffer.length){for(var i=0;i<buffer.length;i++){$(wrapper).text(buffer[i]).appendTo(container+" ul");}
$(container).show();if($.validity.settings.scrollTo){location.hash=$(errors+":eq(0)").attr("id");}}},raise:function($obj,msg){buffer.push(msg);$obj.addClass(erroneous);},raiseAggregate:function($obj,msg){this.raise($obj,msg);}};})(jQuery);