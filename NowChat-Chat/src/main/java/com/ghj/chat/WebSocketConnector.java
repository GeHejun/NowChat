package com.ghj.chat;

import com.ghj.common.base.Constant;
import com.ghj.protocol.MessageProto;
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
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.List;

import static io.netty.buffer.Unpooled.wrappedBuffer;

public class WebSocketConnector {

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
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(Constant.MAX_AGGREGATED_CONTENT_LENGTH));
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                            pipeline.addLast(new MessageToMessageDecoder<WebSocketFrame>() {
                                @Override
                                protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> objs) {
                                    ByteBuf buf = frame.content();
                                    objs.add(buf);
                                    buf.retain();
                                }
                            });
                            pipeline.addLast(new MessageToMessageEncoder<MessageLiteOrBuilder>() {
                                @Override
                                protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) {
                                    ByteBuf result = null;
                                    if (msg instanceof MessageLite) {
                                        result = wrappedBuffer(((MessageLite) msg).toByteArray());
                                    }
                                    if (msg instanceof MessageLite.Builder) {
                                        result = wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
                                    }

                                    WebSocketFrame frame = new BinaryWebSocketFrame(result);
                                    out.add(frame);
                                }
                            });
                            pipeline.addLast(new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));
                            pipeline.addLast(new ConnectHandler());
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
