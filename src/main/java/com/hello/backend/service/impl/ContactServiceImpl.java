package com.hello.backend.service.impl;

import com.hello.backend.dao.ContactDao;
import com.hello.backend.entity.Contact;
import com.hello.backend.service.ContactService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public List<Contact> listContacts(String nameFilter) {

       Pattern pattern = Pattern.compile(nameFilter);
        List<Contact> list = contactDao.findAll();

        List<Contact> res =
               /* list.parallelStream().filter(o->
                        o.getName().matches("[A-Za-z]{1,}")).collect(Collectors.toList());*/
                list.parallelStream().
                filter(o-> !pattern.matcher(o.getName()).find()).
                collect(Collectors.toList());

        return res;
    }
}