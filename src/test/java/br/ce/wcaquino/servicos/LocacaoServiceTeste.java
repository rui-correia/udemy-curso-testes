package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.daos.LocacaoDaoFake;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.*;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHojeComDiferencaDias;

public class LocacaoServiceTeste {

    private LocacaoService locacaoService;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        locacaoService = new LocacaoService();
        LocacaoDAO locacaoDAO = new LocacaoDaoFake();
        locacaoService.setLocacaoDAO(locacaoDAO);
    }

    @BeforeClass
    public static void setupClass() {
        System.out.println("Inicializando a classe de testes.");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Finalizando a classe de testes.");
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
        // GIVEN
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = new ArrayList<>();
        filmes.add(umFilme().comValor(5.0).agora());

        // WHEN
        Locacao locacao;
        locacao = locacaoService.alugarFilme(usuario, filmes);

        // THEN
        //errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
        //errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));
        errorCollector.checkThat(locacao.getValor(), CoreMatchers.is(5.0));
        errorCollector.checkThat(locacao.getDataLocacao(), ehHoje());
        errorCollector.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {
        // GIVEN
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = new ArrayList<>();
        filmes.add(umFilme().semEstoque().agora());

        // THEN
        locacaoService.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemEstoque() throws FilmeSemEstoqueException {
        // GIVEN
        List<Filme> filmes = new ArrayList<>();
        filmes.add(umFilmeSemEstoque().agora());

        // WHEN
        try {
            locacaoService.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario vazio"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {

        // GIVEN
        List<Filme> filmes = new ArrayList<>();
        Usuario usuario = umUsuario().agora();
        expectedException.expect(LocadoraException.class);
        expectedException.expectMessage("Filme vazio");
        // WHEN
        locacaoService.alugarFilme(usuario, filmes);
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //given
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        //when
        Locacao retorno = locacaoService.alugarFilme(usuario, filmes);

        // then
        Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
    }

}