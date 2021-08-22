<template>
  <v-row justify="center">
    <v-dialog
      v-model="show"
      scrollable
      max-width="800px"
    >
        <v-card>
        <!-- <v-card-title>Select Country</v-card-title> -->
        <v-row dense>
            <v-col cols="6">
                <v-img
                    height="250"
                    :src="restaurant.image"
                    style="margin-top:5px;margin-left:5px;"
                ></v-img>
                <v-row align="center" >
                    <v-col align="left" class="ma-0 pa-0">
                        <v-card-title style="margin-left:10px;">{{restaurant.name}}</v-card-title>
                    </v-col>
                    <v-col v-if="user != null" align="right" class="ma-0 pa-0">
                        <v-btn v-if="user.userType == 'CUSTOMER' && restaurant.isWorking" @click.stop="showDialog=true" color="deep-purple lighten-2" text>
                            <v-icon>mdi-cart</v-icon>
                            Shop
                        </v-btn>
                        <v-btn v-else @click.stop="showDialog=true" color="deep-purple lighten-2" text>
                            <v-icon>mdi-file-find</v-icon>
                            Articles
                        </v-btn>
                    </v-col>
                    <v-btn v-else @click.stop="showDialog=true" color="deep-purple lighten-2" text>
                        <v-icon>mdi-file-find</v-icon>
                        Articles
                    </v-btn>
                </v-row>
                <v-card-text>
                    <v-row
                        align="center"
                        class="mx-0"
                        v-if="restaurant.averageRating != 0"
                    >
                        <v-rating
                            :value="restaurant.averageRating"
                            color="amber"
                            dense
                            half-increments
                            readonly
                            size="16"
                            style="margin-top:10px;"
                        ></v-rating>

                        <div class="grey--text ms-4" style="margin-top:10px;">
                        {{restaurant.averageRating}}
                        </div>
                    </v-row>

                    <div class="my-4 text-subtitle-1">
                        $ • {{restaurant.restaurantType}}
                    </div>

                    <div>{{restaurant.location.display_name}}</div>
                </v-card-text>

                <v-divider class="mx-4"></v-divider>

                <v-card-title v-if="restaurant.isWorking">Restaurant is opened tonight</v-card-title>
                <v-card-title v-else>Restaurant is closed tonight</v-card-title>

                <v-divider class="mx-4"></v-divider>

            </v-col>
            <v-col id="map" cols="6"></v-col>
        </v-row>
        <v-card-text style="height: 210px;">
          <v-row v-for="(item, index) in comments" :key="item.id" style="margin-top:10px;">
                <v-col align-self="center">
                    <div style="display:flex;flex-direction:row;">
                        <v-img
                            :src="item.customer.image"
                            max-height="24"
                            max-width="24"
                            style="border-radius:50%;margin-right:5px;"
                        ></v-img>
                        <label style=""> - {{item.customer.username}}, says:</label>
                    </div>
                    <v-rating
                        :value="item.rating"
                        color="amber"
                        dense
                        readonly
                        size="16"
                    ></v-rating>
                    <p style="margin-left:5px;">{{item.description}}</p>
                    <v-divider v-if="index != comments.length - 1" class="mx-4"></v-divider>
                </v-col>
            </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
    <Shopping v-model="showDialog" :restaurant=restaurant></Shopping>
  </v-row>
</template>

<script>
module.exports = {
    name: 'ShowRestaurant',
    computed: {
        user() {
            return this.$store.getters.getUser
        },
        restaurant() {
            return this.$store.getters.getItem
        }
    },
    watch:{
        show:function(newValue, old){
            if(!newValue){
                this.$router.push({name: 'Home'})
            }
        }
    },
    data() {
        return {
            show:true,
            showDialog: false,
            selection: 1,
            comments: [],
            map: null
        }
    },
    methods: {
        getComments() {
            axios.get("http://localhost:8080/rest/comment/get-comments-for-restaurant/" + this.restaurant.id)
                .then(r => {
                    this.comments = r.data
                })
        },
        createMap(restaurant) {
            this.map = new ol.Map({
                target: 'map',
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM()
                    })
                ],
                view: new ol.View({
                    center: ol.proj.fromLonLat([restaurant.location.longitude, restaurant.location.latitude]),
                    zoom: 15
                    })
            });
            let restPointer = new ol.Feature({
                geometry: new ol.geom.Point(ol.proj.fromLonLat([restaurant.location.longitude, restaurant.location.latitude]))
            });
            restPointer.setStyle(
                new ol.style.Style({
                    image: new ol.style.Icon({
                        crossOrigin: 'anonymous',
                        src: 'img/map-marker-icon.png',
                        scale: 0.02,
                    }),
                })
            );
            var layer = new ol.layer.Vector({
                source: new ol.source.Vector({
                    features: [ restPointer,
                    ]})});
            this.map.addLayer(layer);
        }
    },
    mounted() {
        this.createMap(this.restaurant);
        this.getComments();
    }
}

</script>

<style scoped>

</style>

