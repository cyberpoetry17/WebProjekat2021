<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-row item-1">
        <Sidebar></Sidebar>
        <div class="item-1 container-column-2" style="background-color:white;margin:20px;border-radius:20px;">
            <label style="margin-left:17px;margin-right:10px; margin-top:10px;"><strong>Please select one type of users:</strong></label>
            <v-radio-group v-model="selectedUserType" row style="margin-left:10px;margin-right:10px;" v-on:change="getUsers">
                <v-radio
                    label="Managers"
                    value="MANAGERS"
                    color="red darken-3"
                ></v-radio>
                <v-radio
                    label="Couriers"
                    value="COURIERS"
                    color="red darken-3"
                ></v-radio>
                <v-radio
                    label="Customers"
                    value="CUSTOMERS"
                    color="red darken-3"
                ></v-radio>
            </v-radio-group>
            <v-card-title>
                <v-text-field
                    v-model="search"
                    append-icon="mdi-magnify"
                    label="Search"
                    single-line
                    hide-details
                ></v-text-field>
            </v-card-title>
            <v-data-table
            :headers="headers"
            :items="users"
            :search="search"
            ></v-data-table>
        </div>
    </div>
  </div>
</template>

<script>
module.exports = {
    name: 'AllRegisteredUsers',
    data() {
        return {
            selectedUserType: null,
            search: '',
            headers: [
                { text: 'Username', value: 'username' },
                { text: 'Name', value: 'name' },
                { text: 'Surname', value: 'surname' },
                { text: 'Gender', value: 'genderType' },
                { text: 'Birthday', value: 'birthday' },
                { text: 'User Type', value: 'userType'}
            ],
            users: []
        }
    },
    methods: {
        getUsers() {
            if(this.selectedUserType == "MANAGERS") {
                axios.get("http://localhost:8080/rest/manager/get-all-managers")
                    .then(r => {
                        r.data.forEach(element => {
                            element.birthday = this.formatDate(element.birthday);
                        });
                        this.users = r.data;
                    })
            }
            else if(this.selectedUserType == "COURIERS") {
                axios.get("http://localhost:8080/rest/courier/get-all-couriers")
                    .then(r => {
                        r.data.forEach(element => {
                            element.birthday = this.formatDate(element.birthday);
                        });
                        this.users = r.data;
                    })
            }
            else {
                axios.get("http://localhost:8080/rest/customer/get-all-customers")
                    .then(r => {
                        r.data.forEach(element => {
                            element.birthday = this.formatDate(element.birthday);
                        });
                        this.users = r.data;
                    })
            }
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

  .container-column-2 {
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
    align-items: stretch;
  }

  .item-1 {
    flex-grow: 1;
  }

</style>