package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor

// Spring Security 설정들을 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService customOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                // .csrf().disable().headers().frameOptions().disable()
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    
                    // authorizeRequest
                    // URL별 권한 관리를 설정하는 옵션의 시작점
                    // authorizeRequest가 선언되어야만 antMatachers 옵션을 사용할 수 있음.
                    .authorizeRequests()
                    
                    // 권한 관리 대사을 지정하는 옵션 / URL,HTTP 메소드별로 관리 가능
                    // "/" 등 지정된 URL 들은 permitAll()옵션을 통해 전체 열람 권한 할당
                    // "/api/v1/**" 주소를 가진 API는 User 권한을 가진사람만 가능하도록 할당
                    .antMatchers("/","/css/**,","images/**","/js/**","/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                
                    // anyRequest 
                    // 설정된 값들 이외 나머지 URL을 나타냄.
                    // 여기서는 authenticated()을 추가하ㅏ여 나머지 URL들은 모두 인증된 사용자에게만 허용(로그인한 사용자)
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        
                        // 로그아웃 기능에 대한 여러 설정의 진입점/ 로그아웃 성공시 "/" 주소로 이동
                        .logoutSuccessUrl("/")
                .and()

                    // Oauth2 로그인 기능에 대한 여러 설정의 진입점
                    .oauth2Login()

                        // Oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                        .userInfoEndpoint()

                            // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
                            // 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자하는 기능 명시 가능
                            .userService(customOauth2UserService);
    }
}
