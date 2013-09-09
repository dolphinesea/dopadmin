function openWindow(url, width, height, resizable) {
	var left = (screen.width - width) / 2;
	var top = (screen.height - height) / 2;
	var style = 'toolbar=no，status=yes,resizable=' + resizable + ',width='
			+ width + ',height=' + height + ',top=' + top + ',left=' + left;
	window.open(url, '', style);
}

/**
 * 打开一个对话框
 */
function openFullScreenWindow(url, s) {
	var style = 'left=0,top=0,width=' + (screen.availWidth - 10) + ',height='
			+ (screen.availHeight - 50)
			+ ',scrollbars,resizable=yes,toolbar=no';
	window.open(url, '', style);
}

/**
 * 打开一个对话框
 */
function clearForm(form) {
	$("input:text", form).each(function() {
		//alert(this.name);
			$("input[name='" + this.name + "']").val("");
		});
	$("select", form).each(function() {
		//alert(this.name);
			$("select[name='" + this.name + "']").val("");
		});

}

/**
 * 打开一个对话框
 */
function enableButton(id, enable) {
	

	if(!enable){
	$("#"+id).attr("disabled","disabled");
	}else{ 
//通过将第三个text输入框disabled属性置为false来移除disabled属性
		cl($("#"+id));
	$("#"+id).removeAttr("disabled");  }
}

/* Chinese initialisation for the jQuery UI date picker plugin. */
/* Written by Cloudream (cloudream@gmail.com). */
jQuery(function($) {
	$.datepicker.regional['zh-CN'] = {
		closeText : '关闭',
		prevText : '&#x3c;上月',
		nextText : '下月&#x3e;',
		currentText : '今天',
		monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月',
				'十月', '十一月', '十二月' ],
		monthNamesShort : [ '一', '二', '三', '四', '五', '六', '七', '八', '九', '十',
				'十一', '十二' ],
		dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
		dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五', '周六' ],
		dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
		dateFormat : 'yy-mm-dd',
		firstDay : 1,
		isRTL : false
	};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});

/*  
 * .serializeObject (c) Dan Heberden 
 * danheberden.com 
 *  
 * Gives you a pretty object for your form elements 
 */
(function($) {
	$.fn.serializeObject = function() {
		if (!this.length) {
			return false;
		}

		var $el = this, data = {}, lookup = data; //current reference of data  

		$el
				.find(':input[type!="checkbox"][type!="radio"], input:checked')
				.each(function() {
					// data[a][b] becomes [ data, a, b ]  
						var named = this.name.replace(/\[([^\]]+)?\]/g, ',$1')
								.split(','), cap = named.length - 1, i = 0;

						if (named != "") {

							for (; i < cap; i++) {
								// move down the tree - create objects or array if necessary  

								if (named[i] != "") {
									lookup = lookup[named[i]] = lookup[named[i]]
											|| (named[i + 1] == "" ? [] : {});
								}

							}

							// at the end, psuh or assign the value  
							if (lookup.length != undefined) {
								lookup.push($(this).val());
							} else {
								lookup[named[cap]] = $(this).val();
							}

							// assign the reference back to root  
							lookup = data;
						}
					});

		return data;
	};
})(jQuery);


function getNowFormatDate() 
{ 
var day = new Date(); 
var Year = 0; 
var Month = 0; 
var Day = 0; 
var CurrentDate = ""; 
//初始化时间 
//Year= day.getYear();//有火狐下2008年显示108的bug 
Year= day.getFullYear();//ie火狐下都可以 
Month= day.getMonth()+1; 
Day = day.getDate(); 
//Hour = day.getHours(); 
// Minute = day.getMinutes(); 
// Second = day.getSeconds(); 
CurrentDate += Year + "-"; 
if (Month >= 10 ) 
{ 
CurrentDate += Month + "-"; 
} 
else 
{ 
CurrentDate += "0" + Month + "-"; 
} 
if (Day >= 10 ) 
{ 
CurrentDate += Day ; 
} 
else 
{ 
CurrentDate += "0" + Day ; 
} 
return CurrentDate; 
} 


function cl(log){
	console.log("javascript log:"+log);
}

function covertResult(value)
{
		//value="000,"+value
	
	//    	*   nnn:   $      n     <>:      J:       F:    +-:       Y:    X
//      opeartorName,answer,checktime,checknumber,numbertype,dealproblem,serviceterms,isbad
    	
	    cov=value.split(',');
    	cl(value);
    	//value=cov[0];
    	value="";
    	if(cov[1]=="1")
    	{
    		value+="$";
    	}
    	
    	
    	value+=getsec(cov[2].split(':')[1] + cov[2].split(':')[2]);
    	
    	if(cov[3]=="1")
    	{
    		value+=">";
    	}
    	if(cov[3]=="2")
    	{
    		value+="&lt";
    	}
    	
    	if(cov[4]=="1")
    	{
    		value+="J";
    	}
    	if(cov[4]=="2")
    	{
    		value+="F";
    	}
    	if(cov[5]=="1")
    	{
    		value+="+";
    	}
    	if(cov[5]=="2")
    	{
    		value+="-";
    	}
    	if(cov[6]=="2")
    	{
    		value+="Y";
    	}
    	
    	if(cov[7]=="1")
    	{
    		value+="X";
    	}
    	cl(value.toString())
      	return value;
}

function getsec(str){
	var ret = 0;
	var ge = parseInt(str.substr(3,1));
	var shi = parseInt(str.substr(2,1));
	var bai = parseInt(str.substr(1,1));
	var qian = parseInt(str.substr(0,1));
	
	if(qian == 0){
		if(bai == 0){
			if(shi == 0){
				ret = ge;
			}
			else{
				ret = shi * 10 + ge;
			}
		}
		else{
			ret = bai * 100 + shi * 10 + ge;
		}
	}
	else{
		ret = qian * 1000 + bai * 100 + shi * 10 + ge;
	}
	
	return ret;
}


