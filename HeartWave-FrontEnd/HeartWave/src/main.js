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


export var user = {
    id: 0,
    name: null,
    avatar: null,
    email: null
};

export var resetUser = {
    id: 0,
    name: null,
    phone: null
}

export var ws = null;
export function setWs(port) {
    ws = new WebSocket("ws://localhost:" + port.toString());
}
export function closeWs() {
    if (ws != null && ws.readyState != ws.CLOSED) {
        ws.close();
    }
}