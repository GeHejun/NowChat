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
    protobuf.load("/proto/message.proto", function (err, root) {
        if (err) throw err;
        let WSMessage = root.lookup("com.ghj.protocol.MessageProto.Message");
        let reader = new FileReader();
        reader.readAsArrayBuffer(evt.data);
        reader.onload = function (e) {
            let buf = new Uint8Array(reader.result);
            let buffer = WSMessage.decode(buf).content;
            console.log(buffer);
        }
    });
}

function onError(evt) {

}



function send(message) {
    protobuf.load("/proto/message.proto", function (err, root) {
        if (err) throw err;
        let WSMessage = root.lookup("com.ghj.protocol.MessageProto.Message");
        let wsmessage = WSMessage.create(message);
        let buffer = WSMessage.encode(wsmessage).finish();
        websocket.send(buffer);
    });

}

