package ru.itis.infobezroles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.itis.infobezroles.models.Document;
import ru.itis.infobezroles.repository.DocumentRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;


    public ResponseEntity<Resource> getResponseWithFileToDownload(Long fileId, HttpServletRequest request) {
        Document document = documentRepository.findById(fileId).orElseThrow(IllegalArgumentException::new);
        Path path = Paths.get(document.getPath());
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public String getFileContent(Long fileId) {
        List<String> lines = null;
        try {
            Document document = documentRepository.findById(fileId).orElseThrow(IllegalArgumentException::new);
            Path path = Paths.get(document.getPath());
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.join("\n", lines);
    }

    public void save(Long id, String text) {
        try {
            Document document = documentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            Path path = Paths.get(document.getPath());
            Files.write(path, text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
