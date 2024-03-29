# Módulo de Estágios

## Frontend

### Bibliotecas importantes

- [Sakai / PrimeVue](hhttps://primevue.org) - Template de componentes e estilização
- [Tailwind](https://tailwindcss.com/docs) - Biblioteca de estilos
- [dayjs](https://day.js.org/) - Biblioteca de manipulação de datas
- [zod](https://github.com/colinhacks/zod) - Biblioteca de validação de dados

### Requisitos

- Docker (banco)
- Node v18 (front)
- pnpm (front)
- Spring Boot (back)
- Java v17

### Como rodar

## Banco

Criando imagem do banco em docker:

```bash
docker run --name tcc -e MYSQL_ROOT_PASSWORD=poc -e MYSQL_DATABASE=poc -e MYSQL_USER=poc -e MYSQL_PASSWORD=poc -p 3306:3306 -d mariadb:latest
```

### Configurações do Banco

O usuário que aplicação utilizar para acessar o banco precisa ter os seguintes privilégios:
- Usage
- Event

O privilégio Event é necessário pois a aplicação irá criar procedures no banco quando for inicializada e além das procedures também irá criar um scheduler event para executar as procedures de tempo em tempo.

Além disto, também é necessário que o parâmetro global event_scheduler esteja habilitado no banco para que o scheduler execute. Para habilitar o event_scheduler no banco a seguinte query SQL deve ser executada no banco:

```
SET GLOBAL event_scheduler = ON;
```

## Backend

Necessita:

- **Java v17**
- Banco MariaDB

Para rodar:

```
cd backend/estagio-poc/src/main/resources

cp application-example.properties application.properties

```

Então coloque as credenciais do siga no application.properties, e rode o back com:

```bash
./mvnw spring-boot:run
```

Caso ocorra erro de permissão:

```bash
chmod +x mvnw
```

## Frontend

Utilizando o pnpm, instale as dependencias com:

```
pnpm i
```

Então copie as variáveis de ambiente a partir do arquivo exemplo:

```
cp .env.example .env
```

E rode o projeto com:

```
pnpm run dev
```

### Rodando com docker compose

Antes de rodar a primeira vez é necessário buildar o back, e depois o banco e o front:

Antes de rodar o back, é preciso realizar uma cópia do arquivo application.properties, e preencher com as credenciais do keycloak do SIGA:

```
cd backend/estagio-poc/src/main/resources

cp application.properties.example application.properties

```

Além de também colocar as credenciais no arquivo .env no front:

```
cd frontend

cp .env.example .env

```

Então, basta buildar o back:

```bash
cd backend
cd estagios-poc

./mnvw clean install -D skipTests
```

E rodar o docker compose

```bash
docker compose build
```

Então:

```bash
docker-compose up
```

_Rodando pelo docker é necessário atualzar a página para que as alterações tenham efeito_

Se for preciso instalar alguma dependência, é preciso buildar novamente a imagem do docker, logo:

```bash
docker-compose build --no-cache
```

Então pode rodar novamente o comando acima.
