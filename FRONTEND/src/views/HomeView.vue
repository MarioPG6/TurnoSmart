<template>
  <section class="hero">
    <div class="hero-text">
      <h1 class="hero-title">GESTIÓN INTELIGENTE DE CITAS</h1>
      <p class="hero-subtitle">
        <strong>Turno Smart</strong> es una plataforma moderna para el agendamiento
        eficiente de citas. Permite a empresas, profesionales y usuarios
        administrar turnos de manera ágil, reduciendo tiempos de espera y
        mejorando la atención al cliente. Todo desde una interfaz sencilla,
        rápida y accesible desde cualquier dispositivo.
      </p>

      <div class="hero-buttons">
        <RouterLink :to="authLink" class="btn primary">
          ¡Agendar ahora!
        </RouterLink>
      </div>
    </div>

    <div class="hero-image">
      <img src="/turnos.png" alt="Sistema de agendamiento de citas" />
    </div>
  </section>

  <section class="objectives">
    <div class="objectives-grid">
      <div class="objectives-img">
        <img src="/objetivos-turnos.png" alt="Gestión de citas eficiente" />
      </div>

      <div class="objectives-list">
        <h2>Propósitos y Objetivos</h2>

        <div class="cards">
          <div class="objective-card">
            <h3>Automatización de citas</h3>
            <p>
              Turno Smart simplifica el proceso de programación y gestión de
              citas, eliminando la necesidad de registros manuales y reduciendo
              errores en la asignación de horarios.
            </p>
          </div>
          <div class="objective-card">
            <h3>Experiencia del usuario</h3>
            <p>
              Ofrece una interfaz amigable e intuitiva que mejora la
              comunicación entre los usuarios y las entidades que gestionan
              servicios.
            </p>
          </div>
          <div class="objective-card">
            <h3>Optimización del tiempo</h3>
            <p>
              Permite aprovechar mejor los recursos humanos y de atención,
              reduciendo tiempos muertos y maximizando la productividad diaria.
            </p>
          </div>
          <div class="objective-card">
            <h3>Notificaciones y recordatorios</h3>
            <p>
              El sistema envía alertas automáticas para recordar a los usuarios
              sus citas y evitar cancelaciones o retrasos inesperados.
            </p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";

const authLink = ref("/register");

onMounted(() => {
  const token = localStorage.getItem("token");
  if (token) authLink.value = "/citas/nueva";
});
</script>

<style scoped>
.hero {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 80px 80px;
  gap: 40px;
  background: linear-gradient(135deg, #00AEEF 0%, #0052A3 100%);
  color: #fff;
}

.hero-text {
  flex: 1;
  max-width: 600px;
  margin-left: 200px;
}

.hero-title {
  font-size: 3rem;
  font-weight: 800;
  color: #fff;
  margin-bottom: 20px;
}

.hero-subtitle {
  font-size: 1.25rem;
  line-height: 1.6;
  color: #f0f0f0;
  margin-bottom: 30px;
}

.hero-buttons {
  display: flex;
  gap: 16px;
}

.btn {
  padding: 12px 24px;
  border-radius: 6px;
  text-decoration: none;
  font-weight: 600;
  transition: background 0.2s ease, transform 0.2s ease;
}

.btn.primary {
  background: #FF8C00;
  color: #fff;
}

.btn.primary:hover {
  background: #e57a00;
  transform: translateY(-2px);
}

.hero-image img {
  max-width: 100%;
  width: 700px;
  height: 500px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  background: white;
}

.objectives {
  padding: 60px 20px;
  background: #f9fafb;
}

.objectives-grid {
  display: grid;
  grid-template-columns: 500px 1fr;
  column-gap: 60px;
  align-items: start;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.objectives-list {
  margin-left: 180px;
  width: 100%;
}

.cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
}

.objectives-img {
  width: 700px;
  height: 500px;
}

.objectives-img img {
  width: 100%;
  height: 500px;
  object-fit: contain;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  display: block;
}

.objectives-list h2 {
  font-size: 2rem;
  font-weight: 800;
  color: #0052A3;
  margin: 0 0 50px 0;
}

.objective-card {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 4px 12px rgba(0, 82, 163, 0.15);
  transition: transform 0.2s, box-shadow 0.2s;
}
.objective-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 6px 16px rgba(0, 82, 163, 0.25);
}
.objective-card h3 {
  color: #FF8C00;
  font-size: 1.1rem;
  margin: 0 0 6px;
}
.objective-card p {
  color: #333;
  font-size: 0.95rem;
  line-height: 1.4;
}

@media (max-width: 900px) {
  .objectives-grid {
    grid-template-columns: 1fr;
    column-gap: 0;
    row-gap: 28px;
  }
  .objectives-list {
    padding-left: 0;
    text-align: center;
  }
  .cards {
    grid-template-columns: 1fr;
  }
}
</style>

