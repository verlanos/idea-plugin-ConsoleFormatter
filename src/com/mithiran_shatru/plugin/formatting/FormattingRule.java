package com.mithiran_shatru.plugin.formatting;

public class FormattingRule {
    private String searchPattern;
    private String replacePattern;
    private FormattingType formattingType;

    public FormattingRule(String searchPattern, String replacePattern, FormattingType formattingType) {
        this.searchPattern = searchPattern;
        this.replacePattern = replacePattern;
        this.formattingType = formattingType;
    }

    public String getSearchPattern() {
        return searchPattern;
    }

    public void setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
    }

    public void updatePatterns(String searchPattern, String replacePattern) {
        setSearchPattern(searchPattern);
        setReplacePattern(replacePattern);
    }

    public String getReplacePattern() {
        return replacePattern;
    }

    public void setReplacePattern(String replacePattern) {
        this.replacePattern = replacePattern;
    }

    public FormattingType getFormattingType() {
        return formattingType;
    }

    public void setFormattingType(FormattingType formattingType) {
        this.formattingType = formattingType;
    }
}

