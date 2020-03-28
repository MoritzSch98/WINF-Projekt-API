package com.example.gewerbeanmeldung.dbfile;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.Answers.AnswersService;
import com.example.gewerbeanmeldung.Question.QuestionService;

//Service class for the logic behind the database file entity. Every logic belonging to the dbfile entity.
@Service
public class DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;
    @Autowired
    private AnswersService answersService;
    
    //Method to store a single file. A file json always comes as multipartfile entity. We need the
    //form_id an questionID to uniquely identify the file afterwards. through form_id (formfilled_id) 
    //and question_id we can get the belonging answer, without needing the answer_id in the request. 
    //this is important to make both request at ones and let the spring app choose the correct order.
    //frontend dev has no explicit order to take care of
    public DatabaseFile storeFile(MultipartFile file, Integer form_id, Integer question_id) {
    	// Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println(fileName);
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            //Creating the DatabaseFile entity. 
            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());
            //generating the answer
            Answers a = answersService.findAnswerByQuestionIdAndFilledFormId(question_id, form_id);
            //make a check, if it is really a fileanswer question through our isFileQuestion() method
            if(isFileQuestion(a, question_id)) {
            	//if so, we set the answers to it
            	dbFile.setAnswers(a);
                return dbFileRepository.save(dbFile);
            }else {
            	System.out.println("The question you try to add an answe to, is not allowed for file uploads");
            	return null;
            }
            
            
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    //Getting  file through fileId(uuid) from the database
    public DatabaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
   
    //updating a file in the database, by actually deleting old one and store new one, cause
    //we had conflicts by trying changing the file with same uuid
    public String updateFile(String fileId, MultipartFile file, Integer form_id, Integer question_id) {
    	deleteFile(fileId);
    	storeFile(file, form_id, question_id);
    	return "saved";
    }
    //deleting a file from the database with fileId
    public String deleteFile(String fileId) {
    	dbFileRepository.deleteById(fileId);
    	return "Delete Successfull";
    }
    //delete a file from a specific answer, for example if the answer is deleted, we want to delete the file
    //belonging to it, to save database space.
    public String deleteFileByAnswerId(Answers answer) {
    	DatabaseFile dbfile = dbFileRepository.getByAnswer(answer);
    	dbFileRepository.deleteById(dbfile.getId());
    	return "success in deleting";
    }
    
    //--------------Helpers ------------------------------
    
    //the method for checking, which answertype the question has and if it is needed to upload and 
    //safe a file to this question.
    public boolean isFileQuestion(Answers a, Integer question_id) {
    	 a = answersService.findAnswerType(a, question_id);
    	 if(!(a.getAnswerType().equals("fileanswer"))) {
    		return false;
    	 }
    	 return true;
    }
}
