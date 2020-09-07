package cn.muses.common.validator.config.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.muses.common.external.eaxy.Document;
import cn.muses.common.external.eaxy.Element;
import cn.muses.common.external.eaxy.Xml;
import cn.muses.common.validator.config.AbstractXmlValidatorResolver;
import cn.muses.common.validator.config.XmlElement;

public class EasyXmlValidatorResolver extends AbstractXmlValidatorResolver {

	@Override
	public List<XmlElement> resolveXml0() throws IOException {
		List<XmlElement> result = new ArrayList<XmlElement>();
		// 读取框架默认的配置+应用独立配置信息
		InputStream[] inputs = listValidInputStreams();
		Document[] documents = new Document[] { Xml.readAndClose(inputs[0]), Xml.readAndClose(inputs[1]) };
		for (Document document : documents) {
			result.add(resolveXml(document.getRootElement()));
		}
		return result;
	}

	@Override
	public List<XmlElement> resolveXml1() throws IOException {
		List<XmlElement> result = new ArrayList<XmlElement>();
		for (File file : listEntityFiles()) {
			Document document = Xml.read(file);
			result.add(resolveXml(document.getRootElement()));
		}
		return result;
	}

	private XmlElement resolveXml(Element element) {
		Map<String, String> attrs = element.attrs();
		XmlElement xmlel = new XmlElement(element.tagName(), attrs);
		for (Element el : element.elements()) {
			XmlElement xmlel0 = resolveXml(el);
			if (xmlel0 != null) {
				xmlel.elements().add(xmlel0);
			}
		}
		return xmlel;
	}
}
