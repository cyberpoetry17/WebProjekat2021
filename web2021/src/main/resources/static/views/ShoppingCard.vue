<template>
  <v-container style="background-color:white;border-radius:20px;margin-top:50px;">
    <v-row>
      <v-col>
        <label style="font-size:25px;"><b>MY SHOPPING CART</b></label>
        <v-row>
          <v-col cols="1">

          </v-col>
          <v-col align="left" cols="6">
            <label><b>ITEM</b></label>
          </v-col>
          <v-col align="center" cols="2">
            <label><b>QUANTITY</b></label>
          </v-col>
          <v-col align="center" cols="2">
            <label><b>UNIT PRICE</b></label>
          </v-col>
          <v-col align="right">
            <label><b>TOTAL PRICE</b></label>
          </v-col>
        </v-row>
        <v-divider class="border-color: black !important;"></v-divider>
        <div v-for="item in getShoppingCart.articles" :key="item.id">
          <v-row>
            <v-col align="center" align-self="center" cols="1">
              <v-btn
                class="ma-2"
                text
                icon
                color="error"
                v-on:click="removeArticle(item)"
              >
                <v-icon>mdi-close-circle</v-icon>
              </v-btn>
            </v-col>
            <v-col cols="1">
              <v-img
                :src="item.article.image"
                height="125"
                width="125"
                style="margin-top:10px;"
              >
                
              </v-img>
            </v-col>
            <v-col cols="5">
              <div class="font-weight-medium" style="margin-top:5px;">{{item.article.name}}</div>
              <div class="grey--text" style="font-size:12px;">S/N: {{item.article.id}}</div>
              <div class="grey--text" style="font-size:12px;">Type: {{item.article.articleType}}</div>
            </v-col>
            <v-col align-self="center" cols="2">
              <div style="display:flex;justify-content:center;">
                    <v-btn
                      text
                      icon
                      color="success lighten-2"
                      v-on:click="increment(item)"
                    >
                      <v-icon>mdi-plus-thick</v-icon>
                    </v-btn>
                    <div style="margin-left:20px; margin-top:5px; margin-right:20px">{{item.quantity}}</div>
                    <v-btn
                      text
                      icon
                      color="error lighten-2"
                      v-on:click="decrement(item)"
                    >
                      <v-icon>mdi-minus</v-icon>
                    </v-btn>
              </div>
            </v-col>
            <v-col align="center" cols="2">
              <div style="font-size:17px;margin-top:5px;">${{item.article.price}}</div>
            </v-col>
            <v-col align="right" cols="1">
              <div style="font-size:17px;margin-top:5px;">${{item.article.price * item.quantity}}</div>
            </v-col>
          </v-row>
          <v-divider class="mx-4" style="margin-top:10px;"></v-divider>
        </div>
        <v-row>
          <v-col align="right">
            <div class="font-weight-medium" style="margin-top:10px;margin-right:10px;">Total: ${{getShoppingCart.price}}</div>
            <v-btn
              color="red"
              dark
              style="margin:10px;"
              v-on:click="purchase"
              :disabled="getShoppingCart.articles.length == 0"
            ><v-icon>mdi-cart</v-icon>Purchase</v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>

  </v-container>
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
      if(item.quantity + 1 > item.article.quantity) return;
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
      if(item.quantity - 1 == 0) return;
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

</style>