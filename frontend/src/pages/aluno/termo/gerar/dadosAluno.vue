<script lang="ts">
import { defineComponent, reactive } from "vue";
import AlunoService from "~~/services/AlunoService";

export default defineComponent({
  props: {
    advanceStep: {
      type: Function,
      required: true,
    },
    backStep: {
      type: Function,
      required: true,
    },
  },
  async setup({
    advanceStep,
    backStep,
  }: {
    advanceStep: Function;
    backStep: Function;
  }) {
    const { aluno: alunoData, setAluno } = useAluno();
    const alunoService = new AlunoService();

    const curso = reactive({});

    const grr = "GRR20200141";

    const handleFetchCurso = async (cursoID: string) => {
      // if (aluno?.curso) return;
      const response = await alunoService.getCursoAlunoFromSiga(cursoID);
      curso.value = response;
      // console.log(curso.value);
    };

    const { data: aluno } = useAsyncData("aluno", async () => {
      // if (aluno?.value) return aluno;
      const response = await alunoService.getAlunoFullFromSiga(grr);
      // setAluno(response);
      console.log(response);

      await handleFetchCurso(response?.idPrograma);
      return response;
    });

    return {
      advanceStep,
      backStep,
      aluno,
      curso,
    };
  },
});
</script>

<style></style>

<template>
  <div class="grid mt-2">
    <div class="p-2 mt-4">
      <h5 class="mb-0">Dados do aluno</h5>
      <small class="text-sm"
        >Em caso de necessidade de alteração dos dados do aluno, por favor
        acessar a plataforma SIGA</small
      >
    </div>
    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Dados de Registro</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">Nome</label>
            <InputText disabled id="name2" type="text" :value="aluno?.nome" />
          </div>
          <div class="field col">
            <label for="email2">Email</label>
            <InputText disabled id="email2" type="text" :value="aluno?.email" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">RG</label>
            <InputText
              disabled
              id="name2"
              type="text"
              :value="aluno?.documento"
            />
          </div>
          <div class="field col">
            <label for="email2">CPF</label>
            <InputText disabled type="text" :value="aluno?.cpf" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">Telefone</label>
            <InputText
              disabled
              id="name2"
              type="text"
              :value="aluno?.telefone"
            />
          </div>
          <div class="field col">
            <label for="email2">PcD</label>
            <InputText
              disabled
              id="email2"
              type="text"
              :value="aluno?.pcd ? 'Sim' : 'Não'"
            />
          </div>
        </div>
      </div>

      <div class="card p-fluid col-12">
        <h5>Endereço</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">Logradouro</label>
            <InputText disabled :value="aluno?.endereco?.rua" />
          </div>
          <div class="field col">
            <label for="email2">Número</label>
            <InputText disabled :value="aluno?.endereco?.numero" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">Complemento</label>
            <InputText disabled :value="aluno?.endereco?.complemento" />
          </div>
          <div class="field col">
            <label for="email2">Cidade</label>
            <InputText disabled :value="aluno?.endereco?.cidade" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">CEP</label>
            <InputText disabled :value="aluno?.endereco?.cep" />
          </div>
          <div class="field col">
            <label for="email2">Estado</label>
            <InputText disabled :value="aluno?.endereco?.uf" />
          </div>
        </div>
      </div>

      <div class="card p-fluid col-12">
        <h5>Curso</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">Nome</label>
            <InputText
              disabled
              id="name2"
              type="text"
              :value="curso?.value?.nome"
            />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">Matrícula (GRR)</label>
            <InputText disabled id="name2" type="text" :value="aluno?.grr" />
          </div>
          <div class="field col">
            <label for="email2">Nível</label>

            <InputText disabled id="email2" type="text" value="Superior" />
          </div>
        </div>
      </div>
    </div>
    <div class="w-full flex justify-end gap-2">
      <a href="https://www.prppg.ufpr.br/siga/" target="_blank">
        <Button
          @click="() => {}"
          label="Acessar SIGA"
          class="p-button-secondary"
          icon="pi pi-external-link"
        />
      </a>
      <Button
        @click="advanceStep"
        label="Avançar"
        class="p-button-success"
        icon="pi pi-arrow-right"
      />
    </div>
  </div>
</template>
