/**
 * Created by user07 on 14-6-30.
 */
var changeTab = true;
(function ($) {
    $.fn.tabIndex = function () {
        return $(this).parent().find(this).index() - 1;
    };
    $.fn.selectTabByID = function (tabID) {
        $(this).tabs("option", "active", $('#' + tabID).tabIndex());
    };
    $.fn.selectTabByIndex = function (tabIndex) {
        $(this).tabs("option", "active", tabIndex);
    };

    $.fn.extend({
        "clicktabs": function (c) {
            c = $.extend({
                data: null, controllerMapping: null, clickCallback: null, callback: null, async: true, cache: true, loadAll: false, isCheck: true
            }, c);
            var $tabs = $(this);

            $tabs.find("ul").find("li").each(function (i, val) {
                var $li = $(this);
                var hrefname = $li.find("a").attr("href");
                var divId = hrefname.substring(1, hrefname.length);
                var ownData = "";
//              $li.append('<i id=i_'+divId+' class="icon-refresh yellow hide"></i>');
                $tabs.append('<div id=' + divId + '></div>');

                if (c.cache) {  // Add the "cache" option for those pages shouldn't be cached - By Carey on 2015-05-23
                    $li.bind("click",function () {
                        var tabName = $li.attr("aria-controls");
                        if (c.clickCallback != null && c.clickCallback != undefined && $.isFunction(c.clickCallback)) {
                            ownData = c.clickCallback(tabName);
                        }
                        //              $tabs.find(".icon-refresh").not(".hide").addClass("hide");
                        //              $(this).find(".icon-refresh").removeClass("hide");
                    }).one("click", function () {
                            tabsajax(divId, ownData);
                        });
                } else {
                    $li.bind("click", function () {
                        var tabName = $li.attr("aria-controls");
                        if (c.clickCallback != null && c.clickCallback != undefined && $.isFunction(c.clickCallback)) {
							ownData = c.clickCallback(tabName);
                        }
                        var spinner = '<div style="top:100%; width:100%;  text-align:center; vertical-align: middle; z-index:3000; display: block;"><img src="' + _appContext + '/static/images/spinner.gif"/></div>';
                        $("#" + divId).html(spinner);
                        tabsajax(divId, ownData);
                    });
                }
                var _doLiClick = function(){
                    setTimeout(function () {
                        $li.click();
                    }, 100)
                };
                if (i == 0) {
                    _doLiClick();
                } else if (c.loadAll) {
                    $.Deferred(_doLiClick);
                }
//              $("#i_"+divId).bind("click", function (){
//                 tabsajax(divId);
//              })
            });

            //Close BT: 10259
            this.tabs({
                beforeActivate: function (event, ui) {
					changeTab = true;
                    if (c.loadAll) {
                        return true;
                    }
                    var _changeFlag = false;
                    if (c.isCheck) {
                        //bt:10436
                        var _check = true;
                        ui.oldPanel.find('form').each(function (index, item) {
                            var _form_id = $(item).attr("id");
                            if (_form_id != undefined && _form_id != null && _form_id != '' && $('#' + _form_id).data('serialize') != undefined) {
                                if ($('#' + _form_id).serialize() != $('#' + _form_id).data('serialize')) {
                                    _changeFlag = true;
                                }
                                _check = $('#' + _form_id).valid();
                            }
                        });
                        //bt:10436
                        if (!_check) {
                            return false;
                        }
                    }
                    //BT: 10259
                    if (_changeFlag) {
                        if (confirm(jQuery.i18n.prop('not_save_exit'))) {
                            return true;
                        } else {
							changeTab = false;
							return false;
                        }
                    } else {
                        return true;
                    }
                }
            });

            this.tabs()
                .find('.ui-tabs-nav')
                .find('li')
                .css('display','inline-block')
                .css('float','none')
                .css('margin','0')
                .css('vertical-align','top');
            /*this.tabs('paging');*/

            function tabsajax(divId, d) {
                var ajaxData = "";
                var dataMap = c.data;
                var url = "";
                var callback = c.callback;
                var async = c.async;
                for (var index in dataMap) {
                    if (index == divId) {
                        ajaxData = dataMap[index];
                        if (typeof d != "undefined" && d != "") {
                            ajaxData = d;
                        }
                    }
                }

                if (typeof(ajaxData.customUrl) != "undefined") {
                    url = ajaxData.customUrl;
                    //delete ajaxData.customUrl;
                } else {
                    url = _appContext + c.controllerMapping + "/ajax/" + divId;
                }

                $.ajax({
                    type: "post",
                    dataType: "text",
                    data: ajaxData,
                    url: url,
                    async: async,
                    error: function (textStatus, errorThrown) {
                        matrix.showError(jQuery.i18n.prop('download_page_failure'));
                    },
                    success: function (response) {
                        $("#" + divId).html(response);
                        //set initialize
                        $("#" + divId).find('form').each(function (index, item) {
                            var _form_id = $(item).attr("id");
                            if (_form_id != undefined && _form_id != null && _form_id != '') {
                                $('#' + _form_id).data('serialize', $('#' + _form_id).serialize());
                            }
                        });
                        if (callback != null && callback != undefined && $.isFunction(callback)) {
                            callback(response, divId);
                        }
                    }
                });
            }
        }
    });
})(jQuery)