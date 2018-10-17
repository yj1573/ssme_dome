package com.yj.demo;

import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;


/**
 * Controller测试支持
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class TestControllerSupport {

    protected MockMvc mvc;
    @Autowired
    protected WebApplicationContext webApplicationConnect;// 注入WebApplicationContext

    @Before
    public void setUp() throws Exception {
        //模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.webApplicationConnect).build()初始化。
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
    }

    //发起post请求
    public MockHttpServletRequestBuilder post(String uri) {
        return MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON);
    }
    //发起get请求
    public MockHttpServletRequestBuilder get(String uri) {
        return MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
    }

    //调用
    @Test
    public void test() throws Exception {
        Map<String, Object> parameter = new HashMap<>();//请求参数
        RequestBuilder request = get("/api/db/createCode")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(parameter));//传参数
        MvcResult mvcResult = mvc.perform(request).andReturn();// 返回执行请求的结果
        int status = mvcResult.getResponse().getStatus();//获取响应状态
        System.out.println("status is: " + status);
        Assert.assertTrue("错误，正确的返回值为200", status == 200);
        System.out.println(mvcResult.getResponse().getContentAsString());
    }







}
