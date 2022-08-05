
df

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

#### lsof
```shell
# 无任何参数
  lsof
说明:
  lsof 输出各列信息的意义如下:
  COMMAND: 进程的名称
  PID: 进程表示符
  PPID: 父进程表示符(需要指定 -R 参数)
  USER: 进程所有者
  PGID: 进程所属组
  FD: 文件描述符, 应用程序通过文件描述符识别该文件. 如 cwd、 txt 等.

# 查看谁正在使用某个文件, 也就是说查找某个文件相关的进程
  # 查找使用 bash 文件的进程
  lsof /bin/bash
  
# 递归查看某个目录的文件信息
  lsof test/test3
说明:
  使用了 +D, 对应目录下的所有子目录和文件都会被列出
  
# 不使用 +D 选项, 遍历查看某个目录的所有文件信息的方法
  lsof | grep 'test/test3'
  
# 列出某个用户打开的文件信息
  lsof -u username
说明:
  -u 选项, u 其实是 user 的缩写
  
# 列出某个程序进程所打开的文件信息
  lsof -c mysql
说明:
  -c 选项将会列出所有以 mysql 这个进程开头的程序的文件, 其实你也可以写成 lsof | grep mysql, 但是第一种方法明显比第二种方法要少打几个字符了
  
# 列出多个进程多个打开的文件信息
  lsof -c mysql -c apache
  
# 列出某个用户以及某个进程所打开的文件信息
  lsof -u username -c mysql
说明:
  用户与进程可相关, 也可以不相关
  
# 列出除了某个用户外的被打开的文件信息
  lsof -u ^root
说明:
  ^ 这个符号在用户名之前, 将会把是 root 用户打开的进程不让显示
  
# 通过某个进程号显示该进程打开的文件
  lsof -p 1
  
# 列出多个进程号对应的文件信息
  lsof -p 1, 2, 3
  
# 列出除了某个进程号, 其他进程号所打开的文件信息
  lsof -p ^1
  
# 列出所有的网络连接
  lsof -i
  
# 列出所有 tcp 网络连接信息
  lsof -i tcp
  
# 列出所有 udp 网络连接信息
  lsof -i udp
  
# 列出谁在使用某个端口
  lsof -i:3306

# 列出谁在使用某个特定的 udp 端口
  lsof -i udp:55
  
# 列出谁在使用特定的 tcp 端口
  lsof -i tcp:80

# 列出某个用户的所有活跃的网络端口
  lsof -a -u test -i
  
# 列出所有网络文件系统
  lsof -N
  
# 域名 socket 文件
  lsof -u
  
# 某个用户组所打开的文件信息
  lsof -g 5555
  
# 根据文件描述列出对应的文件信息
  lsof -d description(like 2)
说明:
  0 表示标准输入, 1 表述标准输出, 2 表示标准错误, 从而可知: 所以大多数应用程序所打开的文件的 FD 都是从 3 开始
  
# 根据文件描述范围列出文件信息
  lsof -d 2-3
  
# 列出 COMMAND 列中包含字符创 " sshd", 且文件描述符的类型为 txt 的文件信息
  lsof -c sshd -a -d txt
  
# 列出被进程号为 1234 的进程所打开的所有 IPV4 network files
  lsof -i 4 -a -p 1234
  
# 列出目前连接主机 peida.linux 上端口为 20, 21, 22, 25, 53, 80 相关的所有文件信息, 且每隔 3 秒不断的执行 lsof 指令
  lsof -i @peida.linux:20,21,22,25,53,80 -r 3
```

