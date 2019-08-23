var socket;
//如果浏览器支持WebSocket
if (window.WebSocket) {
    //参数就是与服务器连接的地址
    socket = new WebSocket("ws://localhost:8798/ws");
    //客户端收到服务器消息的时候就会执行这个回调方法
    socket.onmessage = function (event) {
        // 解码
        responseMessageDecoder({
            data: event.data,
            success: function (message) {

            },
            fail: function (err) {
                console.log(err);
            },
            complete: function () {
                console.log("解码全部完成")
            }
        })
    }
    //连接建立的回调函数
    socket.onopen = function (event) {

    }
    //连接断掉的回调函数
    socket.onclose = function (event) {

    }
} else {
    alert("浏览器不支持WebSocket！");
}
//发送数据
function send(message) {
    if (!window.WebSocket) {
        return;
    }
    // 判断是否开启
    if (socket.readyState !== WebSocket.OPEN) {
        alert("连接没有开启");
        return;
    }
    requestMessageEncoder({
        data: message,
        success: function (buffer) {
            console.log("编码成功");
            socket.send(buffer);
        },
        fail: function (err) {
            console.log(err);
        },
        complete: function () {
            console.log("编码全部完成")
        }
    });
}
/**
 * 发送的消息编码成 protobuf
 */
function requestMessageEncoder(obj) {
    let data = obj.data;
    let success = obj.success; // 成功的回调
    let fail = obj.fail; // 失败的回调
    let complete = obj.complete; // 成功或者失败都会回调
    protobuf.load("/message.proto", function (err, root) {
        if (err) {
            if (typeof fail === "function") {
                fail(err)
            }
            if (typeof complete === "function") {
                complete()
            }
            return;
        }
        let requestMessageProto = root.lookupType("com.ghj.protocol.Message");
        let payload = data;
        let errMsg = requestMessageProto.verify(payload);
        if (errMsg) {
            if (typeof fail === "function") {
                fail(errMsg)
            }
            if (typeof complete === "function") {
                complete()
            }
            return;
        }
        let message = requestMessageProto.create(payload);
        let buffer = requestMessageProto.encode(message).finish();
        if (typeof success === "function") {
            success(buffer)
        }
        if (typeof complete === "function") {
            complete()
        }
    });
}
/**
 * 接收到服务器二进制流的消息进行解码
 */
function responseMessageDecoder(obj) {
    let data = obj.data;
    let success = obj.success; // 成功的回调
    let fail = obj.fail; // 失败的回调
    let complete = obj.complete; // 成功或者失败都会回调
    protobuf.load("//message.proto", function (err, root) {
        if (err) {
            if (typeof fail === "function") {
                fail(err)
            }
            if (typeof complete === "function") {
                complete()
            }
            return;
        }
        // Obtain a message type
        let requestMessageProto = root.lookupType("com.ghj.protocol.MessageProto.Message");
        let reader = new FileReader();
        reader.readAsArrayBuffer(data);
        reader.onload = function () {
            let buf = new Uint8Array(reader.result);
            let responseUser = requestMessageProto.decode(buf);
            if (typeof success === "function") {
                success(responseUser)
            }
            if (typeof complete === "function") {
                complete()
            }
        }
    });
}