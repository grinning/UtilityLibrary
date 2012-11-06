package com.tommytony.io;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class SystemIO implements ClipboardOwner {

	public static void copyToClipboard(String text) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
	}
	
	public static String getClipboardContents() {
		String result = "";
		Transferable string = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		if((string != null) && string.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				result = (String)string.getTransferData(DataFlavor.stringFlavor);
			} catch(UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		//do nothing
	}
}
