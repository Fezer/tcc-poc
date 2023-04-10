import NovoEstagio from "../types/NovoEstagio";

export const useNovoEstagio = () =>
  useState<NovoEstagio>("novoEstagio", () => ({
    id: null,
    dadosTipoEstagio: null,
    tipoContratante: null,
    dadosEstagio: null,
    dadosAuxiliares: null,
  }));
