var websocket;
let wsUri = "ws://127.0.0.1:8798/";
var user = layui.data('user');
var deviceId = Math.floor(Math.random()*10+1);
let routeMsgId = 10000;
let loginMsgId = 20000;
route(wsUri);

function route(url) {
    websocket = new WebSocket(url);
    websocket.onopen = function () {
        let message = {
            "messageBehavior": 8,
            "messageDirect": 4,
            "deviceId": 10,
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
            "id": new Snowflake(1, 1, 0).nextId(),
            "loginName": user.name,
            "fromUserId": user.id,
            "messageTypeId": 0,
            "messageBehavior": 1,
            "messageDirect": 3,
            "deviceId": deviceId,
            "token": data.token
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
            } else if (buffer.matchMessageId == loginMsgId) {
                layui.use('layim', function(layim){
                    //基础配置
                    layim.config({
                        //初始化接口
                        init: {
                            url: '/index/initMainFrame'
                            ,data: {id:10}
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

                        //,brief: true //是否简约模式（若开启则不显示主面板）

                        //,title: 'WebIM' //自定义主面板最小化时的标题
                        //,right: '100px' //主面板相对浏览器右侧距离
                        //,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
                        ,initSkin: '5.jpg' //1-5 设置初始背景
                        //,skin: ['aaa.jpg'] //新增皮肤
                        //,isfriend: false //是否开启好友
                        //,isgroup: false //是否开启群组
                        //,min: true //是否始终最小化主面板，默认false
                        ,notice: true //是否开启桌面消息提醒，默认false
                        //,voice: false //声音提醒，默认开启，声音文件为：default.mp3
                        ,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
                        ,find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
                        ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可

                    });

                    //监听在线状态的切换事件
                    layim.on('online', function(data){

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
                    //监听layim建立就绪
                    layim.on('ready', function(res){
                        layim.msgbox(5); //模拟消息盒子有新消息，实际使用时，一般是动态获得
                        //添加好友（如果检测到该socket）
                        layim.addList({
                            type: 'group'
                            ,avatar: "http://tva3.sinaimg.cn/crop.64.106.361.361.50/7181dbb3jw8evfbtem8edj20ci0dpq3a.jpg"
                            ,groupname: 'Angular开发'
                            ,id: "12333333"
                            ,members: 0
                        });
                        layim.addList({
                            type: 'friend'
                            ,avatar: "http://tp2.sinaimg.cn/2386568184/180/40050524279/0"
                            ,username: '冲田杏梨'
                            ,groupid: 2
                            ,id: "1233333312121212"
                            ,remark: "本人冲田杏梨将结束AV女优的工作"
                        });
                    });

                    //监听发送消息
                    layim.on('sendMessage', function(data){
                        var To = data.to;

                        if(To.type === 'friend'){
                            layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
                        }
                    });

                    //监听查看群员
                    layim.on('members', function(data){

                    });


                    layim.on('sendMessage', function(res){
                        var fromUserId = res.mine.id; //包含我发送的消息及我的信息
                        var toUserId = res.to.id; //对方的信息
                        var id = new Snowflake(1, 1, 0).nextId();
                        var messageTypeId = 0;
                        var content = "";
                        var messageBehavior = 3;
                        var messageDirect = 1;
                        var deviceId = deviceId;
                        var username = res.to.username;
                        var avatar = res.to.headPortrait;
                        var message = {
                            "fromUserId":fromUserId,
                            "toUserId":toUserId,
                            "id":id,
                            "messageTypeId":messageTypeId,
                            "content":content,
                            "messageBehavior":messageBehavior,
                            "messageDirect":messageDirect,
                            "deviceId":deviceId,
                            "name":username,
                            "headPortrait":avatar
                        }
                        //监听到上述消息后，就可以轻松地发送socket了，如：
                        sendMessage(message);
                    });
                });
            } else {
                layim.getMessage({
                    username: buffer.name //消息来源用户名
                    ,avatar: buffer.headPortrait //消息来源用户头像
                    ,id: buffer.toUserId //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
                    ,type: buffer.messageDirect == 1 ? "friend" :"group" //聊天窗口来源类型，从发送消息传递的to里面获取
                    ,content: buffer.connect //消息内容
                    ,cid: buffer.id //消息id，可不传。除非你要对消息进行一些操作（如撤回）
                    ,mine: false //是否我发送的消息，如果为true，则会显示在右方
                    ,fromid: buffer.fromUserId //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
                    ,timestamp: new Date() //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
                });
            }
        }
    });
}

