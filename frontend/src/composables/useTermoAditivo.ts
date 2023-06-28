import { Ref } from "vue";
import NovoEsatgio from "../types/NovoEstagio";
import { BaseTermo } from "../types/Termos";

const useTermoAditivo = (): {
  termo: Ref<BaseTermo>;
  setTermo(termoData: Ref<BaseTermo>): void;
} => {
  const termo = useState<BaseTermo>("termo");

  const setTermo = (termoData: Ref<BaseTermo>) => {
    console.log("setting termo", termoData);
    termo.value = termoData;
  };

  return {
    termo,
    setTermo,
  };
};

export default useTermoAditivo;
