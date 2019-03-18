/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.analysis;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.muses.common.utils.ObjectUtils;

/**
 * @author miaoqiang
 * @date 2019/3/18.
 */
public class XmlAnalysis extends AbstractAnalysis<String, Element> {

	@Override
	protected Element getTempWithRes(String xml) {
		try {
			SAXReader saxReader = saxReader = new SAXReader();
			Document document = saxReader.read(new StringReader(xml));
			return document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("XmlAnalysis.getTempWithRes exception", e);
		}
	}

	@Override
	protected String getField(String xml, String key) {
		Element root = super.getTemp();
		Object ret = null;
		String[] keys = key.split("\\.");
		for (String k : keys) {
			if (ObjectUtils.isNotNull(ret) && ret instanceof Element) {
				ret = ((Element) ret).element(k);
			} else {
				ret = root.element(k);
			}
		}
		return ret == null ? "" : String.valueOf(ret);
	}
}
