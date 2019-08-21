var socket;
//如果浏览器支持WebSocket
if (window.WebSocket) {
    //参数就是与服务器连接的地址
    socket = new WebSocket("ws://localhost:8899/ws");
    //客户端收到服务器消息的时候就会执行这个回调方法
    socket.onmessage = function (event) {
        // 解码
        responseUserDecoder({
            data: event.data,
            success: function (responseUser) {

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
    // socket.binaryType = "arraybuffer";
    // 判断是否开启
    if (socket.readyState !== WebSocket.OPEN) {
        alert("连接没有开启");
        return;
    }
    var data = {
        userName: message,
        age: 18,
        password: "11111"
    };
    requestUserEncoder({
        data: data,
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
function requestUserEncoder(obj) {
    var data = obj.data;
    var success = obj.success; // 成功的回调
    var fail = obj.fail; // 失败的回调
    var complete = obj.complete; // 成功或者失败都会回调
    protobuf.load("../proto/MessageDataProto.proto", function (err, root) {
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
        var RequestUser = root.lookupType("com.example.nettydemo.protobuf.RequestUser");
        // Exemplary payload
        var payload = data;
        // Verify the payload if necessary (i.e. when possibly incomplete or invalid)
        var errMsg = RequestUser.verify(payload);
        if (errMsg) {
            if (typeof fail === "function") {
                fail(errMsg)
            }
            if (typeof complete === "function") {
                complete()
            }
            return;
        }
        // Create a new message
        var message = RequestUser.create(payload); // or use .fromObject if conversion is necessary
        // Encode a message to an Uint8Array (browser) or Buffer (node)
        var buffer = RequestUser.encode(message).finish();
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
function responseUserDecoder(obj) {
    var data = obj.data;
    var success = obj.success; // 成功的回调
    var fail = obj.fail; // 失败的回调
    var complete = obj.complete; // 成功或者失败都会回调
    protobuf.load("../proto/MessageDataProto.proto", function (err, root) {
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
        var ResponseUser = root.lookupType("com.example.nettydemo.protobuf.ResponseUser");
        var reader = new FileReader();
        reader.readAsArrayBuffer(data);
        reader.onload = function (e) {
            var buf = new Uint8Array(reader.result);
            var responseUser = ResponseUser.decode(buf);
            if (typeof success === "function") {
                success(responseUser)
            }
            if (typeof complete === "function") {
                complete()
            }
        }
    });
}