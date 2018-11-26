package com.hello.backend.controller;

import com.hello.backend.entity.Contact;
import com.hello.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/contacts/create")
    public ResponseEntity contactsCreate() {

        return contactService.save();
    }


    @RequestMapping(value = "/contacts")
    public ResponseEntity<List<Contact>> contacts
            (@RequestParam(value = "nameFilter", required = false) String nameFilter) {

        List<Contact> contacts = contactService.listContacts(nameFilter);

        return new ResponseEntity<>(contacts, HttpStatus.OK);


    }
}



