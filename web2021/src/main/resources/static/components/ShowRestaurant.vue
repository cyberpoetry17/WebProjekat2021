<template>
  <div v-if="restaurant != null">
    <v-dialog v-model="show" width="500px" scrollable>
        <v-card>
            <v-img
                height="250"
                :src="restaurant.image"
            ></v-img>

            <v-card-title>{{restaurant.name}}</v-card-title>

            <v-card-text>
                <v-row
                    align="center"
                    class="mx-0"
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

            <v-card-text style="height:200px">
                <!-- <label style="margin-top:20px;"><b>Reviews:</b></label> -->
                <!-- <v-container>
                    <v-row> -->
                        <v-list three-line>
                            <template v-for="(item, index) in comments">
                                <v-subheader v-if="index == 0" :key="item.index" v-text="'Reviews:'"></v-subheader>

                                <v-list-item :key="item.id">
                                    <v-list-item-avatar>
                                        <v-tooltip bottom>
                                            <!-- <v-img :src="item.avatar"></v-img> -->
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
                                        <!-- <v-list-item-title v-html="item.title"></v-list-item-title> -->
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
                    <!-- </v-row>
                </v-container> -->
            </v-card-text>
        </v-card>
    </v-dialog>
  </div>
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
        }
    },
    data() {
        return {
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
                }

            ],
            // comments: [
            //     {
            //     avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
            //     title: 'Brunch this weekend?',
            //     subtitle: `<span class="text--primary">Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?`,
            //     },
            //     {
            //     avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
            //     title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>',
            //     subtitle: `<span class="text--primary">to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend.`,
            //     },
            //     {
            //     avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
            //     title: 'Oui oui',
            //     subtitle: '<span class="text--primary">Sandra Adams</span> &mdash; Do you have Paris recommendations? Have you ever been?',
            //     },
            //     {
            //     avatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
            //     title: 'Birthday gift',
            //     subtitle: '<span class="text--primary">Trevor Hansen</span> &mdash; Have any ideas about what we should get Heidi for her birthday?',
            //     },
            //     {
            //     avatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg',
            //     title: 'Recipe to try',
            //     subtitle: '<span class="text--primary">Britta Holt</span> &mdash; We should eat this: Grate, Squash, Corn, and tomatillo Tacos.',
            //     },
            // ],
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

