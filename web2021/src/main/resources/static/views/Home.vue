<template>
  <div class="background container-1">
    <Navbar></Navbar>
    <Sidebar></Sidebar>
  </div>
</template>

<script>

module.exports = {
    name: 'Home'
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

  .container-1 {
    display: flex;
    flex-direction: column;
  }

  .item-1 {
    flex-grow: 1;
  }
</style>
