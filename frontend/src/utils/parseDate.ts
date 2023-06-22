import dayjs from "dayjs";

export default function parseDate(date?: string) {
  if (!date) return "";

  const dateObject = dayjs(date);
  return `${dateObject.format("DD/MM/YYYY")}`;
}
