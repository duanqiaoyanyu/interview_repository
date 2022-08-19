
### Linux 目录结构
| 目录 |       应放置档案内容        |
| :---- |:--------------------:|
| /bin | 放置在单人维护模式下还能够被操作的指令  |
| /boot | 放置开机会使用到的档案 |
| /dev | 任何装置与周边设备都是以档案的形态存在于这个目录中 |
| /etc | 系统主要的设定档几乎都放置在这个目录内 |
 | /home | 使用者家目录, 预设的使用者家目录都规范到这里来。家目录还有一种代号:~ |
| /lib | 放置核心相关的模组(驱动程式) |
| /media | 放置可移除的装置. 包括软碟、DVD等 |
| /mnt | 暂时挂载某些额外的装置 |
| /opt | 第三方协议软体放置的目录 |
| /root | 系统管理员 (root) 的家目录 |
| /sbin | 开机过程中所需要的, 包括开机、修复、还原系统所需要的指令 |
| /srv | 一些网络服务启动之后, 这些服务所需要取用的资料目录 |
| /tmp | 一般使用者或者是正在执行的程序暂时放置档案的地方 |
| /lost + foumd | 档案系统发生错误时, 将一些遗失的片段放置到这个目录下 |
| /proc | 虚拟文件系统 |
| /sys | 类似 /proc 记录与核心相关的咨询 |

df

#### ls
```shell
# 列出 /home/peidachang 文件夹下的所有文件和目录的详细资料
  ls -l -R /home/peidachang
在使用 ls 命令时要注意命令的格式: 在命令提示符后, 首先是命令的关键字, 接下来是命令参数, 在命令参数之前要有 - 短横线 "-",所有的命令参数都有特定的作用, 自己可以根据需要选用一个或者多个参数, 在命令参数的后面是命令的操作对象. 在以上这条命令 "ls -l -R /home/peidachang" 中, "ls" 是命令关键字, "-l -R" 是参数, "/home/peidachang" 是命令的操作对象. 在这条命令中, 使用到了两个参数, 分别为 "l" 和 "R", 当然, 你也可以把他们放在一起使用, 如下所示:
  ls -lR /home/peidachang
  这种形式和上面的命令形式执行的结果是完全一样的. 另外, 如果命令的操作对象位于当前目录中, 可以直接对操作对象进行操作, 如果不在当前目录则需要给出操作对象的完整路径, 例如上面的例子中, 我的当前文件夹是 peidachang 文件夹, 我想对 home 文件夹下的 peidachang 文件进行操作, 我可以直接输入 ls -lR peidachang, 也可以使用 ls -lR /home/peidachang
  
# 列出当前目录中所有以 "t" 开头的文件的详细内容, 可以使用如下命令:
  ls -l t*
可以查看当前目录下文件名以 "t" 开头的所有文件的信息. 其实, 在命令格式中, 方括号内的内容都是可以省略的, 对于命令 ls 而言, 如果省略命令参数和操作对象, 直接输入 "ls", 则将会列出当前工作目录的内容清单.

# 只列出文件下的子目录
  # 列出 /opt/soft 文件下面的子目录
  ls -F /opt/soft | grep /$

  # 列出 /opt/soft 文件下面的子目录详细情况
  ls -l /opt/soft | grep "^d"

# 列出目前工作目录下所有名称是 s 开头的文件, 越新的排越后面, 可以使用如下命令
  ls -lrt s*
  
# 列出目前工作目录下所有档案及目录: 目录于名称后加 "/", 可执行档于名称后加 "*"
  ls -AF
  
# 计算当前目录下的文件数和目录数
  ls -l * | grep "^-" | wc -l 文件个数
  ls -l * | grep "^d" | wc -l 目录个数
  
# 在 ls 中列出文件的绝对路径
  ls | sed "s:^:`pwd`/:"

# 列出当前目录下的所有文件(包括隐藏文件)的绝对路径, 对目录不做递归
  find $PWD -maxdepth 1 | xargs ls -ld
  
# 指定文件时间输出格式
  ls -lt --time-style=full-iso
  ls -clt --time-style=long-iso
```

