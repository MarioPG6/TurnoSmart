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

    <!-- Título centrado -->
    <RouterLink to="/" class="title-link">
      <h1 class="title">Turno Smart</h1>
    </RouterLink>

    <!-- Navegación -->
    <nav class="menu">
      <template v-if="!isAuthenticated">
        <RouterLink to="/register" class="item">
          <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
            <path
              fill="currentColor"
              d="M12 12a5 5 0 1 0-5-5 5.006 5.006 0 0 0 5 5zm0 2c-3.33 0-10 1.67-10 5v2h20v-2c0-3.33-6.67-5-10-5z"
            />
          </svg>
          Registrar
        </RouterLink>

        <RouterLink to="/login" class="item">
          <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
            <path
              fill="currentColor"
              d="M10 17v-2h4v-2h-4v-2h4V9h-4V7h6v10z"
            />
          </svg>
          Log-in
        </RouterLink>
      </template>

      <template v-else>
        <button class="logout-btn" @click="logout">
          <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
            <path
              fill="currentColor"
              d="M16 13v-2H7V8l-5 4 5 4v-3zM20 3h-9a2 2 0 0 0-2 2v4h2V5h9v14h-9v-4h-2v4a2 2 0 0 0 2 2h9a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2z"
            />
          </svg>
          Cerrar Sesión
        </button>

        <!-- Logo junto al botón cuando está autenticado -->
        <img
          src="/turnosmart_transparent.png"
          alt="Turno Smart Logo"
          class="logo-inline"
        />
      </template>
    </nav>

    <!-- Logo fijo solo si no está autenticado -->
    <img
      v-if="!isAuthenticated"
      src="/turnosmart_transparent.png"
      alt="Turno Smart Logo"
      class="logo-fixed"
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

.menu {
  display: flex;
  align-items: center;
  gap: 12px;
}

.icon {
  width: 18px;
  height: 18px;
  margin-right: 6px;
  vertical-align: middle;
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

.item {
  display: flex;
  align-items: center;
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
  display: flex;
  align-items: center;
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

.logo-fixed {
  position: absolute;
  right: 16px;
  height: 38px;
  object-fit: contain;
  filter: drop-shadow(0 0 3px rgba(0, 0, 0, 0.3));
  transition: transform 0.3s ease;
}
.logo-fixed:hover {
  transform: scale(1.05);
}

.logo-inline {
  height: 38px;
  object-fit: contain;
  filter: drop-shadow(0 0 3px rgba(0, 0, 0, 0.3));
  transition: transform 0.3s ease;
}
.logo-inline:hover {
  transform: scale(1.05);
}
</style>
