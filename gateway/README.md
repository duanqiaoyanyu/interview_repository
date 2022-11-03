### 术语:
1. `Route`(路由):  
网关配置的基本组成模块, 和 `Zuul` 的路由配置模块类似. 一个 `Route` 模块由一个 `ID`, 一个目标 `URI`, 一组断言和一组过滤器定义. 如果断言为真, 则路由匹配,
目标 `URI` 会被访问.
2. `Filter`(过滤器)  
和 `Zuul` 的过滤器在概念上类似, 可以使用它拦截和修改请求, 并且对上游的相应, 进行二次处理. 过滤器为 `org.springframework.cloud.gateway.filter.GatewayFilter` 类的实例.
3. `Predicate`(断言):  
这时一个 `Java8` 的 `Predicate`, 可以使用它来匹配来自 `HTTP` 请求的任何内容, 例如 `headers 或参数`. 断言的输入类型是一个 `org.springframework.web.server.ServerWebExchange`
> 其中 `Route` 和 `Predicate` 必须同时申明

### 路由 匹配规则
`Spring Cloud Gateway` 的功能很强大, 我们仅仅通过 `Predicates` 的涉及就可以看出来, 前面我们只是使用了 `Predicates`进行了简单的条件匹配, 其实
`Spring Cloud Gateway` 帮我们内置了很多 `Predicates` 功能. `Spring Cloud Gateway` 是通过 `Spring WebFlux` 的 `HandlerMapping` 作为
底层支持来匹配到转发路由, `Spring Cloud Gateway` 内置了很多 `Predicates`工厂, 这些 `Predicates` 工厂通过不同的 `HTTP` 请求参数来匹配, 多个
`Predicates` 工厂可以组合使用.  
说白了 `Predicate` 就是为了实现一组匹配规则, 方便让请求过来找到对应的 `Route` 进行处理, 接下来我们介绍下 `Spring Cloud Gateway` 内置几种 `Predicate` 的使用  

> 转发规则 (predicates), 假设 转发 uri 都设定为 http://localhost:9023

|   规则   | 实例 | 说明|
|:------:| :---: | :---: |
|  Path  | - Path=/gate/,/rule/ | 当请求的路径为 gate、rule 开头的时, 转发到 http://localhost:9023 服务器上 |
| Before | - Before=2017-01-20T17:42:47.789-07:00[America/Denver] | 在某个时间之前的请求才会被转发到 http://localhost:9023 服务器上 |
|  After | - After=2017-01-20T17:42:47.789-07:00[America/Denver] | 在某个时间之后的请求才会被转发 |
| Between | - Between=2017-01-20T17:42:47.789-07:00[America/Denver],2017-01-21T17:42:47.789-07:00[America/Denver] | 在某个时间段之间的才会被转发 |
| Cookie | - Cookie=chocolate, ch.p | 名为 chocolate 的表单或者满足正则 ch.p 的表单才会被匹配到进行请求转发 |
| Header | - Header=X-Request-Id, \d+ | 携带参数 X-Request-Id 或者满足 \d 的请求头才会匹配 |
| Host | - Host=www.google.com | 当主机名为 www.google.com 的时候直接转发到 http://localhost:9023 服务器上 |
| Method | - Method=GET | 只有 GET 方法才会匹配转发请求, 还可以限定 POST、PUT 等请求方式 |

**各种 Predicates 同时存在于同一个路由时, 请求必须同时满足所有的条件才被这个路由匹配.**
**一个请求满足多个路由的断言条件时, 请求只会被首个成功匹配的路由转发**

### 过滤器规则(Filter)
| 过滤规则 | 实例 | 说明 |
| :---: | :---: | :---: |
| PrefixPath | -PrefixPath=/app | 在请求路径前加上 app |
| RewritePath | -RewritePath=/test,/app/test | 访问 localhost:9022/test, 请求会转发到 localhost:8001/app/test |
| SetPath | SetPath=/app/{path} | 通过模板设置路径, 转发的规则时会在路径前增加 app, {path} 表示原请求路径 |
| RedirectTo |  | 重定向 |
| RemoveRequestHeader |  | 去掉某个请求头信息 |

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

#### 过滤器的执行次序
