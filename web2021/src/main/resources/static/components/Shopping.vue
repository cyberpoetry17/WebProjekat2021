<template>
    <v-row justify="center">
        <v-dialog
            v-model="show"
            fullscreen
            hide-overlay
            transition="dialog-bottom-transition"
            >
            <v-card style="background-image: url('../img/background_blur.jpg');background-position: center; background-repeat: no-repeat; background-size: cover;">
                <v-toolbar
                    dark
                    color="purple"
                >
                    <v-btn
                        icon
                        dark
                        @click="show = false"
                    >
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                    <v-toolbar-title>Articles</v-toolbar-title>
                </v-toolbar>
                <v-container>
                <v-row justify="start">
                    <v-card
                        class="mx-auto my-12"
                        max-width="374"
                        v-for="(item) in articles"
                        :key="item.id"
                    >

                        <v-img height="250" :src="item.image"></v-img>
                        
                        <v-row align="center" style="height:60px;">
                            <v-col md="9" align="left">
                                <v-card-title>{{item.name}}</v-card-title>
                            </v-col>
                            <v-col md="3" align="right">                    
                                <v-tooltip bottom>
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-icon style="margin-right:5px;" v-bind="attrs" v-on="on" v-if="item.articleType == 'FOOD'" >mdi-barley</v-icon>
                                        <v-icon style="margin-right:5px;" v-bind="attrs" v-on="on" v-else-if="item.articleType == 'DRINK'" >mdi-bottle-soda</v-icon>
                                        <v-icon style="margin-right:5px;" v-bind="attrs" v-on="on" v-else>mdi-battery-unknown</v-icon>
                                    </template>
                                    <span v-if="item.articleType == 'FOOD'">Food</span>
                                    <span v-else-if="item.articleType == 'DRINK'">Drink</span>
                                    <span v-else>Unknown</span>
                                </v-tooltip>
                            </v-col>
                        </v-row>

                        <v-card-text>
                            <div>
                                ${{item.price}} 
                            </div>

                            <div v-if="item.description != ''">{{item.description}}</div>
                        </v-card-text>

                        <v-divider class="mx-4"></v-divider>

                        <v-card-actions>
                            <v-btn color="deep-purple lighten-2" v-on:click="addToShoppingCart()" text>Add to cart</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-row>
        </v-container>
            </v-card>
        </v-dialog>
    </v-row>
</template>

<script>
  module.exports = {
    name: 'Shopping',
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
    data () {
      return {
        dialog: false,
        notifications: false,
        sound: true,
        widgets: false,
        articles: [],
        article: null
      }
    },
    methods: {
        editArticle(item) {
            this.article = item;
        },
        addToShoppingCart() {
            alert("TODO");
        }
    },
    created() {
        axios.get("http://localhost:8080/rest/article/get-all-articles/" + this.restaurant.id)
            .then(r => {
                this.articles = r.data;
            })
    }
  }
</script>