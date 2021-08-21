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
                        v-for="(item) in filteredArticles"
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
                            <div v-if="user != null">
                                <v-btn v-if="user.userType == 'CUSTOMER'" color="deep-purple lighten-2" v-on:click="addToShoppingCart(item)" text>Add to cart</v-btn>
                            </div>
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
                if(this.restaurant == null) return this.value;
                axios.get("http://localhost:8080/rest/article/get-available-articles/" + this.restaurant.id)
                    .then(r => {
                        this.articles = r.data;
                    })
                return this.value
            },
            set(value) {
                this.$emit('input', value)
            }
        },
        user() {
            return this.$store.getters.getUser
        },
        filteredArticles() {
            if(this.user == null) return this.articles;
            if(this.user.userType != 'CUSTOMER') return this.articles;
            return this.articles.filter(a => {
                detector = true;
                this.user.shoppingCart.articles.forEach(item => {
                    if(item.article.id == a.id) {
                        detector = false;
                    }
                })
                if(detector) return a;
            })
        }
    },
    data () {
      return {
        articles: []
      }
    },
    methods: {
        editArticle(item) {
            this.article = item;
        },
        addToShoppingCart(article) {
            var articleQuantity = {
                userId: this.user.id,
                articleId: article.id,
                quantity: 1
            }
            axios.post("http://localhost:8080/rest/customer/add-article", articleQuantity)
                .then(r => {
                    this.$store.dispatch('updateShoppingCart', r.data);
                })
        }
    }
  }
</script>