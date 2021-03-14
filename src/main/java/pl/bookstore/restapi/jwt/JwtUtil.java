package pl.bookstore.restapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.bookstore.restapi.commons.enums.TokenType;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtUtil {

    public static final long SECONDS_TO_MILLISECONDS_MULTIPLIER = 1000;
    public static final String TYPE = "type";
    public static final String ROLE = "role";

    public static Algorithm JWT_ALGORITHM;

    @Value("${jwt.access.token.expiration.seconds}")
    private long accessTokenExpirationSeconds;
    @Value("${jwt.secret}")
    private void initAlgorithm(String secret) {
        JWT_ALGORITHM = Algorithm.HMAC256(secret);
    }

    public String getJwtToken(String login, List<String> roles, TokenType tokenType, long expirationSec) {
        Date issuedAt = new Date();
        return JWT.create()
                .withClaim(TYPE, tokenType.name())
                .withSubject(login)
                .withClaim(ROLE, roles)
                .withIssuedAt(issuedAt)
                .withExpiresAt(new Date(issuedAt.getTime() + expirationSec * SECONDS_TO_MILLISECONDS_MULTIPLIER))
                .sign(JWT_ALGORITHM);
    }

    public String getAccessTokenFromRefreshTokens(Cookie refreshToken, Cookie logoutRefreshToken) {
        try {
            DecodedJWT decodedRefreshToken = JWT.require(JWT_ALGORITHM)
                    .build()
                    .verify(refreshToken.getValue());
            List<String> roles = decodedRefreshToken.getClaim(ROLE).asList(String.class);
            DecodedJWT decodedLogoutRefreshToken = JWT.require(JWT_ALGORITHM)
                    .build()
                    .verify(logoutRefreshToken.getValue());
            if (tokenTypesValid(decodedRefreshToken, decodedLogoutRefreshToken)
                    && subjectValid(decodedRefreshToken, decodedLogoutRefreshToken)
                    && rolesValid(decodedRefreshToken, decodedLogoutRefreshToken)) {
                return getJwtToken(decodedRefreshToken.getSubject(), roles, TokenType.ACCESS, accessTokenExpirationSeconds);
            }
        } catch (Exception exception) {
            log.debug("Refreshing access token failed.");
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refreshing access token failed.");
    }

    private boolean subjectValid(DecodedJWT decodedRefreshToken, DecodedJWT decodedLogoutRefreshToken) {
        return decodedRefreshToken.getSubject().equals(decodedLogoutRefreshToken.getSubject());
    }

    private boolean tokenTypesValid(DecodedJWT refreshToken, DecodedJWT logoutRefreshToken) {
        return TokenType.REFRESH.name().equals(refreshToken.getClaim(TYPE).asString()) &&
                TokenType.LOGOUT_REFRESH.name().equals(logoutRefreshToken.getClaim(TYPE).asString());
    }
// to check
    private boolean rolesValid(DecodedJWT rolesRefreshToken, DecodedJWT logoutRolesRefreshToken) {
        return rolesRefreshToken.getClaim(ROLE).equals(logoutRolesRefreshToken.getClaim(ROLE));
    }
}
