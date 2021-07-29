/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.analysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author miaoqiang
 * @date 2019/3/18.
 */
public class HtmlAnalysis extends AbstractStrAnalysis<String, Document> {

	@Override
	protected Document getTempWithRes(String html) {
		return Jsoup.parse(html);
	}

	@Override
	protected String getField(String html, String key) {
		Document document = super.getTemp();
		return document.getElementById(key).attr("");
	}
}