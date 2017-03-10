/**
 * Created by WTH on 13-10-30.
 * Global Variable
 */
var i18nBundlePath = _appContext + '/static/scripts/bundle/';

var jqgridConstants = {
    height: 400, //'100%', //'auto', // 350
    autowidth: true,
    viewrecords: true,
    rowNum: 20,
    rowList: [10, 20, 50, 100, 200]
}

var formatter = {
    jqDate: 'dd-mm-yyyy',
    jqDateTime: 'dd-MM-yyyy HH:mm',
    jqFullDateTime: 'dd-MM-yyyy HH:mm:ss',
    date: 'dd-MM-yyyy',
    datetime: 'dd-MM-yyyy HH:mm',
    jqGridDate: 'd-m-Y',
    jqGridDateTime: 'd-m-Y H:i',
    jqGridFullDateTime: 'd-m-Y H:i:s'
}

var userSettingSaveEndpoint = _appContext + "/user/setting/create";
var userSettingRetrieveEndpoint = _appContext + "/user/setting/retrieve";
var userSettingDeleteEndpoint = _appContext + "/user/setting/delete";

