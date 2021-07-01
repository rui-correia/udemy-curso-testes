package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoService {

    private LocacaoDAO locacaoDAO;

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

        double totalLocacao = 0.00;

        if (usuario == null) {
            throw new LocadoraException("Usuario vazio");
        }

        if (filmes.isEmpty()) {
            throw new LocadoraException("Filme vazio");
        }

        //for (Filme filme : filmes) {
        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            Double valorDoFilme = filme.getPrecoLocacao();
            if (filme.getEstoque() == 0) {
                throw new FilmeSemEstoqueException();
            }

            switch (i) {
                case 2:
                    valorDoFilme = valorDoFilme * 0.75;
                    break;
                case 3:
                    valorDoFilme = valorDoFilme * 0.5;
                    break;
                case 4:
                    valorDoFilme = valorDoFilme * 0.25;
                    break;
                case 5:
                    valorDoFilme = valorDoFilme * 0d;
                    break;
            }
            totalLocacao += valorDoFilme;
        }


        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        //locacao.setFilme(filme);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(totalLocacao);

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)){
            dataEntrega = adicionarDias(dataEntrega, 1);
        }
        locacao.setDataRetorno(dataEntrega);

        locacaoDAO.salvar(locacao);

        return locacao;
    }
    public void setLocacaoDAO(LocacaoDAO locacaoDAO){
        this.locacaoDAO = locacaoDAO;
    }
}