package com.gestorpro.admin_service.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gestorpro.admin_service.utils.MultipartFileSaveAdapter;
import com.gestorpro.file_utils.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin/files")
public class FileStorageController {
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @GetMapping("/oi")
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("foi");
    }
    
    
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IllegalStateException, IOException {
        MultipartFileSaveAdapter adapter = new MultipartFileSaveAdapter(file);
        fileStorageService.save(file.getOriginalFilename(), adapter, false);
        
        return ResponseEntity.ok("{\"message\":\"upload completo\"}");
    }
    
    @PostMapping("/restricted/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadFileRestricted(@RequestParam MultipartFile file) throws IllegalStateException, IOException {
        MultipartFileSaveAdapter adapter = new MultipartFileSaveAdapter(file);
        
        fileStorageService.save(file.getOriginalFilename(), adapter, true);
        return ResponseEntity.ok("{\"message\":\"upload restrito completo\"}");
    }
    
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        if(!fileStorageService.exists(fileName, false)){
            return ResponseEntity.notFound().build();
        }
        
        Resource resource = fileStorageService.download(fileName, false);
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        
        return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
    }
    
    @GetMapping("/restricted/download/{fileName:.+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadRestrictedFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        if(!fileStorageService.exists(fileName, true)){
            return ResponseEntity.notFound().build();
        }
        
        Resource resource = fileStorageService.download(fileName, true);
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        
        return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
    }
    
    
    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() throws IOException {
        List<String> files = fileStorageService.list(false);
        
        return ResponseEntity.ok(files);
    }
    
    @GetMapping("/restricted/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<String>> listFilesRestricted() throws IOException {
        List<String> files = fileStorageService.list(true);
        return ResponseEntity.ok(files);
    }
    
}
