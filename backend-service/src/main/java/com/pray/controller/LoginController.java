package com.pray.controller;

import com.pray.common.UserHolder;
import com.pray.entity.dto.RegisterDto;
import com.pray.service.UserService;
import com.pray.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * <p>
 * LoginController
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/8/24
 */
@RequestMapping("/auth")
@RestController
public class LoginController<T> {
    @Resource
    private UserService userService;

    @GetMapping("/local")
    public Result getLocal(){
        return Result.ok(UserHolder.getLocalUser(),null);
    }
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto){
        System.out.println(registerDto);
        return userService.register(registerDto);
    }
    @PostMapping("/code")
    public Result validateCode(@RequestParam String email){
        return userService.sendCode(email);
    }
    @GetMapping("/captchaImage")
    public void imgValidateCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        userService.imgValidateCode(request,response);
    }
}
