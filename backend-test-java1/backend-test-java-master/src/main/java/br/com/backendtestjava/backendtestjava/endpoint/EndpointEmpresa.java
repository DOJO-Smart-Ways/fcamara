package br.com.backendtestjava.backendtestjava.endpoint;

import br.com.backendtestjava.backendtestjava.entity.Empresa;
import br.com.backendtestjava.backendtestjava.respository.RepositoryEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EndpointEmpresa {

    @Autowired
    private RepositoryEmpresa repositoryEmpresa;

    @GetMapping
    public Page<Empresa> listAll(Pageable pageable) {
        return repositoryEmpresa.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity addEmpresa(@RequestBody Empresa empresa) {
        if (empresa.getId() != null) {
            empresa.setId(null);
        }
        repositoryEmpresa.save(empresa);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity editEmpresa(@PathVariable("id") Long id, @RequestBody Empresa empresa) {
        if (repositoryEmpresa.findById(id).isPresent()) {
            empresa.setId(id);
            repositoryEmpresa.save(empresa);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteEmpresa(@PathVariable("id") Long id) {
        if (repositoryEmpresa.findById(id).isPresent()) {
            repositoryEmpresa.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
