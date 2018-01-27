package com.it.huaxia.springcloud.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * zuul网关的使用
 * @author fengqigui
 * @Description
 * @Date 2018/01/27 18:02
 */
public class TokenFilter extends ZuulFilter {
    /**
     * 返回的类型
     *  有四种：
     *      pre: 主要是用在路由映射表
     *      routing: 请求转发使用
     *      error:  出错误时使用这个
     *      post: 运行完前面的后调用这个
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行的顺序：
     *  数字越小越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 控制过滤器是否生效
     * @return
     */

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getParameter("token");
        if(token == null){
            try{// 过滤器异常的处理
                // 设置这个过滤器挂了
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(401);
                context.setResponseBody("unauthrized");
            }catch(Exception e){
                // 不能写错error.status_code:错误码。
                context.set("error.status_code",401);
                context.set("error.message","错误的信息");
                context.set("error.exception",e);
            }
            return null;
        }
        return null;

    }

}
