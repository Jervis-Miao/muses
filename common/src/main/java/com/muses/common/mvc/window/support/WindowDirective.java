package com.muses.common.mvc.window.support;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import com.muses.common.mvc.window.Window;
import com.muses.common.mvc.window.WindowAsync;

/**
 * @author Jervis
 */
public class WindowDirective extends org.apache.velocity.runtime.directive.Directive {

    @Override
    public String getName() {
        return "window";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node)
            throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String name = getRequiredArgument(context, node, 0, getName());
        String path = getRequiredArgument(context, node, 1, getName());
        Window window = new Window(name, path);
        WindowAsync asyncWork = (WindowAsync) context.get(WindowAsync.WINDOW_ASYNC_ATTRIBUTE);
        asyncWork.invoke(window);
        writer.append(window.getContent());
        return true;
    }

    static String getRequiredArgument(InternalContextAdapter context, Node node, int argumentIndex, String directive)
            throws ParseErrorException {
        SimpleNode sn_value = (SimpleNode) node.jjtGetChild(argumentIndex);
        if (sn_value == null) {
            throw new ParseErrorException(
                    "required argument is null with directive:#" + directive + "(),argumentIndex=" + argumentIndex);
        }

        String value = (String) sn_value.value(context);
        if (value == null) {
            throw new ParseErrorException(
                    "required argument is null with directive:#" + directive + "(),argumentIndex=" + argumentIndex);
        }
        return value;
    }
}
