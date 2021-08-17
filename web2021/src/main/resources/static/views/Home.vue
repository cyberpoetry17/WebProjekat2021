<template>
  <div class="background container-column">
    <Navbar></Navbar>
    <div class="container-row item-1">
      <Sidebar></Sidebar>
      <div class="item-1 container-column-2" style="background-color:white;margin:20px;border-radius:20px;">
            <v-data-table
              :headers="headers"
              :items="users"
              :search="search"
            ></v-data-table>
      </div>
    </div>
  </div>
</template>

<script>

module.exports = {
    name: 'Home',
    data() {
      return {
        headers: [
            { text: 'Username', value: 'username' },
            { text: 'Name', value: 'name' },
            { text: 'Surname', value: 'surname' },
            { text: 'Gender', filterable: false, sortable: false, value: 'genderType' },
            { text: 'Birthday', filterable: false, sortable: false, value: 'birthday' },
            { text: 'User Type', filterable: false, sortable: false, value: 'userType'},
            { text: 'Points', filterable: false, value: 'points'}
        ],
        users: [],
        search: ''
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

  .container-column-2 {
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
    align-items: stretch;
  }

  .item-1 {
    flex-grow: 1;
  }
</style>
