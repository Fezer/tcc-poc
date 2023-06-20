<script lang="ts">
import { defineComponent } from "vue";
import parseDate from "../../../utils/parseDate";

export default defineComponent({
  setup() {
    const page = ref(0);

    const filters = reactive<{
      grrAluno: string;
      etapaFluxo?: string;
    }>({
      grrAluno: "",
      // default => pendente parecer
      etapaFluxo: "COE",
    });

    const { data: certificados } = useAsyncData<{
      content: any[];
      totalElements: number;
    }>(
      () =>
        $fetch("/certificadoDeEstagio/", {
          params: {
            page: page.value,
            grrAluno: filters.grrAluno || undefined,
            etapaFluxo: filters.etapaFluxo || undefined,
          },
        }),
      {
        watch: [page, filters],
      }
    );

    const handleDownloadCertificado = (certificadoID: string) => {
      console.log(certificadoID);
    };

    return {
      certificados,
      parseDate,
      handleDownloadCertificado,
      parseObrigatoriedadeEstagio,
      page,
      filters,
    };
  },
});
</script>
<template>
  <div>
    <div>
      <h2>COE</h2>
      <DataTable
        :value="certificados?.content"
        rowHover
        stripedRows
        :show-gridlines="true"
        :paginator="true"
        :rows="10"
        :totalRecords="certificados?.totalElements"
      >
        <template #header>
          <div class="flex items-center justify-content-between">
            <span class="p-input-icon-left">
              <h4 class="font-bold">Termos de Rescisão</h4>
            </span>
            <div class="flex gap-2">
              <Button
                label="Pendentes de parecer"
                :class="`${
                  filters.etapaFluxo === 'COE'
                    ? 'p-button-primary'
                    : 'p-button-secondary opacity-50'
                }`"
                @click="
                  filters.etapaFluxo =
                    filters.etapaFluxo === 'COE' ? undefined : 'COE'
                "
              />
              <span class="p-input-icon-left">
                <i class="pi pi-search" />
                <InputText
                  placeholder="Buscar por matrícula (GRRXXXXXXXX)"
                  v-model="filters.grrAluno"
                />
              </span>
            </div>
          </div>
        </template>
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
            {{ parseObrigatoriedadeEstagio(data?.estagio?.tipoEstagio) }}
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
