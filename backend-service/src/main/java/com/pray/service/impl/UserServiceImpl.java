package com.pray.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.pray.common.UserHolder;
import com.pray.entity.User;
import com.pray.entity.dto.LoginFormDto;
import com.pray.entity.dto.RegisterDto;
import com.pray.entity.dto.UserDto;
import com.pray.entity.po.LoginUser;
import com.pray.entity.vo.response.AuthorizeVO;
import com.pray.mapper.LoginUserMapper;
import com.pray.mapper.UserMapper;
import com.pray.service.UserService;
import com.pray.utils.PrayConstants;
import com.pray.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.pray.utils.PrayConstants.LOGIN_USER_KEY;
import static com.pray.utils.PrayConstants.REGISTER_USER_CODE;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements UserService, UserDetailsService {

    @Resource
    private Producer producer;
    @Resource
    private UserMapper userMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Value("${spring.account}")
    private String sendAccount;

    @Async
    public void sendMailMessage(List<String> to, String subject, String text) {
        log.info("发送邮件===================");
        log.info("to：{}", JSON.toJSONString(to));
        log.info("subject：{}", subject);
        log.info("text：{}", text);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sendAccount);
        mailMessage.setSubject(subject);
        mailMessage.setTo(to.get(0));
        mailMessage.setSentDate(new Date());
        mailMessage.setText(text);
        log.info("发送成功==================");
    }

    @Override
    public Result register(RegisterDto registerDto) {
        List<LoginUser> list = query().eq("username", registerDto.getUsername())
                .eq("email", registerDto.getEmail()).list();
        if (!list.isEmpty()) {
            return Result.fail("用户已存在，请前往登录");
        }
        String isValidated = stringRedisTemplate.opsForValue().get(REGISTER_USER_CODE + registerDto.getEmail());
        if (isValidated == null) {
            return Result.fail("请先获取验证码");
        }
        if (!isValidated.equals(registerDto.getCode())) {
            return Result.fail("验证码错误");
        }
        LoginUser user = new LoginUser();
        registerDto.setPassword(new BCryptPasswordEncoder().encode(registerDto.getPassword()));
        BeanUtil.copyProperties(registerDto, user);
        save(user);
        return Result.ok(user, "注册成功");
    }

    @Override
    public Result sendCode(String email) {
        String codeKey = REGISTER_USER_CODE + email;
        String code = RandomUtil.randomNumbers(6);

        List<String> mail = new ArrayList<>();
        mail.add(email);
        try {
            sendMailMessage(mail, "您有一封邮件", code);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.message(500, "验证码发送失败，请检查输入邮箱格式");
        }
        stringRedisTemplate.opsForValue().set(codeKey, code, 3, TimeUnit.MINUTES);
        return Result.message(200, "验证码发送成功，有效期三分钟");
    }

    //自从3.0之后抛弃javax.servlet
    @Override
    public void imgValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");//设置返回数据类型为图片
        response.setStatus(200);
        String producerText = producer.createText();
        log.info("生成验证码：{}", producerText);

        //验证码放到Redis里面
        stringRedisTemplate.opsForValue().set(PrayConstants.VALID_CODE, producerText);
        BufferedImage bufferedImage = producer.createImage(producerText);//缓冲图片流生成图片验证码
        ServletOutputStream outputStream = response.getOutputStream();

        ImageIO.write(bufferedImage, "jpeg", outputStream);//写入到响应输出流里面
        //刷新输出流
        outputStream.flush();
    }

    @Override
    public Result<User> getUserInfo(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        List<User> userList = userMapper.selectList(queryWrapper);
        if (userList.isEmpty()){
            return Result.fail("不存在该用户");
        }
        return Result.ok(userList.get(0));
    }

    @Override
    @Deprecated
    public Result login(HttpServletRequest request, LoginFormDto loginFormDto) throws Exception {
        String username = loginFormDto.getUsername();
        String password = loginFormDto.getPassword();
        List<LoginUser> list = query().eq("username", username).eq("password", password).list();

        if (list.isEmpty()) {
            return Result.fail("不存在该用户，登录失败");
        }
        LoginUser account = list().get(0);
        UserDto dto = BeanUtil.copyProperties(account, UserDto.class);
        UserHolder.setLocalUser(dto);//存入当前用户信息
        String token = "authorization";
        //封装用户信息为map添加到redis
        String header = request.getHeader("authorization");
        if (!token.equals(header)) {
            String key = LOGIN_USER_KEY + token;
            Map<String, Object> map = BeanUtil.beanToMap(dto, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((o1, o2) -> o2.toString())
            );
            stringRedisTemplate.opsForHash().putAll(key, map);
            stringRedisTemplate.expire(key, 3, TimeUnit.MINUTES);
        }
        //返回token给前端,封装一个对象拷贝
        AuthorizeVO authorizeVO = dto.asViewObject(AuthorizeVO.class, authorize -> {
            authorize.setUsername(username);
            authorize.setExpireTime(new Date());
            authorize.setToken(token);
        });

        return Result.ok(authorizeVO, "登录成功");
    }

    @Resource
    LoginUserMapper loginUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        LoginUser user = loginUserMapper.loadUserByUsername(username);//通过数据库查询用户是否存在
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        } else {
            LoginUser userDetails = new LoginUser();
            userDetails.setUsername(username);
            userDetails.setPassword(user.getPassword());
            return userDetails;
        }
    }
}


