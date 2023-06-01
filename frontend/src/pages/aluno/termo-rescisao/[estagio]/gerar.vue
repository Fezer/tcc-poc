<script lang="ts">
import dayjs from "dayjs";
import { useToast } from "primevue/usetoast";
import { defineComponent, onMounted } from "vue";
import AlunoService from "~~/services/AlunoService";
import TermoDeRescisaoService from "~~/services/TermoDeRescisaoService";
import periodoRecesso from "../../../../components/termo-rescisao/periodo-recesso.vue";
import { PeriodoRecesso } from "~~/src/types/Termos";

export default defineComponent({
  components: { periodoRecesso },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const toast = useToast();

    const { termoRescisao } = useTermoRescisao();

    const { estagio } = route.params;
    const alunoService = new AlunoService();
    const termoService = new TermoDeRescisaoService();

    const state = reactive({
      dataTermino: null,
      periodoTotalRecesso: null,

      id: null,
    });

    const periodosDeRecesso = ref<PeriodoRecesso[]>([]);

    const editingRecessPeriod = reactive({
      dataInicio: null,
      dataFim: null,

      id: null,
    });

    const errors = ref<any>({});

    const handleGerarTermoRescisao = async () => {
      if (!state.dataTermino) {
        errors.value["dataTermino"] = "Data de encerramento é obrigatória";
      }

      if (!state.periodoTotalRecesso) {
        errors.value["periodoTotalRecesso"] =
          "Período total de recesso é obrigatório";
      }

      if (Object.keys(errors.value).length > 0) {
        return;
      }

      try {
        let termoID = state.id;

        if (!termoID) {
          const { id: novoID } = await alunoService.criarTermoDeRecisao(
            "GRR20200141",
            estagio
          );

          termoID = novoID;
        }

        for (let periodo of periodosDeRecesso.value) {
          if (!periodo.id) {
            await termoService
              .criaPeriodoDeRecesso(termoID, estagio, "GRR20200141")
              .then(async ({ id }) => {
                await termoService.atualizaPeriodoDeRecesso(id, {
                  dataInicio: dayjs(periodo.dataInicio).format("YYYY-MM-DD"),
                  dataFim: dayjs(periodo.dataFim).format("YYYY-MM-DD"),
                });
              });
          }
        }

        await termoService.atualizaTermoDeRecisao(termoID, {
          dataTermino: dayjs(state.dataTermino).format("YYYY-MM-DD"),
          periodoTotalRecesso: state.periodoTotalRecesso,
        });

        toast.add({
          severity: "success",
          summary: "Termo de rescisão gerado com sucesso",
          detail: "Aguarde a aprovação do termo",
          life: 3000,
        });

        router.push("/aluno/termo-rescisao/" + termoID);
      } catch (err) {
        console.log(err);

        toast.add({
          severity: "error",
          summary: "Erro ao gerar termo de rescisão",
          detail: "Tivemos um problema ao gerar o termo de rescisão",
          life: 3000,
        });
      }
    };

    const handleRemovePeriodo = (dataInicio: string) => {
      periodosDeRecesso.value = periodosDeRecesso.value.filter(
        (periodo) => periodo.dataInicio !== dataInicio
      );

      toast.add({
        severity: "success",
        summary: "Período de recesso removido com sucesso",
        detail: "O período de recesso foi removido com sucesso",
        life: 3000,
      });
    };

    const handleAddRecessPeriod = () => {
      const { dataFim, dataInicio } = editingRecessPeriod;

      if (!dataFim) {
        errors.value["dataFim"] = "Data de fim é obrigatória";
      }

      if (!dataInicio) {
        errors.value["dataInicio"] = "Data de início é obrigatória";
      }

      if (Object.keys(errors.value).length > 0) {
        return;
      }

      periodosDeRecesso.value.push({
        dataFim: dayjs(dataFim).format("YYYY-MM-DD"),
        dataInicio: dayjs(dataInicio).format("YYYY-MM-DD"),
      });

      editingRecessPeriod.dataFim = null;
      editingRecessPeriod.dataInicio = null;
    };

    onMounted(() => {
      if (!!termoRescisao?.value?.id) {
        state.dataTermino = termoRescisao.value.dataTermino
          ? dayjs(termoRescisao.value.dataTermino).format("DD/MM/YYYY")
          : null;
        state.periodoTotalRecesso = termoRescisao.value.periodoTotalRecesso;

        state.id = termoRescisao.value.id;

        periodosDeRecesso.value = termoRescisao?.value?.periodoRecesso;
      }
    });

    return {
      estagio,
      state,
      errors,
      handleGerarTermoRescisao,
      editingRecessPeriod,
      handleAddRecessPeriod,
      periodosDeRecesso,
      handleRemovePeriodo,
    };
  },
});
</script>
<template>
  <div>
    <Toast />
    <h3 class="m-0">Termo de Recisão</h3>
    <small>Estágio #{{ estagio }}</small>

    <div class="card p-fluid col-12 mt-4">
      <div class="grid formgrid">
        <div class="field col">
          <label for="dataTermino">Data encerramento estágio</label>
          <InputMask
            mask="99/99/9999"
            v-model="state.dataTermino"
            :class="errors['dataTermino'] && 'p-invalid'"
          />
          <small class="text-rose-500">{{ errors["dataTermino"] }}</small>
        </div>
        <div class="field col">
          <label for="periodoTotalRecesso">Período total de recesso</label>
          <InputNumber
            suffix=" dias"
            max="999"
            v-model="state.periodoTotalRecesso"
            :class="errors['periodoTotalRecesso'] && 'p-invalid'"
          />
          <small class="text-rose-500">{{
            errors["periodoTotalRecesso"]
          }}</small>
        </div>
      </div>
    </div>

    <h4>Períodos de recesso</h4>

    <div v-for="item in periodosDeRecesso" :key="item.dataInicio">
      <periodo-recesso
        :termo="state.id"
        :periodo="item"
        :estagio="estagio"
        :onRemove="handleRemovePeriodo"
      />
    </div>

    <h4>Adicionar período de recesso</h4>
    <div class="card p-fluid col-12">
      <div class="grid formgrid">
        <div class="field col-5">
          <label for="dataInicio">Data Inicio</label>
          <InputMask
            mask="99/99/9999"
            v-model="editingRecessPeriod.dataInicio"
            :class="errors['dataInicio'] && 'p-invalid'"
          />
          <small class="text-rose-500">{{ errors["dataInicio"] }}</small>
        </div>
        <div class="field col-5">
          <label for="dataFim">Data Fim</label>
          <InputMask
            mask="99/99/9999"
            v-model="editingRecessPeriod.dataFim"
            :class="errors['dataFim'] && 'p-invalid'"
          />
          <small class="text-rose-500">{{ errors["dataFim"] }}</small>
        </div>

        <div class="field col-2 flex items-end justify-center">
          <Button
            label="Adicionar"
            icon="pi pi-plus"
            @click="handleAddRecessPeriod"
          />
        </div>
      </div>
    </div>
    <div class="w-full flex items-center justify-end gap-2">
      <NuxtLink :to="`/aluno/estagio/${estagio}`">
        <Button
          label="Voltar"
          icon="pi pi-arrow-left"
          class="p-button-secondary"
        />
      </NuxtLink>
      <Button
        label="Gerar termo de recisão"
        icon="pi pi-file"
        class="p-button-success"
        @click="handleGerarTermoRescisao"
      />
    </div>
  </div>
</template>

<style></style>
