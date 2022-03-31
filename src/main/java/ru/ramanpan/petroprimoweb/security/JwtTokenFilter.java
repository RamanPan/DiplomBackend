package ru.ramanpan.petroprimoweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.ramanpan.petroprimoweb.exceptions.JwtAuthException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {
    @Autowired
    private JwtTokenProvider provider;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String path = ((HttpServletRequest) servletRequest).getServletPath();
        if ("/api/v1/log".equals(path)) {
            System.out.println("Done");
        }
        if ("/api/v1/register".equals(path)) {
            System.out.println("Done1");
        }
        String token = provider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if(token != null && provider.validateToken(token)) {
                Authentication authentication = provider.getAuth(token);
                if(authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        catch (JwtAuthException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAuthException("JWT token is expired or invalid");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
