export const formatDate = (data) => {
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

export const formatCurrency = (value) => {
  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(value);
}

export const translateCategories = (category) => {
  const translation = {
    ELECTRONIC: "Eletrônicos",
    APPLIANCE: "Eletrodomésticos",
    FURNITURE: "Móveis",
    CLEANING: "Limpeza"
  };

  return translation[category] || category;
}

export const translateMovementType = (Type) => {
  const translation = {
    IN: "Entrada",
    OUT: "Saída"
  };

  return translation[Type] || Type;
}
