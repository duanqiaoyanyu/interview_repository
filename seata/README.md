## Seata 术语

#### TC(Transaction Coordinator) - 事务协调者

维护全局和分支事务的状态, 驱动全局事务的提交或回滚.



#### TM(Transaction Manager) - 事务管理器

定义全局事务的范围: 开始全局事务、 提交或回滚全局事务.



#### RM(Resource Manager) - 资源管理器

管理分支事务处理的资源, 与 TC 交谈以注册分支事务和报告分支事务的状态, 并驱动分支事务提交或回滚。、.



### 整体机制

------

- 一阶段: 业务数据和回滚日志记录在同一个本地事务中提交, 释放本地锁和连接资源
- 二阶段:
  - 提交异步化, 非常快速地完成。
  - 回滚通过一阶段的回滚日志进行反向补偿。



## 写隔离

- 一阶段本地事务提交前, 需要确保先拿到全局锁
- 拿不到 **全局锁**, 不能提交本地事务
- 拿 **全局锁** 的尝试被限制在一定范围内, 超出范围将放弃, 并回滚本地事务, 释放本地锁。



## 读隔离

在数据库本地事务隔离级别 **读已提交 （Read Committed)**  或以上的基础上, Seata (AT 模式) 的默认全局隔离级别是 **读未提交 (Read Uncommitted)**

如果应用在特定场景下, 必需要求全局的 **读已提交**, 目前 Seata 的方式是通过 SELECT FOR UPDATE 语句的代理。



#### 事务分组

```sh
事务分组说明.
1. 事务分组是什么?
事务分组是 seata 的资源逻辑, 类似于服务实例. 在 file.conf 中的 default_tx_group 就是一个事务分组.
2. 通过事务分组如何找到后端集群?
首先程序中配置了事务分组(GlobalTransactionScanner 构造方法的 txServiceGroup 参数), 程序会通过用户配置的配置中心去寻找 service.vgroupMapping. 事务分组配置项, 取得配置项的值就是 TC 集群的名称. 拿到集群名称程序通过一定的前后缀 + 集群名称去构造服务名, 各配置中心的服务名实现不同. 拿到服务名去相应的注册中心去拉去相应服务名的服务列表, 获取后端真是的 TC 服务列表.
3. 为什么这么设计, 不直接取服务名?
这里多了一层获取事务分组到映射集群的配置. 这样设计后, 事务分组可以作为资源的逻辑隔离单位, 当发生故障时可以快速 failover.
```



```
log_status = 1 的是防御性的, 是收到全局回滚请求, 但是不确定某个分支事务的本地事务是否已经执行完成了, 这时事先插入一条 branch_id 相同的数据, 插入的假数据成功了, 本地事务继续执行就会报唯一索引冲突自动回滚。
假如插入不成功说明表里有数据这个本地事务已经执行完成了, 那么去除这条 undo_log 数据做反向回滚操作.
```



```
在store.mode=db，由于seata是通过jdbc的executeBatch来批量插入全局锁的，根据MySQL官网的说明，连接参数中的rewriteBatchedStatements为true时，在执行executeBatch，并且操作类型为insert时，jdbc驱动会把对应的SQL优化成`insert into () values (), ()`的形式来提升批量插入的性能。
根据实际的测试，该参数设置为true后，对应的批量插入性能为原来的10倍多，因此在数据源为MySQL时，建议把该参数设置为true。
```



## 1.API 支持

Seata API 分为两大类: High-Level API 和 Low-Level API:

- **High-Level API**: 用于事务边界定义、控制及事务状态查询.
- **Low-Level API**: 用于控制事务上下文的传播



### 2. High-Level API

#### 2.1 GlobalTransaction

------

全局事务: 包括开启事务、提交、回滚、获取当前状态等方法.

