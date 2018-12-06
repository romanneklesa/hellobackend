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
    @ResponseBody
    public ResponseEntity<Page<Contact>> contacts(

            @RequestParam(value = "nameFilter", required = false) String nameFilter,
            @RequestParam(value = "page") final Integer pageNumber,
            @RequestParam(value = "size") final Integer pageSize,
            Pageable pageable)  {

        List<Contact> contacts = null;

        try {
            Pattern.compile(nameFilter);
        } catch (
                PatternSyntaxException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        pageable = new PageRequest(pageNumber, pageSize);

        Page<Contact> dataList = contactService.listContacts(nameFilter, pageable);

        return new ResponseEntity<>(dataList, HttpStatus.OK);
    }
}