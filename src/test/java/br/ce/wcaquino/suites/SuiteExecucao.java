package br.ce.wcaquino.suites;

import br.ce.wcaquino.servicos.CalculadoraTeste;
import br.ce.wcaquino.servicos.CalculoValorLocacaoTeste;
import br.ce.wcaquino.servicos.LocacaoServiceTeste;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculadoraTeste.class,
        CalculoValorLocacaoTeste.class,
        LocacaoServiceTeste.class
})
public class SuiteExecucao {
    //Remover se possivel
    @BeforeClass
    public static void before() {
        System.out.println("Before");
    }

    @AfterClass
    public static void after() {
        System.out.println("After");
    }
}
