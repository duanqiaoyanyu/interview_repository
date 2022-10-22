#### 1. 依赖
```xml
    <dependency>
        <groupId>org.redisson</groupId>
        <artifactId>redisson-spring-boot-starter</artifactId>
        <version>3.17.7</version>
    </dependency>
```


#### 2. 配置
##### 2.1 通过 YAML 格式配置
`Redisson` 的配置文件是 YAML 格式. 可以通过调用 `config.fromYaml` 方法并指定一个 `File` 实例来实现读取 `YAML` 格式的配置:
```java
Config config = Config.fromYAML(new File("config-file.yaml"));
RedissonClient redisson = Redisson.create(config);
```
调用 `config.toYAML` 方法可以将一个 `Config` 配置实例序列化为一个含有 YAML 数据类型的字符串:
```java
Config config = new Config();
// ... 省略许多其他的设置
String jsonFormat = config.toYAML();
```


所以具体配置可如下分配, 将 `Redisson` 配置文件与 `Spring` 配置文件分离, `Redisson` 配置单独一个文件进行配置
**在 `application.yml` 中指定 `redisson`配置文件的位置和名字**
```yaml
spring:
  application:
    name: distribute-lock

  # redis 配置
  redis:
    # 数据库
    database: 0
    # 主机
    host: "127.0.0.1"
    # 端口
    port: 6379
    # 密码
    password:
    # ssl
    ssl:
    # 超时
    timeout:
    # 集群模式
    cluster:
      # 节点
      nodes:
    # 哨兵模式
    sentinel:
      # 主结点
      master:
      # 节点
      nodes:

    # redisson 配置
    redisson:
      file: classpath:redisson.yml

```

```yaml
# 单节点模式
singleServerConfig:
  # 连接空闲超时，单位：毫秒
  idleConnectionTimeout: 10000
  # 连接超时，单位：毫秒
  connectTimeout: 10000
  # 命令等待超时，单位：毫秒
  timeout: 3000
  # 命令失败重试次数
  retryAttempts: 3
  # 命令重试发送时间间隔，单位：毫秒
  retryInterval: 1500
  # 密码
  password: null
  # 单个连接最大订阅数量
  subscriptionsPerConnection: 5
  # 客户端名称 (我这里感觉是可以用 spring.application.name 的值的, 但是无法引用过来, 所以就手动 copy 过来吧)
  clientName: distribute-lock
  # 节点地址(可以通过 host:port 的格式来指定节点地址)
  address: "redis://127.0.0.1:6379"
  # 发布和订阅连接的最小空闲连接数
  subscriptionConnectionMinimumIdleSize: 1
  # 发布和订阅连接池大小
  subscriptionConnectionPoolSize: 50
  # 最小空闲连接数
  connectionMinimumIdleSize: 32
  # 连接池大小
  connectionPoolSize: 64
  # 数据库编号
  database: 0
  # DNS监测时间间隔，单位：毫秒
  dnsMonitoringInterval: 5000
threads: 0
nettyThreads: 0
codec: !<org.redisson.codec.JsonJacksonCodec> {}
transportMode: "NIO"

```


#### 2.2 加载配置并生成关键性对象 RedissonClient
```java
/**
 * @author duanqiaoyanyu
 * @date 2022/10/19 11:04
 */
@Configuration
public class RedissonConfiguration {

    @Value("${spring.redis.redisson.file}")
    private String redissonYamlPath;

    @Bean
    RedissonClient redissonClient() {
        Config config = null;
        try {
            String path = ResourceUtils.getURL(redissonYamlPath).getPath();
            config = Config.fromYAML(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Redisson.create(config);
    }
}

```  
  

#### 3. 使用
使用比较简单, 直接在需要用到的地方自动注入 `RedissonClient` 对象即可
