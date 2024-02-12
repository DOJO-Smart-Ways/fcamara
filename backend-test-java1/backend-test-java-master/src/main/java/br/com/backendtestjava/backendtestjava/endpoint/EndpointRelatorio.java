package br.com.backendtestjava.backendtestjava.endpoint;

import br.com.backendtestjava.backendtestjava.entity.Relatorio;
import br.com.backendtestjava.backendtestjava.respository.RepositoryRelatorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorio")
public class EndpointRelatorio {

    @Autowired
    private RepositoryRelatorio repositoryRelatorio;

    @GetMapping
    public Page<Relatorio> listAll(Pageable pageable) {
        return repositoryRelatorio.findAll(pageable) ;
    }

    @PostMapping
    public ResponseEntity addRelatorio(@RequestBody Relatorio relatorio) {
        if (relatorio.getId() != null) {
            relatorio.setId(null);
        }
        repositoryRelatorio.save(relatorio);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping (path = "/{id}")
    public ResponseEntity editRelatorio(@PathVariable ("id") Long id, @RequestBody Relatorio relatorio) {
        if (repositoryRelatorio.findById(id).isPresent()) {
            relatorio.setId(id);
            repositoryRelatorio.save(relatorio);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping (path = "/{id}")
    public ResponseEntity deleteRelatorio(@PathVariable ("id") Long id) {
        if (repositoryRelatorio.findById(id).isPresent()) {
            repositoryRelatorio.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
