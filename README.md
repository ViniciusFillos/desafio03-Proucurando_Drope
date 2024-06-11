## Desafio 03: Microsserviços para gerenciar processos de tomada de decisões em uma empresa

### Time Proucurando_Drope

* **Keren Apuque Cardoso de Morais** - keren.morais.pb@compasso.com.br
* **Nicole Inaê de Oliveira** - nicole.oliveira.pb@compasso.com.br
* **Vinicius Ruan Fillos** - vinicius.fillos.pb@compasso.com.br

### Objetivos

O objetivo deste projeto é construir um sistema de backend que permite que equipes internas proponham e votem em diversas propostas de melhorias. O sistema será composto por três microsserviços, um servidor de descoberta (Discovery Server) e um gateway:

1. **Microsserviço de Gestão de Funcionários:**
   - Cadastrar Funcionário →  [POST] /api/v1/funcionarios.
   - Editar Funcionários →  [PUT] /api/v1/funcionarios/alterar/{id}. 
   - Buscar Funcionário   →  [GET] /api/v1/funcionarios/{id}.
   - Buscar Todos Funcionários →  [GET] /api/v1/funcionarios.
   - Deletar Funcionário → [DELETE]  /api/v1/funcionarios{id}.
---
2. **Microsserviço de Gestão de Propostas:**
   - Cadastrar Proposta → [POST]  /api/v1/propostas.
   - Buscar Proposta → [GET] /api/v1/propostas/{id}.
   - Buscar Todas Propostas → [GET] /api/v1/propostas.
   - Editar Proposta → [PUT] /api/v1/propostas/{id}.
   - Deletar Proposta → [DELETE] /api/v1/propostas/{id}.
   - Iniciar Votação → [POST] /api/v1/propostas/votacao/{id}?limite={minutos}.
   - Votar → [POST]  /api/v1/propostas/votar.
---
3. **Microsserviço de Gestão de Votação:**
   - Encerrar Votação → [POST] /api/v1/votacao/encerrar.
   - Buscar Todas Votações → [GET]  /api/v1/votacao.
---
4. **Eureka Server:**
   * Registrar todos os Microserviços.
---
5. **Gateway:**
   * Load Balancer.

### Estrutura de Branches

* **main:** Branch principal, onde **apenas** as alterações validadas serão mescladas.
* **dev:** Branch de desenvolvimento, onde as funcionalidades serão implementadas.
* **Branches de tasks:** Seguindo o padrão `[nome-do-microserviço]/[feature]-[nome_da_funcionalidade]`, por exemplo: `msFucionario/feature-cadastrar_funcionario`.

### Tipos de Commit

* **docs:** Alterações na documentação.
* **feat:** Implementação de novas funcionalidades.
* **fix:** Correção de bugs.
* **perf:** Melhorias de performance.
* **refactor:** Reorganização do código sem afetar funcionalidades ou corrigir bugs.
* **style:** Formatação e organização do código (espaços em branco, etc.).
* **test:** Adição ou correção de testes.

### Para rodar o Projeto 
1. Na pasta docker abra o terminal e digite o seguinte comando: ` docker-compose up `
2. Na pasta eurekaSerever rodar a classe principal
3. Fazer o mesmo com os três outros microserviços 
4. Rodar a classe principal da pasta gateway
5. Abrir a collection do Postman deixada na pasta raiz

Com o sistema todo no ar é possível registrar uma proposta ou funcionário, com uma porposta registrada é possível iniciar uma votação passando um tempo limite sendo o padrão de 1 minuto, onde cada funcionário tem direito a um voto e, com uma votação ativa é possível encerra-la mostrando o resultado da votação.

### Links para acesso a documentação no Swagger
* Micro Service Funcionários - http://localhost:[porta]/docs-msFuncionarios.html
* Micro Service  Votação - http://localhost:[porta]/docs-msVotacao.html
* Micro Service  Propostas - http://localhost:[porta]/docs-msProposta.html

### Links para acessar o Actuator
* Micro Service Funcionários - http://localhost:[porta]/actuator/metrics
* Micro Service  Votação - http://localhost:[porta]/actuator/metrics
* Micro Service  Propostas - http://localhost:[porta]/actuator/metrics

Ao executar nossos micro serviços baseados em Spring Boot, você notará que a “[porta]” é gerada de forma aleatória toda vez que o serviço é iniciado. Essa abordagem é útil para evitar conflitos de porta quando vários serviços estão sendo executados simultaneamente.

Para encontrar o número da porta atribuído ao micro serviço, você pode seguir a seguinte opção:

**Eureka Discovery Service:**
Acesse o painel do Eureka em http://localhost:8761/
Lá, você encontrará uma lista de todos os micro serviços registrados, incluindo seus nomes e portas.
Localize o micro serviço específico (por exemplo, “Micro Service Funcionários”) e anote o número da porta associado a ele.

### Próximas atualizações
* **Versionamento:** para as próximas versões do projeto mudaríamos a url (/v1) e já temos algumas ideias para implementar.
* Adicionar Flyway ao projeto para também versionar os bancos de dados.
* Implementar logs nos endpoints de cada microserviço e um log com o resultado da votação a ser enviado para os outros microserviços.
* A quantidade de votos estão sendo persistidos no resultado da votação, mas sem o detalhamento de cada voto.
### Relatório de Desenvolvimento do Projeto

Realizamos calls de squad regularmente ao longo do processo de implementação, discutimos os requisitos, chegamos em um consenso quanto a lógica de implementação, planejamos as tarefas e revisamos o progresso do desenvolvimento. Durante essas reuniões, Keren, Nicole e Vinicius contribuíram com ideias e sugestões, tornando o processo de implementação colaborativo.

Testamos os endpoints utilizando o Postman, garantindo que todas as funcionalidades estivessem corretamente implementadas e que os resultados estivessem de acordo com o esperado. Durante esse processo, identificamos áreas que precisavam de ajustes e refinamentos, e realizamos essas correções. Keren e Vinicios foram responsáveis pela escrita dos testes automatizados, colaborando um com o outro para garantir uma cobertura abrangente dos casos de teste e a qualidade dos testes implementados. Nicole realizou a documentação do projeto através do Swagger.

Ao longo do desenvolvimento surgiam dúvidas e dificuldades, recorremos uns aos outros para encontrar soluções e garantir que o desenvolvimento continuasse fluindo sem contratempos significativos. Para resolver problemas específicos, consultamos recursos online e quando necessário, solicitamos apoio dos instrutores, que nos forneceram orientações e sugestões para superar os obstáculos encontrados.

Consideramos que nosso progresso foi significativo, conseguimos colocar em pratica o que aprendemos nos cursos e estamos felizes e orgulhosos dos resultados alcançados pela squad.