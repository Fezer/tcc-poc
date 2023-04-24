import { Ref } from "vue";
import NovoEsatgio from "../types/NovoEstagio";

const useTermo = (): {
  termo: Ref<NovoEsatgio>;
  setTermo(termoData: NovoEsatgio): void;
} => {
  const termo = useState<NovoEsatgio>("termo");

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