#### mkdir
```shell
# 创建一个空目录
  mkdir test1
  
# 递归创建多个目录
  mkdir -p test2/test22

# 创建权限为 777 的目录
  mkdir -m 777 test3
说明:
test3 的权限为 rwxrwxrwx

# 创建新目录都显示信息
  mkdir -v test4

# 一个命令创建项目的目录结构
  mkdir -vp scf/{lib/, bin/, doc/{info, product}, logs/{info, product}, service/deploy/{info, product}}
```

#### rm
```shell
# 删除文件 file, 系统会先询问是否删除
  rm 文件名
说明:
  输入 rm log.log 命令后, 系统会询问是否删除, 输入 y 后就会删除文件, 不想删除则输入 n

# 强行删除 file, 系统不再提示
  rm -r log1.log
  
# 删除任何 .log 文件; 删除前逐一询问确认
  rm -i *.log

# 将 test1 子目录及子目录中的所有文件删除
  rm -r test1
  
# rm -rf test2 命令会将 test2 子目录及子目录中所有档案删除, 并且不用一一确认
  rm -rf test2
 
# 删除以 -f 开头的文件
  rm -- -f
```

#### mv
```shell
# 文件改名
  mv test.log test1.txt
说明:
  将文件 test.log 重命名为 test1.txt
  
# 移动文件
  mv test1.txt test3
说明:
  将 test1.txt 文件移动到目录 test3 中

# 将文件 log1.txt, log2.txt, log3.txt 移动到目录 test3 中
  mv log1.txt log2.txt log3.txt test3
  mv -t /opt/soft/test/test4/ log1.txt log2.txt log3.txt
说明:
  mv log1.txt log2.txt log3.txt test3 命令把 log1.txt log2.txt log3.txt 三个文件移到 test3 目录中去,
  mv -t /opt/soft/test/test4/ log1.txt log2.txt log3.txt 命令又将三个文件移动到 test4 目录中去

# 将文件 file1 改名为 file2, 如果 file2 已经存在, 则询问是否覆盖
  mv -i log1.txt log2.txt
  
# 将文件 file1 改名为 file2, 即使 file2 存在, 也是直接覆盖掉
  mv -f log3.txt log2.txt
说明:
log3.txt 的内容直接覆盖了 log2.txt 内容, -f 这是个危险的选项, 使用的时候一定要保持头脑清晰, 一般情况下最好不用加上它.

# 目录的移动
  mv dir1 dir2
说明:
  如果目录 dir2 不存在, 将目录 dir1 改名为 dir2; 否则, 将 dir1 移动到 dir2 中
  
# 移动当前文件夹下的所有文件到上一级目录
  mv * ../
  
# 把当前目录的一个子目录里的文件移动到另一个子目录里
  mv test3/*.txt test5
  
# 文件被覆盖前做简单备份, 前面加参数 -b
  mv log1.txt -b log2.txt
说明:
-b 不接受参数, mv会去读取环境变量VERSION_CONTROL来作为备份策略.
--backup该选项指定如果目标文件存在时的动作, 共有四种备份策略:
1.CONTROL=none或off : 不备份.
2.CONTROL=numbered或t:数字编号的备份
3.CONTROL=existing或nil:如果存在以数字编号的备份, 则继续编号备份m+1...n:
执行mv操作前已存在以数字编号的文件log2.txt.~1~, 那么再次执行将产生log2.txt~2~, 以次类推. 如果之前没有以数字编号的文件, 则使用下面讲到的简单备份.
4.CONTROL=simple或never:使用简单备份:在被覆盖前进行了简单备份, 简单备份只能有一份, 再次被覆盖时, 简单备份也会被覆盖.
```

