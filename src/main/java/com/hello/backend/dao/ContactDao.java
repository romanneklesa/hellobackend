package com.hello.backend.dao;

import com.hello.backend.entity.Contact;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactDao extends PagingAndSortingRepository<Contact, Long> {


}
