export default function useGRR() {
  const grr = useState<string>("grr", () => {});

  const setGRR = (grrData: string) => {
    grr.value = grrData;
  };

  return {
    grr,
    setGRR,
  };
}
