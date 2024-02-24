package com.pray.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 * IndexController
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/8/24
 */
@RestController
public class IndexController {

    private String url;

    @Value(value = "${url}")
    public void setUrl(String url) {
        this.url = url+":";
    }
    @RequestMapping("/")
    public void root(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }
}
