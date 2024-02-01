import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EndpointVeiculoTest {

    private MockMvc mockMvc;

    @Mock
    private RepositoryVeiculo repositoryVeiculo;

    @InjectMocks
    private EndpointVeiculo endpointVeiculo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(endpointVeiculo).build();
    }
    
    @Test
    public void testListAllVeiculos() throws Exception {
        Page<Veiculo> veiculoPage = new PageImpl<>(Arrays.asList(new Veiculo(), new Veiculo()));
        when(repositoryVeiculo.findAll(any(PageRequest.class))).thenReturn(veiculoPage);

        mockMvc.perform(get("/veiculo").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());
    }

    @Test
    public void testAddVeiculo() throws Exception {
        Veiculo veiculo = new Veiculo(); // Set necessary fields
        String veiculoJson = /* convert veiculo to JSON */;

        mockMvc.perform(post("/veiculo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(veiculoJson))
                .andExpect(status().isCreated());

        verify(repositoryVeiculo, times(1)).save(any(Veiculo.class));
    }

    @Test
    public void testEditVeiculo() throws Exception {
        Long id = 1L;
        Veiculo veiculo = new Veiculo(); // Set necessary fields
        String veiculoJson = /* convert veiculo to JSON */;
        when(repositoryVeiculo.findById(id)).thenReturn(Optional.of(new Veiculo()));

        mockMvc.perform(put("/veiculo/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(veiculoJson))
                .andExpect(status().isOk());

        verify(repositoryVeiculo, times(1)).save(any(Veiculo.class));
    }

    @Test
    public void testDeleteVeiculo() throws Exception {
        Long id = 1L;
        when(repositoryVeiculo.findById(id)).thenReturn(Optional.of(new Veiculo()));

        mockMvc.perform(delete("/veiculo/" + id))
                .andExpect(status().isOk());

        verify(repositoryVeiculo, times(1)).deleteById(id);
    }

}