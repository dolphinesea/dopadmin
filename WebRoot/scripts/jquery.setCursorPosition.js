$.fn.setCursorPosition = function(position){
    if(this.lengh == 0) return this;
    return $(this).setSelection(position, position);
}

$.fn.setSelection = function(selectionStart, selectionEnd) {
    if(this.lengh == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        setTimeout(function() {
        	input.setSelectionRange(selectionStart, selectionEnd);
        	input.focus();
        }, 0);
    }

    return this;
}

$.fn.focusEnd = function(){
    this.setCursorPosition(this.val().length);
}


/*function setCaretPosition(aCtrl, aPos) {
    if (aCtrl.setSelectionRange) {
        setTimeout(function() {
            aCtrl.setSelectionRange(aPos, aPos);
            aCtrl.focus();
        }, 0);
    } else if (aCtrl.createTextRange) {
        var rng = aCtrl.createTextRange();
        rng.collapse(true);
        rng.moveStart('character', aPos);
        rng.moveEnd('character', aPos);
        rng.select();
    }
}*/