(function ($) {
  // register namespace
  $.extend(true, window, {
    "Slick": {
      "AutoTooltips": AutoTooltips
    }
  });


  function AutoTooltips(options) {
    var _grid;
    var _self = this;
    var _defaults = {
      maxToolTipLength: null
    };

    function init(grid) {
      options = $.extend(true, {}, _defaults, options);
      _grid = grid;
      _grid.onMouseEnter.subscribe(handleMouseEnter);
    }

    function destroy() {
      _grid.onMouseEnter.unsubscribe(handleMouseEnter);
    }

    function handleMouseEnter(e, args) {
      var cell = _grid.getCellFromEvent(e);
      if (cell) {
        var node = _grid.getCellNode(cell.row, cell.cell);
        var node1 = _grid.getCellNode(cell.row, 12);
        //var text = $.trim($(node1).text()).replace(/\n/g,'\r\n');
        
        var text = $(node1).text();
        
        $(node).attr("title", text);
//        if ($(node).innerWidth() < node.scrollWidth) {
//          var text = $.trim($(node).text());
//          if (options.maxToolTipLength && text.length > options.maxToolTipLength) {
//            text = text.substr(0, options.maxToolTipLength - 3) + "...";
//          }
//          $(node).attr("title", text);
//        } else {
//          $(node).attr("title", "");
//        }
      }
    }

    $.extend(this, {
      "init": init,
      "destroy": destroy
    });
  }
})(jQuery);




var qTipTag = "*"; //所要改变的标签（需小写）!//
var qTipX = 10;    //弹出窗口位于鼠标左侧的距离。//
var qTipY = 15;    //弹出窗口位于鼠标下方的距离。//
 
tooltip = {
    name: "qTip",
    offsetX: qTipX,
    offsetY: qTipY,
    tip: null
}
 
tooltip.init = function () {
    var tipNameSpaceURI = "http://www.w3.org/1999/xhtml";
    if (!tipContainerID) { var tipContainerID = this.name; }
    var tipContainer = document.getElementById(tipContainerID);
    if (!tipContainer) {
        tipContainer = document.createElementNS ? document.createElementNS(tipNameSpaceURI, "div") : document.createElement("div");
        tipContainer.setAttribute("id", tipContainerID);
        tipContainer.setAttribute("style", "");
        tipContainer.style.display = "none";
        document.getElementsByTagName("body").item(0).appendChild(tipContainer);
    }
 
    if (!document.getElementById) return;
    this.tip = document.getElementById(this.name);
    if (this.tip) document.onmouseover = function (evt) { tooltip.setToolTip(evt) };
    if (this.tip) document.onmousemove = function (evt) { tooltip.move(evt) };
}
 
tooltip.setToolTip = function (evt) {
    anchors = document.getElementsByTagName(qTipTag);
    var obj, sTitle;
 
    for (var i = 0; i < anchors.length; i++) {
        obj = anchors[i];
        sAlt = obj.alt;
        sTitle = obj.title;
 
        if (sAlt) {
            obj.setAttribute("tiptitle", sAlt);
            obj.alt = "";
            obj.onmouseover = function () { tooltip.show(this.getAttribute('tiptitle')), tooltip.move(evt) };
            obj.onmouseout = function () { tooltip.hide() };
        }
        else if (sTitle) {
            obj.setAttribute("tiptitle", sTitle);
            obj.title = "";
            obj.onmouseover = function () { tooltip.show(this.getAttribute('tiptitle')), tooltip.move(evt) };
            obj.onmouseout = function () { tooltip.hide() };
        }
    }
}
 
tooltip.move = function (evt) {
    var x = 0, y = 0;
    var bodyW = document.body.clientWidth;
    var bodyH = document.body.clientHeight;
    var elementH = document.documentElement.clientHeight; //可见区域高度
    var scrollTop = (document.documentElement && document.documentElement.scrollTop) ? document.documentElement.scrollTop : document.body.scrollTop; //网页被卷去的高
    var tipW = this.tip.clientWidth;
    var tipH = this.tip.clientHeight;
 
    if (document.all) {//IE
        x = (document.documentElement && document.documentElement.scrollLeft) ? document.documentElement.scrollLeft : document.body.scrollLeft;
        y = (document.documentElement && document.documentElement.scrollTop) ? document.documentElement.scrollTop : document.body.scrollTop;
        x += window.event.clientX;
        y += window.event.clientY;
    }
    else {//其他浏览器
        x = evt.pageX;
        y = evt.pageY;
    }
 
    if (x > bodyW - tipW - this.offsetX) {
        x = x - tipW - this.offsetX;
    }
    else {
        x = x + this.offsetX;
    }
    if (y >= elementH + scrollTop - tipH - this.offsetY) {
        y = elementH + scrollTop - tipH - 2;
    }
    else {
        y = y + this.offsetY;
    }
 
    this.tip.style.left = x + "px";
    this.tip.style.top = y + "px";
}
 
tooltip.show = function (text) {
    if (!this.tip) return;
 
    text = text.replace(/\n/g, "<br>").replace(/\0x13/g, "<br>").replace(/\{(.[^\{]*)\}/ig, "");
    var strTab =
        "<table id='toolTipTalbe' style='border:1px solid #007db5;line-height:140%;border-collapse: collapse;'>" +
//        "   <tr>" +
//        "       <td style='background-color:#99CC33;padding:8px;text-align:center;'>" +
//        "           <span style='font-size: 16pt; font-family: 隶书;font-weight: 200;'>--项目提示--</span>" +
//        "       </td>" +
//        "   </tr>" +
        "   <tr><td style='background-color:#EDEDED;padding:8px;'>" + text + "</td></tr>" +
        "</table>";
    this.tip.innerHTML = strTab;
    this.tip.style.cssText = "display:block;position:absolute;z-index:10001;";
    if (this.tip.clientWidth >= document.documentElement.clientWidth / 3) {
        this.tip.style.width = document.documentElement.clientWidth / 3 + "px";
    }
}
 
tooltip.hide = function () {
    if (!this.tip) return;
    this.tip.innerHTML = "";
    this.tip.style.display = "none";
}
 
window.onload = function () {
    tooltip.init();
}