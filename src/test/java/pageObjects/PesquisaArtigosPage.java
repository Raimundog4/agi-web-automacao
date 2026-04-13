package pageObjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.Utils.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PesquisaArtigosPage {

	public PesquisaArtigosPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	private String termoPesquisa;

	@FindBy(xpath = "//span[@class='ast-icon icon-search icon-search']")
	private WebElement botaoLupa;

	@FindBy(css = "img.custom-logo")
	private WebElement logomarcaBlog;

	@FindBy(css = "input.search-field")
	private WebElement campoPesquisa;
	
	@FindBy(id = "search_submit")
	private WebElement botaoPesquisa;

	@FindBy(xpath = "//*[contains(text(), 'Resultados encontrados para')]")
	private WebElement textoResultadosEncontrados;
	
	@FindBy(css = "article")
	private List<WebElement> resultadosBusca;
	
	@FindBy(xpath = "//section[@class='no-results not-found']//p")
	private WebElement campoTextoResultadoNaoEncontrado;
	
	@FindBy(id = "search-field-sticky")
	private WebElement campoInputNovaPesquisa;

	public void validarPaginaInicialCarregada() {
		esperarPaginaCarregar();
		if (!elementoVisivel(botaoLupa, 5)) {
            refreshComEspera();
        }
        assertTrue("A logomarca do blog não foi exibida.", elementoVisivel(logomarcaBlog));
        assertTrue("O ícone de busca não foi exibido no canto superior direito.", elementoVisivel(botaoLupa));
	}

	public void acionarBotaoLupa() {
		esperarPorElementoClicavel(botaoLupa);
		botaoLupa.click();
		if (!elementoVisivel(campoPesquisa, 5)) {
			esperarPorElementoClicavel(botaoLupa);
			botaoLupa.click();
		}
	}

	public void preencherCampoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
		esperarPorElemento(campoPesquisa);
		campoPesquisa.sendKeys(termoPesquisa);
	}

	public void realizarPesquisa() {
		botaoPesquisa.click();
	}

	public void validarPaginaResultados() {
		if (paginaComErro429()) {
            throw new AssertionError("A aplicação retornou 429 - Too Many Requests.");
		}
		assertTrue("A página de resultados não foi carregada!", 
				elementoVisivel(textoResultadosEncontrados));
		assertTrue("O termo pesquisado não foi exibido no cabeçalho dos resultados.",textoResultadosEncontrados.findElement(By.xpath("//span[text()='" + termoPesquisa + "']")).isDisplayed());;
	}
	
	public void validarQueExistemResultados() {
		/*
		 * Uso esse método para primeiro aguardar os resultados entrarem no DOM e pelo menos um item ficar visível.
		 * Depois valido se realmente existe artigo visível na tela.
		 */
		validarCarregamentoEVisibilidade(resultadosBusca, 10);
		descerScroll(resultadosBusca.get(0));
	}
	
	public void validarMensagemResultadoNaoEncontrado(String mensagem) {
		esperarPorElemento(campoTextoResultadoNaoEncontrado);
	    assertEquals("A mensagem de nenhum resultado encontrado está incorreta.",
	            mensagem, campoTextoResultadoNaoEncontrado.getText());
	    descerScroll(campoTextoResultadoNaoEncontrado);
	}
	
	public void validarCampoParaNovaBusca() {
		assertTrue("O campo para nova pesquisa não está visível.",
				elementoVisivel(campoInputNovaPesquisa));
		descerScroll(campoInputNovaPesquisa);
	}
}
