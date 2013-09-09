function dialogerror(meg, cause){
	return html ="<div style=\"margin: 50px 50px; font:normal 1em 'Sans Serif', Verdana, 微软雅黑; cursor: pointer; -moz-user-select: none;\" onclick='parent.jQuery.colorbox.close();'>" 
		        +"<p>" + meg + "</p>" 
		        +"<p>原因：" + cause +"</p>" 
		        +"</div>";
}

function dialogsuccess(meg){
	return html ="<div style='margin: 50px 50px; font:normal 1em Sans Serif, Verdana, 微软雅黑; cursor: pointer; -moz-user-select: none;' onclick='parent.jQuery.colorbox.close();'>" 
        +"<p>" + meg + "</p>" 
        +"</div>";
}

function dialogerrorNoCause(meg){
	return html ="<div style='margin: 50px 50px; font:normal 1em Sans Serif, Verdana, 微软雅黑; cursor: pointer; -moz-user-select: none;' onclick='parent.jQuery.colorbox.close();'>" 
		        +"<p>" + meg + "</p>"  
		        +"</div>";
}