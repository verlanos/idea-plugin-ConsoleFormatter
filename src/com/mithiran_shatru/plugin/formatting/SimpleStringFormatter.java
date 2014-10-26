package com.mithiran_shatru.plugin.formatting;

import java.util.regex.Matcher;

public class SimpleStringFormatter extends AbstractStringFormatter {
    @Override
    public String format() {
        if(null == inputString || null == formattingRules || formattingRules.length < 1) return null;

        String outputString = inputString;

        for(FormattingRule formattingRule : formattingRules) {
            if(formattingRule.getFormattingType().equals(FormattingType.SIMPLE_FIND_AND_REPLACE)) {
                outputString = outputString.replace(formattingRule.getSearchPattern(),
                        formattingRule.getReplacePattern());
            }
            else if(formattingRule.getFormattingType().equals(FormattingType.REGEX_FIND_AND_REPLACE)) {
                outputString = outputString.replace(formattingRule.getSearchPattern(),
                        Matcher.quoteReplacement(formattingRule.getReplacePattern()));
            }
        }

        return outputString;
    }
}
