import { authHeaders } from "./authService";

const API_URL = "http://localhost:8080/citas";

export async function crearCita(data) {
  const response = await fetch(`${API_URL}/crear`, {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const errorText = await response.text();
    console.error("Backend Error:", errorText);
    throw new Error("Error creando la cita");
  }

  return await response.json();
}

export async function obtenerCitasPorCliente(clienteId) {
  const response = await fetch(`${API_URL}/cliente/${clienteId}`, {
    headers: authHeaders(),
  });

  if (!response.ok) {
    const errorText = await response.text();
    console.error("Backend Error:", errorText);
    throw new Error("Error obteniendo citas del cliente");
  }

  return await response.json();
}

export async function obtenerCitasPorNegocio(negocioId) {
  const response = await fetch(`${API_URL}/negocio/${negocioId}`, {
    headers: authHeaders(),
  });

  if (!response.ok) {
    const errorText = await response.text();
    console.error("Backend Error:", errorText);
    throw new Error("Error obteniendo citas del negocio");
  }

  return await response.json();
}

export async function cambiarEstadoCita(id, estado) {
  const response = await fetch(`${API_URL}/${id}/estado`, {
    method: "PUT",
    headers: authHeaders(),
    body: JSON.stringify({ estado }),
  });

  if (!response.ok) {
    const errorText = await response.text();
    console.error("Backend Error:", errorText);
    throw new Error("Error cambiando estado de la cita");
  }

  return await response.json();
}

export async function editarCita(id, data) {
  const response = await fetch(`${API_URL}/${id}/editar`, {
    method: "PUT",
    headers: authHeaders(),
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const errorText = await response.text();
    console.error("Backend Error:", errorText);
    throw new Error("Error editando la cita");
  }

  return await response.json();
}
