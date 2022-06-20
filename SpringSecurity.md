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
- AnonymousAuthenticationFilter 匿名身份过滤器，这个过滤器个人认为很重要，需要将它与 UsernamePasswordAuthenticationFilter 放在一起比较理解，spring security 为了兼容未登录的访问，也走了一套认证流程，只不过是一个匿名的身份。
- SessionManagementFilter 和 session 相关的过滤器，内部维护了一个 SessionAuthenticationStrategy，两者组合使用，常用来防止 session-fixation protection attack，以及限制同一用户开启多个会话的数量
- ExceptionTranslationFilter 直译成异常翻译过滤器，还是比较形象的，这个过滤器本身不处理异常，而是将认证过程中出现的异常交给内部维护的一些类去处理，具体是那些类下面详细介绍
- FilterSecurityInterceptor 这个过滤器决定了访问特定路径应该具备的权限，访问的用户的角色，权限是什么？访问的路径需要什么样的角色和权限？这些判断和处理都是由该类进行的。
