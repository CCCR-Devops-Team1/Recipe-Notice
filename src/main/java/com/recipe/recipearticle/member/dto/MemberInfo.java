package com.recipe.recipearticle.member.dto;

import java.util.List;
import lombok.Data;

@Data
public class MemberInfo {
    public Boolean isSuccess;
    public int code;
    public Result result;

    @Data
    public class Result {
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