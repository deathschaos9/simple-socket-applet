package com.io;

import java.io.IOException;
import java.io.OutputStream;
 
import javax.swing.JTextArea;

/**
 * @author John
 *
 */
@SuppressWarnings("restriction")
public class ConsoleOutput extends OutputStream {
    private JTextArea textArea;
     
    public ConsoleOutput(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int c) throws IOException {
        textArea.append(String.valueOf((char)c));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}