<script lang="ts">
import { defineComponent } from "vue";
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
    const grr = "GRR20201212";

    const { data: aluno } = await useFetch(
      `http://localhost:5000/aluno/${grr}`
    );

    return {
      advanceStep,
      backStep,
      aluno,
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
            <InputText disabled id="name2" type="text" :value="aluno?.rg" />
          </div>
          <div class="field col">
            <label for="email2">CPF</label>
            <InputText
              disabled
              id="email2"
              type="text"
              :value="aluno?.documento"
            />
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
            <label for="name2">Rua</label>
            <InputText disabled id="name2" type="text" value="teste" />
          </div>
          <div class="field col">
            <label for="email2">Número</label>
            <InputText disabled id="email2" type="text" value="teste" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">Complemento</label>
            <InputText disabled id="name2" type="text" value="teste" />
          </div>
          <div class="field col">
            <label for="email2">Cidade</label>
            <InputText disabled id="email2" type="text" value="teste" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">CEP</label>
            <InputText disabled id="name2" type="text" value="teste" />
          </div>
          <div class="field col">
            <label for="email2">Estado</label>
            <InputText disabled id="email2" type="text" value="teste" />
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
              :value="aluno?.curso?.nome"
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
