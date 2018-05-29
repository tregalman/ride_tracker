package com.pluralsight.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pluralsight.ApplicationConfiguration;
import com.pluralsight.TestDataSourceConfiguration;
import com.pluralsight.ServletConfiguration;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration(classes = {
    ServletConfiguration.class, ApplicationConfiguration.class, TestDataSourceConfiguration.class})
public class RideControllerTest {

  @ClassRule
  public static final SpringClassRule springClassRule = new SpringClassRule();
  @Rule
  public final SpringMethodRule springMethodRule = new SpringMethodRule();
  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testGetRides() throws Exception {
    mockMvc.perform(get("/rides").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].name", is("China")))
        .andExpect(jsonPath("$[0].duration", is(1)));
  }
}