#### cp
```shell
# 复制单个文件到目标目录, 文件在目标文件夹中不存在
  cp log.log test5
说明:
  在没有带 -a 参数是, 两个文件的时间是不一样的. 在带了 -a 参数时, 两个文件的时间是一致的.

# 目标文件存在时, 会询问是否覆盖
  cp log.log test5
说明:
  目标文件存在时, 会询问是否覆盖. 这是因为 cp 是 cp -i 的别名. 目标文件存在时, 即使加了 -f 标志, 也还会询问是否覆盖.
  
# 复制整个目录
  cp -a test3 test5
说明:
  注意目标目录存在与否结果是不一样的. 目标目录存在时, 整个源目录被复制到目标目录里.
  
# 复制的 log.log 简历一个链接档 log_link.log
  cp -s log.log log_link.log
说明:
  那个 log_link.log 是由 -s 的参数造成的, 建立的是一个『快捷方式』, 所以会看到在文件的最右边, 会显示这个文件是『链接』到哪里去的
```

#### cat
```shell
# 把 log2012.log 的文件内容加上行号后输入 log2013.log 这个文件里
  cat -n log2012.log log2013.log

# 把 log2012.log 和 log2013.log 的文件内容加上行号(空白行不加) 之后将内容附加到 log.log 里
  cat -b log2012.log log2013.log log.log

# 把 log2012.log 的文件内容加上行号输入到 log.log 这个文件
  cat -n log2012.log > log.log
  
备注:
tac (反向列示)
命令:
  tac log.txt
说明:
  tac 是将 cat 反写过来, 所以他的功能就跟 cat 相反, cat 是由第一行到最后一行连续显示在荧幕上, 而 tac 则是由最后一行到第一行反向在荧幕上显示出来!.
```

#### more
```shell
# 显示文件中从第 3 行起的内容
  more +3 log2012.log

# 从文件中查找第一个出现 "day3" 字符串的行, 并从该处前两行开始显示输出
  more +/day3 log2012.log
  
# 设定每屏显示行数
  more -5 log2012.log
说明:
  如下图所示, 最下面显示了该屏展示的内容占文件总行数的比例, 按 Ctrl + F 或者 空格键 将会显示下一屏 5 条内容, 百分比也会跟着变化.
  
# 列一个目录下的文件, 由于内容太多, 我们应该学会用 more 来分页显示. 这得和管道 | 结合起来
  ls -l | more -5
说明:
每页显示 5 个文件信息, 按 Ctrl + F 或者 空格键 将会显示下 5 条文件信息.
```

#### less
```shell
# 查看文件
  less log2013.log
  
# ps 查看进程信息并通过 less 分页显示
  ps -ef | less
  
# 查看命令历史使用记录并通过 less 分页显示
  history | less
  
# 浏览多个文件
  less log2013.log log2014.log
说明:
输入  : n后, 切换到 log2014.log
输入  : p后, 切换到 log2013.log

# 附加备注
1. 全屏导航
  ctrl + F - 向前移动一屏
  ctrl + B - 向后移动一屏
  ctrl + D - 向前移动半屏
  ctrl + U - 向后移动半屏

2. 单行导航
  j - 向前移动一行
  k - 向后移动一行

3. 其他导航
  G - 移动到最后一行
  g - 移动到第一行
  q / zz - 退出 less 命令

4. 其他有用的命令
  v - 使用配置的编辑器编辑当前文件
  h - 显示 less 的帮助文档
  &pattern - 仅显示匹配模式的行, 而不是整个文件
  
5. 标记导航
  当使用 less 查看大文件时, 可以在任何一个位置作标记, 可以通过命令导航到标有特定标记的文本位置:
  ma - 使用 a 标记文本的当前位置
  'a' - 当行到标记 a 处
```

