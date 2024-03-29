import { Ref } from "vue";
import { TermoRescisao } from "../types/Termos";

const useTermoRescisao = (): {
  termoRescisao: Ref<TermoRescisao>;
  setTermoRescisao(termoData: Ref<TermoRescisao>): void;
} => {
  const termoRescisao = useState<TermoRescisao>("termoRescisao");

  const setTermoRescisao = (termoData: Ref<TermoRescisao>) => {
    termoRescisao.value = termoData;
  };

  return {
    termoRescisao,
    setTermoRescisao,
  };
};

export default useTermoRescisao;
