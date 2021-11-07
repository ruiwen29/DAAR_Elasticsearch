<template>
<div>
  <b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand style="position:relative;left:20px" href="/">CV Indexing</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <router-link style="position:relative;left:20px" to="/addCV">Post Your CV</router-link>


        <router-link style="position:relative;left:50px" to="/search">Search CV</router-link>

      </b-navbar-nav>





    </b-collapse>
  </b-navbar>
</div>
</template>
<script>
export default {
    data() {
        return {
        }
    },
  methods: {

    }
  }

</script>