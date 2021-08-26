<template>
    <v-container style="background-color:white;border-radius:20px;margin-top:50px;" fill-height>
        <v-row>
            <v-col>
                <v-card-title primary-title class="justify-center" style="font-size:36px;">
                    Create restaurant
                </v-card-title>
                <v-form
                    ref="form"
                    v-model="valid"
                    lazy-validation
                    class="text-center"
                >
                    <v-text-field
                        v-model="name"
                        label="enter name"
                        required
                        style="margin-left:10px;margin-right:10px;"
                    ></v-text-field>

                    <v-text-field
                        @keyup="searchLocation"
                        id="search-input"
                        v-model="query"
                        label="search location"
                        required
                        style="margin-left:10px;margin-right:10px;"
                    ></v-text-field>
                    <v-divider></v-divider>
                    <v-expand-transition v-if="this.results.length != 0">
                        <v-list class="red lighten-3">
                            <v-list-item v-for="(field, i) in results" :key="i" v-on:click="select(field)" >
                                <v-list-item-content>
                                    <v-list-item-title v-text="field.display_name"></v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </v-list>
                    </v-expand-transition>
                    <v-file-input
                        v-model="image"
                        accept="image/*"
                        label="Select logo"
                        chips
                        prepend-icon="mdi-camera"
                        style="margin-left:10px;margin-right:10px;"
                    ></v-file-input>
                    <v-menu offset-y>
                        <template v-slot:activator="{ on, attrs }">
                            <v-btn v-if="selectedRestaurantType == null || selectedRestaurantType == ''" color="primary" dark v-bind="attrs" v-on="on">
                                Select type
                                <v-icon>mdi-chevron-down</v-icon>
                            </v-btn>
                            <v-btn v-else color="primary" dark v-bind="attrs" v-on="on">
                                {{selectedRestaurantType}}
                                <v-icon>mdi-chevron-down</v-icon>
                            </v-btn>
                        </template>
                        <v-list>
                            <v-list-item v-for="(item, index) in items" :key="index" v-on:click="selectedRestaurantType = item">
                                <v-list-item-title>{{ item }}</v-list-item-title>
                            </v-list-item>
                        </v-list>
                    </v-menu>
                    <v-btn :disabled="this.users.length != 0" color="yellow" @click.stop="showDialog=true">
                        Create manager
                        <v-icon style="margin-left:5px;">mdi-account-plus</v-icon>
                    </v-btn>
                    <v-card-title>
                        <v-text-field
                            v-model="search"
                            append-icon="mdi-magnify"
                            label="Search"
                            single-line
                            hide-details
                        ></v-text-field>
                        <v-icon v-on:click="refresh" style="margin-left:50px;margin-top:20px;">mdi-refresh</v-icon>
                    </v-card-title>
                    <v-data-table
                        v-model="selectedManager"
                        :headers="headers"
                        :items="users"
                        :search="search"
                        :single-select="true"
                        v-on:change="this.$refs.form.validate()"
                        show-select
                    ></v-data-table>
                    <CreateUser v-model="showDialog"></CreateUser>
                    <label v-if="showMessage" style="color:red"><b>{{message}}</b></label>
                </v-form>
            </v-col>
        </v-row>
        <v-row justify="center" >
            <v-btn
                :disabled="!valid"
                color="success"
                class="mr-4"
                @click="validate"
                style="margin:20px;"
                width="400px"
            >
                Create restaurant
            </v-btn>
        </v-row>
    </v-container>
</template>

<script>
module.exports = {
    name: 'CreateRestaurant',
    watch:{
        showDialog:function(newValue, old) {
            if(!newValue){
                this.refresh();
            }
        }
    },
    data() {
        return {
            showDialog: false,
            message: '',
            showMessage: false,
            valid:true,
            items: [],
            headers: [
                { text: 'Username', value: 'username' },
                { text: 'Name', value: 'name' },
                { text: 'Surname', value: 'surname' },
                { text: 'Gender', value: 'genderType' },
                { text: 'Birthday', value: 'birthday' },
                { text: 'User Type', value: 'userType'}
            ],
            users: [],
            search: '',
            query: '',
            results: [],
            name: '',
            selectedLocation: null,
            selectedRestaurantType: null,
            image: null,
            selectedManager: []
        }
    },
    methods: {
        refresh() {
            axios.get("http://localhost:8080/rest/manager/get-managers-without-restaurant")
                .then(r => {
                    this.users = r.data;
                })
        },
        async validate() {
            if(this.name == '') {
                this.message = "Please enter name of restaurant";
                this.showMessage = true;
                return;
            }
            else if(this.selectedLocation == null) {
                this.message = "Please select location of restaurant";
                this.showMessage = true;
                return
            }
            else if(this.selectedRestaurantType == null) {
                this.message = "Please select restaurant type";
                this.showMessage = true;
                return
            }
            else if(this.selectedManager.length == 0) {
                this.message = "Please select manager";
                this.showMessage = true;
                return;
            }
            else if(this.image == null) {
                this.message = "Please select logo";
                this.showMessage = true;
                return;
            }
            else {
                this.message = '';
                this.showMessage = false;
                var restaurant = {
                    name: this.name,
                    restaurantType: this.selectedRestaurantType,
                    location: {
                        address: this.selectedLocation.address,
                        display_name: this.selectedLocation.display_name,
                        longitude: this.selectedLocation.lon,
	                    latitude: this.selectedLocation.lat
                    },
                    image: await this.upload(),
                    managerId: this.selectedManager[0].id
                }
                axios.post("http://localhost:8080/rest/restaurant/create-restaurant", restaurant)
                    .then(r => {
                        this.refresh();
                    })
                alert("Succesfully created restaurant!");
            }
        },
        async upload() {
            const fileToUpload = new FormData();
            fileToUpload.append('file', this.image);
            var image = await axios.post("http://localhost:8080/rest/file/upload", fileToUpload)
                .then(r => {
                    return r.data;
                })
            return image;
        },
        select: function(r) {
            this.selectedLocation = r;
            this.results = [];
            this.$emit('selected', r);
        },
        searchLocation: function() {
            clearTimeout(this.timeout);
            this.results = [];
            let self = this;
            this.timeout = setTimeout(function() {
                axios.get('https://us1.locationiq.com/v1/search.php?key=pk.f79b28f4f091ea8662281203e0081f65&format=json&addressdetails=1&limit=5&q=' + self.query)
                    .then(r => {
                        self.results = r.data;
                    })
            }, 1000);
        }
    },
    created() {
        axios.get("http://localhost:8080/rest/manager/get-managers-without-restaurant")
            .then(r => {
                this.users = r.data;
            })
        axios.get("http://localhost:8080/rest/restaurant/get-all-restaurant-types")
            .then(r => {
                this.items = r.data;
            })
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
    align-items: stretch;
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