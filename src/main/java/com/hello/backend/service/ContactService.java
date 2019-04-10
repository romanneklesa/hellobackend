package com.hello.backend.service;

import com.hello.backend.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ContactService {

    ResponseEntity<Page<Contact>> listContacts(String nameFilter, Pageable pageable);

    ResponseEntity save();
}
