package com.exam.config;

import com.exam.service.impl.UserDetailsServiceImpl;
import com.exam.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private UserDetailsServiceImpl userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String requestTokenHeader=request.getHeader("Authorization");
        System.out.println(requestTokenHeader);
        String username=null;
        String jwtToken = null;
        System.out.println(requestTokenHeader);
        if(requestTokenHeader !=null && requestTokenHeader.startsWith("Bearer "))
        {

            jwtToken =requestTokenHeader.substring(7);
            try {
                System.out.println(jwtToken);
                username=this.jwtUtils.extractUsername(jwtToken);
                System.out.println(username);
            }
            catch (ExpiredJwtException e)
            {
                e.printStackTrace();
                System.out.println("Token expired");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Error occured");
            }
        }
        else
        {
            System.out.println("Invalid token don't start with bearers");
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            final UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

            if(this.jwtUtils.validateToken(jwtToken,userDetails))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

        }   else
        {
            System.out.println("Token not valid");
        }
        filterChain.doFilter(request,response);

    }
}
