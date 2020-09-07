package cn.muses.authc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.muses.common.mvc.web.api.BaseResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.RedirectView;

import com.alibaba.fastjson.JSONObject;

/**
 * 表单验证，重写Token创建
 * @version 1.0.0 <br/>
 *          创建时间：2014年1月16日 下午6:22:17
 */
public class MusesFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

		JSONObject user = readJSONString(request);

		String username = user.getString("username");
		String password = user.getString("password");
		String msg = null;

		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new MusesUsernamePasswordToken(username, password, rememberMe, host, msg);
	}

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		String className = ae.getClass().getName();
		String errorMsg = ae.getMessage();
		if (StringUtils.isBlank(className) || className.contains("AuthenticationException")) {
			// 修改默认提示
			errorMsg = "认证失败，请稍候再试";
		}
		request.setAttribute(getFailureKeyAttribute(), errorMsg);
	}

	/**
	 * 重定向，不论3721，不加JSESSIONID
	 * @see org.apache.shiro.web.filter.AccessControlFilter#redirectToLogin(ServletRequest, ServletResponse)
	 */
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest rqt = (HttpServletRequest) request;
		HttpServletResponse rsp = (HttpServletResponse) response;
		// if (!rqt.isRequestedSessionIdFromCookie()
		// && (rqt.getRequestURI().equals("/") || null == rqt.getHeader(HttpHeaders.REFERER) || rqt
		// .getRequestURL().toString().equals(rqt.getHeader(HttpHeaders.REFERER)))) {
		RedirectView view = new MicRedirectView(getLoginUrl(), true);
		view.renderMergedOutputModel(null, rqt, rsp);
		// DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		// DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
		// HttpSession session = rqt.getSession();
		// String idString = session.getId();
		// Cookie template = sessionManager.getSessionIdCookie();
		// Cookie cookie = new SimpleCookie(template);
		// cookie.setValue(idString);
		// cookie.saveTo(rqt, rsp);
		// } else {
		// super.redirectToLogin(request, response);
		// }
	}

	public static class MicRedirectView extends RedirectView {

		public MicRedirectView(String url, boolean contextRelative) {
			super(url, contextRelative);
		}

		@Override
		protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String targetUrl,
				boolean http10Compatible) throws IOException {
			BaseResponseDTO<Void> dto = new BaseResponseDTO<Void>();
			dto.setRet(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.AUTHEN_FAIL.value());
			dto.addError(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.AUTHEN_FAIL.desc());
			response.setContentType("text/html;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				Writer writer = response.getWriter();
				writer.write(JSONObject.toJSONString(dto));
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return super.isAccessAllowed(request, response, mappedValue);
	}

	/**
	 * 获取json数据
	 * @param request
	 * @return
	 */
	public JSONObject readJSONString(ServletRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		JSONObject jsonObject = JSONObject.parseObject(stringBuilder.toString());
		return jsonObject;
	}
}
