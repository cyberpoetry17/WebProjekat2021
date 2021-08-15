<template>
    <div>
        <v-app-bar color="#d40078" dense dark>
            <!-- <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon> -->
            <router-link to="/" style="color:white;text-decoration:none;">
                <v-toolbar-title>Welcome to GetFood.com</v-toolbar-title>
            </router-link>

            <v-spacer></v-spacer>

            <v-menu left bottom>
                <template v-slot:activator="{ on, attrs }">
                    <v-btn icon v-bind="attrs" v-on="on">
                        <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                </template>

                <v-list>
                    <v-list-item v-for="n in 5" :key="n" @click="() => {}">
                        <v-list-item-title>Option {{ n }}</v-list-item-title>
                    </v-list-item>
                </v-list>
            </v-menu>

            <router-link v-if="isUserLogged" to="/login">
                <v-btn v-on:click="logout" icon>
                    <v-icon>mdi-export</v-icon>
                </v-btn>
            </router-link>

            <router-link v-else to="/login">
                <v-btn icon>
                    <v-icon>mdi-import</v-icon>
                </v-btn>
            </router-link>
        </v-app-bar>
    </div>
</template>

<script>

module.exports = {
    name: 'Navbar',
    computed: {
        isUserLogged() {
            return this.$store.getters.getIsUserLogged
        },
        user() {
            return this.$store.getters.getUser
        }
    },
    data() {
        return {
            drawer: false
        }
    },
    methods: {
        logout() {
            var user = null;
            this.$store.dispatch('updateUser', user);
        }
    }
}
</script>

<style scoped>


</style>