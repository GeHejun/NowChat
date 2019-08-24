var WSMessage;
var wsmessage;
var buffer;
protobuf.load("./proto/message.proto", function (err, root) {
    if (err) throw err;
    WSMessage = root.lookup("com.ghj.protocol.MessageProto.Message");
    wsmessage = WSMessage.create({content: "hello"});
    buffer = WSMessage.encode(wsmessage).finish();
});
var wsUri = "ws://127.0.0.1:8798/";
var output;
testWebSocket();

function testWebSocket() {
    websocket = new WebSocket(wsUri);
    websocket.onopen = function (evt) {
        onOpen(evt)
    };
    websocket.onclose = function (evt) {
        onClose(evt)
    };
    websocket.onmessage = function (evt) {
        onMessage(evt)
    };
    websocket.onerror = function (evt) {
        onError(evt)
    };
}

function onOpen(evt) {
    doSend(buffer);
}

function onClose(evt) {
}

function onMessage(evt) {
    var reader = new FileReader();
    reader.readAsArrayBuffer(evt.data);
    reader.onload = function (e) {
        var buf = new Uint8Array(reader.result);
        WSMessage.decode(buf).content;
    }
}

function onError(evt) {
}

function doSend(message) {
    websocket.send(buffer);
}

