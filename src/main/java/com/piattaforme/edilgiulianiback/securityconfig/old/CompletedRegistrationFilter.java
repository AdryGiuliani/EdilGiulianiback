package com.piattaforme.edilgiulianiback.securityconfig.old;


import com.piattaforme.edilgiulianiback.utils.utility.Utils;
import jakarta.servlet.*;
import org.springframework.core.annotation.Order;

import java.io.IOException;

//@Component
@Order(1)
public class CompletedRegistrationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(Utils.getUID());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
