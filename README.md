# Módulo de Estágios

## Frontend

---

### Bibliotecas importantes

- [Sakai / PrimeVue](hhttps://primevue.org) - Template de componentes e estilização
- [Tailwind](https://tailwindcss.com/docs) - Biblioteca de estilos
- [dayjs](https://day.js.org/) - Biblioteca de manipulação de datas
- [zod](https://github.com/colinhacks/zod) - Biblioteca de validação de dados

### Requisitos

- Docker

### Como rodar

```bash
docker-compose up
```

Se for preciso instalar alguma dependência, é preciso buildar novamente a imagem do docker, logo:

```bash
docker-compose build --no-cache
```

Então pode rodar novamente o comando acima.
