package com.mithiran_shatru.plugin.exception;

public class EditorNotFoundException extends Exception {
    public EditorNotFoundException() {
        super("Editor could not be located");
    }
}
