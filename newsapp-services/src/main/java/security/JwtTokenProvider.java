package security;

import io.jsonwebtoken.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.util.Base64;
import java.util.Date;

@ApplicationScoped
public class JwtTokenProvider {


    private String secretKey = "secret";

    private String header = "Authorization";

    private long validityInSeconds = 60*60*24;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(CredentialValidationResult result) {

        Claims claims = Jwts.claims().setSubject(result.getCallerPrincipal().getName());
        claims.put("roles", result.getCallerGroups());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInSeconds*1000);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validity)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (!claimsJws.getBody().getExpiration().before(new Date())) {
                return claimsJws;
            }
        } catch (JwtException e) {
            throw new JwtException("Jwt token is expired or invalid");
        }
        return null;
    }
}
