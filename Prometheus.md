## 普罗米修斯 学习经历



1. 通过阅读相关博客了解大概用法以及相关基本概念

2. 有了基本的了解后, 因为我们使用东西一般都是使用的版本比较博客的肯定是更新一些，所以很多东西有可能在新的版本就变了。所以说如果我们

   要实际去使用的话，用的版本大概率是和博客版本不太一致，所以我们就需要去阅读官方文档。这个时候就凸显了英语阅读能力的重要性了，平常

   得锻炼自己多看英文文档。

3.  如果在看文档的过程中遇到了不理解的，或者搞不懂的。这个时候可以借助别人的博客对这一知识点的用法，来加深或者说帮助自己理解这个知识点

4. 这样的话就可以继续进行阅读文档了，然后就是在阅读文档的过程中要区分什么是重点，什么是细节。一般我们学习一门新技术，我们在最初的步骤

   1的过程中其实还可以获取到一部分信息那就是什么才是重要的，因为博客一般篇幅比较小都是挑比较重点的部分来进行讲解的，所以也给了我们参考

   我们在阅读文档的过程中就会知道什么地方是重要的，什么地方我们得仔细读。这就是呆着问题去学习，而不是漫无目的，不分重点和细节。从头到尾，

   从外到里一顿把文档全部读完，那估计你学完一个新技术 鸡吃完米了 狗吃完面 火烧断锁了，哈哈哈。开个玩笑，其实最主要的就是说我们把我一个大致的

   主流程一直读下去，然后什么时候才要精读呢？就是当你对整个文档都有了解了，然后前面肯定有一部分你没理解到位的，这个时候可以把之前记录的

   一些疑问点，来进行逐一攻克。或者是说如果你有一个特殊的需求，然后这个需求就恰好稍微有点 "偏门", 然后就需要你对当中某一个或者某几个 "偏门的" 知识点来进行了解，你才会发现哦，原来它还可以这样用？ 还有这样用法，这样才能够解决你的需求。



#### 普罗米修斯 安装和启动

```sh
# 下载普罗米修斯
https://github.com/prometheus/prometheus/releases

# 解压
tar -zxvf -C xxxx.tar.gz  /....../prometheus

# 创建目录
mkdir prometheus

# 移动
mv prometheus_1.23_release/* prometheus

# 修改权限
chown -R root:root prometheus

# 配置文件



```



#### Prometheus 配置

```yml
# my global config
global:
  scrape_interval: 15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
    - static_configs:
        - targets:
          - localhost:9093
          # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  - "rules/first_rules.yml"
  # - "first_rules.yml"
  # - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: "prometheus"

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.

    static_configs:
      - targets: ["localhost:9090"]

  # rabbitmq
  - job_name: "rabbitmq"
    static_configs:
      - targets: ["localhost:15692"]

    # relabel 配置    
    relabel_configs:
      - action: labelmap
        replacement: "cskaoyan$1"

    # metric relabel 配置
    metric_relabel_configs:
      - action: labelmap
        replacement: "cskaoyan$1"

  # rabbitmq_exporter
  - job_name: "rabbitmq_exporter"
    static_configs:
      - targets: ["localhost:9419"]
      
```



#### Prometheus Rule

```yml
groups:
  - name: example
    rules:

     #     RabbitmqTooManyUnackMessages
     # - alert: RabbitmqTooManyUnackMessages
     #   expr: sum(rabbitmq_queue_messages_unacked) BY (queue) > 0
     #   for: 1m
     #   labels:
     #     severity: warning
     #   annotations:
     #     summary: Rabbitmq too many unack messages (instance {{ $labels.instance }})
     #     description: "Too many unacknowledged messages\n  VALUE = {{ $value }}\n  LABELS = {{ $labels }}"

      - alert: RabbitmqNoQueueConsumer
        expr: rabbitmq_queue_consumers < 1
        for: 1m
        labels:
          severity: warning
        annotations:
          summary: Rabbitmq no queue consumer (instance {{ $labels.instance }})
          description: "A queue has less than 1 consumer\n  VALUE = {{ $value }}\n  LABELS = {{ $labels }}"
      - alert: RabbitmqTooManyUnackMessages
        expr: rabbitmq_queue_messages{queue=~".*.dlq"} > 0
        for: 1m
        labels:
          severity: warning
        annotations:
          summary: Rabbitmq queue exist unack messages (instance {{ $labels.instance }})
          description: "exist unacknowledged messages\n  VALUE = {{ $value }}\n  LABELS = {{ $labels }}\n QUEUE = {{ $labels.queue }}"
```



#### Alert Manager 启动和安装
```sh
# 下载 alertmanager
https://github.com/prometheus/alertmanager

# 解压
tar -zxvf -C xxx.tar.gz /.../prometheus/alertmanager

# 创建目录
mkdir alertmanager

# 移动
mv alertmanager_1.23_release/* alertmanager

# 修改权限
chown -R root:root alertmanager

# 配置文件
```



#### Alert Manager 配置文件

