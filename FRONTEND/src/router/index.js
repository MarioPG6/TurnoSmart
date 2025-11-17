import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/user/LoginView.vue";
import RegisterView from "../views/user/RegisterView.vue";
import HomeView from "../views/HomeView.vue";
import NegocioView from "../views/administrador/NegocioView.vue";
import NegocioListView from "../views/administrador/NegocioListView.vue";
import EditarNegocioView from "../views/administrador/EditarNegocioView.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: HomeView,
  },
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
  component: EditarNegocioView
},
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
