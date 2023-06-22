<script lang="ts">
import dayjs from "dayjs";
import { defineComponent, onMounted, reactive } from "vue";
import AlunoService from "~~/services/AlunoService";

export default defineComponent({
  props: {
    grrAluno: {
      type: String,
      required: false,
    },
  },
  setup({ grrAluno }: { grrAluno: string }) {
    const { aluno, setAluno } = useAluno();
    const route = useRoute();

    const alunoService = new AlunoService();

    const { id } = route.params;

    const { auth } = useAuth();

    const grr = grrAluno || auth?.value?.identifier || "";

    const curso = reactive({});

    const handleFetchCurso = async (cursoID: string) => {
      if (aluno?.curso) return;
      const response = await alunoService.getCursoAlunoFromSiga(cursoID);
      curso.value = response;
      console.log(curso.value);
    };

    const { data: dadosAluno } = useAsyncData("aluno", async () => {
      const response = await alunoService.getAlunoFromSiga(grr);
      setAluno(response);

      await handleFetchCurso(response?.idPrograma);
      return response;
    });

    // parse da data para o formato dd/mm/yyyy e adiciona 3 horas devido ao fuso
    const parseDataNascimento = (data: string) => {
      if (!data) return "--/--/----";
      return dayjs(data).add(3, "hour").format("DD/MM/YYYY");
    };

    const getAge = (data: string) => {
      if (!data) return "--";
      return dayjs(new Date()).diff(new Date(data), "year");
    };

    return {
      aluno: dadosAluno,
      curso,
      dayjs,
      parseDataNascimento,
      getAge,
    };
  },
});
</script>

<template>
  <div class="card">
    <h5>Dados do aluno</h5>

    <div class="grid">
      <div class="col-4">
        <strong>Nome completo</strong>
        <p>{{ aluno?.nome }}</p>
      </div>
      <div class="col-4">
        <strong>PcD</strong>
        <p>{{ aluno?.isPcD ? "Sim" : "Não" }}</p>
      </div>
      <div class="col-4">
        <strong>Data de Nascimento</strong>
        <!-- get age -->
        <p>
          {{ parseDataNascimento(aluno?.dataNascimento) }}
          ({{ getAge(aluno?.dataNascimento) }}
          anos)
        </p>
      </div>
      <!-- <div class="col-4">
        <strong>CPF</strong>
        <p>111.111.111-11</p>
      </div> -->
      <div class="col-4">
        <strong>RG</strong>
        <p>{{ aluno?.rg }}</p>
      </div>
      <div class="col-4">
        <strong>GRR</strong>
        <p>{{ aluno?.matricula }}</p>
      </div>

      <div class="col-4">
        <strong>Email</strong>
        <p>{{ aluno?.email }}</p>
      </div>

      <div class="col-4">
        <strong>Período Atual</strong>
        <p>{{ aluno?.periodoAtual }}</p>
      </div>

      <div class="col-4">
        <strong>IRA</strong>
        <p>{{ aluno?.ira }}</p>
      </div>

      <div class="col-4">
        <strong>Turno</strong>
        <p>{{ aluno?.turno }}</p>
      </div>

      <div class="col-6">
        <strong>Curso</strong>
        <p>{{ curso?.value?.nome }}</p>
      </div>
    </div>
  </div>
</template>

<style></style>
