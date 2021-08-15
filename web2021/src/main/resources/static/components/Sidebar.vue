<template>
    <v-navigation-drawer permanent expand-on-hover>
      <v-list>
        <v-list-item class="px-2">
          <v-list-item-avatar>
            <v-img v-if="isUserLogged" src="https://randomuser.me/api/portraits/women/88.jpg"></v-img>
            <v-img v-else src="https://www.donkey.bike/wp-content/uploads/2020/12/user-member-avatar-face-profile-icon-vector-22965342-e1608640557889.jpg"></v-img>
          </v-list-item-avatar>
        </v-list-item>

        <v-list-item link>
          <v-list-item-content>
            <v-list-item-title v-if="isUserLogged" class="text-h6">
              {{user.name}} {{user.surname}}
            </v-list-item-title>
            <v-list-item-title v-else class="text-h6">
              Please log in
            </v-list-item-title>
            <v-list-item-subtitle v-if="isUserLogged">{{user.username}}</v-list-item-subtitle>
            <v-list-item-subtitle v-else>User is not logged</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>

      <v-divider></v-divider>

      <v-list v-if="isUserLogged" nav dense>
        <v-list-item link>
          <v-list-item-icon>
            <v-icon>mdi-folder</v-icon>
          </v-list-item-icon>
          <v-list-item-title>My Files</v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-icon>
            <v-icon>mdi-account-multiple</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Shared with me</v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-icon>
            <v-icon>mdi-star</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Starred</v-list-item-title>
        </v-list-item>
      </v-list>

      <template v-slot:append>
        <v-list nav dense>
            <v-list-item v-if="isUserLogged" link>
                <v-list-item-icon>
                <v-icon>mdi-export</v-icon>
                </v-list-item-icon>
                <v-list-item-title v-on:click="logout">Logout</v-list-item-title>
            </v-list-item>
            <router-link v-else to="/login" style="text-decoration:none;">
                <v-list-item link>
                    <v-list-item-icon>
                        <v-icon>mdi-import</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>Login</v-list-item-title>
                </v-list-item>
            </router-link>
        </v-list>
      </template>

    </v-navigation-drawer>
</template>

<script>
module.exports = {
    name: 'Sidebar',
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
            this.$router.push({name: 'Login'});
        }
    }
}
</script>