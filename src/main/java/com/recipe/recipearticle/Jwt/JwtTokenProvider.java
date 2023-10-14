package com.recipe.recipearticle.Jwt;

import com.recipe.recipearticle.Exception.BaseException;
import com.recipe.recipearticle.Security.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.recipe.recipearticle.Dto.Response.ResponseStatus.EXPIRED_JWT;
import static com.recipe.recipearticle.Dto.Response.ResponseStatus.INVALID_JWT;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  @Value("${jwt.secretKey}")
  private String secretkey;
  private final RedisTemplate<String, String> redisTemplate;
  @Value("${jwt.token.access-expiration-time}")
  private long accessExpirationTime;

  @Value("${jwt.token.refresh-expiration-time}")
  private long refreshExpirationTime;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;



  public boolean validateToken(String token){
    try{
      Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
      return true;
    }catch (ExpiredJwtException e){
      throw new BaseException(EXPIRED_JWT);
    }catch (JwtException e){
      throw new BaseException(INVALID_JWT);
    }
  }

  public Authentication getAuthentication(String token){
    String account = Jwts.parser()
        .setSigningKey(secretkey)
        .parseClaimsJws(token)
        .getBody().get("sub").toString();
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(account);
    return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
  }
  public String getUserAccount(String token) { return extractClaims(token, secretkey).get("sub").toString();}
  public boolean isExpired(String token){
    Date expiredDate = extractClaims(token, secretkey).getExpiration();
    return expiredDate.before(new Date());
  }
  public static Claims extractClaims(String token, String secretkey){
    return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
  }
}