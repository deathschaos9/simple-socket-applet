package com.io;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
 
import javax.swing.JTextArea;

/**
 * @author John
 *
 */
@SuppressWarnings("restriction")
public class OutStreamErr extends OutputStream {
    private JTextArea textArea;
     
    public OutStreamErr(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int c) throws IOException {
    	textArea.setForeground(Color.RED);
        textArea.append(String.valueOf((char)c));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}