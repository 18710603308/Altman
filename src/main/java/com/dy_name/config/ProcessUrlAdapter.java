package com.dy_name.config;

import com.dy_name.config.base.DBIdentifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mzy
 * @date 2021/8/2 21:43
 */
@Component
public class ProcessUrlAdapter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*if(request.getHeader("demo").equals("1")){
            DBIdentifier.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/sys?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=UTC");
        }else if(request.getHeader("demo").equals("2")){
            DBIdentifier.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flow?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=UTC");
        }else if(request.getHeader("demo").equals("3")){
            DBIdentifier.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/cc?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=UTC");
        }*/
        DBIdentifier.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/"+ request.getHeader("demo") + "?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=UTC");
        return true;
    }

}
