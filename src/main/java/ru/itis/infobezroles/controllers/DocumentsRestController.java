package ru.itis.infobezroles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/download/{fileId:.+}")
    public ResponseEntity downloadFileFromLocal(@PathVariable Long fileId, Authentication authentication, HttpServletRequest httpServletRequest) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        return documentService.getResponseWithFileToDownload(fileId, httpServletRequest);
    }

    @GetMapping("/fileContent")
    public @ResponseBody String getFileContent(@RequestParam Long id) {
        return documentService.getFileContent(id);
    }

    @PutMapping("/fileContent")
    public void saveFileContent(@RequestParam Long id, @RequestParam String text) {
        documentService.save(id, text);
    }
}
