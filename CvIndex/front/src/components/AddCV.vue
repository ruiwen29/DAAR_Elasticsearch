<template >

<div class="card" style="width: 18rem; position:relative;top:20px;left:20px">
<form role="form">
  <div class="form-group">
    <input type="text" class="form-control" v-model="nom" id="nom" placeholder="nom" enctype=multipart/form-data>
    <input type="text" class="form-control" v-model="prenom" id="prenom" placeholder="prenom">
    <input type="text" class="form-control" v-model="tel" id="tel" placeholder="tel">
    <input type="text" class="form-control" v-model="email" id="tel" placeholder="email">
  </div>
  <div class="form-group">
    <input type="file"  id="fileInput" enctype=multipart/form-data>
  </div>
<input class="btn btn-primary"  value="Submit"  @click="submitFile">
<input class="btn btn-primary" type="reset" value="Reset" >

</form>
</div>


</template>

<script>

import axios from 'axios'
export default {
  name: 'AddCV',
  data: function () {
    return {
      fileInput: [],
      nom: "",
      prenom: "",
      email: "",
      tel: "",
      valid: true,
      snackbar: false,
      notifColor: "",
      text: ""
    }
  },
  methods: {
     greet: function (event) {
        alert('Hello ' + this.name + '!')
        if (event) {
          alert(event.target.tagName)
        }
      },

    validate () {
      this.$refs.formcv.validate()
    },
    A:function(){
      this.$refs.formcv.reset()
    },

    submitFile(){
      let formData = new FormData();

      formData.append('file', document.getElementById("fileInput").files[0]);
      formData.append('prenom', this.prenom);
      formData.append('nom', this.nom);
      formData.append('mail', this.email);
      formData.append('tel', this.tel);

      console.log('>> formData >> ', formData);


      axios.post( 'http://localhost:8080/CV/file',
        formData,
        {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
      }).then((p) => {
        this.notifColor="success"
        this.text="Upload Réussi";
        this.snackbar=true;
        console.log(p);
        })
      .catch((p) => {
        this.notifColor="error"
        this.text="Erreur d'upload";
        this.snackbar=true;
        console.log(p.response);

      });
    }
  }
}
</script>
