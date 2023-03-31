<template>
  <div class="grid mt-2">
    <h5 class="mb-0 p-2 mt-4">Dados do Estágio</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Periodo de Estágio</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="dataInicio">Data de Início</label>
            <InputText id="dataInicio" type="text" />
          </div>
          <div class="field col">
            <label for="dataFinal">Data de Termino</label>
            <InputText id="dataFinal" type="text" />
          </div>
          <div class="field col">
            <label for="jornadaDiaria">Jornada Diária (em horas)</label>
            <InputText id="jornadaDiaria" type="number" maxlength="2" />
          </div>
          <div class="field col">
            <label for="jornadaSemanal">Jornada Semanal (em horas)</label>
            <InputText id="jornadaSemanal" type="number" maxlength="2" />
          </div>
        </div>
      </div>
      <div class="card p-fluid col-12">
        <h5>Valores da Bolsa Auxílio</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="dataInicio">Valor bolsa auxílio</label>
            <InputText id="dataInicio" type="text" />
          </div>
          <div class="field col">
            <label for="jornadaSemanal"
              >Valor auxílio transporte (R$/diário)</label
            >
            <InputText id="jornadaSemanal" type="text" maxlength="2" />
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
            />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="orientador">Professor Orientador na UFPR</label>
            <InputText id="orientador" type="text" />
          </div>
          <div class="field col">
            <label for="departamentoOrientador"
              >Lotação/Departamento do Orientador</label
            >
            <InputText id="departamentoOrientador" type="text" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeSupervisor"
              >Nome do Supervisor no Local de Estágio</label
            >
            <InputText id="nomeSupervisor" type="text" />
          </div>
          <div class="field col">
            <label for="telefoneSupervisor">Telefone do Supervisor</label>
            <InputText id="telefoneSupervisor" type="text" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeSupervisor">Atividades a Serem Desenvolvidas</label>
            <Textarea id="nomeSupervisor" type="text" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
