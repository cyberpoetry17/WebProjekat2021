<template>
    <v-app-bar clipped-left color="#d40078" dense dark app>
        <router-link to="/" style="color:white;text-decoration:none;">
            <v-toolbar-title>Welcome to GetFood.com</v-toolbar-title>
        </router-link>

        <v-spacer></v-spacer>

        <div v-if="user != null">
            <!-- <router-link to="/shopping-cart"> -->
                <v-icon v-if="user.userType == 'CUSTOMER'" v-on:click="makeOrder">
                    mdi-basket
                </v-icon>
            <!-- </router-link> -->
        </div>
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
            window.sessionStorage.clear();
            var user = null;
            this.$store.dispatch('updateUser', user);
            this.$router.push({name: 'Login'});
        },
        makeOrder() {
            if(this.user.shoppingCart.articles.length == 0) {
                alert("Shopping cart is emtpy.");
                return;
            }
            axios.get("http://localhost:8080/rest/order/make-order/" + this.user.id)
                .then(r => {
                    this.$store.dispatch('updateUser', r.data[0].customer);
                    alert("TODO Add Shopping Cart GUI")
                })
        }
    }
}
</script>

<style scoped>


</style>