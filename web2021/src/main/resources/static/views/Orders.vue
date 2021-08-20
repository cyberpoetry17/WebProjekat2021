<template>
    <v-container style="background-color:white;border-radius:20px;margin-top:50px;" fill-height>
        <v-row justify="center">
            <v-col cols="4">
                <v-text-field
                    v-model="name"
                    placeholder="enter name of restaurant"
                    label="enter name of restaurant" 
                    shaped 
                    solo 
                    single-line 
                    hide-details
                    :disabled="this.user.userType != 'CUSTOMER' && this.user.userType != 'COURIER'"
                ></v-text-field>
            </v-col>
            <v-col cols="4">
                <v-menu
                    v-model="fromCalendar"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                >
                    <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                        v-model="fromDate"
                        label="Select from date"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                    ></v-text-field>
                    </template>
                    <v-date-picker
                    v-model="fromDate"
                    @input="fromCalendar = false"
                    ></v-date-picker>
                </v-menu>
            </v-col>
            <v-col cols="4">
                <v-autocomplete
                    shaped
                    solo
                    placeholder="filter by restaurant type"
                    :items="restaurantTypes"
                    v-model="selectedRestaurantType"
                    :disabled="this.user.userType != 'CUSTOMER' && this.user.userType != 'COURIER'"
                ></v-autocomplete>
            </v-col>
            <v-col cols="4">
                <v-range-slider
                    v-model="range"
                    :max="1000"
                    :min="1"
                    hide-details
                    class="align-center"
                >
                    <template v-slot:prepend>
                    <v-text-field
                        :value="range[0]"
                        class="mt-0 pt-0"
                        hide-details
                        single-line
                        type="number"
                        v-model="range[0]"
                        style="width: 60px"
                        step="0.1"
                        @change="$set(range, 0, $event)"
                    ></v-text-field>
                    </template>
                    <template v-slot:append>
                    <v-text-field
                        :value="range[1]"
                        v-model="range[1]"
                        class="mt-0 pt-0"
                        hide-details
                        single-line
                        type="number"
                        step="0.1"
                        style="width: 60px"
                        @change="$set(range, 1, $event)"
                    ></v-text-field>
                    </template>
                </v-range-slider>
            </v-col>
            <v-col cols="4">
                <v-menu
                    v-model="toCalendar"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                >
                    <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                        v-model="toDate"
                        label="Select end date"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                    ></v-text-field>
                    </template>
                    <v-date-picker
                    v-model="toDate"
                    @input="toCalendar = false"
                    ></v-date-picker>
                </v-menu>
            </v-col>
            <v-col cols="4">
                <v-autocomplete
                    shaped
                    solo
                    placeholder="filter by order status"
                    :items="orderStatus"
                    v-model="selectedOrderStatus"
                ></v-autocomplete>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="12">
                <v-data-table :headers="headers" :items="filteredOrders" style="margin:20px;">
                    <template v-slot:item.image="{ item }">
                        <v-img :src="item.image" style="width: 50px; height: 50px; border-radius:50%; margin:5px;"></v-img>
                    </template>

                    <template v-slot:item.additional="{ item }">
                        <!-- <v-btn color="green" dark> --> 
                        <v-icon @click.stop="showDialog=true" v-on:click="openAdditionalInformation(item)" color="green" large>mdi-dots-horizontal-circle</v-icon>
                        <!-- </v-btn> -->
                        <!-- <v-icon small class="mr-2" @click="editItem(item)">edit</v-icon>
                        <v-icon small @click="deleteItem(item)">delete</v-icon> -->
                    </template>
                </v-data-table>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
module.exports = {
    name: 'Orders',
    computed: {
        user() {
            return this.$store.getters.getUser;
        },
        filteredOrders() {
            return this.orders.filter(i => {
                if(this.filterByName(i)) {
                    if(this.filterByPrice(i)) {
                        if(this.filterByRestaurantType(i)) {
                            if(this.filterByOrderStatus(i)) {
                                if(this.filterByDate(i)) {
                                    i.dateOfOrder = this.formatDate(new Date(i.dateOfOrder));
                                    return i;
                                }
                            }
                        }
                    }
                }
            })
        }
    },
    data() {
        return {
            name: '',
            range: [0,10000],
            fromDate: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOS,
            fromCalendar: false,
            toDate: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOS,
            toCalendar: false,
            restaurantTypes: [],
            selectedRestaurantType: null,
            orderStatus: [],
            selectedOrderStatus: null,
            orders: [],
            headers: [
                { text: 'Restaurant', value: 'restaurant.name' },
                { text: 'Price',  value: 'price' },
                { text: 'Date of order', value: 'dateOfOrder' },
                { text: 'Status', sortable: false,  value: 'orderStatus' },
                { text: 'Logo', filterable: false, sortable:false, value:'image'},
                { text: 'More', filterable: false, sortable:false, value: 'additional'}
            ]
        }
    },
    methods: {
        filterByName(item) {
            if(this.name == "") return true;
            if(item.restaurant.name.toLowerCase().includes(this.name.toLowerCase())) return true;
            return false
        },
        filterByPrice(item) {
            if(item.price >= this.range[0] && item.price <= this.range[1]) return true;
            return false;
        },
        filterByRestaurantType(item) {
            if(this.selectedRestaurantType == null) return true;
            if(item.restaurant.restaurantType == this.selectedRestaurantType) return true;
            return false;
        },
        filterByOrderStatus(item) {
            if(this.selectedOrderStatus == null) return true;
            if(item.orderStatus == this.selectedOrderStatus) return true;
            return false;
        },
        filterByDate(item) {
            if(this.fromDate == null || this.toDate == null) return true;
            var fromDate = this.getDateTimeFromString(this.fromDate.toString(), "00:00").getTime();
            var toDate = this.getDateTimeFromString(this.toDate.toString(), "00:00").getTime();
            var compare = this.getDateTimeFromString(item.dateOfOrder.toString(), "00:00").getTime();
            if(compare>= fromDate && compare <= toDate) return true;
            return false;
        },
        // Expected yy-mm-dd and HH:mm format
        getDateTimeFromString: function(dstr, tstr) {
            let dparts = dstr.split('-');
            let tparts = tstr.split(':');
            // -1 because js counts months from 0
            return new Date(dparts[0], dparts[1] - 1, dparts[2], tparts[0], tparts[1]);
        },
        formatDate(date) {
            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();

            if (month.length < 2) 
                month = '0' + month;
            if (day.length < 2) 
                day = '0' + day;

            return [year, month, day].join('-');
        }
    },
    created() {
        axios.get("http://localhost:8080/rest/order/get-order-status")
            .then(r => {
                this.orderStatus = r.data;
            })
        axios.get("http://localhost:8080/rest/restaurant/get-all-restaurant-types")
            .then(r => {
                this.restaurantTypes = r.data;
        })
        if(this.user.userType == 'CUSTOMER') {
            axios.get("http://localhost:8080/rest/order/get-orders-for-customer/" + this.user.id)
                .then(r => {
                    this.orders = r.data;
                })
            }
        else if (this.user.userType == 'COURIER') {
            axios.get("http://localhost:8080/rest/order/get-orders-for-courier/" + this.user.id)
                .then(r => {
                    this.orders = r.data;
                })
        }
        else if (this.user.userType == 'MANAGER') {
            axios.get("http://localhost:8080/rest/order/get-orders-for-manager/" + this.user.restaurant.id)
                .then(r => {
                    this.orders = r.data;
                })
        }
        else {

        }
    }
}
</script>

<style scoped>

</style>