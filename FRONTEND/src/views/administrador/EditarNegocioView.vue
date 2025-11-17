<template>
  <div class="admin-container p-6 min-h-screen">

    <h1 class="title">Editar Negocio</h1>

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

      <!-- HORARIO -->
      <div>
        <label class="label">Horario</label>
        <input v-model="form.horario" type="text" class="input" />
      </div>

      <button type="submit" class="btn-primary mt-4">Actualizar Negocio</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { obtenerNegocioPorId, actualizarNegocio } from "@/services/negocioService";

const router = useRouter();
const route = useRoute();
const form = ref({});

onMounted(async () => {
  const id = route.params.id;
  const data = await obtenerNegocioPorId(id);
  form.value = { ...data };
});

async function submitForm() {
  try {
    await actualizarNegocio(form.value.id, form.value);
    alert("Negocio actualizado correctamente");
    router.push("/admin/negocios");
  } catch (e) {
    alert("Error actualizando negocio");
  }
}
</script>

<style scoped>
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
.label {
  font-weight: 600;
  color: #0d47a1;
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
