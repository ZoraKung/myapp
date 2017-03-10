<script type="text/javascript">
    var _appContext = "${ctx}";
    var _appLanguage = "zh";
    if (_appContext == undefined) {
        _appContext = "/";
    }
    if (_appLanguage == undefined) {
        _appLanguage = "zh";
    }
    var _currentUserName = '<shiro:principal property="name"/>';
    var _currentUserId = '<shiro:principal property="id"/>';
    var _userName = '<shiro:principal property="name"/>';

    var _is_submit = false;
</script>
