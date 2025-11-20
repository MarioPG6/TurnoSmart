<template>
  <div class="container">
    <button class="btn-back" @click="router.back()">⬅ Volver</button>

    <h1 class="title">{{ negocio.nombreNegocio }}</h1>

    <div class="card">
      <p><strong>Descripción:</strong> {{ negocio.descripcion }}</p>
      <p><strong>Dirección:</strong> {{ negocio.direccion }}</p>
      <p><strong>Teléfono:</strong> {{ negocio.telefono }}</p>

      <h3 class="subtitle">Horario de Atención:</h3>
      <p>{{ negocio.horario }}</p>
    </div>

    <div class="card mt-4">
      <h2 class="subtitle">Reservar una Cita</h2>

      <form @submit.prevent="reservar">
        <label>Fecha</label>
        <input type="date" class="input" v-model="form.fecha" required />

        <label>Hora</label>
        <input type="time" class="input" v-model="form.hora" required />

        <label>Razón</label>
        <textarea class="input" v-model="form.razon" required></textarea>

        <button class="btn-primary mt-3">Reservar</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { obtenerNegocioPorId } from "@/services/negocioService";
import { crearCita } from "@/services/citaService";
import { getCurrentUser } from "@/services/authService";

const route = useRoute();
const router = useRouter();
const negocio = ref({});
const form = ref({
  fecha: "",
  hora: "",
  razon: "",
});

onMounted(async () => {
  negocio.value = await obtenerNegocioPorId(route.params.id);
});

async function reservar() {
  const user = getCurrentUser();
  if (!user) {
    alert("Debes iniciar sesión");
    return router.push("/login");
  }

  const payload = {
    idCliente: user.id,
    idNegocio: negocio.value.id,
    razon: form.value.razon,
    fecha: form.value.fecha,
    hora: form.value.hora,
  };

  try {
    await crearCita(payload);
    alert("Cita reservada con éxito");
    router.push("/cliente/citas");
  } catch (err) {
    alert("Error creando cita");
  }
}
</script>

<style scoped>
.container {
  padding: 20px;
}

.title {
  text-align: center;
  color: #0d47a1;
  font-size: 24px;
  font-weight: bold;
}

.subtitle {
  color: #0d47a1;
  font-weight: bold;
  margin-top: 10px;
  margin-bottom: 6px;
}

.card {
  background: white;
  padding: 16px;
  border-radius: 12px;
  border: 1px solid #d0d7e2;
}

.btn-back {
  background: transparent;
  border: none;
  color: #1e88e5;
  cursor: pointer;
  margin-bottom: 10px;
}

.input {
  width: 100%;
  padding: 8px;
  border-radius: 6px;
  border: 1px solid #ccc;
  margin-bottom: 8px;
}

.btn-primary {
  background-color: #fb8c00;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}
</style>
