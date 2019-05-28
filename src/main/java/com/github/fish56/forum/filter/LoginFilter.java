package com.github.fish56.forum.filter;

import com.github.fish56.forum.user.User;
import com.github.fish56.forum.user.UserRepos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * 检查请求头中是否携带了token
 *   如果携带了token，就查询出这个token对应的用户
 *   并将其放置到请求对象中，供下游使用
 */
@Slf4j
@Component
public class LoginFilter implements Filter {
    private static String BEARER = "bearer ";
    @Autowired
    private UserRepos userRepos;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.debug("用户正在访问: " + servletRequest.getLocalAddr());

        String authorization = request.getHeader("Authorization");
        String token = null;
        if (authorization !=null && authorization.toLowerCase().startsWith(BEARER)) {
            // 去除 bearer 的前缀
            token = authorization.substring(7);
        }

        if (token == null) {
            log.info("用户没有携带token，说明这是一个匿名访问");
            log.info("为了使得用户可以在通过swagger-ui测试，对于匿名用户将其设置为用户1");
            Optional<User> userOptional = userRepos.findById(1);
            servletRequest.setAttribute("user", userOptional.get());
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
