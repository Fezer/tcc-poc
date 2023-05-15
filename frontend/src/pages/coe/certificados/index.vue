<script lang="ts">
import { defineComponent } from "vue";
import parseDate from "../../../utils/parseDate";

export default defineComponent({
  setup() {
    const { data: certificados } = useFetch(
      "http://localhost:5000/coe/certificado/pendenteAprovacaoCoe"
    );

    const handleDownloadCertificado = (certificadoID: string) => {
      console.log(certificadoID);
    };

    return { certificados, parseDate, handleDownloadCertificado };
  },
});
</script>
<template>
  <div>
    <div>
      <h2>Certificados de estágio pendentes de aprovação</h2>
      <DataTable :value="certificados" rowHover stripedRows>
        <Column field="process" header="Processo Certificado">
          <template #body="{ data }"> #{{ data?.id }} </template>
        </Column>
        <Column field="process" header="Processo Estágio">
          <template #body="{ data }"> #{{ data?.estagio?.id }} </template>
        </Column>
        <Column field="process_type" header="Data Início Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.estagio?.dataInicio) }}
          </template>
        </Column>
        <Column field="process_type" header="Data Término Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.estagio?.dataTermino) }}
          </template>
        </Column>
        <Column field="student_name" header="Tipo Estágio">
          <template #body="{ data }">
            {{ data?.estagio?.tipoEstagio }}
          </template>
        </Column>

        <Column field="action" header="Etapa" bodyStyle="color:orange;">
          <template #body="{ data }">
            {{ data.etapaFluxo }}
          </template>
        </Column>
        <Column field="button" header="Ações">
          <template #body="{ data }">
            <NuxtLink :to="`/coe/certificados/parecer/${data?.id}`">
              <Button label="Ver processo" icon="pi pi-arrow-right"></Button>
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>

<style></style>
