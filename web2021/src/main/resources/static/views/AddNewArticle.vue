<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-row item-1">
        <Sidebar></Sidebar>
        <div class="item-1 container-column-2" style="background-color:white;margin:20px;border-radius:20px;">
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
                        required
                        style="margin-left:10px;margin-right:10px;"
                    ></v-text-field>

                    <v-text-field
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

                <v-btn
                    :disabled="!valid"
                    color="success"
                    class="mr-4"
                    @click="validate"
                    style="margin:20px;"
                    width="400px"
                >
                    Create restaurant
                </v-btn>
        </div>
    </div>
  </div>
</template>

<script>
module.exports = {
    name: 'AddNewArticle',
    data() {
        return {
            valid:true,
            message: '',
            showMessage: false,
            name: '',
            nameRules: [
              v => !!v || 'Name is required',
              v => (v && v.length <= 100) || 'Name must be less than 100 characters',
            ],
            price: null,
            priceRules: [
              v => !!v || 'Price is required and must be a number',
            ],
            description: '',
            descriptionRules: [
              v => (v && v.length <= 100) || 'Description must be less than 100 characters'
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
        async validate() {
            this.$refs.form.validate()
            if(this.selectedFoodType == null) {
                this.message = 'Please select food type';
                this.showMessage = true;
                return;
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
        axios.get("http://localhost:8080/rest/restaurant/get-all-restaurant-types")
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