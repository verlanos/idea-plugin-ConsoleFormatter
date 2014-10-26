package com.mithiran_shatru.plugin.core;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Queues;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.impl.EditorComponentImpl;
import com.mithiran_shatru.plugin.exception.EditorNotFoundException;
import com.mithiran_shatru.plugin.formatting.StringFormatter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;

public class ConsoleWrapper {
    private Editor consoleEditor;

    private ConsoleWrapper(Editor consoleEditor) {
        this.consoleEditor = consoleEditor;
    }

    public String getText() {
        return consoleEditor.getDocument().getText();
    }

    public void setText(String text) {
        consoleEditor.getDocument().setText(text);
    }

    public void applyFormatting(StringFormatter formatter) {
        if(formatter.hasInput() && formatter.hasRules()) setText(formatter.format());
    }

    public void addDocumentListener(DocumentListener documentListener) {
        consoleEditor.getDocument().addDocumentListener(documentListener);
    }

    public void removeDocumentListener(DocumentListener documentListener) {
        consoleEditor.getDocument().removeDocumentListener(documentListener);
    }

    public static class Builder {
        private ConsoleView consoleView;

        public Builder () {
        }

        public Builder (ConsoleView consoleView) {
            this.consoleView = consoleView;
        }

        public void setConsoleView(ConsoleView consoleView) {
            this.consoleView = consoleView;
        }

        public ConsoleWrapper build() throws EditorNotFoundException {
            if(null != consoleView) {
                List<EditorComponentImpl> consoleEditors = findConsoleEditorComponents(consoleView.getComponent());
                if(consoleEditors.size() > 0) {
                    Editor editor = consoleEditors.get(0).getEditor();
                    return new ConsoleWrapper(editor);
                }
            }

            throw new EditorNotFoundException();
        }

        private List<EditorComponentImpl> findConsoleEditorComponents(JComponent component) {
            JComponent current = component;
            Queue<JComponent> queue = Queues.newLinkedBlockingQueue();
            List<EditorComponentImpl> textComponents = Lists.newArrayList();

            while(current != null) {
                if(current instanceof EditorComponentImpl) {
                    textComponents.add((EditorComponentImpl) current);
                }
                else
                {
                    for(Component comp : current.getComponents()) {
                        if(comp instanceof EditorComponentImpl) {
                            textComponents.add((EditorComponentImpl) comp);
                        }
                        else if(comp instanceof JComponent) {
                            queue.add((JComponent) comp);
                        }
                    }
                }
                current = queue.poll();
            }

            return textComponents;
        }
    }
}
