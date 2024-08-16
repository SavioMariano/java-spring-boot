package dio.spring.security.jwt.security;

import java.security.SignatureException;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "authorization";
    public static final String ROLES_AUTHORIZATION = "authorities";

    public static String create(String prefix, String key, JWTObject jwtObject){
        String token = Jwts.builder().setSubject(jwtObject.getSubject()).setIssuedAt(jwtObject.getIssueAt()).setExpiration(jwtObject.getExpiration())
        .claim(ROLES_AUTHORIZATION, checkRoles(jwtObject.getRoles())).signWith(SignatureAlgorithm.HS512, key).compact();
        return prefix + " " + token;
    }

    public static JWTObject create(String token, String prefix, String key)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException{
        JWTObject object = new JWTObject();
        token = token.replace(prefix, "");
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(key).getBody();
        object.setSubject(claims.getSubject());
        object.setExpiration(claims.getExpiration());
        object.setIssueAt(claims.getIssuedAt());
        object.setRoles((List) claims.get(ROLES_AUTHORIZATION));
        return object;
    }

    private static List<String> checkRoles(List<String> roles){
        return roles.stream().map(s -> "ROLE_". concat(s.replace("ROLE_", ""))).collect(Collectors.toList());
    }
}
