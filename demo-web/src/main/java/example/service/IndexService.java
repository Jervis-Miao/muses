/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package example.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utils.DateFormatUtils;

import example.dto.PdfInfo;

/**
 * @author miaoqiang
 * @date 2018/11/8.
 */
@Service
public class IndexService {
	private static final Log	logger	= LogFactory.getLog(IndexService.class);

	@Autowired
	private PdfManager			pdfManager;

	/**
	 * 获取PDF内容
	 *
	 * @param id
	 * @return
	 */
	public PdfInfo getAppPdf(Long id) {
		Map<String, Object> paraMap = new HashMap<>();
		String velocityName = StringUtils.EMPTY;
		paraMap.put("ZH_DATE_FORMAT", DateFormatUtils.ZH_DATE_FORMAT);
		velocityName = "base_new";

		return pdfManager.createPdf(id, velocityName, paraMap);
	}
}
