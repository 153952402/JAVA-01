package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.HeaderHttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final List<String> proxyServer;
    private HttpOutboundHandler handler;
    private List<HttpRequestFilter> filters;
    
    public HttpInboundHandler(List<String> proxyServer, List<HttpRequestFilter> filters) {
        this.filters = filters;
        this.proxyServer = proxyServer;
        this.handler = new HttpOutboundHandler(this.proxyServer);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        try {
            Iterator<HttpRequestFilter> iterator = filters.iterator();
            //through filters
            while(iterator.hasNext()) {
                HttpRequestFilter filter = iterator.next();
                boolean isContinue = filter.filter(fullRequest, ctx);
                if(!isContinue) {
                    //break by filter
                    return;
                }
            }

            // do handler
            handler.handle(fullRequest, ctx);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


}
