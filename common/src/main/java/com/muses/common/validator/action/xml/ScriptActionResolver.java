package com.muses.common.validator.action.xml;

import java.util.Arrays;
import java.util.List;

import com.muses.common.validator.config.XmlElement;
import com.muses.common.validator.config.XmlValidatorResolver;
import com.muses.common.validator.data.Action;
import com.muses.common.validator.data.Entity;
import com.muses.common.validator.data.ScriptAction;

public class ScriptActionResolver implements ActionResolver<ScriptAction> {

    @Override
    public boolean supports(XmlElement xmlElement) {
        return XmlValidatorResolver.XML_EL_ACTION_SCRIPT.equals(xmlElement.tagName());
    }

    @Override
    public List<ScriptAction> resolve(XmlElement element, Entity entity, Action action, ActionResolverChain chain) {
        ScriptAction script = new ScriptAction(element.attr(XmlValidatorResolver.XML_ATT_TEST));
        for (XmlElement el : element.elements()) {
            chain.resolve(el, entity, script);
        }
        addAction(action, script);
        return Arrays.asList(script);
    }

    private void addAction(Action action, ScriptAction script) {
        if (action != null) {
            action.addAction(script);
        }
    }
}
