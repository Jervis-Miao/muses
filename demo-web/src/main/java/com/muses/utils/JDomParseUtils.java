package com.muses.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONObject;
import com.muses.common.utils.SpringContextUtils;
import com.muses.send.TemVelocity;

/**
 * 解析配置邮件模板的xml文件
 *
 * @author yuanchao
 *
 */
@SuppressWarnings("unchecked")
public class JDomParseUtils {
	private static Logger	log	= LoggerFactory.getLogger(JDomParseUtils.class);
	private Document		docMail;
	private Document		docSms;
	// private Resource smsTemplate;
	private Resource		mailTemplate;

	public void initLoadXml() throws JDOMException, IOException {

		// 使用的是默认的解析器
		SAXBuilder builder = new SAXBuilder(false);
		// 得到Document
		try {
			log.info("begin to loading xml !");
			docMail = builder.build(mailTemplate.getInputStream());
			/* docSms = builder.build(smsTemplate.getInputStream()); */
			log.info("loading  xml successful !");
		} catch (JDOMException e) {
			log.error("loading  xml fail !", e);
			throw e;
		} catch (IOException e) {
			log.error("loading  xml fail !", e);
			throw e;
		}
	}

	public Map<String, Object> parseXmlMail(String velocityId) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 得到根元素
			Element velocities = docMail.getRootElement();
			// 得到元素（节点）的集合
			List listVelocity = velocities.getChildren("velocity");

			for (Iterator it = listVelocity.iterator(); it.hasNext();) {
				Element velocity = (Element) it.next();
				// 取得元素velocity的属性名为“id”的属性值。
				String id = velocity.getAttributeValue("id");
				// 判断是否是当前使用的模板
				if (StringUtils.trimToEmpty(id).equals(velocityId)) {
					// 取得当前模板的路径
					String location = velocity.getAttributeValue("location");
					map.put("location", location);
					// 取得邮件的主题
					String subject = velocity.getChildText("subject");
					map.put("subject", subject);
					String senderAddress = velocity.getChildText("senderAddress");
					map.put("senderAddress", senderAddress);
					String senderDispaly = velocity.getChildText("senderDispaly");
					map.put("senderDispaly", senderDispaly);
					String replyTo = velocity.getChildText("replyTo");
					map.put("replyTo", replyTo);
					String title = velocity.getChildText("title");
					map.put("title", title);
					// 取得代表邮件中的内嵌资源的元素
					Element resourceInline = velocity.getChild("resourceInline");
					if (resourceInline != null) {
						// 取得内嵌资源的元素集合
						List resources = resourceInline.getChildren("resource");
						Map<String, String> map1 = new HashMap<String, String>();
						for (Iterator itr = resources.iterator(); itr.hasNext();) {
							Element resource = (Element) itr.next();
							// 取出内嵌资源的key
							String resourceKey = resource.getAttributeValue("key");
							// 取出内嵌资源对应的路径
							String keyPath = resource.getAttributeValue("keyPath");

							map1.put(resourceKey, keyPath);
						}
						map.put("inLineResource", map1);
					}
					// 取得代表邮件中的附件的元素
					Element mailAttaches = velocity.getChild("mailattach");
					if (mailAttaches != null) {
						// 取得附件的元素集合
						List attaches = mailAttaches.getChildren("attach");
						Map<String, String> map2 = new HashMap<String, String>();
						for (Iterator ita = attaches.iterator(); ita.hasNext();) {
							Element attach = (Element) ita.next();
							String fileName = attach.getAttributeValue("fileName");
							String filePath = attach.getAttributeValue("filePath");
							map2.put(fileName, filePath);
						}
						map.put("attach", map2);
					}

				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
	}

	public Map<String, Object> parseSmsXml(String velocityId) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 得到根元素
		Element velocities = docSms.getRootElement();
		// 得到元素（节点）的集合
		List listVelocity = velocities.getChildren("velocity");
		for (Iterator it = listVelocity.iterator(); it.hasNext();) {
			Element velocity = (Element) it.next();
			// 取得元素velocity的属性名为“id”的属性值。
			String id = velocity.getAttributeValue("id");
			// 判断是否是当前使用的模板
			if (StringUtils.trimToEmpty(id).equals(velocityId)) {
				// 取得当前模板的路径
				String location = velocity.getAttributeValue("location");
				map.put("location", location);
				// 取得代表短信发送时间的元素
				Element sendTimeElement = velocity.getChild("sendTime");
				if (sendTimeElement != null) {
					String sendTime = sendTimeElement.getText();
					map.put("sendTime", sendTime);
				}
				// 取得代表短信优先级的元素
				Element smsPriorityElement = velocity.getChild("smsPriority");
				if (smsPriorityElement != null) {
					String priority = smsPriorityElement.getText();
					map.put("priority", priority);
				}
			}
		}

		return map;
	}

