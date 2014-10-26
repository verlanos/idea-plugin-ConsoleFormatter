package com.mithiran_shatru.plugin.action;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.mithiran_shatru.plugin.core.ConsoleWrapper;
import com.mithiran_shatru.plugin.core.Kernel;
import com.mithiran_shatru.plugin.exception.EditorNotFoundException;

public class ClearConsoleFormatting extends AnAction {
    ConsoleWrapper consoleWrapper;

    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getData(PlatformDataKeys.PROJECT);
        final ConsoleView consoleView = e.getData(LangDataKeys.CONSOLE_VIEW);

        try {
            consoleWrapper = new ConsoleWrapper.Builder(consoleView).build();
            if(Kernel.INSTANCE.knowsOriginal()) {
                consoleWrapper.setText(Kernel.INSTANCE.getOriginal());
            }
        } catch (EditorNotFoundException editorNotFound) {
            editorNotFound.printStackTrace();
        }
    }
}
