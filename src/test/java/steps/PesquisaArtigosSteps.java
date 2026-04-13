package steps;

import static utils.Utils.*;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pageObjects.PesquisaArtigosPage;

public class PesquisaArtigosSteps {
	
	PesquisaArtigosPage paPage = new PesquisaArtigosPage(getDriver());

	@Dado("que o usuário tenha acessado a página inicial do Blog do Agi")
	public void queOUsuárioTenhaAcessadoAPáginaInicialDoBlogDoAgi() {
		paPage.validarPaginaInicialCarregada();
	}

	@Quando("o usuário clicar no ícone de pesquisa")
	public void oUsuárioClicarNoÍconeDePesquisa() {
		paPage.acionarBotaoLupa();
	}

	@Quando("informar o termo {string}")
	public void informarOTermo(String termoPesquisa) {
		paPage.preencherCampoPesquisa(termoPesquisa);
	}

	@Quando("executar a pesquisa")
	public void executarAPesquisa() {
		paPage.realizarPesquisa();
	}

	@Então("o sistema deve exibir a página de resultados contendo o termo pesquisado")
	public void oSistemaDeveExibirAPáginaDeResultadosContendoOTermoPesquisado() {
		paPage.validarPaginaResultados();
	}

	@Então("deve apresentar ao menos um artigo relacionado ao termo pesquisado")
	public void deveApresentarAoMenosUmArtigoRelacionadoAoTermoPesquisado() {
		paPage.validarQueExistemResultados();
	}
	
	@Então("deve apresentar a mensagem {string}")
	public void deveApresentarAMensagem(String mensagem) {
		paPage.validarMensagemResultadoNaoEncontrado(mensagem);
	}

	@Então("deve apresentar um campo de pesquisa para uma nova busca")
	public void deveApresentarUmCampoDePesquisaParaUmaNovaBusca() {
		paPage.validarCampoParaNovaBusca();
	}

}
