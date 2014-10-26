package com.mithiran_shatru.plugin.application;

import com.intellij.openapi.actionSystem.ActionManager;
import com.mithiran_shatru.plugin.action.ApplyConsoleFormatting;
import com.mithiran_shatru.plugin.action.ClearConsoleFormatting;
import org.jetbrains.annotations.NotNull;

public class ConsoleFormatterRegistration implements PluginRegistrationComponent {
    // Returns the component name
    @NotNull public String getComponentName() {
        return "com.mithiran_shatru.application.ConsoleFormatterRegistration";
    }

    public void initComponent() {
        ActionManager manager = ActionManager.getInstance();
        ApplyConsoleFormatting applyFormatting = new ApplyConsoleFormatting();
        ClearConsoleFormatting clearFormatting = new ClearConsoleFormatting();
    }

    @Override
    public void disposeComponent() {

    }
}
