> Spring Cloud Stream is a framework for building highly scalable event-driven microservices connected with shared messaging systems.

> The framework provides a flexible programming model built on already established and familiar Spring idioms and best practices, including support for persistent pub/sub semantics, consumer groups, and stateful partitions.

### The core building blocks of Spring Cloud Stream are:

- Destination Binders: Components responsible to provide integration with the external messaging systems.

- Destination Bindings: Bridge between the external messaging systems and application code (producer/consumer) provided by the end user.

- Message: The canonical data structure used by producers and consumers to communicate with Destination Binders (and thus other applications via external messaging systems).

### Usage
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
</dependency>
```

### 特性 1, RabbitMQ Binder
默认, RabbitMQ Binder 实现了将每一个 `destination` 映射为一个 `TopicExchange`. 对每一个消费组, 一个 `Queue` 被绑定到了该 `TopicExchange` 上. 对消费组的 `Queue` 每一个消费者实例都有一个相应的 RabbitMQ `Consumer`实例. 对于分区的生产者和消费者, 队列会用分区下标作为后缀, 并且使用分区下标作为路由键. 对于匿名消费者(没有 `group` 属性), 使用一个自动删除的队列(随机的唯一名字)

通过使用可选项 `autoBindDlq`, 你可以配置 `binder` 去创建和配置死信队列(还有一个死信交换机 `DLX`, 也是路由架构). 默认的, 死信队列的名字是 `destination` 的名字加上 `.dlq`. 如果启用重试(`maxAttempts > 1`), 在重试次数耗尽之后失败的消息会被投递到 DLQ. 如果禁用重试(`maxAttempts = 1`), 你应该把 `requeueRejected` 设置为 `false`(默认的)以便于失败的消息会被路由到 DLQ, 而不是重新入队. 除此之外, `republishToDlq` 会导致 `binder` 去发布一个失败的消息到 DLQ(而不是拒绝它). 这个特性使得在消息头中添加了额外的信息(比如在 `x-exception-stacktrace` 头中的 stack trace). 有关截断堆栈跟踪的信息, 请参阅 `frameMaxHeadroom` 属性. 这一项不需要启用重试. 你可以在仅一次尝试后重新发布一个失败的消息. 自从 1.2 版本, 你可以配置重新发布的消息的投递模式. 参阅 `republishDeliveryMode` 属性.

如果 steam listener 抛出了一个 `ImmediateAcknowledgeAmqpException` 异常, DLQ被绕过, 消息被简单地丢弃. 自从 2.1 版本, 无论 `republishToDlq` 怎么设置都会像上面一样; 之前是只有当 `republishToDlq` 设置为 `false` 的时候才会.

### 重要
设置 `requeueRejected` 为 `true`(并且 `republishToDlq = false`)会使得消息持续性地重新入队和重新投递, 这可能不是你想要的除非失败的原因是短暂的. 通常, 你应该通过在 binder 中设置 `maxAttempts` 比 1 大来启用重试或者通过设置 `republishToDlq` 为 `true`.

自从 3.1.2 版本, 如果消费者被标记为 `transacted`, 发布到 DLQ 将会参与事务. 如果因为某些原因发布过程中失败可以允许事务回滚(比如, 如果用户没有被授权发布到死信交换机). 除此之外, 如果 `connection factory` 配置了 `publisher confirms` 或 `returns`, 到 DLQ 的发布将会等待确认信息和一个返回消息的检查. 如果收到一个否定确认或返回消息, binder 将会抛出一个 `AmqpRejectAndDontRequeueException` 异常, 允许 broker 负责发布到 DLQ 就好像 `republishToDlq` 属性为 `false`.

框架不提供任何标准机制去消费死信消息(或者去 重新路由他们回到 主队列).

自从 2.0 版本 `RabbitMessageChannelBinder ` 设置 `RabbitTemplate.userPublisherConnection` 属性为 `true` 使得无事务生产者避免消费者上的死锁, 发生在因为 broker 上的内存告警导致的缓存连接被阻塞。
注意: 当前一个 `multiplex` 消费者(单个消费者监听多个队列) 仅仅支持消息驱动的消费者; 轮训消费者仅仅只能从单个队列中取得消息.


```yaml
spring:
  cloud:
    stream:
      binders:
        rabbit:
          type: rabbit
          
      # 下面是对所有队列都生效的配置, 不区分 binder
      default:
        consumer:
          concurrency: 3 # 初始/最少/空闲时 消费者数量。默认1
          
      # 下面是 binder 为 rabbitmq 的所有队列都应用的默认配置, 若某个队列需要覆盖, 直接在对应的配置文件中覆盖对应的配置即可
      rabbit:
        bindings:
          default:
            consumer:
              max-concurrency: 10 # queue的消费者的最大数量。当前消费者数量不足以及时消费消息时, 会动态增加消费者数量, 直到到达最大数量, 即该配置的值.
              autoBindDlq: true # 是否自动声明死信队列（DLQ）并将其绑定到死信交换机（DLX）。默认是false。
              republishToDlq: true # (如果定义了DLQ，当消费失败的消息重试次数耗尽后，会将消息路由到该DLQ。) 当为true时，死信队列接收到的消息的headers会更加丰富，多了异常信息和堆栈跟踪。默认false。
              republishDeliveryMode: DeliveryMode.PERSISTENT # 默认DeliveryMode.PERSISTENT（持久化）。当republishToDlq为true时，转发的消息的delivery mode
```

```yaml
spring:
  cloud:
    stream:
      bindings:
        # 会员充值
        memberRechargeOutput:
          destination: memberRechargeTopic
          content-type: application/json
          binder: rabbit

        # 会员充值
        memberRechargeInput:
          destination: memberRechargeTopic
          content-type: application/json
          group: ${spring.application.name}
          binder: rabbit
          consumer:
            concurrency: 3 # 初始/最少/空闲时 消费者数量。默认1

      rabbit:
        bindings:
          ############# output #############
          # 关闭超时未支付订单
          closePayExpiredOrderOutput:
            producer:
              delayedExchange: true # 是否将目标exchange声明为一个延迟消息交换机，默认false。即消息productor发布消息到延迟exchange后，延迟n长时间后才将消息推送到指定的queue中。 -RabbitMQ需要安装/启用插件: rabbitmq-delayed-message-exchange

          popupUpdateOutput:
            producer:
              # 生产者配置RabbitMq的动态路由键
              routingKeyExpression: headers.source

          ############# input #############
          memberRechargeInput:
            consumer:
              max-concurrency: 10
              autoBindDlq: true
              republishToDlq: true    
          # 监管数据上传
          regulatoryDataPushInput:
            consumer:
              delayedExchange: true
              max-concurrency: 10
              autoBindDlq: true
              republishToDlq: true
```
