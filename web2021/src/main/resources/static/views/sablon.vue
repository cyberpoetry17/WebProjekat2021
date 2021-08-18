<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-row item-1">
        <Sidebar></Sidebar>
        <div class="item-1" style="background-color:white;margin:20px;border-radius:20px;">
                
        </div>
    </div>
  </div>
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

        }
    },
    methods: {
        
    },
    created() {
        
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