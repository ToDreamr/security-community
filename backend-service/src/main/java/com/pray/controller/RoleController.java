package com.pray.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pray.entity.po.Role;
import com.pray.service.RoleService;
import com.pray.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * RoleController
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/8/24
 */

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/page")
    public Result<List<Role>> pageInfo(HttpServletRequest request,
                           @RequestParam int current,
                           @RequestParam int limit) throws JsonProcessingException {
        List<Role> data = roleService.listRole(current, limit);
        return Result.ok(data);
    }
    @PostMapping("/update_page")
    public Result<Role> updatePageInfo(@RequestBody Role role) throws JsonProcessingException {
        //返回插入的更新数据
        return Result.ok(role,roleService.updatePageWithMutex(role));
    }
}
