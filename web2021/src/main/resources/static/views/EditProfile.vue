<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-row item-1">
      <Sidebar></Sidebar>
      <div class="container-1 item-1">
        <v-card style="width:400px;margin-bottom:150px;">
          <v-card-title primary-title class="justify-center" style="font-size:36px;">
            EDIT PROFILE
          </v-card-title>
          <v-form
            ref="form"
            v-model="valid"
            lazy-validation
            class="text-center"
          >
            <v-text-field
              v-model="user.username"
              v-on:input="checkIsUsernameValid"
              :rules="usernameRules"
              label="enter username"
              required
              style="margin-left:10px;margin-right:10px;"
            ></v-text-field>

            <v-text-field
              type="password"
              v-model="oldPassword"
              v-on:input="checkPassword"
              :rules="oldPasswordRules"
              label="enter old password"
              style="margin-left:10px;margin-right:10px;"
            ></v-text-field>

            <v-text-field
              type="password"
              v-model="user.password"
              v-on:input="newPasswordType"
              :rules="passwordRules"
              label="enter new password"
              style="margin-left:10px;margin-right:10px;"
            ></v-text-field>

            <v-text-field
              type="password"
              v-model="user.confirmPassword"
              v-on:input="validateForm"
              :rules="confirmPasswordRules"
              label="confirm new password"
              style="margin-left:10px;margin-right:10px;"
            ></v-text-field>

            <v-text-field
              v-model="user.name"
              :rules="nameRules"
              label="enter name"
              required
              style="margin-left:10px;margin-right:10px;"
            ></v-text-field>

            <v-text-field
              v-model="user.surname"
              :rules="surnameRules"
              label="enter surname"
              required
              style="margin-left:10px;margin-right:10px;"
            ></v-text-field>

          <v-menu
              v-model="menu"
              :close-on-content-click="false"
              :nudge-right="40"
              transition="scale-transition"
              offset-y
              min-width="auto"
          >
              <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                      v-model="user.birthday"
                      label="Select birthday date"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                      style="margin-left:10px;margin-right:10px;"
                      :rules="birthdayRules"
                  ></v-text-field>
              </template>
              <v-date-picker
                  v-model="user.birthday"
                  @input="menu = false"
              ></v-date-picker>
        </v-menu>
          
            <v-btn
              :disabled="!valid"
              color="success"
              class="mr-4"
              @click="validate"
              style="margin:20px;"
            >
              Update profile
            </v-btn>

          </v-form>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
    name: 'EditProfile',
    mounted() {
      var user = this.$store.getters.getUser;
      this.user.id = user.id;
      this.user.username = user.username;
      this.user.name = user.name;
      this.user.surname = user.surname;
      this.user.birthday = user.birthday;
      this.user.userType = user.userType;
    },
    data() {
        return {
            menu: false,
            valid: true,
            validUsername: true,
            user: {
                id: null,
                username: '',
                password: '',
                confirmPassword: '',
                name: '',
                surname: '',
                birthday: null,
                userType: null
            },
            usernameRules: [
              v => !!v || 'Username is required',
              v => (v && v.length <= 15) || 'Username must be less than 10 characters',
              v => (v && this.validUsername) || 'This username is already taken'
            ],
            validPassword: true,
            oldPassword: '',
            oldPasswordRules: [
              v => (this.validPassword) || 'Old password is not valid'
            ],
            newPassword: true,
            passwordRules: [
              v => (this.newPassword) || 'You must enter new password'
            ],
            confirmPasswordRules: [
              v => (this.user.password == this.user.confirmPassword) || 'Passwords do not match'
            ],
            nameRules: [
                v => !!v || 'Name is required'
            ],
            surnameRules: [
                v => !!v || 'Surname is required'
            ],
            genderRules: [
                v => !!v || 'Please select gender'
            ],
            birthdayRules: [
                v => !!v || 'Please select birthday'
            ]
        }
    },
    methods: {
        validateForm() {
          this.$refs.form.validate()
        },
        validate () {
            if(this.$refs.form.validate()) {
              this.update()
            }
        },
        newPasswordType() {
          if(this.user.password == '' && this.oldPassword == '') {
            this.validPassword = true;
            this.newPassword = true;
          }
          else if(this.user.password == '' && this.oldPassword != '') {
            this.newPassword = false;
          }
          else if(this.user.password != '' && this.oldPassword == '') {
            this.newPassword = true;
            this.validPassword = false;
          }
          else if(this.user.password != '' && this.oldPassword != '') {
            this.newPassword = true;
          }
          this.validateForm();
        },
        checkIsUsernameValid() {
          axios.get("http://localhost:8080/rest/customer/check-username/" + this.user.username)
            .then(r => {
              if(r.data == "taken") {
                if(this.user.username == this.$store.getters.getUser.username) {
                  this.validUsername = true
                  this.$refs.form.validate()
                }
                else {
                  this.validUsername = false
                  this.$refs.form.validate()
                }

              }
              else {
                this.validUsername = true
                this.$refs.form.validate()
              }
            })
        },
        checkPassword() {
            if(this.oldPassword == '' && this.user.password == '') {
              this.validPassword = true;
              this.newPassword = true;
            }
            else if(this.oldPassword != '' && this.user.password == '') {
              var user = this.$store.getters.getUser;
              if(user.password == this.oldPassword) {
                this.validPassword = true
              }
              else {
                this.validPassword = false
              }
              this.newPassword = false;
            }
            else if(this.oldPassword != '' && this.user.password != '') {
              var user = this.$store.getters.getUser;
              if(user.password == this.oldPassword) {
                this.validPassword = true
              }
              else {
                this.validPassword = false
              }
              this.newPassword = true;
            }
            this.$refs.form.validate()
        },
        update() {
            var date = this.getDateTimeFromString(this.user.birthday.toString(), "00:00").getTime()
            var user = {
                id: this.user.id,
                username: this.user.username,
                password: this.user.password,
                name: this.user.name,
                surname: this.user.surname,
                birthday: date,
                userType: this.user.userType
            }
            axios.put("http://localhost:8080/rest/user/edit-profile", user)
                .then(r => {
                    r.data.birthday = this.formatDate(new Date(r.data.birthday));
                    this.$store.dispatch('updateUser', r.data);
                    this.$router.push({name: 'Home'})
                }) 
        },
        // Expected yy-mm-dd and HH:mm format
        getDateTimeFromString: function(dstr, tstr) {
            let dparts = dstr.split('-');
            let tparts = tstr.split(':');
            // -1 because js counts months from 0
            return new Date(dparts[0], dparts[1] - 1, dparts[2], tparts[0], tparts[1]);
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
  }

  .container-row {
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .item-1 {
    flex-grow: 1;
  }

</style>