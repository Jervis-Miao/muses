/*
Copyright All rights reserved.
 */

package com.muses.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utils.DownloadUtils;

import com.muses.web.dto.PdfInfo;
import com.muses.web.service.IndexService;

/**
 * @author Jervis
 * @date 2018/6/24.
 */
@Controller
@RequestMapping("/home")
public class IndexController {
	private static final Log	logger	= LogFactory.getLog(IndexController.class);

	@Autowired
	private IndexService		indexService;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * 下载附件
	 *
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void ajaxDownloadApp(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		PdfInfo appPdf = indexService.getAppPdf(id);
		String name = "投保单模板_" + id + ".pdf";
		byte[] content = appPdf.getContent();
		DownloadUtils.down(name, content, request, response);
	}
}
