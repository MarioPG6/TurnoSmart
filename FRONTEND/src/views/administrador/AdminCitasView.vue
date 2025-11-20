<template>
  <div class="container">
    <h2>Gestión de Citas del Negocio</h2>

    <div v-if="loading" class="loading">Cargando citas...</div>

    <table v-else class="tabla">
      <thead>
        <tr>
          <th>Cliente</th>
          <th>Teléfono</th>
          <th>Fecha</th>
          <th>Hora</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="cita in citas" :key="cita.id">
          <td>{{ cita.cliente.firstname }} {{ cita.cliente.lastname }}</td>
          <td>{{ cita.cliente.phone }}</td>
          <td>{{ cita.fecha }}</td>
          <td>{{ cita.hora }}</td>
          <td>
            <select v-model="cita.estado" @change="actualizarEstado(cita)">
              <option value="PENDIENTE">PENDIENTE</option>
              <option value="ACEPTADA">ACEPTADA</option>
              <option value="RECHAZADA">RECHAZADA</option>
              <option value="REPROGRAMADA">REPROGRAMADA</option>
              <option value="CANCELADA">CANCELADA</option>
            </select>
          </td>

          <td>
            <button @click="abrirModal(cita)">Reprogramar</button>
          </td>
        </tr>
      </tbody>
    </table>

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
  max-width: 850px;
  margin: 0 auto;
  margin-top: 40px;
  padding: 25px;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  text-align: center;
}

h2 {
  margin-bottom: 25px;
  color: #003366;
  font-size: 22px;
  font-weight: bold;
}

.tabla {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.tabla th {
  background: #eaeff8;
  padding: 12px;
  text-align: left;
  font-weight: bold;
}

.tabla td {
  padding: 12px;
  border-bottom: 1px solid #dcdcdc;
}

.tabla tr:hover {
  background: #f7faff;
}

select {
  padding: 6px;
  border-radius: 6px;
  border: 1px solid #ccc;
}

button {
  padding: 7px 15px;
  background: #ff9900;
  border: none;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  font-weight: 600;
  transition: 0.2s;
}

button:hover {
  background: #cc7a00;
}

/* MODAL */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.45);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-content {
  background: #ffffff;
  padding: 25px;
  width: 380px;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.25);
}

.modal-content h3 {
  text-align: center;
  margin-bottom: 20px;
  color: #003366;
}

.modal-content input {
  width: 100%;
  padding: 8px;
  border-radius: 6px;
  border: 1px solid #ccc;
  margin-bottom: 12px;
}

.modal-actions {
  margin-top: 15px;
  display: flex;
  justify-content: space-between;
}

.cancel {
  background: #e74c3c;
}

.cancel:hover {
  background: #c0392b;
}

</style>
