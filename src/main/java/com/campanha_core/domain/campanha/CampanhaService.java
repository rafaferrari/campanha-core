package com.campanha_core.domain.campanha;

import com.campanha_core.domain.exceptions.ServiceException;
import java.util.Optional;

public interface CampanhaService {

    Iterable<Campanha> findAll() throws ServiceException;

    Iterable<Campanha> findAll(final Long idTimeCoracao) throws ServiceException;

    Optional<Campanha> findOne(final Long id) throws ServiceException;

    Optional<Campanha> save(final Campanha campanha) throws ServiceException;

    void delete(final Long id) throws ServiceException;

}
