package org.lynxz.demo.web

import org.lynxz.demo.bean.Greeting
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong


@RestController
class GreetingController {
    private val template = "Hello, %s!"
    private val counter = AtomicLong()

    /*
    * 返回的对象会自动转换为json数据传给客户端
    * http://localhost:8080/greeting?name=lynxz
    * */
    @RequestMapping(method = arrayOf(RequestMethod.GET), path = arrayOf("/greeting"))
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String)
            = Greeting(counter.incrementAndGet(), String.format(template, name))

}
