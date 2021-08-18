<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-row item-1">
      <Sidebar></Sidebar>
      <div class="item-1 container-column-2" style="background-color:white;margin:20px;border-radius:20px;">
        <div class="container-row" style="margin:20px;">
          <v-text-field
            v-model="searchName"
            shaped 
            solo 
            placeholder="enter name of restaurant"
            label="enter name of restaurant" 
            single-line hide-details
            style="max-width:250px;margin-right:5px;margin-left:5px;"
          ></v-text-field>
          <v-text-field
            v-model="searchRestaurantType"
            shaped 
            solo 
            placeholder="enter type of restaurant"
            label="enter type of restaurant" 
            single-line hide-details
            style="max-width:250px;margin-right:5px;margin-left:5px;"
          ></v-text-field>
          <v-text-field
            v-model="searchLocation"
            shaped 
            solo 
            placeholder="enter location of restaurant"
            label="enter location of restaurant" 
            single-line hide-details
            style="max-width:250px;margin-right:5px;margin-left:5px;"
          ></v-text-field>
          <v-autocomplete
            shaped
            solo
            placeholder="filter by restaurant type"
            :items="restaurantTypes"
            v-model="filterByRestaurantType"
            style="max-width:250px;margin-right:5px;margin-left:5px;"
          ></v-autocomplete>
          <v-range-slider
            v-model="range"
            :max="5"
            :min="1"
            hide-details
            class="align-center"
            step="0.1"
            style="margin-right:5px;margin-left:5px;margin-bottom:15px;"
          >
            <template v-slot:prepend>
              <v-text-field
                :value="range[0]"
                class="mt-0 pt-0"
                hide-details
                single-line
                type="number"
                v-model="range[0]"
                style="width: 60px"
                step="0.1"
                @change="$set(range, 0, $event)"
              ></v-text-field>
            </template>
            <template v-slot:append>
              <v-text-field
                :value="range[1]"
                v-model="range[1]"
                class="mt-0 pt-0"
                hide-details
                single-line
                type="number"
                step="0.1"
                style="width: 60px"
                @change="$set(range, 1, $event)"
              ></v-text-field>
            </template>
          </v-range-slider>
          <v-switch
            v-model="filterByOpened"
            :label="'List only opened'"
            style="margin-right:5px;margin-left:5px;"
          ></v-switch>
        </div>
        <v-divider class="mx-4"></v-divider>
        <div class="container-row" style="justify-content:space-between;">
              <v-text-field
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="Search"
                  single-line
                  hide-details
                  style="max-width:500px;margin-left:20px;margin-top;20px;"
              ></v-text-field>
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
          if(this.filterBySearchName(i)) {
            if(this.filterBySearchRestaurantType(i)) {
              if(this.filterBySearchLocation(i)) {
                if(this.filterByType(i)) {
                  if(this.filterByAverageRating(i)) {
                    if(this.filterByOpened) {
                      if(i.isWorking) {
                        return i;
                      }
                    }
                    else return i;
                  }
                }
              }
            }
          }
        })
      }
    },
    data() {
      return {
        searchName: '',
        searchRestaurantType: '',
        searchLocation: '',
        range: [1,5],
        restaurantTypes: [],
        filterByRestaurantType: null,
        filterByOpened: false,
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
      },
      filterBySearchName(r) {
        if(this.searchName == '') return true;
        return r.name.toLowerCase().includes(this.searchName.toLowerCase());
      },
      filterBySearchRestaurantType(r) {
        if(this.searchRestaurantType == '') return true;
        return r.restaurantType.toLowerCase().includes(this.searchRestaurantType.toLowerCase());
      },
      filterBySearchLocation(r) {
        if(this.searchLocation == '') return true;
        let words = this.searchLocation.split(",");
        var valid = true;
        words.forEach(item => {
          if(item.trim() != "") {
            if(!r.location.display_name.toLowerCase().includes(item.toLowerCase().trim())) {
              valid = false;
            }
          }
        })
        return valid;
      },
      filterByType(r) {
        if(this.filterByRestaurantType == null) return true;
        if(r.restaurantType == this.filterByRestaurantType) {
          return true;
        }
        return false;
      },
      filterByAverageRating(r) {
        if(r.averageRating >= this.range[0] && r.averageRating <= this.range[1]) {
          return true;
        }
        else if(r.averageRating < 1) {
          return true;
        }
        return false;
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
                this.restaurantTypes = r.data;
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
