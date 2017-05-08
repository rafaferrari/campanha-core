package com.processo_seletivo_core.domain.campanha;

import com.processo_seletivo_core.domain.exceptions.ServiceException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CampanhaServiceTest {

    @Autowired
    private CampanhaService campanhaService;

    @Before
    public void setup() throws ServiceException {
        campanhaService.save(populate("Campanha 1", LocalDate.of(2017, Month.JANUARY, 1), LocalDate.of(2017, Month.JANUARY, 3)));
        campanhaService.save(populate("Campanha 2", LocalDate.of(2017, Month.JANUARY, 1), LocalDate.now()));
        campanhaService.save(populate("Campanha 3", LocalDate.now(), LocalDate.now().plusDays(3)));
    }

    private Campanha populate(final String nomeCampanha, final LocalDate dataInicial, final LocalDate dataFinal) {
        final Campanha campanha = new Campanha();
        campanha.setIdTimeCoracao(1L);
        campanha.setNome(nomeCampanha);
        campanha.setDataInicial(dataInicial);
        campanha.setDataFinal(dataFinal);
        return campanha;
    }

    @Test
    public void test_deve_buscar_todas_as_campanhas_correntes() throws ServiceException {
        // GIVEN  
        final Long total = 2L;

        // WHEN
        final Iterable<Campanha> campanhas = campanhaService.findAll();

        // THEN
        assertThat(campanhas.spliterator().getExactSizeIfKnown()).isEqualTo(total);
    }

    @Test
    public void test_deve_buscar_todas_as_campanhas_correntes_pelo_id_time_coracao() throws ServiceException {
        // GIVEN  
        final Long idTimeCoracao = 1L;

        // WHEN
        final Iterable<Campanha> campanhas = campanhaService.findAll(idTimeCoracao);

        // THEN
        assertThat(campanhas.spliterator().getExactSizeIfKnown()).isEqualTo(2L);
    }

    @Test
    public void test_deve_buscar_uma_campanha_por_id() throws ServiceException {
        // GIVEN 
        final Long id = 1L;

        // WHEN
        final Optional<Campanha> campanha = campanhaService.findOne(id);

        // THEN
        assertThat(campanha.isPresent()).isEqualTo(true);
        assertThat(campanha.get().getNome()).isEqualTo("Campanha 1");
    }

    @Test
    public void test_deve_salvar_uma_campanha() throws ServiceException {
        // GIVEN
        final Optional<Campanha> campanha = campanhaService.save(populate("Campanha 1", LocalDate.now(), LocalDate.now().plusDays(3)));

        // WHEN
        final Optional<Campanha> resultado = campanhaService.save(campanha.get());
        final Optional<Campanha> campanhaVigente = campanhaService.findOne(3L);

        // THEN
        assertThat(resultado.isPresent()).isEqualTo(true);
        assertThat(resultado.get().getNome()).isEqualTo(campanha.get().getNome());
        assertThat(campanhaVigente.isPresent()).isEqualTo(true);
        assertThat(campanhaVigente.get().getDataFinal().compareTo(resultado.get().getDataFinal().plusDays(1))).isEqualTo(0);
    }

    @Test(expected = ServiceException.class)
    public void test_deve_lancar_excecao_ao_salvar_uma_campanha_sem_time_coracao() throws ServiceException {
        // GIVEN
        final Campanha campanha = new Campanha();
        campanha.setNome("Teste");
        campanha.setDataInicial(LocalDate.now());
        campanha.setDataFinal(LocalDate.now());

        // WHEN
        campanhaService.save(campanha);

        // THEN 
        // Catch the ServiceException
    }

    @Test(expected = ServiceException.class)
    public void test_deve_lancar_excecao_ao_salvar_uma_campanha_sem_nome() throws ServiceException {
        // GIVEN
        final Campanha campanha = new Campanha();
        campanha.setDataInicial(LocalDate.now());
        campanha.setDataFinal(LocalDate.now());

        // WHEN
        campanhaService.save(campanha);

        // THEN 
        // Catch the ServiceException
    }

    //TODO
    // @Test(expected = ServiceException.class)
    public void test_deve_lancar_excecao_ao_salvar_uma_campanha_sem_data_inicial() throws ServiceException {
        // GIVEN
        final Campanha campanha = new Campanha();
        campanha.setNome("Teste");
        campanha.setDataFinal(LocalDate.now());

        // WHEN
        campanhaService.save(campanha);

        // THEN 
        // Catch the ServiceException
    }

    // TODO
    //@Test(expected = ServiceException.class)
    public void test_deve_lancar_excecao_ao_salvar_uma_campanha_sem_data_final() throws ServiceException {
        // GIVEN
        final Campanha campanha = new Campanha();
        campanha.setNome("Teste");
        campanha.setDataInicial(LocalDate.now());

        // WHEN
        campanhaService.save(campanha);

        // THEN 
        // Catch the ServiceException
    }

    @Test
    public void test_deve_atualizar_uma_campanha() throws ServiceException {
        // GIVEN
        final Long id = 1L;
        final Optional<Campanha> campanha = campanhaService.findOne(id);
        campanha.get().setNome("Campanha Atualizada");

        // WHEN
        final Optional<Campanha> resultado = campanhaService.save(campanha.get());

        // THEN
        assertThat(resultado.isPresent()).isEqualTo(true);
        assertThat(resultado.get().getNome()).isEqualTo(campanha.get().getNome());
    }

    @Test
    public void test_deve_remover_uma_campanha() throws ServiceException {
        // GIVEN
        final Long id = 2L;

        // WHEN
        campanhaService.delete(id);
        final Iterable<Campanha> campanhas = campanhaService.findAll();

        // THEN
        assertThat(campanhas.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test(expected = ServiceException.class)
    public void test_deve_retornar_erro_ao_remover_uma_campanha_com_id_inexistente() throws ServiceException {
        // GIVEN
        final Long id = 5L;

        // WHEN
        campanhaService.delete(id);

        // THEN
        //Catch the ServiceException
    }

}
