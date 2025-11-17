<template>
  <div class="sidebar">
    <div class="head">Menú</div>

    <ul class="list">
      <template v-if="isAuthenticated">
        <!-- CLIENTE -->
        <template v-if="userRole === 'CLIENTE'"></template>

        <!-- TRABAJADOR -->
        <template v-if="userRole === 'OPERADOR'"></template>

        <!-- ADMINISTRADOR -->
        <template v-if="userRole === 'ADMINISTRADOR'">
          <li><RouterLink to="/admin/negocios">Negocios</RouterLink></li>
        </template>
      </template>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { getCurrentUser } from "../services/authService.js";

import {
  Home,
  LogIn,
  UserPlus,
  User,
  LogOut,
  MapPin,
  Package,
  Truck,
  Boxes,
  AlertTriangle,
  Users,
  BarChart2,
} from "lucide-vue-next";

const router = useRouter();
const isAuthenticated = ref(false);
const userRole = ref(null);

async function applyAuth() {
  try {
    const u = await getCurrentUser();
    isAuthenticated.value = !!u;
    userRole.value = u?.role ?? null;
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
</script>

<style scoped>
/* 🎨 Paleta basada en el logo */
:root {
  --brand-blue: #1e88e5;
  --brand-dark-blue: #0d47a1;
  --brand-orange: #fb8c00;
  --text-light: #e3f2fd;
  --hover-bg: rgba(251, 140, 0, 0.2);
}

.sidebar {
  background: linear-gradient(
    180deg,
    var(--brand-dark-blue),
    var(--brand-blue)
  );
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
  padding: 0;
  font: inherit;
  cursor: pointer;
  text-align: left;
  transition: color 0.25s;
}
.logout-link:hover {
  color: var(--brand-orange);
  text-decoration: underline;
}
</style>
