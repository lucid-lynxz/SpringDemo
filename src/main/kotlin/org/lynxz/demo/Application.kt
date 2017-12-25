package org.lynxz.demo

import org.lynxz.demo.storage.StorageProperties
import org.lynxz.demo.storage.StorageService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean


// 重要: 标识本项目为一个spring boot项目
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties::class)
class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // 指定程序的入口
            SpringApplication.run(Application::class.java, *args)
        }
    }

    @Bean
    fun init(storageService: StorageService) = CommandLineRunner {
        storageService.deleteAll()
        storageService.init()
    }
}