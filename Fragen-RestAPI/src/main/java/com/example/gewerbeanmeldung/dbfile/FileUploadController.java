package com.example.gewerbeanmeldung.dbfile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//This is the Controller for file upload, edit requests, which will directed to the DatabaseFileService and than executed!
@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
public class FileUploadController {

    @Autowired
    private DatabaseFileService fileStorageService;

    //uploading a file with parameters formfilled_id(formfilled) and question_id. This both make it possible to generate
    //the answer where the file belongs to. This makes it easier for the frontend dev to integrate the file
    //upload
    @PostMapping("/uploadFile/filled/{formfilled_id}/question/{question_id}")
    public Response uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Integer formfilled_id, @PathVariable Integer question_id) {
    	DatabaseFile fileName = fileStorageService.storeFile(file, formfilled_id, question_id);
    	
    	//Creating the filedownloaduri
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        //Return the file with its belongings as success
        return new Response(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    //Uploading multiple files, actually just making multiple single upload request after another, using java 8 collections for 
    //looping through array
    @PostMapping("/uploadMultipleFiles/filled/{formfilled_id}/question/{question_id}")
    public List<Response> uploadMultipleFiles(@PathVariable Integer formfilled_id, @PathVariable Integer question_id, @RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, formfilled_id, question_id))
                .collect(Collectors.toList());
    }
    
    //method editing an exsting file. We actually delete the old one and create a new one in behind, visible
    //in service class
    @RequestMapping(method = RequestMethod.PUT, path = "/uploadFile/{id}/filled/{formfilled_id}/question/{question_id}/update")
    public String updateFile(@RequestParam("file") MultipartFile file, @PathVariable Integer formfilled_id, @PathVariable Integer question_id, @PathVariable String id) {
    	fileStorageService.updateFile(id, file, formfilled_id, question_id);
		return id;
    }
}
