# SpringSecurity



SpringSecurity是一个基于Spring开发的非常强大的权限验证框架，其核心功能包括：

- 认证 （用户登录）
- 授权 （此用户能够做哪些事情）
- 攻击防护 （防止伪造身份攻击）

本章，我们会从多个方面对此框架进行介绍，让各位小伙伴能够在自己的项目中使用更加安全和可靠的身份校验方案。

### CSRF跨站请求伪造攻击

CSRF是我们要介绍的第一种攻击形式，这种攻击方式非常好理解。

![image-20230701181244150](https://s2.loli.net/2023/07/01/4ibrwFIPnSE81lx.png)

### SFA会话固定攻击

这同样是利用Cookie中相同的JSESSIONID进行的攻击，会话固定攻击（Session fixation attack）是一种针对Web应用程序的安全漏洞攻击，攻击者利用这种漏洞，将一个有效的会话ID分配给用户，并诱使用户在该会话中进行操作，然后攻击者可以利用该会话ID获取用户的权限，或者通过此会话继续进行其他攻击。

简单来说，就是黑客把他的JSESSIONID直接给你，你一旦使用这个ID登录，那么在后端这个ID就被认定为已登录状态，那么也就等同于他直接进入了已登录状态，从而直接访问你账号的任意内容，执行任意操作。

攻击者通常使用以下几种方式进行会话固定攻击：

1. 会话传递：攻击者通过URL参数、表单隐藏字段、cookie等方式将会话ID传递给用户。当用户使用该会话ID登录时，攻击者就能利用该会话ID获取用户的权限。
2. 会话劫持：攻击者利用劫持用户与服务器之间的通信流量，获取到用户的会话ID，然后利用该会话ID冒充用户进行操作。
3. 会话劫持：攻击者事先获取到会话ID，并将其分配给用户，之后通过其他方式欺骗用户登录该会话。这样，攻击者就可以利用会话ID获取用户的权限。

这里我们来尝试一下第一种方案，这里我们首先用另一个浏览器访问目标网站，此时需要登录，开始之前记得先清理一下两个浏览器的缓存，否则可能无法生效：

为了彻底杜绝这个问题，登录成功之后应该重新给用户分配一个新的JSESSIONID才行，而这些都由SpringSecurity帮我们实现了。

### XSS跨站脚本攻击


XSS（跨站脚本攻击）是一种常见的网络安全漏洞，攻击者通过在合法网站中注入恶意脚本代码来攻击用户。当用户访问受到注入攻击的页面时，恶意代码会在用户的浏览器中执行，从而导致攻击者能够窃取用户的敏感信息、诱导用户操作、甚至控制用户的账号。

XSS攻击常见的方式有三种：

1. 存储型XSS攻击：攻击者将恶意代码存储到目标网站的数据库中，当其他用户访问包含恶意代码的页面时，恶意代码会被执行。
2. 反射型XSS攻击：攻击者将恶意代码嵌入到URL中，当用户点击包含恶意代码的URL时，恶意代码会被执行。
3. DOM-based XSS攻击：攻击者利用前端JavaScript代码的漏洞，通过修改页面的DOM结构来执行恶意代码。

在一些社交网站上，用户可以自由发帖，而帖子是以富文本形式进行编辑和上传的，发送给后台的帖子往往是直接以HTML代码的形式，这个时候就会给黑客可乘之机了。


可以看到`p`标签上添加了一段JS恶意脚本，黑客可以利用这种特性，获取用户的各种信息，甚至直接发送到他的后台，这样，我们的个人信息就从网站内部被泄露了。

XSS漏洞最早被发现是在1996年，由于JavaScript的出现，导致在Web应用程序中存在了一些安全问题。在1997年，高智文(Gareth Owen)也就是“XSS之父”，在他的博客中描述了一种称为“脚本注入”(script injection)的攻击技术，这就是XSS漏洞的前身。从那时起，XSS漏洞便成为了Web应用程序中的一种常见安全漏洞。

![image-20230702004827194](https://s2.loli.net/2023/07/02/DkzJWPxQ5BUl2tC.png)

这种攻击一般需要前端配合后端进行防御，或者后端对前端发送的内容进行安全扫描并处理，有机会我们会分享如何防范此类攻击。

***

## 开发环境配置

我们继续使用之前的测试项目进行教学，首先我们需要导入SpringSecurity的相关依赖，它不仅仅是一个模块，我们可以根据需求导入需要的模块，常用的是以下两个：

```xml
        <dependencies>
  <!--jdbc相关依赖-->
  <!--jdbc相关依赖-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
    <version>3.1.2</version>
  </dependency>

  <!--    授权校验模块    -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>3.1.2</version>
  </dependency>

  <!--基础的web模块-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.1.2</version>
  </dependency>

  <!--mysql驱动-->
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.31</version>
  </dependency>

  <!--mybatis-plus框架-->
  <dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3.1</version>
  </dependency>

  <dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus</artifactId>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <optional>true</optional>
  </dependency>

  <!--测试模块-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>

  <!--hutool工具包-->
  <dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.8.11</version>
  </dependency>

  <!--redis缓存和数据交互-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>3.1.2</version>

  </dependency>

  <dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-redis</artifactId>
  </dependency>

  <dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.9.19</version>

  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
    <version>2.7.5</version>

  </dependency>

  <dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>4.3.0</version>
  </dependency>

  <dependency>
    <groupId>org.jetbrains</groupId>
    <artifactId>annotations</artifactId>
    <version>24.0.1</version>
    <scope>compile</scope>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
    <version>3.1.2</version>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-autoconfigure</artifactId>
    <version>3.1.2</version>
  </dependency>

  <!--        <dependency>-->
  <!--            <groupId>org.springframework.security</groupId>-->
  <!--            <artifactId>spring-security-oauth2-client</artifactId>-->
  <!--        </dependency>-->
  <!--图形验证码工具-->
  <dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <version>3.1.2</version>
  </dependency>

  <!--引入mybatis-plus自动导包依赖-->
  <dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.4.1</version>
  </dependency>

  <!--freemarker -->
  <dependency>
    <groupId>org.freemarker</groupId>
    <artifactId>freemarker</artifactId>
    <version>2.3.32</version>
  </dependency>
  <!--Velocity（默认-->
  <dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity-engine-core</artifactId>
    <version>2.2</version>
  </dependency>
  <!--消息队列客户端-->
  <dependency>
    <groupId>com.rabbitmq</groupId>
    <artifactId>amqp-client</artifactId>
    <version>5.14.2</version>
  </dependency>
</dependencies>

```

## 认证

认证是我们网站的第一步，用户需要登录之后才能进入，这一部分我们将详细介绍如何使用SpringSecurity实现用户登录。


配置完成后，我们就可以前往登录界面，进行登录操作了：

![image-20230702144938540](https://s2.loli.net/2023/07/02/tSGxZmv6jUDMy95.png)


并且为了防止我们之前提到的会话固定问题，在登录之后，JSESSIONID会得到重新分配：


退出登录后就需要重新登录才能访问我们的网站了。

可以发现，在有了SpringSecurity之后，我们网站的登录验证模块相当于直接被接管了，因此，从现在开始，我们的网站不需要再自己编写登录模块了，这里我们可以直接去掉，只留下主页面：


这样，我们的网站就成功用上了更加安全的SpringSecurity框架了。细心的小伙伴可能发现了，我们在配置用户信息的时候，报了黄标，实际上这种方式存储密码并不安全：

![image-20230702151123338](https://s2.loli.net/2023/07/02/yuYe5pODwqBTQh7.png)

这是因为SpringSecurity的密码校验不推荐直接使用原文进行比较，而是使用加密算法将密码进行加密（更准确地说应该进行Hash处理，此过程是不可逆的，无法解密），最后将用户提供的密码以同样的方式加密后与密文进行比较。对于我们来说，用户提供的密码属于隐私信息，直接明文存储并不好，而且如果数据库内容被窃取，那么所有用户的密码将全部泄露，这是我们不希望看到的结果，我们需要一种既能隐藏用户密码也能完成认证的机制，而Hash处理就是一种很好的解决方案，通过将用户的密码进行Hash值计算，计算出来的结果一般是单向的，无法还原为原文，如果需要验证是否与此密码一致，那么需要以同样的方式加密再比较两个Hash值是否一致，这样就很好的保证了用户密码的安全性。

因此，我们在配置用户信息的时候，可以使用官方提供的BCrypt加密工具：

```java
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
  	//这里将BCryptPasswordEncoder直接注册为Bean，Security会自动进行选择
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User
                .withUsername("user")
                .password(encoder.encode("password"))   //这里将密码进行加密后存储
                .roles("USER")
                .build();
      	System.out.println(encoder.encode("password"));  //一会观察一下加密出来之后的密码长啥样
        UserDetails admin = User
                .withUsername("admin")
                .password(encoder.encode("password"))   //这里将密码进行加密后存储
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
```

这样，我们存储的密码就是更加安全的密码了：

![image-20230702152216162](https://s2.loli.net/2023/07/02/tk5pGDrNHWfaJXU.png)

这样，一个简单的权限校验就配置完成了，是不是感觉用起来还是挺简单的？

不过，可能会有小伙伴发现，所有的POST请求都被403了：

![image-20230702183040922](https://s2.loli.net/2023/07/02/F94URzLh6oIBrCJ.png)

这是因为SpringSecurity自带了csrf防护，需求我们在POST请求中携带页面中的csrfToken才可以，否则一律进行拦截操作，这里我们可以将其嵌入到页面中，随便找一个地方添加以下内容：

实际上现在的浏览器已经很安全了，完全不需要使用自带的csrf防护，后面我们会讲解如何通过配置关闭csrf防护。这里温馨提醒一下，在后续各位小伙伴跟我们的实战项目时，如果遇到诸如401、403这种错误时，优先查看你的SpringSecurity配置是否错误。

> 从Spring Security 4.0开始，默认情况下会启用CSRF保护，以防止CSRF攻击应用程序，Spring Security CSRF会针对PATCH，POST，PUT和DELETE方法的请求（不仅仅只是登陆请求，这里指的是任何请求路径）进行防护，而这里的登陆表单正好是一个POST类型的请求。在默认配置下，无论是否登陆，页面中只要发起了PATCH，POST，PUT和DELETE请求一定会被拒绝，并返回**403**错误**（注意，这里是个究极大坑，这个没有任何提示，直接403，因此如果你不知道的话根本不清楚是什么问题，就一直卡这里了）**，需要在请求的时候加入csrfToken才行，也就是"83421936-b84b-44e3-be47-58bb2c14571a"，正是csrfToken，如果提交的是表单类型的数据，那么表单中必须包含此Token字符串，键名称为"_csrf"；如果是JSON数据格式发送的，那么就需要在请求头中包含此Token字符串。

### 基于数据库验证

前面我们已经实现了直接认证的方式，但是实际项目中往往都是将用户信息存储在数据库中，那么如何将其连接到数据库，通过查询数据库中的用户信息来进行用户登录呢？


启动后，可以看到两张表中已经自动添加好对应的数据了：



这样我们就可以在首页进行修改密码操作了：

![image-20230703001525592](https://s2.loli.net/2023/07/03/akAtDrPeMdc6N3b.png)

当然，这种方式的权限校验虽然能够直接使用数据库，但是存在一定的局限性，只适合快速搭建Demo使用，不适合实际生产环境下编写，下一节我们将介绍如何实现自定义验证，以应对各种情况。

### 自定义验证

有些时候，我们的数据库可能并不会像SpringSecurity默认的那样进行设计，而是采用自定义的表结构，这种情况下，上面两种方式就很难进行验证了，此时我们得编写自定义验证，来应对各种任意变化的情况。

既然需要自定义，那么我们就需要自行实现UserDetailsService或是功能更完善的UserDetailsManager接口，这里为了简单，我们直接选择前者进行实现：

```java
@Service
public class AuthorizeService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
```

现在我们需要去实现这个`loadUserByUsername`方法，表示在验证的时候通过自定义的方式，根据给定的用户名查询用户，并封装为`UserDetails`对象返回，然后由SpringSecurity将我们返回的对象与用户登录的信息进行核验，基本流程实际上跟之前是一样的，只是现在由我们自己来提供用户查询方式。

现在我们在数据库中创建一个自定义的用户表：

![image-20230703181046326](https://s2.loli.net/2023/07/03/ln4uZ1TFIe7qaCK.png)

随便插入一点数据：

![image-20230703152719655](https://s2.loli.net/2023/07/03/tToR2JPykeuCK73.png)

接着我们自行编写对应的查询操作，首先创建一个对应的实体类：

```java
@Data
public class Account {
    int id;
    String username;
    String password;
}
```

然后是根据用户名查询用户的Mapper接口：

```java
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    Account findUserByName(String username);
}
```

最后我们在配置类上添加相应的包扫描：

```java
@EnableWebMvc
@Configuration
@ComponentScans({
        @ComponentScan("com.example.controller"),
        @ComponentScan("com.example.service")
})
@MapperScan("com.example.mapper")
public class WebConfiguration implements WebMvcConfigurer {
  	...
}
```

然后我们来到Service这边进行一下完善，从数据库中进行查询：

```java
@Service
public class AuthorizeService implements UserDetailsService {

    @Resource
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = mapper.findUserByName(username);
        if(account == null)
            throw new UsernameNotFoundException("用户名或密码错误");
        return User
                .withUsername(username)
                .password(account.getPassword())
                .build();
    }
}
```

这样，我们就通过自定义的方式实现了数据库信息查询，并完成用户登录操作。

```java
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

   	...
      
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                ...
                //以下是csrf相关配置
                .csrf(conf -> {
                    conf.disable();   //此方法可以直接关闭全部的csrf校验，一步到位
                    conf.ignoringRequestMatchers("/xxx/**");   //此方法可以根据情况忽略某些地址的csrf校验
                })
                .build();
    }
}
```

这样，我们就不需要再往页面中嵌入CSRF相关的输入框了，发送请求时也不会进行校验，至此，我们就完成了简单的自定义登录界面配置。

### 记住我功能

我们的网站还有一个重要的功能，就是记住我，也就是说我们可以在登陆之后的一段时间内，无需再次输入账号和密码进行登陆，相当于服务端已经记住当前用户，再次访问时就可以免登陆进入，这是一个非常常用的功能。

我们之前在JavaWeb阶段，使用本地Cookie存储的方式实现了记住我功能，但是这种方式并不安全，同时在代码编写上也比较麻烦，那么能否有一种更加高效的记住我功能实现呢？

SpringSecurity为我们提供了一种优秀的实现，它为每个已经登陆的浏览器分配一个携带Token的Cookie，并且此Cookie默认会被保留14天，只要我们不清理浏览器的Cookie，那么下次携带此Cookie访问服务器将无需登陆，直接继续使用之前登陆的身份，这样显然比我们之前的写法更加简便。并且我们需要进行简单配置，即可开启记住我功能：


配置完成后，我们需要修改一下前端页面中的表单，将记住我勾选框也作为表单的一部分进行提交：

接着我们来尝试勾选记住我选项进行登录：


当然，由于记住我信息是存放在内存中的，我们需要保证服务器一直处于运行状态，如果关闭服务器的话，记住我信息会全部丢失，因此，如果我们希望记住我能够一直持久化保存，我们就需要进一步进行配置。我们需要创建一个基于JDBC的TokenRepository实现：

```java
@Bean
public PersistentTokenRepository tokenRepository(DataSource dataSource){
    JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
  	//在启动时自动在数据库中创建存储记住我信息的表，仅第一次需要，后续不需要
    repository.setCreateTableOnStartup(true);
    repository.setDataSource(dataSource);
    return repository;
}
```

然后添加此仓库：

```java
.rememberMe(conf -> {
     conf.rememberMeParameter("remember-me");
     conf.tokenRepository(repository);      //设置刚刚的记住我持久化存储库
     conf.tokenValiditySeconds(3600 * 7);   //设置记住我有效时间为7天
})
```

这样，我们就成功配置了数据库持久化存储记住我信息，即使我们重启服务器也不会导致数据丢失。当我们登录之后，数据库中会自动记录相关的信息：

![image-20230704220701000](https://s2.loli.net/2023/07/04/kIJpuWdiEGqUKBx.png)

***

## 内部机制探究

### 授权校验流程

最后我们再来聊一下SpringSecurity的实现原理，它本质上是依靠N个Filter实现的，也就是一个完整的过滤链（注意这里是过滤器，不是拦截器）

![image-20230705005303736](https://s2.loli.net/2023/07/05/LVxihksHZu2qN6X.png)

```java
private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    FirewalledRequest firewallRequest = this.firewall.getFirewalledRequest((HttpServletRequest)request);
    HttpServletResponse firewallResponse = this.firewall.getFirewalledResponse((HttpServletResponse)response);
  	//这里获取了一个Filter列表，实际上SpringSecurity就是由N个过滤器实现的，这里获取的都是SpringSecurity提供的过滤器
  	//但是请注意，经过我们之前的分析，实际上真正注册的Filter只有DelegatingFilterProxy
  	//而这里的Filter列表中的所有Filter并没有被注册，而是在这里进行内部调用
    List<Filter> filters = this.getFilters((HttpServletRequest)firewallRequest);
  	//只要Filter列表不是空，就依次执行内置的Filter
    if (filters != null && filters.size() != 0) {
        if (logger.isDebugEnabled()) {
            logger.debug(LogMessage.of(() -> {
                return "Securing " + requestLine(firewallRequest);
            }));
        }
				//这里创建一个虚拟的过滤链，过滤流程是由SpringSecurity自己实现的
        FilterChainProxy.VirtualFilterChain virtualFilterChain = new FilterChainProxy.VirtualFilterChain(firewallRequest, chain, filters);
      	//调用虚拟过滤链的doFilter
        virtualFilterChain.doFilter(firewallRequest, firewallResponse);
    } else {
        if (logger.isTraceEnabled()) {
            logger.trace(LogMessage.of(() -> {
                return "No security for " + requestLine(firewallRequest);
            }));
        }

        firewallRequest.reset();
        chain.doFilter(firewallRequest, firewallResponse);
    }
}
```

我们来看一下虚拟过滤链的doFilter是怎么处理的：

```java
//看似没有任何循环，实际上就是一个循环，是一个递归调用
public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
  	//判断是否已经通过全部的内置过滤器，定位是否等于当前大小
    if (this.currentPosition == this.size) {
        if (FilterChainProxy.logger.isDebugEnabled()) {
            FilterChainProxy.logger.debug(LogMessage.of(() -> {
                return "Secured " + FilterChainProxy.requestLine(this.firewalledRequest);
            }));
        }

        this.firewalledRequest.reset();
      	//所有的内置过滤器已经完成，按照正常流程走DelegatingFilterProxy的下一个Filter
      	//也就是说这里之后就与DelegatingFilterProxy没有任何关系了，该走其他过滤器就走其他地方配置的过滤器，SpringSecurity的过滤操作已经结束
        this.originalChain.doFilter(request, response);
    } else {
      	//定位自增
        ++this.currentPosition;
      	//获取当前定位的Filter
        Filter nextFilter = (Filter)this.additionalFilters.get(this.currentPosition - 1);
        if (FilterChainProxy.logger.isTraceEnabled()) {
            FilterChainProxy.logger.trace(LogMessage.format("Invoking %s (%d/%d)", nextFilter.getClass().getSimpleName(), this.currentPosition, this.size));
        }
				//执行内部过滤器的doFilter方法，传入当前对象本身作为Filter，执行如果成功，那么一定会再次调用当前对象的doFilter方法
      	//可能最不理解的就是这里，执行的难道不是内部其他Filter的doFilter方法吗，怎么会让当前对象的doFilter方法递归调用呢？
      	//没关系，下面我们接着了解了其中一个内部过滤器就明白了
        nextFilter.doFilter(request, response, this);
    }
}
```

因此，我们差不多已经了解了整个SpringSecurity的实现机制了，那么我们来随便看一个内部的过滤器在做什么。


了解了整个用户验证实现流程，其实其它的过滤器是如何实现的也就很容易联想到了，SpringSecurity的过滤器从某种意义上来说，更像是一个处理业务的Servlet，它做的事情不像是拦截，更像是完成自己对应的职责，只不过是使用了过滤器机制进行实现罢了，从而将所有的验证提前到进入Controller之前。

最后附上完整的过滤器清单，这里列出14个常见的内部过滤器：

| 过滤器名称                              | 职责                                                         |
| --------------------------------------- | ------------------------------------------------------------ |
| DisableEncodeUrlFilter                  | 禁止 HttpServletResponse 对 URL 进行编码，以防止在 URL 中包含 Session ID，此类 URL 不被视为 URL，因为会话 ID 可能会在 HTTP 访问日志等内容中泄露。 |
| WebAsyncManagerIntegrationFilter        | 实现了对SecurityContext与WebAsyncManager的集成，使 Controller 中能够线程安全地获取到用户上下文认证信息。 |
| SecurityContextHolderFilter             | 通过HttpSessionSecurityContextRepository接口从Session中读取SecurityContext或是直接创建新的，然后存入到SecurityContextHolder中，最后请求结束时会进行清理。 |
| HeaderWriterFilter                      | 给HTTP响应添加一些Header属性，如：X-Frame-Options、X-XSS-Protection、X-Content-Type-Options等。 |
| CsrfFilter                              | 针对Csrf相关校验。                                           |
| LogoutFilter                            | 对退出登录的请求进行处理，执行登出操作。                     |
| UsernamePasswordAuthenticationFilter    | 对登录的请求进行处理，执行登录操作。                         |
| ConcurrentSessionFilter                 | 检查SessionRegistry保存的Session信息是否过期。               |
| RequestCacheAwareFilter                 | 缓存Request请求，可以用于恢复因登录而打断的请求。            |
| SecurityContextHolderAwareRequestFilter | 对ServletRequest进行进一步包装，让Request具有更加丰富的内容。 |
| RememberMeAuthenticationFilter          | 针对于记住我Cookie进行校验。                                 |
| AnonymousAuthenticationFilter           | 未验证成功的情况下进行匿名登录操作。                         |
| SessionManagementFilter                 | Session管理相关。                                            |
| ExceptionTranslationFilter              | 异常转换处理，比如最常见的AccessDenied之类的。               |

各位小伙伴感兴趣的话可以自行了解。
