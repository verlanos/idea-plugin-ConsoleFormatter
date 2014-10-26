package com.mithiran_shatru.plugin.formatting;

public enum FormattingType {
    SIMPLE_FIND_AND_REPLACE("Simple"),
    REGEX_FIND_AND_REPLACE("Regex");

    private String friendlyLabel;

    private FormattingType(String friendlyLabel) {
        this.friendlyLabel = friendlyLabel;
    }

    public String getFriendlyLabel() {
        return friendlyLabel;
    }
}
