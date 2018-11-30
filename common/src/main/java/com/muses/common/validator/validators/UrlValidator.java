package com.muses.common.validator.validators;

import java.net.MalformedURLException;
import java.net.URL;

import com.muses.common.validator.ValidContext;
import com.muses.common.validator.config.XmlValidatorResolver;

public class UrlValidator extends AbstractValidator {

    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        if (object == null) {
            return true;
        }
        String xmlUrl = validContext.getValid().attr(XmlValidatorResolver.XML_ATT_URL);
        if (xmlUrl == null) {// 如没有配置则仅校验格式正确
            try {
                new URL(object.toString());
                return true;
            } catch (MalformedURLException e) {
                return false;
            }
        }
        String protocol = null, host = null;
        int port = 0;
        try {
            URL url = new URL(xmlUrl);
            protocol = url.getProtocol();
            host = url.getHost();
            port = url.getPort();
        } catch (MalformedURLException e1) {
            return false;
        }
        String value = object.toString();
        if ((value == null) || (value.length() == 0)) {
            return true;
        }
        URL url;
        try {
            url = new URL(value.toString());
        } catch (MalformedURLException e) {
            return false;
        }

        if ((protocol != null) && (protocol.length() > 0) && !url.getProtocol().equals(protocol)) {
            return false;
        }

        if ((host != null) && (host.length() > 0) && !url.getHost().equals(host)) {
            return false;
        }

        if ((port != -1) && (url.getPort() != port)) {
            return false;
        }

        return true;
    }
}