```java

public interface GlobalTransaction {

    /**
     * Begin a new global transaction with default timeout and name.
     *
     * @throws TransactionException Any exception that fails this will be wrapped with TransactionException and thrown
     * out.
     */
    void begin() throws TransactionException;

    /**
     * Begin a new global transaction with given timeout and default name.
     *
     * @param timeout Global transaction timeout in MILLISECONDS
     * @throws TransactionException Any exception that fails this will be wrapped with TransactionException and thrown
     * out.
     */
    void begin(int timeout) throws TransactionException;

    /**
     * Begin a new global transaction with given timeout and given name.
     *
     * @param timeout Given timeout in MILLISECONDS.
     * @param name    Given name.
     * @throws TransactionException Any exception that fails this will be wrapped with TransactionException and thrown
     * out.
     */
    void begin(int timeout, String name) throws TransactionException;

    /**
     * Commit the global transaction.
     *
     * @throws TransactionException Any exception that fails this will be wrapped with TransactionException and thrown
     * out.
     */
    void commit() throws TransactionException;

    /**
     * Rollback the global transaction.
     *
     * @throws TransactionException Any exception that fails this will be wrapped with TransactionException and thrown
     * out.
     */
    void rollback() throws TransactionException;

    /**
     * Suspend the global transaction.
     *
     * @return the SuspendedResourcesHolder which holds the suspend resources
     * @throws TransactionException Any exception that fails this will be wrapped with TransactionException and thrown
     * @see SuspendedResourcesHolder
     */
    SuspendedResourcesHolder suspend() throws TransactionException;

    /**
     * Resume the global transaction.
     *
     * @param suspendedResourcesHolder the suspended resources to resume
     * @throws TransactionException Any exception that fails this will be wrapped with TransactionException and thrown
     * out.
     * @see SuspendedResourcesHolder
     */
    void resume(SuspendedResourcesHolder suspendedResourcesHolder) throws TransactionException;

    /**
     * Ask TC for current status of the corresponding global transaction.
     *
     * @return Status of the corresponding global transaction.
     * @throws TransactionException Any exception that fails this will be wrapped with TransactionException and thrown
     * out.
     * @see GlobalStatus
     */
    GlobalStatus getStatus() throws TransactionException;

    /**
     * Get XID.
     *
     * @return XID. xid
     */
    String getXid();

    /**
     * report the global transaction status.
     *
     * @param globalStatus global status.
     *
     * @throws TransactionException Any exception that fails this will be wrapped with TransactionException and thrown
     * out.
     */
    void globalReport(GlobalStatus globalStatus) throws TransactionException;

    /**
     * local status of the global transaction.
     *
     * @return Status of the corresponding global transaction.
     * @see GlobalStatus
     */
    GlobalStatus getLocalStatus();

    /**
     * get global transaction role.
     *
     * @return global transaction Role.
     * @see GlobalTransactionRole
     */
    GlobalTransactionRole getGlobalTransactionRole();

}
```



#### 2.2 GlobalTransactionContext

GlobalTransaction 实例的获取需要通过 GlobalTransactionContext:

```java
 /**
     * Get GlobalTransaction instance bind on current thread. Create a new on if no existing there.
     *
     * @return new context if no existing there.
     */
    public static GlobalTransaction getCurrentOrCreate() {
        GlobalTransaction tx = getCurrent();
        if (tx == null) {
            return createNew();
        }
        return tx;
    }

    /**
     * Reload GlobalTransaction instance according to the given XID
     *
     * @param xid the xid
     * @return reloaded transaction instance.
     * @throws TransactionException the transaction exception
     */
    public static GlobalTransaction reload(String xid) throws TransactionException {
        return new DefaultGlobalTransaction(xid, GlobalStatus.UnKnown, GlobalTransactionRole.Launcher) {
            @Override
            public void begin(int timeout, String name) throws TransactionException {
                throw new IllegalStateException("Never BEGIN on a RELOADED GlobalTransaction. ");
            }
        };
    }
```



#### 2.3 TransactionalTemplate

事务化模板: 通过上述 GlobalTransaction 和 GlobalTransactionContext API 把一个业务服务的调用包装成带有分布式事务支持的服务.

```java
    /**
     * Execute object.
     *
     * @param business the business
     * @return the object
     * @throws TransactionalExecutor.ExecutionException the execution exception
     */
    public Object execute(TransactionalExecutor business) throws Throwable {
        // 1. Get transactionInfo
        TransactionInfo txInfo = business.getTransactionInfo();
        if (txInfo == null) {
            throw new ShouldNeverHappenException("transactionInfo does not exist");
        }
        // 1.1 Get current transaction, if not null, the tx role is 'GlobalTransactionRole.Participant'.
        GlobalTransaction tx = GlobalTransactionContext.getCurrent();

        // 1.2 Handle the transaction propagation.
        Propagation propagation = txInfo.getPropagation();
        SuspendedResourcesHolder suspendedResourcesHolder = null;
        try {
            switch (propagation) {
                case NOT_SUPPORTED:
                    // If transaction is existing, suspend it.
                    if (existingTransaction(tx)) {
                        suspendedResourcesHolder = tx.suspend();
                    }
                    // Execute without transaction and return.
                    return business.execute();
                case REQUIRES_NEW:
                    // If transaction is existing, suspend it, and then begin new transaction.
                    if (existingTransaction(tx)) {
                        suspendedResourcesHolder = tx.suspend();
                        tx = GlobalTransactionContext.createNew();
                    }
                    // Continue and execute with new transaction
                    break;
                case SUPPORTS:
                    // If transaction is not existing, execute without transaction.
                    if (notExistingTransaction(tx)) {
                        return business.execute();
                    }
                    // Continue and execute with new transaction
                    break;
                case REQUIRED:
                    // If current transaction is existing, execute with current transaction,
                    // else continue and execute with new transaction.
                    break;
                case NEVER:
                    // If transaction is existing, throw exception.
                    if (existingTransaction(tx)) {
                        throw new TransactionException(
                            String.format("Existing transaction found for transaction marked with propagation 'never', xid = %s"
                                    , tx.getXid()));
                    } else {
                        // Execute without transaction and return.
                        return business.execute();
                    }
                case MANDATORY:
                    // If transaction is not existing, throw exception.
                    if (notExistingTransaction(tx)) {
                        throw new TransactionException("No existing transaction found for transaction marked with propagation 'mandatory'");
                    }
                    // Continue and execute with current transaction.
                    break;
                default:
                    throw new TransactionException("Not Supported Propagation:" + propagation);
            }

            // 1.3 If null, create new transaction with role 'GlobalTransactionRole.Launcher'.
            if (tx == null) {
                tx = GlobalTransactionContext.createNew();
            }

            // set current tx config to holder
            GlobalLockConfig previousConfig = replaceGlobalLockConfig(txInfo);

            try {
                // 2. If the tx role is 'GlobalTransactionRole.Launcher', send the request of beginTransaction to TC,
                //    else do nothing. Of course, the hooks will still be triggered.
                beginTransaction(txInfo, tx);

                Object rs;
                try {
                    // Do Your Business
                    rs = business.execute();
                } catch (Throwable ex) {
                    // 3. The needed business exception to rollback.
                    completeTransactionAfterThrowing(txInfo, tx, ex);
                    throw ex;
                }

                // 4. everything is fine, commit.
                commitTransaction(tx);

                return rs;
            } finally {
                //5. clear
                resumeGlobalLockConfig(previousConfig);
                triggerAfterCompletion();
                cleanUp();
            }
        } finally {
            // If the transaction is suspended, resume it.
            if (suspendedResourcesHolder != null) {
                tx.resume(suspendedResourcesHolder);
            }
        }
    }
```

