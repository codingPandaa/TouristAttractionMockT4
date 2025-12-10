package com.practice.wingsMockT4.utility;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "qwertyuioplkjhgfdsazxcvbnmnbvcxzasdf";
	private static final long EXPIRATION_TIME = 1000 * 60 * 60;
	private SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	public String generateToken(String username) {

		Date now = new Date();

		Date expiry = new Date(now.getTime() + EXPIRATION_TIME);

		String token = Jwts.builder().subject(username).issuedAt(now).expiration(expiry).signWith(key).compact();

		return token;

	}

	public String extractUsername(String token) {

		String username = extractAllClaims(token).getSubject();

		return username;
	}

	public boolean validateToke(String token) {

		try {
			extractAllClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Claims extractAllClaims(String token) {

		Jws<Claims> jws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

		Claims payload = jws.getPayload();

		return payload;
	}
}
