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

#### top
```shell
# 显示进程信息
  top
说明:
统计信息区:
    前五行是当前系统情况整体的统计信息区. 下面我们看每一行信息的具体意义.
第一行, 任务队列信息, 同 uptime 命令的执行结果, 具体参数说明情况如下:
  23:25:28 - 当前系统时间
  up 70 days, 16:44 - 系统已经运行了 70 天 16 小时 44 分钟(在这期间系统没有重启过的!)
  2 users - 当前有 2 个用户登录系统
  load average: 1.15, 1.42, 1.44 - load average 后面的三个数分别是 1 分钟、5 分钟、15 分钟的负载情况.
  load average 数据是每隔 5 秒钟检查一次活跃的进程数, 然后按特定算法计算出的数值. 如果这个数除以逻辑 cpu 的数量,
  结果高于 5 的时候就表明系统在超负荷运转了.
 
第二行, Tasks - 任务(进程), 具体信息说明如下:
系统现在共有 206 个进程, 其中出于运行中的有 1 个, 205 个在休眠(sleep), stop 状态的有 0 个, zombie状态(僵尸)的有 0 个.

第三行, cpu 状态信息, 具体属性说明如下:
5.9 %us - 用户空间占用 cpu 的百分比
3.4 % sy - 内核空间占用 cpu 的百分比
0.0% ni - 改变过优先级的进程占用 cpu 的百分比
90.4% id - 空闲 cpu 的百分比
0.0% wa - IO 等待占用 cpu 的百分比
0.0% hi - 硬终端(Hardware IRQ) 占用 cpu 的百分比
0.2% si - 软终端(Software Interrupts) 占用 cpu 的百分比

第四行, 内存状态, 具体信息如下:
32949016k total - 物理内存总量(32GB)
14411180k used - 使用中的内存总量(14GB)
18537836K free - 空闲内存总量(18GB)
169884k buffers - 缓存的内存量(169M)

第五行, swap 交换分区信息, 具体信息说明如下:
32764556k total - 交换区总量(32GB)
0k used - 使用的交换区总量(OK)
32764556k free - 空闲交换区总量(32GB)
3612636k cached - 缓冲的交换区总量(3.6GB)

备注：
第四行中使用中的内存总量（used）指的是现在系统内核控制的内存数，空闲内存总量（free）是内核还未纳入其管控范围的数量。纳入内核管理的内存不见得都在使用中，还包括过去使用过的现在可以被重复利用的内存，内核并不把这些可被重新使用的内存交还到free中去，因此在linux上free内存会越来越少，但不用为此担心。

如果出于习惯去计算可用内存数，这里有个近似的计算公式：第四行的free + 第四行的buffers + 第五行的cached，按这个公式此台服务器的可用内存：18537836k +169884k +3612636k = 22GB左右。

对于内存监控，在top里我们要时刻监控第五行swap交换分区的used，如果这个数值在不断的变化，说明内核在不断进行内存和swap的数据交换，这是真正的内存不够用了。

第六行, 空行.
第七行一下: 各进程(任务) 的状态监控, 项目列信息说明如下:
  PID - 进程id
  USER - 进程所有者
  PR - 进程优先级
  NI - nice 值. 负值表示高优先级, 正值表示低优先级
  VIRT - 进程使用的虚拟内存总量, 单位 kb. VIRT=SWAP+RES
  RES - 进程使用的、未被换出的物理内存大小, 单位 kb. RES=CODE+DATA
  SHR - 共享内存大小, 单位 kb
  S - 进程状态. D=不可中断的睡眠状态 R=运行 S=睡眠 T=跟踪/停止 Z=僵尸进程
  %CPU - 上次更新到现在的 cpu 时间占用百分比
  % MEM - 进程使用的物理内存百分比
  TIME+ - 进程使用的 cpu 时间总计, 单位 1/100秒
  COMMAND - 进程名称(命令名/命令行)

其他使用技巧:
1. 多 U 多核 cpu 监控
  在 top 基本试图中, 按键盘数字 "1", 可监控每天逻辑 cpu 的状况. 再按数字键 1, 就会返回到 top 基本视图界面.
  
2. 高亮显示当前运行进程
  敲击键盘 "b"(打开/关闭 高亮效果), top 的视图变化如下:
  我们发现进程 id 为 2570 的 "top" 进程被高亮了, top 进程就是视图第二行显示的唯一的运行态(running)的那个进程, 可以通过敲击 "y" 键关闭或打开运行态进程的高亮效果.
  
3. 进程字段排序
  默认进入 top 时, 各进程是按照 cpu 的占用量来排序的, 在下图中进程 ID 为 28894 的 java 进程排在第一, 进程 ID 为 574 的 java 进程排在第二(cpu 占用 16%).
  敲击键盘 "x"(打开/关闭 排列序的高亮效果), top的视图变化如下:
  可以看到, top 默认的排序列是 "%CPU".
  
4. 通过 "shift + >" 或者 "shift + <" 可以向右或向左改变排序列
下面按一次 "shift + >" 的效果图, 视图现在已经按照 %MEM 来排序

# 显示 完整命令
  top -c

# 以批处理模式显示程序信息
  top -b
  
# 以累积模式显示程序信息
  top -S
 
# 设置信息更新次数
  # 表示更新周期为 3 秒
  top -d 3
  
# 显示指定的进程信息
  top -p 574
  
# top 交互式命令(详细查阅文档)
```

