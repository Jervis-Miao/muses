/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package utils.area;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.dom4j.Element;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import utils.area.dto.AreaDto;
import utils.area.dto.CityDto;
import utils.area.dto.CountyDto;
import utils.area.dto.ProvinceDto;
import utils.excel.ExcelUtil;

/**
 * @author MiaoQiang
 * @date 2018/6/24.
 */
public class AreaUtil {
	private static Logger				logger			= LoggerFactory.getLogger(AreaUtil.class);					// 日志记录
	private static List<AreaDto>		areaDtos		= new ArrayList<>();
	private static List<ProvinceDto>	provinceDtos	= new ArrayList<>();

	private static final String			baseUrl			= "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/";

	public static void main(String[] args) {
		try {
			AreaUtil areaUtil = new AreaUtil();
			List<AreaDto> provinces = areaUtil.parsXml(AreaUtil.httpGet(baseUrl + "index.html"), 1);
			for (AreaDto p : provinces) {
				System.out.println("=========================================");
				System.out.println("省");
				System.out.println("name: " + p.getName() + ", code: " + p.getCode());
				ProvinceDto pd = new ProvinceDto();
				BeanUtils.copyProperties(p, pd);
				provinceDtos.add(pd);
				List<AreaDto> citys = areaUtil.parsXml(AreaUtil.httpGet(baseUrl + pd.getLink()), 2);
				for (AreaDto c : citys) {
					System.out.println("市");
					System.out.println("name: " + c.getName() + ", code: " + c.getCode());
					CityDto cd = new CityDto();
					BeanUtils.copyProperties(c, cd);
					pd.getCityDtos().add(cd);
					List<AreaDto> counties = areaUtil.parsXml(AreaUtil.httpGet(baseUrl + c.getLink()), 3);
					System.out.println("区");
					for (AreaDto co : counties) {
						System.out.println("name: " + co.getName() + ", code: " + co.getCode());
						CountyDto cod = new CountyDto();
						BeanUtils.copyProperties(co, cod);
						cd.getCountyDtos().add(cod);
					}
				}
			}
			// 判断文件是否存在
			System.out.println(ExcelUtil.fileExist("E:/area.xls"));
			// 创建文件
			String title[] = { "province", "provinceCode", "city", "cityCode", "county", "countyCode" };
			ExcelUtil.createExcel("E:/area.xls", new String[] { "sheet1" }, title);
			List<Map> list = new ArrayList<>();
			for (ProvinceDto p : provinceDtos) {
				for (CityDto c : p.getCityDtos()) {
					for (CountyDto cd : c.getCountyDtos()) {
						Map<String, String> map = new HashMap<>();
						map.put("province", p.getName());
						map.put("provinceCode", p.getCode());
						map.put("city", c.getName());
						map.put("cityCode", c.getCode());
						map.put("county", cd.getName());
						map.put("countyCode", cd.getCode());
						list.add(map);
					}
				}
			}
			ExcelUtil.writeToExcel("E:/area.xls", "sheet1", list);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析返回信息
	 * @param xml
	 */
	public static List<AreaDto> parsXml(String html, int level) {
		List<AreaDto> areaDtos = new ArrayList<>();
		try {
			HtmlCleaner hc = new HtmlCleaner();
			TagNode tn = hc.clean(html);
			String xpath = "";
			switch (level) {
			case 1:
				xpath = "//a[@href]";
				break;
			case 2:
				xpath = "//a[@href]";
				break;
			case 3:
				xpath = "//a";
				break;
			default:
				xpath = "//a[@href]";
				break;
			}
			Object[] objarr = tn.evaluateXPath(xpath);
			for (Object obj : objarr) {
				TagNode tagNode = (TagNode) obj;
				String name = tagNode.getText().toString();
				String link = tagNode.getAttributeByName("href");
				int start = link.contains("/") ? link.indexOf("/") + 1 : 0;
				int end = link.contains(".") ? link.indexOf(".") : link.length();
				String code = link.substring(start, end);
				while (code.length() < 6) {
					code += "0";
				}
				// 汉字字符串正则
				String reg = "[\\u4e00-\\u9fa5]+";
				if (level == 1 && (name.contains("省") || name.contains("市") || name.contains("区"))) {
					AreaDto areaDto = new AreaDto();
					areaDto.setName(name);
					areaDto.setCode(code);
					areaDto.setLink(link);
					areaDtos.add(areaDto);
				} else if (name.matches(reg)) {
					AreaDto areaDto = new AreaDto();
					areaDto.setName(name);
					areaDto.setCode(code);
					areaDto.setLink(link);
					areaDtos.add(areaDto);
				}
			}
			return areaDtos;
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串截取替换
	 *
	 * @param nativeStr
	 * @param startStr
	 * @param endStr
	 * @param replaceStr
	 */
	public static String subReplaceStr(String nativeStr, String startStr, String endStr, String replaceStr) {
		String result = nativeStr;
		while (result.indexOf(startStr) > 0) {
			int startIndex = result.indexOf(startStr);
			int endIndex = result.indexOf(endStr, startIndex) + 1;
			String subStr = result.substring(startIndex, endIndex);
			result = result.replace(subStr, replaceStr);
		}
		return result;
	}

	/**
	 * 遍历节点
	 * @param node
	 */
	public static void parsNode(Element node) {
		if (node.getName().equals("a")) {
			System.out.println(node.getName());
		}

		// 递归遍历当前节点所有的子节点
		List<Element> listElement = node.elements();// 所有一级子节点的list
		for (Element e : listElement) {// 遍历所有一级子节点
			parsNode(e);// 递归
		}
	}

	/**
	 * 发送get请求
	 * @param url 路径
	 * @return
	 */
	public static String httpGet(String url) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		// 采用绕过验证的方式处理https请求
		SSLContext sslcontext = createIgnoreVerifySSL();
		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
		HttpParams params = new BasicHttpParams();
		// 设置请求超时2秒钟 根据业务调整
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2 * 1000);
		// 设置等待数据超时时间2秒钟 根据业务调整
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 2 * 1000);
		// 该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大
		params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, 500L);
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		// 设置整个连接池最大连接数 根据自己的场景决定
		connManager.setMaxTotal(200);
		// （目前只有一个路由，因此让他等于最大值）
		connManager.setDefaultMaxPerRoute(connManager.getMaxTotal());
		HttpClients.custom().setConnectionManager(connManager);
		// 创建自定义的httpclient对象
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();

		try {
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = EntityUtils.toString(response.getEntity(), "gbk");
				url = URLDecoder.decode(url, "UTF-8");
				return strResult;
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		}
		return "";
	}

	/**
	 * 绕过验证
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

}
