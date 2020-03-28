package com.example.gewerbeanmeldung.dbfile;

import org.hibernate.annotations.GenericGenerator;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

//This class represents an DatabaseFile entity. It has the needed format, that we can safe files into
//the database directly. Therefore we create a uuid as id, we have a fileName and fileType. The data is 
//represente as bytearray. Also we relate the databaseFile to a answer, it's always an answer to a question.
@Entity
@Table(name = "files")
public class DatabaseFile {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String fileName;

	//It's defined as one of the valid MediaTypes, which are used in Springboot 
	private String fileType;

	//Bytearray for the data of the file
	@Lob
	private byte[] data;

	//Having a many to one relation between many DatabaseFile entities and an Answer Entity. This is because we 
	//want to be able to upload for example two pictures as answer to one question
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="answers_id", nullable=false)
	@JsonIgnore
	private Answers answers;
	
	public DatabaseFile() {

	}

	public DatabaseFile(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	
	public Answers getAnswers() {
		return answers;
	}

	public void setAnswers(Answers answers) {
		this.answers = answers;
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
