package org.tempo.springEdu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.tempo.springEdu.common.Converter;
import org.tempo.springEdu.dto.FileDescriptionDto;
import org.tempo.springEdu.entity.FileDescription;
import org.tempo.springEdu.exception.CustomSecurityException;
import org.tempo.springEdu.exception.ObjectNotFoundException;
import org.tempo.springEdu.repository.FileDescriptionRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileManagerService {

    @Value("${file-storage-path}")
    private String storagePath;

    private final FileDescriptionRepository fileDescriptionRepository;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(storagePath));
        } catch (IOException ex) {
            throw new RuntimeException("Can't create file storage folder: " + storagePath, ex);
        }
    }

    public void moveFile(String id, String destDir) throws IOException {
        FileDescription fileDescription = findFileById(id);

        var oldPath = buildFilePath(fileDescription);
        Path newPath = buildFilePath(destDir, fileDescription.getFilename());

        checkPathSecurity(newPath);
        Files.createDirectories(buildFilePath(destDir));
        Files.move(oldPath, newPath);

        fileDescription.setDirectory(destDir);
        fileDescriptionRepository.save(fileDescription);
    }

    public void copyFile(String id, String destDir, String newFilename) throws IOException {
        FileDescription fileDescription = findFileById(id);

        var oldPath = buildFilePath(fileDescription);

        Path newPath;
        if (destDir.isBlank()) {
            newPath = buildFilePath(newFilename);
        }
        else {
            newPath = buildFilePath(destDir, newFilename);
        }

        checkPathSecurity(newPath);
        Files.createDirectories(buildFilePath(destDir));
        Files.copy(oldPath, newPath);

        fileDescription.setFilename(newFilename);
        fileDescription.setDirectory(destDir);
        fileDescription.setId(null);
        fileDescriptionRepository.save(fileDescription);
    }

    public Resource loadFile(String id) {
        FileDescription fileDescription = findFileById(id);
        return new FileSystemResource(buildFilePath(fileDescription));
    }

    public void deleteFile(String id) throws IOException {
        FileDescription delFile = findFileById(id);
        Files.delete(buildFilePath(delFile));
        fileDescriptionRepository.delete(delFile);
    }

    public FileDescription findFileById(String id) {
        return fileDescriptionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("File with id=%s not found", id)));
    }

    public List<FileDescriptionDto> findAllDto(String dir) {
        List<FileDescription> fileDescriptions;
        if (dir.isBlank()){
            fileDescriptions=fileDescriptionRepository.findAll();
        }
        else {
            fileDescriptions=fileDescriptionRepository.findByDirectory(dir);
        }

        return new Converter(FileDescription.class, FileDescriptionDto.class)
                .entityListToDtoList(fileDescriptions);
    }

    public void uploadFile(String link, String fileName) throws IOException {
        try (InputStream inputStream = new URL(link).openStream()) {
            Files.copy(inputStream, buildFilePath(fileName));
            create(buildFilePath(fileName));
        }
    }

    private void create(Path filePath) throws IOException {
        FileDescription metadata = getMetadataFromFile(filePath);
        fileDescriptionRepository.save(metadata);
    }

    private FileDescription getMetadataFromFile(Path filePath) throws IOException{
        FileDescription fileDescription = new FileDescription();
        fileDescription.setOriginalFilename(filePath.getFileName().toString());
        fileDescription.setFilename(fileDescription.getOriginalFilename());
        fileDescription.setMimeType(Files.probeContentType(filePath));
        fileDescription.setSize(Files.size(filePath));
        fileDescription.setDirectory("");
        return fileDescription;
    }

    public Path buildFilePath(String filename){
        return Paths.get(storagePath).resolve(filename);
    }

    public Path buildFilePath(String directory, String filename){
        return Paths.get(storagePath)
                .resolve(directory)
                .resolve(filename);
    }

    public Path buildFilePath(FileDescription fileDescription){
        return Paths.get(storagePath)
                .resolve(fileDescription.getDirectory())
                .resolve(fileDescription.getFilename());
    }

    public void checkPathSecurity(Path path){
        String parentPath = Paths.get(storagePath)
                .normalize().toAbsolutePath().toString();
        String pathToTest = path.normalize().toAbsolutePath().toString();
        if (pathToTest.contains(parentPath) == false) {
            log.warn("Restricted path: " + path.toString());
            throw new CustomSecurityException("Restricted path");
        }
    }


}
