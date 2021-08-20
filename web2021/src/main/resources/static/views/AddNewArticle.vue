<template>
    <v-container style="max-width:600px;background-color:white;border-radius:20px;margin-top:50px;" fill-height>
        <v-row>
            <v-col>
                <v-card-title primary-title class="justify-center" style="font-size:36px;">
                    Add article
                </v-card-title>
                <v-form
                    ref="form"
                    v-model="valid"
                    lazy-validation
                    class="text-center"
                >   
                    <v-text-field
                        v-model="name"
                        :rules="nameRules"
                        label="enter name"
                        placeholder="enter name"
                        v-on:input="checkIsNameValid"
                        required
                        style="margin-left:10px;margin-right:10px;"
                    ></v-text-field>

                    <v-text-field
                        prefix="$"
                        type="number"
                        v-model="price"
                        :rules="priceRules"
                        label="enter price"
                        placeholder="enter price"
                        required
                        style="margin-left:10px;margin-right:10px;"
                    ></v-text-field>

                    <v-text-field
                        type="description"
                        v-model="description"
                        :rules="descriptionRules"
                        label="enter description"
                        placeholder="enter description"
                        style="margin-left:10px;margin-right:10px;"
                    ></v-text-field>
                    
                    <v-text-field
                        type="number"
                        v-model="quantity"
                        :rules="quantityRules"
                        label="enter quantity"
                        placeholder="enter quantity"
                        style="margin-left:10px;margin-right:10px;"
                    ></v-text-field>

                    <v-file-input
                        v-model="image"
                        :rules="imageRules"
                        accept="image/*"
                        label="Select image"
                        chips
                        prepend-icon="mdi-camera"
                        style="margin-left:10px;margin-right:10px;"
                    ></v-file-input>

                    <v-menu offset-y>
                        <template v-slot:activator="{ on, attrs }">
                            <v-btn v-if="selectedFoodType == null || selectedFoodType == ''" color="primary" dark v-bind="attrs" v-on="on">
                                Select type
                                <v-icon>mdi-chevron-down</v-icon>
                            </v-btn>
                            <v-btn v-else color="primary" dark v-bind="attrs" v-on="on">
                                {{selectedFoodType}}
                                <v-icon>mdi-chevron-down</v-icon>
                            </v-btn>
                        </template>
                        <v-list>
                            <v-list-item v-for="(item, index) in items" :key="index" v-on:click="selectedFoodType = item">
                                <v-list-item-title>{{ item }}</v-list-item-title>
                            </v-list-item>
                        </v-list>
                    </v-menu>
                    <label v-if="showMessage" style="color:red"><b>{{message}}</b></label>
                </v-form>
            </v-col>
        </v-row>
        <v-row justify="center">
            <v-btn
                :disabled="!valid"
                color="success"
                class="mr-4"
                @click="validate"
                style="margin:20px;"
                width="300px"
            >
                Add article
            </v-btn>
        </v-row>
    </v-container>
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
            valid:true,
            message: '',
            showMessage: false,
            name: '',
            nameRules: [
              v => !!v || 'Name is required',
              v => (v && v.length <= 100) || 'Name must be less than 100 characters',
              v => (v && this.nameValid) || 'Name is already taken'
            ],
            nameValid: true,
            price: null,
            priceRules: [
              v => !!v || 'Price is required and must be a number',
            ],
            description: '',
            descriptionRules: [
              v => (v.length == 0 || v.length <= 100) || 'Description must be less than 100 characters'
            ],
            quantity: null,
            quantityRules: [
              
            ],
            image: null,
            imageRules: [
              v => (this.image != null) || 'Image is required'
            ],
            items: [],
            selectedFoodType: null,
        }
    },
    methods: {
        checkIsNameValid() {
            var check = {
                restaurantId: this.user.restaurant.id,
                name: this.name
            }
            axios.post("http://localhost:8080/rest/article/check-is-name-taken", check)
                .then(r => {
                    if(r.data == true) this.nameValid = false;
                    else this.nameValid = true;
                    this.$refs.form.validate()
                })
        },
        async validate() {
            if(!this.$refs.form.validate()) {
                return;
            }
            else if(this.selectedFoodType == null) {
                this.message = 'Please select food type';
                this.showMessage = true;
                return;
            }
            else {
                this.message = '';
                this.showMessage = false;
                var quantity;
                if(this.quantity == "") quantity = 0;
                else quantity = this.quantity;
                var article = {
                    name: this.name,
                    price: this.price,
                    articleType: this.selectedFoodType,
                    quantity: quantity,
                    description: this.description,
                    image: await this.upload(),
                    restaurantId: this.user.restaurant.id
                }
                axios.post("http://localhost:8080/rest/article/add-article", article)
                    .then(r => {
                        alert("Article is successfuly added!")
                        this.name = '';
                    })
            }
        },
        async upload() {
            const fileToUpload = new FormData();
            fileToUpload.append('file', this.image);
            var image = await axios.post("http://localhost:8080/rest/file/upload", fileToUpload)
                .then(r => {
                    return r.data;
                })
            return image;
        },
    },
    created() {
        axios.get("http://localhost:8080/rest/article/get-all-aricle-types")
            .then(r => {
                this.items = r.data;
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

  .container-column-2 {
    display: flex;
    flex-direction: column;
    align-items: stretch;
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