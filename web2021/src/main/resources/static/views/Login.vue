<template>
  <v-container style="max-width:450px;background-color:white;border-radius:20px;margin-top:200px;" fill-height>
    <v-row>
      <v-col>
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

          <p v-if="this.messageShow == true" style="color:red;"><b>{{this.message}}</b></p>

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
            >
              Sign up
            </v-btn>
          </router-link>

        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

module.exports = {
    name: 'Login',
    data() {
        return {
            valid: true,
            username: '',
            usernameRules: [
              v => !!v || 'Username is required'
            ],
            password: '',
            passwordRules: [
              v => !!v || 'Password is required'
            ],
            message: 'Username or password is not valid!',
            messageShow: false
        }
    },
    methods: {
      validate () {
        if(this.$refs.form.validate()) {
          this.login()
        }
      },
      login() {
        var login = {
          username: this.username,
          password: this.password
        }
        axios.post("http://localhost:8080/rest/user/login", login)
          .then(r => {
              if(r.data != null) {
                if(r.data.isBlocked) {
                  this.message = "This account is suspended.";
                  this.messageShow = true;
                }
                else {
                  r.data.birthday = this.formatDate(new Date(r.data.birthday));
                  this.$store.dispatch('updateUser', r.data);
                  this.$router.push({name: 'Home'});
                }

              }
              else {
                this.message = 'Username or password is not valid!';
                this.messageShow = true;
              }
          })
      },
      formatDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) 
            month = '0' + month;
        if (day.length < 2) 
            day = '0' + day;

        return [year, month, day].join('-');
      }
    }
  }

</script>

<style scoped>

  .background {
    background-image: url("../img/background.jpg");
    min-width: 100%;
    min-height: 100%;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
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
