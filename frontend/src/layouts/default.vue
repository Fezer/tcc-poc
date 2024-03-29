<script lang="ts">
import { defineComponent } from "vue";
import AppTopBar from "~/components/layouts/default/AppTopbar.vue";
import AlunoMenu from "../components/layouts/default/AlunoMenu.vue";
import COEMenuVue from "../components/layouts/default/COEMenu.vue";
import COAFEmenuVue from "../components/layouts/default/COAFEmenu.vue";
import CoordMenuVue from "../components/layouts/default/CoordMenu.vue";
import OrientadorMenuVue from "../components/layouts/default/OrientadorMenu.vue";
import { ofetch } from "ofetch";
import parseJwt from "../utils/parseJWT";

export default defineComponent({
  components: {
    AppTopBar,
    AlunoMenu,
    CoeMenu: COEMenuVue,
    CoafeMenu: COAFEmenuVue,
    CoordMenu: CoordMenuVue,
    OrientadorMenu: OrientadorMenuVue,
  },
  data() {
    return {
      layoutMode: "static",
      menuActive: false,
      menuClick: false,
      staticMenuInactive: false,
      overlayMenuActive: false,
      mobileMenuActive: false,
    };
  },
  computed: {
    containerClass() {
      return [
        "layout-wrapper",
        {
          "layout-overlay": this.layoutMode === "overlay",
          "layout-static": this.layoutMode === "static",
          "layout-static-sidebar-inactive":
            this.staticMenuInactive && this.layoutMode === "static",
          "layout-overlay-sidebar-active":
            this.overlayMenuActive && this.layoutMode === "overlay",
          "layout-mobile-sidebar-active": this.mobileMenuActive,
          "p-input-filled": this.$primevue.config.inputStyle === "filled",
          "p-ripple-disabled": this.$primevue.config.ripple === false,
          "layout-theme-light": this.$appState.theme?.startsWith("saga"),
        },
      ];
    },
    logo() {
      return this.$appState.darkTheme
        ? "/images/logo-white.svg"
        : "/images/logo.svg";
    },
  },
  watch: {
    $route() {
      this.menuActive = false;
    },
  },
  beforeUpdate() {
    if (this.mobileMenuActive) {
      this.addClass(document.body, "body-overflow-hidden");
    } else {
      this.removeClass(document.body, "body-overflow-hidden");
    }
  },
  mounted() {
    if (!process?.client) return;
    const alunoToken = localStorage.getItem("accessToken");
    const atoresToken = localStorage.getItem("atoresToken");

    if (!alunoToken && !atoresToken) {
      return this.$router.replace("/login");
    }

    if (this.$route.path.includes("/aluno")) {
      const accessToken = localStorage.getItem("accessToken");

      if (!accessToken) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("atoresToken");
        return this.$router.replace("/login");
      }
    } else {
      const atoresToken = localStorage.getItem("atoresToken");

      if (!atoresToken) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("atoresToken");
        return this.$router.replace("/login");
      }
    }

    if (atoresToken) {
      globalThis.$fetch = ofetch.create({
        baseURL: this.$config.BACKEND_URL || "http://localhost:5000",
      });
    } else {
      globalThis.$fetch = ofetch.create({
        baseURL: this.$config.BACKEND_URL || "http://localhost:5000",
        headers: {
          Authorization: `Bearer ${alunoToken}`,
        },
      });
    }
  },
  methods: {
    onWrapperClick() {
      if (!this.menuClick) {
        this.overlayMenuActive = false;
        this.mobileMenuActive = false;
      }

      this.menuClick = false;
    },
    onMenuToggle(event: Event) {
      this.menuClick = true;

      if (this.isDesktop()) {
        if (this.layoutMode === "overlay") {
          if (this.mobileMenuActive) {
            this.overlayMenuActive = true;
          }

          this.overlayMenuActive = !this.overlayMenuActive;
          this.mobileMenuActive = false;
        } else if (this.layoutMode === "static") {
          this.staticMenuInactive = !this.staticMenuInactive;
        }
      } else {
        this.mobileMenuActive = !this.mobileMenuActive;
      }

      event.preventDefault();
    },
    onSidebarClick() {
      this.menuClick = true;
    },
    onMenuItemClick(event: any) {
      if (event.item && !event.item.items) {
        this.overlayMenuActive = false;
        this.mobileMenuActive = false;
      }
    },
    onLayoutChange(layoutMode: string) {
      this.layoutMode = layoutMode;
    },
    addClass(element: Element, className: string) {
      if (element.classList) {
        element.classList.add(className);
      } else {
        element.className += ` ${className}`;
      }
    },
    removeClass(element: Element, className: string) {
      if (element.classList) {
        element.classList.remove(className);
      } else {
        element.className = element.className.replace(
          new RegExp(`(^|\\b)${className.split(" ").join("|")}(\\b|$)`, "gi"),
          " "
        );
      }
    },
    isDesktop() {
      return window.innerWidth >= 992;
    },
    isSidebarVisible() {
      if (this.isDesktop()) {
        if (this.layoutMode === "static") {
          return !this.staticMenuInactive;
        } else if (this.layoutMode === "overlay") {
          return this.overlayMenuActive;
        }
      }

      return true;
    },

    getCurrentProfile(): string {
      if (!process.client) return "";
      const isAlunoToken = localStorage.getItem("accessToken");
      const token = localStorage.getItem("atoresToken");

      if (!token && !isAlunoToken) {
        this.$router.replace("/login");
        return "";
      }

      if (isAlunoToken) return "Aluno";

      const profile = parseJwt(token)?.tipo;

      return profile || "";
    },
  },
});
</script>

<template>
  <div :class="containerClass" @click="onWrapperClick">
    <AppTopBar @menu-toggle="onMenuToggle" />

    <div class="layout-sidebar" v-if="getCurrentProfile().includes('COE')">
      <CoeMenu @menuitem-click="onMenuItemClick" />
    </div>

    <div
      class="layout-sidebar"
      v-else-if="getCurrentProfile().includes('Coordenacao')"
    >
      <CoordMenu @menuitem-click="onMenuItemClick" />
    </div>

    <div
      class="layout-sidebar"
      v-else-if="getCurrentProfile().includes('COAFE')"
    >
      <CoafeMenu @menuitem-click="onMenuItemClick" />
    </div>

    <div
      class="layout-sidebar"
      v-else-if="getCurrentProfile().includes('Orientador')"
    >
      <OrientadorMenu @menuitem-click="onMenuItemClick" />
    </div>

    <div
      class="layout-sidebar"
      @click="onSidebarClick"
      v-else-if="getCurrentProfile().includes('Aluno')"
    >
      <AlunoMenu @menuitem-click="onMenuItemClick" />
    </div>

    <div class="layout-main-container">
      <div class="layout-main">
        <Toast />
        <slot />
      </div>
    </div>

    <!-- <AppConfig :layout-mode="layoutMode" @layout-change="onLayoutChange" /> -->
    <transition name="layout-mask">
      <div v-if="mobileMenuActive" class="layout-mask p-component-overlay" />
    </transition>
  </div>
</template>

<style lang="scss">
@import "~/assets/styles/App.scss";
</style>
