package org.lynxz.demo.bean

import org.springframework.stereotype.Component

/**
 * 注解 @Component 表明该类会作为组件类,并告知spring要为这个类创建bean
 * 无需显式配置 SgtPeppers bean
 * */
@Component
class SgtPeppers : CompactDisc {
    private val title = "Sgt. Peppers's Lonely Club Band"
    override fun play() {
        println("Playing $title")
    }
}