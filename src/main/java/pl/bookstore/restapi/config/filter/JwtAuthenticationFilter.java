package pl.bookstore.restapi.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.bookstore.restapi.commons.enums.TokenType;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.bookstore.restapi.jwt.JwtUtil.*;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String JWT_HEADER_VALUE_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain) throws IOException, ServletException {
        Optional.of(httpRequest)
                .map(request -> request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(JWT_HEADER_VALUE_PREFIX))
                .map(header -> header.substring(JWT_HEADER_VALUE_PREFIX.length()))
                .map(this::getUsernamePasswordAuthenticationToken)
                .ifPresent(token -> SecurityContextHolder.getContext().setAuthentication(token));
        chain.doFilter(httpRequest, httpResponse);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String jwt) {
        UsernamePasswordAuthenticationToken token = null;
        try {
            DecodedJWT decodedJWT = JWT.require(JWT_ALGORITHM)
                    .build()
                    .verify(jwt);
            if (TokenType.ACCESS.name().equals(decodedJWT.getClaim(TYPE).asString())) {
                String login = decodedJWT.getSubject();
                List<String> roles = decodedJWT.getClaim(ROLE).asList(String.class);
                List<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                token = new UsernamePasswordAuthenticationToken(login, null, authorities);
            }
        } catch (Exception exception) {
            log.debug("Invalid Access token.");
        }
        return token;
    }
}