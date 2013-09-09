//
//
//function addmedia(url) {
//	// Player.controls.stop();                 //停止播放器
//	alert(Player.playState); //播放器的版本信息
//
//	// 取得当前的播放列表
//	var playlist = Player.currentPlaylist;//新建一个指定URL的Media。
//	var currMedia = Player.newMedia(url);
//	//把新建的Media item添加到播放器列表
//	playlist.appendItem(currMedia);
//}
////清空播放列表
//function clearlist() {
//	//Player.currentPlaylist.count返回列表中的歌曲数量
//	while (Player.currentPlaylist.count > 0) {
//		var item = Player.currentPlaylist
//				.item(Player.currentPlaylist.count - 1);
//		Player.currentPlaylist.removeItem(item); //            从播放列表中删除项
//	}
//}
//
////播放选中的歌曲
//function addSeletedSongToPlayList() {
//
//	var selectItem = document.getElementsByName("song");
//	// alert(selectItem.length);            
//	for ( var i = 0; i < selectItem.length; i++) {
//		if (selectItem[i].checked == true) {
//			//alert(selectItem[i].value);
//			addmedia(selectItem[i].value);
//		}
//	}
//	// alert(Player.currentPlaylist.count);
//	Player.controls.play(); //让播放器开始播放
//}//添加文本输入框中的url到播放列表
//function addUrlToList() {
//	addmedia(document.getElementById("songURL").value);
//}



//很简单的一小段代码就可以实现啦 - -!
//通过读取userAgent来判断浏览器类型
//ie内核的使用 classid="clsid:6BF52A52-394A-11d3-B153-00C04F79FAA6"
//非ie内核的使用 type="application/x-ms-wmp"
function showPlayer(id, url) {
	var vhtml = '<object id="Player"';
	var userAg = navigator.userAgent;
	if (-1 != userAg.indexOf("MSIE")) {
		vhtml += ' classid="clsid:6BF52A52-394A-11d3-B153-00C04F79FAA6"';
	} else if (-1 != userAg.indexOf("Firefox")
			|| -1 != userAg.indexOf("Chrome") || -1 != userAg.indexOf("Opera")
			|| -1 != userAg.indexOf("Safari")) {
		//vhtml += ' type="application/x-mplayer2"';
		vhtml += ' type="application/x-ms-wmp"';

	}
	vhtml += ' width="100%"  height="45" style="margin-top:20px;" >';
	vhtml += '<param name="URL" value="' + url + '"/>';
	//下面这些播放器的参数自己配置吧
	vhtml += '<param name="autoStart" value="1" />';
	vhtml += '<param name="invokeURLs" value="true">';
	vhtml += '<param name="playCount" value="100">';
	vhtml += '<param name="uiMode" value="full" />';
	vhtml += '<param name="Volume" value="100">';
	vhtml += '<param name="loop" value="false">';
	vhtml += '<param name="defaultFrame" value="datawindow">';
	vhtml += '</object>';
	document.getElementById(id).innerHTML = vhtml;
	//document.getElementById("Player").settings.requestMediaAccessRights("full");
}

function deletePlayer() {
	if (document.getElementById("Player")) {
		try{
			document.getElementById("Player").parentNode.removeChild(document
				.getElementById("Player"));
		}
		catch(e){
			
		}
		
	}
}
function playAll() {
	isPlayAll = true;
	var selectItem = document.getElementsByName("song");
	//alert(selectItem.length);
	for ( var i = 0; i < selectItem.length; i++) {
		//alert(selectItem[i].value);
		playList[i] = selectItem[i].value;
		//addmedia(selectItem[i].value);
	}

	$("input[name='song'][value=" + playList[0] + "]").dblclick();
	playIndex = 0;

}
function playWav(url, recordid) {
	currPlaySong=url;
	$("#recordid").val(recordid);
	deletePlayer();
	showPlayer("divPlayer", url);

}