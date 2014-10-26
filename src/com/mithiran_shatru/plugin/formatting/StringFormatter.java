package com.mithiran_shatru.plugin.formatting;

public interface StringFormatter {
    public void setInput(String input);
    public void setRules(FormattingRule... rules);
    public boolean hasInput();
    public boolean hasRules();

    public String format();

}
