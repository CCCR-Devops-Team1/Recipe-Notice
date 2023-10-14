package com.recipe.recipearticle.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemberInfo {
  public Boolean isSuccess;
  public int code;
  public Result result;
  @Data
  public class Result{
    public long id;
    public String account;
    public String pw;
    public String createDate;
    public String updateDate;
    public List<GrantedAuthority> grantedAuthorities;
    public Boolean admin;
    @Data
    public static class GrantedAuthority {
      public String authority;
    }
  }
}