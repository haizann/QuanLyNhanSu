package com.example.quanlyns.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.example.quanlyns.entity.dto.LoginDTO;
import com.example.quanlyns.entity.response.ApiResponse;
import com.example.quanlyns.util.SecurityUtil;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final com.example.quanlyns.util.SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginDTO loginDTO) {
        // nap input vao security
        /*
         * usernamePasswordAuthenticationToken tra ve:
         * principal = username
         * credentials = password
         * authorities = null (chưa có quyền)
         * authenticated = false
         */
        // dong goi nguoi dung vao
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // xac thuc nguoi dung => viet ham loadUserByUserName
        /*
         * authentication tra ve:
         * principal = UserDetails (đầy đủ info)
         * credentials = null (đã bị xoá)
         * authorities = ROLE_USER, ROLE_ADMIN, ...
         * authenticated = true
         */
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(usernamePasswordAuthenticationToken);

        // tao token
        String access_token = this.securityUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var result = new ApiResponse<>(HttpStatus.OK, "createToken", access_token, null);
        return ResponseEntity.ok().body(result);
    }

}
