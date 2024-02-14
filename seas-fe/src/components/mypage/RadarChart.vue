<script setup>
import { Radar } from "vue-chartjs";
import { ref, onMounted } from "vue";
import { getRadarChart } from "@/api/mypage.js";
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

const chartData = ref({
  labels: [
    "데이터베이스",
    "네트워크",
    "자료구조",
    "알고리즘",
    "컴퓨터구조",
    "운영체제",
  ],
  datasets: [
    {
      label: "나의 CS 학습추이",
      backgroundColor: "rgba(0,0,255,0.2)",
      borderColor: "rgba(0,0,255,0.4)",
      pointBackgroundColor: "rgba(0,0,255,0.4)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(179,181,198,1)",
      data: [0, 0, 0, 0, 0, 0],
    },
  ],
});

const options = {
  responsive: true,
  maintainAspectRatio: true,
  borderColor: "rgba(54, 162, 235, 1)",
  borderWidth: 2,
  scales: {
    r: {
      beginAtZero: true,
      max: 100,
      backgroundColor: "rgba(81,60,58, 0.2)",
      grid: {
        color: "rgba(81,60,58, 0.5)",
      },
      pointLabels: {
        color: "rgba(81,60,58, 1)",
        font: {
          size: 12,
        },
      },
      ticks: {
        stepSize: 20,
        backdropColor: "rgba(81,60,58, 0)",
        color: "rgba(81,60,58, 1)",
        font: {
          size: 10,
        },
      },
    },
  },
  plugins: {
    legend: {
      display: false,
      onClick: false,
      labels: {
        boxHeight: 1,
        color: "rgba(81,60,58, 1)",
      },
    },
  },
};

const loaded = ref(false);

onMounted(async () => {
  loaded.value = false;

  try {
    getRadarChart(
      ({ data }) => {
        chartData.value.datasets[0].data = data.data.map((e) => e.rate);

        loaded.value = true;
      },
      (error) => {
        console.log(error);
      }
    );

    // const response = await fetch(
    //   "https://i10a609.p.ssafy.io/api/mypage/quiz-rate"
    // );
    // const userQuizRate = await response.json();
    // data.value.datasets[0].data = userQuizRate.data.map((e) => e.rate);

    // loaded.value = true;
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <Radar v-if="loaded" :data="chartData" :options="options" />
</template>
