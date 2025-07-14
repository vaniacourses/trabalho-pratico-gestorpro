package com.gestorpro.file_utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class FileStorageService {
    private String repositoryPath = "./file-repository/public";
    private String repositoryPathRestricted = "./file-repository/restricted";

    private final Path fileStoragePath;
    private final Path fileRestrictedStoragePath;
 
    public FileStorageService() throws IOException {
        this.fileStoragePath = Paths.get(repositoryPath).toAbsolutePath();
        this.fileRestrictedStoragePath = Paths.get(repositoryPathRestricted).toAbsolutePath();

        Files.createDirectories(this.fileStoragePath);
        Files.createDirectories(this.fileRestrictedStoragePath);
    }

    public void save(String fileName, InputStream file, boolean restricted) throws IllegalStateException, IOException {
        String fileNameClean = StringUtils.cleanPath(fileName);

        Path uploadLocation;
         if(restricted){
            uploadLocation = fileRestrictedStoragePath.resolve(fileNameClean);
        }
        else{
            uploadLocation = fileStoragePath.resolve(fileNameClean);
        }
        
        Files.copy(file, uploadLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    public Resource download(String fileName, boolean restricted) throws IOException{
        Path filePath;
        if(restricted){
            filePath = fileRestrictedStoragePath.resolve(fileName).normalize();
        }
        else{
            filePath = fileStoragePath.resolve(fileName).normalize();
        }


        return new UrlResource(filePath.toUri());
    }

    public boolean exists(String fileName, boolean restricted){
        if (restricted) {
            if(!Files.exists(fileRestrictedStoragePath.resolve(fileName).normalize())){
                return false;
            }
            else{
                return true;
            }
        }
        else if(!Files.exists(fileStoragePath.resolve(fileName).normalize())){
            return false;
        }
        else{
            return true;
        }
    }

    public List<String> list(boolean restricted) throws IOException{
        Path filePath;
        if(restricted){
            filePath = fileRestrictedStoragePath;
        }
        else{
            filePath = fileStoragePath;
        }

        List<String> files = Files.list(filePath)
            .map(Path::getFileName)
            .map(Path::toString)
            .collect(Collectors.toList());

        return files;
    }
}
