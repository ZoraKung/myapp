function showNav(arr) {
    var ul1 = [], ul2 = [], ul3 = [], ul4 = [], ul5 = [];

    //level1
    for (var a1 = 0; a1 < arr.length; a1++) {
        if (arr[a1].level == 1) {
            ul1.push(arr[a1]);
        }
    }
    //level2
    for (var a2 = 0; a2 < arr.length; a2++) {
        if (arr[a2].level == 2) {
            ul2.push(arr[a2]);
        }
    }

    //level3
    for (var a3 = 0; a3 < arr.length; a3++) {
        if (arr[a3].level == 3) {
            ul3.push(arr[a3]);
        }
    }
    //level4
    for (var a4 = 0; a4 < arr.length; a4++) {
        if (arr[a4].level == 4) {
            ul4.push(arr[a4]);
        }
    }
    //level5
    for (var a5 = 0; a5 < arr.length; a5++) {
        if (arr[a5].level == 4) {
            ul5.push(arr[a5]);
        }
    }
    //level1-2
    $("#nav").append('<ul class="nav1" id="nav1"></ul>');
    for (var b1 = 0; b1 < ul1.length; b1++) {
        $("#nav1").append('<li class="li1" id="li1_' + ul1[b1].id + '"><a href="' + ul1[b1].href + '">' + ul1[b1].name + '</a></li>');

        $("#li1_" + ul1[b1].id).append('<ul class="nav2" id="nav2_' + ul1[b1].id + '"></ul>');
        for (var b2 = 0; b2 < ul2.length; b2++) {
            if (ul2[b2].parId == ul1[b1].id) {
                $("#nav2_" + ul1[b1].id).append('<li class="li2" id="li2_' + ul2[b2].id + '"><a href="' + ul2[b2].href + '">' + ul2[b2].name + '</a></li>');
            }
        }
    }

    //level2-3
    for (var b2 = 0; b2 < ul2.length; b2++) {
        $("#li2_" + ul2[b2].id).append('<ul class="nav3" id="nav3_' + ul2[b2].id + '"></ul>');
        for (var b3 = 0; b3 < ul3.length; b3++) {
            if (ul3[b3].parId == ul2[b2].id) {
                $("#nav3_" + ul2[b2].id).append('<li class="li3" id="li3_' + ul3[b3].id + '"><a href="' + ul3[b3].href + '">' + ul3[b3].name + '</a></li>');
            }

        }
    }

    //level3-4
    for (var b3 = 0; b3 < ul3.length; b3++) {
        $("#li3_" + ul3[b3].id).append('<ul class="nav4" id="nav4_' + ul3[b3].id + '"></ul>');
        for (var b4 = 0; b4 < ul4.length; b4++) {
            if (ul4[b4].parId == ul3[b3].id) {
                $("#nav4_" + ul3[b3].id).append('<li class="li4" id="li4_' + ul4[b4].id + '"><a href="' + ul4[b4].href + '">' + ul4[b4].name + '</a></li>');
            }

        }
    }

    //level4-5
    for (var b4 = 0; b4 < ul4.length; b4++) {
        $("#li4_" + ul4[b4].id).append('<ul class="nav5" id="nav5_' + ul4[b4].id + '"></ul>');
        for (var b5 = 0; b5 < ul5.length; b5++) {
            if (ul5[b5].parId == ul4[b4].id) {
                $("#nav5_" + ul4[b4].id).append('<li class="li5" id="li5_' + ul5[b5].id + '"><a href="' + ul5[b5].href + '">' + ul5[b5].name + '</a></li>');
            }

        }
    }
}
