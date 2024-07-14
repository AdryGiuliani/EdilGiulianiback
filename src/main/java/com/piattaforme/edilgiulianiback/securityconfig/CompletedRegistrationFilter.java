package com.piattaforme.edilgiulianiback.securityconfig;


import com.piattaforme.edilgiulianiback.utils.utility.Utils;
import jakarta.servlet.*;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;

@Component
@Order(1)
public class CompletedRegistrationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(Utils.getUID());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
