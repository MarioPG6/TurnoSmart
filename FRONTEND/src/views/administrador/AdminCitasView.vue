<template>
  <div class="container">
    <h2>Gestión de Citas del Negocio</h2>

    <div v-if="loading" class="loading">Cargando citas...</div>

    <table v-else class="tabla">
      <thead>
        <tr>
          <th>Cliente</th>
          <th>Fecha</th>
          <th>Hora</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="cita in citas" :key="cita.id">
         <td>{{ cita.cliente.firstname }} {{ cita.cliente.lastname }}</td>
          <td>{{ cita.fecha }}</td>
          <td>{{ cita.hora }}</td>

          <!-- ESTADO -->
          <td>
            <select v-model="cita.estado" @change="actualizarEstado(cita)">
              <option value="PENDIENTE">PENDIENTE</option>
              <option value="ACEPTADA">ACEPTADA</option>
              <option value="RECHAZADA">RECHAZADA</option>
              <option value="REPROGRAMADA">REPROGRAMADA</option>
              <option value="CANCELADA">CANCELADA</option>
            </select>
          </td>

          <!-- BOTÓN REPROGRAMAR -->
          <td>
            <button @click="abrirModal(cita)">Reprogramar</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- MODAL PARA REPROGRAMAR -->
    <div v-if="modal" class="modal">
      <div class="modal-content">
        <h3>Reprogramar Cita</h3>

        <label>Nueva fecha:</label>
        <input type="date" v-model="form.fecha" />

        <label>Nueva hora:</label>
        <input type="time" v-model="form.hora" />

        <div class="modal-actions">
          <button @click="guardarReprogramacion">Guardar</button>
          <button class="cancel" @click="modal = false">Cancelar</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import {
  obtenerCitasPorNegocio,
  cambiarEstadoCita,
  editarCita
} from "../../services/citaService.js";

const negocioId = localStorage.getItem("negocioId");

const citas = ref([]);
const loading = ref(true);

const modal = ref(false);
const citaSeleccionada = ref(null);

const form = ref({
  fecha: "",
  hora: "",
});

// Cargar citas del negocio
async function cargarCitas() {
  if (!negocioId) {
    console.error("❌ No hay negocioId en localStorage");
    loading.value = false;
    return;
  }

  loading.value = true;

  try {
    citas.value = await obtenerCitasPorNegocio(negocioId);
  } catch (error) {
    console.error("❌ Error cargando citas:", error);
  }

  loading.value = false;
}

// Abrir modal y precargar fecha/hora
function abrirModal(cita) {
  citaSeleccionada.value = cita;
  form.value.fecha = cita.fecha;
  form.value.hora = cita.hora;
  modal.value = true;
}

// Guardar reprogramación usando editarCita()
async function guardarReprogramacion() {
  await editarCita(citaSeleccionada.value.id, {
    fecha: form.value.fecha,
    hora: form.value.hora,
    estado: "REPROGRAMADA"
  });

  modal.value = false;
  await cargarCitas();
}

// Cambiar estado individual
async function actualizarEstado(cita) {
  await cambiarEstadoCita(cita.id, cita.estado);
}

onMounted(() => {
  cargarCitas();
});
</script>


<style scoped>
.container {
  padding: 20px;
}

.tabla {
  width: 100%;
  border-collapse: collapse;
}

.tabla th,
.tabla td {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

button {
  padding: 6px 12px;
  cursor: pointer;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: #fff;
  padding: 20px;
  width: 350px;
  border-radius: 8px;
}

.modal-actions {
  margin-top: 15px;
  display: flex;
  justify-content: space-between;
}

.cancel {
  background: #ff4444;
  color: white;
}
</style>
