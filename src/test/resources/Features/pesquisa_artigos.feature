#language: pt
#Author: José Raimundo
@PesquisaArtigos
Funcionalidade: Pesquisa de artigos no Blog do Agi
  
  Contexto: 
    Dado que o usuário tenha acessado a página inicial do Blog do Agi

  Cenário: Pesquisar artigo com termo válido
    Quando o usuário clicar no ícone de pesquisa
    E informar o termo "cartão"
    E executar a pesquisa
    Então o sistema deve exibir a página de resultados contendo o termo pesquisado
    E deve apresentar ao menos um artigo relacionado ao termo pesquisado

  Cenário: Pesquisar artigo com termo inexistente
    Quando o usuário clicar no ícone de pesquisa
    E informar o termo "termo inexistente - teste"
    E executar a pesquisa
    Então o sistema deve exibir a página de resultados contendo o termo pesquisado
    E deve apresentar a mensagem "Lamentamos, mas nada foi encontrado para sua pesquisa, tente novamente com outras palavras."
    E deve apresentar um campo de pesquisa para uma nova busca
