<template>
  <div class="background container-column">

    <div class="container-row item-1">
    
      <div
        class="item-1 container-column-2"
        style="background-color: white; margin: 20px; border-radius: 20px"
      >
        <div class="main-frame-div">
          <h3>Shopping cart: {{this.user.name + " " + this.user.surname}}</h3>
          <div class="parent">
            <div class="div1">
              <div></div>
              <v-card
                class="mx-auto my-12"
                max-width="300"
                elevation="20"
                v-for="item in articles"
                :key="item.id"
              >
                <div class="d-flex flex-no-wrap justify-space-between">
                  <div>
                    <v-card-title
                      class="text-h5"
                      v-text="item.article.name"
                    ></v-card-title>
                    <v-card-text>
                      <div>${{ item.article.price }}</div>
                    </v-card-text>

                    <v-card-subtitle v-text="item.artist"></v-card-subtitle>
                    <div class="item-quantity">
                      <v-btn
                        small
                        color="accent"
                        elevation="2"
                        outlined
                        raised
                        @click="decrement(item)"
                        >-</v-btn
                      >
                      <label> {{ item.quantity }} </label>
                      <v-btn
                        small
                        color="accent"
                        elevation="2"
                        outlined
                        raised
                        @click="increment(item)"
                        >+</v-btn
                      >
                    </div>
                  </div>
                  <!-- <v-divider inset></v-divider> -->
                  <v-avatar class="ma-3" size="150" tile>
                    <v-img :src="item.article.image"></v-img>
                  </v-avatar>
                </div>
                <v-divider></v-divider>
                <v-card-actions>
                  <v-btn
                    @click="removeArticle(item)"
                    color="deep-purple lighten-2"
                    text
                    >remove</v-btn
                  >
                </v-card-actions>
                <!-- <v-img class="img" :src="item.article.image"></v-img>
                <v-card-title>{{ item.article.name }}</v-card-title>
                <v-card-text>
                  <div>${{ item.article.price }}</div>
                </v-card-text>
                <div class="parent1">
                  <div class="div11">
                    <v-btn
                      small
                      color="accent"
                      elevation="2"
                      outlined
                      raised
                      @click="decrement(item)"
                      >-</v-btn
                    >
                  </div>
                  <div class="div21">
                    <label>{{ item.quantity }}</label>
                  </div>
                  <div class="div31">
                    <v-btn
                      small
                      color="accent"
                      elevation="2"
                      outlined
                      raised
                      @click="increment(item)"
                      >+</v-btn
                    >
                  </div>
                </div>

                <v-divider class="mx-4"></v-divider>
                <v-card-actions>
                  <v-btn
                    @click="removeArticle(item)"
                    color="deep-purple lighten-2"
                    text
                    >remove</v-btn
                  >
                </v-card-actions>-->
              </v-card>
            </div>

            <div class="div2">
              <v-card>
                <v-card-title>The total amount:</v-card-title>
                <v-card-text>$ {{ this.totalAmount }} </v-card-text>
                <div></div>
                <v-card-actions>
                  <v-btn color="deep-purple" text @click="purchase">PURCHASE</v-btn>
                </v-card-actions>
              </v-card>
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
  },
  data() {
    return {
      articles: [{ id: 0, quantity: 1, article: {} }],
      user: { name: "", surname: "" },
      totalAmount: 0,
      shopping_cart: {
        id: 0,
        articles: [],
        price: 0,
      },
    };
  },
  methods: {
    removeArticle(item) {
      this.articles.splice(this.articles.indexOf(item), 1);

      this.totalAmount = this.totalAmount - item.quantity * item.article.price;
    },
    totalAmountCalculator() {
      var price = 0;
      this.articles.forEach(function (arrayItem) {
        price = price + arrayItem.article.price;
      });
      this.totalAmount = price;
    },
    increment(item) {
      var vm = this;
      this.articles.forEach(function (arrayItem) {
        if (arrayItem.article.id === item.article.id) {
          if (arrayItem.article.quantity > item.quantity) {
            arrayItem.quantity = arrayItem.quantity + 1; //
            vm.totalAmount = vm.totalAmount + arrayItem.article.price;
            console.log("UKUPNA CENA JE " + vm.totalAmount);
          }
        }
      });
    },
    decrement(item) {
      var vm = this;
      this.articles.forEach(function (arrayItem) {
        if (arrayItem.article.id === item.article.id) {
          if (arrayItem.quantity > 0) {
            arrayItem.quantity = arrayItem.quantity - 1;
            vm.totalAmount = vm.totalAmount - arrayItem.article.price;
            console.log("UKUPNA CENA JE " + vm.totalAmount);
          }
        }
      });
    },

    purchase(){
      //nije smanjen broj artikala koji je u bazi
      this.shopping_cart.price = this.totalAmount;
      this.shopping_cart.articles = this.articles; //valjda ovo treba
    
    }
  },
  mounted() {
    /**
     * otkomentarisati zakomentarisano
     */

    var user = this.getUser;
    this.user.name = user.name;
    this.user.surname = user.surname;

    //quantity articles
    // this.articles = user.shopping_cart.articles

    //zakomentarisati dummy podatke
    var item = {
      quantity: 1,
      id: 1,
      article: {
        id: "123",
        name: "hrana",
        price: 200,
        articleType: "hrana",
        restaurantId: "123",
        quantity: 10,
        description: "bla bla",
        image: "./img/Capture.PNG",
      },
    };
    var item2 = {
      quantity: 1,
      id: 2,
      article: {
        id: "1234",
        name: "hrana",
        price: 200,
        articleType: "hrana",
        restaurantId: "123",
        quantity: 10,
        description: "bla bla",
        image: "./img/Capture.PNG",
      },
    };
    //zakomentarisati
    this.articles.push(item);
    this.articles.push(item2);

    //ovo ne brisati
    this.articles.shift();
    this.totalAmountCalculator();
    this.shoppingCart.id = this.user.shoppingCart.id;
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
.parent1 {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: 1fr;
  grid-column-gap: 0px;
  grid-row-gap: 0px;
  margin-right: 15px;
}
.div11 {
  grid-area: 1 / 1 / 2 / 2;
  text-align: center;
}
.div21 {
  grid-area: 1 / 2 / 2 / 3;
  text-align: center;
  /* background-color: aqua; */
}
.div31 {
  grid-area: 1 / 3 / 2 / 4;
  text-align: center;
}
.item-quantity {
  margin-left: 0px;
}
</style>
