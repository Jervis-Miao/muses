/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package example.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utils.CollectionUtils;
import com.utils.PdfUtils;
import com.utils.SpringContextUtils;
import com.utils.VelocityTemplateUtils;

import example.dto.PdfInfo;

/**
 * @author miaoqiang
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
	public PdfInfo createPdf(Long Id, String vmTemplectName, Map<String, Object> modelData) {
		String fileDownPath = StringUtils.EMPTY;
		PdfInfo pdfInfo = new PdfInfo();
		if (StringUtils.isNotBlank(vmTemplectName) && CollectionUtils.isNotEmpty(modelData)) {
			try {
				String root = SpringContextUtils.getApplicationContext().getResource("WEB-INF/").getURI().getPath();
				String vmFilePath = root + VM + "/";
				String vmTempName = vmTemplectName + "." + VM;
				String templet = velocityTemParse.mergeDynamicTemplet(vmFilePath, vmTempName, "UTF-8", modelData);
				byte[] tempContent = new PdfUtils().createPdfBytesWithContent(templet);
				String name = vmTemplectName + Id;
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
