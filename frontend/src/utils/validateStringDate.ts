import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";

dayjs.extend(customParseFormat);

export default function validateStringDate(val: unknown): any {
  if (typeof val !== "string") return false;
  try {
    const date = dayjs(val, "DD/MM/YYYY", true);
    if (!date.isValid()) throw new Error("Invalid date");
    return true;
  } catch (e) {
    return false;
  }
}
