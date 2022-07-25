全局事务开始肯定是以 `GlobalTransactionalInterceptor` 开始拦截 `@GlobalTransactional` 开始,

然后处理全局事务 `handleGlobalTransaction`



1. 根据方法获取对应的注解, 如果有 @GlobalTransactional 就按照 handleGlobalTransaction方法进行处理；再判断是否有 @GlobalLock注解, 有就按照 handleGlobalLock 进行处理, 否则就执行方法自身的方法，这里其实是对目标方法进行了代理。
2. 先说 `handleGlobalTransaction` 的处理逻辑, 通过事务模板调用 `execute` 方法, 方法需要一个 `TransactionalExecutor` 接口, 然后这里采用的是匿名内部类的方式, 所以需要实现 `TransactionalExecutor#execute 方法` 和 `TransactionalExecutor#getTransactionInfo 方法` 其中  `TransactionalExecutor#execute` 的实现就是用原有被代理的方法, 即 `methodInvocation.proceed()`
3. 我们再来仔细看事务模板是怎么具体执行的. 如果 `GlobalTransaction` 是空, 创建一个新的 `GlobalTransaction` , 如果事务角色是 启动器, 发送开启事务请求到 TC, 然后再执行业务逻辑, 也就是我们定义在执行器 `TransactionalExecutor#execute 方法`,  如果在执行业务逻辑的时候抛出了异常会对其进行捕获, 执行 `completeTransactionAfterThrowing` 然后再把异常原封不动的抛出。如果没有抛出异常的话，就会提交事务。最后在 finally 方法中 还会进行清理方法, 恢复全局锁配置到之前的配置, 然后执行清理。如果事务被暂停, 恢复它。