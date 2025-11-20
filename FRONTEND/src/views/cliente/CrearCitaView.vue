<template>
  <div class="container">
    <h2>Reservar cita</h2>

    <form @submit.prevent="reservar">
      <label>Razón de la cita</label>
      <input v-model="form.razon" required />

      <label>Fecha</label>
      <input type="date" v-model="form.fecha" required />

      <label>Hora</label>
      <input type="time" v-model="form.hora" required />

      <button class="btn">Confirmar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { crearCita } from "../../services/citaService.js";
import { getCurrentUser } from "../../services/authService.js";

const route = useRoute();
const router = useRouter();

const form = ref({
  razon: "",
  fecha: "",
  hora: "",
});

async function reservar() {
  const user = await getCurrentUser();

  const data = {
    idCliente: user.id,
    idNegocio: route.params.id,
    ...form.value,
  };

  await crearCita(data);
  alert("Cita creada con éxito");
  router.push("/cliente/citas");
}
</script>

<style scoped>
.container {
  padding: 20px;
  max-width: 400px;
}

label {
  display: block;
  margin-top: 12px;
}

input {
  width: 100%;
  padding: 8px;
  margin-top: 4px;
  border-radius: 6px;
  border: 1px solid #ccc;
}

.btn {
  margin-top: 20px;
  width: 100%;
  padding: 10px;
  background: #1e88e5;
  border: none;
  color: white;
  border-radius: 6px;
}

.btn:hover {
  background: #0d47a1;
}
</style>
