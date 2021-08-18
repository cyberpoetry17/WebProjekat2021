<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-row item-1">
        <Sidebar></Sidebar>
        <div class="item-1 container-row-2" style="background-color:white;margin:20px;border-radius:20px;">
            <v-card
                class="mx-auto my-12"
                max-width="374"
                v-for="(item) in articles"
                :key="item.id"
            >

                <v-img height="250" :src="item.image"></v-img>
                
                <v-card-title>
                    <v-row align="center" style="background-color:red;">
                        <v-col md="9" align="left" style="background-color:yellow;">
                            <v-card-title style="background-color:purple;">{{item.name}}</v-card-title>
                        </v-col>
                        <v-col md="3" align="right" style="background-color:blue;">                    
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                    <v-icon style="background-color:purple;" v-bind="attrs" v-on="on" v-if="item.articleType == 'FOOD'" >mdi-barley</v-icon>
                                    <v-icon v-bind="attrs" v-on="on" v-else-if="item.articleType == 'DRINK'" >mdi-bottle-soda</v-icon>
                                    <v-icon v-bind="attrs" v-on="on" v-else>mdi-battery-unknown</v-icon>
                                </template>
                                <span v-if="item.articleType == 'FOOD'">Food</span>
                                <span v-else-if="item.articleType == 'DRINK'">Drink</span>
                                <span v-else>Unknown</span>
                            </v-tooltip>
                        </v-col>
                    </v-row>
                </v-card-title>

                

                <v-card-text>
                    <v-row
                        align="center"
                        class="mx-0"
                    >
                        <v-rating
                        :value="4.5"
                        color="amber"
                        dense
                        half-increments
                        readonly
                        size="14"
                        ></v-rating>

                        <div class="grey--text ms-4">
                        {{item.articleType}}
                        </div>
                    </v-row>

                    <div class="my-4 text-subtitle-1">
                        ${{item.price}} 
                    </div>

                    <div v-if="item.description != ''">{{item.description}}</div>
                </v-card-text>

                <v-divider class="mx-4"></v-divider>

                <v-card-title>Tonight's availability</v-card-title>

                <v-card-text>
                <v-chip-group
                    active-class="deep-purple accent-4 white--text"
                    column
                >
                    <v-chip>5:30PM</v-chip>

                    <v-chip>7:30PM</v-chip>

                    <v-chip>8:00PM</v-chip>

                    <v-chip>9:00PM</v-chip>
                </v-chip-group>
                </v-card-text>

                <v-card-actions>
                <v-btn
                    color="deep-purple lighten-2"
                    text
                >
                    Reserve
                </v-btn>
                </v-card-actions>
            </v-card>
        </div>
    </div>
  </div>
</template>

<script>
module.exports = {
    name: 'AddNewArticle',
    computed: {
        user() {
            return this.$store.getters.getUser
        }
    },
    data() {
        return {
            articles: []
        }
    },
    methods: {
        
    },
    created() {
        axios.get("http://localhost:8080/rest/article/get-all-articles/" + this.user.restaurant.id)
            .then(r => {
                this.articles = r.data;
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

  .container-row-2 {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
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