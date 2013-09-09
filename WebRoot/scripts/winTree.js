
var __winRoot__ = top || parent || window; // ���ȴ��ڶ���
var __winParent__ = __winRoot__; // �����ڶ���Ĭ��Ϊ���ȴ��ڣ�
var __winTree__ = {subWinNames:''}; // �����ڵ��Ӵ��ڶ��󼯺ϡ� subWinNames ��¼�����Ӵ��ڵ����ƣ��������

//--------------------------------------------------------
window.closeSub = function()
{
	__winTree_closeAllSubWins__(); // �ر������Ӵ���
	__winTree_closeWin__(); // �رյ�ǰ����
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
// һЩ����
//
//////////////////////////////////////////////////////////

//--------------------------------------------------------
// ָ�������Ƿ����
//--------------------------------------------------------
function __winTree_isExistWin__(hdl){try{var ret=hdl && !hdl.closed}catch(e){var ret=false};return ret;}

//--------------------------------------------------------
//////////////////////////////////////////////////////////
//
// ���ڿ���
//
//////////////////////////////////////////////////////////

//--------------------------------------------------------
// ���촰��ȱʡ����
//--------------------------------------------------------
function __winTree_getDefaultWinName__()
{
	return "__wn" + (new Date().getTime()) + "__"; // �õ�ǰʱ�乹�촰��ȱʡ����
}

//--------------------------------------------------------
// ���Ӵ��ڣ�����ӵ�����Ŀ¼��
//--------------------------------------------------------
function __winTree_openSubWin__(url, wname, property)
{
	var hdl = window.open(url, wname, property);
	__winTree_addSubWin__(wname, hdl); // ���浽�Ӵ��ڶ��󼯺�
}

//--------------------------------------------------------
// ���浽�Ӵ��ڶ��󼯺�
//--------------------------------------------------------
function __winTree_addSubWin__(subWinName, sWin)
{
	var win = top ? top : window;  // ��ȡ�����ڵ����ϲ㴰��
	var winTree = win.__winTree__;

	winTree[subWinName] = sWin; // ���Ӵ��ڶ�����뵽������
	winTree["subWinNames"] += subWinName + ","; // �����Ӵ������Ƽ���
}

//--------------------------------------------------------
// ��ָ�����ڵ����ﴰ�ڶ�����ӵ�ָ�����ڵĸ�������
//--------------------------------------------------------
// ʹָ�����ڵ����ﴰ�ڱ�����Ŀ¼���еĽṹ
// ����ָ�����ڵ����ﴰ�ڽ���Ŀ¼���ṹ�жϿ�������ͳһ���ơ�
// �����ڹر�ָ�����ڵĲ���ʱ��
//--------------------------------------------------------
function __winTree_addSubWins2pWin__(thisWin)
{
	thisWin = thisWin || window; // ȱʡΪ������

	var pWin = thisWin.__winParent__;
	var winTree = thisWin.__winTree__;

	var arrSubWinNames = winTree.subWinNames.split(","); // �Ӵ�����������
	var intSubWinNamesLen = arrSubWinNames.length;
	for(var i=0; i<intSubWinNamesLen; i++)
	{
		var subWinName = arrSubWinNames[i]; // ָ�����ڵ����ﴰ������
		if (!subWinName) continue;

		var sWin = winTree[subWinName]; // ָ�����ڵ����ﴰ�ڶ���

		// Ϊ����͸����������е��Ӵ��ڵ�������ͬ�����Ҫ����ȱʡ�ġ��Ե�ǰʱ�������������
		pWin.__winTree_addSubWin__(subWinName + __winTree_getDefaultWinName__(), sWin);
	}
}

//--------------------------------------------------------
// �����ڵ������¼��Ӵ���ִ��ָ������
//--------------------------------------------------------
// funcName					Ҫִ�еĺ������ƣ����� close ���� window.close() �����������Լ�����ĺ��������� changeBgColor �ȵȣ�
// argVals					Ҫִ�еĺ����Ĳ���ֵ
//--------------------------------------------------------
function __winTree_doWithSubs__(win, funcName, argVals)
{
	if (!win || !funcName) return;

	var winTree = win.__winTree__;
	var arrSubWinNames = winTree.subWinNames.split(","); // �Ӵ�����������
	var intSubWinNamesLen = arrSubWinNames.length;
	for(var i=0; i<intSubWinNamesLen; i++)
	{
		var subWinName = arrSubWinNames[i]; // �Ӵ�������
		if (!subWinName) continue;

		var sWin = winTree[subWinName]; // �Ӵ��ڶ���
		if (__winTree_isExistWin__(sWin))
		{
			sWin.__winTree_doWithSubs__(sWin, funcName, argVals); // ���Ҹ��Ӵ��ڵ��¼��Ӵ���
			sWin[funcName](argVals); // ִ��ָ���Ķ���
		}
	}
}

//--------------------------------------------------------
// ��ʼ������Ŀ¼��
//--------------------------------------------------------
function __winTree_init__()
{
	if (__winTree_isExistWin__(opener)) // ������ڸ�����
	{
		var pWin = opener.top; // �����ڡ��п������� iframe �д򿪱����壬��˸�����Ӧ���� opener �Ķ�������
		__winParent__ = pWin; // ���ȴ���
		__winRoot__ = pWin;

		var ppWin = pWin.opener;
		if (__winTree_isExistWin__(ppWin)) // �������ȴ���
		{
			__winRoot__ = pWin.__winRoot__;
		}
	}
	//alert([__winRoot__.document.title, __winParent__.document.title, window.document.title]); // for test only
}

//--------------------------------------------------------
// �رյ�ǰ����
//--------------------------------------------------------
function __winTree_closeWin__(win)
{
	win = win || window; // ȱʡΪ�رյ�ǰ����
	var pWin = win.__winParent__;
	if (__winTree_isExistWin__(pWin)) // ���ָ�������и����ڣ���δ�ر�
	{
		__winTree_addSubWins2pWin__(win); // ��ָ�����ڵ����ﴰ�ڶ�����ӵ�ָ�����ڵĸ�������
	}
	win.opener = null; // ���������ȡ�����Ƿ�رմ˴��ڡ�����ʾ
	win.close(); // �ر�ָ������
	pWin.focus(); // �۽���������
}

//--------------------------------------------------------
// �رձ����ڵ������¼��Ӵ���
//--------------------------------------------------------
function __winTree_closeAllSubWins__(win)
{
	win = win || window; // ȱʡΪ�رյ�ǰ����
	__winTree_doWithSubs__(win, "close");
}

//--------------------------------------------------------
// ��ʼ��
//--------------------------------------------------------
__winTree_init__();
