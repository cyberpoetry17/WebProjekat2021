<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-row item-1">
      <Sidebar></Sidebar>
      <div class="item-1 container-column-2" style="background-color:white;margin:20px;border-radius:20px;">
        <div class="container-row" style="justify-content:space-between;">
              <v-text-field
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="Search"
                  single-line
                  hide-details
                  style="max-width:500px;margin-left:20px;margin-top;20px;"
              ></v-text-field>
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                    <v-btn v-if="filterBy == null || filterBy == ''" color="primary" dark v-bind="attrs" v-on="on" style="margin-top:13px;margin-right:20px;">
                        NO FILTER
                        <v-icon>mdi-chevron-down</v-icon>
                    </v-btn>
                    <v-btn v-else color="primary" dark v-bind="attrs" v-on="on" style="margin-top:13px;margin-right:20px;">
                        Filtered by: {{filterBy}}
                        <v-icon>mdi-chevron-down</v-icon>
                    </v-btn>
                </template>
                <v-list>
                    <v-list-item v-for="(item, index) in items" :key="index" v-on:click="filterBy = item">
                        <v-list-item-title>{{ item }}</v-list-item-title>
                    </v-list-item>
                </v-list>
              </v-menu>
        </div>
            <v-data-table :headers="headers" :items="filteredItems" :search="search" style="margin:20px;">
              <template v-slot:item.image="{ item }">
                  <v-img :src="item.image" style="width: 50px; height: 50px; border-radius:50%; margin:5px;"></v-img>
              </template>
        
              <template v-slot:item.additional="{ item }">
                <!-- <v-btn color="green" dark> --> 
                  <v-icon @click.stop="showDialog=true" v-on:click="openAdditionalInformation(item)" color="green" large>mdi-dots-horizontal-circle</v-icon>
                <!-- </v-btn> -->
                <!-- <v-icon small class="mr-2" @click="editItem(item)">edit</v-icon>
                <v-icon small @click="deleteItem(item)">delete</v-icon> -->
              </template>
            </v-data-table>
      </div>
    </div>
    <ShowRestaurant v-model="showDialog" :restaurant=restaurant></ShowRestaurant>
  </div>
</template>

<script>

module.exports = {
    name: 'Home',
    computed: {
      filteredItems() {
        return this.restaurants.filter(i => {
          if(this.filterBy == null || this.filterBy == '') {
            return i;
          }
          else if(this.filterBy == 'ONLY WORKING') {
            if(i.isWorking) {
              return i;
            }
          }
          else {
            if(i.restaurantType == this.filterBy) {
              return i;
            }
          }
        })
      }
    },
    data() {
      return {
        showDialog: false,
        filterBy: null,
        items: [],
        search: '',
        headers: [
            { text: 'Name', value: 'name' },
            { text: 'Restaurant type', sortable:false, value: 'restaurantType' },
            { text: 'Location',  value: 'location.display_name' },
            { text: 'Logo', filterable: false, sortable:false, value:'image'},
            { text: 'Rating', value: 'averageRating'},
            { text: 'More', filterable: false, sortable:false, value: 'additional'}
        ],
        restaurants: [
          {
            name: 'Chinese Food',
            restaurantType: 'CHINESE',
            location: {
              display_name: 'Serbia, Novi Sad, 22000'
            },
            image: "../upload/17189273479274248605.jpg",
            averageRating: 4
          }
        ],
        search: '',
        restaurant: null
      }
    },
    methods: {
      openAdditionalInformation(item) {
        this.restaurant = item;
      }
    },
    created() {
        axios.get("http://localhost:8080/rest/restaurant/get-all-restaurants")
            .then(r => {
                this.restaurants = r.data;
            })
        axios.get("http://localhost:8080/rest/restaurant/get-all-restaurant-types")
            .then(r => {
                this.items.push("ONLY WORKING");
                r.data.forEach(element => {
                  this.items.push(element);          
                });
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
