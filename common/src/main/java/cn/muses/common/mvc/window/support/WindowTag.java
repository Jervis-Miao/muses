package cn.muses.common.mvc.window.support;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.muses.common.mvc.window.Window;
import cn.muses.common.mvc.window.WindowAsync;

/**
 *
 * @author Jervis
 *
 */
@SuppressWarnings("serial")
public class WindowTag extends BodyTagSupport {

    private String name;
    private String path;
    private Window window;

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return EVAL_BODY_INCLUDE or EVAL_BODY_BUFFERED or SKIP_BODY
     */
    @Override
    public int doStartTag() throws JspException {
        WindowAsync windowAsync = (WindowAsync) pageContext.getAttribute(WindowAsync.WINDOW_ASYNC_ATTRIBUTE,
                PageContext.REQUEST_SCOPE);
        window = new Window(name, path);
        windowAsync.invoke(window);
        return super.doStartTag();
    }

    /**
     * @return EVAL_PAGE or SKIP_PAGE
     */
    @Override
    public int doEndTag() throws JspException {
        BodyContent b = getBodyContent();
        String c = null;
        if (b != null) {
            c = b.getString();
        } else {
            c = window.getContent();
        }
        try {
            pageContext.getOut().write(c);
        } catch (IOException e) {
            throw new JspException("write window content occer IOException,window name:" + name, e);
        }
        return EVAL_PAGE;
    }

}
