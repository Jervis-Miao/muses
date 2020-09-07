package cn.muses.common.mvc.spring;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

/**
 * <p>
 * 命名规范-约定大于配置(COC)-仅在开发阶段使用校验，用于规范Url与Controller与view之间的对应关系
 * </p>
 * <ul>
 * <li>1、类必须以Controller结尾</li>
 * <li>2、类所在的[包名+类名]与此类标注的[RequestMapping注解路径]必须一致</li>
 * <li>3、类标注的[RequestMapping注解路径]以'/'开头且不以’/’结尾</li>
 * <li>4、方法名与此方法标注的[RequestMapping注解路径]需以此开头</li>
 * <li>5、方法标注的[RequestMapping注解路径]不能以'/'开头或结尾</li>
 * <li>6、视图名称与包+类+方法对应，规则为[包/类名称_方法名称]</li>
 * </ul>
 *
 * @author Jervis
 */
@Profile({ "development", "develop", "dev" })
public class DevCocInterceptor extends HandlerInterceptorAdapter {

    private static final String CONTROLLER_PACKAGE = ".web.controller";
    private static final String CONTROLLER_CLASS = "Controller";
    private static final String PACKAGE_PATH = "/";
    private static final String PACKAGE_DOT = ".";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String servletPath = getServletPath(request);
            String string = checkPath(method, servletPath);
            if (string != null) {
                warnToDeveloper(response, warnInfo1(method, string));
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (supportsView(method)) {
                String string = checkView(method, modelAndView.getViewName());
                if (string != null) {
                    warnToDeveloper(response, warnInfo2(method, string));
                    throw new IllegalArgumentException(string);
                }
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }

    private String checkPath(Method method, String servletPath) {
        String string = null;
        Class<?> clazz = method.getDeclaringClass();
        if (!clazz.getName().endsWith(CONTROLLER_CLASS)) {
            string = String.format(clazz + " - 控制层不是以[%s]结尾！", CONTROLLER_CLASS);
            return string;
        }
        String clazzPath = getClazzPath(clazz);
        RequestMapping clazzReqM = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
        RequestMapping methodReqM = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        String clazzReqMPath = ((clazzReqM == null) || (clazzReqM.value() == null) || (clazzReqM.value().length == 0)
                || (clazzReqM.value()[0].trim().length() == 0)) ? null : clazzReqM.value()[0];
        String methodReqMPath = ((methodReqM == null) || (methodReqM.value() == null)
                || (methodReqM.value().length == 0) || (methodReqM.value()[0].trim().length() == 0)) ? null
                        : methodReqM.value()[0];
        if (!clazz.getName().endsWith(CONTROLLER_CLASS)) {
            string = String.format("类不是以[%s]结尾！", CONTROLLER_CLASS);
        } else if (!servletPath.startsWith(clazzPath)) {
            string = String.format("请求路径[%s]与'类名'[%s]不匹配！", servletPath, clazzPath);
        } else if (clazzReqMPath == null) {
            string = "类声明的RequestMapping注解配置路径为空！";
        } else if (!clazzReqMPath.startsWith(PACKAGE_PATH) || clazzReqMPath.endsWith(PACKAGE_PATH)
                || !clazzReqMPath.equals(clazzReqMPath.toLowerCase())) {
            string = String.format("类声明的RequestMapping注解配置路径[%s]必须全部小写且以'/'开头不以'/'结尾！", clazzReqMPath);
        } else if (!clazzPath.equals(clazzReqMPath)) {
            string = String.format("类声明的RequestMapping注解配置路径[%s]与'类名'[%s]不匹配！", clazzReqMPath, clazzPath);
        } else if (methodReqMPath == null) {
            string = "类声明的RequestMapping注解配置路径为空！";
        } else if (methodReqMPath.startsWith(PACKAGE_PATH) || methodReqMPath.endsWith(PACKAGE_PATH)) {
            string = String.format("方法声明的RequestMapping注解配置路径[%s]必须全部小写且不能以'/'开头或结尾！", methodReqMPath);
        } else if (!method.getName().matches("\\w+") || !Character.isLowerCase(method.getName().charAt(0))) {
            string = String.format("方法名[%s]非法，只能包含-字母(A-Za-z)、数字(0-9)，且首字母小写!", method.getName());
        } else if (!methodReqMPath.startsWith(method.getName().toLowerCase())) {
            string = String.format("方法名[%s]与RequestMapping注解配置路径[%s]不匹配！", method.getName(), methodReqMPath);
        }
        return string;
    }

    private String checkView(Method method, String viewName) {
        String string = null;
        if (StringUtils.isEmpty(viewName)) {
            string = String.format("视图路径[%s]为空！", viewName);
        } else if (viewName.startsWith("redirect:") || viewName.startsWith("forward:")) {
            /* 对redirect或forward不做约束 */
        } else if (viewName.startsWith(PACKAGE_PATH) || viewName.endsWith(PACKAGE_PATH)) {
            string = String.format("视图路径[%s]不能以'/'开头或结尾！", viewName);
        } else if (!viewName.matches("(\\w|/)+")) {
            string = String.format("视图路径[%s]非法，只能包含-字母(A-Za-z)、数字(0-9)、下划线(_)、斜杠(/)！", viewName);
        }
        return string;
    }

    private String warnInfo1(Method method, String string) {
        StringBuilder buffer = new StringBuilder(method.toString());
        buffer.append("<br/>\n类的注解 -> ");
        buffer.append(method.getDeclaringClass().getAnnotation(RequestMapping.class));
        buffer.append("<br/>\n方法注解 -> ");
        buffer.append(method.getAnnotation(RequestMapping.class));
        buffer.append("<br/>\n错误信息 -> ");
        buffer.append(string);
        return buffer.toString();
    }

    private String warnInfo2(Method method, String string) {
        StringBuilder buffer = new StringBuilder("<p>" + method.toString() + "</p>");
        buffer.append("<p><h3>错误信息 -> ");
        buffer.append(string);
        buffer.append("</h3></p>");
        return buffer.toString();
    }

    private void warnToDeveloper(HttpServletResponse response, String string) {
        logger.error(string);
        try {
            response.getWriter().write(string);
        } catch (IOException ioe) {
            logger.error(string, ioe);
        }
    }

    private boolean supportsView(Method method) {
        return (method.getAnnotation(ResponseBody.class) == null) && (method.getReturnType() != ResponseEntity.class);
    }

    private String getClazzPath(Class<?> clazz) {
        String clazzName = clazz.getSimpleName();
        int nameIndex = clazzName.length() - CONTROLLER_CLASS.length();
        return PACKAGE_PATH + clazzName.substring(0, nameIndex).toLowerCase();
    }

    private String getServletPath(HttpServletRequest request) {
        String path = null;
        if (WebUtils.isIncludeRequest(request)) {
            path = (String) request.getAttribute(WebUtils.INCLUDE_SERVLET_PATH_ATTRIBUTE);
        } else {
            path = request.getServletPath();
        }
        return path;
    }
}
