/*package com.example.gewerbeanmeldung.dbfile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.criteria.Path;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileController {

    private String fileBasePath = "http://localhost:8090/filesafe/";
    
    @PostMapping("/upload")
    public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
    	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	java.nio.file.Path path = Paths.get(fileBasePath + fileName);
    	try {
    		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
    			.path("/files/download/")
    			.path(fileName)
    			.toUriString();
    	return ResponseEntity.ok(fileDownloadUri);
    }

}
*/