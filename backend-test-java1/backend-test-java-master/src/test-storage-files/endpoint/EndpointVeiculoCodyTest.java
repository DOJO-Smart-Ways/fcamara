import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import br.com.backendtestjava.backendtestjava.entity.Veiculo;
import br.com.backendtestjava.backendtestjava.repository.RepositoryVeiculo;

public class RepositoryVeiculoTest {

  @Mock
  private RepositoryVeiculo repository;

  @Test
  public void testFindById_success() {
    Veiculo veiculo = new Veiculo();
    veiculo.setId(1L);
    
    when(repository.findById(1L)).thenReturn(java.util.Optional.of(veiculo));
    
    Veiculo result = repository.findById(1L).get();
    assertEquals(1L, result.getId());
  }

  @Test
  public void testFindById_notFound() {
    when(repository.findById(1L)).thenReturn(java.util.Optional.empty());
    
    Veiculo result = repository.findById(1L).orElse(null);
    assertEquals(null, result); 
  }
}