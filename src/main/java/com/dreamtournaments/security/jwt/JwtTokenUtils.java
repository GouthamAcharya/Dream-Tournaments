package com.dreamtournaments.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.dreamtournaments.services.UserDetailsImpl;

import io.jsonwebtoken.*;



@Component
public class JwtTokenUtils {

	private static final Logger logger=LoggerFactory.getLogger(JwtTokenUtils.class);
	
	@Value("${kloud.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${kloud.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public String generateJwtToken(Authentication authentication) {
		
		UserDetailsImpl userPrincipal=(UserDetailsImpl) authentication.getPrincipal();
		
		logger.debug("in generateJwtToken(Authentication authentication)");
		
//		Key key = MacProvider.generateKey();
//		String signingKeyB64 = Base64.getEncoder().encodeToString(key.getEncoded());

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date().getTime())+jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUserNameFromJwtToken(String token) {
		logger.debug("in getUserNameFromJwtToken(String token)");
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		
		logger.debug("in validateJwtToken(String authToken)");
		
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		}catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		}catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		}catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		}catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		
		return false;
	}
	
}
