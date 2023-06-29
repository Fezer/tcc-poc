<script>
import { defineComponent } from "vue";
import parseTipoTermo from "~~/src/utils/parseTipoProcesso";

export default defineComponent({
  setup() {
    const route = useRoute();

    const { auth } = useAuth();

    const { data: processes } = useFetch(
      `/orientador/${auth?.value?.identifier}/termoDeRescisao/pendenteCiencia`
    );

    return {
      processes,
      parseTipoTermo,
    };
  },
});
</script>

<template>
  <div>
    <div>
      <h1>Termos de Rescisão</h1>
    </div>
    <div>
      <DataTable :value="processes" rowHover stripedRows :show-gridlines="true">
        <Column field="process" header="Processo">
          <template #body="{ data }"> #{{ data.id }} </template>
        </Column>
        <Column field="process_type" header="Tipo de Processo">
          <template #body="{ data }"> Termo de Rescisão </template>
        </Column>

        <Column field="estagio" header="Estágio">
          <template #body="{ data }"> #{{ data?.estagio?.id }} </template>
        </Column>

        <Column field="dataInicio" header="Data de Início Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.estagio?.dataInicio) }}
          </template>
        </Column>
        <Column field="process_type" header="Data Término Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.dataTermino) }}
          </template>
        </Column>

        <Column field="dataInicio" header="Estágio UFPR">
          <template #body="{ data }">
            {{ data?.estagio?.estagioUfpr ? "Sim" : "Não" }}
          </template>
        </Column>

        <Column field="button">
          <template #body="{ data }">
            <NuxtLink :to="`/orientador/termo-rescisao/${data.id}`">
              <Button label="Ver Termo"></Button>
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
