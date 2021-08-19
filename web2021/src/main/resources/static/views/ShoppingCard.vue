<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-row item-1">
      <Sidebar></Sidebar>
      <div
        class="item-1 container-column-2"
        style="background-color: white; margin: 20px; border-radius: 20px"
      >
        <div class="main-frame-div">
          <h1>Shopping cart</h1>
          <div class="parent">
            <div class="div1">
              <div >
               
              </div>
              <v-card
                class="mx-auto my-12"
                max-width="374"
                elevation="20"
                v-for="item in articles"
                :key="item.id"
                
              >
                <v-img class="img" :src="item.image"></v-img>
                <v-card-title>{{ item.name }}</v-card-title>
                <v-card-text>
                  <div>${{ item.price }}</div>
                  <div>quantity</div>
                </v-card-text>
                <v-divider class="mx-4"></v-divider>
                <v-card-actions>
                  <v-btn @click="removeArticle(item)" color="deep-purple lighten-2" text>remove</v-btn>
                </v-card-actions>
              </v-card>
            </div>
            <div class="div2">
              <h2>
                <v-card>
                  <v-card-title>The total amount:</v-card-title>
                  <div></div>
                  <v-card-actions>
                    <v-btn color="deep-purple" text>PURCHASE</v-btn>
                  </v-card-actions>
                </v-card>
              </h2>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
module.exports = {
  name: "ShoppingCard",
  computed: {
    getUser() {
      return this.$store.getters.getUser;
    },
    setUser(user){
      return this.$store.dispatch("updateUser",user);
    }
  },
  data() {
    return{
      articles: [],
    };
  },
  methods:{
    removeArticle(item){
      this.articles.pop(item);
    }
  },
  created() {
    var user = this.$store.getters.getUser;
    var article = {
      id: "123",
      name: "hrana",
      price: 200,
      articleType: "hrana",
      restaurantId: "123",
      quantity: 1,
      description: "bla bla",
      image: "./img/Capture.PNG",
    };
     var article2 = {
      id: "123",
      name: "slika",
      price: 200,
      articleType: "hrana",
      restaurantId: "123",
      quantity: 1,
      description: "bla bla",
      image: "./img/Capture.PNG",
    };
     

     this.articles.push(article)
     this.articles.push(article2)
  },
};
</script>

<style scoped>
.background {
  background-image: url("../img/background.jpg");
  min-width: 100%;
  min-height: 100%;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  /* height: 100%; */
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
  /* background-color: rgb(221, 160, 221); */
  height: 100%;
}
.item-1 {
  flex-grow: 1;
}

.container-column-2 {
  display: flex;
  flex-direction: column;
  /* background-color: aqua; */
}
/* .main-frame-div {
  background-color: brown;
} */
/* .grid-frame-div {
  background-color: aquamarine;
} */
.parent {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: 1fr;
  grid-column-gap: 1px;
  grid-row-gap: 0px;
}

.div1 {
  grid-area: 1 / 1 / 2 / 2;
  overflow-y: auto;
}
.div2 {
  grid-area: 1 / 2 / 2 / 3;
}
.img {
  width: 100%;
  height: 100%;
}
</style>
