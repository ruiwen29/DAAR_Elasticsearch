<template>
  <div>
    <v-card
      height="600"
      max-width="800"
      elevation="16"
      class="mx-auto pb-2 pt-2 pl-2 justify-center"
      v-if="listProfils==null || listProfils.length==0"
    >
      <b-card
        style="width:100%;"
      >
        <h1> Current search empty </h1>
        <p/> <p/> <p/>
        <h3> Try again </h3>
        <p/>
        <ol>
          <li> Either by search of name and or first name </li>
          <li> Either by conjunction of filtering</li>
        </ol>
        <p/> <p/> <p/>
      </b-card>

    </v-card>
    <v-card
      elevation="16"
      max-width="800"
      class="mx-auto pb-2 pt-2 pl-2"
      v-if="listProfils != null && listProfils.length>0"
    >
      <v-virtual-scroll
        :items="listProfils"
        height="600"
        item-height="64"
      >
        <template v-slot:default="{ item }">
          <v-list-item :key="item.id">
            <v-list-item-action>

            </v-list-item-action>

            <v-list-item-content>
              <v-list-item-title>
                <v-row>
                  <v-col>
                    <strong>
                      {{ item.nom }} {{item.prenom }}
                    </strong>
                  </v-col>

                  <v-list-item-content>
                  <v-list-item-title>Competence</v-list-item-title>
                  <v-list-item-subtitle> {{ item.competences ? item.competences.join(", ") : '' }} </v-list-item-subtitle>
                  </v-list-item-content>
                  <v-list-item-content>
                  <v-list-item-title> {{ item.email }} </v-list-item-title>
                  <v-list-item-subtitle>Email</v-list-item-subtitle>
                  </v-list-item-content>
                  <v-list-item-content>
                  <v-list-item-title> {{ item.numTel }} </v-list-item-title>
                  <v-list-item-subtitle>TEL number</v-list-item-subtitle>
                  </v-list-item-content>
                </v-row>
              </v-list-item-title>
            </v-list-item-content>

            <v-list-item-action>

            </v-list-item-action>
          </v-list-item>

          <v-divider></v-divider>
        </template>
      </v-virtual-scroll>
    </v-card>
    <v-overlay
      z-index="0"
      :value="overlay"
    >
      <v-row>
        <v-col class="pl-15">
          <OverlayVue
            :item="selectedItem"
          />
        </v-col>
        <v-col>
          <v-btn
            class="white--text"
            icon
            @click="overlay = false"
          >
            <v-icon>
              mdi-close
            </v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-overlay>
  </div>
</template>

<script>


export default {
    name: 'CVList',
    props: {
        listProfils: Array
    },
    components: {
    },
    data: function() {
        return {
          overlay: false,
          selectedItem: null,
          data: [

          ]
        }
    },
    methods: {


    }
}
</script>

<style>
.disable-events {
  pointer-events: none
}
</style>