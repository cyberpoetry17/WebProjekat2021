<template>
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
                <v-btn color="deep-purple lighten-2" @click.stop="showDialog=true" v-on:click="editArticle(item)" text>Edit</v-btn>
            </v-card-actions>
        </v-card>
        <EditArticle v-model="showDialog" :article=article></EditArticle>
    </v-row>
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
            articles: [],
            article: null,
            showDialog: false
        }
    },
    methods: {
        editArticle(item) {
            this.article = item;
        }
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