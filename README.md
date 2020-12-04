# Senior Hotel

Seja muito bem-vindo ao resultado do meu desafio Senior Hotel - Desenvolvedor Full Stack | Fábrica de Software.

## Instalação

### API

#### Ambiente

- Instale o [Maven](https://maven.apache.org/download.cgi) para que possamos instalar as dependências do projeto;
- Instale o Java 11 (JDK 11.0.9 ou superior), aqui temos o caminho para o site da [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) onde estão disponíveis as versões adequadas para cada sistema operacional;
- Instale o PostgresQL 13v, Sugiro a instalação via [Docker](https://hub.docker.com/_/postgres) :whale:, porém caso seja de sua preferência temos o site oficial do [PostgresQL](https://www.postgresql.org/download/) para instalação;
> É importante notar que a aplicação esta setada para acessar o banco de dados via porta padrão `5432`;
- Após instalação do PostgresQL, acesse o seu gerenciador de banco de dados favorito, pessoalmente recomendo o [DBeaver](https://dbeaver.io/) :heart: e execute o comando `CREATE DATABASE senior-hotel`, esse comando irá preparar o nosso banco de dados para receber a conexão com nosso serviço.

#### Serviço

- Caso ainda não tenha o repositório, clone-o para uma pasta local do seu sistema;
- Acesse a pasta `api` do repositório e execute o comando `mvn package`.

### WEB

#### Ambiente
- Instale o [Node.js](https://nodejs.org/en/) e para evitar problemas vamos considerar a versão `14.15.1` ou superior como versão minima necessária;
- Após instalação do Node, abra o terminal e execute `npm install -g @angular/cli`, esse comando serve para conseguirmos executar de forma global em nosso sistemas comando para nossa aplicação Angular. Para mais informações acesse o site oficial do [Angular Cli](https://cli.angular.io/).

#### Aplicação

- Caso ainda não tenha o repositório, clone-o para uma pasta local do seu sistema;
- Acesse a pasta `web` do repositório e execute no terminal o comando `npm install --only=prod` para instalar as dependências.

## Deploy

É importante notar que a aplicação web é dependente do serviço de api rest, portanto para que as aplicações sejam integradas corretamente vamos seguir a ordem de api > web.

1. Acesse a pasta `api` e execute o comando `mvn spring-boot:run` para buildar o serviço (Servido na porta `:3000`);
2. Acesse a pasta `web` e execute o comando `npm run start` para buildar a aplicação (Servido na porta `:4200`).

## Documentação API

Ao fazer deploy da API, o próprio serviço disponibilizará uma documentação viva através do swagger, para acessar basta entrar em `http://localhost:3000/swagger-ui.html`, porém, para verificar mais detalhes e regras de negócio segue a documentação feita por mim:

### Hospede Controller

---

Endpoint: `GET /v1/hospedes/`

Parameters:

```txt
- page: Parâmetro para gerenciamento da paginação, não obrigatório;
- size: Parâmetro para gerenciamento da paginação, não obrigatório;
```

Responses:

- 200: Busca realizada e retornado elementos páginados;

---

Endpoint: `POST /v1/hospedes/`

Body:

```txt
{
  "nome": "string", // [Obrigatório]
  "documento": "string", // [Obrigatório / Único]
  "telefone": "string" // [Obrigatório]
}
```

Responses:

- 201: Registro inserido com sucesso!;
- 409: Conflito com regra de negócio:
  - Documento informado já está cadastro por outro hóspede;

---

Endpoint: `GET /v1/hospedes/{hospedeId}`

Parameters:

```txt
- id: Obrigatório e representa o identificador do registro no banco de dados, pode ser buscado via página index;
```

Responses:

- 200: Busca realizada com sucesso, retornado modelo do Hóspede;
- 404: Registro não encontrado;

---

Endpoint: `DELETE /v1/hospedes/{hospedeId}`

Parameters:

```txt
- id: Obrigatório e representa o identificador do registro no banco de dados, pode ser buscado via página index;
```

Responses:

- 200: Registro excluído com sucesso;

// Não existe validação se registro existe ou não, portanto caso não exista vamos retornar 200 da mesma forma.

---

Endpoint: `PATCH /v1/hospedes/{hospedeId}`

Parameters:

```txt
- id: Obrigatório e representa o identificador do registro no banco de dados, pode ser buscado via página index;
```

Body:

```txt
{
  "nome": "string", // [Opcional]
  "documento": "string", // [Opcional / Único]
  "telefone": "string" // [Opcional]
}
```

Responses:

- 200: Registro atualizado com sucesso;
- 409: Conflito com regra de negócio:
  - Documento informado já está cadastro por outro hóspede;
- 404: Registro não encontrado.

### CheckIn Controller

---

Endpoint: `POST /v1/check-ins/`

Body:

```txt
{
  "adicionalVeiculo": true | false, // [Obrigatório]
  "dataEntrada": "string", // (Formato: "yyyy-MM-dd'T'HH:mm:ss") [Obrigatório]
  "dataSaida": "string", // (Formato: "yyyy-MM-dd'T'HH:mm:ss") [Obrigatório]
  "documentoHospede": "string" [Obrigatório]
}
```

Responses:

- 201: Registro inserido com sucesso!;
- 409: Conflito com regra de negócio:
  - Não existe nenhum hóspede com o documento informado;
  - Data de Entrada está no futuro;
  - Data de Entrada é maior que a de Saida;

---

Endpoint: `GET /v1/check-ins/hospedes`

Parameters:

```txt
- content: Não Obrigatório e representa o filtro que será utilizado nos campos "Nome", "Documento" e "Telefone" do Hóspede;
- status: Pode receber ["IN", "OUT"] e não é obrigatório, sendo considerado "IN" caso nulo, representa o status do Hóspede de acordo com o Check In (é importante notar que Hóspedes sem Check In não são listados):
  - IN: Tem Check In na data atual;
  - OUT: Tem Check In fora da data atual;
- page: Parâmetro para gerenciamento da paginação, não obrigatório;
- size: Parâmetro para gerenciamento da paginação, não obrigatório;
```

Responses:

- 200: Busca realizada e retornado elementos páginados;
- 409: Conflito com regra de negócio:
  - Status informado não identificado;

---
