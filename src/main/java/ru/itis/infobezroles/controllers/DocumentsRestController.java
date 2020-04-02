package ru.itis.infobezroles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.infobezroles.models.User;
import ru.itis.infobezroles.security.UserDetailsImpl;
import ru.itis.infobezroles.service.DocumentService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DocumentsRestController {


    @Autowired
    DocumentService documentService;

    @GetMapping("/download/{id:.+}")
    public ResponseEntity<Resource> downloadFileFromLocal(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return documentService.getResponseWithFileToDownload(id, httpServletRequest);
    }

    @GetMapping("/fileContent")
    public ResponseEntity getFileContent(@RequestParam Long id) {
        return ResponseEntity.ok(documentService.getFileContent(id));
    }

    @PutMapping("/fileContent")
    public ResponseEntity saveFileContent(@RequestParam Long id, @RequestParam String text) {
        documentService.save(id, text);
        return ResponseEntity.ok().build();
    }
}
