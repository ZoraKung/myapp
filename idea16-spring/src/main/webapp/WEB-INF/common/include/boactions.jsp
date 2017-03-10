<script type="text/javascript">
    var boactions = [];
    $(document).ready(function () {

        $.ajax({
            type: "POST",
            url: _appContext + "/misc/boactions",
            dataType: "json",
            contentType: "application/json",
            async:false,
            success: function (data) {
                boactions = data;
                // var result = isActionEnabled("IND_MEM_APPLICATION", "DRAFT", "EDIT");
                // alert(result);
            }
        });
    });

    function isActionEnabled(boType, boCurrentStatus, boAction) {
        var result = false;
        $.each(boactions, function (index, value) {
            var _boType = value.boType.toUpperCase();
            var _boStatus = value.boStatus.toUpperCase();
            var _actionEnabled = value.boActionEnabled.toUpperCase();

            if (boType.toUpperCase() === _boType && boCurrentStatus.toUpperCase() === _boStatus) {
                if (_actionEnabled.indexOf(boAction.toUpperCase()) >= 0) {
                    result = true;
                    return false; // break out
                }
            }
        });
        return result;
    }

</script>