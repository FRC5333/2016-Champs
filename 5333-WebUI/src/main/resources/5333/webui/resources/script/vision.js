var loc = window.location, new_uri;
new_uri = "ws:"
new_uri += "//" + loc.host;
new_uri += "/socket/vision";

var gauge;

var socket = new WebSocket(new_uri);
socket.onmessage = function(event) {
    var json = JSON.parse(event.data);
    gauge.clear();
    for (var i in json) {
        if (json.hasOwnProperty(i)) {
            var rect = json[i];
            gauge.push(rect.x, rect.y, rect.width, rect.height, rect.active);
        }
    }
    gauge.draw();
};

window.onload = function() {
    gauge = createGauge(document.getElementById("visionGauge"));
    gauge.draw();
};

function createGauge(element) {
    var gauge = {
        canvas: element,
        ctx: element.getContext("2d"),
        nodes: []
    };

    gauge.canvas.width = gauge.canvas.parentElement.clientWidth;
    var ratio = 360 / 640.0;
    gauge.canvas.height = gauge.canvas.width * ratio;

    gauge.height = gauge.canvas.height;
    gauge.width = gauge.canvas.width;

    gauge.clear = function() { clearGauge(gauge) };
    gauge.push = function(x, y, width, height) { pushGauge(gauge, x, y, width, height) };
    gauge.draw = function() { drawGauge(gauge) };

    return gauge;
}

function pushGauge(gauge, x, y, width, height, active) {
    gauge.nodes.push({
        x: x / 640 * gauge.width,
        y: y / 360 * gauge.height,
        width: width / 640 * gauge.width,
        height: height / 360 * gauge.height,
        active: active
    });
}

function clearGauge(gauge) {
    gauge.nodes = [];
}

function drawGauge(gauge) {
    gauge.ctx.clearRect(0, 0, gauge.width, gauge.height);

    gauge.ctx.fillStyle = "#333333";
    gauge.ctx.fillRect(0, 0, gauge.width, gauge.height);

    gauge.ctx.fillStyle = "#DDDDDD";
    gauge.ctx.fillRect(2, 2, gauge.width - 4, gauge.height - 4);

    for (var node in gauge.nodes) {
        if (gauge.nodes.hasOwnProperty(node)) {
            var n = gauge.nodes[node];
            gauge.ctx.strokeStyle = "#AA33AA";
            if (n.active) gauge.ctx.strokeStyle = "#33AA33";
            gauge.ctx.strokeRect(n.x, n.y, n.width, n.height);

            var cx = n.x + n.width / 2;
            var cy = n.y + n.height / 2;
            gauge.ctx.fillStyle = "#33AA33";
            gauge.ctx.fillRect(cx - 15, cy - 1, 30, 2);
            gauge.ctx.fillStyle = "#3333AA";
            gauge.ctx.fillRect(cx - 1, cy - 15, 2, 30);
        }
    }

    gauge.ctx.fillStyle = "#333333";
    gauge.ctx.fillRect((gauge.width / 2) - 1, 0, 2, gauge.height);
}