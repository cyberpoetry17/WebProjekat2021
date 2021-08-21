<template>
    <v-dialog v-if="order != null" v-model="show" max-width=500>
        <v-card
            class="elevation-16 mx-auto"
        >
            <v-card-title class="text-h5">
                Rate our service
            </v-card-title>
            <v-card-text>
            If you are satisfied, please take a few seconds to rate your experience with our service. It really helps!
                <v-form 
                    ref="form"
                    v-model="valid"
                    lazy-validation
                    class="text-center"
                >
                    <v-text-field
                        v-model="description"
                        :rules="descriptionRules"
                        label="enter description"
                        placholder="Write something good"
                        required
                        style="margin-left:10px;margin-right:10px;"
                    ></v-text-field>
                    <!-- <div class="text-center mt-12"> -->
                        <v-rating
                            v-model="rating"
                            color="yellow darken-3"
                            background-color="grey darken-1"
                            empty-icon="$ratingFull"
                            hover
                            large
                            :rules="ratingRules"
                        ></v-rating>
                        <label v-if="messageShow" style="color:red;">{{message}}</label>
                    <!-- </div> -->
                </v-form>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-actions class="justify-space-between">
            <v-btn v-on:click="show = false" text>
                No Thanks
            </v-btn>
            <v-btn
                color="primary"
                text
                v-on:click="validate"
            >
                Rate Now
            </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
module.exports = {
    name: 'Review',
    props: {
        value: Boolean,
        order: Object
    },
    computed: {
        show: {
            get() {
                return this.value
            },
            set(value) {
                this.$emit('input', value)
                this.description = '';
                this.rating = null;
            }
        },
        user() {
            return this.$store.getters.getUser
        }
    },
    data() {
        return {
            rating: null,
            ratingRules: [
              v => (this.rating != null) || 'Please rate us :('
            ],
            description: '',
            descriptionRules: [
                v => !!v || 'Enter some description',
                v => (v && v.length < 100) || 'Description is too long',
            ],
            message: '',
            messageShow: false,
            valid: true
        }
    },
    methods: {
        validate () {
            if(this.rating == null) {
                this.message = 'Please rate us :('
                this.messageShow = true;
                return;
            }
            if(this.$refs.form.validate()) {
                this.message = ''
                this.messageShow = false;
                this.addComment()
            }
        },
        addComment() {
            var comment = {
                orderId: this.order.id,
                customerId: this.order.customer.id,
                restaurantId: this.order.restaurant.id,
                description: this.description,
                rating: this.rating
            }
            axios.post("http://localhost:8080/rest/comment/add-comment", comment)
            this.order.isReviewed = true;
            this.show = false;
        }
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

