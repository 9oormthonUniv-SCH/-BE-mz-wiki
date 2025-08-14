package com.example.demo.security;

import com.example.demo.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class MemberDetails implements UserDetails {

    private final User user;

    public MemberDetails(User user) {
        this.user = user;
    }

    public Long getId() {
        return user.getId();  // 이걸 통해 controller에서 memberDetails.getId() 사용 가능
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 권한 필요 없으면 null 처리
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();  // 로그인 식별자로 이메일 사용 예시
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
