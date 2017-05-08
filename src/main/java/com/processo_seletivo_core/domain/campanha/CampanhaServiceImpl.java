package com.processo_seletivo_core.domain.campanha;

import com.processo_seletivo_core.domain.exceptions.ServiceException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampanhaServiceImpl implements CampanhaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final UnaryOperator<LocalDate> PLUS_DAY = date -> date.plus(1, ChronoUnit.DAYS);

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Override
    public Iterable<Campanha> findAll() throws ServiceException {
        try {
            return campanhaRepository.findByDataFinalGreaterThanEqual(LocalDate.now());
        } catch (final Exception e) {
            logger.error("Erro ao buscar Campanhas.");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Iterable<Campanha> findAll(final Long idTimeCoracao) throws ServiceException {
        try {
            return campanhaRepository.findByDataFinalGreaterThanEqualAndIdTimeCoracao(LocalDate.now(), idTimeCoracao);
        } catch (final Exception e) {
            logger.error("Erro ao buscar Campanhas pelo ID do Time Coracao.");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<Campanha> findOne(final Long id) throws ServiceException {
        try {
            return Optional.ofNullable(campanhaRepository.findOne(id));
        } catch (final Exception e) {
            logger.error("Erro ao buscar Campanha por ID.");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(final Long id) throws ServiceException {
        try {
            campanhaRepository.delete(id);
        } catch (final Exception e) {
            logger.error(String.format("Erro ao Remover uma Campanha pelo ID: %s", id));
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<Campanha> save(final Campanha campanha) throws ServiceException {
        try {
            validateCampanhasVigentes(campanha);
            return Optional.of(campanhaRepository.save(campanha));
        } catch (final Exception e) {
            logger.error("Erro ao Salvar/Inserir uma Campanha.");
            throw new ServiceException(e.getMessage());
        }
    }

    private void validateCampanhasVigentes(final Campanha campanha) {
        final List<Campanha> campanhasVigentes = campanhaRepository
                .findByDataInicialGreaterThanEqualAndDataFinalLessThanEqual(campanha.getDataInicial(), campanha.getDataFinal());
        if (campanhasVigentes.size() > 0) {
            campanhasVigentes.add(campanha);
            atualizarDataFinalCampanhas(campanhasVigentes);
        }
    }

    private void atualizarDataFinalCampanhas(final List<Campanha> campanhasVigentes) {
        final Iterator<Campanha> iterator = campanhasVigentes.stream().iterator();
        iterator.forEachRemaining(campanhaConsumer -> {
            if (iterator.hasNext()) {
                campanhaConsumer.setDataFinal(PLUS_DAY.apply(campanhaConsumer.getDataFinal()));
                inserirDiaDataFinal(campanhasVigentes, campanhaConsumer);
                campanhaRepository.save(campanhaConsumer);
            }
        });
    }

    private void inserirDiaDataFinal(final List<Campanha> campanhasVigentes, final Campanha campanha) {
        final Optional<Campanha> campanhaResultado = campanhasVigentes.parallelStream()
                .filter(c -> c.getDataFinal().compareTo(campanha.getDataFinal()) == 0 && c.getId().compareTo(campanha.getId()) != 0)
                .findFirst();
        if (campanhaResultado.isPresent()) {
            campanha.setDataFinal(PLUS_DAY.apply(campanha.getDataFinal()));
            inserirDiaDataFinal(campanhasVigentes, campanha);
        }
    }

}
