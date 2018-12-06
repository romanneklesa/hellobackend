package com.hello.backend.controller;

import com.hello.backend.entity.Contact;
import com.hello.backend.service.ContactService;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /*@RequestMapping(value = "/contacts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Contact>> contacts
            (@RequestParam(value = "nameFilter", required = false) String nameFilter) {


        List<Contact> contacts = contactService.listContacts(nameFilter);

        return new ResponseEntity<>(contacts, HttpStatus.OK);

    }*/

    @RequestMapping(value = "/contacts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> contacts(@RequestParam(value = "nameFilter", required = false)
                                                   String nameFilter) throws JSONException {



        List<Contact> contacts = null;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            Pattern.compile(nameFilter);
        } catch (
                PatternSyntaxException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        contacts = contactService.listContacts(nameFilter);

        for (Contact c : contacts) {
            jsonArray.add(c.toJSON());
        }

        jsonObject.put("contacts", jsonArray);

        return new ResponseEntity<>(jsonObject.toJSONString(), HttpStatus.OK);

    }


}



