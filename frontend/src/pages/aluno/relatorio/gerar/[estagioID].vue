<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import { z } from "zod";
import AlunoService from "~~/services/AlunoService";
import RelatorioEstagioService from "~~/services/RelatorioEstagioService";
import RelatorioEstagio from "~~/src/types/RelatorioEstagio";

export default defineComponent({
  setup() {
    const route = useRoute();
    const router = useRouter();
    const completionOptions = ["Nao", "Parcialmente", "Integralmente"];
    const evaluateOptions = ["Pessimo", "Ruim", "Razoavel", "Bom", "Excelente"];

    const { estagioID } = route.params;
    const { auth } = useAuth();

    const grr = auth?.value?.identifier || "";
    const toast = useToast();
    const relatorioService = new RelatorioEstagioService();
    const alunoService = new AlunoService();

    const consideracoesError = ref("");

    const state = reactive<RelatorioEstagio>({
      avalAtividades: "",
      avalFormacaoProfissional: "",
      avalRelacoesInterpessoais: "",
      avalDesenvolvimentoAtividades: "",
      avalContribuicaoEstagio: "",
      avalEfetivacao: "",
      consideracoes: "",
      tipo: "RelatorioParcial",
    });

    const validation = z.object({
      avalAtividades: z.string().nonempty(),
      avalFormacaoProfissional: z.string().nonempty(),
      avalRelacoesInterpessoais: z.string().nonempty(),
      avalDesenvolvimentoAtividades: z.string().nonempty(),
      avalContribuicaoEstagio: z.string().nonempty(),
      avalEfetivacao: z.string().nonempty(),
      consideracoes: z.string().nonempty(),
    });

    const handleGenerateRelatorio = async () => {
      const validate = validation.safeParse(state);

      const consideracoesLength = state?.consideracoes.length;

      if (consideracoesLength < 50) {
        return (consideracoesError.value =
          "O campo considerações deve ter no mínimo 50 caracteres");
      }

      if (consideracoesLength > 700) {
        return (consideracoesError.value =
          "O campo considerações deve ter no máximo 700 caracteres");
      }

      if (!validate.success) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
      }

      try {
        const { id: relatorioID } = await alunoService.novoRelatorioDeEstagio(
          grr,
          estagioID
        );

        await relatorioService.definirTipoRelatorio(relatorioID, state.tipo);
        const updateRelatorio = {
          ...state,
          tipo: undefined,
        };

        await relatorioService.atualizarRelatorioDeEstagio(
          relatorioID,
          updateRelatorio
        );

        // keep toast alive on redirect
        toast.add({
          severity: "success",
          summary: "Sucesso",
          detail: "Relatório criado com sucesso",
          life: 3000,
        });

        router.push("/aluno/relatorio/" + relatorioID);
      } catch (error) {
        console.log(error);
        toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Erro ao criar relatório",
          life: 3000,
        });
      }
    };

    return {
      state,
      evaluateOptions,
      completionOptions,
      handleGenerateRelatorio,
      consideracoesError,
    };
  },
});
</script>
<template>
  <div>
    <span>
      <small>Estágio > Novo Relatório de Estágio</small>
      <h3>Novo Relatório de Estágio</h3>
    </span>
    <div class="grid">
      <div class="col-12">
        <div class="card p-fluid col-12">
          <h5>Avaliação Estágio</h5>

          <label class="text-md"
            >As atividades programadas que constam no termo de compromisso foram
            realizadas:</label
          >
          <SelectButton
            v-model="state.avalAtividades"
            :options="completionOptions"
            class="mt-2 mb-4"
          />

          <label class="text-md"
            >De que forma você considera que a realização deste estágio
            contribuiu para sua formação profissional?
          </label>
          <SelectButton
            v-model="state.avalFormacaoProfissional"
            :options="evaluateOptions"
            class="mt-2 mb-4"
          />

          <label class="text-md">
            Como foram as relações interpessoais no local de estágio e o
            convívio no ambiente de trabalho?
          </label>

          <SelectButton
            v-model="state.avalRelacoesInterpessoais"
            :options="evaluateOptions"
            class="mt-2 mb-4"
          />

          <label class="text-md">
            De que forma foram desenvolvidas as atividades em equipe e/ou
            reuniões de planejamento?
          </label>

          <SelectButton
            v-model="state.avalDesenvolvimentoAtividades"
            :options="evaluateOptions"
            class="mt-2 mb-4"
          />

          <label class="text-md">
            Como você considera suas contribuições como estagiário para a
            unidade concedente?
          </label>
          <SelectButton
            v-model="state.avalContribuicaoEstagio"
            :options="evaluateOptions"
            class="mt-2 mb-4"
          />

          <label class="text-md">
            Qual a possibilidade da sua efetivação junto à unidade concedente do
            estágio?
          </label>
          <SelectButton
            v-model="state.avalEfetivacao"
            :options="evaluateOptions"
            class="mt-2 mb-4"
          />
          <div class="formgrid grid">
            <div class="field col">
              <span>
                <label for="atividades">Considerações</label>
                <small class="ml-2"
                  >50 - 700 caracteres ({{
                    state.consideracoes?.trim()?.length || 0
                  }})</small
                >
              </span>
              <Textarea
                id="atividades"
                placeholder="Descreva a sua avaliação relativa ao preríodo de estágio."
                v-model="state.consideracoes"
                maxlength="700"
                class="h-32"
                :class="!!consideracoesError ? 'p-invalid' : ''"
              />
              <small class="p-error">{{ consideracoesError }}</small>
            </div>
          </div>
        </div>

        <div class="flex w-full gap-2 justify-end">
          <NuxtLink to="/aluno">
            <Button
              label="Cancelar"
              class="p-button-secondary"
              icon="pi pi-times"
            >
            </Button>
          </NuxtLink>
          <Button
            label="Gerar relatório"
            class="p-button-success"
            icon="pi pi-file"
            @click="handleGenerateRelatorio"
          >
          </Button>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
