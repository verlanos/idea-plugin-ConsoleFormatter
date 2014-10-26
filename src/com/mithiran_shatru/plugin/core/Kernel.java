package com.mithiran_shatru.plugin.core;

import com.mithiran_shatru.plugin.formatting.FormattingRule;

public enum Kernel {
    INSTANCE;

    private String originalConsoleText;
    private boolean hasOriginal;
    private FormattingRule lastAppliedFormatting;

    public String getOriginal() {
        return originalConsoleText;
    }

    public void memoriseOriginal(String originalConsoleText) {
        this.originalConsoleText = originalConsoleText;
        this.hasOriginal = true;
    }

    public void forgetOriginal() {
        this.originalConsoleText = null;
        this.hasOriginal = false;
    }

    public boolean knowsOriginal() {
        return hasOriginal;
    }

    public FormattingRule getLastAppliedFormatting() {
        return lastAppliedFormatting;
    }

    public void setLastAppliedFormatting(FormattingRule lastAppliedFormatting) {
        this.lastAppliedFormatting = lastAppliedFormatting;
    }
}
