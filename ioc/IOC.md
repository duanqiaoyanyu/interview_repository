#### 什么是循环依赖
循环依赖, 其实就是循环引用, 就是两个或者两个以上的 `bean` 互相引用对方, 最终形成一个闭环, 如 `A` 依赖 `B`, `B` 依赖 `C`,
`C` 依赖 `A`. A -> B -> C -> A  
循环依赖, 其实就是一个 **死循环** 的过程, 在初始化 `A` 的时候发现引用了 `B`, 这时就会去初始化 `B`, 然后又发现 `B` 引用 `C`,
跑去初始化 `C`, 初始化 `C` 的时候发现引用了 `A`, 则又会去初始化 `A`, 依次循环永不退出, 除非有**终结条件**.  

三级缓存:
1. 第一级为 `singletonObjects`: 单例对象的 `Cache`
2. 第二级为 `earlySingletonObjects`: **提前曝光**的单例对象的 `Cache`
3. 第三级为 `singletonFactories`: 单例对象工厂的 `Cache`

过程:  
- 首先, 从一级缓存 `singletonObjects` 获取
- 如果一级缓存中没有并且当前指定的 `beanName` 正在创建, 就再从二级缓存 `earlySingletonObjects` 中获取.
- 如果二级缓存中还是没忽悠获取到且允许 `singletonFactories` 通过 `#getObject()` 方法获取, 则从三级缓存 `singletonFactories`
获取. 如果获取到, 则通过其 `#getObject()` 方法, 获取对象, 并将其加入到二级缓存 `earlySingletonObjects` 中, 并从三级缓存 `singletonFactories` 删除.
- 这样, 就从三级缓存 **升级** 到二级缓存了
- 所以, 二级缓存存在的意义, 就是缓存三级缓存中的 `ObjectFactory` 的 `#getObject()` 方法的执行结果, 提早曝光的 **单例** `Bean` 对象.
