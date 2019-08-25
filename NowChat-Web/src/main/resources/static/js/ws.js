function encode(message) {
    let buffer = null;
    protobuf.load("./proto/message.proto", function (err, root) {
        if (err) throw err;
        let WSMessage = root.lookup("com.ghj.protocol.MessageProto.Message");
        let wsmessage = WSMessage.create(message);
        buffer = WSMessage.encode(wsmessage).finish();
    });
    return buffer;
}
function decode(message) {
    let buffer = null;
    protobuf.load("./proto/message.proto", function (err, root) {
        if (err) throw err;
        let WSMessage = root.lookup("com.ghj.protocol.MessageProto.Message");
        let reader = new FileReader();
        reader.readAsArrayBuffer(message);
        reader.onload = function (e) {
            let buf = new Uint8Array(reader.result);
            buffer = WSMessage.decode(buf).content;
        }
    });
    return buffer;

}
let wsUri = "ws://127.0.0.1:8798/";
connect(wsUri);

function connect(url) {
    websocket = new WebSocket(url);
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

}

function onClose(evt) {

}

function onMessage(evt) {
    // if (decode(evt.data).matchMessageId == ackMessage) {
    //
    // }
}

function onError(evt) {

}



function send(message) {
    websocket.send(encode(message));
}

