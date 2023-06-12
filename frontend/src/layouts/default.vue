<script lang="ts">
import { defineComponent } from "vue";
import AppTopBar from "~/components/layouts/default/AppTopbar.vue";
import AlunoMenu from "../components/layouts/default/AlunoMenu.vue";
import COEMenuVue from "../components/layouts/default/COEMenu.vue";
import COAFEmenuVue from "../components/layouts/default/COAFEmenu.vue";
import CoordMenuVue from "../components/layouts/default/CoordMenu.vue";
import OrientadorMenuVue from "../components/layouts/default/OrientadorMenu.vue";
import { ofetch } from "ofetch";

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
      this.$toast.removeAllGroups();
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
    const accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
      return this.$router.replace("/login");
    }

    globalThis.$fetch = ofetch.create({
      baseURL: this.$config.BACKEND_URL,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
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
  },
});
</script>

<template>
  <div :class="containerClass" @click="onWrapperClick">
    <AppTopBar @menu-toggle="onMenuToggle" />

    <div class="layout-sidebar" v-if="$route.path.includes('coe')">
      <CoeMenu :model="menu" @menuitem-click="onMenuItemClick" />
    </div>

    <div class="layout-sidebar" v-else-if="$route.path.includes('coord')">
      <CoordMenu :model="menu" @menuitem-click="onMenuItemClick" />
    </div>

    <div class="layout-sidebar" v-else-if="$route.path.includes('coafe')">
      <CoafeMenu :model="menu" @menuitem-click="onMenuItemClick" />
    </div>

    <div class="layout-sidebar" v-else-if="$route.path.includes('orientador')">
      <OrientadorMenu :model="menu" @menuitem-click="onMenuItemClick" />
    </div>

    <div class="layout-sidebar" @click="onSidebarClick" v-else>
      <AlunoMenu :model="menu" @menuitem-click="onMenuItemClick" />
    </div>

    <div class="layout-main-container">
      <div class="layout-main">
        <slot />
      </div>
    </div>

    <Toast />

    <!-- <AppConfig :layout-mode="layoutMode" @layout-change="onLayoutChange" /> -->
    <transition name="layout-mask">
      <div v-if="mobileMenuActive" class="layout-mask p-component-overlay" />
    </transition>
  </div>
</template>

<style lang="scss">
@import "~/assets/styles/App.scss";
</style>
