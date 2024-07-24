package com.piattaforme.edilgiulianiback.utils.utility;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;


public class Utils {
    public static String getEmail() {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authenticationToken.getPrincipal().getAttribute("email");
    }
    public static String getUID() {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authenticationToken.getPrincipal().getAttributes().keySet().toString();
    }

    public static boolean isAdmin(){
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority sa : authenticationToken.getAuthorities())
            if (sa.getAuthority().equals("ROLE_admin")){
                return true;
            }
        return false;
    }


}