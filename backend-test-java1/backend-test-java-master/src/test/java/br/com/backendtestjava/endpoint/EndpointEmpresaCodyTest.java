import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import br.com.backendtestjava.backendtestjava.endpoint.EndpointEmpresa;
import br.com.backendtestjava.backendtestjava.entity.Empresa;
import br.com.backendtestjava.backendtestjava.repository.RepositoryEmpresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class EndpointEmpresaTests {

  @Mock
  private RepositoryEmpresa repository;

  private EndpointEmpresa endpoint = new EndpointEmpresa();

  @Test
  void testListAll_valid() {
    when(repository.findAll(any())).thenReturn(Page.empty()); 
    Page<Empresa> result = endpoint.listAll(Pageable.unpaged());
    assertNotNull(result);
    verify(repository).findAll(any());
  }

  @Test
  void testListAll_invalid() {
    when(repository.findAll(any())).thenThrow(IllegalArgumentException.class);
    assertThrows(IllegalArgumentException.class, () -> {
      endpoint.listAll(Pageable.unpaged()); 
    });
  }

  @Test
  void testAddEmpresa_valid() {
    Empresa empresa = new Empresa();
    ResponseEntity response = endpoint.addEmpresa(empresa);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void testAddEmpresa_invalid() {
    Empresa empresa = new Empresa();
    empresa.setId(1L);
    ResponseEntity response = endpoint.addEmpresa(empresa);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void testEditEmpresa_valid() {
    when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(new Empresa()));
    
    Empresa empresa = new Empresa();
    empresa.setId(1L);
    ResponseEntity response = endpoint.editEmpresa(1L, empresa);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testEditEmpresa_notFound() {
    when(repository.findById(anyLong())).thenReturn(java.util.Optional.empty());

    Empresa empresa = new Empresa();
    empresa.setId(1L);
    ResponseEntity response = endpoint.editEmpresa(1L, empresa);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testDeleteEmpresa_valid() {
    when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(new Empresa()));
    
    ResponseEntity response = endpoint.deleteEmpresa(1L);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testDeleteEmpresa_notFound() {
    when(repository.findById(anyLong())).thenReturn(java.util.Optional.empty());
    
    ResponseEntity response = endpoint.deleteEmpresa(1L);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

}
