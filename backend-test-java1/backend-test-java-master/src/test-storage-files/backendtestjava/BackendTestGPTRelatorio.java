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

public class EndpointRelatorioTest {

    private MockMvc mockMvc;

    @Mock
    private RepositoryRelatorio repositoryRelatorio;

    @InjectMocks
    private EndpointRelatorio endpointRelatorio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(endpointRelatorio).build();
    }


    @Test
    public void testListAllRelatorios() throws Exception {
        Page<Relatorio> relatorioPage = new PageImpl<>(Arrays.asList(new Relatorio(), new Relatorio()));
        when(repositoryRelatorio.findAll(any(PageRequest.class))).thenReturn(relatorioPage);

        mockMvc.perform(get("/relatorio").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());
    }

    @Test
    public void testAddRelatorio() throws Exception {
        Relatorio relatorio = new Relatorio(); // Set necessary fields
        String relatorioJson = /* convert relatorio to JSON */;

        mockMvc.perform(post("/relatorio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(relatorioJson))
                .andExpect(status().isCreated());

        verify(repositoryRelatorio, times(1)).save(any(Relatorio.class));
    }

    @Test
    public void testEditRelatorio() throws Exception {
        Long id = 1L;
        Relatorio relatorio = new Relatorio(); // Set necessary fields
        String relatorioJson = /* convert relatorio to JSON */;
        when(repositoryRelatorio.findById(id)).thenReturn(Optional.of(new Relatorio()));

        mockMvc.perform(put("/relatorio/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(relatorioJson))
                .andExpect(status().isOk());

        verify(repositoryRelatorio, times(1)).save(any(Relatorio.class));
    }

    @Test
    public void testDeleteRelatorio() throws Exception {
        Long id = 1L;
        when(repositoryRelatorio.findById(id)).thenReturn(Optional.of(new Relatorio()));

        mockMvc.perform(delete("/relatorio/" + id))
                .andExpect(status().isOk());

        verify(repositoryRelatorio, times(1)).deleteById(id);
    }
}
