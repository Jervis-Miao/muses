package cn.muses.common.mvc.window;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.web.util.WebUtils;

class WindowRes extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream content = new ByteArrayOutputStream();

    private final ServletOutputStream outputStream = new ResponseServletOutputStream();

    private PrintWriter writer;

    private int statusCode = HttpServletResponse.SC_OK;

    public WindowRes(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void setStatus(int sc) {
        this.statusCode = sc;
    }

    @Override
    public void setStatus(int sc, String sm) {
        this.statusCode = sc;
    }

    @Override
    public void sendError(int sc) throws IOException {
        this.statusCode = sc;
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        this.statusCode = sc;
    }

    @Override
    public void setContentLength(int len) {
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return this.outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.writer == null) {
            String characterEncoding = getCharacterEncoding();
            this.writer = (characterEncoding != null ? new ResponsePrintWriter(characterEncoding)
                    : new ResponsePrintWriter(WebUtils.DEFAULT_CHARACTER_ENCODING));
        }
        return this.writer;
    }

    @Override
    public void resetBuffer() {
        this.content.reset();
    }

    @Override
    public void reset() {
        // super.reset();
        resetBuffer();
    }

    private int getStatusCode() {
        return statusCode;
    }

    public byte[] toByteArray() {
        return this.content.toByteArray();
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        throw new UnsupportedOperationException("don't call sendRedirect in window request:" + location);
    }

    private class ResponseServletOutputStream extends ServletOutputStream {

        @Override
        public void write(int b) throws IOException {
            content.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            content.write(b, off, len);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }

    private class ResponsePrintWriter extends PrintWriter {

        private ResponsePrintWriter(String characterEncoding) throws UnsupportedEncodingException {
            super(new OutputStreamWriter(content, characterEncoding));
        }

        @Override
        public void write(char buf[], int off, int len) {
            super.write(buf, off, len);
            super.flush();
        }

        @Override
        public void write(String s, int off, int len) {
            super.write(s, off, len);
            super.flush();
        }

        @Override
        public void write(int c) {
            super.write(c);
            super.flush();
        }
    }
}
