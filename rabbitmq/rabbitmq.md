

交换机 exchanger
队列 queue
绑定 binding
消息 message
路由键 routingKey

绑定隶属于交换机:
    绑定是什么, 绑定是一种关系.
    绑定是谁和谁绑, 是 routingKey 和 queue 绑

重点讲解 topic 交换机

```yaml
A_TOPIC_EXCHANGER
    binding1: a.* QUEUE1
    binding2: a.* QUEUE2

topic 根据通配符规则进行 消息的路由
理论上 一个消息过来比如是 a.b 然后经过交换机由于交换机有两个绑定 并且都匹配所以说这种情况下其实 交换机就像是扇出交换机了
```

```yaml
A_TOPIC_EXCHANGER
binding1: a.* QUEUE1
binding2: b.* QUEUE1

这种情况下 分别两个消息 一个 a.b 一个 b.a都路由到了 QUEUE1 交换机了. 因为一个队列可以与多个队列绑定
```

```yaml
A_TOPIC_EXCHANGER
binding1: a.* QUEUE1
binding2: b.* QUEUE2
这种应该是最常见的 两个队列有各自的路由规则, 所以来两条消息 a.b b.a 会分别路由到 QUEUE1, QUEUE2
```
