package com.ghj.common.mq;

import com.ghj.common.util.JSONUtil;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.common.protocol.MessageProto;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author GeHejun
 * @date 2019-06-24
 */
public class SendUtil {
        public static void sendForQueue(MessageProto.message message) {
            try {
                PropertiesUtil.getInstance().getValue(null, null, null);
                // 获取到连接以及mq通道
                Connection connection = ConnectionUtil.getConnect("", 100, "", "", "");
                // 从连接中创建通道
                Channel channel = connection.createChannel();
                // 声明（创建）队列
                channel.queueDeclare("", false, false, false, null);

                channel.basicPublish("", "", null, JSONUtil.beanToJson(message).getBytes());
                //关闭通道和连接
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

}
