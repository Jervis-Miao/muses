/*
Copyright All rights reserved.
 */

package cn.muses.web.service;

import java.util.HashMap;
import java.util.Map;

import cn.muses.repository.dao.InsureApplicantInfoDAO;
import cn.muses.utils.DateFormatUtils;
import cn.muses.web.dto.PdfInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jervis
 * @date 2018/11/8.
 */
@Service
public class IndexService {
	private static final Log		logger	= LogFactory.getLog(IndexService.class);

	@Autowired
	private PdfManager				pdfManager;

	@Autowired
	private InsureApplicantInfoDAO applicantInfoDAO;

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

	public void test() {
		applicantInfoDAO.selectByPrimaryKey(1L);
	}
}
