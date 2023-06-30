import { Ref } from "vue";
import NovoEsatgio from "../types/NovoEstagio";
import { BaseTermo } from "../types/Termos";

const useTermo = (): {
  termo: Ref<BaseTermo>;
  setTermo(termoData: Ref<BaseTermo>): void;
} => {
  const termo = useState<BaseTermo>("termo");

  const setTermo = (termoData: Ref<BaseTermo>) => {
    termo.value = termoData;
  };

  return {
    termo,
    setTermo,
  };
};

export default useTermo;
