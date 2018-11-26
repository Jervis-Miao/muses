/*
Copyright 2018 All rights reserved.
 */

package com.muses.provider.test;

import com.alibaba.fastjson.JSONObject;
import com.muses.api.StudentProvider;
import com.muses.dto.StudentDTO;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/provider-test.xml" })
public class StrudentProviderTest {
	@Autowired
	private StudentProvider	studentProvider;

	@Test
	public void test() {
		StudentDTO student = studentProvider.getStudentById(1L);
		System.out.println(JSONObject.toJSONString(student));
	}
}
