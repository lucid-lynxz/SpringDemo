package org.lynxz.demo.web

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// 表明这是一个Spring MVC的Controller，Dispatcher Servlet会自动扫描该类，发现其中的@RequestMapping注解并映射；
@RestController
class HelloController {

    // 本地运行之后就可以通过 localhost:8080/hello 来访问了
    @RequestMapping("/hello")
    fun index() = "hello world"

    @RequestMapping("/hello/{name}")
    fun index(@PathVariable name: String) = "hello $name"
}