package com.hello.backend.hello;

import com.hello.backend.controller.ContactController;
import com.hello.backend.entity.Contact;
import com.hello.backend.service.ContactService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)

@SpringBootTest
public class HelloApplicationTests {

    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactService contactService;

    @Mock
    private JSONObject jsonObject;

    @Mock
    private JSONArray jsonArray;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(contactController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

    }

    @Test
    public void testContactFound() throws Exception {

        List<Contact> expected = new ArrayList<>();
        Page expectedPage = new PageImpl(expected);
        when(contactService.listContacts(any(String.class), any(Pageable.class))).thenReturn(expectedPage);
    }

    @Test
    public void testFilterPage() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/hello/contacts")
                        .param("nameFilter", "^f.*$&")
                        .param("page", "0" )
                        .param("size", "10");
       mockMvc.perform(builder)
                .andExpect(status()
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