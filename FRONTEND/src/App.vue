<template>
  <div class="app">
    <Topbar @toggle-sidebar="isSidebarOpen = !isSidebarOpen" />

    <div
      class="overlay"
      v-show="isMobile && isSidebarOpen"
      @click="isSidebarOpen = false"
    ></div>

    <aside class="sidebar-shell" :class="{ open: isSidebarOpen }">
      <Sidebar />
    </aside>

    <main class="content" :class="{ pushed: isSidebarOpen && !isMobile }">
      <RouterView />
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { RouterView } from "vue-router";
import Topbar from "./components/Topbar.vue";
import Sidebar from "./components/Sidebar.vue";

const isSidebarOpen = ref(false);
const isMobile = ref(false);
const update = () => (isMobile.value = matchMedia("(max-width:900px)").matches);

onMounted(() => {
  update();
  addEventListener("resize", update);
});
onBeforeUnmount(() => removeEventListener("resize", update));
</script>

<style>
:root {
  --topbar-h: 56px;
  --sidebar-w: 260px;

  /* 🎨 Colores basados en el logo */
  --brand-blue: #1e88e5;
  --brand-dark-blue: #0d47a1;
  --brand-orange: #fb8c00;
  --text-dark: #212121;
  --bg-light: #f5f5f5;

  /* Aplicación del color a secciones */
  --topbar-bg: var(--brand-blue);
  --sidebar-bg: var(--brand-dark-blue);
}

body {
  background: var(--bg-light);
  color: var(--text-dark);
}

.overlay {
  position: fixed;
  inset: 0;
  z-index: 1040;
  background: rgba(0, 0, 0, 0.45);
}
@media (min-width: 901px) {
  .overlay {
    display: none;
  }
}

.sidebar-shell {
  position: fixed;
  top: var(--topbar-h);
  left: 0;
  width: var(--sidebar-w);
  height: calc(100vh - var(--topbar-h));
  background: var(--sidebar-bg);
  transform: translateX(-100%);
  transition: transform 0.2s ease;
  z-index: 1050;
  overflow-y: auto;
}
.sidebar-shell.open {
  transform: translateX(0);
}

.content {
  padding: calc(var(--topbar-h) + 24px) 16px 24px;
  transition: margin-left 0.2s ease;
}

/* Ejemplo para acentos */
button,
a {
  color: white;
  background-color: var(--brand-orange);
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.2s;
}
button:hover,
a:hover {
  background-color: #f57c00;
}
</style>