	/**
	 * 遍历邮件模板配置文件中所有的配置项
	 * @return 返回json str
	 */
	public String traverseMailTemplate() {
		List<TemVelocity> list = new ArrayList<TemVelocity>();
		// 得到根元素
		Element velocities = docMail.getRootElement();
		// 得到元素（节点）的集合
		List listVelocity = velocities.getChildren("velocity");
		for (Iterator it = listVelocity.iterator(); it.hasNext();) {
			TemVelocity temVelocity = new TemVelocity();
			try {
				Element velocity = (Element) it.next();
				String temId = velocity.getAttributeValue("id");
				String location = velocity.getAttributeValue("location");
				String subject = velocity.getChildText("subject");
				String senderAddress = velocity.getChildText("senderAddress");
				String temName = velocity.getChildText("temName");
				String senderDisplay = velocity.getChildText("senderDispaly");
				String temModule = velocity.getChildText("temModule");
				temVelocity.setSenderAddress(senderAddress);
				temVelocity.setSubject(subject);
				temVelocity.setTemFilePath(location);
				temVelocity.setSenderDisplay(senderDisplay);
				temVelocity.setTemId(temId);
				temVelocity.setTemName(temName);
				temVelocity.setTemModule(temModule);
				temVelocity.setTemFileName(location.substring(location.lastIndexOf("/") + 1));
				temVelocity.setTemType("mail");
				VelocityTemplateUtils velocityUtils = SpringContextUtils.getBean("velocityUtils");
				String htmlContent = velocityUtils.mergeMailTemplet(location, "UTF-8", new HashMap<String, Object>());
				temVelocity.setHtmlContent(htmlContent);
				String txtContent = readVm(location);
				temVelocity.setTxtContent(txtContent);
				list.add(temVelocity);
			} catch (Exception e) {
				log.error("遍历邮件模板时发生错误", e);
				temVelocity.setTxtContent("邮件模板内容存在错误，无法显示");
				temVelocity.setHtmlContent("邮件模板内容存在错误，无法预览");
				list.add(temVelocity);
				continue;
			}
		}
		return JSONObject.toJSONString(list);

	}

	/**
	 * 遍历短信模板配置文件中所有的配置项
	 * @return 返回json str
	 */
	public String traverseSmsTemplate() {
		List<TemVelocity> list = new ArrayList<TemVelocity>();
		// 得到根元素
		Element velocities = docSms.getRootElement();
		// 得到元素（节点）的集合
		List listVelocity = velocities.getChildren("velocity");
		for (Iterator it = listVelocity.iterator(); it.hasNext();) {
			TemVelocity temVelocity = new TemVelocity();
			try {
				Element velocity = (Element) it.next();
				String temId = velocity.getAttributeValue("id");
				String location = velocity.getAttributeValue("location");
				String temName = velocity.getChildText("temName");
				String triggerMode = velocity.getChildText("triMode");
				String temModule = velocity.getChildText("temModule");
				temVelocity.setTemFilePath(location);
				temVelocity.setTemId(temId);
				temVelocity.setTemName(temName);
				temVelocity.setTriggerMode(triggerMode);
				temVelocity.setTemType("sms");
				temVelocity.setTemModule(temModule);
				temVelocity.setTemFileName(location.substring(location.lastIndexOf("/") + 1));
				VelocityTemplateUtils velocityUtils = SpringContextUtils.getBean("velocityUtils");
				String content = velocityUtils.mergeMailTemplet(location, "UTF-8", new HashMap<String, Object>());
				temVelocity.setHtmlContent(content);
				String txtContent = readVm(location);
				temVelocity.setTxtContent(txtContent);
				list.add(temVelocity);
			} catch (Exception e) {
				log.error("遍历短信模板时发生错误", e);
				temVelocity.setHtmlContent("短信模板内容存在错误，无法预览");
				list.add(temVelocity);
				continue;
			}
		}
		return JSONObject.toJSONString(list);

	}

	/**
	 * 根据模板相对路径读取模板的文本内容
	 * @author yuanchao
	 * @return 文本内容
	 */
	private String readVm(String location) {
		FileChannel in = null;
		Charset charset = Charset.forName("UTF-8");
		try {
			File file = ResourceUtils.getFile("classpath:velocity/" + location);
			String url = file.getAbsolutePath();
			in = new FileInputStream(url).getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
			StringBuilder builder = new StringBuilder();
			while (in.read(byteBuffer) != -1) {
				byteBuffer.flip();
				builder.append(charset.decode(byteBuffer));
				byteBuffer.clear();
			}

			/**
			 * 另外一种方式
			 */
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String s;
			StringBuilder stringBuilder = new StringBuilder();
			while ((s = reader.readLine()) != null) {
				stringBuilder.append(s);
			}
			// System.out.println(stringBuilder.toString());
			// System.out.println(builder.toString());
			return builder.toString();

		} catch (Exception e) {
			return "";
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//
	// public Resource getSmsTemplate() {
	// return smsTemplate;
	// }
	//
	// public void setSmsTemplate(Resource smsTemplate) {
	// this.smsTemplate = smsTemplate;
	// }

	public Resource getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(Resource mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

}
