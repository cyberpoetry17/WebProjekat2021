<template>
    <v-container style="background-color:white;margin-top:50px;border-radius:20px;">
        <v-row justify="center">
            <v-col>
                <v-text-field
                    v-model="search"
                    placeholder="enter name of restaurant"
                    label="enter name of restaurant" 
                    single-line 
                ></v-text-field>
            </v-col>
            <v-col class="d-flex" cols="4">
                <v-spacer></v-spacer>
                <v-switch
                    v-model="showAll"
                    :label="'Show all'"
                ></v-switch>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols=12>
            <v-data-table :headers="headers" :items="filteredComments" :search="search" style="margin:20px;">
                <template v-slot:item.additional="{ item }">
                    <v-btn @click.stop="showDialog=true" v-on:click="selectedComment = item" color="primary" style="margin:3px;">Show</v-btn>
                </template>
                <template v-slot:item.rating="{ item }">
                    <v-rating
                        v-model="item.rating"
                        readonly
                        color="yellow darken-3"
                        background-color="grey darken-1"
                        empty-icon="$ratingFull"
                        size="20"
                    ></v-rating>
                </template>
                <template v-slot:item.approve="{ item }">
                    <div v-if="!item.isReviewed && user.userType == 'MANAGER'">
                        <v-btn v-on:click="review(item, true)" style="margin:3px;" color="success">Approve</v-btn>
                        <v-btn v-on:click="review(item, false)" style="margin:3px;" color="error">Reject</v-btn>
                    </div>
                    <v-btn v-else-if="item.isReviewed && user.userType == 'MANAGER'" color="yellow">Reviewed</v-btn>
                    <v-btn v-else color="error">Unavailable</v-btn>
                </template>
            </v-data-table>
            </v-col>
        </v-row>
        <ViewComment v-model="showDialog" :comment=selectedComment ></ViewComment>
    </v-container>
</template>

<script>
module.exports = {
    name: 'Comments',
    computed: {
        user() {
            return this.$store.getters.getUser
        },
        filteredComments() {
            return this.comments.filter(i => {
                if(this.showAll) {
                    return i;
                }
                else {
                    if(!i.isReviewed) {
                        return i;
                    }
                }  
            })
        }
    },
    data() {
        return {
            comments: [],
            search: '',
            headers: [
                { text: 'Customer name', value: 'customer.name' },
                { text: 'Customer surname', value: 'customer.surname' },
                { text: 'Customer username', value: 'customer.username' },
                { text: 'Restaurant name',  value: 'restaurant.name' },
                { text: 'Rating', value: 'rating'},
                { text: 'View comment', filterable: false, sortable:false, value: 'additional'},
                { text: 'Review', filterable: false, sortable:false, value: 'approve'}
            ],
            showDialog: false,
            selectedComment: null,
            showAll: true
        }
    },
    methods: {
        review(item, isApproved) {
            item.isReviewed = true;
            item.isApproved = isApproved;
            var comment = {
                commentId: item.id,
                isApproved: isApproved
            }
            axios.post("http://localhost:8080/rest/comment/review-comment", comment)
        }
    },
    created() {
        if(this.user.userType == 'MANAGER') {
            axios.get("http://localhost:8080/rest/comment/get-comments-for-manager/" + this.user.restaurant.id)
                .then(r => {
                    this.comments = r.data
                })
        }   
        else {
            axios.get("http://localhost:8080/rest/comment/get-all-comments")
                .then(r => {
                    this.comments = r.data;
                })
        }
    }
}
</script>