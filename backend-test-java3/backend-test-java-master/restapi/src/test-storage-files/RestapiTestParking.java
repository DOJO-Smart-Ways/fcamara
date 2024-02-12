import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ParkinServiceTest {

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private ParkinService parkinService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEntrada() throws NotFoundException, AlreadyFullException {
        Integer estabelecimentoId = 1;
        Integer veiculoId = 1;
        Estabelecimento estabelecimento = new Estabelecimento(); // Set fields as needed
        Veiculo veiculo = new Veiculo(); // Set fields as needed

        when(estabelecimentoRepository.findById(estabelecimentoId)).thenReturn(Optional.of(estabelecimento));
        when(veiculoRepository.findById(veiculoId)).thenReturn(Optional.of(veiculo));

        String result = parkinService.entrada(estabelecimentoId, veiculoId);
        assertNotNull(result);

        // Additional assertions as needed
    }

    @Test
    public void testSaida() throws NotFoundException {
        Integer estabelecimentoId = 1;
        Integer veiculoId = 1;
        Estabelecimento estabelecimento = new Estabelecimento(); // Set fields as needed
        Veiculo veiculo = new Veiculo(); // Set fields as needed

        when(estabelecimentoRepository.findById(estabelecimentoId)).thenReturn(Optional.of(estabelecimento));
        when(veiculoRepository.findById(veiculoId)).thenReturn(Optional.of(veiculo));

        String result = parkinService.saida(veiculoId, estabelecimentoId);
        assertNotNull(result);

        // Additional assertions as needed
    }

}
