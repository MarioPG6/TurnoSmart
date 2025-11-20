import { createRouter, createWebHistory } from "vue-router";

import LoginView from "../views/user/LoginView.vue";
import RegisterView from "../views/user/RegisterView.vue";
import HomeView from "../views/HomeView.vue";
import NegocioView from "../views/administrador/NegocioView.vue";
import NegocioListView from "../views/administrador/NegocioListView.vue";
import EditarNegocioView from "../views/administrador/EditarNegocioView.vue";
import AdminCitasView from "../views/administrador/AdminCitasView.vue";
import NegocioClienteListView from "../views/cliente/NegocioClienteListView.vue";
import NegocioClienteDetalleView from "../views/cliente/NegocioClienteDetailView.vue";
import CrearCitaView from "../views/cliente/CrearCitaView.vue";
import CitasClienteView from "../views/cliente/CitasClienteView.vue";

const routes = [
  // Home
  {
    path: "/",
    name: "Home",
    component: HomeView,
  },

  // Auth
  {
    path: "/login",
    name: "Login",
    component: LoginView,
  },
  {
    path: "/register",
    name: "Register",
    component: RegisterView,
  },

  {
    path: "/cliente/negocios",
    name: "NegociosCliente",
    component: NegocioClienteListView,
  },
  {
    path: "/cliente/negocios/:id",
    name: "NegocioClienteDetalle",
    component: NegocioClienteDetalleView,
    props: true,
  },
  {
    path: "/cliente/citas/crear/:negocioId",
    name: "CrearCita",
    component: CrearCitaView,
    props: true,
  },
  {
    path: "/cliente/citas",
    name: "CitasCliente",
    component: CitasClienteView,
    props: true,
  },

  {
    path: "/admin/negocios",
    name: "AdminNegocios",
    component: NegocioListView,
  },
  {
    path: "/admin/negocios/crear",
    name: "CrearNegocio",
    component: NegocioView,
  },
  {
    path: "/admin/negocios/editar/:id",
    name: "EditarNegocio",
    component: EditarNegocioView,
    props: true,
  },
  {
    path: "/admin/citas",
    name: "AdminCitas",
    component: AdminCitasView,
     props: true,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
