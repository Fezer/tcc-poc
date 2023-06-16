import dayjs from "dayjs";

const parseDateToMask = (date?: string) => {
  if (!date) return;

  const dayJSDate = dayjs(date);

  return dayJSDate.format("DD/MM/YYYY");
};

export default parseDateToMask;
