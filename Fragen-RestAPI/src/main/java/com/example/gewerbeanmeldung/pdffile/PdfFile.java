package com.example.gewerbeanmeldung.pdffile;

import org.hibernate.annotations.GenericGenerator;

import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "pdfs")
public class PdfFile {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String fileName;

	private String fileType;

	@Lob
	private byte[] data;

	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="formFilled_id", nullable=false)
	@JsonIgnore
	private FormFilled formFilled;
	
	public PdfFile() {

	}

	public PdfFile(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	

	public FormFilled getFormFilled() {
		return formFilled;
	}

	public void setFormFilled(FormFilled formFilled) {
		this.formFilled = formFilled;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
