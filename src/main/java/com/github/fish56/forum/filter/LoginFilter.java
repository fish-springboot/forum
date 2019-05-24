package com.github.fish56.forum.filter;

import com.github.fish56.forum.user.User;
import com.github.fish56.forum.user.UserRepos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查请求头中是否携带了token
 *   如果携带了token，就查询出这个token对应的用户
 *   并将其放置到请求对象中，供下游使用
 */
@Slf4j
@Component
public class LoginFilter implements Filter {
    @Autowired
    private UserRepos userRepos;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.debug("用户正在访问: " + servletRequest.getLocalAddr());

        String token = null;
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.length() > 7) {
            // 去除 bearer 的前缀
            token = authorization.substring(7);
        }

        if (token == null) {
            log.info("这是个匿名用户");
        } else {
            log.info("用户的token是: " + token);
            User user = userRepos.findByToken(token);
            if (user == null) {
                log.info("用户携带的是无效的token");
            } else {
                log.info("查询到用户: " + user.getName());
                servletRequest.setAttribute("user", user);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
