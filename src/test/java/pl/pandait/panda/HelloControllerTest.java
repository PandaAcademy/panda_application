package pl.pandait.panda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest{

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeSetUp(){
        this.mockMvc = standaloneSetup(new HelloController()).build();
    }

    @Test
    public void whenGivenAttributeNameThenItIsInModel() throws Exception{
        this.mockMvc.perform(get("/greeting?name=Panda")
            .accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(model().attribute("name", "Panda"));
    }
}