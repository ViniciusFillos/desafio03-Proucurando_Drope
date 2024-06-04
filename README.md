## Desafio 03: Microsserviços para gerenciar processos de tomada de decisões em uma empresa

### Time Proucurando_Drope

* **Keren Apuque Cardoso de Morais** - keren.morais.pb@compasso.com.br
* **Nicole Inaê de Oliveira** - nicole.oliveira.pb@compasso.com.br
* **Vinicius Ruan Fillos** - vinicius.fillos.pb@compasso.com.br

### Objetivos

O objetivo deste projeto é construir um sistema de backend que permite que equipes internas proponham e votem em diversas propostas de melhorias. O sistema será composto por três microsserviços, um servidor de descoberta (Discovery Server) e um gateway:

1. **Microsserviço de Gestão de Funcionários:**
   * Cadastrar Funcionário.
   * Editar Funcionários.

2. **Microsserviço de Gestão de Propostas:**
    * Cadastrar Proposta.

3. **Microsserviço de Gestão de Votação:**
   * Iniciar votação.
   * Registrar votos.
   * Enviar resultado.
  
4. **Eureka Server:**
   * Registrar todos os Microserviços.
  
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
