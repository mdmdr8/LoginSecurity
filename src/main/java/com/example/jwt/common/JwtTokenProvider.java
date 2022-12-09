package com.example.jwt.common;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	private String secretKey = "cookcrewcookcrewcookcrewcookcrewcookcrewcookcrewcookcrewgoodproject";
//	시간은 밀리세컨드 타입이라서 long, 토큰 유효시간 :1시간
	private long tokenValidTime = 60*60*1000L;
	
	private final UserDetailsService userDetailsService;
	
	@PostConstruct //생성된 후 다른 객체에 주입되기 전에 실행
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		}			
	
	//JWT 토큰 생성
	public String createToken(String userPK) {
//		JWT payload에 저장되는 정보단위, 보통 user를 식별하는 값을넣는다.
		Claims claims = Jwts.claims().setSubject(userPK);
//		claims.put(""); 다른 정보도 넣을 수 있다.
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims)
				.setId(userPK)
				.setIssuedAt(now) //만들어진 시간
				.setExpiration(new Date(now.getTime()+tokenValidTime)) //만료시간
				.signWith(SignatureAlgorithm.HS256,secretKey) //알고리즘 이용해서 키로 서명
				.compact();
	}
	public String refreshToken(String userPK) {
//		JWT payload에 저장되는 정보단위, 보통 user를 식별하는 값을넣는다.
		Claims claims = Jwts.claims().setSubject(userPK);
//		claims.put(""); 다른 정보도 넣을 수 있다.
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims)
				.setId(userPK)
				.setIssuedAt(now) //만들어진 시간
				.setExpiration(new Date(now.getTime()+tokenValidTime*5)) //만료시간
				.signWith(SignatureAlgorithm.HS256,secretKey) //알고리즘 이용해서 키로 서명
				.compact();
	}
	//토큰에서 회원정보 추출
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	//JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
	}
	//request헤더에서 엑세스토큰값을 가져온다. "authorization : token"
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}
	
	//토큰의 유효성 + 만료시간 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claimSpec = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claimSpec.getBody().getExpiration().before(new Date()); //만료시간 체크
		}catch(Exception e) {
			return false;
		}
	}

}
