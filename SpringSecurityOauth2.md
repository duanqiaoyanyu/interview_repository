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
