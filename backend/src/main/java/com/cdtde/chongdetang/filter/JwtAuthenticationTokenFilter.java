//用于验证jwt token，如果验证成功，则将User信息注入上下文中
package com.cdtde.chongdetang.filter;

import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!token.startsWith("Bearer ")) {
            request.setAttribute("tokenError", new RuntimeException("token缺少Bearer"));
            request.getRequestDispatcher("/error/token").forward(request, response);
            return;
        }

        token = token.substring(7);
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            request.setAttribute("tokenError", e);
            request.getRequestDispatcher("/error/token").forward(request, response);
            return;
        }

        String userid = claims.getSubject();
        User user = userMapper.selectById(Integer.parseInt(userid));
        if (user == null) {
            request.setAttribute("tokenError", new RuntimeException("用户未注册"));
            request.getRequestDispatcher("/error/token").forward(request, response);
            return;
        }

        LoginUser loginUser = new LoginUser(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
