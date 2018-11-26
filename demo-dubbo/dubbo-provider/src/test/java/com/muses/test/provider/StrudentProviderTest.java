/*
Copyright 2018 All rights reserved.
 */

package com.muses.test.provider;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.muses.api.StudentProvider;
import com.muses.api.dto.StudentDTO;
import com.muses.api.dto.StudentQueryDTO;
import com.muses.api.dto.base.PageListDTO;

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
	public void testSave() {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setName("test");
		studentDTO.setMobile("13111111111");
		System.out.println(studentProvider.save(studentDTO));
	}

	@Test
	public void testGet() {
		StudentDTO student = studentProvider.getById(2L);
		System.out.println(JSONObject.toJSONString(student));
	}

	@Test
	public void testGetPage() {
		StudentQueryDTO studentQueryDTO = new StudentQueryDTO();
		studentQueryDTO.setStudentIds(Arrays.asList(new Long[] { 1L, 2L, 3L }));
		studentQueryDTO.setLimit(1);
		studentQueryDTO.setPageIndex(2);
		PageListDTO<StudentDTO> student = studentProvider.getPageStudents(studentQueryDTO);
		System.out.println(JSONObject.toJSONString(student));
	}
}
