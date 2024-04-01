/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from '@/plugins'


// Components
import App from './App.vue'
import jQuery from 'jquery'
// Composables
import { createApp } from 'vue'

// 饿了么
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)

registerPlugins(app)

window.$ = jQuery
app.use(ElementPlus)
app.mount('#app')
