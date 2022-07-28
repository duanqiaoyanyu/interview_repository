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


#### chmod
```shell
# 增加所有用户及群组 对该文件的可执行权限
  chmod a+x 202207026.log
  
# 同时修改不同用户权限
  chmod ug+w, o-x 202207026.log
  
# 删除文件权限
  # a代表所有用户及群组, 所以这里是删除所有用户以及群组对文件的可执行权限
  chmod a-x 202207026.log
  
# 使用 "=" 设置权限
  # 撤销原来所有的权限, 然后使拥有者具有可执行权限
  chmod u=x 202207026.log
  
# 对一个目录及其子目录所有文件添加权限
  # 递归地给 logs 目录下所有文件和子目录的拥有者增加可执行权限
  chmod -R u+x logs
  
# 给 file 的拥有者分配 读、写、执行 (7) 的权限, 给 file 的所在组分配读、执行 (5) 的权限, 给其他组分配执行(1)的权限
  chmod 751 file
```

#### chgrp
> 说明:
通过群组识别码改变文件群组属性, 100 为 users 群组的识别码, 具体群组和群组识别码可以去 /etc/group 文件中查看
```shell
# 改变文件的群组属性
  # 将 xxx.log 文件由原有群组改为 group_name 群组
  chgrp -v group_name xxx.log
  
# 根据指定文件改变文件的群组属性
  # 改变文件 yyy.log 的群组属性, 使得文件 yyy.log 的群组属性和参考文件 xxx.log 的群组属性相同
  chgrp --reference=xxx.log yyy.log
  
# 改变指定目录以及其子目录下的所有文件的群组属性
  chgrp -R group_name directory_name
  
# 通过群组识别码改变文件群组属性
  chgrp -R 100 directory_name
```

#### chown
```shell
# 改变拥有者和群组
  chown username group_name xxx.log
  
# 改变文件拥有者和群组
  # 省略后面的群组, 将群组也改为 username 对应的群组
  chown username: xxx.log
  
# 改变文件群组
  # 只会改变对应的群组为 group_name
  chown :group_name xxx.log
  
# 改变指定目录以及其子目录下的所有文件的拥有者和群组(-v 显示详细的处理信息)
  chown -R -v username:group_name xxx.log
```
