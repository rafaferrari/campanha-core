package com.campanha_core.domain.campanha;

import com.campanha_core.domain.exceptions.ServiceException;
import java.util.Optional;

/**
 * Service de Campanha.
 * 
 * @author rafael.ferrari
 */
public interface CampanhaService {

    /**
     * Busca todas as Campanhas vigentes.
     * 
     * @return As Campanhas se encontradas.
     * @throws ServiceException .
     */
    Iterable<Campanha> findAll() throws ServiceException;

    /**
     * Busca todas as Campanhas vigentes pelo ID do Time do Coração.
     * 
     * @param idTimeCoracao - ID do Time do Coração à ser pesquisado.
     * @return As Campanhas se encontrado.
     * @throws ServiceException .
     */
    Iterable<Campanha> findAll(final Long idTimeCoracao) throws ServiceException;

    /**
     * Busca uma Campanha pelo ID.
     * 
     * @param id - ID da Campanha à ser pesquisada.
     * @return Campanha se encontrada.
     * @throws ServiceException .
     */
    Optional<Campanha> findOne(final Long id) throws ServiceException;

    /**
     * Salva/Atualiza uma Campanha.
     * 
     * @param campanha - Campanha à ser Salva/Atualizada.
     * @return Campanha.
     * @throws ServiceException .
     */
    Optional<Campanha> save(final Campanha campanha) throws ServiceException;

    /**
     * Remove uma campanha pelo ID.
     * 
     * @param id - ID da Campanha à ser removida.
     * @throws ServiceException .
     */
    void delete(final Long id) throws ServiceException;

}
