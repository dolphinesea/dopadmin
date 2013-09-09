
var __winRoot__ = top || parent || window; // 祖先窗口对象
var __winParent__ = __winRoot__; // 父窗口对象（默认为祖先窗口）
var __winTree__ = {subWinNames:''}; // 本窗口的子窗口对象集合。 subWinNames 记录所有子窗口的名称，方便遍历

//--------------------------------------------------------
window.closeSub = function()
{
	__winTree_closeAllSubWins__(); // 关闭所有子窗口
	__winTree_closeWin__(); // 关闭当前窗口
}

window.openSub = function(url, wname, property)
{
	__winTree_openSubWin__(url, wname, property);
}

window.onbeforeunload = function()
{
	var n = window.event.screenX - window.screenLeft;
	var b = n > document.documentElement.scrollWidth-20;
	if(b && window.event.clientY < 0 || window.event.altKey)
	{
		window.closeSub();
	}
}

//--------------------------------------------------------
//////////////////////////////////////////////////////////
//
// 一些函数
//
//////////////////////////////////////////////////////////

//--------------------------------------------------------
// 指定窗口是否存在
//--------------------------------------------------------
function __winTree_isExistWin__(hdl){try{var ret=hdl && !hdl.closed}catch(e){var ret=false};return ret;}

//--------------------------------------------------------
//////////////////////////////////////////////////////////
//
// 窗口控制
//
//////////////////////////////////////////////////////////

//--------------------------------------------------------
// 构造窗口缺省名字
//--------------------------------------------------------
function __winTree_getDefaultWinName__()
{
	return "__wn" + (new Date().getTime()) + "__"; // 用当前时间构造窗口缺省名字
}

//--------------------------------------------------------
// 打开子窗口，并添加到窗口目录树
//--------------------------------------------------------
function __winTree_openSubWin__(url, wname, property)
{
	var hdl = window.open(url, wname, property);
	__winTree_addSubWin__(wname, hdl); // 保存到子窗口对象集合
}

//--------------------------------------------------------
// 保存到子窗口对象集合
//--------------------------------------------------------
function __winTree_addSubWin__(subWinName, sWin)
{
	var win = top ? top : window;  // 获取本窗口的最上层窗口
	var winTree = win.__winTree__;

	winTree[subWinName] = sWin; // 将子窗口对象加入到集合中
	winTree["subWinNames"] += subWinName + ","; // 所有子窗口名称集合
}

//--------------------------------------------------------
// 将指定窗口的子孙窗口对象，添加到指定窗口的父窗口中
//--------------------------------------------------------
// 使指定窗口的子孙窗口保持在目录树中的结构
// 否则指定窗口的子孙窗口将从目录树结构中断开，不能统一控制。
// （用于关闭指定窗口的操作时）
//--------------------------------------------------------
function __winTree_addSubWins2pWin__(thisWin)
{
	thisWin = thisWin || window; // 缺省为本窗口

	var pWin = thisWin.__winParent__;
	var winTree = thisWin.__winTree__;

	var arrSubWinNames = winTree.subWinNames.split(","); // 子窗口名字数组
	var intSubWinNamesLen = arrSubWinNames.length;
	for(var i=0; i<intSubWinNamesLen; i++)
	{
		var subWinName = arrSubWinNames[i]; // 指定窗口的子孙窗口名字
		if (!subWinName) continue;

		var sWin = winTree[subWinName]; // 指定窗口的子孙窗口对象

		// 为避免和父窗口中已有的子窗口的名称相同，因此要加上缺省的、以当前时间来构造的名称
		pWin.__winTree_addSubWin__(subWinName + __winTree_getDefaultWinName__(), sWin);
	}
}

//--------------------------------------------------------
// 本窗口的所有下级子窗口执行指定动作
//--------------------------------------------------------
// funcName					要执行的函数名称（例如 close ，即 window.close() ，或者其他自己定义的函数，例如 changeBgColor 等等）
// argVals					要执行的函数的参数值
//--------------------------------------------------------
function __winTree_doWithSubs__(win, funcName, argVals)
{
	if (!win || !funcName) return;

	var winTree = win.__winTree__;
	var arrSubWinNames = winTree.subWinNames.split(","); // 子窗口名字数组
	var intSubWinNamesLen = arrSubWinNames.length;
	for(var i=0; i<intSubWinNamesLen; i++)
	{
		var subWinName = arrSubWinNames[i]; // 子窗口名字
		if (!subWinName) continue;

		var sWin = winTree[subWinName]; // 子窗口对象
		if (__winTree_isExistWin__(sWin))
		{
			sWin.__winTree_doWithSubs__(sWin, funcName, argVals); // 查找该子窗口的下级子窗口
			sWin[funcName](argVals); // 执行指定的动作
		}
	}
}

//--------------------------------------------------------
// 初始化窗口目录树
//--------------------------------------------------------
function __winTree_init__()
{
	if (__winTree_isExistWin__(opener)) // 如果存在父窗口
	{
		var pWin = opener.top; // 父窗口。有可能是在 iframe 中打开本窗体，因此父窗体应该是 opener 的顶级窗体
		__winParent__ = pWin; // 祖先窗口
		__winRoot__ = pWin;

		var ppWin = pWin.opener;
		if (__winTree_isExistWin__(ppWin)) // 修正祖先窗口
		{
			__winRoot__ = pWin.__winRoot__;
		}
	}
	//alert([__winRoot__.document.title, __winParent__.document.title, window.document.title]); // for test only
}

//--------------------------------------------------------
// 关闭当前窗口
//--------------------------------------------------------
function __winTree_closeWin__(win)
{
	win = win || window; // 缺省为关闭当前窗口
	var pWin = win.__winParent__;
	if (__winTree_isExistWin__(pWin)) // 如果指定窗口有父窗口，且未关闭
	{
		__winTree_addSubWins2pWin__(win); // 将指定窗口的子孙窗口对象，添加到指定窗口的父窗口中
	}
	win.opener = null; // 此语句用于取消“是否关闭此窗口”的提示
	win.close(); // 关闭指定窗口
	pWin.focus(); // 聚焦到父窗口
}

//--------------------------------------------------------
// 关闭本窗口的所有下级子窗口
//--------------------------------------------------------
function __winTree_closeAllSubWins__(win)
{
	win = win || window; // 缺省为关闭当前窗口
	__winTree_doWithSubs__(win, "close");
}

//--------------------------------------------------------
// 初始化
//--------------------------------------------------------
__winTree_init__();
