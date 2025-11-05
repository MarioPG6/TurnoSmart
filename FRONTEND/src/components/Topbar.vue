<template>
  <header class="topbar">
    <!-- Botón de menú lateral -->
    <button
      v-if="isAuthenticated"
      class="burger"
      @click="onBurger"
      aria-label="Abrir menú"
    >
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
        <path
          d="M3 6h18M3 12h18M3 18h18"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
        />
      </svg>
    </button>

    <!-- 🟦 Título centrado -->
    <RouterLink to="/" class="title-link">
      <h1 class="title">Turno Smart</h1>
    </RouterLink>

    <!-- Navegación -->
    <nav class="menu">
      <template v-if="!isAuthenticated">
        <RouterLink to="/register" class="item">Registrar</RouterLink>
        <RouterLink to="/login" class="item">Log-in</RouterLink>
      </template>
      <template v-else>
        <button class="logout-btn" @click="logout">Cerrar Sesión</button>
      </template>
    </nav>

    <!-- 🟠 Logo en la esquina derecha -->
    <img
      src="/turnosmart_transparent.png"
      alt="Turno Smart Logo"
      class="logo"
    />
  </header>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { RouterLink, useRouter } from "vue-router";

const emit = defineEmits(["toggle-sidebar"]);
const router = useRouter();
const isAuthenticated = ref(false);

function onBurger() {
  emit("toggle-sidebar");
}

function applyAuth() {
  isAuthenticated.value = !!localStorage.getItem("token");
}

function logout() {
  localStorage.removeItem("token");
  applyAuth();
  emit("toggle-sidebar");
  window.dispatchEvent(new Event("auth-changed"));
  router.push("/login");
}

onMounted(() => {
  applyAuth();
  window.addEventListener("auth-changed", applyAuth);
  window.addEventListener("storage", applyAuth);
});

onBeforeUnmount(() => {
  window.removeEventListener("auth-changed", applyAuth);
  window.removeEventListener("storage", applyAuth);
});
</script>

<style scoped>
:root {
  --brand-blue: #1e88e5;
  --brand-dark-blue: #0d47a1;
  --brand-orange: #fb8c00;
  --text-light: #e3f2fd;
}

.topbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 56px;
  z-index: 1100;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  background: linear-gradient(90deg, var(--brand-dark-blue), var(--brand-blue));
  color: var(--text-light);
  border-bottom: 2px solid var(--brand-orange);
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.25);
}

.title-link {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  color: inherit;
  text-decoration: none;
}

.title {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 700;
  letter-spacing: 0.5px;
  color: var(--text-light);
}

/* 🟠 Logo en la esquina derecha */
.logo {
  position: absolute;
  right: 16px;
  height: 38px;
  object-fit: contain;
  filter: drop-shadow(0 0 3px rgba(0, 0, 0, 0.3));
  transition: transform 0.3s ease;
}
.logo:hover {
  transform: scale(1.05);
}

.burger {
  width: 40px;
  height: 40px;
  border: 0;
  border-radius: 8px;
  background: transparent;
  color: inherit;
  cursor: pointer;
  transition: background 0.25s;
}
.burger:hover {
  background: rgba(255, 255, 255, 0.15);
}

.menu {
  display: flex;
  align-items: center;
  gap: 16px;
}

.item {
  color: var(--text-light);
  text-decoration: none;
  font-size: 0.95rem;
  padding: 6px 10px;
  border-radius: 6px;
  transition: background 0.25s, color 0.25s;
}
.item:hover {
  background: rgba(251, 140, 0, 0.2);
  color: #fff;
}
.item.router-link-active {
  background: var(--brand-orange);
  color: #fff;
}

.logout-btn {
  background: none;
  border: none;
  color: var(--brand-orange);
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  border-radius: 6px;
  padding: 6px 10px;
  transition: background 0.25s, color 0.25s;
}
.logout-btn:hover {
  background: rgba(251, 140, 0, 0.2);
  color: #fff;
}
</style>
