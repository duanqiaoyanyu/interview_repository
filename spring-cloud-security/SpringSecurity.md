Authentication 的 getCredentials 和 userDetails 中的 getPassword 需要被区分对待,

Authentication 的 getCredentials 是用户提交的密码凭证  表单提交密码

userDetails 中的 getPassword 是用户正确的密码  正确密码



Authentication 的 getAuthorities 由 userDetails 的 getAuthorities 传递而来 (待确定，不是很完全相信)



http 作为根开始配置, 每一个and()对应了一个模块的配置(等同于 xml 配置中的结束标签)，

并且 and() 返回了 HttpSecurity 本身, 于是可以连续进行配置

过滤器链:
- SecurityContextPersistenceFilter 两个主要职责：请求来临时，创建 SecurityContext 安全上下文信息，请求结束时清空 SecurityContextHolder。
- SecurityContextPersistenceFilter 两个主要职责：请求来临时，创建 SecurityContext 安全上下文信息，请求结束时清空 SecurityContextHolder。
- HeaderWriterFilter (文档中并未介绍，非核心过滤器) 用来给 http 响应添加一些 Header, 比如 X-Frame-Options, X-XSS-Protection*，X-Content-Type-Options.
- CsrfFilter 在 spring4 这个版本中被默认开启的一个过滤器，用于防止 csrf 攻击，了解前后端分离的人一定不会对这个攻击方式感到陌生，前后端使用 json 交互需要注意的一个问题。
- LogoutFilter 顾名思义，处理注销的过滤器
- UsernamePasswordAuthenticationFilter 这个会重点分析，表单提交了 username 和 password，被封装成 token 进行一系列的认证，便是主要通过这个过滤器完成的，在表单认证的方法中，这是最最关键的过滤器。
- RequestCacheAwareFilter (文档中并未介绍，非核心过滤器) 内部维护了一个 RequestCache，用于缓存 request 请求
- SecurityContextHolderAwareRequestFilter 此过滤器对 ServletRequest 进行了一次包装，使得 request 具有更加丰富的 API
- AnonymousAuthenticationFilter 匿名身份过滤器，这个过滤器个人认为很重要，需要将它与 UsernamePasswordAuthenticationFilter 放在一起比较理解，spring security 为了兼容未登录的访问，也走了一套认证流程，只不过是一个匿名的身份, 顺序很有讲究, 在 UsernamePasswordAuthenticationFilter 之后, 相当于
前面的身份认证都没有通过, 那么就会给他分配一个匿名用户, 生成一个匿名 AnonymousAuthenticationToken, 用户名为 "anonymousUser" 权限为
  "ROLE_ANONYMOUS".
- SessionManagementFilter 和 session 相关的过滤器，内部维护了一个 SessionAuthenticationStrategy，两者组合使用，常用来防止 session-fixation protection attack，以及限制同一用户开启多个会话的数量
- ExceptionTranslationFilter 直译成异常翻译过滤器，还是比较形象的，这个过滤器本身不处理异常，而是将认证过程中出现的异常交给内部维护的一些类去处理，具体是那些类下面详细介绍
- FilterSecurityInterceptor 这个过滤器决定了访问特定路径应该具备的权限，访问的用户的角色，权限是什么？访问的路径需要什么样的角色和权限？这些判断和处理都是由该类进行的。
  AccessDecisionManager 决定用户是否具备访问某个资源的权限，具体的实现类是 AffirmativeBased。投票方法, 如果有一个授权通过了那就直接放行，
  最后判断 deny 如果 > 0, 就抛出 AccessDeniedException 异常。

ExceptionTranslationFilter 本身并不会对异常进行处理, 而是将异常进行分发交给别的进行处理。
一般只处理两大异常 AccessDeniedException 访问异常 和 AuthenticationException 认证异常
AuthenticationException -> AuthenticationEntryPoint 去处理
AccessDeniedException ->
    1. 如果当前用户是匿名用户 -> AuthenticationEntryPoint
    2. 不是匿名用户 -> AccessDeniedHandler

FilterSecurityInterceptor 作用是访问控制, 会从安全上下文中拿到用户拥有的角色权限 和 目标资源需要的角色权限进行对比。是否满足访问条件


Authentication -> token
一般有两个构造方法, 一个是认证前用的构造后给 authenticationManager 使用 认证
                 一个是认证后使用的. 填充权限信息


DelegatingFilterProxy Spring Web 中的类 而并不是 spring security 中的类 实现了 Filter 接口 Web 容器中名字叫 springSecurityFilterChain
    private volatile Filter delegate -> FilterChainProxy 是spring security 的类

FilterChainProxy 实现了 Filter接口 Web 容器中 名字叫 springSecurityFilterChain
    private List<SecurityFilterChain> filterChains;
    内部维护了一个 SecurityFilterChain的列表. 同一个 spring 环境下可能存在多个安全过滤器, 需要经过 chain.mateches(request) 判断
列表中的哪一条过滤器链会匹配成功, 每个 request 最多指挥经过一个 SecurityFilterChain. 因为 Web 环境下可能有多种安全保护策略, 每一种策略
都需要有自己的一条链路, 比如 Oauth2 服务, 极端条件下, 可能同一个服务本身即使资源服务器, 又是认证服务器, 还需要做 Web 安全!

SecurityFilterChain -> DefaultSecurityFilterChain
    private final List<Filter> filters; 就是之前分析过的一系列过滤器 
        - UsernamePasswordAuthenticationFilter，
        - SecurityContextPersistenceFilter，
        - FilterSecurityInterceptor
        ...



集群环境 session 处理
1. 添加spring-session-data-redis依赖
```xml
      <!-- https://mvnrepository.com/artifact/org.springframework.session/spring-session-data-redis -->
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
    <version>2.7.0</version>
</dependency>
```

配置Spring-session存储策略
```yaml
spring:
  redis:
    host: localhost
    port: 6379
  session:
    store-type: redis
```
..
