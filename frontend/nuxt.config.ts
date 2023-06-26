import { defineNuxtConfig } from "nuxt/config";

export default defineNuxtConfig({
  app: {
    head: {
      meta: [
        { "http-equiv": "x-ua-compatible", content: "IE=edge" },
        { name: "viewport", content: "width=device-width, initial-scale=1.0" },
      ],
      link: [{ rel: "icon", href: "/favicon.ico" }],
    },
  },

  build: {
    transpile: ["chart.js", "primevue"],
  },

  components: {
    dirs: [
      {
        extensions: ["vue"],
        global: true,
        path: "~/components/common/",
        pathPrefix: false,
      },
    ],
  },

  css: [
    "primevue/resources/primevue.css",
    "primeflex/primeflex.css",
    "primeicons/primeicons.css",
    "prismjs/themes/prism-coy.css",
    "~/assets/styles/layout.scss",
  ],

  dir: {
    public: "../public/",
  },

  experimental: {
    reactivityTransform: true,
    treeshakeClientOnly: true,
  },

  imports: {
    autoImport: true,
    addons: {
      vueTemplate: true,
    },
  },

  modules: [
    "@pinia/nuxt",
    "@vueuse/nuxt",
    "@nuxtjs/tailwindcss",
    "~/modules/primevue",
  ],

  nitro: {
    preset: "vercel",
  },

  srcDir: "src/",

  vite: {
    build: {
      sourcemap: false,
    },
    clearScreen: true,
    logLevel: "info",
  },

  runtimeConfig: {
    public: {
      BACKEND_URL: process.env.BACKEND_URL,
      SIGA_TOKEN_URL: process.env.SIGA_TOKEN_URL,
      SIGA_CLIENT_ID: process.env.SIGA_CLIENT_ID,
      SIGA_CLIENT_SECRET: process.env.SIGA_CLIENT_SECRET,
      SIGA_REDIRECT_URI: process.env.SIGA_REDIRECT_URI,
    },
  },
});
