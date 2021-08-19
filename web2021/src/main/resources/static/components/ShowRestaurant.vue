<template>
    <v-dialog v-if="restaurant != null" v-model="show" width="800px" scrollable>
        <v-card>
            <v-container>
                <v-row dense>
                    <v-col cols="6">
                        <v-img
                            height="250"
                            :src="restaurant.image"
                        ></v-img>
                        <!-- <v-container> -->
                            <v-row align="center" >
                                <v-col align="left" class="ma-0 pa-0">
                                    <v-card-title style="margin-left:10px;">{{restaurant.name}}</v-card-title>
                                </v-col>
                                <v-col v-if="user != null" align="right" class="ma-0 pa-0">
                                    <v-btn v-if="user.userType == 'CUSTOMER'" @click.stop="showDialog=true" color="deep-purple lighten-2" text>
                                        <v-icon>mdi-cart</v-icon>
                                        Shop
                                    </v-btn>
                                </v-col>
                            </v-row>
                        <!-- </v-container> -->
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
                                    size="14"
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
                </v-row>
            </v-container>
            <v-card-text style="height:200px">
                <v-list three-line>
                    <template v-for="(item, index) in comments">
                        <v-subheader v-if="index == 0" :key="item.index" v-text="'Reviews:'"></v-subheader>

                        <v-list-item :key="item.id">
                            <v-list-item-avatar>
                                <v-tooltip bottom>
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-img
                                            :src="item.customer.image"
                                            v-bind="attrs"
                                            v-on="on"
                                        ></v-img>
                                    </template>
                                    <span>{{item.customer.username}}</span>
                                </v-tooltip>
                            </v-list-item-avatar>

                            <v-list-item-content>
                                <v-rating
                                    :value="4"
                                    color="amber"
                                    dense
                                    half-increments
                                    readonly
                                    size="14"
                                ></v-rating>
                                <v-list-item-subtitle v-html="item.description"></v-list-item-subtitle>
                            </v-list-item-content>
                        </v-list-item>

                        <v-divider v-if="index != comments.length-1" :key="index"></v-divider>

                    </template>
                </v-list>
            </v-card-text>
        </v-card>
        <Shopping v-model="showDialog" :restaurant=restaurant ></Shopping>
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

