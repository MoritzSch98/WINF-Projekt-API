package com.example.gewerbeanmeldung.pdffile;

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

import com.itextpdf.layout.Document;

@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
public class PdfUploadController {

    @Autowired
    private PdfFileService fileStorageService;

   /* @PostMapping("/uploadPdf/filled/{form_id}")
    public Response uploadFile(@RequestParam("file") Document file, @PathVariable Integer form_id) {
    	PdfFile fileName = fileStorageService.storeFile(file, form_id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        return new Response(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultiplePdf/filled/{form_id}")
    public List<Response> uploadMultipleFiles(@PathVariable Integer form_id, @RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, form_id))
                .collect(Collectors.toList());
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/uploadPdf/{id}/filled/{form_id}/update")
    public String updateFile(@RequestParam("file") MultipartFile file, @PathVariable Integer form_id, @PathVariable Integer question_id, @PathVariable String id) {
    	fileStorageService.updateFile(id, file, form_id);
		return id;
    }*/
    // im Service eine Lösch methode 
}
