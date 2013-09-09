/***
 * Contains basic SlickGrid formatters.
 * @module Formatters
 * @namespace Slick
 */

(function ($) {
  // register namespace
  $.extend(true, window, {
    "Slick": {
      "Formatters": {
        "PercentComplete": PercentCompleteFormatter,
        "PercentCompleteBar": PercentCompleteBarFormatter,
        "YesNo": YesNoFormatter,
        "Checkmark": CheckmarkFormatter,
        "Recordmark": RecordFormatter,
        "Resultmark": ResultFormatter,
        "Answermark": answerFormatter,
        "Timemark":timeFormatter,
        "startTimemark":startTimeFormatter
      }
    }
  });

  function PercentCompleteFormatter(row, cell, value, columnDef, dataContext) {
    if (value == null || value === "") {
      return "-";
    } else if (value < 50) {
      return "<span style='color:red;font-weight:bold;'>" + value + "%</span>";
    } else {
      return "<span style='color:green'>" + value + "%</span>";
    }
  }

  function PercentCompleteBarFormatter(row, cell, value, columnDef, dataContext) {
    if (value == null || value === "") {
      return "";
    }

    var color;

    if (value < 30) {
      color = "red";
    } else if (value < 70) {
      color = "silver";
    } else {
      color = "green";
    }

    return "<span class='percent-complete-bar' style='background:" + color + ";width:" + value + "%'></span>";
  }

  function YesNoFormatter(row, cell, value, columnDef, dataContext) {
    return value ? "Yes" : "No";
  }

  function CheckmarkFormatter(row, cell, value, columnDef, dataContext) {
    return value ? "<img src='../images/tick.png'>" : "";
  }
  
  function RecordFormatter(row, cell, value, columnDef, dataContext) {
	  return value ? "<span style='display:inline-block; background:url(images/ui-icons_222222_256x240.png)no-repeat -5px -158px; width: 14px; height: 14px;'></span>" : "";
  }
  
  function ResultFormatter(row, cell, value, columnDef, dataContext) {
   
	  if (value == null || value === "") {
      return "";
     
    } else {
    	

		return covertResult(value);
    }
  }
  
  function answerFormatter(row, cell, value, columnDef, dataContext) {
		return getsec((value == null || value =="")?"0000":value.split(':')[1] + value.split(":")[2]);
  }
  
  function startTimeFormatter(row, cell, value, columnDef, dataContext){
	  return (value == null || value === "") ? "" : value.split('-')[1] + "-" + value.split('-')[2];
  }
  
  function timeFormatter(row, cell, value, columnDef, dataContext){
	if (value == null || value === "") {
      return "";
    } else {
		return value.split(" ")[1];
    }
  }
  
})(jQuery);