模板方法执行的异常: ExecutionException

```java
    /**
     * The enum Code.
     */
    enum Code {

        /**
         * Begin failure code.
         */
        //
        BeginFailure,

        /**
         * Commit failure code.
         */
        //
        CommitFailure,

        /**
         * Rollback failure code.
         */
        //
        RollbackFailure,

        /**
         * Rollback done code.
         */
        //
        RollbackDone,

        /**
         * Report failure code.
         */
        //
        ReportFailure,

        /**
         * Rollback retrying code.
         */
        //
        RollbackRetrying
    } 

/**
     * The type Execution exception.
     */
    class ExecutionException extends Exception {

        private GlobalTransaction transaction;

        private Code code;

        private Throwable originalException;

        /**
         * Instantiates a new Execution exception.
         *
         * @param transaction the transaction
         * @param cause       the cause
         * @param code        the code
         */
        public ExecutionException(GlobalTransaction transaction, Throwable cause, Code code) {
            this(transaction, cause, code, null);
        }

        /**
         * Instantiates a new Execution exception.
         *
         * @param transaction       the transaction
         * @param code              the code
         * @param originalException the original exception
         */
        public ExecutionException(GlobalTransaction transaction, Code code, Throwable originalException) {
            this(transaction, null, code, originalException);
        }

        /**
         * Instantiates a new Execution exception.
         *
         * @param transaction       the transaction
         * @param cause             the cause
         * @param code              the code
         * @param originalException the original exception
         */
        public ExecutionException(GlobalTransaction transaction, Throwable cause, Code code,
                                  Throwable originalException) {
            this(transaction, null, cause, code, originalException);
        }

        /**
         * Instantiates a new Execution exception.
         *
         * @param transaction       the transaction
         * @param message           the message
         * @param cause             the cause
         * @param code              the code
         * @param originalException the original exception
         */
        public ExecutionException(GlobalTransaction transaction, String message, Throwable cause, Code code,
                                  Throwable originalException) {
            super(message, cause);
            this.transaction = transaction;
            this.code = code;
            this.originalException = originalException;
        }

        /**
         * Gets transaction.
         *
         * @return the transaction
         */
        public GlobalTransaction getTransaction() {
            return transaction;
        }

        /**
         * Sets transaction.
         *
         * @param transaction the transaction
         */
        public void setTransaction(GlobalTransaction transaction) {
            this.transaction = transaction;
        }

        /**
         * Gets code.
         *
         * @return the code
         */
        public Code getCode() {
            return code;
        }

        /**
         * Sets code.
         *
         * @param code the code
         */
        public void setCode(Code code) {
            this.code = code;
        }

        /**
         * Gets original exception.
         *
         * @return the original exception
         */
        public Throwable getOriginalException() {
            return originalException;
        }

        /**
         * Sets original exception.
         *
         * @param originalException the original exception
         */
        public void setOriginalException(Throwable originalException) {
            this.originalException = originalException;
        }
    }
```

外层调用逻辑 try-catch 这个异常, 根据异常编码进行处理

- **BeginFailure**(开启事务失败): getCause() 得到开启事务失败的框架异常, getOriginalException() 为空.
- **CommitFailure**(全局提交失败): getCause() 得到全局提交失败的框架异常, getOriginalException()为空。
- **RollbackFailure**(全局回滚失败): getCause() 得到全局回滚失败的框架异常, getOriginalException() 业务应用的原始异常.
- **RollbackDone** (全局回滚成功): getCause()为空, getOriginalException()业务应用的原始异常.

### 3. Low-Level API

