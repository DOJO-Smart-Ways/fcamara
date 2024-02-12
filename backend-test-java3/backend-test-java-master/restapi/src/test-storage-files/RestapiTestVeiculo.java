import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        when(veiculoRepository.findAll()).thenReturn(List.of(new Veiculo(), new Veiculo()));
        List<Veiculo> result = veiculoService.findAll();
        assertEquals(2, result.size());
    }
    @Test
    public void testFindById() throws NotFoundException {
        Integer testId = 1;
        Veiculo veiculo = new Veiculo();
        when(veiculoRepository.findById(testId)).thenReturn(Optional.of(veiculo));

        Veiculo result = veiculoService.findById(testId);
        assertEquals(veiculo, result);
    }
    @Test
    public void testFindByPlaca() throws NotFoundException {
        String testPlaca = "ABC123";
        Veiculo veiculo = new Veiculo();
        when(veiculoRepository.findByPlaca(testPlaca)).thenReturn(Optional.of(veiculo));

        Veiculo result = (Veiculo) veiculoService.findByPlaca(testPlaca);
        assertEquals(veiculo, result);
    }
    @Test
    public void testCreate() throws AlreadyExistException {
        VeiculoDTO dto = new VeiculoDTO();
        Veiculo veiculo = new Veiculo();
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo result = veiculoService.create(dto);
        assertEquals(veiculo, result);
    }
    @Test
    public void testDelete() throws NotFoundException {
        Integer testId = 1;
        when(veiculoRepository.findById(testId)).thenReturn(Optional.of(new Veiculo()));

        String result = veiculoService.delete(testId);
        assertEquals("Veiculo deletado com sucesso", result);
    }
    @Test
    public void testUpdate() throws NotFoundException {
        Integer testId = 1;
        Veiculo veiculo = new Veiculo();
        when(veiculoRepository.findById(testId)).thenReturn(Optional.of(veiculo));
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo result = veiculoService.update(veiculo, testId);
        assertEquals(veiculo, result);
    }

}
