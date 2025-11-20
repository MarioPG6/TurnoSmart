import { jwtDecode } from "jwt-decode";

const API_URL = "http://localhost:8080/auth";

// -------------------- LOGIN --------------------
export async function login(credentials) {
  const response = await fetch(`${API_URL}/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(credentials),
  });

  if (!response.ok) throw new Error("Error en login");

  const data = await response.json();

  // Guardar token en localStorage
  localStorage.setItem("token", data.token);

  // Decodificar el token
  const decoded = jwtDecode(data.token);

  // Guardar datos del usuario
  localStorage.setItem("user", JSON.stringify(decoded));

  return decoded;
}

export async function register(form) {
  const response = await fetch(`${API_URL}/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(form),
  });

  if (!response.ok) throw new Error("Error en registro");

  return await response.json();
}

export function getCurrentUser() {
  const token = localStorage.getItem("token");
  if (!token) return null;

  try {
    const decoded = jwtDecode(token);

    // si el token expiró
    if (decoded.exp * 1000 < Date.now()) {
      logout();
      return null;
    }

    return decoded; // devuelve: id, email, role
  } catch (e) {
    logout();
    return null;
  }
}

export function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("user");
}

export function authHeaders() {
  const token = localStorage.getItem("token");
  return token
    ? { Authorization: `Bearer ${token}`, "Content-Type": "application/json" }
    : { "Content-Type": "application/json" };
}
