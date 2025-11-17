import { authHeaders } from "./authService";

const API_URL = "http://localhost:8080/negocios";

/* --------------------------------------
   OBTENER NEGOCIOS DEL USUARIO
---------------------------------------*/
export async function obtenerNegociosPorUsuario(userId) {
  const response = await fetch(`${API_URL}/usuario/${userId}`, {
    headers: authHeaders(),
  });

  if (!response.ok) {
    throw new Error("Error obteniendo negocios del usuario");
  }

  return await response.json();
}

/* --------------------------------------
   CREAR NEGOCIO
---------------------------------------*/
export async function crearNegocio(userId, data) {
  const response = await fetch(`${API_URL}/crear/${userId}`, {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new Error("Error creando negocio");
  }

  return await response.json();
}

/* --------------------------------------
   OBTENER NEGOCIO POR ID
---------------------------------------*/
export async function obtenerNegocioPorId(id) {
  const response = await fetch(`${API_URL}/${id}`, {
    headers: authHeaders(),
  });

  if (!response.ok) {
    throw new Error("Error obteniendo el negocio");
  }

  return await response.json();
}

/* --------------------------------------
   ACTUALIZAR NEGOCIO
---------------------------------------*/
export async function actualizarNegocio(id, data) {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: authHeaders(),
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new Error("Error actualizando el negocio");
  }

  return await response.json();
}

/* --------------------------------------
   ELIMINAR NEGOCIO
---------------------------------------*/
export async function eliminarNegocio(id) {
  const response = await fetch(`${API_URL}/eliminar/${id}`, {
    method: "DELETE",
    headers: authHeaders(),
  });

  if (!response.ok) {
    throw new Error("Error eliminando el negocio");
  }

  return true;
}
