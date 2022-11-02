### 术语:
1. `Route`(路由):  
网关配置的基本组成模块, 和 `Zuul` 的路由配置模块类似. 一个 `Route` 模块由一个 `ID`, 一个目标 `URI`, 一组断言和一组过滤器定义. 如果断言为真, 则路由匹配,
目标 `URI` 会被访问.
2. `Filter`(过滤器)  
和 `Zuul` 的过滤器在概念上类似, 可以使用它拦截和修改请求, 并且对上游的相应, 进行二次处理. 过滤器为 `org.springframework.cloud.gateway.filter.GatewayFilter` 类的实例.
3. `Predicate`(断言):  
这时一个 `Java8` 的 `Predicate`, 可以使用它来匹配来自 `HTTP` 请求的任何内容, 例如 `headers 或参数`. 断言的输入类型是一个 `org.springframework.web.server.ServerWebExchange`

**服务发现路由 predicate 和 filters 的自定义定义**
可以将网关配置为基于使用 `DiscoveryClient` 注册中心注册的服务发现路由.  
要启用此功能, 请设置 `spring.cloud.gateway.discovery.locator.enabled=true`, 并确保 `DiscoveryClient` 实现位于 `classpath` 上并已启用(如 Netflix eureka、 nacos)

**为注册中心路由配置断言和过滤器**
默认情况下, 网关为通过 `DiscoveryClient` 创建的路由定义单个断言和过滤器
```shell
默认情况下, 网关为通过 `DiscoveryClient` 创建的路由定义单个断言和过滤器.

默认断言是使用 `/serviceId/**` 定义的 `path` 断言, 其中 `serviceId` 是 `DiscoveryClient` 中服务的 `ID`.
默认过滤器是使用正则表达式 `serviceId/(?.*)` 和替换的 `/${remaining}` 进行重写. 这只是在请求被发送到下游之前从路径中截取掉 `service id`.
```
