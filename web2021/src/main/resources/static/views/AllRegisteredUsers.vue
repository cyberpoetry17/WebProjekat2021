<template>
    <v-container style="background-color:white;border-radius:20px;margin-top:50px;" fill-height>
        <v-row>
            <v-col>
                <label style="margin-left:17px;margin-right:10px; margin-top:10px;"><strong>Please select one type of users:</strong></label>
                <v-row no-gutters>
                    <v-col>
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
                    </v-col>
                    <v-col v-if="selectedUserType == 'CUSTOMERS'" class="d-flex">
                        <v-spacer></v-spacer>
                        <v-switch
                            v-model="filterBySuspect"
                            :label="'List suspected users'"
                            style="margin-right:5px;margin-left:5px;"
                        ></v-switch>
                    </v-col>
                </v-row>
                <v-text-field
                    v-model="search"
                    append-icon="mdi-magnify"
                    label="Search"
                    single-line
                    hide-details
                ></v-text-field>
                <v-data-table v-if="selectedUserType == null || selectedUserType != 'CUSTOMERS'" :headers="headers2" :items="users" :search="search">
                    <template v-slot:item.block="{ item }">
                        <v-btn v-on:click="blockUnblock(item)" v-if="!item.isBlocked" color="error">Block</v-btn>
                        <v-btn v-on:click="blockUnblock(item)" v-else color="success">Unblock</v-btn>
                    </template>
                </v-data-table>
                <v-data-table v-else :headers="headers" :items="filteredUsers" :search="search">
                        <template v-slot:item.block="{ item }">
                        <v-btn v-on:click="blockUnblock(item)" v-if="!item.isBlocked" color="error">Block</v-btn>
                        <v-btn v-on:click="blockUnblock(item)" v-else color="success">Unblock</v-btn>
                    </template>
                    </v-data-table>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
module.exports = {
    name: 'AllRegisteredUsers',
    computed: {
        filteredUsers() {
            if(this.filterBySuspect) {
                return this.suspectedUsers;
            }
            else {
                return this.users;
            }
        }
    },
    data() {
        return {
            filterBySuspect: false,
            selectedUserType: null,
            search: '',
            headers: [
                { text: 'Username', value: 'username' },
                { text: 'Name', value: 'name' },
                { text: 'Surname', value: 'surname' },
                { text: 'Gender', filterable: false, sortable: false, value: 'genderType' },
                { text: 'Birthday', filterable: false, sortable: false, value: 'birthday' },
                { text: 'User Type', filterable: false, sortable: false, value: 'userType'},
                { text: 'Points', filterable: false, value: 'points'},
                { text: 'Block user', filterable: false, sortable: false, value: 'block'}
            ],
            headers2: [
                { text: 'Username', value: 'username' },
                { text: 'Name', value: 'name' },
                { text: 'Surname', value: 'surname' },
                { text: 'Gender', filterable: false, sortable: false, value: 'genderType' },
                { text: 'Birthday', filterable: false, sortable: false, value: 'birthday' },
                { text: 'User Type', filterable: false, sortable: false, value: 'userType'},
                { text: 'Block user', filterable: false, sortable: false, value: 'block'}
            ],
            users: [],
            suspectedUsers: []
        }
    },
    methods: {
        blockUnblock(item) {
            axios.get("http://localhost:8080/rest/user/block-unblock/" + item.id)
            item.isBlocked = !item.isBlocked;
        },
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
                axios.get("http://localhost:8080/rest/user/get-suspected-users")
                    .then(r => {
                        r.data.forEach(element => {
                            element.birthday = this.formatDate(element.birthday);
                        });
                        this.suspectedUsers = r.data;
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