package br.com.hfs.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.hfs.model.User;
import br.com.hfs.repository.UserRepository;

public class TokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	
	private UserRepository userRepository;

	public TokenFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = retrieveToken(request);
		
		boolean valid = tokenService.isValidToken(token);
		
		if (valid) {
			authenticateUser(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void authenticateUser(String token) {
		Long idUser = tokenService.getIdUser(token);
		User user = userRepository.findById(idUser).get();
		HfsUserDetails hfsUserDetails = new HfsUserDetails(user);
		
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(hfsUserDetails, null, hfsUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String retrieveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
