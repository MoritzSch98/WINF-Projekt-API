package com.example.gewerbeanmeldung.pdffile;

public class PdfStorageException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public PdfStorageException(String message) {
        super(message);
    }

    public PdfStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
