var loc = window.location, new_uri;
new_uri = "ws:"
new_uri += "//" + loc.host;
new_uri += "/socket/config";

var configTarget = "";

var socket = new WebSocket(new_uri);
socket.onmessage = function(event) {
    var msg = JSON.parse(event.data);
    var action = msg.action;
    if (action == "list") {
        var table = document.getElementById("configList").getElementsByTagName('tbody')[0];
        var new_body = document.createElement("tbody");
        var list = msg.data;
        for (var obj in list) {
            if (list.hasOwnProperty(obj)) {
                var jsonObj = list[obj];
                var row = new_body.insertRow(new_body.rows.length);
                row.insertCell(0).innerHTML = "<a href='/config?config=" + jsonObj.code + "'>" + jsonObj.name + "</a>"
            }
        }
        table.parentNode.replaceChild(new_body, table);
    } else if (action == "update") {
        document.getElementById("config_area").value = msg.data;
        var name = msg.name;
        document.getElementById("config_label").innerHTML = "Configuration - " + name;
    }
};

socket.onopen = function(event) {
    socket.send("list");
    socket.send(JSON.stringify( {
        action: "initial",
        code: configTarget
    }));
};

function updateConfig() {
    var json = document.getElementById("config_area").value;
    socket.send(JSON.stringify( {
        action: "set",
        code: configTarget,
        data: json
    }));
}

window.onload = function() {
    configTarget = getParameterByName("config");
    document.getElementById("config_area").addEventListener('keydown', textAreaKeyDown, false);
    document.addEventListener("keydown", function(e) {
        if (e.keyCode == 83 && (navigator.platform.match("Mac") ? e.metaKey : e.ctrlKey)) {
            e.preventDefault();
            updateConfig();
        }
    }, false);
};

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)", "i"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}