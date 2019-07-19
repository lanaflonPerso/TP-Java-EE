package com.tp_sdzee.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

    public static final String ENCODING = "encoding"; // key for encoding.
    private String             encoding;

    public void init( FilterConfig filterConfig ) throws ServletException {
        this.encoding = filterConfig.getInitParameter( ENCODING );
    }

    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain filterChain )
            throws IOException, ServletException {
        req.setCharacterEncoding( encoding );
        resp.setContentType( "text/html;charset=" + encoding );
        filterChain.doFilter( req, resp );
    }

    public void destroy() {
    }

}
