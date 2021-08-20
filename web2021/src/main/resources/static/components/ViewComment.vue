<template>
    <v-dialog v-if="comment != null" v-model="show" max-width=500>
        <v-card
            class="elevation-16 mx-auto"
        >
            <v-card-title>Comment description:</v-card-title>
            <v-divider class="mx-4"></v-divider>
            <v-card-text>{{comment.description}}</v-card-text>
        </v-card>
    </v-dialog>
</template>

<script>
module.exports = {
    name: 'ViewComment',
    props: {
        value: Boolean,
        comment: Object
    },
    computed: {
        show: {
            get() {
                return this.value
            },
            set(value) {
                this.$emit('input', value)
            }
        }
    }
}
</script>

