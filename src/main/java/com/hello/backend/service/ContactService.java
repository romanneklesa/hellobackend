package com.hello.backend.service;

import com.hello.backend.entity.Contact;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {

    List<Contact> listContacts(String nameFilter);

    ResponseEntity save();
}
