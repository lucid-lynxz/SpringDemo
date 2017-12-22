package org.lynxz.demo

import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lynxz.demo.web.HelloController
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders


@RunWith(SpringJUnit4ClassRunner::class)
//@RunWith(SpringRunner::class)
@SpringBootTest
class DemoApplicationTests {
    private lateinit var mvc: MockMvc

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mvc = MockMvcBuilders.standaloneSetup(HelloController()).build()
    }


    @Throws(Exception::class)
    @Test
    fun getHello() {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().string(equalTo("hello world")))
    }

    @Test
    fun contextLoads() {
    }

}
