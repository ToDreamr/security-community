package com.pray.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pray.entity.po.PrayMessage;
import com.pray.service.PrayMessageService;
import com.pray.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * AdminController
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/10 0:15
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    PrayMessageService messageService;
    @Resource
    SessionRegistry sessionRegistry;
    @GetMapping("/publish")
    public Result<List<PrayMessage>> publish() throws JsonProcessingException {
        return Result.ok(messageService.selectLatestPublish());
    }
    @GetMapping("/kick")
    //@Secured(value = "user:admin")
    //@PreAuthorize(value = "hasRole('user:admin')")//传入的角色或者逻辑需要满足里面的表达式，这种不好用
    public Result<String> kick(@RequestParam String usename){
        kickUser(usename);
        return Result.ok("剔除下线成功");
    }
    private void kickUser(String username) {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (Object principles:allPrincipals){
            List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principles, false);
            User user= (User) allPrincipals;

            if (user.getUsername().equals(username)){
                allSessions.forEach(SessionInformation::expireNow);//将所有的符合条件的用户信息剔除掉
            }
        }
    }
}
