<template>
  <div class="admin-container p-6 min-h-screen">

    <h1 class="title">Lista de Mis Negocios</h1>

    <button @click="goCreate" class="btn-primary mb-4">
      + Crear Nuevo Negocio
    </button>

    <!-- CARD -->
    <div class="section mt-4">

      <table class="styled-table w-full">
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Teléfono</th>
            <th>Dirección</th>
            <th>Horario</th>
            <th class="text-center">Acciones</th> <!-- NUEVA COLUMNA -->
          </tr>
        </thead>

        <tbody>
          <tr v-for="neg in negocios" :key="neg.id">
            <td>{{ neg.nombreNegocio }}</td>
            <td>{{ neg.telefono }}</td>
            <td>{{ neg.direccion }}</td>
            <td>{{ neg.horario }}</td>

            <!-- BOTONES -->
            <td class="text-center">
              <button class="btn-edit" @click="goEdit(neg.id)">Editar</button>
              <button class="btn-delete" @click="goDelete(neg.id)">Eliminar</button>
            </td>
          </tr>
        </tbody>
      </table>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

import { getCurrentUser } from "@/services/authService";
import { obtenerNegociosPorUsuario } from "@/services/negocioService";

const router = useRouter();
const negocios = ref([]);

onMounted(async () => {
  const user = getCurrentUser();
  if (!user) {
    router.push("/login");
    return;
  }

  negocios.value = await obtenerNegociosPorUsuario(user.id);
});

function goCreate() {
  router.push("/admin/negocios/crear");
}

function goEdit(id) {
  router.push(`/admin/negocios/editar/${id}`);
}

function goDelete(id) {
  if (confirm("¿Seguro que deseas eliminar este negocio?")) {
    router.push(`/admin/negocios/eliminar/${id}`);
  }
}
</script>

<style scoped>
/* ----------- Mismos estilos que en Crear Negocio ----------- */

.admin-container {
  background: #f5f8fc;
  border-radius: 10px;
}

.title {
  text-align: center;
  color: #0d47a1;
  margin-bottom: 20px;
  font-size: 26px;
  font-weight: bold;
}

.section {
  background: white;
  padding: 20px;
  border-radius: 12px;
  border: 1px solid #d0d7e2;
  box-shadow: 0px 2px 5px rgba(0,0,0,0.08);
}

/* ----- TABLA con tu estilo corporativo ----- */
.styled-table {
  border-collapse: collapse;
  width: 100%;
}

.styled-table th {
  background: #0d47a1;
  color: white;
  padding: 10px;
  font-weight: 600;
  text-align: left;
}

.styled-table td {
  padding: 10px;
  border-bottom: 1px solid #d0d7e2;
}

.styled-table tr:hover {
  background: #f1f4fa;
}

/* Botones principales */
.btn-primary {
  background-color: #fb8c00;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 6px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.btn-primary:hover {
  background-color: #f57c00;
}

/* ----- BOTONES DENTRO DE LA TABLA ----- */
.btn-edit {
  background-color: #1e88e5;
  color: white;
  padding: 6px 10px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 13px;
  margin-right: 6px;
  transition: 0.2s;
}

.btn-edit:hover {
  background-color: #1565c0;
}

.btn-delete {
  background-color: #e53935;
  color: white;
  padding: 6px 10px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 13px;
  transition: 0.2s;
}

.btn-delete:hover {
  background-color: #c62828;
}
</style>
