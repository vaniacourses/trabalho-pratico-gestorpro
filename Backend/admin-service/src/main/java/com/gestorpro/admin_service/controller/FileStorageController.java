package com.gestorpro.admin_service.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gestorpro.admin_service.config.FileStorageProperties;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/admin/files")
public class FileStorageController {

    private final Path fileStoragePath;
    private final Path fileRestrictedStoragePath;

    public FileStorageController(FileStorageProperties fileStorageProperties) throws IOException {
        this.fileStoragePath = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath();
        this.fileRestrictedStoragePath = Paths.get(fileStorageProperties.getUploadDirRestricted()).toAbsolutePath();

        Files.createDirectories(this.fileStoragePath);
        Files.createDirectories(this.fileRestrictedStoragePath);
    }

    @GetMapping("/oi")
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("foi");
    }
    

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IllegalStateException, IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path uploadLocation = fileStoragePath.resolve(fileName);
        file.transferTo(uploadLocation);

        return ResponseEntity.ok("Upload completo!");
    }

    @PostMapping("/restricted/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadFileRestricted(@RequestParam MultipartFile file) throws IllegalStateException, IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path uploadLocation = fileRestrictedStoragePath.resolve(fileName);
        file.transferTo(uploadLocation);

        return ResponseEntity.ok("Upload restrito completo!");
    }
    
    private ResponseEntity<Resource> returnDownloadFile(HttpServletRequest request, Path filePath) throws IOException{
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());

        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Path filePath = fileStoragePath.resolve(fileName).normalize();

        return returnDownloadFile(request, filePath);
    }

    @GetMapping("/restricted/download/{fileName:.+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadRestrictedFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Path filePath = fileRestrictedStoragePath.resolve(fileName).normalize();

        return returnDownloadFile(request, filePath);
    }


    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() throws IOException {
        List<String> files = Files.list(fileStoragePath)
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());

        return ResponseEntity.ok(files);
    }
    
    @GetMapping("/restricted/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<String>> listFilesRestricted() throws IOException {
        List<String> files = Files.list(fileRestrictedStoragePath)
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());

        return ResponseEntity.ok(files);
    }
    
}
