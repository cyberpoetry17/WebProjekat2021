<template>
  <div class="text-center">
    <v-dialog v-model="show" width="500px">
        <v-card>
            <v-card-title primary-title class="justify-center" style="font-size:36px;text-align:center;">
                Create new user
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
                    v-model="user.password"
                    :rules="passwordRules"
                    label="enter password"
                    required
                    style="margin-left:10px;margin-right:10px;"
                ></v-text-field>

                <v-text-field
                    type="password"
                    v-model="user.confirmPassword"
                    :rules="confirmPasswordRules"
                    label="confirm password"
                    required
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

                <v-radio-group
                    v-model="user.genderType"
                    :rules="genderRules"
                    row
                    style="margin-left:10px;margin-right:10px;"
                >
                    <v-radio
                        label="Male"
                        value="MALE"
                    ></v-radio>
                    <v-radio
                        label="Female"
                        value="FEMALE"
                    ></v-radio>
                </v-radio-group>
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

                <v-radio-group
                    v-model="user.userType"
                    :rules="userTypeRules"
                    row
                    style="margin-left:10px;margin-right:10px;"
                >
                    <v-radio
                        label="Manager"
                        value="MANAGER"
                    ></v-radio>
                    <v-radio
                        label="Courier"
                        value="COURIER"
                    ></v-radio>
                </v-radio-group>

                <v-btn
                    :disabled="!valid"
                    color="success"
                    class="mr-4"
                    @click="validate"
                    style="margin:20px;"
                >
                    CREATE
                </v-btn>
            </v-form>
        </v-card>
    </v-dialog>
  </div>
</template>

<script>
module.exports = {
    name: 'Registration',
    props: {
        value: Boolean
    },
    computed: {
        show: {
            get() {
                return this.value
            },
            set(value) {
                this.$emit('input', value)
            }
        }
    },
    data() {
        return {
            dialog: false,
            menu: false,
            valid: true,
            validUsername: true,
            user: {
                username: '',
                password: '',
                confirmPassword: '',
                name: '',
                surname: '',
                genderType: null,
                birthday: null,
                userType: null
            },
            usernameRules: [
              v => !!v || 'Username is required',
              v => (v && v.length <= 15) || 'Username must be less than 10 characters',
              v => (v && this.validUsername) || 'This username is already taken'
            ],
            passwordRules: [
              v => !!v || 'Password is required',
              v => (v && v.length >= 5) || 'Password must have at least 5 characters'
            ],
            confirmPasswordRules: [
              v => !!v || 'Password is required',
              v => (v && this.user.password == this.user.confirmPassword) || 'Passwords do not match'
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
            ],
            userTypeRules: [
                v => !!v || 'Please select user type'
            ]
        }
    },
    methods: {
        validate () {
            if(this.$refs.form.validate()) {
              this.register()
            }
        },
        checkIsUsernameValid() {
          axios.get("http://localhost:8080/rest/user/check-username/" + this.user.username)
            .then(r => {
              if(r.data == "taken") {
                this.validUsername = false
                this.$refs.form.validate()
              }
              else {
                this.validUsername = true
                this.$refs.form.validate()
              }
            })
        },
        register() {
            var date = this.getDateTimeFromString(this.user.birthday.toString(), "00:00").getTime()
            var user = {
                username: this.user.username,
                password: this.user.password,
                name: this.user.name,
                surname: this.user.surname,
                genderType: this.user.genderType,
                birthday: date,
                userType: this.user.userType
            }
            if(user.userType == "MANAGER") {
                axios.post("http://localhost:8080/rest/manager/register", user)
                    .then(r => {
                        this.user.username = '';
                        alert("Successfuly created new manager!");
                    }) 
            }
            else {
                 axios.post("http://localhost:8080/rest/courier/register", user)
                    .then(r => {
                        this.user.username = '';
                        alert("Successfuly created new courier!");
                    }) 
            }

        },
        // Expected yy-mm-dd and HH:mm format
        getDateTimeFromString: function(dstr, tstr) {
            let dparts = dstr.split('-');
            let tparts = tstr.split(':');
            // -1 because js counts months from 0
            return new Date(dparts[0], dparts[1] - 1, dparts[2], tparts[0], tparts[1]);
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

