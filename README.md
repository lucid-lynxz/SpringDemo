初学spring,跟着官网教程一步步做demo
开发工具:
IDE: Intellij IDEA 2017.1
JAVA: 1.8.0
Kotlin: 1.1.61
系统: macOS 10.13.2

## 参考资料
[Spring guide官方入门教程](https://spring.io/guides)
[Spring Boot快速入门](http://blog.didispace.com/spring-boot-learning-1/);

## 几个常用注解说明
* `SpringApplication.run(Springboot0Application.class, args)`：main方法中的这句话用来指定程序的入口；
* `@SpringBootApplication`：这个是最重要的，标识着这是一个Spring Boot项目，开启自动配置；
* `@RestController`：这个是Spring MVC里面的注解，组合了`@Controller`和`@ResponseBody`；
* `@RequestMapping`：用来映射请求路径/参数、处理类和方法；
* `@Controller`：表明这是一个Spring MVC的Controller，Dispatcher Servlet会自动扫描该类，发现其中的`@RequestMapping`注解并映射；
* `@ResponseBody`：用来支持把返回值放到response体内；

## 快速搭建
1. 到 [官网](https://start.spring.io/) 快速生成一个项目脚手架,我选择的是 `kotlin`;
2. 解压下载的zip包,导入intellij(这里用的是2017.1)中;
4. 修改项目 `pom.xml` 中添加如下语句即可添加web开发中常用的依赖包:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
5. 修改 `Application` 类为:
```java
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DemoApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(DemoApplication::class.java, *args)
        }
    }
}
```

### 异常:
#### 多个 Class JavaLaunchHelper
```shell
Class JavaLaunchHelper is implemented in both /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/bin/java (0x108fa74c0) and /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/libinstrument.dylib (0x10afeb4e0). One of the two will be used. Which one is undefined.
Error: Main method not found in class org.lynxz.demo.DemoApplication, please define the main method as:
   public static void main(String[] args)
or a JavaFX application class must extend javafx.application.Application

Process finished with exit code 1
```
后面一个报错(main函数问题),已经在代码中修改了, 需要改kotlin原代码为 @jvmstatic
前一个报错(JavaLaunchHelper...),在 [这里](https://stackoverflow.com/questions/18794573/class-javalaunchhelper-is-implemented-in-both-libinstrument-dylib-one-of-th) 找到答案: 
> Go to ➤ (Help | Edit Custom Properties...) create the file if it ask you to create it, to disable the error message paste the following to the file you created :<br>
<pre>
idea_rt
idea.no.launcher=true
</pre>
This will take effect on the next restart of the Intellij IDEA.

#### 访问不存在的网址
```shell
Whitelabel Error Page
This application has no explicit mapping for /error, so you are seeing this as a fallback.
```
暂时不知道如何处理

#### jre版本不一致
我使用maven快速生成了一个脚手架项目, 参考 [官网教程](https://spring.io/guides/gs/rest-service/) 的方法( `./mvnw clean package` )想打包生成jar文件的时候,报错如下:
```shell
[ERROR] Failed to execute goal org.jetbrains.kotlin:kotlin-maven-plugin:1.1.61:compile (compile) on project demo: Execution compile of goal org.jetbrains.kotlin:kotlin-maven-plugin:1.1.61:compile failed: Unable to load the mojo 'compile' in the plugin 'org.jetbrains.kotlin:kotlin-maven-plugin:1.1.61' due to an API incompatibility: org.codehaus.plexus.component.repository.exception.ComponentLookupException: org/jetbrains/kotlin/maven/K2JVMCompileMojo : Unsupported major.minor version 52.0
```
排查后发现,项目的 `pom.xml` 规定的jre版本为1.8,而我打包命令行中所使用的java版本是1.7导致的问题;
方案:
修改命令行中的java版本:
```shell
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home
```
重新打包后就会在项目 `target/` 目录下生成项目jar文件了;