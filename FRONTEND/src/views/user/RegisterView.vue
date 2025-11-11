<template>
  <div class="container">
    <h2>Registro</h2>
    <form @submit.prevent="handleRegister">
      <input v-model="form.email" placeholder="Correo" required />
      <input v-model="form.password" type="password" placeholder="Contraseña" required />
      <input
        v-model="form.repeatPassword"
        type="password"
        placeholder="Repetir contraseña"
        required
      />
      <input v-model="form.firstname" placeholder="Nombre" required />
      <input v-model="form.lastname" placeholder="Apellido" required />
      <input v-model="form.direction" placeholder="Dirección" />
      <input v-model="form.phone" placeholder="Teléfono" />
      <button type="submit">Registrarse</button>
    </form>

    <p v-if="message" :class="messageClass">{{ message }}</p>
  </div>
</template>

<script setup>
import { reactive, ref, computed } from "vue";
import { useRouter } from "vue-router";
import { register } from "../../services/authService";

const router = useRouter();

const form = reactive({
  email: "",
  password: "",
  repeatPassword: "",
  firstname: "",
  lastname: "",
  direction: "",
  phone: "",
});

const message = ref("");
const isSuccess = ref(false);

const messageClass = computed(() => (isSuccess.value ? "success" : "error"));

async function handleRegister() {
  if (form.password !== form.repeatPassword) {
    message.value = "Las contraseñas no coinciden";
    isSuccess.value = false;
    return;
  }

  try {
    const res = await register(form);

    if (res.token) {
      isSuccess.value = true;
      message.value = "Registro exitoso. Redirigiendo...";
      setTimeout(() => {
        router.push("/login");
      }, 1200);
    } else {
      isSuccess.value = false;
      message.value = "Error en el registro";
    }
  } catch (err) {
    console.error("Error en registro:", err);
    isSuccess.value = false;
    message.value = "Error en el registro";
  }
}
</script>

<style scoped>
.container {
  max-width: 400px;
  margin: 80px auto;
  padding: 20px;
  background: #f5f8fc;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

h2 {
  text-align: center;
  color: #0d47a1; /* Azul oscuro */
  margin-bottom: 20px;
}

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

input {
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  outline: none;
  transition: border-color 0.2s ease;
}

input:focus {
  border-color: #1e88e5;
}

button {
  background-color: #fb8c00;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 6px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

button:hover {
  background-color: #f57c00;
}

p.message {
  text-align: center;
  margin-top: 10px;
  font-weight: 600;
}

/* Colores dinámicos */
.error {
  color: #d32f2f; /* rojo */
  background: #ffebee;
  padding: 6px;
  border-radius: 6px;
}

.success {
  color: #2e7d32; /* verde */
  background: #e8f5e9;
  padding: 6px;
  border-radius: 6px;
}
</style>
