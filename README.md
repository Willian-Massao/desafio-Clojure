# Desafio Clojure

Projeto feito em formato de Desafio.

## Funcionalidades
- Página inicial (`/`): Exibe o conteúdo de `index.html`.
- Página GraphQL (`/graphql`): Exibe o conteúdo de `graphql.html`.
- API REST para contador:
  - `GET /api/` — Retorna o valor atual do contador.
  - `POST /api/increment` — Incrementa o contador.
  - `POST /api/decrease` — Decrementa o contador.
  - `POST /api/reset` — Reseta o contador para zero.
- Endpoint GraphQL (`POST /graphql`): Manipulação de dados via GraphQL (handler em `graphql/core.clj`).

## Estrutura do Projeto
```
project.clj
src/
  routes/core.clj      ; Definição das rotas e handlers
  graphql/core.clj     ; Handler GraphQL
  server/core.clj      ; Inicialização do servidor
resources/public/
  index.html           ; Página inicial
  graphql.html         ; Página GraphQL
  script.js            ; Scripts JS
  scriptgraph.js       ; Scripts JS para GraphQL
```

## Como rodar o projeto
1. Instale as dependências:
   ```sh
   lein deps
   ```
2. Inicie o servidor:
   ```sh
   lein run
   ```
3. Acesse no navegador:
   - [http://localhost:8080/](http://localhost:8080/) — Página inicial
   - [http://localhost:8080/graphql](http://localhost:8080/graphql) — Página GraphQL

## Requisitos
- [Leiningen](https://leiningen.org/)
- Java 8+

## Observações
- O estado do contador é mantido em memória usando um `atom`.
- O projeto utiliza políticas de segurança CSP nas rotas HTML.

---

Desenvolvido para fins de estudo e demonstração de Clojure/ClojureScript.
