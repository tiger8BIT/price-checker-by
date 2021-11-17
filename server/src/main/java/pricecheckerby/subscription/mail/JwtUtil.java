package pricecheckerby.subscription.mail;

import io.jsonwebtoken.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

public class JwtUtil {
    public static String createJWT(final String email, final Long subscriptionId, final String SECRET_KEY, int expirationDays) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, expirationDays);
        Map<String, Object> tokenData = (
                new TokenData(email, subscriptionId, LocalDateTime.now())).getMap();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setExpiration(calendar.getTime())
                .setClaims(tokenData)
                .signWith(signatureAlgorithm, signingKey);
        return jwtBuilder.compact();
    }
    private static Optional<Claims> decodeJWT(String jwt, final String SECRET_KEY) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt).getBody();
            return Optional.of(claims);
        } catch (SignatureException | MalformedJwtException |
                ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
    public static Optional<TokenData> tokenDataFrom(String jwt, final String SECRET_KEY) {
        Optional<Claims> optionalClaims = decodeJWT(jwt, SECRET_KEY);
        if (optionalClaims.isEmpty()) return Optional.empty();
        Claims claims = optionalClaims.get();
        return Optional.of(new TokenData(claims));
    }
}
