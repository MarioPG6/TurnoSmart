<template>
  <div class="admin-container p-6">
    <h1 class="title">Panel Administrador - Crear Negocio</h1>

    <form @submit.prevent="submitForm" class="grid gap-4 max-w-xl mx-auto">

      <!-- NOMBRE -->
      <div>
        <label class="label">Nombre del Negocio</label>
        <input v-model="form.nombreNegocio" type="text" class="input" required />
      </div>

      <!-- DESCRIPCION -->
      <div>
        <label class="label">Descripción</label>
        <textarea v-model="form.descripcion" class="input" rows="3"></textarea>
      </div>

      <!-- DIRECCION -->
      <div>
        <label class="label">Dirección</label>
        <input v-model="form.direccion" type="text" class="input" />
      </div>

      <!-- TELEFONO -->
      <div>
        <label class="label">Teléfono</label>
        <input v-model="form.telefono" type="text" class="input" />
      </div>

      <!-- HORARIO AUTO -->
      <div>
        <label class="label">Horario (se genera automáticamente)</label>
        <textarea class="input readonly" :value="generateHorario()" disabled></textarea>
      </div>

      <!-- BLOQUE LUNES A VIERNES -->
      <div class="section">
        <h2 class="section-title">Lunes a Viernes</h2>

        <div class="flex items-center gap-3">
          <label>Atiende</label>
          <input type="checkbox" v-model="weekday.active" />
        </div>

        <div class="grid grid-cols-2 gap-3 mt-2">
          <div>
            <label>Hora Inicio</label>
            <input type="time" v-model="weekday.start" :disabled="!weekday.active" class="input" />
          </div>

          <div>
            <label>Hora Cierre</label>
            <input type="time" v-model="weekday.end" :disabled="!weekday.active" class="input" />
          </div>
        </div>
      </div>

      <!-- BLOQUE FINES DE SEMANA -->
      <div class="section">
        <h2 class="section-title">Fines de Semana (Sábado y Domingo)</h2>

        <div class="flex items-center gap-3">
          <label>Atiende</label>
          <input type="checkbox" v-model="weekend.active" />
        </div>

        <div class="grid grid-cols-2 gap-3 mt-2">
          <div>
            <label>Hora Inicio</label>
            <input type="time" v-model="weekend.start" :disabled="!weekend.active" class="input" />
          </div>

          <div>
            <label>Hora Cierre</label>
            <input type="time" v-model="weekend.end" :disabled="!weekend.active" class="input" />
          </div>
        </div>
      </div>

      <button type="submit" class="btn-primary">
        Guardar Negocio
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

import { getCurrentUser } from "@/services/authService";
import { crearNegocio } from "@/services/negocioService";

const router = useRouter();

const form = ref({
  nombreNegocio: "",
  descripcion: "",
  direccion: "",
  telefono: "",
});

// NUEVA CONFIGURACIÓN
const weekday = ref({
  active: false,
  start: "",
  end: "",
});

const weekend = ref({
  active: false,
  start: "",
  end: "",
});

function generateHorario() {
  let parts = [];

  if (weekday.value.active) {
    parts.push(`Lunes-Viernes: ${weekday.value.start} - ${weekday.value.end}`);
  }

  if (weekend.value.active) {
    parts.push(`Sábado-Domingo: ${weekend.value.start} - ${weekend.value.end}`);
  }

  return parts.join(" | ");
}

async function submitForm() {
  const user = getCurrentUser();
  if (!user) {
    alert("Debes iniciar sesión");
    return router.push("/login");
  }

  const payload = {
    ...form.value,
    horario: generateHorario(),
  };

  try {
    await crearNegocio(user.id, payload);
    alert("Negocio creado correctamente");
    router.push("/admin/negocios");
  } catch (error) {
    alert("Error creando negocio");
    console.error(error);
  }
}
</script>

<style scoped>
.admin-container {
  background: #f5f8fc;
  border-radius: 10px;
  padding: 20px;
}

.title {
  text-align: center;
  color: #0d47a1;
  margin-bottom: 20px;
  font-size: 26px;
  font-weight: bold;
}

.label {
  font-weight: 600;
  color: #0d47a1;
}

.section {
  background: white;
  padding: 15px;
  border-radius: 10px;
  border: 1px solid #d0d7e2;
}

.section-title {
  color: #0d47a1;
  font-weight: 700;
  margin-bottom: 10px;
}

.input {
  width: 100%;
  border: 1px solid #ccc;
  padding: 8px;
  border-radius: 6px;
  outline: none;
  transition: border-color 0.2s ease;
}

.input:focus {
  border-color: #1e88e5;
}

.readonly {
  background: #e8edf5;
}

.btn-primary {
  background-color: #fb8c00;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 6px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.btn-primary:hover {
  background-color: #f57c00;
}
</style>
