import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mock;

import br.com.backendtestjava.backendtestjava.endpoint.EndpointRelatorio;
import br.com.backendtestjava.backendtestjava.entity.Relatorio;
import br.com.backendtestjava.backendtestjava.repository.RepositoryRelatorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EndpointRelatorioTest {

  @Mock
  private RepositoryRelatorio repository;

  private EndpointRelatorio endpoint = new EndpointRelatorio();

  @Test
  public void testListAll_valid() {
    when(repository.findAll(any())).thenReturn(Page.empty());
    Page<Relatorio> result = endpoint.listAll(Pageable.unpaged());
    assertNotNull(result);
    verify(repository).findAll(any());
  }
  
  @Test
  public void testListAll_invalid() {
    when(repository.findAll(any())).thenThrow(IllegalArgumentException.class);
    assertThrows(IllegalArgumentException.class, () -> {
      endpoint.listAll(Pageable.unpaged());
    });
  }

  @Test
  public void testAddRelatorio_valid() {
    Relatorio relatorio = new Relatorio();
    ResponseEntity response = endpoint.addRelatorio(relatorio);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void testAddRelatorio_invalid() {
    Relatorio relatorio = new Relatorio();
    relatorio.setId(1L);
    ResponseEntity response = endpoint.addRelatorio(relatorio);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); 
  }

  @Test
  public void testEditRelatorio_valid() {
    when(repository.findById(anyLong())).thenReturn(Optional.of(new Relatorio()));
    Relatorio relatorio = new Relatorio();
    relatorio.setId(1L);
    ResponseEntity response = endpoint.editRelatorio(1L, relatorio);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testEditRelatorio_notFound() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());
    Relatorio relatorio = new Relatorio();
    relatorio.setId(1L);
    ResponseEntity response = endpoint.editRelatorio(1L, relatorio);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void testDeleteRelatorio_valid() {
    when(repository.findById(anyLong())).thenReturn(Optional.of(new Relatorio()));
    ResponseEntity response = endpoint.deleteRelatorio(1L);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testDeleteRelatorio_notFound() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());
    ResponseEntity response = endpoint.deleteRelatorio(1L); 
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

}