package com.ghj.registry;

import com.alibaba.fastjson.JSON;
import com.ghj.common.base.Constant;
import com.ghj.protocol.*;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.List;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * @author GeHejun
 * @date 2019/7/1 11:22
 */
public class Broker {

    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    ServerBootstrap serverBootstrap;


    public void start(int port) {
        try {
            serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // HTTP请求的解码和编码
                            pipeline.addLast(new HttpServerCodec());
                            // 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse，
                            // 原因是HTTP解码器会在每个HTTP消息中生成多个消息对象HttpRequest/HttpResponse,HttpContent,LastHttpContent
                            pipeline.addLast(new HttpObjectAggregator(Constant.MAX_AGGREGATED_CONTENT_LENGTH));
                            // 主要用于处理大数据流，比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的; 增加之后就不用考虑这个问题了
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                            // 协议包解码
//                            pipeline.addLast(new MessageToMessageDecoder<WebSocketFrame>() {
//                                @Override
//                                protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> objs) {
//                                    ByteBuf buf = frame.content();
//                                    objs.add(buf);
//                                    buf.retain();
//                                }
//                            });
//                            // 协议包编码
//                            pipeline.addLast(new MessageToMessageEncoder<MessageLiteOrBuilder>() {
//                                @Override
//                                protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) {
//                                    ByteBuf result = null;
//                                    if (msg instanceof MessageLite) {
//                                        result = wrappedBuffer(((MessageLite) msg).toByteArray());
//                                    }
//                                    if (msg instanceof MessageLite.Builder) {
//                                        result = wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
//                                    }
//
//                                    WebSocketFrame frame = new BinaryWebSocketFrame(result);
//                                    out.add(frame);
//                                }
//                            });
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new ProtobufVarint32FrameDecoder());
                            pipeline.addLast(new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));
                            pipeline.addLast(new BrokeHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            Channel channel = channelFuture.channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
