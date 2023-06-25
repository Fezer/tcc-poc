<script lang="ts">
import { defineComponent, ref, watch, onMounted } from "vue";

export default defineComponent({
  emits: ["update:modelValue"],
  props: {
    modelValue: {
      type: Number,
    },
  },
  setup(props, { emit }) {
    const config = useRuntimeConfig();
    const contratantes = ref([]);

    const contratantesInput = ref("");
    const localContratante = ref(null);

    let controller; // Declara uma variável para o controlador de cancelamento
    let timeoutId; // Declara uma variável para o ID do timeout
    onMounted(async () => {
      console.log(props.modelValue);
      localContratante.value = props.modelValue;

      if (props.modelValue) {
        const url = `${config.BACKEND_URL}/contratante/${props.modelValue}`;

        console.log(url);

        await fetch(url)
          .then((response) => response.json())
          .then((data) => {
            contratantesInput.value = `${data.nome} - ${data.cnpj}`;
          });
      }
    });

    // watch input and fetch contratantes from backend when input length is >= 3
    watch(contratantesInput, async (input) => {
      if (input.length >= 3) {
        // Cancela o timeout anterior, se houver
        if (timeoutId) {
          clearTimeout(timeoutId);
        }

        // Define um novo timeout para aguardar o atraso
        timeoutId = setTimeout(async () => {
          // Cancela a requisição anterior, se houver
          if (controller) {
            controller.abort();
          }

          controller = new AbortController(); // Inicializa o controlador de cancelamento
          const signal = controller.signal; // Obtém o sinal de cancelamento

          try {
            const response = await $fetch(
              `/contratante/nome/contendo/${input}`,
              {
                signal, // Passa o sinal de cancelamento para a opção 'signal' do fetch
              }
            );

            contratantes.value = response;
          } catch (error) {
            if (error?.name === "AbortError") {
              // A requisição foi cancelada, pode lidar com isso aqui se necessário
            } else {
              console.error(error);
            }
          }
        }, 500);
      }
    });

    const selectContratante = (
      contratanteId: number,
      nome: string,
      cnpj: string
    ) => {
      console.log("SELECTING CONTRATANTE", contratanteId);
      localContratante.value = contratanteId;
      contratantesInput.value = `${nome} - ${cnpj}`;
      emit("update:modelValue", contratanteId);
    };
    const clearSelection = () => {
      localContratante.value = null;
      contratantesInput.value = "";
      emit("update:modelValue", null);
    };

    return {
      contratantes,
      contratantesInput,
      modelValue: props.modelValue,
      emit,
      selectContratante,
      clearSelection,
      localContratante,
    };
  },
});
</script>
<template>
  <div>
    <div class="relative">
      <InputText
        v-tooltip="'Digite pelo menos 3 caracteres para buscar a contratante'"
        v-if="!localContratante"
        placeholder="Busca por nome da contratante"
        v-model="contratantesInput"
        class="mb-2"
      />

      <InputText
        v-else
        placeholder="Busca por nome da contratante"
        v-model="contratantesInput"
        class="mb-2"
        disabled
      />

      <span
        v-if="!!localContratante"
        @click="clearSelection"
        class="pi pi-times absolute pb-2 right-4 top-0 bottom-0 flex items-center justify-center cursor-pointer"
      >
      </span>
    </div>

    <!-- <Listbox
      v-if="contratantesInput.length >= 3 && !modelValue"
      :options="contratantes"
      :optionLabel="(c) => `${c.nome} - ${c.cnpj}`"
      optionValue="id"
      @select="$emit('select', $event.value)"
    ></Listbox> -->

    <!-- list contratantes -->

    <div
      class="border rounded flex flex-col gap-2"
      v-if="contratantes.length > 0 && !localContratante"
    >
      <div
        v-for="c in contratantes"
        :key="c.id"
        @click="() => selectContratante(c.id, c.nome, c.cnpj)"
        class="cursor-pointer hover:bg-gray-200 transition-all p-2 rounded"
      >
        <span> {{ c.nome }} - {{ c.cnpj }} </span>
      </div>
    </div>

    <div class="border rounded flex flex-col p-2" v-else-if="!localContratante">
      <p>Nenhuma contratante encontrada</p>
    </div>
  </div>
</template>

<style></style>
