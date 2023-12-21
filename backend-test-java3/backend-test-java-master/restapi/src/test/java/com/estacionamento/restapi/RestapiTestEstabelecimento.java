import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EstabelecimentoServiceTest {

    @MockBean
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private EstabelecimentoService estabelecimentoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        when(estabelecimentoRepository.findAll()).thenReturn(List.of(new Estabelecimento(), new Estabelecimento()));
        List<Estabelecimento> result = estabelecimentoService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindById() throws NotFoundException {
        int testId = 1;
        Estabelecimento estabelecimento = new Estabelecimento();
        when(estabelecimentoRepository.findById(testId)).thenReturn(Optional.of(estabelecimento));

        Estabelecimento result = estabelecimentoService.findById(testId);
        assertEquals(estabelecimento, result);
    }

    @Test
    public void testFindByCnpj() throws NotFoundException {
        String testCnpj = "123456789";
        Estabelecimento estabelecimento = new Estabelecimento();
        when(estabelecimentoRepository.findByCnpj(testCnpj)).thenReturn(Optional.of(estabelecimento));

        Estabelecimento result = estabelecimentoService.findByCnpj(testCnpj);
        assertEquals(estabelecimento, result);
    }

    @Test
    public void testCreate() {
        EstabelecimentoDTO dto = new EstabelecimentoDTO();
        Estabelecimento estabelecimento = new Estabelecimento();
        when(estabelecimentoRepository.save(any(Estabelecimento.class))).thenReturn(estabelecimento);

        Estabelecimento result = estabelecimentoService.create(dto);
        assertEquals(estabelecimento, result);
    }

    @Test
    public void testDelete() throws NotFoundException {
        int testId = 1;
        when(estabelecimentoRepository.findById(testId)).thenReturn(Optional.of(new Estabelecimento()));

        String result = estabelecimentoService.delete(testId);
        assertEquals("Estabelecimento deletado com sucesso", result);
    }

    @Test
    public void testUpdate() throws NotFoundException {
        int testId = 1;
        Estabelecimento estabelecimento = new Estabelecimento();
        when(estabelecimentoRepository.findById(testId)).thenReturn(Optional.of(estabelecimento));
        when(estabelecimentoRepository.save(any(Estabelecimento.class))).thenReturn(estabelecimento);

        Estabelecimento result = estabelecimentoService.update(estabelecimento, testId);
        assertEquals(estabelecimento, result);
    }


}
