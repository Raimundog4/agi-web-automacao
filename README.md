# Testes automatizados - Blog do Agi

Projeto de automação web desenvolvido como parte de um desafio técnico, com foco na funcionalidade de pesquisa de artigos do Blog do Agi.

A proposta foi validar os principais fluxos da busca, cobrindo cenários positivos e negativos, com uma estrutura organizada, legível e de fácil manutenção.

------------------------------------------------------------------------

## Objetivo

Validar o comportamento da pesquisa de artigos no Blog do Agi, considerando:

- busca com retorno de resultados
- busca sem retorno de resultados
- possibilidade de realizar uma nova busca após uma pesquisa sem sucesso

------------------------------------------------------------------------

## Tecnologias utilizadas

- Java 17
- Selenium WebDriver
- Cucumber
- JUnit 4
- Maven
- WebDriverManager
- Allure Report
- GitHub Actions

------------------------------------------------------------------------

## Estrutura do projeto

A estrutura do projeto foi organizada da seguinte forma:

- `pageObjects` → mapeamento dos elementos e ações da página
- `steps` → implementação dos passos dos cenários
- `utils` → configuração do driver, waits e métodos utilitários
- `hooks` → setup e teardown dos testes
- `features` → cenários escritos em Gherkin
- `.github/workflows` → configuração da execução em CI

------------------------------------------------------------------------

## Cenários cobertos

Foram automatizados os seguintes cenários:

- busca com termo válido retornando resultados
- busca com termo inexistente exibindo mensagem adequada
- possibilidade de realizar uma nova busca após não encontrar resultados

A escolha desses cenários foi feita com base na relevância funcional da busca e na necessidade de validar tanto o fluxo principal quanto comportamentos alternativos do usuário.

------------------------------------------------------------------------

## Clonar o repositório

Para clonar o projeto:

```bash
git clone https://github.com/Raimundog4/agi-web-automacao.git
cd agi-web-automacao
```

------------------------------------------------------------------------

## Como executar localmente

Para rodar os testes:

```bash
mvn clean test
```

Para executar os testes com geração completa dos relatórios:

```bash
mvn clean verify
```

Também é possível executar diretamente a classe runner:

```text
WebTestRunner
```

------------------------------------------------------------------------

## Relatórios

### Cucumber Report

Após a execução local, os relatórios do Cucumber ficam disponíveis em:

```text
target/cucumber-reports/
target/cucumber-report/
```

------------------------------------------------------------------------

### Allure Report

O projeto está configurado para geração de resultados do Allure durante a execução dos testes.

Relatório publicado no GitHub Pages:

```text
https://raimundog4.github.io/agi-web-automacao/
```

Após a execução local, também é possível gerar o HTML do Allure com:

```bash
mvn allure:report
```

O relatório HTML será gerado em:

```text
target/site/allure-maven-plugin
```

------------------------------------------------------------------------

## Integração contínua

O projeto possui integração com GitHub Actions.

A cada `push` ou `pull request` para a branch `main`, a pipeline executa os testes automaticamente, gera os relatórios e publica:

- artefatos de execução no GitHub Actions
- resultados do Allure
- relatório HTML do Allure
- relatório Allure no GitHub Pages
- relatório HTML do Cucumber Reports

Dessa forma, os resultados podem ser analisados sem necessidade de execução local.

------------------------------------------------------------------------

## Observações sobre o sistema testado

Durante a automação, foram identificados comportamentos intermitentes no front-end do Blog do Agi, observados inclusive em determinadas execuções manuais, tais como:

- inconsistência visual no header
- indisponibilidade momentânea do ícone de busca no canto superior direito
- exibição incorreta do campo de busca em posição inesperada
- ocorrência eventual de erro `429 Too Many Requests`

A automação foi mantida validando o comportamento esperado da interface, sem adaptar o teste ao estado incorreto da aplicação. Em casos de inconsistência visual, foi adotada tentativa de recuperação controlada, preservando a confiabilidade dos cenários.

------------------------------------------------------------------------

## Estratégia de automação

Algumas decisões adotadas no projeto:

- uso de waits explícitos para reduzir problemas de sincronização e evitar dependência de Thread.sleep
- utilização do WebDriverManager para gerenciamento automático de drivers, garantindo portabilidade e facilidade de execução
- validação do carregamento visual dos resultados, considerando o estado final renderizado da interface
- geração de evidências de execução por meio de screenshots e relatórios (Cucumber e Allure)

No cenário positivo de pesquisa, optei por não validar que o título do artigo necessariamente contenha o termo pesquisado, pois o sistema pode retornar resultados com base também no conteúdo interno dos artigos. Essa validação poderia gerar falsos negativos e comprometer a confiabilidade do teste.

Para aumentar a robustez das validações, considerei não apenas a presença dos elementos no DOM, mas também o seu estado visual final na interface. Dessa forma, o teste aguarda que ao menos um item esteja efetivamente visível ao usuário (por exemplo, com opacity = 1), garantindo que os resultados foram renderizados corretamente.

Além disso, foram incluídas validações para identificar comportamentos anômalos da aplicação, como respostas 429 Too Many Requests, evitando que falhas de infraestrutura sejam interpretadas como sucesso do fluxo funcional.

A abordagem adotada prioriza a confiabilidade dos testes em detrimento de validações superficiais. Em vez de adaptar os testes para contornar inconsistências da aplicação, os cenários foram construídos para refletir o comportamento esperado do usuário final.

Essa estratégia permitiu identificar problemas reais na aplicação, como:

- elementos presentes no DOM, porém não visíveis ao usuário (opacity = 0)
- inconsistências no comportamento do header e do campo de busca

------------------------------------------------------------------------

## Reporte de bugs

Durante a execução dos testes, foram identificados problemas relevantes na aplicação:

1. Header com quebra de layout
- Expansão inesperada do header
- Ocultação do ícone de busca
- Comportamento intermitente

Detalhes: bugs/Reporte de bug - Header apresenta quebra de layout e oculta ícone de busca após o carregamento da página.pdf

2. Artigos não visíveis (opacity = 0)
- Elementos presentes no DOM
- Porém não visíveis para o usuário
- Indício de falha de renderização/async

Detalhes: bugs/Reporte de Bug - Artigos sendo carregados com opacidade '0'.pdf

------------------------------------------------------------------------

## Autor

José Raimundo
