package com.campanha_core.controller;

import com.campanha_core.domain.campanha.Campanha;
import com.campanha_core.domain.campanha.CampanhaService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CampanhaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CampanhaService campanhaService;

    @Test
    public void test_deve_retornar_no_content_quando_nao_existir_campanhas() throws Exception {
        final Iterable<Campanha> campanhas = new ArrayList<>();
        when(campanhaService.findAll()).thenReturn(campanhas);

        mvc.perform(MockMvcRequestBuilders.get("/campanhas")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_deve_retornar_as_campanhas_cadastradas() throws Exception {
        final List<Campanha> campanhas = new ArrayList<>();
        campanhas.add(populate("Campanha", LocalDate.now(), LocalDate.now().plusDays(3)).get());
        when(campanhaService.findAll()).thenReturn(campanhas);

        mvc.perform(MockMvcRequestBuilders.get("/campanhas")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deve_retornar_no_content_quando_nao_existir_campanhas_pelo_id_time_coracao() throws Exception {
        final Iterable<Campanha> campanhas = new ArrayList<>();
        when(campanhaService.findAll(5L)).thenReturn(campanhas);

        mvc.perform(MockMvcRequestBuilders.get("/campanhas?idTimeCoracao=5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_deve_retornar_as_campanhas_cadastradas_pelo_id_time_coracao() throws Exception {
        final List<Campanha> campanhas = new ArrayList<>();
        campanhas.add(populate("Campanha", LocalDate.now(), LocalDate.now().plusDays(3)).get());
        when(campanhaService.findAll(1L)).thenReturn(campanhas);

        mvc.perform(MockMvcRequestBuilders.get("/campanhas?idTimeCoracao=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deve_retornar_no_content_quando_nao_existir_campanha_por_id() throws Exception {
        final Long id = 0L;
        when(campanhaService.findOne(id)).thenReturn(Optional.ofNullable(null));

        mvc.perform(MockMvcRequestBuilders.get("/campanhas/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_deve_retornar_a_campanha_por_id() throws Exception {
        final Long id = 1L;
        final Optional<Campanha> campanha = populate("Campanha", LocalDate.now(), LocalDate.now().plusDays(3));
        when(campanhaService.findOne(id)).thenReturn(campanha);

        mvc.perform(MockMvcRequestBuilders.get("/campanhas/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deve_inserir_uma_campanha() throws Exception {
        final Optional<Campanha> campanha = populate("Campanha", LocalDate.now(), LocalDate.now().plusDays(3));
        when(campanhaService.save(campanha.get())).thenReturn(campanha);

        mvc.perform(MockMvcRequestBuilders.post("/campanhas")
                .content(new JSONObject("{'nome': 'teste'}").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test_deve_retornar_bad_request_quando_executar_post_sem_corpo_requisicao() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/campanhas")
                .content("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_deve_atualizar_uma_campanha() throws Exception {
        final Optional<Campanha> campanha = populate("Campanha", LocalDate.now(), LocalDate.now().plusDays(3));
        when(campanhaService.save(campanha.get())).thenReturn(campanha);
        when(campanhaService.findOne(1L)).thenReturn(campanha);

        mvc.perform(MockMvcRequestBuilders.put("/campanhas/1")
                .content(new JSONObject("{'nome': 'teste'}").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deve_retornar_not_found_quando_id_informado_nao_existir_ao_atualizar() throws Exception {
        final Optional<Campanha> campanha = populate("Campanha", LocalDate.now(), LocalDate.now().plusDays(3));
        when(campanhaService.findOne(2L)).thenReturn(Optional.ofNullable(null));

        mvc.perform(MockMvcRequestBuilders.put("/campanhas/2")
                .content(new JSONObject("{'nome': 'teste'}").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_deve_retornar_bad_request_quando_executar_put_sem_corpo_requisicao() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/campanhas/1")
                .content("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_deve_remover_uma_campanha() throws Exception {
        final Optional<Campanha> campanha = populate("Campanha", LocalDate.now(), LocalDate.now().plusDays(3));
        when(campanhaService.findOne(1L)).thenReturn(campanha);

        mvc.perform(MockMvcRequestBuilders.delete("/campanhas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_deve_retornar_not_found_quando_id_informado_nao_existir_ao_remover() throws Exception {
        when(campanhaService.findOne(2L)).thenReturn(Optional.ofNullable(null));

        mvc.perform(MockMvcRequestBuilders.delete("/campanhas/2"))
                .andExpect(status().isNotFound());
    }

    private Optional<Campanha> populate(final String nomeCampanha, final LocalDate dataInicial, final LocalDate dataFinal) {
        final Campanha campanha = new Campanha();
        campanha.setIdTimeCoracao(1L);
        campanha.setNome(nomeCampanha);
        campanha.setDataInicial(dataInicial);
        campanha.setDataFinal(dataFinal);
        return Optional.of(campanha);
    }

}
