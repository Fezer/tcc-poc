<script lang="ts">
import dayjs from "dayjs";
import { defineComponent, onMounted, reactive } from "vue";
import AlunoService from "~~/services/AlunoService";

export default defineComponent({
  setup() {
    const { aluno, setAluno } = useAluno();
    const route = useRoute();

    const alunoService = new AlunoService();

    const { id } = route.params;

    const grr = "GRR20200141";

    const curso = reactive({});

    const handleFetchCurso = async (cursoID: string) => {
      const response = await alunoService.getCursoAlunoFromSiga(cursoID);
      curso.value = response;
      console.log(curso.value);
    };

    useAsyncData("aluno", async () => {
      if (aluno?.nome) return aluno;
      const response = await alunoService.getAlunoFromSiga(grr);
      setAluno(response);

      await handleFetchCurso(response?.idPrograma);
      return response;
    });

    return {
      aluno,
      curso,
      dayjs,
    };
  },
  // console.log(aluno)
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
          {{ dayjs(new Date(aluno?.dataNascimento || 0)).format("DD/MM/YYYY") }}
          ({{ dayjs(new Date()).diff(new Date(aluno?.dataNascimento), "year") }}
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
        <p>{{ aluno?.grr }}</p>
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
