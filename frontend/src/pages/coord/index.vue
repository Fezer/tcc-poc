<script>
export default {
  setup() {
    const { data: processes } = useFetch(
      "/coordenacao/termo/pendenteAprovacaoCoordenacao"
    );

    console.log(processes);

    return {
      processes,
    };
  },
};
</script>

<template>
  <div>
    <div>
      <h1>
        Coordenação
        <h6>Curso X</h6>
      </h1>
    </div>
    <div>
      <DataTable :value="processes" rowHover stripedRows :show-gridlines="true">
        <template #header>
          <div class="flex items-center justify-content-between">
            <span class="p-input-icon-left">
              <h4 class="font-bold">Processos pendentes de parecer</h4>
            </span>
            <span class="p-input-icon-left">
              <i class="pi pi-search" />
              <InputText placeholder="Keyword Search" />
            </span>
          </div>
        </template>
        <Column field="process" header="Processo">
          <template #body="{ data }">
            {{ data.id }}
          </template>
        </Column>
        <Column field="process_type" header="Tipo de Processo">
          <template #body="{ data }">
            {{ data.tipoTermoDeEstagio }}
          </template>
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
        <Column
          field="action"
          header="Ação necessária"
          bodyStyle="color:orange;"
        >
          <template #body="{ data }">
            Parecer {{ data.tipoTermoDeEstagio }}
          </template>
        </Column>
        <Column field="button">
          <template #body="{ data }">
            <NuxtLink :to="`/coord/termo/${data.id}`">
              <Button label="Ver contato"></Button>
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
