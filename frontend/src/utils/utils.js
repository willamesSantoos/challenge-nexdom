export const formatarData = (data) => {
  try {
    if (!data) return "";

    const date = new Date(data);
    if (isNaN(date.getTime())) return "Data inválida";

    return date.toLocaleDateString("pt-BR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    });
  } catch (e) {
    console.error("Erro ao formatar data:", e);
    return "Erro de formatação";
  }
};
