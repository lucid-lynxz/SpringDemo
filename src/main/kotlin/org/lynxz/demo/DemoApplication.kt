package org.lynxz.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

// 重要: 标识本项目为一个spring boot项目
@SpringBootApplication
class DemoApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // 指定程序的入口
            SpringApplication.run(DemoApplication::class.java, *args)
        }
    }
}