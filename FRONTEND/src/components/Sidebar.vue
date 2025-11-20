<template>
  <div class="sidebar">
    <div class="head">Menú</div>

    <ul class="list">
      <!-- AUTENTICADO -->
      <template v-if="isAuthenticated">
        
        <!-- CLIENTE -->
        <template v-if="userRole === 'CLIENTE'">
          <li><RouterLink to="/">Inicio</RouterLink></li>
          <li><RouterLink to="/cliente/negocios">Negocios</RouterLink></li>
          <li><RouterLink to="/cliente/citas">Mis Citas</RouterLink></li>
        </template>

        <!-- OPERADOR -->
        <template v-else-if="userRole === 'OPERADOR'">
          <li><RouterLink to="/">Inicio</RouterLink></li>
        </template>

        <!-- ADMIN -->
        <template v-else-if="userRole === 'ADMINISTRADOR'">
          <li><RouterLink to="/">Inicio</RouterLink></li>
          <li><RouterLink to="/admin/negocios">Negocios</RouterLink></li>

          <!-- MENÚ DESPLEGABLE DE CITAS -->
          <li class="dropdown">
            <div class="dropdown-header" @click="toggleDropdown">
              Citas por Negocio
              <span class="arrow">{{ dropdownOpen ? "▲" : "▼" }}</span>
            </div>

            <ul v-if="dropdownOpen" class="dropdown-list">
              <li 
                v-for="neg in negociosAdmin"
                :key="neg.id"
                class="dropdown-item"
              >
                <!-- NUEVA LÓGICA: usar la misma función que en la tabla -->
                <a href="#" @click.prevent="goCitasSidebar(neg.id)">
                  {{ neg.nombreNegocio }}
                </a>
              </li>
            </ul>
          </li>
        </template>
      </template>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { getCurrentUser } from "../services/authService.js";
import { obtenerNegociosPorUsuario } from "@/services/negocioService.js";

const router = useRouter();
const isAuthenticated = ref(false);
const userRole = ref(null);

const dropdownOpen = ref(false);
function toggleDropdown() {
  dropdownOpen.value = !dropdownOpen.value;
}

const negociosAdmin = ref([]);

async function applyAuth() {
  try {
    const u = await getCurrentUser();
    isAuthenticated.value = !!u;
    userRole.value = u?.role ?? null;

    // Cargar negocios del admin
    if (u && u.role === "ADMINISTRADOR") {
      negociosAdmin.value = await obtenerNegociosPorUsuario(u.id);
    }

  } catch {
    isAuthenticated.value = false;
    userRole.value = null;
  }
}

function onAuthChanged() {
  applyAuth();
}

function onStorage(e) {
  if (e.key === "token") applyAuth();
}

onMounted(() => {
  applyAuth();
  window.addEventListener("auth-changed", onAuthChanged);
  window.addEventListener("storage", onStorage);
});

onBeforeUnmount(() => {
  window.removeEventListener("auth-changed", onAuthChanged);
  window.removeEventListener("storage", onStorage);
});

function logout() {
  localStorage.removeItem("token");
  isAuthenticated.value = false;
  userRole.value = null;
  window.dispatchEvent(new Event("auth-changed"));
  router.push("/login");
}

function goCitasSidebar(id) {
  localStorage.setItem("negocioId", id);
  window.dispatchEvent(new Event("negocio-changed"));

  router.push("/admin/citas");
}

</script>

<style scoped>
:root {
  --brand-blue: #1e88e5;
  --brand-dark-blue: #0d47a1;
  --brand-orange: #fb8c00;
  --text-light: #e3f2fd;
  --hover-bg: rgba(251, 140, 0, 0.2);
}

.sidebar {
  background: linear-gradient(180deg, var(--brand-dark-blue), var(--brand-blue));
  color: var(--text-light);
  padding: 16px 12px;
  height: 100%;
  border-right: 2px solid var(--brand-orange);
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.head {
  font-weight: 700;
  margin-bottom: 12px;
  font-size: 1.1rem;
  color: var(--brand-orange);
  text-transform: uppercase;
  letter-spacing: 1px;
}

.list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  gap: 10px;
}

a {
  color: var(--text-light);
  text-decoration: none;
  padding: 8px 10px;
  border-radius: 6px;
  display: block;
  transition: background 0.25s, color 0.25s;
}

a:hover {
  background: var(--hover-bg);
  color: #fff;
}

.logout-link {
  background: none;
  border: 0;
  color: #ffccbc;
  padding: 8px 10px;
  cursor: pointer;
  text-align: left;
  border-radius: 6px;
  width: 100%;
  transition: 0.25s;
}

.logout-link:hover {
  color: var(--brand-orange);
  background: var(--hover-bg);
}

/* 🔽 Estilos Dropdown con naranja */
.dropdown-header {
  cursor: pointer;
  padding: 8px 10px;
  border-radius: 6px;
  background: var(--brand-orange);
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  transition: 0.25s;
}

.dropdown-header:hover {
  background: #ff7300; /* naranja más fuerte */
}

.dropdown-list {
  padding-left: 12px;
  margin-top: 6px;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.dropdown-item a {
  padding: 6px 8px;
  background: rgba(251, 140, 0, 0.15); /* naranja suave */
  border-left: 3px solid var(--brand-orange);
  border-radius: 6px;
}

.dropdown-item a:hover {
  background: rgba(251, 140, 0, 0.3);
  color: white;
}
</style>
