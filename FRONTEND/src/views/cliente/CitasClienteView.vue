<template>
  <div class="page">
    <h2>Mis Citas</h2>

    <!-- Si está cargando -->
    <div v-if="loading" class="loading">Cargando citas...</div>

    <!-- Si no hay citas -->
    <div v-else-if="citas.length === 0" class="empty">
      No tienes citas registradas.
    </div>

    <!-- Lista de citas -->
    <div v-else class="citas-list">
      <div
        class="cita-card"
        v-for="cita in citas"
        :key="cita.id"
      >
        <div class="cita-info">
          <h3>{{ cita.negocio.nombreNegocio }}</h3>

          <p><strong>Fecha:</strong> {{ formatFecha(cita.fecha) }}</p>
          <p><strong>Hora:</strong> {{ cita.hora }}</p>

          <p>
            <strong>Estado:</strong>
            <span :class="'status ' + cita.estado">
              {{ cita.estado }}
            </span>
          </p>
        </div>

        <!-- Solo se puede cancelar si está PENDIENTE o ACEPTADA -->
        <button
          v-if="['PENDIENTE','ACEPTADA'].includes(cita.estado)"
          class="cancelar-btn"
          @click="cancelarCita(cita.id)"
        >
          Cancelar
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { getCurrentUser } from "../../services/authService.js";
import { obtenerCitasPorCliente, cambiarEstadoCita } from "../../services/citaService.js";

const citas = ref([]);
const loading = ref(true);

// Cargar citas
async function cargarCitas() {
  loading.value = true;

  const user = await getCurrentUser();
  if (!user) {
    citas.value = [];
    loading.value = false;
    return;
  }

  try {
    citas.value = await obtenerCitasPorCliente(user.id);
  } catch (e) {
    console.error("Error obteniendo citas", e);
  } finally {
    loading.value = false;
  }
}

// Cancelar cita
async function cancelarCita(id) {
  const confirmar = confirm("¿Seguro que deseas cancelar esta cita?");
  if (!confirmar) return;

  try {
    await cambiarEstadoCita(id, "CANCELADA");
    alert("Cita cancelada exitosamente");
    cargarCitas();
  } catch (e) {
    console.error("Error cancelando cita", e);
    alert("No se pudo cancelar la cita");
  }
}

function formatFecha(fecha) {
  return new Date(fecha).toLocaleDateString("es-CO", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
}

onMounted(() => {
  cargarCitas();
});
</script>


<style scoped>
.status {
  padding: 4px 8px;
  border-radius: 6px;
  font-weight: bold;
  color: white;
}

/* Estado: PENDIENTE */
.status.PENDIENTE {
  background: #fb8c00;
}

/* Estado: ACEPTADA */
.status.ACEPTADA {
  background: #1e88e5;
}

/* Estado: RECHAZADA */
.status.RECHAZADA {
  background: #8e24aa;
}

/* Estado: REPROGRAMADA */
.status.REPROGRAMADA {
  background: #3949ab;
}

/* Estado: CANCELADA */
.status.CANCELADA {
  background: #e53935;
}

.page {
  padding: 24px;
}

h2 {
  margin-bottom: 20px;
  color: #1e88e5;
}

.loading,
.empty {
  text-align: center;
  padding: 20px;
  color: #555;
}

.citas-list {
  display: grid;
  gap: 16px;
}

.cita-card {
  background: #fff;
  padding: 16px;
  border-radius: 12px;
  border-left: 6px solid #1e88e5;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.cita-info p {
  margin: 4px 0;
}

.status {
  padding: 2px 6px;
  border-radius: 4px;
  font-weight: bold;
  color: white;
}

.status.PENDIENTE {
  background: #fb8c00;
}

.status.CONFIRMADA {
  background: #1e88e5;
}

.status.CANCELADA {
  background: #e53935;
}

.cancelar-btn {
  background: #e53935;
  color: white;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
  transition: 0.2s;
}

.cancelar-btn:hover {
  background: #c62828;
}
</style>
