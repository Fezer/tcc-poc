<script lang="ts">
import { defineComponent, ref } from "vue";
import parseDate from "../../../utils/parseDate";
import { useToast } from "primevue/usetoast";

export default defineComponent({
  setup() {
    const { auth } = useAuth();
    const toast = useToast();

    const grr = auth?.value?.identifier || "";
    const { data: certificados } = useFetch(
      `/aluno/${grr}/certificadoDeEstagio`
    );

    const reprovacaoModalInfo = ref(null);

    const handleDownloadCertificado = async (certificadoID: string) => {
      try {
        const url = `/aluno/${grr}/certificado-de-estagio/${certificadoID}/gerar`;

        const file = await $fetch(url, {
          method: "GET",
        });

        const fileURL = URL.createObjectURL(file);

        return window.open(fileURL, "_blank");
      } catch (err) {
        toast.add({
          severity: "error",
          summary: "Erro ao baixar certificado",
          detail:
            err?.response?._data?.error ||
            "Ocorreu um erro ao baixar o certificado, tente novamente mais tarde",
        });
      }
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
      <h2>Certificados de Estágio</h2>
      <DataTable
        :value="certificados"
        rowHover
        stripedRows
        :show-gridlines="true"
      >
        <Column field="process" header="Número do Processo de Certificado">
          <template #body="{ data }"> #{{ data?.id }} </template>
        </Column>
        <Column field="process" header="Número do Processo de Estágio">
          <template #body="{ data }"> #{{ data?.estagio?.id }} </template>
        </Column>
        <Column field="process_type" header="Data de Início do Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.estagio?.dataInicio) }}
          </template>
        </Column>
        <Column field="process_type" header="Data de Término do Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.estagio?.dataTermino) }}
          </template>
        </Column>
        <Column field="student_name" header="Tipo do Estágio">
          <template #body="{ data }">
            {{ data?.estagio?.tipoEstagio ? "Não Obrigatório" : "Obrigatório" }}
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
