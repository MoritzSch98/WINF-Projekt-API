package com.example.gewerbeanmeldung.pdffile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.codec.Base64.InputStream;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
public class PdfFileDownloadController {

    @Autowired
    private PdfFileService fileStorageService;

    @GetMapping(produces = "application/pdf", path="/downloadPdf/{fileId:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletRequest request) throws IOException {
        // Load file as Resource
        PdfFile pdfFile = fileStorageService.getFile(fileId);
       
        ByteArrayInputStream targetStream = new ByteArrayInputStream(pdfFile.getData());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(pdfFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pdfFile.getFileName() + "\"")
                .body(new ByteArrayResource(targetStream.toString().getBytes()));
    }

}
