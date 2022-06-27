`Windows` 环境下多个版本 JDK 共存与切换

这里就举例安装 JDK8 和 JDK11

1. 先安装JDK8, 安装一直点下一步就好了. 然后注意安装的目录建议手动选择一个安装的目录, 我这里安装的是 C:\MyDevelop\Java\jdk1.8.0_333。安装JDK8还有个小细节,
中间会同时需要安装JRE, 然后建议是和 JDK 在一个目录下, 需要手动创建JRE 目录, 在 C:\MyDevelop\Java 下创建这样一个目录 jre1.8.0_333。然后
一直下一步就安装成功了.
2. JDK11的安装会简单很多, 因为 JDK11 的 JRE 集成到了JDK的安装里了, 所以只需要手动选择一个安装目录就好 C:\MyDevelop\Java\jdk-11.0.15.1
3. 然后就是配置环境变量这里建议这么配置 先分别新增一个 JDK8的 "JAVA_HOME" 和一个 JDK11的 "JAVA_HOME"
4. 于是就有在 高级系统设置-环境变量-系统变量中新建两个
5. 变量名: JAVA_HOME8 变量值: C:\MyDevelop\Java\jdk1.8.0_333
6. 变量名: JAVA_HOME11 变量值: C:\MyDevelop\Java\jdk-11.0.15.1
7. 然后再新建一个 变量名: JAVA_HOME 变量值:%JAVA_HOME8%        (变量值是填 %JAVA_HOME8% 还是 %JAVA_HOME11% 取决于你当前想用什么版本)
8. 然后再到 Path 变量中 新增一个 %JAVA_HOME%\bin
9. 这样我们的环境变量就算配置完成了吗? 不这里有一个小坑, 配没配置完取决于你新增的 %JAVA_HOME%\bin 是否是在 Path 变量中靠前的位置,
具体要多靠前呢, 取决于两个值的位置 一个 C:\Program Files\Common Files\Oracle\Java\javapath 和 另一个 C:\Program Files (x86)\Common Files\Oracle\Java\javapath
要在这两个变量之前. **如果嫌麻烦, 直接把 %JAVA_HOME%\bin 放在 Path 变量的最前面即可**, 这样需要用到 JAVA 相关的比如 java || javac, 他就会
优先从 当前目录 -> Path变量 的顺序进行查找, 而 %JAVA_HOME%\bin 放在最前面那么就会从我们设置的目录进行查找, 这里说明一下 上面那两个目录
   C:\Program Files\Common Files\Oracle\Java\javapath 和 C:\Program Files (x86)\Common Files\Oracle\Java\javapath
是你在安装 JDK 的时候给你生成的, 环境变量也是那时候加上的. 在多 JDK 的情况下他会干扰我们的 JAVA_HOME 的作用, 所以我们为了强制让我们的 JAVA_HOME
起到作用, 需要将 JAVA_HOME 的位置提到最前, 这样才不会被干扰, 以后切换 JDK 也仅仅只需要将 JAVA_HOME里面的变量值改变一下
例如 JDK8 -> JDK11: %JAVA_HOME8% -> %JAVA_HOME11%
    JDK11 -> JDK8:  %JAVA_HOME11% -> %JAVA_HOME8%

总结: 其实总的来说就是 Windows 系统下环境变量和程序加载的问题. 搞懂环境变量是什么? 有什么用? 程序是怎么启动的其实就很好理解上面的了.

参考资料:
https://blog.51cto.com/u_15349906/3716713
https://blog.csdn.net/qq_37235616/article/details/106159841
https://www.cnblogs.com/Y-zhiwei/p/8144583.html
https://www.zhihu.com/question/28428426
