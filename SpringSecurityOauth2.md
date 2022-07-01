AuthorizationServerConfigurerAdapter

```java
public class AuthorizationServerConfigurerAdapter implements AuthorizationServerConfigurer {
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 配置 AuthorizationServer 安全认证的相关信息 创建 ClientCredentialsTokenEndpointFilter 核心过滤器
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置 OAuth2 的客户端相关信息
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置 AuthorizationServerEndpointsConfigurer 众多相关类, 包括配置身份认证器, 配置认证方式, TokenStore, TokenGranter, OAuth2RequestFactory
    }
    
}
```

客户端身份认证核心过滤器 ClientCredentialsTokenEndpointFilter
顶级身份管理者 AuthenticationManager
Token 处理端点 TokenEndpoint
TokenGranter -> AbstractTokenGranter
AuthorizationServerTokenServices -> DefaultTokenServices
TokenService 就是用来对 token 进行管理的, token 分为 accessToken 和 refreshToken

浅析 创建 accessToken 的过程
1. 从 tokenStore 中根据认证信息获取已经存在的 accessToken, 初始化 refreshToken 为 null
2. 如果已经存在的 accessToken 不为空, 也就是真的存在 就去判断 accessToken 是否过期
3. 如果 accessToken 过期并且 accessToken 想关联的 refreshToken 不为空, 那就把 refreshToken 赋值为 access 关联的 refreshToken
4. 然后因为 accessToken 过期, 需要移除 accessToken, 本来同时也会移除 refreshToken 的, 但是这里想要更加肯定一点,
5. 所以提前清除了 tokenStore 里的 refreshToken, 然后再移除 tokenStore 里的 accessToken, 至于为什么要在创建 token 的时候
去判断过期了才去清除 tokenStore 里的 accessToken 和 refreshToken, 而不是有一种机制保证 token 过期了, 就一定会把 tokenStore 里的
token 给清除了, 而不是等到创建再清除呢? 如果要做到我说的那种过期了就清除, 就肯定是有一种定时清理的机制, 比如定时任务之类的找出一批
过期的 token 然后把对应在 tokenStore 里的给清除了, 要么就是像延迟队列那样异步的到那个时间点了去消费掉那个过期的 token, 将 tokenStore
里的 token 给清除了。那么为什么作者没有像我那样做呢？ 我猜想一是我的那些方案并不能够说 100% 保证 tokenStore 一定被清理干净,
那么最后还是要一个兜底的方案来确保清理干净了, 然后其实把那个兜底的逻辑放在创建 token 这里是再合适不过了, 创建 token 的过程中
一旦发现 token 过期就去清理掉它, 清理完才生成 token. 中途有报错, 那么 token 生成失败, 那么下次来可能 过期 token 已经被清那就直接
走生成 token 的逻辑, 如果过期 token 上次没清掉, 那就这次清掉它, 清掉过期 token 才继续 token 的生成。另一个方面我想的是我之前的那些
方案都是比较复杂的, 引入那些东西来解决这个清掉 token 好像也不太合适. 综上就是我对为什么要在创建 token 期间对 token 的过期判断并且清理的
理解, 当然也可能其他地方也有对 tokenStore 里过期 token 清理的逻辑, 我只是阐述为什么在这个创建 token 的过程中为什么会有这么一段逻辑.
6. 继续分析... 如果 已经存在的 token 没过期, 那么就把 token 重新放入 tokenStore 里面. 然后直接就返回 token 了
7. 如果已经存在的 token 为 空, 那就需要生成新的 accessToken, 而 accessToken 的生成依赖于 refreshToken, 那就先看看 refreshToken
的生成过程吧. 如果 refreshToken 为空, 那就创建 refreshToken. 如果 refreshToken 是可过期类型的, 判断 refreshToken 是否过期,
如果 refreshToken 已经过期, 那也是需要创新新的 refreshToken 的, 所以到这里 refreshToken 要么是新生成的, 要么就是之前的 accessToekn
相关联的 refreshToken 还没过期, 然后用认证信息和 refreshToken 去生成 accessToken, 生成后的 accessToken 存在 tokenStore,
到这里有一步我比较困惑就是此时也需要存入 refreshToken 到 tokenStore里去, 按道理直接拿生成 accessToken 的 refreshToken 存入
tokenStore 不就好了吗？ 但是源码这里说以防 refreshToken 被修改, 所以需要从 accessToken 中拿到 refreshToken 再存进去,
我有点不太理解, 然后是说这么短暂的时间内可能 refreshToken 就突然发生了某种变化然后就需要重新拿, 那你这拿了之后也可能会变啊. 怎么保证
最终的数据是对的呢？ 这里我还不是特别明白， 姑且做个猜想, 他这个用 accessToken 去生成 refreshToken 中可能存在是之前就有 accessToken
accessToken 然后只是拿 refreshToken 去换 accessToken 然后也是走的这套逻辑, 然后出于安全的考虑 refreshToken 不能重复使用, 用过一次就
会作废然后用新的 refreshToken 跟 accessToken 绑定起来了, 所以说采用重新拿, 只有从 accessToken 里新拿出来的 refreshToken 才是
和 accessToken 是一套的, 之前的用于生成新 accessToken 的老 refreshToken 已经作废掉了. 这是我猜的, 后续作验证. 在继续研究研究
8. 与 accessToken 绑定的 refreshToken 是不是空. 不是空的话, 那就把它存入 tokenStore

资源服务器 
ResourceServerSecurityConfigurer
    为 OAuth2AuthenticationProcessingFilter 提供固定的 AuthenticationManager 即 OAuth2AuthenticationManager，
    它并没有将 OAuth2AuthenticationManager 添加到 spring 的容器中，不然可能会影响 spring security 的普通认证流程（非 oauth2 请求），
    只有被 OAuth2AuthenticationProcessingFilter 拦截到的 oauth2 相关请求才被特殊的身份认证器处理。
OAuth2AuthenticationProcessingFilter 核心过滤器
OAuth2AuthenticationManager OAuth2 的身份管理器
在之前的 OAuth2 核心过滤器中出现的 AuthenticationManager 其实在我们意料之中，携带 access_token 必定得经过身份认证，但是在我们 debug 进入其中后，
发现了一个出乎意料的事，AuthenticationManager 的实现类并不是我们在前面文章中聊到的常用实现类 ProviderManager，而是 OAuth2AuthenticationManager。
新的 AuthenticationManager 实现类 OAuth2AuthenticationManager,这里要强调的是 OAuth2AuthenticationManager 是密切与 token 认证相关的，而不是与获取 token 密切相关的。

...continue
