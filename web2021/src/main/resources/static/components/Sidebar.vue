<template>
    <v-navigation-drawer color="purple lighten-3" permanent expand-on-hover>
      <v-list>
        <v-list-item class="px-2">
          <v-list-item-avatar>
            <v-img v-if="isUserLogged" src="https://randomuser.me/api/portraits/women/88.jpg"></v-img>
            <v-img v-else src="../img/profile.jpg"></v-img>
          </v-list-item-avatar>
        </v-list-item>

        <router-link v-if="isUserLogged" to="/editProfile" style="text-decoration:none;">
            <v-list-item link>
                <v-list-item-content>
                    <v-list-item-title class="text-h6">
                    {{user.name}} {{user.surname}}
                    </v-list-item-title>
                    <v-list-item-subtitle>{{user.username}}</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
        </router-link>

        <router-link v-else to="/login" style="text-decoration:none;">
            <v-list-item link>
                <v-list-item-content>
                    <v-list-item-title class="text-h6">
                    Please log in
                    </v-list-item-title>
                    <v-list-item-subtitle>User is not logged</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
        </router-link>

      </v-list>

      <v-divider></v-divider>

      <div v-if="isUserLogged">
        <v-list v-if="user.userType == 'ADMINISTRATOR'" nav dense>
          <router-link to="/addManagerCourier" style="text-decoration:none;">
            <!-- <v-list-item link @click.stop="showDialog=true"> -->
            <v-list-item link>
              <v-list-item-icon >
                <v-icon>mdi-account-multiple-plus</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Create manager or courier</v-list-item-title>
            </v-list-item>
          </router-link>

          <router-link to="/allRegisteredUsers" style="text-decoration:none;">
            <v-list-item link>
              <v-list-item-icon>
                <v-icon>mdi-account-group</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Registered users</v-list-item-title>
            </v-list-item>
          </router-link>

          <router-link to="/createRestaurant" style="text-decoration:none;">
            <v-list-item link>
              <v-list-item-icon>
                <v-icon>mdi-silverware-fork-knife</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Create restaurant</v-list-item-title>
            </v-list-item>
          </router-link>

        </v-list>

        <v-list v-if="user.userType == 'MANAGER'" nav dense>
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


      </div>

      <template v-slot:append>
        <v-list nav dense>
            <v-list-item v-on:click="logout" v-if="isUserLogged" link>
                <v-list-item-icon>
                <v-icon>mdi-export</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Logout</v-list-item-title>
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
      <!-- <CreateUser v-model="showDialog"></CreateUser> -->
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
            drawer: false,
            showDialog: false
        }
    },
    methods: {
        logout() {
            window.sessionStorage.clear();
            var user = null;
            this.$store.dispatch('updateUser', user);
            this.$router.push({name: 'Login'});
        }
    }
}
</script>

<style scoped>

</style>