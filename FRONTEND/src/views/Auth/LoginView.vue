<template>
  <div class="auth">
    <h2>Iniciar Sesión</h2>
    <form>
      <input type="email" placeholder="Correo electrónico" />
      <input type="password" placeholder="Contraseña" />
      <button>Entrar</button>
    </form>
    <p>¿No tienes cuenta? <a href="/register">Regístrate aquí</a></p>
  </div>
</template>

<style scoped>
.auth { max-width: 400px; margin: auto; text-align: center; }
input { display: block; margin: 10px auto; padding: 8px; width: 80%; }
button { padding: 10px 20px; cursor: pointer; }
</style>