#### 3.1 RootContext

事务的根上下文: 负责在应用运行时, 维护XID.

```java

public class RootContext {

    private RootContext() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RootContext.class);

    /**
     * The constant KEY_XID.
     */
    public static final String KEY_XID = "TX_XID";

    /**
     * The constant KEY_TIMEOUT.
     */
    public static final String KEY_TIMEOUT = "TX_TIMEOUT";

    /**
     * The constant MDC_KEY_XID for logback
     * @since 1.5.0
     */
    public static final String MDC_KEY_XID = "X-TX-XID";

    /**
     * The constant MDC_KEY_BRANCH_ID for logback
     * @since 1.5.0
     */
    public static final String MDC_KEY_BRANCH_ID = "X-TX-BRANCH-ID";

    /**
     * The constant KEY_BRANCH_TYPE
     */
    public static final String KEY_BRANCH_TYPE = "TX_BRANCH_TYPE";

    /**
     * The constant KEY_GLOBAL_LOCK_FLAG, VALUE_GLOBAL_LOCK_FLAG
     */
    public static final String KEY_GLOBAL_LOCK_FLAG = "TX_LOCK";
    public static final Boolean VALUE_GLOBAL_LOCK_FLAG = true;

    private static ContextCore CONTEXT_HOLDER = ContextCoreLoader.load();

    private static BranchType DEFAULT_BRANCH_TYPE;

    public static void setDefaultBranchType(BranchType defaultBranchType) {
        if (defaultBranchType != AT && defaultBranchType != XA) {
            throw new IllegalArgumentException("The default branch type must be " + AT + " or " + XA + "." +
                " the value of the argument is: " + defaultBranchType);
        }
        if (DEFAULT_BRANCH_TYPE != null && DEFAULT_BRANCH_TYPE != defaultBranchType && LOGGER.isWarnEnabled()) {
            LOGGER.warn("The `{}.DEFAULT_BRANCH_TYPE` has been set repeatedly. The value changes from {} to {}",
                RootContext.class.getSimpleName(), DEFAULT_BRANCH_TYPE, defaultBranchType);
        }
        DEFAULT_BRANCH_TYPE = defaultBranchType;
    }

    /**
     * Gets xid.
     *
     * @return the xid
     */
    @Nullable
    public static String getXID() {
        return (String) CONTEXT_HOLDER.get(KEY_XID);
    }

    /**
     * Bind xid.
     *
     * @param xid the xid
     */
    public static void bind(@Nonnull String xid) {
        if (StringUtils.isBlank(xid)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("xid is blank, switch to unbind operation!");
            }
            unbind();
        } else {
            MDC.put(MDC_KEY_XID, xid);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("bind {}", xid);
            }
            CONTEXT_HOLDER.put(KEY_XID, xid);
        }
    }

    public static Integer getTimeout() {
        return (Integer) CONTEXT_HOLDER.get(KEY_TIMEOUT);
    }

    public static void setTimeout(Integer timeout) {
        CONTEXT_HOLDER.put(KEY_TIMEOUT,timeout);
    }

    /**
     * declare local transactions will use global lock check for update/delete/insert/selectForUpdate SQL
     */
    public static void bindGlobalLockFlag() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Local Transaction Global Lock support enabled");
        }

        //just put something not null
        CONTEXT_HOLDER.put(KEY_GLOBAL_LOCK_FLAG, VALUE_GLOBAL_LOCK_FLAG);
    }

    /**
     * Unbind xid.
     *
     * @return the previous xid or null
     */
    @Nullable
    public static String unbind() {
        String xid = (String) CONTEXT_HOLDER.remove(KEY_XID);
        if (xid != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("unbind {} ", xid);
            }
            MDC.remove(MDC_KEY_XID);
        }
        return xid;
    }

    public static void unbindGlobalLockFlag() {
        Boolean lockFlag = (Boolean) CONTEXT_HOLDER.remove(KEY_GLOBAL_LOCK_FLAG);
        if (LOGGER.isDebugEnabled() && lockFlag != null) {
            LOGGER.debug("unbind global lock flag");
        }
    }

    /**
     * In global transaction boolean.
     *
     * @return the boolean
     */
    public static boolean inGlobalTransaction() {
        return CONTEXT_HOLDER.get(KEY_XID) != null;
    }

    /**
     * In tcc branch boolean.
     *
     * @return the boolean
     */
    public static boolean inTccBranch() {
        return BranchType.TCC == getBranchType();
    }

    /**
     * In saga branch boolean.
     *
     * @return the boolean
     */
    public static boolean inSagaBranch() {
        return BranchType.SAGA == getBranchType();
    }

    /**
     * get the branch type
     *
     * @return the branch type String
     */
    @Nullable
    public static BranchType getBranchType() {
        if (inGlobalTransaction()) {
            BranchType branchType = (BranchType) CONTEXT_HOLDER.get(KEY_BRANCH_TYPE);
            if (branchType != null) {
                return branchType;
            }
            //Returns the default branch type.
            return DEFAULT_BRANCH_TYPE != null ? DEFAULT_BRANCH_TYPE : BranchType.AT;
        }
        return null;
    }

    /**
     * bind branch type
     *
     * @param branchType the branch type
     */
    public static void bindBranchType(@Nonnull BranchType branchType) {
        if (branchType == null) {
            throw new IllegalArgumentException("branchType must be not null");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("bind branch type {}", branchType);
        }

        CONTEXT_HOLDER.put(KEY_BRANCH_TYPE, branchType);
    }

    /**
     * unbind branch type
     *
     * @return the previous branch type or null
     */
    @Nullable
    public static BranchType unbindBranchType() {
        BranchType unbindBranchType = (BranchType) CONTEXT_HOLDER.remove(KEY_BRANCH_TYPE);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("unbind branch type {}", unbindBranchType);
        }
        return unbindBranchType;
    }

    /**
     * requires global lock check
     *
     * @return the boolean
     */
    public static boolean requireGlobalLock() {
        return CONTEXT_HOLDER.get(KEY_GLOBAL_LOCK_FLAG) != null;
    }

    /**
     * Assert not in global transaction.
     */
    public static void assertNotInGlobalTransaction() {
        if (inGlobalTransaction()) {
            throw new ShouldNeverHappenException(String.format("expect has not xid, but was:%s",
                CONTEXT_HOLDER.get(KEY_XID)));
        }
    }

    /**
     * entry map
     *
     * @return the key-value map
     */
    public static Map<String, Object> entries() {
        return CONTEXT_HOLDER.entries();
    }
}

```

