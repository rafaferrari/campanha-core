package com.processo_seletivo_core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.processo_seletivo_core.domain.campanha.Campanha;
import com.processo_seletivo_core.domain.campanha.CampanhaService;
import com.processo_seletivo_core.domain.exceptions.ServiceException;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/campanhas")
public class CampanhaController {

    @Autowired
    private CampanhaService campanhaService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Campanha>> findAll() throws ServiceException {
        final Iterable<Campanha> campanhas = campanhaService.findAll();
        if (campanhas.spliterator().getExactSizeIfKnown() <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(campanhas, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = "idTimeCoracao")
    public ResponseEntity<Iterable<Campanha>> findAllByTimeCoracao(@RequestParam final Long idTimeCoracao) throws ServiceException {
        final Iterable<Campanha> campanhas = campanhaService.findAll(idTimeCoracao);
        if (campanhas.spliterator().getExactSizeIfKnown() <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(campanhas, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Campanha> findById(@PathVariable final Long id) throws ServiceException {
        final Optional<Campanha> campanha = campanhaService.findOne(id);
        if (campanha.isPresent()) {
            return new ResponseEntity<>(campanha.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody final Campanha campanha, final UriComponentsBuilder ucBuilder) throws ServiceException {
        campanhaService.save(campanha);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/campanhas/{id}").buildAndExpand(campanha.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Campanha> update(@PathVariable("id") final Long id, @RequestBody final Campanha campanha) throws ServiceException {
        final Optional<Campanha> currentCampanha = campanhaService.findOne(id);
        if (currentCampanha.isPresent()) {
            currentCampanha.get().setNome(campanha.getNome());

            campanhaService.save(currentCampanha.get());

            return new ResponseEntity<>(currentCampanha.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Campanha> delete(@PathVariable("id") final Long id) throws ServiceException {
        final Optional<Campanha> currentCampanha = campanhaService.findOne(id);
        if (currentCampanha.isPresent()) {
            campanhaService.delete(currentCampanha.get().getId());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
