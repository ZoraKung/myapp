$(function ($) {
    $.validator.setDefaults({
        errorElement: 'div',
        errorClass: 'help-block',
        focusInvalid: false,
        highlight: function (e) {
            $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
        },
        unhighlight: function (e) {
            $(e).closest('.form-group').removeClass('has-error').removeClass('has-info');
        },
        success: function (e) {
            $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
            $(e).remove();
        },
        invalidHandler: function (form, validator) {
            var errors = validator.numberOfInvalids();
            if (errors) {
                var _element_height = $(validator.errorList[0].element).offset().top;
                var _browser_height = $(window).height();
                if (_element_height > _browser_height){
                    $("html,body,div").animate({scrollTop: _element_height}, 'fast');
                }
                validator.errorList[0].element.focus();
            }
        },
        errorPlacement: function (error, element) {
            if (element.is(':checkbox') || element.is(':radio')) {
                var controls = element.closest('.controls');
                if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
                else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
            }
            else if (element.is(':input') && (!(element.is(':checkbox') || element.is(':radio')))) {
                var controls = element.closest('.controls');
                if (controls.find(':input').length > 1) controls.append(error);
                else error.insertAfter(element);
            }
            else if (element.is(':select') && (!(element.is(':checkbox') || element.is(':radio')))) {
                var controls = element.closest('.controls');
                if (controls.find(':select').length > 1) controls.append(error);
                else error.insertAfter(element);
            }
            else if (element.is('.select2')) {
                error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
            }
            else if (element.is('.chzn-select')) {
                error.insertAfter(element.siblings('[class*="chzn-container"]:eq(0)'));
            }
            else error.insertAfter(element);
        }
    });
}(jQuery));
jQuery.validator.addClassRules("groupRequireOne", {
    require_from_group: [1, ".groupRequireOne"]
});
jQuery.validator.addMethod("mobile", function (value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "Please enter a valid mobile.");

jQuery.validator.addMethod("phone", function (value, element) {
    var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
    return this.optional(element) || (tel.test(value));
}, "Please enter a valid phone.");

jQuery.validator.addMethod("mobilePhone", function(value, element) {
    var tel = /^(\d|[( )+-]){1,25}$/g;
    //return this.optional(element) || (tel.test(value));
    // BT 12558
    return this.optional(element) || (tel.test(value.replace(/\s/g, '')));
}, "Please enter a valid phone.");

jQuery.validator.addMethod("testCheckId", function (value, element, params) {
    var arrParams = params.split(',');
    var value1 = value, value2 = "", value3 = "";
    if (arrParams.length > 1) {
        value2 = $(arrParams[0]).val();
        value3 = $(arrParams[1]).val();
    } else if (arrParams.length > 0) {
        value2 = $(arrParams[0]).val();
    }
    return !(value1 == "" && value2 == "" && value3 == "");
}, "HKID,PassportNo,PRCID at least one isn't empty");

jQuery.validator.addMethod("newApplicationCheck", function (value, element, params) {
    var arrParams = params.split(',');
    var value1 = value, value2 = "", value3 = "", value4 = "";
    if (arrParams.length > 2) {
        value2 = $(arrParams[0]).val();
        value3 = $(arrParams[1]).val();
        value4 = $(arrParams[2]).val();
    } else if (arrParams.length > 1) {
        value2 = $(arrParams[0]).val();
        value3 = $(arrParams[1]).val();
    } else if (arrParams.length > 0) {
        value2 = $(arrParams[0]).val();
    }
    return !(value1 == "" && value2 == "" && value3 == "" && value4 == "");
}, "HKID,PassportNo,PRCID,Previous Student number at least one isn't empty");

jQuery.validator.addMethod("creditRequiredCheck", function (value, element, params) {
    var arrParams = params.split(',');
    var value1 = value, value2 = "";
    if (arrParams.length > 1) {
        value2 = $(arrParams[0]).val();
    } else if (arrParams.length > 0) {
        value2 = $(arrParams[0]).val();
    }
    return !(value1 == "" && value2 == "");
}, "HKID,PassportNo at least one isn't empty");

jQuery.validator.addMethod("ip", function (value, element) {
    var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
}, "Please enter a valid ip address.");

jQuery.validator.addMethod("chrnum", function (value, element) {
    var chrnum = /^([a-zA-Z0-9]+)$/;
    return this.optional(element) || (chrnum.test(value));
}, "Please enter the characters or numbers.");

jQuery.validator.addMethod("checkYear", function (value, element) {
    var year = /^\d{4}$|^\d{2}\.\d{4}$|^\d{2}\.\d{2}\.\d{4}$/;
    return this.optional(element) || (year.test(value));
}, "Please enter yyyy or MM.yyyy or dd.MM.yyyy!");

jQuery.validator.addMethod("dietNumberCheck", function (value, element) {
   if(value.length == 6){
       var checkVal = value.substring(value.length-2,value.length);
       if(checkVal == "06" || checkVal == "12"){
           return true;
       }else {
           return false;
       }
   }else{
       return true;
   }
}, "The last two digits must be end with 06 or 12.");

jQuery.validator.addMethod("dietNumberCheckYear", function (value, element) {
   if(value.length >= 4){
       var checkVal = value.substring(0,2);
       if(checkVal == "19" || checkVal == "20"){
           return true;
       }else {
           return false;
       }
   }else{
       return true;
   }
}, "please enter a proper year.");

jQuery.validator.addMethod("dietNumberCheckDigits", function (value, element) {
    if(0< value.length < 6){
        return false;
    }else if(value.length == 6){
        return true;
    }else if(value.length == 0){
        return true;
    }
}, "Please enter the 6 digit number.");
jQuery.validator.addMethod("urlWithoutHTTP", function(value, element, param) {
	return this.optional(element) || /^(www\.)(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)*(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
	}, jQuery.validator.messages.url);
jQuery.validator.addMethod("urlpath", function (value, element) {
    var urlpath = /^((\/([0-9a-zA-Z?*_-]+))+)*(\/\#)*(\/)?$/;
    return this.optional(element) || (urlpath.test(value));
}, "Please enter a valid URL path.");

jQuery.validator.addMethod("chinese", function (value, element) {
    var chinese = /^[\u4e00-\u9fa5]+$/;
    return this.optional(element) || (chinese.test(value));
}, "Please enter chinese.");

jQuery.validator.addMethod("chrnum2", function (value, element) {
    var chrnum = /^[\da-zA-Z_]+$/;
    return this.optional(element) || (chrnum.test(value));
}, "Please enter the characters or numbers or underline.");

jQuery.validator.addMethod("numberGTZero", function (value, element) {
    value = parseFloat(value);
    return this.optional(element) || value > 0;
}, "Please enter a number greater than zero.");

jQuery.validator.addMethod("numberGTEZero", function (value, element) {
    value = parseFloat(value);
    return this.optional(element) || value >= 0;
}, "Please enter a number greater than or equal to zero.");

jQuery.validator.addMethod("digitGTZero", function (value, element) {
    value = parseInt(value);
    return this.optional(element) || value > 0;
}, "Please enter a digit greater than zero.");

jQuery.validator.addMethod("digitGTEZero", function (value, element) {
    value = parseInt(value);
    return this.optional(element) || value >= 0;
}, "Please enter a digit greater than or equal to zero.");

jQuery.validator.addMethod("lesserToday", function (value, element) {
    var thisDate = value;
    var todayDate = new Date();
    if (value == '' || value.length != 10) {
        return true;
    }
    thisDate = thisDate.substring(6, 10) + "-" + thisDate.substring(3, 5) + "-" + thisDate.substring(0, 2);
    var reg = new RegExp('-', 'g');
    thisDate = new Date(parseInt(Date.parse(thisDate.replace(reg, '/')), 10));
    return thisDate < todayDate;
}, "The date cannot later than today.");

jQuery.validator.addMethod("compareNumber", function (value, element, params) {
    var minNumberElement = $(params), minNumber = minNumberElement.val(), maxNumber = value;
    if (minNumber == '' || maxNumber == '') {
        return true;
    }
    minNumber = parseInt(minNumber);
    maxNumber = parseInt(maxNumber);
    return maxNumber > minNumber;
}, "Max number must be greater than Min number.");

jQuery.validator.addMethod("compareDate", function (value, element, params) {
    var fromDateElement = $(params), fromDate = fromDateElement.val(), toDate = value;
    if (fromDate == '' || toDate == '' || fromDate.length != 10 || toDate.length != 10) {
        return true;
    }
    fromDate = fromDate.substring(6, 10) + "-" + fromDate.substring(3, 5) + "-" + fromDate.substring(0, 2);
    toDate = toDate.substring(6, 10) + "-" + toDate.substring(3, 5) + "-" + toDate.substring(0, 2);
    var reg = new RegExp('-', 'g');
    fromDate = new Date(parseInt(Date.parse(fromDate.replace(reg, '/')), 10));
    toDate = new Date(parseInt(Date.parse(toDate.replace(reg, '/')), 10));
    return toDate >=fromDate;
}, "End date must be greater than or equal to start date.");

jQuery.validator.addMethod("compareTime", function (value, element, params) {
    var fromTimeElement = $(params), fromTime = fromTimeElement.val(), toTime = value;
    if (fromTime == '' || toTime == '' || fromTime.length != 5 || toTime.length != 5) {
        return true;
    }
    var now = new Date().format('dd-MM-yyyy');
    fromTime = now + " " + fromTime.substring(0, 2) + ":" + fromTime.substring(3, 5);
    toTime = now + " " + toTime.substring(0, 2) + ":" + toTime.substring(3, 5);
    var reg = new RegExp('-', 'g');
    fromTime = new Date(parseInt(Date.parse(fromTime.replace(reg, '/')), 16));
    toTime = new Date(parseInt(Date.parse(toTime.replace(reg, '/')), 16));
    return toTime > fromTime;
}, "End time must be greater than start time.");

jQuery.validator.addMethod("hkid", function (value, element, params) {
    var hkidElement = $(params), hkid = hkidElement.val();
    if (hkid == '') {
        return true;
    }
    return isHkid(hkid);
}, "Please enter the correct Hong Kong identity card number.");

jQuery.validator.addMethod("idCardNo", function (value, element) {
    return this.optional(element) || isIdCardNo(value);
}, "Please enter the correct identity card number.");

jQuery.validator.addMethod('positiveInteger', function (value) {
    return /^\+?(0|[1-9]\d*)$/.test(value) && Number(value) >= 0;
}, 'Enter a positive integer.');

jQuery.validator.addMethod("remoteSync", function (value, element, params) {
    var _result = true, _name = element.name, _params = '{"' + _name + '": "' + encodeURIComponent(value) + '"}';
    if(value == ''){
        return true;
    }
    jQuery.ajax({
        type: "POST",
        dataType: 'text',
        data: jQuery.parseJSON(_params),
        url: params,
        async: false,
        success: function (data) {
            if (data == 'true') {
                _result = true;
            } else {
                _result = false;
            }
        },
        error: function () {
            _result = false;
        }
    });
    return _result;
}, "Please enter the correct remote operator url.");

function isHkid(str) {
    var strValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    // basic check length
    if (str.length < 8)
        return false;

    // handling bracket
    if (str.charAt(str.length - 3) == '(' && str.charAt(str.length - 1) == ')')
        str = str.substring(0, str.length - 3) + str.charAt(str.length - 2);

    // convert to upper case
    str = str.toUpperCase();

    // regular expression to check pattern and split
    var hkidPat = /^([A-Z]{1,2})([0-9]{6})([A0-9])$/;
    var matchArray = str.match(hkidPat);

    // not match, return false
    if (matchArray == null)
        return false;

    // the character part, numeric part and check digit part
    var charPart = matchArray[1];
    var numPart = matchArray[2];
    var checkDigit = matchArray[3];

    // calculate the checksum for character part
    var checkSum = 0;
    if (charPart.length == 2) {
        checkSum += 9 * (10 + strValidChars.indexOf(charPart.charAt(0)));
        checkSum += 8 * (10 + strValidChars.indexOf(charPart.charAt(1)));
    } else {
        checkSum += 9 * 36;
        checkSum += 8 * (10 + strValidChars.indexOf(charPart));
    }

    // calculate the checksum for numeric part
    for (var i = 0, j = 7; i < numPart.length; i++, j--)
        checkSum += j * numPart.charAt(i);

    // verify the check digit
    var remaining = checkSum % 11;
    var verify = remaining == 0 ? 0 : 11 - remaining;

    return verify == checkDigit || (verify == 10 && checkDigit == 'A');
}

function isIdCardNo(num) {
    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
    var varArray = new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        return false;
    }
    // check and set value
    for (i = 0; i < intStrLen; i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            return false;
        } else if (i < 17) {
            varArray[i] = varArray[i] * factorArr[i];
        }
    }
    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6, 14);
        if (isDate8(date8) == false) {
            return false;
        }
        // calculate the sum of the products
        for (i = 0; i < 17; i++) {
            lngProduct = lngProduct + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = parityBit[lngProduct % 11];
        // check last digit
        if (varArray[17] != intCheckDigit) {
            return false;
        }
    }
    else {        //length is 15
        //check date
        var date6 = idNumber.substring(6, 12);
        if (isDate6(date6) == false) {
            return false;
        }
    }
    return true;
}

function isDate6(sDate) {
    if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    if (year < 1700 || year > 2500) return false
    if (month < 1 || month > 12) return false
    return true
}

function isDate8(sDate) {
    if (!/^[0-9]{8}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    day = sDate.substring(6, 8);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if (year < 1700 || year > 2500) return false
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false
    if (day < 1 || day > iaMonthDays[month - 1]) return false
    return true
}

function getValidateErrorString(formElement) {
    var _result = '';
    var _errorList = formElement.validate().errorList;
    if (_errorList != undefined && _errorList.length > 0) {
        for (var i = 0; i < _errorList.length; i++) {
            var _name = _errorList[i].element.name, _msg = _errorList[i].message;
            if (_result != '') {
                _result += '\n';
            }
            _result += '[' + _name + '] - ' + _msg;
        }
    }
    return _result;
}
