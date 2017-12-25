package org.lynxz.demo.bean

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Created by lynxz on 25/12/2017.
 * 通过java代码定义spring的装配原则
 * 注解 @ComponentScan 告知spring启用组件扫描
 * 默认会扫描与配置类相同的包(及其之包)，查找带有@Component注解的类,并且会在Spring中自动为 其创建一个bean
 *
 * 注意: 测试了下,如果跟对应的Component不在同一个包中,则无法创建成功
 */
@Configuration
@ComponentScan
class CDPlayerConfig