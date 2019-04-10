package com.hello.backend.service.impl;

import com.hello.backend.dao.ContactDao;
import com.hello.backend.entity.Contact;
import com.hello.backend.service.ContactService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

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

    public ResponseEntity<Page<Contact>> listContacts(String nameFilter, Pageable pageable) {

        try {
            final Pattern pattern = Pattern.compile(nameFilter);
            Page<Contact> list = contactDao.findAll(pageable);
            List<Contact> res = list.getContent();
            res = list.getContent().parallelStream().
                    filter(o -> !pattern.matcher(o.getName()).matches()).
                    collect(Collectors.toList());

            Page<Contact> pages = new PageImpl<>(res);

            return new ResponseEntity<>(pages, HttpStatus.OK);
        } catch (
                PatternSyntaxException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}