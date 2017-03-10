/**
 * Author: Jack
 * Date: 15-3-6
 * Description: prompt users before session timeout
 */
//onload call init session monitor
/*window.onload = function() {
    initSessionMonitor();
}*/
//How frequently to check for session expiration in milliseconds
var session_pollInterval = 1200000; //20 minutes
//How many minutes the session is valid for
var session_expirationMinutes = 30;
//How many minutes before the warning prompt
var session_warningMinutes = 25;

var session_intervalID;
var session_lastActivity;

function initSessionMonitor() {
    session_lastActivity = new Date();
    sessionSetInterval();
    $(document).bind('keypress.session', function (ed, e) { sessionKeyPressed(ed, e); });
}
function sessionSetInterval() {
    session_intervalID = setInterval('sessionInterval()', session_pollInterval);
}
function sessionClearInterval() {
    clearInterval(session_intervalID);
}
function sessionKeyPressed(ed, e) {
    session_lastActivity = new Date();
}
function sessionPingServer() {
    //Call an AJAX function to keep-alive your session.
    ajaxKeepAlive();
}
function ajaxKeepAlive(){
    $.get("/mis/keep-alive", null, function(data){ });
}
function sessionLogOut() {
    window.location.href = '/mis/logout';
}

function sessionInterval() {
    var now = new Date();
    var diff = now - session_lastActivity;
    var diffMinutes = (diff / 1000 / 60);

    if (diffMinutes >= session_warningMinutes) {
        //warn before expiring
        //stop the timer
        sessionClearInterval();
        //prompt for attention
        if (confirm('Your session will expire in ' + (session_expirationMinutes - session_warningMinutes) +
            ' minutes (as of ' + now.toTimeString() + '), press OK to remain logged in ' +
            'or press Cancel to log off. \nIf you are logged off any changes will be lost.')) {
            now = new Date();
            diff = now - session_lastActivity;
            diffMinutes = (diff / 1000 / 60);

            if (diffMinutes > session_expirationMinutes) {
                //timed out
                sessionLogOut();
            }
            else {
                //reset inactivity timer
                sessionPingServer();
                sessionSetInterval();
                session_lastActivity = new Date();
            }
        } else {
            sessionLogOut();
        }
    } else {
        sessionPingServer();
        sessionSetInterval();
        session_lastActivity = new Date();
    }
}
