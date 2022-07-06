package com.scendodevteam.scendo.filter;

import java.io.IOException;

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

import com.scendodevteam.scendo.service.AuthUserSC;
import com.scendodevteam.scendo.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtFIlter extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AuthUserSC userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                String authorization = request.getHeader("Authorization");
                String token = null;
                String userName = null;
                
                // Se il token esiste nella richiesta e se è nel formato giusto
                // allora valorizza il token e l'username con i valori presenti nella richiesta
                if(authorization != null && authorization.startsWith("Bearer ")) {
                    
                    token = authorization.substring(7);

                    try {
                        userName = jwtUtil.getUsernameFromToken(token);
                    } catch (ExpiredJwtException e) {
                        System.out.println("Expired");
                        logger.warn("Il token è scaduto");
                        //throw new CustomAuthenticationException("Il token è scaduto","LG_004");
                    }catch(SignatureException e){
                        logger.warn("Non è possibile verificare la firma del token");
                        //throw new CustomAuthenticationException("Non è possibile verificare la firma del token","LG_005");
                    }catch(MalformedJwtException e){
                        logger.warn("Il token è malformato");
                        //throw new CustomAuthenticationException("Il token non è corretto","LG_006");
                    }
                    
                    
                }
                

                if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    
                    UserDetails userDetails
                            = userService.loadUserByUsername(userName);
        
                    if(jwtUtil.validateToken(token,userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                                = new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());
        
                        usernamePasswordAuthenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
        
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
        
                }
                filterChain.doFilter(request, response);
        }
        
}
    
