/*
Copyright All rights reserved.
 */

package cn.muses.web.service;

import java.util.Map;

import cn.muses.web.dto.PdfInfo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.muses.common.utils.SpringContextUtils;
import cn.muses.utils.PdfUtils;
import cn.muses.utils.VelocityTemplateUtils;

/**
 * @author Jervis
 * @date 2018/11/8.
 */
@Service
public class PdfManager {
	private static final Log		logger	= LogFactory.getLog(PdfManager.class);

	private static final String		EXT		= "pdf";
	private static final String		VM		= "vm";

	@Autowired
	private VelocityTemplateUtils	velocityTemParse;

	/**
	 * 根据vm模板生成pdf文件
	 * @param Id 业务主键
	 * @param vmTemplectName 模板名称
	 * @param modelData 数据结构
	 * @return 返回pdf下载链接
	 */
	public PdfInfo createPdf(Long id, String vmTemplectName, Map<String, Object> modelData) {
		String fileDownPath = StringUtils.EMPTY;
		PdfInfo pdfInfo = new PdfInfo();
		if (StringUtils.isNotBlank(vmTemplectName) && MapUtils.isNotEmpty(modelData)) {
			try {
				String root = SpringContextUtils.getApplicationContext().getResource("WEB-INF/").getURI().getPath();
				String vmFilePath = root + VM + "/";
				String vmTempName = vmTemplectName + "." + VM;
				String templet = velocityTemParse.mergeDynamicTemplet(vmFilePath, vmTempName, "UTF-8", modelData);
				byte[] tempContent = new PdfUtils().createPdfBytesWithContent(templet);
				String name = vmTemplectName + id;
				pdfInfo.setContent(tempContent);
				pdfInfo.setName(name);
				pdfInfo.setCapacity(tempContent.length);
				pdfInfo.setExt(EXT);
			} catch (Exception e) {
				logger.error("pdf生成异常：" + e);
			}
		}
		return pdfInfo;
	}
}
