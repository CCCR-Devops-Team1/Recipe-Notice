package com.recipe.recipearticle.Jwt;

import com.recipe.recipearticle.Exception.BaseException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.recipe.recipearticle.Dto.Response.ResponseStatus.*;

public class JwtTokenFilter extends OncePerRequestFilter {
  private JwtTokenProvider jwtTokenProvider;
  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider){
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    System.out.println("JwtTokenFilter 실행");
    String token = request.getHeader("Authorization");
    try{
      // String.utils()를 통해서 유효성 검사 가능
      if(token == null || !token.startsWith("Bearer ")){
        throw new BaseException(INVALID_JWT);
      }
      token = token.replace("Bearer ","");

      if(token != null && jwtTokenProvider.validateToken(token)){
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }catch (RedisConnectionFailureException e){
      SecurityContextHolder.clearContext();
      throw new BaseException(REDIS_ERROR);
    } catch (BaseException e){
      if(e.getStatus().equals("유효하지 않은 JWT입니다.")){
        throw new BaseException(INVALID_JWT);
      }else{
        throw new BaseException(EXPIRED_JWT);
      }

    } catch(Exception e){
      e.printStackTrace();
      throw new BaseException(FAIL_AUTH_JWT);
    }
    filterChain.doFilter(request,response);
  }
}
