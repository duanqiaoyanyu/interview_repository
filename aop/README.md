
#### 如何让切面 `Aspect` 能够被 Spring 管理. 也就是目标类能够被切面增强
1. 简单的用 `@Aspect` 和 `@Component` 来标记切面 `aspect`, 然后通过扫描的方式进行(**推荐采用这种方式**)
2. `@EnableAspectJAutoProxy` + `@Configuration` 并且在配置类中定义切面 `@Bean`

### 切面定义
```java
/**
 * component 注解要加上, 否则切面不会生效
 */
@Slf4j
@Aspect
@Component
public class KaoYanAspect {

    @Pointcut(value = "execution(* com.cskaoyan.service.BusinessService.open())")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void prepareFood() {
        log.info("[前置通知] 方法执行前...");
    }

    @After("pointcut()")
    public void afterMethod() {
        log.info("[后置通知] 方法执行后...");
    }

    @AfterReturning("pointcut()")
    public void afterReturn() {
        log.info("[返回通知] 方法执行后...");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("[异常通知] 抛出异常后...");
    }

    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("[环绕通知] 目标方法执行前...");
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        log.info("[环绕通知] 目标方法执行后");
        return proceed;
    }
}
```

`@After` 和 `AfterReturning` 的区别.  
`@After` 无论任何时候都会执行, 无论是否会发生异常. 在目标方法执行后, 都会执行 `@After` 对应的逻辑  
`AfterReturning` 只有在目标方法正常返回后才会执行 `AfterReturning` 相应的逻辑
`AfterThrowing` 当目标方法发生异常之后, 会执行 `AfterThrowing` 相应的逻辑

目标方法无异常: `Around 前逻辑` -> `Before` -> `目标方法` -> `AfterReturning` -> `After` -> `Around 后逻辑`  
目标方法出现异常: `Around 前逻辑` -> `Before` -> `目标方法` -> `AfterThrowing` -> `After`  
所以其实无论是 `AfterReturning` 还是 `AfterThrowing` 都是在目标方法执行之后 `After` 逻辑之前执行的.

特例:

```java
/**
 * 这种异常捕获方式不对, 实际上还是会被 异常通知检测到
 */
  @Test
    public void test1() {
        StoreDTO dto = null;
        try {
            dto = businessService.open();
        } catch (Exception e) {
            log.info("捕获直接吃掉");
        }
        log.info("商店开业详情: {}", dto);
    }
```

```java
/**
 * 在对应的目标方法里面进行异常捕获才是正确的做法, 这样异常通知才不会检测到异常
 */
    @Override
    public StoreDTO open() {
        System.out.println("[目标方法] 开始营业啦!");

        StoreDTO dto = new StoreDTO();
        dto.setId(9999L);
        dto.setName("聚散又依依");

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.info("捕获直接吃掉");
        }

        return dto;
    }
```

```java
/**
 * 仅仅只是对环绕通知进行异常捕获, 这样也只是做到了让环绕通知的逻辑可以正常执行, 异常通知一样可以检测到异常
 */
    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("[环绕通知] 目标方法执行前...");
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.info("捕获直接吃掉");
            //throw new RuntimeException(e);
        }
        log.info("[环绕通知] 目标方法执行后");
        return proceed;
    }
```

**ProceedingJoinPoint 只能用在环绕通知中, 其他类型的通知都不行**
