<script lang="ts">
import { defineComponent, ref } from "vue";
import parseDate from "../../../utils/parseDate";

export default defineComponent({
  setup() {
    const { data: certificados } = useFetch(
      "http://localhost:5000/aluno/GRR20200141/certificadoDeEstagio"
    );

    const reprovacaoModalInfo = ref(null);
    const handleDownloadCertificado = (certificadoID: string) => {
      console.log(certificadoID);
    };

    return {
      certificados,
      parseDate,
      handleDownloadCertificado,
      reprovacaoModalInfo,
    };
  },
});
</script>
<template>
  <div>
    <div
      class="h-full w-full flex items-center justify-center mt-4"
      v-if="!certificados || certificados?.length === 0"
    >
      Você ainda não possui nenhum certificado! Para gerar um certificado, você
      precisa concluir um estágio.
    </div>

    <div v-else>
      <h2>Certificados de estágio</h2>
      <DataTable
        :value="certificados"
        rowHover
        stripedRows
        :show-gridlines="true"
      >
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

        <Column field="action" header="Parecer COE">
          <template #body="{ data }">
            {{ data.parecerCOE }}
          </template>
        </Column>
        <Column field="button" header="Ações">
          <template #body="{ data }">
            <Button
              v-if="data?.parecerCOE === 'Aprovado'"
              label="Baixar certificado"
              icon="pi pi-download"
              @click="() => handleDownloadCertificado(data?.id)"
            ></Button>
            <Button
              v-else-if="data?.parecerCOE === 'Reprovado'"
              label="Ver motivo reprovação"
              icon="pi pi-arrow-right"
              class="p-button-danger"
              @click="reprovacaoModalInfo = data"
            ></Button>
          </template>
        </Column>
      </DataTable>
    </div>

    <Dialog
      :visible="!!reprovacaoModalInfo"
      header="Certificado de Estágio reprovado"
      :closable="false"
      style="width: 500px"
      :modal="true"
    >
      <strong class="text-lg">Motivo reprovação</strong>
      <p>{{ reprovacaoModalInfo?.motivoReprovacao }}</p>
      <template #footer>
        <Button
          label="Fechar"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="reprovacaoModalInfo = null"
        />
      </template>
    </Dialog>
  </div>
</template>

<style></style>
