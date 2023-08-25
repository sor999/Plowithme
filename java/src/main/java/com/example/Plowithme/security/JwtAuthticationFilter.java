package com.example.Plowithme.security;

import com.example.Plowithme.exception.custom.TokenException;
import com.example.Plowithme.exception.error.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private UserDetailsService userDetailsService;

    public JwtAuthticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // http request에서 jwt 토큰 가져옴
        String token = getTokenFromRequest(request);
        try {
            // 토큰 검증
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

                    // 토큰으로부터 username가져옴
                    String username = jwtTokenProvider.getUsername(token);

                    // 토큰 유저 불러옴
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

//                Authentication authentication = jwtTokenProvider.getAuthentication(token);
//                log.info("authentication:", authentication);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                logger.info("============토큰 유효함 ===========");
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
            log.info("==================예외 감지");
        }
//            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
//                     SignatureException | IllegalArgumentException e) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired，登陆已过期");
//            }


        filterChain.doFilter(request, response);

    }

    //http 헤더로부터 bearer 토큰을 가져옴
    private String getTokenFromRequest(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }

}
