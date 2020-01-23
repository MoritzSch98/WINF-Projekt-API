package com.example.gewerbeanmeldung.pdffile;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.Answers.AnswersService;
import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.example.gewerbeanmeldung.FormFilled.FormFilledService;
import com.example.gewerbeanmeldung.Question.QuestionService;
import com.itextpdf.layout.Document;


@Service
public class PdfFileService {

    @Autowired
    private PdfFileRepository dbFileRepository;
    @Autowired
    private FormFilledService ffService;
 
    public PdfFile storeFile(byte[] d, Integer form_id, String filename) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(filename);

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new PdfStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            PdfFile dbFile = new PdfFile(fileName, "application/pdf", d);
            FormFilled ff = ffService.getFilledForm(form_id);
            dbFile.setFormFilled(ff);
            return dbFileRepository.save(dbFile);
            
        } catch (Exception ex) {
            throw new PdfStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public PdfFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new PdfFileNotFoundException("File not found with id " + fileId));
    }
   
    
   /* public String updateFile(String fileId, MultipartFile file, Integer form_id) {
    	deleteFile(fileId);
    	storeFile(file, form_id);
    	return "saved";
    }
    */
    public String deleteFile(String fileId) {
    	dbFileRepository.deleteById(fileId);
    	return "Delete Successfull";
    }
    public String deleteFileByAnswerId(FormFilled ff) {
    	PdfFile dbfile = dbFileRepository.getByFormFilled(ff);
    	dbFileRepository.deleteById(dbfile.getId());
    	return "success in deleting";
    }
}
