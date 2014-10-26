package com.mithiran_shatru.plugin.formatting;

public abstract class AbstractStringFormatter implements StringFormatter {
    protected String inputString;
    protected FormattingRule[] formattingRules;

    @Override
    public void setInput(String inputString) {
        this.inputString = inputString;
    }

    @Override
    public void setRules(FormattingRule... formattingRules) {
        this.formattingRules = formattingRules;
    }

    @Override
    public boolean hasInput() {
        return !(null == inputString || inputString.equals(""));
    }

    @Override
    public boolean hasRules() {
        return !(null == formattingRules || formattingRules.length < 1);
    }
}
