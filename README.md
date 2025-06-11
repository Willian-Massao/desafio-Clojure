# Desafio Clojure 🚀

Este projeto é uma API construída em Clojure usando **Pedestal** e **Datomic Free**, que implementa um contador persistente com operações CRUD (create-if-not-exists, read, increment, decrement, reset).

---

## ⚙️ Pré-requisitos

- [Java 11+](https://adoptium.net/index.html)
- [Leiningen](https://leiningen.org/) ou Clojure CLI
- [Datomic Free](https://my.datomic.com/downloads/free) instalado/executando localmente

---

## 🛠 Instalação & configuração

1. Clone o repositório:
   ```bash
   git clone https://github.com/Willian-Massao/desafio-Clojure.git
   cd desafio-Clojure
   ```

2. Inicie o Transactor (no diretório do Datomic Free):
   ```bash
   bin/transactor config/samples/free-transactor-template.properties
   ```

3. No projeto, instale dependências:
   ```bash
   lein deps      # usando Leiningen
   # ou
   clj -P         # com Clojure CLI
   ```

---

## ▶️ Executando a aplicação

- **Com Leiningen**:
  ```bash
  lein run -m server.core
  ```

- **Com Clojure CLI**:
  ```bash
  clj -M -m server.core
  ```

A API será inicializada em: `http://localhost:3000/`.

---

## 📡 Endpoints disponíveis

| Método | Rota            | Descrição                              |
|--------|-----------------|-----------------------------------------|
| GET    | `/api/`         | Retorna o valor atual (cria se não existir) |
| POST   | `/api/increment`| Incrementa e retorna o valor atualizado |
| POST   | `/api/decrease` | Decrementa e retorna o valor atualizado |
| POST   | `/api/reset`    | Zera o valor (`0`)                      |

---

## 🛠 Arquitetura das camadas

- `server.core`: inicializador do serviço Pedestal, define rotas.
- `routes.core`: handlers HTTP com lógica de negócio protegida por `try/catch`.
- `datomic.core`: configuração e conexão ao banco Datomic.
- `datomic.orm`: funções de acesso a dados (_ORM_), incluindo CRUD do contador usando `:entidade/uid` com `:db.unique/identity`.

---

## 🧠 Notas técnicas

- Utilizei `:entidade/uid` com `:db.unique/identity` para garantir uma chave fixa no banco.
- A transação `create-if-not-exists` impede duplicação de entidades.
- Todos os handlers tratam exceções e garantem respostas adequadas (`200` ou `500`).

---

## 💻 Testando manualmente

```bash
curl localhost:3000/api/            # deve retornar 0 ao iniciar
curl -X POST localhost:3000/api/increment   # retorna 1
curl -X POST localhost:3000/api/decrease    # retorna 0
curl -X POST localhost:3000/api/reset       # retorna 0
```

---

## ✔️ Próximos passos

- Adicionar testes unitários com `clojure.test`
- Cobertura de código com `cloverage`
- Refatorar `routes.core` para usar interceptors do Pedestal
- Adicionar tratamento JSON e validações
- Documentar as APIs usando Swagger ou Spec

---

## 🙏 Contribuições

Contribuições são bem-vindas! Abra issues para bugs ou dúvidas, e PRs para melhorias.

---

## 📄 Licença

Licença MIT — veja o arquivo [LICENSE](LICENSE) para detalhes.