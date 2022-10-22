
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

#### 当有多个切面, 可以用 `@Order(value = ?)` 来控制切面的顺序, `value` 是整数, 值越小, 优先级越高. 越先进入切面
> 然后也和 `Filte` 的设计很像, `Filter` 有多个会组成过滤器链, 然后以 `doFilter` 为界限是一个栈的结构. 对应到这里也是一样的, 以 `目标方法 proceed()` 为界限. 目标方法前的方法, 谁的优先级高谁先执行. 目标方法后的方法, 谁的优先级高谁后执行

```java
/**
 * @author duanqiaoyanyu
 * @date 2022/10/12 10:54
 */
@Slf4j
@Order(value = 1)
@Aspect
@Component
public class UniversityAspect {
    
    @Pointcut(value = "execution(* com.cskaoyan.service.impl.ComplexServiceImpl.study())")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("[环绕前逻辑-大学切面]");
        Object proceed = proceedingJoinPoint.proceed();
        log.info("[环绕后逻辑-大学切面]");

        return proceed;
    }

    @Before("pointcut()")
    public void before() {
        log.info("[前置通知-大学切面]");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        log.info("[返回通知-大学切面]");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("[异常通知-大学切面]");
    }

    @After("pointcut()")
    public void after() {
        log.info("[后置通知-大学切面]");
    }
}

```

```java
@Slf4j
@Aspect
@Order(value = 2)
@Component
public class GraduateAspect {

    @Pointcut(value = "execution(* com.cskaoyan.service.impl.ComplexServiceImpl.study())")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("[环绕前逻辑-研究生切面]");
        Object proceed = proceedingJoinPoint.proceed();
        log.info("[环绕后逻辑-研究生切面]");

        return proceed;
    }

    @Before("pointcut()")
    public void before() {
        log.info("[前置通知-研究生切面]");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        log.info("[返回通知-研究生切面]");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("[异常通知-研究生切面]");
    }

    @After("pointcut()")
    public void after() {
        log.info("[后置通知-研究生切面]");
    }
}
```

**当存在多个切面的时候的各个通知的顺序(示范只有2个切面)**
1. 大学切面 `@Order(value = 1)`
2. 研究生切面 `@Order(value = 2)`

**切面是有顺序的, 只有走完一个切面的一部分流程才会到第二个切面, 而不是说按通知顺序, 不能理解为走完所有的 环绕前 才会进到 前置, 整体按照切面顺序来的**
`Around 前逻辑 [大学切面]` -> `Before [大学切面]` -> `Around 前逻辑 [研究生切面]` -> `Before [研究生切面]` -> `目标方法(分界线)` -> `AfterReturning [研究生切面]` -> `After [研究生切面]` -> `Around 后逻辑 [研究生切面]` -> `AfterReturning [大学切面]` -> `After [大学切面]` -> `Around 后逻辑 [大学切面]`
