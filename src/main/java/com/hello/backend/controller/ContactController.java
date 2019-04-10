package com.hello.backend.controller;

import com.hello.backend.entity.Contact;
import com.hello.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@RestController
@RequestMapping("/hello")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/contacts/save")
    public ResponseEntity contactsCreate() {

        return contactService.save();
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<Contact>> contacts(

            @RequestParam(value = "nameFilter") String nameFilter,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "100000") Integer pageSize) {

        Pageable pageable = new PageRequest(pageNumber, pageSize);

        return contactService.listContacts(nameFilter, pageable);

    }
}