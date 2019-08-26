let wsUri = "ws://127.0.0.1:8798/";
route(wsUri);
let routeMsgId = 10000;
let loginMsgId = 20000;
var websocket;

function route(url) {
    websocket = new WebSocket(url);
    websocket.onopen = function () {
        let message = {
            "messageBehavior": 8,
            "messageDirect": 4,
            "deviceId": 10,
            "id": routeMsgId,
        }
        send(message);
    };
    websocket.onmessage = function (evt) {
        onMessage(evt)
    };
}

function connect(url) {
    websocket = new WebSocket(url);
    websocket.onopen = function (evt) {
        $.ajax({
            url: "/security/validateUser",
            data: {'loginName': 'GeHejun', 'password': '1'},
            type: "Post",
            dataType: "json",
            success: function (data) {
                let message = {
                    "id": new Snowflake(1, 1, 0).nextId(),
                    "loginName": "GeHejun",
                    "fromUserId": 10,
                    "messageTypeId": 0,
                    "messageBehavior": 1,
                    "messageDirect": 3,
                    "deviceId": 10,
                    "token": data.data.token
                };
                send(message);
            },
            error: function (data) {
            }
        });
    };

    websocket.onmessage = function (evt) {
        onMessage(evt)
    };

}


function onMessage(evt) {
    protobuf.load("/proto/message.proto", function (err, root) {
        if (err) throw err;
        let WSMessage = root.lookup("com.ghj.protocol.MessageProto.Message");
        let reader = new FileReader();
        reader.readAsArrayBuffer(evt.data);
        reader.onload = function (e) {
            let buf = new Uint8Array(reader.result);
            let buffer = WSMessage.decode(buf);
            if (buffer.matchMessageId == routeMsgId) {
                websocket.close();
                let url = "ws://" + buffer.ip + ":" + buffer.port;
                connect(url);
            } else if (buffer.matchMessageId == loginMsgId) {
                console.log(buffer);
            } else {
                layim.getMessage({
                    username: "Hi"
                    ,avatar: "http://qzapp.qlogo.cn/qzapp/100280987/56ADC83E78CEC046F8DF2C5D0DD63CDE/100"
                    ,id: buffer.fromUserId
                    ,type: "friend"
                    ,content: buffer.content +  new Date().getTime()
                });
            }
        }
    });
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

