<script lang="ts">
import { defineComponent, ref } from "vue";
import aluno from "../../../components/common/aluno.vue";
import Contratante from "../../../components/common/contratante.vue";
import planoAtividades from "../../../components/common/plano-atividades.vue";
import Status from "../../../components/common/statusEstagio.vue";
import AlunoService from "~~/services/AlunoService";
import { useToast } from "primevue/usetoast";
import { Estagio } from "~~/src/types/NovoEstagio";

type TipoUsuario = "ALUNO" | "COE" | "COAFE" | "COORD";

export default defineComponent({
  components: {
    Aluno: aluno,
    PlanoAtividades: planoAtividades,
    Contratante,
    Status,
  },
  async setup() {
    const { setTermo } = useTermo();
    const route = useRoute();
    const router = useRouter();
    const alunoService = new AlunoService();
    const toast = useToast();

    const { auth } = useAuth();

    const grr = auth?.id || "";

    const { setTermoRescisao } = useTermoRescisao();

    const { id } = route.params;

    const { data: estagio, refresh } = await useFetch<Estagio>(
      `/estagio/${id}`
    );

    const handleNovoTermoRescisao = () => {
      if (estagio?.termoDeRescisao) {
        toast.add({
          severity: "error",
          summary: "Erro ao gerar termo de rescisão",
          detail:
            "Você já possui um termo de rescisão em processo de aprovação",
          life: 3000,
        });

        return;
      }

      setTermoRescisao(null);

      cancelationConfirm.value = false;
      router.push(`/aluno/termo-rescisao/${estagio?.value?.id}/gerar`);
    };

    const handleNovoTermoAditivo = async () => {
      const termoEmProcessoExiste = await alunoService.getTermoAditivoAtivo(
        grr
      );

      if (termoEmProcessoExiste) {
        toast.add({
          severity: "error",
          summary: "Erro ao gerar termo aditivo",
          detail: "Você já possui um termo aditivo em processo de aprovação",
          life: 3000,
        });
        return;
      }

      setTermo(null);
      router.push(`/aluno/termo-aditivo/${estagio?.value?.id}/gerar`);
    };

    const handleRedirectToTermoAditivo = (id: string) => {
      setTermo(null);
      router.push(`/aluno/termo-aditivo/${id}`);
    };

    // const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);

    const tipoUsuario = ref("ALUNO" as TipoUsuario);

    const cancelationConfirm = ref(false);

    const handleSolicitarCertificado = async () => {
      await alunoService
        .solicitarCertificadoEstagio(grr, id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Certificado solicitado com sucesso",
            detail:
              "Aguarde aprovação da COE para baixar seu certificado. Você pode acompanhar o processo na aba Certificados.",
            life: 3000,
          });
        })
        .catch((err) => {
          toast.add({
            severity: "error",
            summary: "Erro ao solicitar certificado",
            detail:
              err?.response?._data?.error || "Erro ao solicitar certificado",
            life: 3000,
          });
          console.error(err);
        });
    };

    return {
      tipoUsuario,
      cancelationConfirm,
      estagio,
      id,
      handleSolicitarCertificado,
      handleNovoTermoAditivo,
      handleRedirectToTermoAditivo,
      handleNovoTermoRescisao,
    };
  },
});
</script>

<template>
  <div>
    <small class="m-0">Estágios > Ver estágio</small>
    <h2 class="m-0 mb-4">Estágio</h2>

    <div class="card" v-if="estagio?.statusEstagio === 'Aprovado'">
      <h4>Ações</h4>
      <div class="flex gap-2">
        <Button
          label="Termo de Rescisão"
          class="p-button-danger"
          icon="pi pi-times"
          @click="() => (cancelationConfirm = true)"
        />
        <NuxtLink :to="`/aluno/relatorio/gerar/${id}`">
          <Button
            label="Novo relatório de Estágio"
            class="p-button-danger self-center bg-orange-500"
            icon="pi pi-file"
          />
        </NuxtLink>
        <NuxtLink :to="`/aluno/estagio/avaliacao/${id}`">
          <Button
            label="Ficha de Avaliação"
            class="p-button"
            icon="pi pi-file"
          />
        </NuxtLink>
        <Button
          label="Termo aditivo"
          class="p-button-success"
          icon="pi pi-plus"
          @click="handleNovoTermoAditivo"
        />
        <!-- TODO: só poder solicitar certificado após ficha de avaliação -->
        <Button
          label="Solicitar certificado"
          class="p-button-success"
          icon="pi pi-file"
          @click="handleSolicitarCertificado"
        />
      </div>
    </div>

    <Status :estagio="estagio" tipoUsuario="ALUNO" />

    <Estagio :termo="estagio" />

    <PlanoAtividades
      :termo="estagio"
      :planoAtividades="estagio?.planoDeAtividades"
    />

    <Contratante :termo="estagio" />

    <SuspensaoEstagio :termo="termo" />

    <objetos-de-estagio :estagio="estagio" perfil="aluno" />

    <Dialog
      :visible="cancelationConfirm"
      header="Confirmar Termo de Rescisão"
      :closable="false"
      style="width: 500px"
      :modal="true"
    >
      <p>
        Tem certeza que deseja iniciar o processo de rescisao do estágio? O
        processo será cancelado e o aluno não poderá mais realizar atividades
        referentes a esse estágio.
      </p>
      <template #footer>
        <Button
          label="Voltar"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="cancelationConfirm = false"
        />
        <Button
          label="Inicar termo de recisão"
          icon="pi pi-check"
          class="p-button-danger"
          autofocus
          @click="handleNovoTermoRescisao"
        />
      </template>
    </Dialog>
  </div>
</template>

<style scoped>
.col-4 {
  margin-bottom: 1rem;
}

.text-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5px;
  margin-bottom: 1rem;
}
</style>
