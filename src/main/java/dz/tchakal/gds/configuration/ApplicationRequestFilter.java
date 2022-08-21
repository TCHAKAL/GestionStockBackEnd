package dz.tchakal.gds.configuration;

import dz.tchakal.gds.service.authentication.ApplicationUserDetailService;
import dz.tchakal.gds.util.JWTUtil;
import dz.tchakal.gds.util.StaticUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApplicationRequestFilter extends OncePerRequestFilter {//OncePerRequestFilter toute requete doit etre filtré

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ApplicationUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(StaticUtil.AUTHORIZATION_HEADER);
        String email = null,
                jwt = null,
                entreprise = null;

        if (authHeader != null && StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);//Bearer  a 7 caratères
            email = jwtUtil.extractUsername(jwt);
            entreprise = jwtUtil.extractEntreprise(jwt);
        }
        //Vérifier si jwt token est valide
        if (email != null && StringUtils.hasLength(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Récupérer l'utilisateur
            UserDetails userDetails = this.userDetailService.loadUserByUsername(email);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        //Stocker l'id de l'entreprise
        MDC.put("entreprise", entreprise);
        filterChain.doFilter(request, response);
    }


}