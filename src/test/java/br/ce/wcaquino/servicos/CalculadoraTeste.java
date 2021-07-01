package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTeste {

    private Calculadora calculadora;

    @Before
    public void setup() {
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores() {
        //given
        int a = 5;
        int b = 3;

        //when
        int resultado = calculadora.somar(a, b);

        //then
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        //given
        int a = 8;
        int b = 5;

        //when
        int resultado = calculadora.subtrair(a, b);

        //then
        Assert.assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {

        //given
        int a = 6;
        int b = 3;

        //when
        int resultado = calculadora.dividir(a, b);

        //then
        Assert.assertEquals(2, resultado);

    }


    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcessaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {

        //given
        int a = 10;
        int b = 0;

        //when
        int resultado = calculadora.dividir(a, b);

        //then
        Assert.assertEquals(2, resultado);

    }
}
