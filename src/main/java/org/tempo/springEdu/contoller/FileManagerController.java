package org.tempo.springEdu.contoller;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.tempo.springEdu.SpringEduApplication;
import org.tempo.springEdu.entity.User;
import org.tempo.springEdu.service.FileManagerService;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/file-manager")
@RequiredArgsConstructor
@Log4j2
public class FileManagerController {

    private final FileManagerService fileManagerService;

    @RequestMapping("/test")
    String test(
            @RequestParam(value = "destDir", required = false, defaultValue = "") String destDir) {
        var paramPath = fileManagerService.buildFilePath(destDir);
        log.debug(paramPath);
        log.debug(paramPath.normalize().toAbsolutePath());
        fileManagerService.checkPathSecurity(paramPath);
        return "FileManagerController.test";
    }

    @GetMapping("/upload-file")
    public ResponseEntity<?> uploadFile (
            @RequestParam("link") String link,
            @RequestParam("filename") String filename) throws IOException, ExecutionException, InterruptedException {
        fileManagerService.uploadFile(link, filename);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll (
            @RequestParam(value = "dir", required = false, defaultValue = "") String dir) {
        return ResponseEntity.ok(fileManagerService.findAllDto(dir));
    }

    @GetMapping("/find")
    public ResponseEntity<?> find (
            @RequestParam("filenamePart") String filenamePart,
            @RequestParam(value = "dir", required = false, defaultValue = "") String dir) {
        return ResponseEntity.ok(fileManagerService.findDto(filenamePart, dir));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable(value = "id") String id)
            throws IOException{
        fileManagerService.deleteFile(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        Resource file = fileManagerService.loadFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=\"%s\"", file.getFilename()))
                .body(file);
    }

    // Copy file. Client provides file ID, destination directory and new file name.
    @GetMapping("/copy")
    public ResponseEntity<?> copyFile(
            @RequestParam String id,
            @RequestParam(value = "destDir", required = false, defaultValue = "") String destDir,
            @RequestParam(value ="newFilename") String newFilename)
            throws IOException {
        fileManagerService.copyFile(id, destDir, newFilename);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Move to some directory. Client provides file ID, destination directory.
    @GetMapping("/move")
    public ResponseEntity<?> moveFile(
            @RequestParam String id,
            @RequestParam(value = "destDir") String destDir)
            throws IOException {
        fileManagerService.moveFile(id, destDir);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
