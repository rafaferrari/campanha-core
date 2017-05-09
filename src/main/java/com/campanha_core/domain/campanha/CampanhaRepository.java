package com.campanha_core.domain.campanha;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositório de Campanha
 * 
 * @author rafael.ferrari
 */
public interface CampanhaRepository extends CrudRepository<Campanha, Long> {

    /**
     * Busca as Campanhas Vigentes.
     * 
     * @param date - Data corrente para filtro.
     * @return As Campanhas se encontradas.
     */
    List<Campanha> findByDataFinalGreaterThanEqual(final LocalDate date);

    /**
     * Busca as Campanhas por filtro de Data Inicial e Data Final.
     * 
     * @param dataInicial - Data Inicial da Campanha.
     * @param dataFinal - Data Final da Campanha.
     * @return As Campanhas se encontradas.
     */
    List<Campanha> findByDataInicialGreaterThanEqualAndDataFinalLessThanEqual(final LocalDate dataInicial, final LocalDate dataFinal);

    /**
     * Busca as Campanhas Vigentes pelo ID do Time do Coração.
     * 
     * @param date - Data Corrente para filtro.
     * @param idTimeCoracao - ID do Time do Coração.
     * @return As Campanhas se encontradas.
     */
    List<Campanha> findByDataFinalGreaterThanEqualAndIdTimeCoracao(final LocalDate date, final Long idTimeCoracao);

}
