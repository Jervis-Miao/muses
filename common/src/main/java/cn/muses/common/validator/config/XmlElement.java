/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.validator.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Xml配置抽象，便于使用不同的解析器解析配置
 *
 * @author Jervis
 * @date 2018/11/29.
 */
public class XmlElement {
	public static final String	XML_TAG_NAME	= "tagName";
	private String				tagName;
	private Map<String, String>	attrs			= new HashMap<String, String>();
	private List<XmlElement>	elements		= new ArrayList<XmlElement>();

	public XmlElement(String tagName, Map<String, String> attrs) {
		this.tagName = tagName;
		this.attrs.putAll(attrs);
	}

	public String attr(String attrName) {
		return attrs.get(attrName);
	}

	public Map<String, String> attrs() {
		return attrs;
	}

	public XmlElement element(String name) {
		List<XmlElement> result = elements(name);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<XmlElement> elements(String name) {
		List<XmlElement> result = new ArrayList<XmlElement>();
		for (XmlElement element : elements) {
			if (element.tagName().equals(name)) {
				result.add(element);
			}
		}
		return result;
	}

	public List<XmlElement> elements() {
		return elements;
	}

	public String tagName() {
		return this.tagName;
	}
}
