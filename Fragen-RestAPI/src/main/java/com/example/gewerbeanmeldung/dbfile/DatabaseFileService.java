package com.example.gewerbeanmeldung.dbfile;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.Answers.AnswersService;
import com.example.gewerbeanmeldung.Question.QuestionService;


@Service
public class DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;
    @Autowired
    private AnswersService answersService;
    @Autowired
    private QuestionService qService;
    

    public DatabaseFile storeFile(MultipartFile file, Integer form_id, Integer question_id) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());
            Answers a = answersService.findAnswerByQuestionIdAndFilledFormId(question_id, form_id);
            if(isFileQuestion(a, question_id)) {
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

    public DatabaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
   
    
    public String updateFile(String fileId, MultipartFile file, Integer form_id, Integer question_id) {
    	deleteFile(fileId);
    	storeFile(file, form_id, question_id);
    	return "saved";
    }
    
    public String deleteFile(String fileId) {
    	dbFileRepository.deleteById(fileId);
    	return "Delete Successfull";
    }
    
    //--------------Helpers ------------------------------
    public boolean isFileQuestion(Answers a, Integer question_id) {
    	 a = answersService.findAnswerType(a, question_id);
    	 if(!(a.getAnswerType().equals("fileanswer"))) {
    		return false;
    	 }
    	 return true;
    }
}
