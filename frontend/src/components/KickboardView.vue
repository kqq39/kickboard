<template>

<v-data-table
    :headers="headers"
    :items="kickboardView"
    :items-per-page="5"
    class="elevation-1"
  ></v-data-table>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'KickboardView',
    props: {
      value: Object,
      editMode: Boolean,
      isNew: Boolean
    },
    data: () => ({
        headers: [
            { text: "ticketId", value: "ticketId" },
            { text: "ticketStatus", value: "ticketStatus" },
            { text: "ticketType", value: "ticketType" },
            { text: "buyerPhoneNum", value: "buyerPhoneNum" },
            { text: "paymentId", value: "paymentId" },
            { text: "paymentStatus", value: "paymentStatus" },
            { text: "kickId", value: "kickId" },
            { text: "kickStatus", value: "kickStatus" },
            { text: "usingTime", value: "usingTime" },
        ],
        kickboardView : [],
    }),
    async created() {
      var temp = await axios.get(axios.backend + '/kickboardviews')

      this.kickboardView = temp.data._embedded.kickboardviews;

    },
    methods: {
    }
  }
</script>

