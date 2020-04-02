package ru.itis.infobezroles.aspects;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.itis.infobezroles.models.Document;
import ru.itis.infobezroles.models.Role;
import ru.itis.infobezroles.models.User;
import ru.itis.infobezroles.repository.DocumentRepository;
import ru.itis.infobezroles.security.UserDetailsImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Configuration
public class AccessAspect {

    @Autowired
    DocumentRepository documentRepository;

    @Pointcut("execution (* *..DocumentsRestController.downloadFileFromLocal(..))")
    public void downloadFile() {
    }

    @Pointcut("execution (* *..DocumentsRestController.getFileContent(..))")
    public void getFileContent() {
    }

    @Pointcut("downloadFile() || getFileContent()")
    public void readOperation() {
    }

    @SneakyThrows
    @Around("readOperation()")
    public ResponseEntity<Object> checkAccessToRead(ProceedingJoinPoint jp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        Long fileId = (Long) jp.getArgs()[0];
        Document document = documentRepository.findById(fileId).orElseThrow(IllegalArgumentException::new);
        if (user.getRole().getName().equals("ROLE_GUEST") && !document.isAllowedToGuests()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return (ResponseEntity) jp.proceed(jp.getArgs());
    }

    @Pointcut("execution(* *..DocumentsRestController.saveFileContent(..))")
    public void writeOperation() {
    }

    @SneakyThrows
    @Around("writeOperation()")
    public ResponseEntity<Object> checkAccessToWrite(ProceedingJoinPoint jp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        if (user.getRole().getName().equals("ROLE_USER") || user.getRole().getName().equals("ROLE_GUEST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return (ResponseEntity) jp.proceed(jp.getArgs());
    }

    @Pointcut("execution (* *..GrantRestController.grantRole(..))")
    public void changeRolesOperation() {
    }

    @SneakyThrows
    @Around("changeRolesOperation()")
    public ResponseEntity checkAccessToGrant(ProceedingJoinPoint jp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        if (!user.getRole().getName().equals("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return (ResponseEntity) jp.proceed(jp.getArgs());
    }

}
