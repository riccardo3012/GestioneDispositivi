package security;
import entities.User;
import exception.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String createToken(User user) { // mi serve id per metterlo nel payload
        return Jwts.builder().setSubject(String.valueOf(user.getId())) //imposto il subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // imposto data emissione
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3))// imposto data scadenza
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();//aggiungi firma che parte da un segreto (secret.)
    }

    public void verifyToken(String token) {//metodo per verificare il token
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception e) {
            throw new UnauthorizedException("Il token non è valido");
        }
    }

    public String idFromToken(String token) {//metodo per tornare lid dal token
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject();
    }
}