```yml
global:
  # The smarthost and SMTP sender used for mail notifications.
  smtp_smarthost: 'smtp.exmail.qq.com:465'
  smtp_from: 'username@domain.com'
  smtp_auth_username: 'username@domain.com'
  smtp_auth_password: 'password'
  # 和安全登录相关的, 如果邮件发送不成功。则可能需要将 “smtp_require_tls” 置为 false。默认值是 true
  smtp_require_tls: false

# The directory from which notification templates are read.
templates:
  - 'template/*.tmpl'

# The root route on which each incoming alert enters.
route:
  # The labels by which incoming alerts are grouped together. For example,
  # multiple alerts coming in for cluster=A and alertname=LatencyHigh would
  # be batched into a single group.
  #
  # To aggregate by all possible labels use '...' as the sole label name.
  # This effectively disables aggregation entirely, passing through all
  # alerts as-is. This is unlikely to be what you want, unless you have
  # a very low alert volume or your upstream notification system performs
  # its own grouping. Example: group_by: [...]
  group_by: ['alertname']

  # When a new group of alerts is created by an incoming alert, wait at
  # least 'group_wait' to send the initial notification.
  # This way ensures that you get multiple alerts for the same group that start
  # firing shortly after another are batched together on the first
  # notification.
  group_wait: 30s

  # When the first notification was sent, wait 'group_interval' to send a batch
  # of new alerts that started firing for that group.
  group_interval: 5m

  # If an alert has successfully been sent, wait 'repeat_interval' to
  # resend them.
  repeat_interval: 3h

  # A default receiver
  receiver: team-X-mails

  # All the above attributes are inherited by all child routes and can
  # overwritten on each.

# Inhibition rules allow to mute a set of alerts given that another alert is
# firing.
# We use this to mute any warning-level notifications if the same alert is
# already critical.
inhibit_rules:
  - source_matchers: [severity="critical"]
    target_matchers: [severity="warning"]
    # Apply inhibition if the alertname is the same.
    # CAUTION:
    #   If all label names listed in `equal` are missing
    #   from both the source and target alerts,
    #   the inhibition rule will apply!
    equal: ['alertname', 'dev', 'instance']


receivers:
  - name: 'team-X-mails'
    email_configs:
      - to: 'receiver@domain.com'
      
```



一些用法:

告警规则中 AlertRule中涉及到 expr 表达式。然后需要学习相关的自定义的写法就是需要 PromeQL 

PromeQL 的写法以及语法

https://prometheus.io/docs/prometheus/latest/querying/basics/



Prometheus 配置 configuration

```sh
<relabel_config>
Relabeling is a powerful tool to dynamically rewrite the label set of a target before it gets scraped. Multiple relabeling steps can be configured per scrape configuration. They are applied to the label set of each target in order of their appearance in the configuration file.

Initially, aside from the configured per-target labels, a target's job label is set to the job_name value of the respective scrape configuration. The __address__ label is set to the <host>:<port> address of the target. After relabeling, the instance label is set to the value of __address__ by default if it was not set during relabeling. The __scheme__ and __metrics_path__ labels are set to the scheme and metrics path of the target respectively. The __param_<name> label is set to the value of the first passed URL parameter called <name>.

The __scrape_interval__ and __scrape_timeout__ labels are set to the target's interval and timeout. This is experimental and could change in the future.

Additional labels prefixed with __meta_ may be available during the relabeling phase. They are set by the service discovery mechanism that provided the target and vary between mechanisms.

Labels starting with __ will be removed from the label set after target relabeling is completed.

If a relabeling step needs to store a label value only temporarily (as the input to a subsequent relabeling step), use the __tmp label name prefix. This prefix is guaranteed to never be used by Prometheus itself.

relabel 是一个强有力的 可以使得 被抓取 target 的 label 集合进行重写， 每一个抓取配置可以有多个 relabel 步骤. 他们被应用到出现在配置文件中的每一个 target 的标签集中. 最初的，除了每一个配置的标签，一个target的job标签被设置为 scrape配置相应的 job_name的值。 __address__ 标签被设置为 targe的地址。在 relabeling 之后，如果 instance 标签 在relabelling这个过程中没有被设置值, instance 标签默认被设置为 __address__ 的值。__scheme__ 和 __metrics_path__ 标签分别被设置为 target的 协议和指标的值. __param_<name> 标签被设置为第一个 传过来叫 <name>的 url 参数的值。

在 relabelling 结束后，以 __开头的标签都会被移除标签集合
如果只是暂时的存储（作为随后的 relabelling 步骤的输入参数），使用 __tmp 标签开头。这个前缀可以保证永远不会被 Prometheus 自己使用

配置文件中有多个地方可以配置 relabel


# List of target relabel configurations.
# 配置的是要对 一些元信息的重写过程。过滤 保留等等。。
relabel_configs:
  [ - <relabel_config> ... ]

# List of metric relabel configurations.
# 对指标进行 relabel 过程。经常是用来通过标签来过滤具有指定标签的指标，一般不处理时间序列。因为开销很大
metric_relabel_configs:
  [ - <relabel_config> ... ]


# Alerting specifies settings related to the Alertmanager.
# 配置到 alertmanager 之前需要经过 relabel过程。相当于 alert manager 拿到的最后是经过这一层之后的 标签集
alerting:
  alert_relabel_configs:
    [ - <relabel_config> ... ]
  alertmanagers:
    [ - <alertmanager_config> ... ]

```





```sh
# 参考资料
# Prometheus 官方文档
1. https://prometheus.io/docs/introduction/first_steps/

# rabbitmq-Prometheus 官方文档
2. https://www.rabbitmq.com/prometheus.html

# 常见的告警规则写法
3. https://awesome-prometheus-alerts.grep.to/rules#rabbitmq
4. https://chenzhonzhou.github.io/categories/%E7%9B%91%E6%8E%A7%E7%B3%BB%E7%BB%9F/prometheus/
5. https://blog.csdn.net/li4528503/article/details/106709682
6. https://www.51cto.com/article/683864.html
7. https://www.jianshu.com/p/b47e8a7d7b25
8. https://juejin.cn/post/7103152042658496525
9. https://aeric.io/post/rabbitmq-prometheus-monitoring/
10. https://blog.csdn.net/yaomingyang/article/details/104037083
11. https://www.cnblogs.com/hahaha111122222/p/15683696.html
```

