package com.hello.backend.service.impl;

import com.hello.backend.dao.ContactDao;
import com.hello.backend.entity.Contact;
import com.hello.backend.service.ContactService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDao contactDao;

    @Override
    public ResponseEntity save() {

        for (int i = 0; i < 1000000; i++) {
            contactDao.save(new Contact(RandomStringUtils.randomAlphabetic(10)));
        }

        return ResponseEntity.ok().build();
    }

    public List<Contact> listContacts(String nameFilter) {

        List<Contact> contacts = contactDao.findAll();
        final Pattern pattern = Pattern.compile(nameFilter);

        for (Iterator<Contact> it = contacts.iterator();
             it.hasNext(); ) {

            final Contact contact = it.next();
            final Matcher matcher = pattern.matcher(contact.getName());
            if (matcher.matches()) {
                it.remove();

            }
        }

        return contacts;
    }
}