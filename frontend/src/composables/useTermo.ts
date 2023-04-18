const useTermo = () => {
  const termo = useState("termo", () => {});

  const setTermo = (termoData: any) => {
    console.log("SETTING TERMO");

    termo.value = termoData;
  };

  return {
    termo,
    setTermo,
  };
};

export default useTermo;
