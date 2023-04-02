<template>
  <div class="grid mt-2">
    <h5 class="mb-0 p-2 mt-4">Dados Auxiliares</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Dados da vaga</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="tipoVaga">Tipo de Vaga</label>
            <InputText id="tipoVaga" type="text" />
          </div>
          <div class="field col">
            <label for="nivelVaga">Nível</label>
            <InputText id="nivelVaga" type="text" />
          </div>
        </div>
      </div>
    </div>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Dados do Aluno</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="formacao">Formação em andamento</label>
            <InputText id="formacao" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="nivelVaga">Peridiocidade total do curso</label>
            <InputText id="nivelVaga" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="rg">RG</label>
            <InputText id="rg" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="orgaoEmissor">Órgão Emissor</label>
            <InputText id="orgaoEmissor" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="uf">UF</label>
            <InputText id="uf" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="dataExpedicaoRG">Data de expedição</label>
            <InputText
              id="dataExpedicaoRG"
              type="text"
              disabled
              value="Teste"
            />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="tituloEleitoral">Título eleitoral</label>
            <InputText
              id="tituloEleitoral"
              type="text"
              disabled
              value="Teste"
            />
          </div>
          <div class="field col">
            <label for="dataEmissaoTitulo">Data ded emissão</label>
            <InputText
              id="dataEmissaoTitulo"
              type="text"
              disabled
              value="Teste"
            />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="zonaEleitoral">Título eleitoral</label>
            <InputText id="zonaEleitoral" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="secaoEleitora">Seção</label>
            <InputText id="secaoEleitora" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="estadoCivil">Estado Civil</label>
            <InputText id="estadoCivil" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="dependentes">Dependentes</label>
            <InputText id="dependentes" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="grupoSanguineo">Grupo Sanguíneo</label>
            <InputText id="grupoSanguineo" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="corDaPele">Cor da Pele</label>
            <InputText id="corDaPele" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="sexo">Sexo</label>
            <InputText id="sexo" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="cidadeNascimento">Cidade de Nascimento</label>
            <InputText
              id="cidadeNascimento"
              type="text"
              disabled
              value="Teste"
            />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeDoPai">Nome do pai</label>
            <InputText id="nomeDoPai" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="nomeDaMae">Nome da mãe</label>
            <InputText id="nomeDaMae" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="nacionalidade">Nacionalidade</label>
            <InputText id="nacionalidade" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="dataChegadaPais">Data de chegada no pais</label>
            <InputText
              id="dataChegadaPais"
              type="text"
              disabled
              value="Teste"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Dados bancários</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="banco">Banco</label>
            <InputText id="banco" type="text" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="numAgencia">Número da Agência</label>
            <InputText id="numAgencia" type="text" />
          </div>

          <div class="field col">
            <label for="numConta">Número da Conta</label>
            <InputText
              id="numConta"
              type="text"
              v-maska
              data-maska="###.###-#"
            />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeAgencia">Nome da agência</label>
            <InputText id="nomeAgencia" type="text" />
          </div>

          <div class="field col">
            <label for="cidadeAgencia">Cidade da agência</label>
            <InputText id="cidadeAgencia" type="text" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="endAgencia">Endereço da agência</label>
            <InputText id="endAgencia" type="text" />
          </div>

          <div class="field col">
            <label for="bairroAgencia">Bairo da agência</label>
            <InputText id="bairroAgencia" type="text" />
          </div>
        </div>
      </div>
    </div>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Certificado de Dispensa Militar</h5>
        <div class="formgrid grid">
          <div class="field col">
            <FileUpload id="banco" type="text" mode="basic" title="teste" />
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="orgaoExpedicaoCertMilitar">Órgão de Expedição</label>
            <InputText id="orgaoExpedicaoCertMilitar" type="text" />
          </div>

          <div class="field col">
            <label for="serieCertMilitar">Série</label>
            <InputText id="serieCertMilitar" type="text" />
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
        label="Gerar termo"
        class="p-button-success"
        icon="pi pi-file"
      />
    </div>
  </div>
</template>

<script setup>
import { vMaska } from "maska";
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
};
</script>

<style></style>
