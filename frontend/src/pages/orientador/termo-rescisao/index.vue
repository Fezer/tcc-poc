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
      <h1>
        Prof. Professor Orientador
        <h6>Orientador</h6>
      </h1>
    </div>
    <div>
      <DataTable :value="processes" rowHover stripedRows :show-gridlines="true">
        <template #header>
          <div class="flex items-center justify-content-between">
            <span class="p-input-icon-left">
              <h4 class="font-bold">Termos de Rescisão Pendentes de Ciência</h4>
            </span>
          </div>
        </template>
        <Column field="process" header="Número do Processo">
          <template #body="{ data }"> #{{ data.id }} </template>
        </Column>
        <Column field="process_type" header="Tipo de Processo">
          <template #body="{ data }"> Termo de Rescisão </template>
        </Column>

        <Column field="estagio" header="Número do Estágio">
          <template #body="{ data }"> #{{ data?.estagio?.id }} </template>
        </Column>

        <Column field="dataInicio" header="Data de Início do Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.estagio?.dataInicio) }}
          </template>
        </Column>
        <Column field="process_type" header="Data de Término Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.dataTermino) }}
          </template>
        </Column>

        <Column field="dataInicio" header="Estágio UFPR">
          <template #body="{ data }">
            {{ data?.estagio?.estagioUfpr ? "Sim" : "Não" }}
          </template>
        </Column>

        <Column field="button" header="Ver">
          <template #body="{ data }">
            <NuxtLink :to="`/orientador/termo-rescisao/${data.id}`">
              <Button
                class="p-button-icon-only p-button-outlined"
                icon="pi pi-eye"
                type="primary"
              />
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
