/**
 * Created by WTH on 13-7-26.
 */

function clickSKinSelect() {
    // TODO
    /**
    if (document.body.classList.contains("skin-1")) {
        $("#skin-colorpicker option")
            .filter(function () {
                return $(this).text() == '#222A2D';
            }).attr('selected', true);

    }
    else if (document.body.classList.contains("skin-2")) {
        $("select#skin-colorpicker option")
            .each(function () {
                this.selected = (this.text == '#C6487E');
            });

    }
    else if (document.body.classList.contains("skin-3")) {
        $("select#skin-colorpicker option")
            .each(function () {
                this.selected = (this.text == '#D0D0D0');
            });
    }
    else {
        $("select#skin-colorpicker option")
            .each(function () {
                this.selected = (this.text == '#438EB9');
            });
    }
     */

    return true;
}


function aceSetting() {
    if (document.body.classList.contains("skin-1")) {
        $(".btn-colorpicker").css('background-color', '#222A2D');
    }
    else if (document.body.classList.contains("skin-2")) {
        //$("#skin-colorpicker").val("#C6487E");
        $(".btn-colorpicker").css("background-color", "#C6487E");
    }
    else if (document.body.classList.contains("skin-3")) {
        //$("#skin-colorpicker").val("#D0D0D0");
        $(".btn-colorpicker").css("background-color", "#D0D0D0");
    }
    else {
        //$("#skin-colorpicker").val("#438EB9");
        $(".btn-colorpicker").css("background-color", "#438EB9");
    }


//  Navbar Fixed

    var tf1 = document.body.classList.contains("navbar-fixed");
    if (tf1) {
        $("#ace-settings-header").prop('checked', true);
    }
    else {
        $("#ace-settings-header").prop('checked', false);
    }

    var tf2 = window.document.getElementById("sidebar").classList.contains("fixed");
    if (tf2) {
        $("#ace-settings-sidebar").prop('checked', true);
    }
    else {
        $("#ace-settings-sidebar").prop('checked', false);
    }

    var tf3 = document.body.classList.contains("breadcrumbs-fixed");
    if (tf3) {
        $("#ace-settings-breadcrumbs").prop('checked', true);
    }
    else {
        $("#ace-settings-breadcrumbs").prop('checked', false);
    }
}

function setSkinCookie() {
    var c_name = "skin";
    var c_value = "";
    var colorvalue = window.document.getElementById("skin-colorpicker").value;
    if (colorvalue == '#438EB9') {
        c_value = ""; // Default
    }
    else if (colorvalue == '#222A2D') {
        c_value = "skin-1"; //
    }
    else if (colorvalue == '#C6487E') {
        c_value = "skin-2"; //
    }
    else if (colorvalue == '#D0D0D0') {
        c_value = "skin-3"; //
    }

    setCookie(c_name, c_value, 20);

}

function setSidebarCookie() {
    var c_name = "sidebar";
    var c_value = "";
    var tf = window.document.getElementById("sidebar").classList.contains("menu-min");
    if (tf) {
        c_value = ""; // Default
    }
    else {
        c_value = "menu-min";
    }

    setCookie(c_name, c_value, 20);

}

function setHeaderFixedCookie() {
    var c_name = "navbarFixed";
    var c_value = "";
    var e = window.document.getElementById("ace-settings-header");

    if (!e.checked) {
        c_value = "false"; // Default
    }
    else {
        c_value = "true";
    }

    setCookie(c_name, c_value, 20);
}

function setSidebarFixedCookie() {
    var c_name = "sidebarFixed";
    var c_value = "";
    var e = window.document.getElementById("ace-settings-sidebar");

    if (!e.checked) {
        c_value = "false"; // Default
    }
    else {
        c_value = "true";
    }

    setCookie(c_name, c_value, 20);
}

function setBreadcrumbsFixedCookie() {
    var c_name = "breadcrumbsFixed";
    var c_value = "";
    var e = window.document.getElementById("ace-settings-breadcrumbs");

    if (!e.checked) {
        c_value = "false"; // Default
    }
    else {
        c_value = "true";
    }

    setCookie(c_name, c_value, 20);
}

function setCookie(c_name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + "=" + escape(value) + ";path=/" +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())

    var strCookie = document.cookie;
}



