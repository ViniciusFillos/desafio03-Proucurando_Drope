## Desafio 03: Microsserviços para gerenciar processos de tomada de decisões em uma empresa

### Time Procurando_Drope

* **Keren Apuque Cardoso de Morais** - keren.morais.pb@compasso.com.br
* **Nicole Inaê de Oliveira** - nicole.oliveira.pb@compasso.com.br
* **Vinicius Ruan Fillos** - vinicius.fillos.pb@compasso.com.br

### Objetivos

O objetivo deste projeto é construir um sistema de backend que permite que equipes internas proponham e votem em diversas propostas de melhorias. O sistema será composto por três microsserviços, um servidor de descoberta (Discovery Server) e um gateway, além de um servidor Zookeeper e Kafka.

### Pré-requisitos

Antes de executar o projeto, certifique-se de que os seguintes softwares estão instalados e configurados em seu ambiente:

* **Java Development Kit (JDK) 21** - [Baixar JDK](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
* **Apache Maven 3.8.4 ou superior** - [Baixar Maven](https://maven.apache.org/download.cgi)
* **Docker 20.10.8 ou superior** - [Baixar Docker](https://www.docker.com/products/docker-desktop)
* **Docker Compose 1.29.2 ou superior** - [Baixar Docker Compose](https://docs.docker.com/compose/install/)

### Instruções de Execução

1. **Subir o Zookeeper e o Kafka:**
   - Navegue até a pasta `docker` em seu terminal.
   - Execute o seguinte comando para iniciar o Zookeeper e o Kafka:
     ```sh
     docker-compose up
     ```

2. **Executar o servidor Eureka (Discovery Server):**
   - Navegue até a pasta `eurekaServer` em seu terminal.
   - Execute a classe principal com o seguinte comando:
     ```sh
     mvn spring-boot:run
     ```

3. **Executar os microsserviços:**
   - Para cada um dos três microsserviços, navegue até sua respectiva pasta em seu terminal.
   - Execute a classe principal com o seguinte comando:
     ```sh
     mvn spring-boot:run
     ```

4. **Executar o gateway:**
   - Navegue até a pasta `gateway` em seu terminal.
   - Execute a classe principal com o seguinte comando:
     ```sh
     mvn spring-boot:run
     ```

5. Use a collection do Postman disponível na pasta raiz para interagir com a API.
```
  Microsserviço de Gestão de Funcionários:
   - Cadastrar Funcionário →  [POST] /api/v1/funcionarios
   - Editar Funcionários →  [PUT] /api/v1/funcionarios/alterar/{id}
   - Buscar Funcionário   →  [GET] /api/v1/funcionarios/{id}
   - Buscar Todos Funcionários →  [GET] /api/v1/funcionarios
   - Deletar Funcionário → [DELETE]  /api/v1/funcionarios{id}

 Microsserviço de Gestão de Propostas:
   - Cadastrar Proposta → [POST]  /api/v1/propostas
   - Buscar Proposta → [GET] /api/v1/propostas/{id}
   - Buscar Todas Propostas → [GET] /api/v1/propostas
   - Editar Proposta → [PUT] /api/v1/propostas/{id}
   - Deletar Proposta → [DELETE] /api/v1/propostas/{id}
   - Iniciar Votação → [POST] /api/v1/propostas/votacao/{id}?limite={minutos}
   - Votar → [POST]  /api/v1/propostas/votar

 Microsserviço de Gestão de Votação:
   - Encerrar Votação → [POST] /api/v1/votacao/encerrar
   - Buscar Votação → [GET] /api/v1/votacao/{id}
   - Buscar Todas Votações → [GET]  /api/v1/votacao
```


### Estrutura de Branches

* **main:** Branch principal, onde **apenas** as alterações validadas serão mescladas.
* **dev:** Branch de desenvolvimento, onde as funcionalidades serão implementadas.
* **Branches de tasks:** Seguindo o padrão `[nome-do-microserviço]/[feature]-[nome_da_funcionalidade]`
* **Exemplo:** `msFucionario/feature-cadastrar_funcionario`.

### Tipos de Commit

* **docs:** Alterações na documentação.
* **feat:** Implementação de novas funcionalidades.
* **fix:** Correção de bugs.
* **perf:** Melhorias de performance.
* **refactor:** Reorganização do código sem afetar funcionalidades ou corrigir bugs.
* **style:** Formatação e organização do código (espaços em branco, etc.).
* **test:** Adição ou correção de testes.

### Links para acesso à documentação no Swagger

* Micro Serviço Funcionários - http://localhost:[porta]/docs-msFuncionarios.html
* Micro Serviço Votação - http://localhost:[porta]/docs-msVotacao.html
* Micro Serviço Propostas - http://localhost:[porta]/docs-msProposta.html

### Links para acessar o Actuator

* Micro Serviço Funcionários - http://localhost:[porta]/actuator/metrics
* Micro Serviço Votação - http://localhost:[porta]/actuator/metrics
* Micro Serviço Propostas - http://localhost:[porta]/actuator/metrics

Ao executar nossos microsserviços baseados em Spring Boot, você notará que a “[porta]” é gerada de forma aleatória toda vez que o serviço é iniciado. Essa abordagem é útil para evitar conflitos de porta quando vários serviços estão sendo executados simultaneamente.

Para encontrar o número da porta atribuído ao microsserviço, você pode seguir a seguinte opção:

**Eureka Discovery Service:**
Acesse o painel do Eureka em http://localhost:8761/
Lá, você encontrará uma lista de todos os microsserviços registrados, incluindo seus nomes e portas.
Localize o microsserviço específico (por exemplo, “Micro Serviço Funcionários”) e use o número da porta associado a ele.

### Próximas Atualizações

* **Versionamento:** Para as próximas versões do projeto, mudaríamos a URL (/v1) e já temos algumas ideias para implementar (caso houvesse um cliente, cuidaríamos para que as funcionalidades da (/v1) continuassem funcionando por algum tempo dentro da (/v2), dando tempo para os mesmos se atualizarem).
* Adicionar Flyway ao projeto para também versionar os bancos de dados.
* Trocar o modo em que passamos os resultados das votações para outros microsserviços, saindo do openFeign e criando um novo tópico no Kafka para armazenar os resultados, colocando os outros microsserviços como listeners para esse tópico, assim deixando o sistema menos acoplado.

### Relatório de Desenvolvimento do Projeto

Realizamos calls de squad regularmente ao longo do processo de implementação, discutimos os requisitos, chegamos em um consenso quanto à lógica de implementação, planejamos as tarefas e revisamos o progresso do desenvolvimento. Durante essas reuniões, Keren, Nicole e Vinicius contribuíram com ideias e sugestões, tornando o processo de implementação colaborativo.

Abordamos uma ideia de fazer da maneira mais simples e, então, depois de pouco em pouco em pouco, acrescentaríamos as partes que acreditávamos serem mais difíceis. Temos uma primeira release neste repositório onde o sistema já estava funcionando, porém sem utilizar do Kafka e com muitas regras de negócios ainda faltando. Fazendo as atualizações e cada vez aumentando a complexidade do projeto, lançamos a segunda release.

Testamos os endpoints utilizando o Postman, garantindo que todas as funcionalidades estivessem corretamente implementadas e que os resultados estivessem de acordo com o esperado. Durante esse processo, identificamos áreas que precisavam de ajustes e refinamentos, e realizamos essas correções. Keren e Vinicius foram responsáveis pela escrita dos testes automatizados, colaborando um com o outro para garantir uma cobertura abrangente dos casos de teste e a qualidade dos testes implementados. Nicole realizou a documentação do projeto através do Swagger.

Ao longo do desenvolvimento, surgiam dúvidas e dificuldades, recorremos uns aos outros para encontrar soluções e garantir que o desenvolvimento continuasse fluindo sem contratempos significativos. Para resolver problemas específicos, consultamos recursos online e, quando necessário, solicitamos apoio dos instrutores, que nos forneceram orientações e sugestões para superar os obstáculos encontrados.

Consideramos que nosso progresso foi significativo, conseguimos colocar em prática o que aprendemos nos cursos e estamos felizes e orgulhosos dos resultados alcançados pela squad.
