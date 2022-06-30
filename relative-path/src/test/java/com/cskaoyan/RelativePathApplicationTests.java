package com.cskaoyan;

import cn.hutool.core.io.resource.ClassPathResource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URL;

@SpringBootTest
class RelativePathApplicationTests {

    // 获取相对路径
    // path: /C:/MyWorkspace/MyGit/interview_repository/relative-path/target/test-classes/com/cskaoyan/
    @Test
    public void getRelativePath() {
        String path = RelativePathApplicationTests.class.getResource("").getPath();
        System.out.println(path);
    }

    // path: /C:/MyWorkspace/MyGit/interview_repository/relative-path/target/test-classes/
    @Test
    public void getRelativePath2() {
        String path = RelativePathApplicationTests.class.getResource("/").getPath();
        System.out.println(path);
    }

    // path: /C:/MyWorkspace/MyGit/interview_repository/relative-path/target/test-classes/
    @Test
    public void getRelativePath3() {
        String path = RelativePathApplicationTests.class.getClassLoader().getResource("").getPath();
        System.out.println(path);
    }

    // 会报错：java.lang.IllegalArgumentException: Invalid path: /C:/MyWorkspace/MyGit/interview_repository/relative-path/target/test-classes/
    @Test
    public void getRelativePath4() {
        URL url = getClass().getClassLoader().getResource("/");
        System.out.println(url); // url == null

        String path = RelativePathApplicationTests.class.getClassLoader().getResource("/").getPath();
        System.out.println(path);
    }

    // path: /C:/MyWorkspace/MyGit/interview_repository/relative-path/target/test-classes/
    @Test
    public void getRelativePath5() {
        String path = ClassLoader.getSystemResource("").getPath();
        System.out.println(path);
    }

    // path: /C:/MyWorkspace/MyGit/interview_repository/relative-path/target/test-classes/
    @Test
    public void getRelativePath6() {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(path);
    }

    @Test
    public void getRelativePath7() {
        // 在 web 应用程序中, 得到 web 应用程序的根目录的绝对路径. 这样只需要提供相对于 web 应用程序的路径就可以了.
        // ServletActionContext.getServletContext().getRealPath("/");
    }

    @Test
    public void getClassPath() {
        boolean equal = RelativePathApplicationTests.class == getClass();
        System.out.println(equal);
    }

    @Test
    public void getPath() {
        try {
            String path = ResourceUtils.getURL("classpath:").getPath();
            System.out.println(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getClassPath2() {
        ClassPathResource classPathResource = new ClassPathResource("a.txt");
        System.out.println(classPathResource.getAbsolutePath());
        System.out.println(classPathResource.getPath());
    }

    // 总结
    // 1. 使用工程相对路径是靠不住的, 使用 classPath 路径是可靠的(工程路径就是包括了 src 目录, main目录这样的目录, 可以理解为物理文件存放的目录,
    // 而 classPath 的目录都是文件经过打包编译之后到 target 目录下的目录, 可以理解为编译之后的文件存放的目录)
    // 2. 对于程序要读取的文件, 尽可能放到 classPath 下, 这样就能保证在开发和发布的时候均正常读取

    // 如果资源文件放在 maven 工程的 src/main/resources 资源文件夹下，源码文件放在 src/main/java/ 下，两者编译后都会放到 target/classes 中，
    // 注意：src/main/java/com/xxx 中的文件路径不是 classpath 路径。
    // classPath的路径就是 target/classes 的路径 或者 target/test-classes 的路径。

    // 参考资料:
    // https://segmentfault.com/a/1190000015802324
    // https://developer.aliyun.com/article/101240


    // --------------------------------------------------------------------------------------------------------------------
    // 如果是我自己用的话, 那我就基本上会使用 getClass().getResource("") 获取资源文件的路径了.
    // 也可以用用 hutool 的 ClassPathResource构造方法, 不过这个方法使用的前提是必须确保资源必须存在于 classpath 下.
    // 如果有时候会报错或者 NPE, 那么可以先获取 ClassPath 路径, 然后在进行拼接处理.
    // --------------------------------------------------------------------------------------------------------------------
}
