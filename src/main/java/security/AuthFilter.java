package security;


import entities.User;
import exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import security.JWTTools;
import service.UserService;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Verifico l'header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserire token in Authorization Header");
        } else {
            // 2. Si estrae il T dal Header
            String token = authHeader.substring(7);
            System.out.println(token);
            jwtTools.verifyToken(token);
            // 3. Devo cercare l'user nel database
            String id = jwtTools.idFromToken(token);
            User foundUser = userService.findUserById(Integer.parseInt(id));
            // 4. Autorizziamo  l'user (user autenticato)                                                      //rappresenta i ruoli dell utente permettendo a spring di abilitare i ruoli
            Authentication authentication = new UsernamePasswordAuthenticationToken(foundUser, null, foundUser.getAuthorities());//credeziali utente per maggiori dati
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }
    //specifichiamo quando jwtfiltr può ignorare
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
   }
}
//il filtro verifa la presenza e "spacchetta" autorizzation header per estrarre il token