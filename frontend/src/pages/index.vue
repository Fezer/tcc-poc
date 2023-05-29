<script>
export default {
  async mounted() {
    const urlParams = new URLSearchParams(window.location.search);
    const authorizationCode = urlParams.get('code');

    if (authorizationCode) {
      const tokenUrl = 'https://login.ufpr.br/realms/master/protocol/openid-connect/token';
      const data = new URLSearchParams();
      data.append('grant_type', 'authorization_code');
      data.append('code', authorizationCode);
      data.append('client_id', 'estagios');
      data.append('client_secret', '2xWHPudAW4hmAsnOYzW9eg4oUUKUHTLu');
      data.append('redirect_uri', 'http://localhost:3000/');

      try {
        const response = await fetch(tokenUrl, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          body: data,
        });
        const tokenData = await response.json();
        const accessToken = tokenData.access_token;
        console.log(accessToken);
        localStorage.setItem("accessToken", accessToken);
      } catch (error) {
        console.error('Erro ao obter o token de acesso:', error);
      }
    }
  }
};
</script>

<script scoped></script>

<script setup>
const { data: termos } = await useFetch(`http://localhost:5000/termo`);

console.log(termos)

// const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);
</script>

<template>
  <div>
    <h1>Módulo de Estágios - PoC</h1>
    <!-- <table class="p-datatable-table">
      <th>
        <td>Termo</td>
        <td>GRR</td>
        <td>Contratante</td>
        <td>Tipo Estágio</td>
        <td>Status</td>
        <td>Ações</td>
      </th>
    </table> -->
    <!-- <NuxtLink to="/termo/1">
      <Button type="primary">
        Ir para termo
      </Button>
    </NuxtLink> -->
    <DataTable :value="termos">
      <Column field="termo" header="Termo">
        <template #body="{ data }">
          {{ data.id }}
        </template>
      </Column>
      <Column field="grr" header="GRR" style="min-width:12rem">
        <template #body="{ data }">
          {{ data.grrAluno }}
        </template>
      </Column>
      <Column field="tipo" header="Tipo" style="min-width:12rem">
        <template #body="{ data }">
          <!-- {{ data.tipoTermoDeEstagio }} -->
          Termo de Compromisso
        </template>
      </Column>
      <Column field="contratante" header="Contratante" style="min-width:12rem">
        <template #body="{ data }">
          {{ data.nomeContratante }}
        </template>
      </Column>
      <Column field="etapa" header="Status" style="min-width:12rem; font-weight: bold;">
        <template #body="{ data }">
          {{ data.statusEstagio !== "EmAprovacao" ? data.statusEstagio : `Análise ${data.etapaFluxo}` }}
        </template>
      </Column>
      <Column field="acoes" header="Ações" style="min-width:12rem">
        <template #body="{ data }">
          <NuxtLink :to="`/termo/${data.id}`">
            <Button type="primary">
              Ver
            </Button>
          </NuxtLink>
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<style>

</style>
