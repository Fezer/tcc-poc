<script setup>
import { vMaska } from "maska";

// parse money to BRL currency and R$ prefix
const moneyMask = {
  preProcess: (value) => {
    return value.replace(/\D/g, "");
  },
  postProcess: (value) => {
    if (!value) return "";

    return "R$ " + value.replace(/(\d{1,2})$/, ",$1");
  },
};
</script>

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
  },
  data() {
    return {
      errors: { dataInicio: "Data inválida" },

      dataInicio: "",
      dataFinal: "",
      jornadaDiaria: "",
      jornadaSemanal: "",
      bolsaAuxilio: "",
      auxilioTransporte: "",
      coordenador: "",
      orientador: "",
      departamentoOrientador: "",
      nomeSupervisor: "",
      telefoneSupervisor: "",
      atividades: "",
    };
  },
};
</script>

<style></style>

<template>
  <div class="grid mt-2">
    <h5 class="mb-0 p-2 mt-4">Dados do Estágio</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Periodo de Estágio</h5>

        <div class="formgrid grid">
          <div class="field col">
            <label for="dataInicio">Data de Início {{ dataInicio }}</label>
            <InputText
              id="dataInicio"
              type="text"
              v-maska
              data-maska="##/##/####"
              v-model="dataInicio"
              required
              :class="errors['dataInicio'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["dataInicio"] }}</small>
          </div>
          <div class="field col">
            <label for="dataFinal">Data de Termino</label>
            <InputText
              id="dataFinal"
              type="text"
              v-maska
              data-maska="##/##/####"
              v-model="dataFinal"
              :class="errors['dataFinal'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["dataFinal"] }}</small>
          </div>
          <div class="field col">
            <label for="jornadaDiaria">Jornada Diária (em horas)</label>
            <InputText
              id="jornadaDiaria"
              type="number"
              v-maska
              data-maska="##"
              v-model="jornadaDiaria"
            />
          </div>
          <div class="field col">
            <label for="jornadaSemanal">Jornada Semanal (em horas)</label>
            <InputText
              id="jornadaSemanal"
              type="number"
              maxlength="2"
              v-maska
              data-maska="##"
              v-model="jornadaSemanal"
            />
          </div>
        </div>
      </div>
      <div class="card p-fluid col-12">
        <h5>Valores da Bolsa Auxílio</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="bolsaAuxilio"
              >Valor bolsa auxílio {{ bolsaAuxilio }}</label
            >
            <InputText
              id="bolsaAuxilio"
              v-maska:[moneyMask]
              data-maska="0.99"
              data-maska-tokens="0:\d:multiple|9:\d:optional"
              v-model="bolsaAuxilio"
            />
          </div>
          <div class="field col">
            <label for="auxilioTransporte"
              >Valor auxílio transporte (R$/diário)</label
            >
            <InputText
              id="auxilioTransporte"
              v-maska:[moneyMask]
              data-maska="0.99"
              data-maska-tokens="0:\d:multiple|9:\d:optional"
              v-model="auxilioTransporte"
            />
          </div>
        </div>
      </div>
      <div class="card p-fluid col-12">
        <h5>Plano de Atividades</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="coordenador">Coordenador do curso</label>
            <InputText
              id="coordenador"
              type="text"
              disabled
              value="Prof. Dr. Professor Professor"
              v-model="coordenador"
            />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="orientador">Professor Orientador na UFPR</label>
            <InputText id="orientador" type="text" v-model="orientador" />
          </div>
          <div class="field col">
            <label for="departamentoOrientador"
              >Lotação/Departamento do Orientador</label
            >
            <InputText
              id="departamentoOrientador"
              type="text"
              v-model="departamentoOrientador"
            />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeSupervisor"
              >Nome do Supervisor no Local de Estágio</label
            >
            <InputText
              id="nomeSupervisor"
              type="text"
              v-model="nomeSupervisor"
            />
          </div>
          <div class="field col">
            <label for="telefoneSupervisor">Telefone do Supervisor</label>
            <InputText
              id="telefoneSupervisor"
              type="text"
              v-maska
              data-maska="(##) #####-####"
              v-model="telefoneSupervisor"
            />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="atividades">Atividades a Serem Desenvolvidas</label>
            <Textarea id="atividades" type="text" v-model="atividades" />
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
  </div>
</template>
