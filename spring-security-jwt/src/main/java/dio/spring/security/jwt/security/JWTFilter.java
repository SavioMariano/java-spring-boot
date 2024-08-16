package dio.spring.security.jwt.security;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static java.util.stream.Collectors.toList;

public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtenção do token a partir do cabeçalho Authorization
        String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);

        try {
            // Verificação se o token está presente e não vazio
            if (token != null && !token.isEmpty()) {
                // Criação do JWTObject a partir do token
                JWTObject tokenObject = JWTCreator.create(token, SecurityConfig.PREFIX, SecurityConfig.KEY);

                // Transformando as roles em SimpleGrantedAuthority
                List<SimpleGrantedAuthority> authorities = authorities(tokenObject.getRoles());

                // Criação do UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                        tokenObject.getSubject(),
                        null,
                        authorities);

                // Configuração do contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(userToken);
            } else {
                // Limpa o contexto de segurança se o token não estiver presente
                SecurityContextHolder.clearContext();
            }
            // Continua a execução da cadeia de filtros
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
    }

    // Método auxiliar para converter as roles para SimpleGrantedAuthority
    private List<SimpleGrantedAuthority> authorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }
}
