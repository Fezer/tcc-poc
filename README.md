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

## Backend

Necessita:

- **Java v17**
- Banco MariaDB

Para rodar:

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

Antes de rodar a primeira vez:

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