High-Level API 的实现都是基于 RootContext 中维护的 XID 来做的.

应用的当前运行的操作是否在一个全局事务的上下文中, 就是看 RootContext 中是否有 XID.

RootContext 的默认实现是基于 ThreadLocal的, 即 XID 保存在当前线程上下文中.

Low-Level API 的两个典型的应用场景:

#### 1. 远程调用事务上下文的传播

远程调用前后去当前 XID:

```java
	String xid = RootContext.getXID();
```

远程调用过程把 XID 也传递到服务提供方, 在执行服务提供方的业务逻辑前, 把 XID 绑定到当前应用的运行时:

```java
	RootContext.bind(rpcXid);
```

#### 2. 事务的暂停和恢复

在一个全局事务中, 如果需要某些业务逻辑不在全局事务的管辖范围内, 则在调用前, 把 XID 解绑:

```java
	String unbindXid = RootContext.unbind();
```

待相关业务逻辑执行完成, 再把 XID 绑定回去, 即可实现全局事务的恢复:

```
	RootContext.bind(unbindXid);
```



# 全局事务状态表

以db模式举例，global_table是seata的全局事务表。你可以通过观察global_table表中status字段知悉全局事务处于哪个状态

| 状态                                              | 代码 | 备注                                                         |
| ------------------------------------------------- | ---- | ------------------------------------------------------------ |
| 全局事务开始（Begin）                             | 1    | 此状态可以接受新的分支事务注册                               |
| 全局事务提交中（Committing）                      | 2    | 这个状态会随时改变                                           |
| 全局事务提交重试（CommitRetry）                   | 3    | 在提交异常被解决后尝试重试提交                               |
| 全局事务回滚中（Rollbacking）                     | 4    | 正在重新回滚全局事务                                         |
| 全局事务回滚重试中（RollbackRetrying）            | 5    | 在全局回滚异常被解决后尝试事务重试回滚中                     |
| 全局事务超时回滚中（TimeoutRollbacking）          | 6    | [全局事务超时回滚中](https://seata.io/zh-cn/docs/user/appendix/global-transaction-status.html#TimeoutRollbacking_description) |
| 全局事务超时回滚重试中（TimeoutRollbackRetrying） | 7    | 全局事务超时回滚重试中                                       |
| 异步提交中（AsyncCommitting）                     | 8    | 异步提交中                                                   |
| 二阶段已提交（Committed）                         | 9    | 二阶段已提交，此状态后全局事务状态不会再改变                 |
| 二阶段提交失败（CommitFailed）                    | 10   | 二阶段提交失败                                               |
| 二阶段决议全局回滚（Rollbacked）                  | 11   | 二阶段决议全局回滚                                           |
| 二阶段全局回滚失败（RollbackFailed）              | 12   | 二阶段全局回滚失败                                           |
| 二阶段超时回滚（TimeoutRollbacked）               | 13   | 二阶段超时回滚                                               |
| 二阶段超时回滚失败（TimeoutRollbackFailed）       | 14   | 二阶段超时回滚失败                                           |
| 全局事务结束（Finished）                          | 15   | 全局事务结束                                                 |
| 二阶段提交超时（CommitRetryTimeout）              | 16   | 二阶段提交因超过重试时间限制导致失败                         |
| 二阶段回滚超时（RollbackRetryTimeout）            | 17   | 二阶段回滚因超过重试时间限制导致失败                         |
| 未知状态（UnKnown）                               | 0    | 未知状态                                                     |

# 分支事务状态表

| 状态                                                         | 代码 | 备注                         |
| ------------------------------------------------------------ | ---- | ---------------------------- |
| 分支事务注册（Registered）                                   | 1    | 向TC注册分支事务             |
| 分支事务一阶段完成（PhaseOne_Done）                          | 2    | 分支事务一阶段业务逻辑完成   |
| 分支事务一阶段失败（PhaseOne_Failed）                        | 3    | 分支事务一阶段业务逻辑失败   |
| 分支事务一阶段超时（PhaseOne_Timeout）                       | 4    | 分支事务一阶段处理超时       |
| 分支事务二阶段已提交（PhaseTwo_Committed）                   | 5    | 分支事务二阶段提交           |
| 分支事务二阶段提交失败重试（PhaseTwo_CommitFailed_Retryable） | 6    | 分支事务二阶段提交失败重试   |
| 分支事务二阶段提交失败不重试（PhaseTwo_CommitFailed_Unretryable） | 7    | 分支事务二阶段提交失败不重试 |
| 分支事务二阶段已回滚（PhaseTwo_Rollbacked）                  | 8    | 分支事务二阶段已回滚         |
| 分支事务二阶段回滚失败重试（PhaseTwo_RollbackFailed_Retryable） | 9    | 分支事务二阶段回滚失败重试   |
| 分支事务二阶段回滚失败不重试（PhaseTwo_RollbackFailed_Unretryable） | 10   | 二阶段提交失败               |
| 未知状态（UnKnown）                                          | 0    | 未知状态                     |

为帮助理解，下面对个别状态进行补充说明：

### 全局事务超时回滚中（TimeoutRollbacking）

怎么发生的？

1. 当某个seata全局事务执行过程中，无法完成业务。
2. TC中的一个定时任务（专门用来寻找已超时的全局事务），发现该全局事务未回滚完成，就会将此全局事务改为**全局事务超时回滚中（TimeoutRollbacking）**，开始回滚，直到回滚完毕后删除global_table数据。

建议：当你发现全局事务处于该状态，请排查为何业务无法在限定时间内完成事务。若确实无法完成，应调大全局事务超时时间。（如排查一切正常，请检查tc集群时区与数据库是否一致，若不一致请改为一致）。



## Seata 事务隔离

`StatementProxy#executeXXX()` **的处理逻辑**

- ExecuteTemplate.execute(...) 方法中, Seata 根据不同的 dbType 和 sql 语句类型使用不同的 Executer, 调用 `io.seata.rm.datasource.exec.BaseTransactionalExecutor` 类的 `protected abstract T doExecute(Object... args) throws Throwable` .
- 如果选了 DML 类型 Executer, 主要做了以下事情:
  - 查询前镜像(select for update, 因此此时获得本地锁)
  - 执行业务sql
  - 查询后镜像
  - 准备 undo_log

- 如果你的 sql 是 select for update 则会使用 `SelectForUpdateExecutor` (Seata 代理了 select for update), 代理后处理的逻辑是这样的:
  - 先执行 select for update(获取数据库本地锁)
  - 如果处于 `@GlobalTranscational` or `@GlobalLock`, **检查**是否有全局锁
  - 如果有全局锁, 则未开启本地事务下会 rollback 本地事务, 再重新争抢本地锁和全局锁, 以此类推, 除非拿到全局锁



`ConnectionProxy#doCommit()` **的处理逻辑**

- 处于全局事务中(即, 数据持久化方法带有 `@GlobalTransactional`)
  - 注册分支事务, 获取全局锁
  - undo_log 数据入库
  - 让数据库 commit 本次事务

- 处于 `GlobalLock` 中(即, 数据持久化方法带有 `@GlobalLock`)

  - 向 TC 查询是否有全局锁存在, 如存在, 则抛出异常
  - 让数据库 commit 本次事务

  - 初了以上情况(`else` 分支)
    - 让数据库 commit 本次事务

#### @GlobalLock + select for update 的作用

如果 `method` 方法带有 `@GlobalLock + select for update`, Seata 在处理时, 会先获取数据库本地锁, 然后查询该记录是否有全局锁存在, 若有, 则抛出 LockConflictException.

#### 怎么用 Seata 防止脏写?

##### 方法一: `updateA()` 也加上 `@GlobalTransactional`

##### 方法二: @GlobalLock + select for update

- 一定会有人问, "这里为什么要加上 select for update? 只用@GlobalLock 能不能防止脏写?" 能. 但 select for update 能带来这么几个好处
  - 锁冲突更 "温柔" 些. 如果只有 @GlobalLock, 检查到全局锁, 则立刻抛出异常, 也许再 "坚持" 那么一下, 全局锁就释放了, 抛出异常岂不可惜了.
  - 在 `updateA()` 中可以通过 select for update 获取最新的 A, 接着再做更新.

​	 

#### **`GlobalTransactionalInterceptor`处理带有`@GlobalTransactional`或`@GlobalLock`的方法**

会针对被两个注解的方法进行处理

```java
    Object handleGlobalTransaction(final MethodInvocation methodInvocation,
        final AspectTransactional aspectTransactional) throws Throwable {
        boolean succeed = true;
        try {
            return transactionalTemplate.execute(new TransactionalExecutor() {
                @Override
                public Object execute() throws Throwable {
                    return methodInvocation.proceed();
                }

                public String name() {
                    String name = aspectTransactional.getName();
                    if (!StringUtils.isNullOrEmpty(name)) {
                        return name;
                    }
                    return formatMethod(methodInvocation.getMethod());
                }

                @Override
                public TransactionInfo getTransactionInfo() {
                    // reset the value of timeout
                    int timeout = aspectTransactional.getTimeoutMills();
                    if (timeout <= 0 || timeout == DEFAULT_GLOBAL_TRANSACTION_TIMEOUT) {
                        timeout = defaultGlobalTransactionTimeout;
                    }

                    TransactionInfo transactionInfo = new TransactionInfo();
                    transactionInfo.setTimeOut(timeout);
                    transactionInfo.setName(name());
                    transactionInfo.setPropagation(aspectTransactional.getPropagation());
                    transactionInfo.setLockRetryInterval(aspectTransactional.getLockRetryInterval());
                    transactionInfo.setLockRetryTimes(aspectTransactional.getLockRetryTimes());
                    Set<RollbackRule> rollbackRules = new LinkedHashSet<>();
                    for (Class<?> rbRule : aspectTransactional.getRollbackFor()) {
                        rollbackRules.add(new RollbackRule(rbRule));
                    }
                    for (String rbRule : aspectTransactional.getRollbackForClassName()) {
                        rollbackRules.add(new RollbackRule(rbRule));
                    }
                    for (Class<?> rbRule : aspectTransactional.getNoRollbackFor()) {
                        rollbackRules.add(new NoRollbackRule(rbRule));
                    }
                    for (String rbRule : aspectTransactional.getNoRollbackForClassName()) {
                        rollbackRules.add(new NoRollbackRule(rbRule));
                    }
                    transactionInfo.setRollbackRules(rollbackRules);
                    return transactionInfo;
                }
            });
        } catch (TransactionalExecutor.ExecutionException e) {
            TransactionalExecutor.Code code = e.getCode();
            switch (code) {
                case RollbackDone:
                    throw e.getOriginalException();
                case BeginFailure:
                    succeed = false;
                    failureHandler.onBeginFailure(e.getTransaction(), e.getCause());
                    throw e.getCause();
                case CommitFailure:
                    succeed = false;
                    failureHandler.onCommitFailure(e.getTransaction(), e.getCause());
                    throw e.getCause();
                case RollbackFailure:
                    failureHandler.onRollbackFailure(e.getTransaction(), e.getOriginalException());
                    throw e.getOriginalException();
                case RollbackRetrying:
                    failureHandler.onRollbackRetrying(e.getTransaction(), e.getOriginalException());
                    throw e.getOriginalException();
                default:
                    throw new ShouldNeverHappenException(String.format("Unknown TransactionalExecutor.Code: %s", code));
            }
        } finally {
            if (degradeCheck) {
                EVENT_BUS.post(new DegradeCheckEvent(succeed));
            }
        }
    }

    /**
     * Execute object.
     *
     * @param business the business
     * @return the object
     * @throws TransactionalExecutor.ExecutionException the execution exception
     */
    public Object execute(TransactionalExecutor business) throws Throwable {
        // 1. Get transactionInfo
        TransactionInfo txInfo = business.getTransactionInfo();
        if (txInfo == null) {
            throw new ShouldNeverHappenException("transactionInfo does not exist");
        }
        // 1.1 Get current transaction, if not null, the tx role is 'GlobalTransactionRole.Participant'.
        GlobalTransaction tx = GlobalTransactionContext.getCurrent();

        // 1.2 Handle the transaction propagation.
        Propagation propagation = txInfo.getPropagation();
        SuspendedResourcesHolder suspendedResourcesHolder = null;
        try {
            switch (propagation) {
                case NOT_SUPPORTED:
                    // If transaction is existing, suspend it.
                    if (existingTransaction(tx)) {
                        suspendedResourcesHolder = tx.suspend();
                    }
                    // Execute without transaction and return.
                    return business.execute();
                case REQUIRES_NEW:
                    // If transaction is existing, suspend it, and then begin new transaction.
                    if (existingTransaction(tx)) {
                        suspendedResourcesHolder = tx.suspend();
                        tx = GlobalTransactionContext.createNew();
                    }
                    // Continue and execute with new transaction
                    break;
                case SUPPORTS:
                    // If transaction is not existing, execute without transaction.
                    if (notExistingTransaction(tx)) {
                        return business.execute();
                    }
                    // Continue and execute with new transaction
                    break;
                case REQUIRED:
                    // If current transaction is existing, execute with current transaction,
                    // else continue and execute with new transaction.
                    break;
                case NEVER:
                    // If transaction is existing, throw exception.
                    if (existingTransaction(tx)) {
                        throw new TransactionException(
                            String.format("Existing transaction found for transaction marked with propagation 'never', xid = %s"
                                    , tx.getXid()));
                    } else {
                        // Execute without transaction and return.
                        return business.execute();
                    }
                case MANDATORY:
                    // If transaction is not existing, throw exception.
                    if (notExistingTransaction(tx)) {
                        throw new TransactionException("No existing transaction found for transaction marked with propagation 'mandatory'");
                    }
                    // Continue and execute with current transaction.
                    break;
                default:
                    throw new TransactionException("Not Supported Propagation:" + propagation);
            }

            // 1.3 If null, create new transaction with role 'GlobalTransactionRole.Launcher'.
            if (tx == null) {
                tx = GlobalTransactionContext.createNew();
            }

            // set current tx config to holder
            GlobalLockConfig previousConfig = replaceGlobalLockConfig(txInfo);

            try {
                // 2. If the tx role is 'GlobalTransactionRole.Launcher', send the request of beginTransaction to TC,
                //    else do nothing. Of course, the hooks will still be triggered.
                beginTransaction(txInfo, tx);

                Object rs;
                try {
                    // Do Your Business
                    rs = business.execute();
                } catch (Throwable ex) {
                    // 3. The needed business exception to rollback.
                    completeTransactionAfterThrowing(txInfo, tx, ex);
                    throw ex;
                }

                // 4. everything is fine, commit.
                commitTransaction(tx);

                return rs;
            } finally {
                //5. clear
                resumeGlobalLockConfig(previousConfig);
                triggerAfterCompletion();
                cleanUp();
            }
        } finally {
            // If the transaction is suspended, resume it.
            if (suspendedResourcesHolder != null) {
                tx.resume(suspendedResourcesHolder);
            }
        }
    }

    @Override
    public void begin(int timeout, String name) throws TransactionException {
        if (role != GlobalTransactionRole.Launcher) {
            assertXIDNotNull();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Ignore Begin(): just involved in global transaction [{}]", xid);
            }
            return;
        }
        assertXIDNull();
        String currentXid = RootContext.getXID();
        if (currentXid != null) {
            throw new IllegalStateException("Global transaction already exists," +
                " can't begin a new global transaction, currentXid = " + currentXid);
        }
        xid = transactionManager.begin(null, null, name, timeout);
        status = GlobalStatus.Begin;
        RootContext.bind(xid);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Begin new global transaction [{}]", xid);
        }
    }

```

在开启事务的时候进行了 `RootContext.bind(xid);`



```java
    private Object handleGlobalLock(final MethodInvocation methodInvocation, final GlobalLock globalLockAnno) throws Throwable {
        return globalLockTemplate.execute(new GlobalLockExecutor() {
            @Override
            public Object execute() throws Throwable {
                return methodInvocation.proceed();
            }

            @Override
            public GlobalLockConfig getGlobalLockConfig() {
                GlobalLockConfig config = new GlobalLockConfig();
                config.setLockRetryInterval(globalLockAnno.lockRetryInterval());
                config.setLockRetryTimes(globalLockAnno.lockRetryTimes());
                return config;
            }
        });
    }

    public Object execute(GlobalLockExecutor executor) throws Throwable {
        boolean alreadyInGlobalLock = RootContext.requireGlobalLock();
        if (!alreadyInGlobalLock) {
            RootContext.bindGlobalLockFlag();
        }

        // set my config to config holder so that it can be access in further execution
        // for example, LockRetryController can access it with config holder
        GlobalLockConfig myConfig = executor.getGlobalLockConfig();
        GlobalLockConfig previousConfig = GlobalLockConfigHolder.setAndReturnPrevious(myConfig);

        try {
            return executor.execute();
        } finally {
            // only unbind when this is the root caller.
            // otherwise, the outer caller would lose global lock flag
            if (!alreadyInGlobalLock) {
                RootContext.unbindGlobalLockFlag();
            }

            // if previous config is not null, we need to set it back
            // so that the outer logic can still use their config
            if (previousConfig != null) {
                GlobalLockConfigHolder.setAndReturnPrevious(previousConfig);
            } else {
                GlobalLockConfigHolder.remove();
            }
        }
    }
```

在方法执行的一开始就进行了 `RootContext.bindGlobalLockFlag();`



```java
// BaseTransactionalExecutor
    @Override
    public T execute(Object... args) throws Throwable {
        String xid = RootContext.getXID();
        if (xid != null) {
            statementProxy.getConnectionProxy().bind(xid);
        }

        statementProxy.getConnectionProxy().setGlobalLockRequire(RootContext.requireGlobalLock());
        return doExecute(args);
    }
```

`BaseTransactionalExecutor#execute` 里的 xid, requireGlobalLock 就是来自上面方法的初始化中。

然后再被赋值到了 statementProxy里的 ConnectionProxy 中的 ConnectionContext 里去了。

> 注意 ConnectionContext  和 RootContext可是两个东西哦，里面的 xid, isGlobalLockRequire也是两个东西。要搞清楚他的来源