#### tar
```shell
# tar usage and options
  c - create an archive file
  C - specified directory
  x - extract an archive file
  v - verbosely show the progress of the archive file
  f - filename of the archive file
  t - viewing the content of the archive file
  z - filter archive through gzip
  
# 将文件全部打包成 tar 包
  # 仅打包, 不压缩!
  tar -cvf log.tar log2012.log
  # 打包后, 以 gzip 压缩
  tar -zcvf log.tar.gz log2012.log
  # 打包后, 以 bzip2 压缩
  tar -jcvf log.tar.bz2 log2012.log
在参数 f 之后的文件名是自己取的, 我们习惯上都以 .tar 来作为辨识. 如果加 z 参数, 则以 .tar.gz 或 .tgz 来代表 gzip 压缩过的 tar 包; 如果加 j 参数, 则以 .tar.bz2 来作为 tar 包名.

# 查阅上述 tar 包内有那些文件
  tar -ztvf log.tar.gz
说明:
  由于我们使用 gzip 压缩的 log.tar.gz, 所以要查阅 log.tar.gz 包内的文件时, 就得要加上 z 这个参数了.
  
# 将 tar 包解压缩
  tar -zxvf /opt/soft/test/log.tar.gz
说明:
  在预设的情况下, 我们可以将压缩文件在任何地方解开的
  
# 只将 /tar 内的部分文件解压出来
  tar -zxvf /opt/soft/log30.tar.gz log2013.log
说明:
  我们可以通过 tar -ztvf 来查阅 tar 包内的文件名称, 如果单只要一个文件, 就可以通过这个方式来解压部分文件
  
# 文件备份下来, 并且保存其权限
  tar -zcvpf log31.tar.gz log2014.log log2015.log log2016.log
说明:
  这个 -p 的属性是很重要的, 尤其是当您要保留原本文件的属性时.
  
# 在 文件夹当中, 比某个日期新的文件才备份
  tar -N "2012/11/13" -zcvf log17.tar.gz test
  
# 备份文件夹内容时排除部分文件
  tar --exclude scf/service -zcvf scf.tar.gz scf/*
```

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

#### diff
```shell
#### 比较两个文件
  diff log2014.log log2013.log
说明:
  上面的 "3c3" 和 "8c8" 表示 log2014.log 和 log2013.log 文件在 3 行 和第 8 行 内容有所不同; "11,12d10" 表示第一个文件比第二个文件多了第 11 行和 12 行.

# diff 的 normal 显示格式有三种提示
  a -add 
  c -change
  d -delete

# 并排格式输出
  diff log2013.log log2014.log -y -W 50
说明:
  "|" 表示前后 2 个文件内容有不同
  "<" 表示后面文件比前面文件少了 1 行内容
  ">" 表示后面文件比前面文件多了 1 行内容

# 上下文输出格式
  diff log2013.log log2014.log -c
说明:
  这种方式在开头两行作了比较文件的说明, 这里也有三种特殊字符:
  "+" 比较的文件的后者比前者多一行
  "-" 比较的文件的后者比前者少一行
  "! " 比较的文件两者有差别的行
  
# 统一格式输出
  diff log2014.log log2013.log -u
说明:
  它的第一部分, 也是文件的基本信息:
  --- log2014.log 2012-12-07 18:01:54. 0000000000 +0800
  +++ log2013.log 2012-12-07 18:36:26. 0000000000 +0800
  "---" 表示变动前的文件, "+++" 表示变动后的文件
  第二部分, 变动的位置用两个 @ 作为起首和结束.
  @@ -1,12 +1,10 @@
  前面的 "-1,12" 分成三个部分: 减号表示第一个文件(即 log2014.log), "1"表示第 1 行, "12" 表示连续 12 行. 合在一起, 就表示下面是第一个文件从第 1 行开始的连续 12 行. 同样的, "+1, 10" 表示变动后, 成为第二个文件从第 1 行开始的连续 10 行.
  
# 比较文件夹不同
  diff test3 test6

# 比较两个文件不同, 并生产补丁
  diff -ruN log2013.log log2014.log > patch.log

# 打补丁
  cat log2013.log
  patch log2013.log patch.log
```

告一段落
