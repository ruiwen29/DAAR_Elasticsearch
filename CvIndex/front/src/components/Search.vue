<template>
  <b-container>

<b-card-group deck>
<b-col cols="7" offset-lg="1" style=" position:absolute;top:80px;left:0px">

<b-card  title="Search by name and first name"  style="max-width: 20rem;"    class="mb-2">
<div>
<b-form inline>
<label class="sr-only" for="inline-form-input-name">Name</label>
<b-form-input
v-model="nom"
class="mb-2 mr-sm-2 mb-sm-0"
placeholder="">
</b-form-input>

<label class="sr-only" for="inline-form-input-username">First name</label>
<b-input-group  class="mb-2 mr-sm-2 mb-sm-0">
<b-form-input  v-model="prenom" placeholder=""></b-form-input>
</b-input-group>
<b-btn
elevation="4"
medium
blue
rounded
@click="profilsNameRecherche"
>
                Search
            </b-btn>      </b-form>
    </div>
  </b-card>
    </b-col>

  <b-col cols="7" offset-lg="1" style=" position:absolute;top:80px;right:250px">

    <b-card  title="Select competences"  style="max-width: 20rem;"    class="mb-2">
                          <b-btn
                            elevation="4"
                            medium

                            rounded
                            @click="filtrageRecherche"
                          >
                            <div class="mr-1">
                            searchByFilters
                            </div>
                          </b-btn>
    <b-form-group label="" >
      <b-form-checkbox-group
        id="competences"
        v-model="selected"
        :options="programmingLanguages"
        name="competences"
      ></b-form-checkbox-group>

    </b-form-group>


     </b-card>
         <b-card  title="Select degrees"  style="max-width: 20rem;"    class="mb-2">
         <b-form-group label=""  >
           <b-form-checkbox-group
             id="degrees"
             v-model="selected"
             :options="degrees"
             name="degrees"
           ></b-form-checkbox-group>

         </b-form-group>
  </b-card>
   </b-col>

  <b-col cols="7" offset-lg="1" style=" position:absolute;top:80px;right:0px">
  <CVList  :listProfils="response"/>
  </b-col>
 </b-card-group>

  </b-container>


</template>

<script>
import axios from 'axios'
import CVList from "./CVList";


export default {
  name: 'Recherche',
  data: function() {
    return {
      prenom: "",
      nom: "",
      selected: [],
      programmingLanguages: [
        'c','c#','c++','caml','clojure', 'cofeescript',
        'css','dotnet','go','groovy','hadoop', 'haskell',
        'html','java','javascript','lua','matlab', 'nosql',
        'objectivec','ocaml', 'perl','php','python','r',
        'ruby', 'rust', 'scala', 'shell', 'spark', 'sql',
        'swift', 'typescript', 'uml', 'vba',
      ],
      degrees: [
        'Baccalauréat', 'Bts', 'Dut', 'Licence',
        'Master', 'Doctorat', 'Ecole'
      ],
      response: [],
    }
  },
  methods: {
    profilsNameRecherche(){
      if(this.nom.length==0){
        this.getByName("prenom", this.prenom)
      }
      else if(this.prenom.length==0){
        this.getByName("nom", this.nom)
      }
      else {
        axios
        .get('http://localhost:8080/CV?nom='+this.nom+"&prenom="+this.prenom)
        .then(response => (this.response = response.data))
        .catch((p) => {
          console.log(p)
        });
      }
    },
    filtrageRecherche(){
      if (this.selected.length==0){
        this.getAllCv()
      } else {
        this.filter()
      }
    },
    getByName: function(nameType, name){
      axios
      .get('http://localhost:8080/CV?'+nameType+"="+name)
      .then(response => (this.response = response.data))
      .catch((p) => {
        console.log(p)
      });
    },
    getAllCv: function(){
      axios
      .get('http://localhost:8080/CV')
      .then(response => (this.response = response.data.content))
      .catch((p) => {
        console.log(p)
      });
    },
    filter: function(){
      var arg = this.wrapFiltrage()
      console.log(arg)
      axios
      .get('http://localhost:8080/CV?filtres='+arg.join(','))
      .then(response => (this.response = response.data))
      .catch((p) => {
        console.log(p)
      });
    },
    wrapFiltrage(){
      var listRes = this.selected;
      for(var i=0; i<listRes.length; i++){
        switch(listRes[i]){
          case "c++":
            listRes[i]="cpp"
            break;
          case "c#":
            listRes[i]="csharp"
            break;
          default:
        }
      }
      return listRes;
    }
  },
  components: {
    CVList
  }
}

</script>
