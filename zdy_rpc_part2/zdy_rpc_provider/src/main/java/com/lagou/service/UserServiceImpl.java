package com.lagou.service;

import com.lagou.handler.UserServerHandler;
import com.lagou.zookeeper.CreateNote;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.zookeeper.KeeperException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private CreateNote cn;
    public String sayHello(String word) {
        try {
            System.out.println("调用成功--参数 " + word);
            String[] time = word.split("_");
            SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            Date date1 = new Date();
            Date date2 = timeStamp.parse(time[1]);
            long difference = date1.getTime() - date2.getTime();
            String current = timeStamp.format(date1);
            if (cn == null) {
                cn = new CreateNote();
                cn.getConnection("127.0.0.1:2181");
            }
            cn.registerTime("/times/" + "8990_" + difference + "_" + current);
        } catch(ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "调用成功--参数 " + word;
    }

    //hostName:ip地址  port:端口号
    public static void startServer(String hostName,int port) throws InterruptedException {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast( new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        pipeline.addLast(new UserServerHandler());

                    }
                });
        serverBootstrap.bind(hostName,port).sync();
        System.out.println("服务启动");


    }


}
