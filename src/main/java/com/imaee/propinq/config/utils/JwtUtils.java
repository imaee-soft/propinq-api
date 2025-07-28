package com.imaee.propinq.config.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import static com.imaee.propinq.config.utils.Constants.TTL_JWT_TOKEN;
@Component
public class JwtUtils {
    
    @Value("${security.jwt.key}")
    private String privateKey;
    
    @Value("${security.jwt.user}")
    private String userGenerator;

    public String createToken(Authentication authentication){

        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();

        String authorities = authentication.getAuthorities()

            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        String jwtToken = JWT.create()

            .withIssuer(this.userGenerator)
            .withSubject(username)
            .withClaim("authorities", authorities)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + TTL_JWT_TOKEN))
            .withJWTId(UUID.randomUUID().toString())
            .withNotBefore(new Date(System.currentTimeMillis()))
            .sign(algorithm);

        return jwtToken;
    }

    public DecodedJWT validatetoken(String jwtToken){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(userGenerator)
                .build();
                
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            return decodedJWT;

        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token Invalid, not Authorized", exception);
        }
    }

    public String extractUserName(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJwt){ 
        return decodedJwt.getClaims();
    }
}