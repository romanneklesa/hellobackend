package com.hello.backend.hello;

import com.hello.backend.controller.ContactController;
import com.hello.backend.entity.Contact;
import com.hello.backend.service.ContactService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloApplicationTests {

    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactService contactService;

    private MockMvc mockMvc;
    private ArrayList<Contact> testContacts;

    private String nameFilter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();

        final Contact testContact1 = new Contact("BciufPk");
        final Contact testContact2 = new Contact("GoRLfOO");

        testContacts = new ArrayList<Contact>();

        testContacts.add(testContact1);
        testContacts.add(testContact2);
    }

    @Test
    public void testContactFound() throws Exception {

        when(contactService.listContacts(nameFilter)).thenReturn(testContacts);
    }

    @Test
    public void testFilterPage() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/hello/contacts?nameFilter=^A.*$");
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testSave() throws Exception {

        Contact contact = new Contact("TestName");
        when(contactService.save())
                .thenReturn(new ResponseEntity<>(contact, HttpStatus.OK));
    }
}
