package com.cherkasov;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cherkasov.entities.ClientReference;
import com.cherkasov.repositories.DataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest
public class WebRESTTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  DataRepository repository;

  @Test
  public void testGet() throws Exception{

    final ClientReference entity = new ClientReference();
    entity.setId(1);
    when(repository.getById(any())).thenReturn(entity);
    this.mockMvc.perform(get("/get/1")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().
            string(containsString("1")));
  }

  @Test
  public void testPost() throws Exception{

    final ClientReference entity = new ClientReference();
    entity.setId(1);
    entity.setApiKey("edsdfsdf");

    ObjectMapper objectMapper = new ObjectMapper();

    when(repository.saveEntity(any())).thenReturn(entity);
    this.mockMvc.perform(post("/save/one").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entity))).andDo(print()).andExpect(status().isOk())
        .andExpect(content().
            string(containsString("1")));
  }
}
