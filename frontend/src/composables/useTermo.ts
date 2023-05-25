import { Ref } from "vue";
import NovoEsatgio from "../types/NovoEstagio";
import { BaseTermo } from "../types/Termos";

const useTermo = (): {
  termo: Ref<BaseTermo>;
  setTermo(termoData: BaseTermo): void;
} => {
  const termo = useState<BaseTermo>("termo");

  const setTermo = (termoData: any) => {
    console.log("setting termo", termoData);
    termo.value = termoData;
  };

  return {
    termo,
    setTermo,
  };
};

export default useTermo;
