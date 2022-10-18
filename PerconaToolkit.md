### pt-online-schema-change - 修改表格而不会锁住它
**使用**
```shell
pt-online-schema-change [OPTIONS] DSN
```


```shell
            pt-online-schema-change \
                -h"$master_host" --user="$ddl_user" --password="$ddl_pass" --port="$master_port" \
                --charset="$charset" --chunk-size="${chunk_size}" --chunk-time=0.03 \
                --max-load="Threads_running:${max_threads}" --critical-load="Threads_running:${max_threads}" --progress="time,1" \
                --nocheck-replication-filters --max-lag=1 --recurse=0 \
                --check-slave-lag h="$slave_host",u="$read_user",p="$read_pass",P="$slave_port" \
                --alter="$ddl_sql" \
                D=$table_schema,t=$table_name --exec --print
```

脚本解释:
-h="$master_host" 主库地址
--user="$ddl_user" 登录用户
--password="$ddl_pass" 连接用的密码
--port="$master_port" 连接用的端口号
--charset="$charset" 字符集
--chunk-size="${chunk_size}" 块大小
--chunk-time=0.03 每次数据拷贝查询的时间
--max-load="Threads_running:${max_threads}" 最大负载
--critical-load="Threads_running:${max_threads}"
--progress="time,1" 1s打印一次过程
--max-lag=1 主从延迟1s
h="$slave_host",u="$read_user",p="$read_pass",P="$slave_port" 参考主库, 从库的相应配置
--nocheck-replication-filters 不检查复制过滤器
--check-slave-lag 检查从库落后
