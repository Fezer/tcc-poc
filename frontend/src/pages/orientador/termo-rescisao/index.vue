<script>
import { defineComponent } from "vue";
import parseTipoTermo from "~~/src/utils/parseTipoProcesso";

export default defineComponent({
  setup() {
    const route = useRoute();

    const { processo } = route.params;

    const orientador = 6;

    const { data: processes } = useFetch(
      `/orientador/${orientador}/termoDeRescisao/pendenteCiencia`
    );

    console.log(processes);

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
        <Column field="student_name" header="Nome do Aluno">
          <template #body="{ data }">
            {{ data?.aluno?.nome }}
          </template>
        </Column>
        <Column field="curse" header="Curso">
          <template #body="{ data }">
            {{ data.curse }}
          </template>
        </Column>
        <Column field="contratante" header="Contratante">
          <template #body="{ data }">
            {{ data?.contratante?.nome }}
          </template>
        </Column>
        <Column field="process_type" header="Data Término Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.dataTermino) }}
          </template>
        </Column>
        <Column
          field="action"
          header="Ação necessária"
          bodyStyle="color:orange;"
        >
          <template #body="{ data }">
            <Tag value="Parecer" severity="warning" class="p-2 font-md" />
          </template>
        </Column>
        <Column field="button">
          <template #body="{ data }">
            <NuxtLink :to="`/orientador/termo-rescisao/${data.id}`">
              <Button label="Ver contato"></Button>
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
