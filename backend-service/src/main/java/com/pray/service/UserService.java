package com.pray.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pray.entity.User;
import com.pray.entity.dto.LoginFormDto;
import com.pray.entity.dto.RegisterDto;
import com.pray.entity.po.LoginUser;
import com.pray.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

/**
* @author Rainy-Heights
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-08-24 13:00:41
*/
public interface UserService extends IService<LoginUser> , UserDetailsService {
    Result login(HttpServletRequest request, LoginFormDto loginFormDto) throws Exception;

    Result register(RegisterDto registerDto);

    Result sendCode(String email);

    void imgValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException;

    Result<User> getUserInfo(String username);
}
