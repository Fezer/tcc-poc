const parseDateToMask = (date?: string) => {
  if (!date) return "";
  const [year, month, day] = date.split("-");
  return `${day}/${month}/${year}`;
};

export default parseDateToMask;
