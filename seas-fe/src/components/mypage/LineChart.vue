<script setup>
import { Line } from "vue-chartjs";
import { ref, onMounted } from "vue";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import * as d3 from "d3";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

const data = ref({
  labels: ["Jan 01", "Jan 02", "Jan 03", "Jan 04", "Jan 05"],
  datasets: [
    {
      label: "카테고리",
      backgroundColor: "rgba(81,60,58, 1)",
      data: [10, 40, 39, 80, 40],
    },
  ],
});

const options = {
  responsive: true,
  maintainAspectRatio: false,
  borderColor: "rgba(81,60,58, 1)",
  borderWidth: 2,
  scales: {
    x: {
      ticks: {
        font: {
          size: 11,
          family: "Ahnjunggeun",
          weight: "bold",
        },
        color: "black",
      },
      grid: {
        color: "rgba(0,0,0, 0.3)",
      },
    },
    y: {
      beginAtZero: true,
      max: 100,
      padding: 50,
      ticks: {
        stepSize: 20,
        font: {
          size: 14,
          family: "Ahnjunggeun",
          weight: "bold",
        },
        color: "black",
      },
      grid: {
        color: "rgba(0,0,0, 0.3)",
      },
    },
  },
  plugins: {
    legend: {
      display: true,
      onClick: false,
      labels: {
        font: {
          family: "Ahnjunggeun",
          weight: "bold",
          size: 17,
        },
        boxHeight: 0.1,
        color: "rgba(81,60,58, 1)",
      },
    },
  },
};

const props = defineProps(["category"]);

const loaded = ref(false);
const formatDate = d3.timeFormat("%x");
onMounted(async () => {
  loaded.value = false;
  try {
    data.value.datasets[0].label = await props.category.categoryName;
    const graphValues = await props.category.history.map((e) => e.averageScore);
    const graphDates = await props.category.history.map(function (e) {
      var studiedDate = new Date(e.date);
      var shortDate = formatDate(studiedDate);
      return shortDate;
    });
    data.value.datasets[0].data = graphValues;
    data.value.labels = graphDates;
    loaded.value = true;
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <Line
    v-if="loaded"
    :data="data"
    :options="options"
    width="500"
    height="200"
  />
</template>
