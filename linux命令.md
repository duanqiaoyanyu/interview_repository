ps -ef
ps -aux
top
df
chown -R user:usergroup dirctory

// 查看指定端口进程
lsof -i:端口号

tar:
    - z 通过 gzip 指令处理备份文件。
    - x(extract) 从压缩文件中还原文件。
    - c(create) 建立新的压缩文件
    - v(verbose) 显示指令执行过程
    - f(file) 压缩文件
    - C 目的目录



vim 取消高亮
命令行模式下
```shell
:noh
```

// 根据关键字查找文件
find <directory> -name "keyword"

// 从本地服务器 复制到 远程服务器
scp local_file remote_username@remote_ip:remote_directory 只是指定了文件目录, 名字不变
scp local_file remote_username@remote_ip:remote_directory/remote_file 指定了文件目录和文件名

// 从远程服务器 复制到 本地服务器
scp remote_username@remote_ip:remote_directory/remote_file local_directory
scp remote_username@remote_ip:remote_directory/remote_file local_directory/local_file

#### tail
```shell
# 显示文件末尾内容
tail -f n 5 xxx.log

# 循环查看文件内容
tail -f xxx.log

# 从第五行开始显示文件
tail -n +5 xxx.log
```
