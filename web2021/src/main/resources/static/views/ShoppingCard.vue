<template>
  <div class="background container-column">
    <div class="container-row item-1">
      <div
        class="item-1 container-column-2"
        style="background-color: white; margin: 20px; border-radius: 20px"
      >
        <div class="main-frame-div">
          <h3>Shopping cart: {{this.getUser.name + " " + this.getUser.surname}}</h3>
          <div class="parent">
            <div class="div1">
              <div></div>
              <v-card
                class="mx-auto my-12"
                max-width="300"
                elevation="20"
                v-model="getShoppingCart.articles"
                v-for="item in getShoppingCart.articles"
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
              </v-card>
            </div>

            <div class="div2">
              <v-card>
                <v-card-title>The total amount:</v-card-title>
                <v-card-text>$ {{ getShoppingCart.price }} </v-card-text>
                <div></div>
                <v-card-actions>
                  <v-btn color="deep-purple" text v-on:click="purchase"
                    >PURCHASE</v-btn
                  >
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
    getShoppingCart(){
      return this.$store.getters.getShoppingCart;
    }
  },
  data() {
    return {
      totalAmount: 0
    }
  },
  methods: {
    removeArticle(item) {
      var shoppingCart = {
        userId: this.getUser.id,
        articleId:item.article.id,
        quantity:item.quantity
      }
      axios.put("http://localhost:8080/rest/customer/remove-article",shoppingCart)
        .then((r) => {
            this.$store.dispatch("updateShoppingCart", r.data);
      });
    },
    increment(item) {
      var increment = {
        id:item.id,
        userId: this.getUser.id,
      }
      axios.put("http://localhost:8080/rest/customer/increment-article-quantity",increment)
        .then((r) => {
            this.$store.dispatch("updateShoppingCart", r.data);
        });
    },
    decrement(item) {
      var decrement = {
        id:item.id,
        userId: this.getUser.id,
      }
      axios.put("http://localhost:8080/rest/customer/decrement-article-quantity",decrement)
        .then((r) => {
            this.$store.dispatch("updateShoppingCart", r.data);
        });
    },

    purchase() {
      if (this.getUser.shoppingCart.articles.length == 0) {
        alert("Shopping cart is emtpy.");
        return;
      }
      axios.get("http://localhost:8080/rest/order/make-order/" + this.getUser.id)
          .then((r) => {
              this.$store.dispatch("updateUser", r.data[0].customer);
          });
    },
  }
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
