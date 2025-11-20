<template>
  <div class="container">
    <h1 class="title">Negocios Disponibles</h1>

    <div class="grid">
      <div
        v-for="neg in negocios"
        :key="neg.id"
        class="card"
        @click="goDetail(neg.id)"
      >
        <h2>{{ neg.nombreNegocio }}</h2>
        <p>{{ neg.descripcion }}</p>
        <p><strong>Dirección:</strong> {{ neg.direccion }}</p>
        <p><strong>Horario:</strong> {{ neg.horario }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { obtenerTodosNegocios } from "@/services/negocioService";
import { useRouter } from "vue-router";

const negocios = ref([]);
const router = useRouter();

onMounted(async () => {
  negocios.value = await obtenerTodosNegocios();
});

function goDetail(id) {
  router.push(`/cliente/negocios/${id}`);
}
</script>

<style scoped>
.container {
  padding: 20px;
}

.title {
  text-align: center;
  color: #0d47a1;
  margin-bottom: 20px;
  font-size: 26px;
  font-weight: bold;
}

.grid {
  display: grid;
  gap: 20px;
}

.card {
  background: white;
  padding: 16px;
  border-radius: 12px;
  border: 1px solid #d0d7e2;
  cursor: pointer;
  transition: 0.25s;
}

.card:hover {
  transform: scale(1.02);
  background: #f5f8fc;
}
</style>
