layui.use('layim', function(layim){
var deviceId = parseInt(10*Math.random() + 1);
var websocket;
let wsUri = "ws://127.0.0.1:9998/";
var user = JSON.parse(localStorage.getItem('user'));
let routeMsgId = 10000;
let loginMsgId = 20000;
route(wsUri);


function route(url) {
    websocket = new WebSocket(url);
    websocket.onopen = function () {
        let message = {
            "messageBehavior": 9,
            "messageDirect": 4,
            "deviceId": deviceId,
            "id": routeMsgId,
        }
        sendMessage(message);
    };
    websocket.onmessage = function (evt) {
        onMessage(evt)
    };
}

function connect(url) {
    websocket = new WebSocket(url);
    websocket.onopen = function (evt) {
        let message = {
            "id": user.id + loginMsgId,
            "loginName": user.name,
            "fromUserId": user.id,
            "messageTypeId": 0,
            "messageBehavior": 1,
            "messageDirect": 3,
            "deviceId": deviceId,
            "token": user.token
        };
        sendMessage(message);
    };
    websocket.onmessage = function (evt) {
        onMessage(evt)
    };

}

function sendMessage(message) {
    protobuf.load("/proto/message.proto", function (err, root) {
        if (err) throw err;
        let WSMessage = root.lookup("com.ghj.protocol.MessageProto.Message");
        let wsmessage = WSMessage.create(message);
        let buffer = WSMessage.encode(wsmessage).finish();
        websocket.send(buffer);
    });
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
            } else if (buffer.matchMessageId == user.id + loginMsgId) {
                    //基础配置
                    layim.config({
                        //初始化接口
                        init: {
                            url: '/index/initMainFrame'
                            ,data: {id:user.id}
                        }
                        ,members: {
                            url: '/index/initMembers/'
                            ,data: {}
                        }
                        //上传图片接口
                        ,uploadImage: {
                            url: '/upload/image' //（返回的数据格式见下文）
                            ,type: '' //默认post
                        }

                        //上传文件接口
                        ,uploadFile: {
                            url: '/upload/file' //（返回的数据格式见下文）
                            ,type: '' //默认post
                        }
                        ,isAudio: true //开启聊天工具栏音频
                        ,isVideo: true //开启聊天工具栏视频
                        //扩展工具栏
                        ,tool: [{
                            alias: 'code'
                            ,title: '代码'
                            ,icon: '&#xe64e;'
                        }]
                        ,initSkin: '5.jpg' //1-5 设置初始背景
                        ,notice: true //是否开启桌面消息提醒，默认false
                        ,msgbox: '/layui/css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
                        ,find: '/layui/css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
                        ,chatLog: '/layui/css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可

                    });


                    layim.on('ready', function(options){
                        $.ajax({
                            url: "/index/initOffLineMessages",
                            data: {"toUserId":user.id},
                            type: "Post",
                            dataType: "json",
                            success: function (data) {
                                var messageList = data.data;
                                for (let i = 0; i < messageList.length; i++) {
                                    var message = messageList[i];
                                    layim.getMessage({
                                        username: message.username //消息来源用户名
                                        ,avatar: message.avatar //消息来源用户头像
                                        ,id: message.id //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
                                        ,type: message.type //聊天窗口来源类型，从发送消息传递的to里面获取
                                        ,content: message.content //消息内容
                                        ,cid: message.cid //消息id，可不传。除非你要对消息进行一些操作（如撤回）
                                        ,mine: false //是否我发送的消息，如果为true，则会显示在右方
                                        ,fromid: message.fromUserId //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
                                        ,timestamp: message.timestamp //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
                                        ,mine: false
                                    });
                                }
                                //
                            },
                            error: function (data) {
                            }
                        });
                    });
                    //监听在线状态的切换事件
                    layim.on('online', function(data){
                      //getOffLineMessage();
                    });

                    layim.on('members', function(data){
                        console.log(data);
                    });

                    //监听签名修改
                    layim.on('sign', function(value){

                    });

                    //监听自定义工具栏点击，以添加代码为例
                    layim.on('tool(code)', function(insert){
                        layer.prompt({
                            title: '插入代码'
                            ,formType: 2
                            ,shade: 0
                        }, function(text, index){
                            layer.close(index);
                            insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
                        });
                    });

                    //监听查看群员
                    layim.on('members', function(data){

                    });


                    layim.on('sendMessage', function(res){
                        var messageDirect = res.to.type == 'friend' ? 1 : 2;
                        var fromUserId = res.mine.id; //包含我发送的消息及我的信息
                        var toUserId = res.to.id; //对方的信息
                        var id = createRandomId();
                        var messageTypeId = 1;
                        var content = res.mine.content;
                        var messageBehavior = 3;
                        var deviceId = deviceId;
                        var username = res.mine.username;
                        var avatar = res.mine.headPortrait;
                        var message = {
                            "fromUserId":fromUserId,
                            "id":id,
                            "messageTypeId":messageTypeId,
                            "content":content,
                            "messageBehavior":messageBehavior,
                            "messageDirect":messageDirect,
                            "deviceId":deviceId,
                            "name":username,
                            "headPortrait":avatar
                        };
                        if (res.to.type == 'friend')  {
                            var key = "toUserId";
                        } else {
                            var key = "toGroupId";
                        }
                        var value = toUserId;
                        message[key] = value;
                        //监听到上述消息后，就可以轻松地发送socket了，如：
                        sendMessage(message);
                    });

            } else {
                if (buffer.messageBehavior != 5)  {
                    layim.getMessage({
                        username: buffer.name //消息来源用户名
                        ,avatar: buffer.headPortrait //消息来源用户头像
                        ,id: buffer.messageDirect == 1 ? buffer.fromUserId : buffer.toGroupId //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
                        ,type: buffer.messageDirect == 1 ? "friend" :"group" //聊天窗口来源类型，从发送消息传递的to里面获取
                        ,content: buffer.content //消息内容
                        ,cid: buffer.id //消息id，可不传。除非你要对消息进行一些操作（如撤回）
                        ,mine: false //是否我发送的消息，如果为true，则会显示在右方
                        ,fromid: buffer.fromUserId //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
                        ,timestamp: new Date() //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
                        ,mine: false
                    });
                }
            }
        }
    });
}
});

function createRandomId() {
    return (Math.random()*10000000).toString(16).substr(0,4)+(new Date()).getTime()+Math.random().toString().substr(2,5);
}




