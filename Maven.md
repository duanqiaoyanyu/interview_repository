1. maven 父子工程中, 指定父子关系时的版本号不能通过 `<properties/>` 标签里的内容去指定, 因为当指定父子关系的时候, 这个时候压根就还不知道父亲是谁,
这种情况下都没办法得知对应的父亲版本有没有这个对应的 `属性值`, 所以说. `pom` 文件在解析父子关系版本号这个时候就使用不了属性引用, 只会把它 `<version/>`
中间的内容当一个字符串来看待, 这样得到的结果就是 `"${xxxxxx}"`, 所以就会报错, 说版本找不到什么之类的, 因为压根就没有一个叫做 `project-${xxxxxx}` 版本的
依赖, `${xxxxxx}` 引用不了你想要的值, 它只能被当作一个普通的字符串.

2. 升级 `父 Pom` 的 `<dependencies/>` 记得要同时升级 `父 Pom` 和 `子 Pom` 的 `<version/>`

3. `repositories`、`distributionManagement`、`pluginRepositories` 中 repository 的区别
- **3.1 repositories 中的 repository**  
表示从什么库地址可以下载项目依赖的库文件, 比如
```xml
<repositories>
    <repository>
        <id>nexus</id>
        <name>Nexus</name>
        <url>https://maven.aliyun.com/repository/central</url>
        <releases>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
            <checksumPolicy>warn</checksumPolicy>
        </releases>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
            <checksumPolicy>warn</checksumPolicy>
        </snapshots>
    </repository>
</repositories>
```
repository 中的字段说明如下
```shell
id, 库的ID
name, 库的名称
url, 库的 URL
layout, 在 Maven 2/3 中都是 default, 只有在 Maven 1.x 中才是 legacy
releases, 库中版本为 releases 的构件
snapshots, 库中版本为 snapshots 的构件
  - enabled, 是否支持更新
  - updatePolicy, 构件更新的策略, 可选值有 daily, always, never, interval:X(其中的 X 是一个数字, 表示间隔的时间, 单位 min), 默认为 daily
  - checksumPolicy,校验码异常的策略, 可选值有 ignore, fail, warn
  - layout, 在 Maven 2/3 中都是 default, 只有在 Maven1.x 中才是 legacy
```
如果需要认证才能访问, 则需要在 `setting.xml` 文件中添加如下内容, 并且 sever 中的 id 字段的值要与 repository 中 id 字段的值相同
```xml
<servers>
    <server>
        <id>nexus</id>
        <username>admin</username>
        <password>admin123</password>
    </server>
</servers>
```
- **distributionManagement 中的 repository**  
表示的是项目打包成库文件后要上传到什么库地址, 比如:
```xml
<distributionManagement>
    <repository>
        <uniqueVersion>false</uniqueVersion>
        <id>releases</id>
        <name>releases</name>
        <url>https://maven.aliyun.com/repository/releases</url>
        <layout>default</layout>
    </repository>

    <snapshotRepository>
        <uniqueVersion>true</uniqueVersion>
        <id>snapshots</id>
        <name>snapshots</name>
        <url>https://maven.aliyun.com/repository/snapshots</url>
        <layout>legacy</layout>
    </snapshotRepository>
</distributionManagement>
```
与 `repositories` 中的 `repository` 不同的是, `distributionManagement` 中分为 `repository` 和 `snapshotRepository`, 但里面的字段大致
与 `repositories` 中的 `repository` 内的相同. 同样, 如果访问需要认证的话, 也需要在 `setting.xml` 文件中设置:
```xml
<servers>
    <server>
        <id>releases</id>
        <username>admin</username>
        <password>admin123</password>
    </server>
    <server>
        <id>snapshots</id>
        <username>admin</username>
        <password>admin123</password>
    </server>
</servers>
```
- **pluginRepository 中的 repository**  
`pluginRepositories` 中的 `repository` 是以 `pluginRepository`表示的, 它表示插件从什么库地址下载
```xml
<pluginRepositories>
    <pluginRepository>
        <id>spring-plugin</id>
        <name>spring-plugin</name>
        <url>https://maven.aliyun.com/repository/spring-plugin</url>
        <releases>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
            <checksumPolicy>warn</checksumPolicy>
        </releases>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
            <checksumPolicy>warn</checksumPolicy>
        </snapshots>
    </pluginRepository>
</pluginRepositories>
```
它里面的字段和用法与 `repositories` 中的 `repository` 基本一致.


参考文档:
1. https://juejin.cn/post/6844903942170607624
