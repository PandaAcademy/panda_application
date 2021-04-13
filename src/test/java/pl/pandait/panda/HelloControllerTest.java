package pl.pandait.panda;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

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