#### netstat
```shell
# 列出所有端口
  netstat -a
说明:
  显示一个所有的有效连接信息列表, 包括已建立的连接(ESTABLISHED), 也包括监听连接请(LISTENING)的那些连接.
  
# 显示当前 UDP 连接状况
  netstat -nu
  
# 显示 UDP 端口号的使用情况
  netstat -apu
  
# 显示网卡列表
  netstat -i
  
# 显示组播组的关系
  netstat -g
  
# 显示网络统计信息
  netstat -s
说明:
  按照各个协议分别显示其统计数据. 如果我们的应用程序(如 web 浏览器)运行速度比较慢, 或者不能显示 web 页之类的数据, 那么我们就可以用本选项来查看一下所显示的信息. 我们需要仔细查看统计数据的各行, 找到出错的关键字, 进而确定问题所在.

# 显示监听的套接字
  netstat -l
  
# 显示所有已建立的有效连接
  netstat -n
  
# 显示关于以太网的统计数据
  netstat -e
说明:
  用于显示关于以太网的统计数据. 它列出的项目包括传送的数据报的总字节数、错误数、删除数、数据报的数量和广播的数量. 这些统计数据既有发送的数据报的数量, 也有接收的数据报数量. 这个选项可以用来统计一些基本的网络流量)
  
# 显示关于路由表的信息
  netstat -r

# 列出所有 tcp 端口
  netstat -at
  
# 统计机器中网络连接各个状态个数
  netstat -a | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}'
  
# 把状态全部取出来后使用 uniq -c 统计后再进行排序
  netstat -nat | awk '{print $6}' | sort | uniq -c
  
# 查看连接某服务端口最多的 IP 地址
  netstat -nat | grep "192.168.120.20:16067" | awk '{print $5}' | awk -F: '{print $4}' | sort | uniq -c | sort -nr | head -20
  
# 找出程序运行的端口
  netstat -ap | grep ssh
  
# 在 netstat 输出中显示 PID 和进程名称
  netstat -pt
说明:
  netstat -p 可以与其他开关一起使用, 就可以添加 "PID/进程名称" 到 netstat 输出中, 这样 debugging 的时候可以很方便的发现特定端口运行的程序

# 找出运行在指定端口的进程
  netstat -anpt | grep ':16064'

说明:
  运行在端口 16064 的进程 id 为 24596, 再通过 ps 命令就可以找到具体的应用程序了.
```

#### date
```shell
# 显示当前时间
  date 
  date'+%c'
  date'+%D'
  date'+%x'
  date+'%T'
  date+'%X'
  
# 取当前时间(精确到秒)
  date '+%Y%m%d%H%M%S'
```

#### grep
```shell
# 查找指定进程
  ps -ef | grep svn
说明:
  第一条记录是查找出的进程; 第二条结果是 grep 进程本身, 并非真正要找的进程.
  
# 查找指定进程个数
  ps -ef | grep svn -c
  ps -ef | grep -c svn
  
# 从文件中读取关键词进行搜索
  cat test.txt | grep -f test2.txt
说明:
  输出 test.txt 文件中含有从 test2.txt 文件中读取出的关键词的内容行. 简单的理解就是
  从 test.txt 中过滤出包含了 test2.txt文件中内容的 内容
  
# 从文件中读取关键词进行搜索 且显示行号
  cat test.txt | grep -nf test2.txt
说明:
  输出 test.txt 文件中含有从 test2.txt 文件中读取出的关键词的内容行, 并显示每一行的行号

# 从文件中查找关键词
  grep 'linux' test.txt
  
# 从多个文件中查找关键词
  grep 'linux' test.txt test2.txt
说明:
  多文件时, 输出查询到的信息内容时, 会把文件的命名在行最前面输出并且加上 ":" 作为标识符
  
# grep 不显示本身进程
  ps aux | grep \[s]sh
  ps aux | grep ssh | grep -v "grep"
  
# 找出 以 u 开头的行内容
  cat test.txt | grep ^u
  
# 输出非 u 开头的行内容
  cat test.txt | grep ^[^u]
  
# 输出以 hat 结尾的行内容
  cat test.txt | grep hat$
  
# 显示包含 ed 或者 at 字符的内容行
  cat test.txt | grep -E "ed|at"
  
# 显示当前目录下面以 .txt 结尾的文件中的所有包含每个字符串至少有 7 个连续小写字符的字符串的行
  grep '[a-z]\{7\}' *.txt
```

#### ps
```shell
# 显示所有进程信息
  ps -A
  
# 显示指定用户信息
  ps -u root
# 显示所有进程信息, 连同命令行
  ps -ef
  
# ps 与 grep 常用组合写法, 查找特定进程
  ps -ef | grep ssh
  
# 将目前属于您自己这次登录的 PID 与相关信息列示出来
  ps -l
  
# 列出目前所有的正在内存当中的程序
  ps aux

# 列出类似程序树的程序显示
  ps -axjf
  
# 找出与 cron 与 syslog 这两个服务有关的 PID 号码
  ps aux | egrep '(cron|syslog)'
说明:
其他实例:
1. 可以用 | 管道和 more 连接起来分页查看
  ps -aux | more
2. 把所有进程显示出来, 并输出到 ps001.txt 文件
  ps -aux > ps001.txt
3. 输出指定的字段
  ps -o pid,ppid,pgrp,session,tpgid,comm
```
