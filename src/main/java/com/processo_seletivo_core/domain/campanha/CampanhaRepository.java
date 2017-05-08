package com.processo_seletivo_core.domain.campanha;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CampanhaRepository extends CrudRepository<Campanha, Long> {

    List<Campanha> findByDataFinalGreaterThanEqual(final LocalDate date);

    List<Campanha> findByDataInicialGreaterThanEqualAndDataFinalLessThanEqual(final LocalDate dataInicial, final LocalDate dataFinal);

    List<Campanha> findByDataFinalGreaterThanEqualAndIdTimeCoracao(final LocalDate date, final Long idTimeCoracao);

}
