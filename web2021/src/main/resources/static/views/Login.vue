<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-1 item-1">
      <v-card style="width:400px;height:280px;margin-bottom:150px;">
        <v-card-title primary-title class="justify-center" style="font-size:36px;">
          Welcome
        </v-card-title>
        <v-form
          ref="form"
          v-model="valid"
          lazy-validation
          class="text-center"
        >
          <v-text-field
            v-model="username"
            :rules="usernameRules"
            label="Username"
            required
            style="margin-left:10px;margin-right:10px;"
          ></v-text-field>

          <v-text-field
            type="password"
            v-model="password"
            :rules="passwordRules"
            label="Password"
            required
            style="margin-left:10px;margin-right:10px;"
          ></v-text-field>

          <v-btn
            :disabled="!valid"
            color="success"
            class="mr-4"
            @click="validate"
            style="margin:20px;"
          >
            Sign in
          </v-btn>

          <router-link to="/registration">
            <v-btn
              color="error"
              class="mr-4"
              @click="reset"
            >
              Sign up
            </v-btn>
          </router-link>

        </v-form>
      </v-card>
    </div>
  </div>
</template>

<script>

module.exports = {
    name: 'Login',
    data() {
        return {
            valid: true,
            username: '',
            usernameRules: [
              v => !!v || 'Username is required',
              v => (v && v.length <= 10) || 'Username must be less than 10 characters'
            ],
            password: '',
            passwordRules: [
              v => !!v || 'Password is required',
              v => /.+@.+\..+/.test(v) || 'Password must be valid',
            ]
        }
    },
    methods: {
       validate () {
        this.$refs.form.validate()
      },
      reset () {
        this.$refs.form.reset()
      },
      resetValidation () {
        this.$refs.form.resetValidation()
      }
    }
}

</script>

<style scoped>

  .background {
    background-color: #3498db;
    min-width: 100%;
    min-height: 100%;
    position: absolute;
  }

  .container-column {
    display: flex;
    flex-direction: column;
  }

  .container-1 {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }

  .container-row {
    display: flex;
    flex-direction: row;
  }

  .item-1 {
    flex-grow: 1;
  }

</style>
