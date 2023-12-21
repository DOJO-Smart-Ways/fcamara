package br.com.backendtestjava.backendtestjava.endpoint;

import br.com.backendtestjava.backendtestjava.entity.Veiculo;
import br.com.backendtestjava.backendtestjava.respository.RepositoryVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class    EndpointVeiculo {

    @Autowired
    private RepositoryVeiculo repositoryVeiculo;

    @GetMapping
    public Page<Veiculo> listAll(Pageable pageable) {
        return repositoryVeiculo.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity addVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo.getId() != null) {
            veiculo.setId(null);
        }
        repositoryVeiculo.save(veiculo);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity editVeiculo(@PathVariable("id") Long id,@RequestBody Veiculo veiculo) {
        if (repositoryVeiculo.findById(id).isPresent()) {
            veiculo.setId(id);
            repositoryVeiculo.save(veiculo);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteVeiculo(@PathVariable("id") Long id) {
        if (repositoryVeiculo.findById(id).isPresent()) {
            repositoryVeiculo.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
