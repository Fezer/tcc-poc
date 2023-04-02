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
            <InputText disabled id="name2" type="text" value="teste" />
          </div>
          <div class="field col">
            <label for="email2">Email</label>
            <InputText disabled id="email2" type="text" value="teste" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">RG</label>
            <InputText disabled id="name2" type="text" value="teste" />
          </div>
          <div class="field col">
            <label for="email2">CPF</label>
            <InputText disabled id="email2" type="text" value="teste" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">Telefone</label>
            <InputText disabled id="name2" type="text" value="teste" />
          </div>
          <div class="field col">
            <label for="email2">Email</label>
            <InputText disabled id="email2" type="text" value="teste" />
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
            <label for="name2">Rua</label>
            <InputText disabled id="name2" type="text" value="teste" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="name2">Matrícula (GRR)</label>
            <InputText disabled id="name2" type="text" value="teste" />
          </div>
          <div class="field col">
            <label for="email2">Nível</label>
            <InputText disabled id="email2" type="text" value="teste" />
          </div>
        </div>
      </div>
    </div>
    <div class="w-full flex justify-end gap-2">
      <Button
        @click="backStep"
        label="Voltar"
        class="p-button-secondary"
        icon="pi pi-arrow-left"
      />
      <Button
        @click="advanceStep"
        :label="finalStep ? 'Gerar termo' : 'Avançar'"
        class="p-button-success"
        icon="pi pi-arrow-right"
      />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    advanceStep: {
      type: Function,
      required: true,
    },
    backStep: {
      type: Function,
      required: true,
    },
    finalStep: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<style></style>
