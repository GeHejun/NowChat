let wsUri = "ws://127.0.0.1:8798/";
let routeMsgId = 10000;
let serverMsgId = 20000;
connect(wsUri);
var websocket;
function connect(url) {
    websocket = new WebSocket(url);
    websocket.onopen = function (evt) {
        console.log(websocket.readyState)
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
    let message = {
        "messageBehavior":7,
        "messageDirect":3,
        "deviceId":100,
        "id": routeMsgId,
    };
    send(message);
}

function onClose(evt) {
    console.log("close");
    console.log(evt);
}

function onMessage(evt) {
    console.log(websocket.readyState);
    console.log("message" + evt);
    protobuf.load("/proto/message.proto", function (err, root) {
        if (err) throw err;
        let WSMessage = root.lookup("com.ghj.protocol.MessageProto.Message");
        let reader = new FileReader();
        reader.readAsArrayBuffer(evt.data);
        reader.onload = function (e) {
            let buf = new Uint8Array(reader.result);
            let buffer = WSMessage.decode(buf).content;
            console.log(buffer);
            if (buffer.matchMessageId == routeMsgId) {
                websocket.close();
                let url = "ws://" + buffer.ip + ":" + buffer.port;
                connect(url);
            }
            if (buffer.matchMessageId == serverMsgId) {
                $.ajax({
                    url:"/security/validateUser",
                    data:{'loginName':'GeHejun','password':'1'},
                    type:"Post",
                    dataType:"json",
                    success:function(data){
                        let message = {
                            "id": new Snowflake(1, 1, 0).nextId(),
                            "loginName":"GeHejun",
                            "fromUserId":10,
                            "messageTypeId":0,
                            "messageBehavior":1,
                            "messageDirect":2,
                            "deviceId":100,
                            "token":data.data.token
                        };
                        send(message);
                    },
                    error:function(data){
                    }
                });
            }

        }
    });
}

function onError(evt) {
    console.log("error");
    console.log(evt);
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

