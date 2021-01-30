package io.github.kimmking.gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class RateLimitHttpRequestFilter implements HttpRequestFilter {

    Logger logger = LoggerFactory.getLogger(RateLimitHttpRequestFilter.class);

    private static final String REJECT_INFO = "本次请求被限流，每秒最多访问1次";


    private RateLimiter rateLimiter = RateLimiter.create(1);//每秒发放1个令牌

    @Override
    public boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        if(rateLimiter.tryAcquire()) {
            return true;
        } else {
            System.out.println(REJECT_INFO);
            FullHttpResponse response = null;
            ByteBuf REJECT_INFO_BYTE_BUF = Unpooled.wrappedBuffer(REJECT_INFO.getBytes(StandardCharsets.UTF_8));
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, REJECT_INFO_BYTE_BUF);
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", REJECT_INFO.getBytes(StandardCharsets.UTF_8).length);
            ctx.write(response);
            ctx.flush();
            return false;
        }
    }
}
