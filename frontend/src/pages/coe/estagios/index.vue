<script lang="ts">
import { defineComponent } from "vue";
import { Estagio } from "~~/src/types/NovoEstagio";
import parseTipoTermo from "~~/src/utils/parseTipoProcesso";

export default defineComponent({
  setup() {
    const route = useRoute();

    const { data: estagios } = useFetch<Estagio>(`/estagio/`);

    console.log(estagios);

    return {
      estagios,
      parseTipoTermo,
    };
  },
});
</script>

<template>
  <div>
    <div>
      <h1>
        COE
        <h6>Comissão Orientadora de Estágio</h6>
      </h1>
    </div>
    <div>
      <DataTable :value="estagios" rowHover stripedRows :show-gridlines="true">
        <Column field="process" header="Processo">
          <template #body="{ data }"> #{{ data.id }} </template>
        </Column>

        <Column field="student_name" header="Nome do Aluno">
          <template #body="{ data }">
            {{ data?.aluno?.nome }}
          </template>
        </Column>
        <Column field="curse" header="Matrícula">
          <template #body="{ data }">
            {{ data.aluno?.matricula }}
          </template>
        </Column>
        <Column field="contratante" header="Contratante">
          <template #body="{ data }">
            {{ data?.contratante?.nome }} -
            {{ data?.contratante?.cnpj }}
          </template>
        </Column>
        <Column field="process_type" header="Tipo Estágio">
          <template #body="{ data }">
            {{ data?.tipoEstagio }}
          </template>
        </Column>
        <Column field="process_type" header="Estágio UFPR">
          <template #body="{ data }">
            {{ data?.estagioUfpr ? "Sim" : "Não" }}
          </template>
        </Column>
        <Column field="status" header="Status" bodyStyle="color:orange;">
          <template #body="{ data }">
            <StatusTag :status="data?.statusEstagio" />
          </template>
        </Column>
        <Column field="button">
          <template #body="{ data }">
            <NuxtLink :to="`/estagio/${data.id}?perfil=coe`">
              <Button label="Ver Estágio"></Button>
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
