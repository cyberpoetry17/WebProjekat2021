<template>
    <v-dialog width="800" v-if="restaurant != null" v-model="show">
        <v-card style="background-color:white;">
            <v-container>
                <v-row dense>
                    <v-col cols="6">
                        <v-img
                            height="250"
                            :src="restaurant.image"
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
                    <v-col cols="6">
                        <v-img
                            height="492"
                            :src="'../img/map.png'"
                        ></v-img>
                    </v-col>
                    <v-col cols="12">
                        <v-row v-for="item in comments" :key="item.id" style="margin-top:10px;">
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
                            </v-col>
                        </v-row>
                    </v-col>
                </v-row>
            </v-container>
        </v-card>
        <Shopping v-model="showDialog" :restaurant=restaurant></Shopping>
    </v-dialog>
</template>

<script>
module.exports = {
    name: 'ShowRestaurant',
    props: {
        value: Boolean,
        restaurant: Object
    },
    computed: {
        show: {
            get() {
                this.getComments();
                return this.value
            },
            set(value) {
                this.$emit('input', value)
            }
        },
        user() {
            return this.$store.getters.getUser
        }
    },
    data() {
        return {
            showDialog: false,
            selection: 1,
            comments: [
                {
                    customer: {
                        username: 'antonic901',
                        image: 'https://cdn.vuetifyjs.com/images/lists/1.jpg'
                    },
                    description: 'I will be in your neighborhood doing errands this weekend. Do you want to hang out?',
                    rating: 4
                },
                {
                    customer: {
                        username: 'antonic901',
                        image: 'https://cdn.vuetifyjs.com/images/lists/2.jpg'
                    },
                    description: 'I will be in your neighborhood doing errands this weekend. Do you want to hang out?',
                    rating: 4
                },
                {
                    customer: {
                        username: 'antonic901',
                        image: 'https://cdn.vuetifyjs.com/images/lists/3.jpg'
                    },
                    description: 'I will be in your neighborhood doing errands this weekend. Do you want to hang out?',
                    rating: 4
                },
                {
                    customer: {
                        username: 'antonic901',
                        image: 'https://cdn.vuetifyjs.com/images/lists/4.jpg'
                    },
                    description: 'I will be in your neighborhood doing errands this weekend. Do you want to hang out?',
                    rating: 4
                },
                {
                    customer: {
                        username: 'antonic901',
                        image: 'https://cdn.vuetifyjs.com/images/lists/5.jpg'
                    },
                    description: 'I will be in your neighborhood doing errands this weekend. Do you want to hang out?',
                    rating: 4
                },
                {
                    customer: {
                        username: 'antonic901',
                        image: 'https://cdn.vuetifyjs.com/images/lists/5.jpg'
                    },
                    description: 'I will be in your neighborhood doing errands this weekend. Do you want to hang out?',
                    rating: 4
                },
                {
                    customer: {
                        username: 'antonic901',
                        image: 'https://cdn.vuetifyjs.com/images/lists/5.jpg'
                    },
                    description: 'I will be in your neighborhood doing errands this weekend. Do you want to hang out?',
                    rating: 4
                },
                {
                    customer: {
                        username: 'antonic901',
                        image: 'https://cdn.vuetifyjs.com/images/lists/5.jpg'
                    },
                    description: 'I will be in your neighborhood doing errands this weekend. Do you want to hang out?',
                    rating: 4
                },
                {
                    customer: {
                        username: 'antonic901',
                        image: 'https://cdn.vuetifyjs.com/images/lists/5.jpg'
                    },
                    description: 'I will be in your neighborhood doing errands this weekend. Do you want to hang out?',
                    rating: 4
                }
            ]
        }
    },
    methods: {
        getComments() {
            axios.get("http://localhost:8080/rest/comment/get-comments-for-restaurant/" + this.restaurant.id)
                .then(r => {
                    this.comments = r.data
                })
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

