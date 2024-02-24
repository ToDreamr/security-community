package com.pray.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.pray.common.UserHolder;
import com.pray.entity.dto.LoginFormDto;
import com.pray.entity.dto.RegisterDto;
import com.pray.entity.dto.UserDto;
import com.pray.entity.po.LoginUser;
import com.pray.entity.vo.response.AuthorizeVO;
import com.pray.mapper.LoginUserMapper;
import com.pray.service.AccountService;
import com.pray.utils.PrayConstants;
import com.pray.utils.Result;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.pray.utils.PrayConstants.LOGIN_USER_KEY;
import static com.pray.utils.PrayConstants.REGISTER_USER_CODE;

@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements AccountService, UserDetailsService {

    @Resource
    private Producer producer;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    JavaMailSender mailSender;
    @Value("${spring.account}")
    private String sendAccount;

    private String mailText;
    @PostConstruct
    public void init(){
        this.mailText=
                "<head>\n" +
                        "    <base target=\"_blank\"/>\n" +
                        "    <style id=\"scrollbar\" type=\"text/css\">::-webkit-scrollbar {\n" +
                        "        width: 0 !important\n" +
                        "    }\n" +
                        "\n" +
                        "    pre {\n" +
                        "        white-space: pre-wrap !important;\n" +
                        "        word-wrap: break-word !important;\n" +
                        "        *white-space: normal !important\n" +
                        "    }\n" +
                        "\n" +
                        "    pre {\n" +
                        "        white-space: pre-wrap !important;\n" +
                        "        word-wrap: break-word !important;\n" +
                        "        *white-space: normal !important\n" +
                        "    }\n" +
                        "\n" +
                        "    #letter img {\n" +
                        "        max-width: 300px\n" +
                        "    }</style>\n" +
                        "    <style id=\"from-wrapstyle\" type=\"text/css\">#form-wrap {\n" +
                        "        overflow: hidden;\n" +
                        "        height: 447px;\n" +
                        "        position: relative;\n" +
                        "        top: 0px;\n" +
                        "        transition: all 1s ease-in-out .3s;\n" +
                        "        z-index: 0\n" +
                        "    }</style>\n" +
                        "    <style id=\"from-wraphoverstyle\" type=\"text/css\">#form-wrap:hover {\n" +
                        "        height: 1300px;\n" +
                        "        top: -200px\n" +
                        "    }</style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div style=\"width: 530px;margin: 20px auto 0;height: 1000px;\">\n" +
                        "    <div id=\"form-wrap\"><img src=\"https://upyun.thatcdn.cn/public/web/email_template/head_before.png\" alt=\"before\"\n" +
                        "                             style=\"position: absolute;bottom: 126px;left: 0px;background-repeat: no-repeat;width: 530px;height: 317px;z-index:-100\">\n" +
                        "        <div style=\"position: relative;overflow: visible;height: 1500px;width: 500px;margin: 0px auto;transition: all 1s ease-in-out .3s;padding-top:200px;\"\n" +
                        "        <form>\n" +
                        "            <div style=\"background: white;width: 95%;max-width: 800px;margin: auto auto;border-radius: 5px;border: 1px solid;overflow: hidden;-webkit-box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.12);box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.18);\">\n" +
                        "                <img style=\"width:100%;overflow: hidden;\"\n" +
                        "                     src=\"https://upyun.thatcdn.cn/public/web/email_template/head_wely.jpg\"/>\n" +
                        "                <div style=\"padding: 5px 20px;\"><br>\n" +
                        "                    <div><h3 style=\"text-decoration: none; color: rgb(246, 214, 175);\">尊敬的客户，见信安：</h3>\n" +
                        "                    </div>\n" +
                        "                    <br>\n" +
                        "                    <div id=\"letter\"\n" +
                        "                         style=\"overflow:auto;height:300px;width:100%;display:block;word-break: break-all;word-wrap: break-word;\">\n" +
                        "                        <p>您的验证码是:</p>\n" +
                        "                        <div id=\"selfC\"\n" +
                        "                             style=\"border-bottom: #ddd 1px solid;border-left: #ddd 1px solid;padding-bottom: 20px;background-color: #eee;margin: 15px 0px;padding-left: 20px;padding-right: 20px;border-top: #ddd 1px solid;border-right: #ddd 1px solid;padding-top: 20px;font-family: 'Arial', 'Microsoft YaHei' , '黑体' , '宋体' , sans-serif;\">\n" +
                        "                            2713\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                    <br>\n" +
                        "                    <div style=\"text-align: center;margin-top: 40px;\"><img\n" +
                        "                            src=\"https://upyun.thatcdn.cn/public/web/email_template/footer_bilibili.png\" alt=\"hr\"\n" +
                        "                            style=\"width:100%; margin:5px auto 5px auto; display: block;\"/><a\n" +
                        "                            style=\"text-transform: uppercase;text-decoration: none;font-size: 14px;border: 2px solid #6c7575;color: #2f3333;padding: 10px;display: inline-block;margin: 10px auto 0;background-color: rgb(246, 214, 175);\"\n" +
                        "                            target=\"_blank\" href=\"https://todreamr.github.io\">{{ parent.nick }}｜请您点击签收~</a></div>\n" +
                        "                    <p style=\"font-size: 12px;text-align: center;color: #999;\"><br>薇尔莉特·伊芙加登<br>自动书记人偶竭诚为您服务！<br>©2020-2023<a\n" +
                        "                            style=\"text-decoration:none; color:rgb(246, 214, 175)\"\n" +
                        "                            href=\"https://todreamr.github.io\">春江花朝秋月夜</a></p></div>\n" +
                        "            </div>\n" +
                        "        </form>\n" +
                        "    </div>\n" +
                        "    <img src=\"https://upyun.thatcdn.cn/public/web/email_template/head_after.png\" alt=\"after\"\n" +
                        "         style=\"      position: absolute;bottom: -2px;left: 0;background-repeat: no-repeat;width: 530px;height: 259px;z-index:100\">\n" +
                        "</div>\n" +
                        "</div></body>\n";
    }
    @Override
    public Result register(RegisterDto registerDto) {
        List<LoginUser> list = query().eq("username", registerDto.getUsername())
                .eq("email", registerDto.getEmail()).list();
        if (!list.isEmpty()){
            return Result.fail("用户已存在，请前往登录");
        }
        String isValidated = stringRedisTemplate.opsForValue().get(REGISTER_USER_CODE + registerDto.getEmail());
        if (isValidated==null){
            return Result.fail("请先获取验证码");
        }
        if (!isValidated.equals(registerDto.getCode())){
            return Result.fail("验证码错误");
        }
        LoginUser user = new LoginUser();
        registerDto.setPassword(new BCryptPasswordEncoder().encode(registerDto.getPassword()));
        BeanUtil.copyProperties(registerDto, user);
        save(user);
        return Result.ok(user,"注册成功");
    }

    @Override
    public Result sendCode(String email)
    {
        String codeKey = REGISTER_USER_CODE + email;
        String code = RandomUtil.randomNumbers(6);

        try
        {
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mailSender.createMimeMessage(),true);
            mimeMessageHelper.setFrom(sendAccount);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("【验证码信息】");
            mimeMessageHelper.setText(getMailText(),true);
            mailSender.send(mimeMessageHelper.getMimeMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.message(500,"验证码发送失败，请检查输入邮箱格式");
        }
        stringRedisTemplate.opsForValue().set(codeKey,code,3, TimeUnit.MINUTES);
        return Result.message(200,"验证码发送成功，有效期三分钟");
    }

    //自从3.0之后抛弃javax.servlet
    @Override
    public void imgValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");//设置返回数据类型为图片
        response.setStatus(200);
        String producerText = producer.createText();
        log.info("生成验证码：{}",producerText);

        //验证码放到Redis里面
        stringRedisTemplate.opsForValue().set(PrayConstants.VALID_CODE,producerText);
        BufferedImage bufferedImage=producer.createImage(producerText);//缓冲图片流生成图片验证码
        ServletOutputStream outputStream = response.getOutputStream();

        ImageIO.write(bufferedImage,"jpeg",outputStream);//写入到响应输出流里面
        //刷新输出流
        outputStream.flush();
    }
    @Override
    @Deprecated
    public Result login(HttpServletRequest request, LoginFormDto loginFormDto) throws Exception {
        String username = loginFormDto.getUsername();
        String password = loginFormDto.getPassword();
        List<LoginUser> list = query().eq("username", username).eq("password", password).list();

        if (list.isEmpty()){
            return Result.fail("不存在该用户，登录失败");
        }
        LoginUser account = list().get(0);
        UserDto dto = BeanUtil.copyProperties(account, UserDto.class);
        UserHolder.setLocalUser(dto);//存入当前用户信息
        String token = "authorization";
        //封装用户信息为map添加到redis
        String header = request.getHeader("authorization");
        if (!token.equals(header)){
            String key = LOGIN_USER_KEY + token;
            Map<String, Object> map = BeanUtil.beanToMap(dto, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((o1, o2) -> o2.toString())
            );
            stringRedisTemplate.opsForHash().putAll(key,map);
            stringRedisTemplate.expire(key,3,TimeUnit.MINUTES);
        }
        //返回token给前端,封装一个对象拷贝
        AuthorizeVO authorizeVO= dto.asViewObject(AuthorizeVO.class,authorize->{
            authorize.setUsername(username);
            authorize.setExpireTime(new Date());
            authorize.setToken(token);
        });

        return Result.ok(authorizeVO,"登录成功");
    }

    @Resource
    LoginUserMapper loginUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username){
        LoginUser user = loginUserMapper.loadUserByUsername(username);//通过数据库查询用户是否存在
        if (user==null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        else {
            LoginUser userDetails = new LoginUser();
            userDetails.setUsername(username);
            userDetails.setPassword(user.getPassword());
            return userDetails;
        }
    }
    public String getMailText() {
        return mailText;
    }
}


