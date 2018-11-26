package com.hello.backend.dao;

import com.hello.backend.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDao extends JpaRepository<Contact, Long> {

}
