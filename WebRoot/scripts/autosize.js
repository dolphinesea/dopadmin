function getObj(name){
    if (document.getElementById){
        this.obj = document.getElementById(name);
        this.style = document.getElementById(name).style;
    }
    else if (document.all){
        this.obj = document.all[name];
        this.style = document.all[name].style;
    }
}

function getWinSize(){
   var iWidth = 0, iHeight = 0;

   if (document.documentElement && document.documentElement.clientHeight){
       iWidth = parseInt(window.innerWidth,10);
       iHeight = parseInt(window.innerHeight,10);
   }
   else if (document.body){
       iWidth = parseInt(document.body.offsetWidth,10);
       iHeight = parseInt(document.body.offsetHeight,10);
   }

   return {width:iWidth, height:iHeight};
}

function resize_id(obj) {                           
    var oContent = new getObj(obj);
    var oWinSize = getWinSize();
    if ((oWinSize.height - parseInt(oContent.obj.offsetTop,10))>0)
        oContent.style.height = (oWinSize.height - parseInt(oContent.obj.offsetTop,10));
}

