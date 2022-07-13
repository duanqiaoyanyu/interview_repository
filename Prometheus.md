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



```yml
global:
  # The smarthost and SMTP sender used for mail notifications.
  smtp_smarthost: 'localhost:25'
  smtp_from: 'alertmanager@example.org'

# The root route on which each incoming alert enters.
route:
  # The root route must not have any matchers as it is the entry point for
  # all alerts. It needs to have a receiver configured so alerts that do not
  # match any of the sub-routes are sent to someone.
  receiver: 'team-X-mails'

  # The labels by which incoming alerts are grouped together. For example,
  # multiple alerts coming in for cluster=A and alertname=LatencyHigh would
  # be batched into a single group.
  #
  # To aggregate by all possible labels use '...' as the sole label name.
  # This effectively disables aggregation entirely, passing through all
  # alerts as-is. This is unlikely to be what you want, unless you have
  # a very low alert volume or your upstream notification system performs
  # its own grouping. Example: group_by: [...]
  group_by: ['alertname', 'cluster']

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

  # All the above attributes are inherited by all child routes and can
  # overwritten on each.

  # The child route trees.
  routes:
  # This routes performs a regular expression match on alert labels to
  # catch alerts that are related to a list of services.
  - match_re:
      service: ^(foo1|foo2|baz)$
    receiver: team-X-mails

    # The service has a sub-route for critical alerts, any alerts
    # that do not match, i.e. severity != critical, fall-back to the
    # parent node and are sent to 'team-X-mails'
    routes:
    - match:
        severity: critical
      receiver: team-X-pager

  - match:
      service: files
    receiver: team-Y-mails

    routes:
    - match:
        severity: critical
      receiver: team-Y-pager

  # This route handles all alerts coming from a database service. If there's
  # no team to handle it, it defaults to the DB team.
  - match:
      service: database

    receiver: team-DB-pager
    # Also group alerts by affected database.
    group_by: [alertname, cluster, database]

    routes:
    - match:
        owner: team-X
      receiver: team-X-pager

    - match:
        owner: team-Y
      receiver: team-Y-pager


# Inhibition rules allow to mute a set of alerts given that another alert is
# firing.
# We use this to mute any warning-level notifications if the same alert is
# already critical.
inhibit_rules:
- source_matchers:
    - severity="critical"
  target_matchers:
    - severity="warning"
  # Apply inhibition if the alertname is the same.
  # CAUTION: 
  #   If all label names listed in `equal` are missing 
  #   from both the source and target alerts,
  #   the inhibition rule will apply!
  equal: ['alertname']


receivers:
- name: 'team-X-mails'
  email_configs:
  - to: 'team-X+alerts@example.org, team-Y+alerts@example.org'

- name: 'team-X-pager'
  email_configs:
  - to: 'team-X+alerts-critical@example.org'
  pagerduty_configs:
  - routing_key: <team-X-key>

- name: 'team-Y-mails'
  email_configs:
  - to: 'team-Y+alerts@example.org'

- name: 'team-Y-pager'
  pagerduty_configs:
  - routing_key: <team-Y-key>

- name: 'team-DB-pager'
  pagerduty_configs:
  - routing_key: <team-DB-key>
```

