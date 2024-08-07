package pl.gruszm.ZephyrWork.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gruszm.ZephyrWork.config.TokenConfig;

import java.util.Date;

@Component
public class JwtUtils
{
    private TokenConfig tokenConfig;

    @Autowired
    public JwtUtils(TokenConfig tokenConfig)
    {
        this.tokenConfig = tokenConfig;
    }

    public String createToken(String userEmail)
    {
        return JWT.create()
                .withSubject("UserDetails")
                .withClaim("userEmail", userEmail)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (tokenConfig.getExpirationTimeDays() * 1000 * 86400)))
                .sign(Algorithm.HMAC512(tokenConfig.getSecret()));
    }


    public UserDetails readToken(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC512(tokenConfig.getSecret());

            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject("UserDetails")
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            UserDetails userDetails = new UserDetails(jwt.getClaim("userEmail").asString());

            return userDetails;
        }
        catch (JWTVerificationException e)
        {
            return null;
        }
    }
}
