package com.mithiran_shatru.plugin.action;

import com.beust.jcommander.internal.Lists;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBTabbedPane;
import com.mithiran_shatru.plugin.FormattingFactory;
import com.mithiran_shatru.plugin.core.ConsoleWrapper;
import com.mithiran_shatru.plugin.core.FormattingKernel;
import com.mithiran_shatru.plugin.exception.EditorNotFoundException;
import com.mithiran_shatru.plugin.formatting.FormattingRule;
import com.mithiran_shatru.plugin.formatting.FormattingType;
import com.mithiran_shatru.plugin.formatting.StringFormatter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplyConsoleFormatting extends AnAction {
    private String original;
    private FormattingType strategy = FormattingType.SIMPLE_FIND_AND_REPLACE;
    private FormattingStatus formattingStatus = FormattingStatus.OK;
    private ConsoleWrapper consoleWrapper;

    private JBTabbedPane strategySelectionPane;

    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getData(PlatformDataKeys.PROJECT);
        final ConsoleView consoleView = e.getData(LangDataKeys.CONSOLE_VIEW);

        try {
            consoleWrapper = new ConsoleWrapper.Builder(consoleView).build();
            original = consoleWrapper.getText();
            FormattingKernel.INSTANCE.memoriseOriginal(original);

            JBPopup popup = createPopup("Format current console output",createPopupContent(),null);
            showPopup(popup,project);
        } catch (EditorNotFoundException editorNotFound) {
            editorNotFound.printStackTrace();
        }
    }

    private JBPopup createPopup(@NotNull String title, @NotNull JComponent popupContent,
                                @Nullable JComponent withFocus) {
        ComponentPopupBuilder popupBuilder = JBPopupFactory.getInstance().createComponentPopupBuilder(popupContent,
                withFocus)
                .setCancelOnClickOutside(true)
                .setTitle(title)
                .setMovable(true)
                .setResizable(true)
                .setMayBeParent(true);

        return popupBuilder.createPopup();
    }
    private JComponent createPopupContent() {
        strategySelectionPane = new JBTabbedPane();

        strategySelectionPane.addTab(FormattingType.SIMPLE_FIND_AND_REPLACE.getFriendlyLabel(), createSimpleFormattingTab());
        strategySelectionPane.addTab(FormattingType.REGEX_FIND_AND_REPLACE.getFriendlyLabel(), createAdvancedFormattingTab());

        return strategySelectionPane;
    }

    private JComponent createSimpleFormattingTab() {
        JPanel simpleTab = new JPanel(new FlowLayout());

        Border inputPatternBorder = BorderFactory.createTitledBorder("Find");
        Border replacementPatternBorder = BorderFactory.createTitledBorder("Replace");

        final JTextField simplePatternInput = new JTextField(20);
        simplePatternInput.setBorder(inputPatternBorder);

        final JTextField simplePatternReplace = new JTextField(20);
        simplePatternReplace.setBorder(replacementPatternBorder);

        final JLabel userMessage = new JLabel();
        userMessage.setVisible(true);

        JButton simpleFormatAction = new JButton("Format");
        simpleFormatAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                updateStrategy();
                StringFormatter stringFormatter = FormattingFactory.createSimpleStringFormatter(
                        original,
                        createRulesFromForm(simplePatternInput, simplePatternReplace)
                );
                consoleWrapper.applyFormatting(stringFormatter);
                updateStatus(userMessage);
            }
        });

        simpleTab.add(simplePatternInput);
        simpleTab.add(simplePatternReplace);
        simpleTab.add(simpleFormatAction);
        simpleTab.add(userMessage);

        return simpleTab;
    }

    private JComponent createAdvancedFormattingTab() {
        JPanel advancedTab = new JPanel(new FlowLayout());

        Border inputPatternBorder = BorderFactory.createTitledBorder("Find");
        Border replacementPatternBorder = BorderFactory.createTitledBorder("Replace");

        final JTextField advancedPatternInput = new JTextField(20);
        advancedPatternInput.setBorder(inputPatternBorder);

        final JTextField advancedPatternReplace = new JTextField(20);
        advancedPatternReplace.setBorder(replacementPatternBorder);

        final JLabel userMessage = new JLabel();
        userMessage.setVisible(true);

        JButton advancedFormatAction = new JButton("Format");
        advancedFormatAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                updateStrategy();
                StringFormatter stringFormatter = FormattingFactory.createSimpleStringFormatter(
                        original,
                        createRulesFromForm(advancedPatternInput,advancedPatternReplace)
                );
                consoleWrapper.applyFormatting(stringFormatter);
                updateStatus(userMessage);
            }
        });

        advancedTab.add(advancedPatternInput);
        advancedTab.add(advancedPatternReplace);
        advancedTab.add(advancedFormatAction);
        advancedTab.add(userMessage);

        return advancedTab;
    }

    private void updateStatus(JLabel statusComponent) {
        if(!FormattingStatus.OK.equals(formattingStatus)) {
            statusComponent.setText(formattingStatus.toString());
        }
        else {
            statusComponent.setText("");
        }

    }

    private void updateStrategy() {
        strategy = FormattingType.values()[strategySelectionPane.getSelectedIndex()];
    }

    private boolean validPattern(JTextComponent ... textComponents) {
        for(JTextComponent textComponent : textComponents) {
            if(textComponent.getText().equals("")) return false;
        }
        return true;
    }

    private FormattingRule[] createRulesFromForm(JTextComponent input, JTextComponent replacement) {
        String searchPattern;
        String replacePattern;
        java.util.List<FormattingRule> formattingRuleList = Lists.newArrayList();

        if(validPattern(input,replacement))
        {
            searchPattern = input.getText();
            replacePattern = replacement.getText();
            formattingStatus = FormattingStatus.OK;
            formattingRuleList.add(new FormattingRule(searchPattern,replacePattern,strategy));
        }
        else
        {
            formattingStatus = FormattingStatus.INVALID_INPUT;
        }

        return formattingRuleList.toArray(new FormattingRule[formattingRuleList.size()]);
    }


    private void showPopup(final JBPopup popup, final Project project) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                popup.showCenteredInCurrentWindow(project);
            }
        });
    }

    private enum FormattingStatus {
        OK("Everything works as expected"),
        INVALID_INPUT("Please check the search and replace patterns"),
        INVALID_OUTPUT("Unable to create formatted console text");

        private String message;

        public String getMessage() {
            return message;
        }

        FormattingStatus(String message) {
            this.message = message;
        }


        @Override
        public String toString() {
            return name()+": "+getMessage();
        }
    }
}
