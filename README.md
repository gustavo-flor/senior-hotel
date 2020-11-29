# Senior Hotel

Seja muito bem-vindo ao resultado do meu desafio Senior Hotel - Desenvolvedor Full Stack | Fábrica de Software.

## Instalação

É importante notar que as aplicações front e back end estão disponíveis no Heroku para testes, porém caso queira brincar no seu ambiente local siga os passos a seguir:

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

1. Acesse a pasta `api` e execute o comando `java -jar target/*.jar` para buildar o serviço;
2. Acesse a pasta `web` e execute o comando `npm run build` para buildar a aplicação.

## Documentação API

Ao fazer deploy da API, o próprio serviço disponibilizará uma documentação viva através do swagger, para acessar basta entrar em `http://localhost:3000/swagger-ui.html`.

## Muito Obrigado!
