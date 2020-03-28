package com.example.gewerbeanmeldung.dbfile;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//This class is the controller for making a download of a DatabaseFile from the database 
//and make it accessable
@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
public class FileDownloadController {

    @Autowired
    private DatabaseFileService fileStorageService;

    //Actual request for downloading a file by the fileId
    @GetMapping("/downloadFile/{fileId:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletRequest request) {
        // Load file as Resource
        DatabaseFile databaseFile = fileStorageService.getFile(fileId);

        //Return a response Entity as the link for the Downloaded file and also contentType
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
                .body(new ByteArrayResource(databaseFile.getData()));
    }

}
