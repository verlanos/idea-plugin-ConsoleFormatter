package com.mithiran_shatru.plugin;

import com.mithiran_shatru.plugin.formatting.FormattingRule;
import com.mithiran_shatru.plugin.formatting.SimpleStringFormatter;
import com.mithiran_shatru.plugin.formatting.StringFormatter;

public class FormattingFactory {
    public static StringFormatter createSimpleStringFormatter(String input, FormattingRule[] rules) {
        SimpleStringFormatter formatter = new SimpleStringFormatter();
        formatter.setInput(input);
        formatter.setRules(rules);
        return formatter;
    }
}
