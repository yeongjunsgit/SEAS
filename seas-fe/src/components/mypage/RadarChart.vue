<script>
import { Radar } from "vue-chartjs";
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from "chart.js";
// import * as chartConfig from "@/chartConfig.js";

ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

export default {
  name: 'RadarChart',
  components: { Radar },
  data() {
    return {
      chartData: {
        labels: [
          "자료구조",
          "알고리즘",
          "운영체제",
          "데이터베이스",
          "네트워크",
          "컴퓨터 구조",
        ],
        datasets: [
          {
            label: "나의 CS 학습추이",
            backgroundColor: "rgba(179,181,198,0.2)",
            borderColor: "#000",
            pointBackgroundColor: "#000",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(179,181,198,1)",
            data: [65, 59, 90, 81, 56, 55],
          }
        ]
      },
      chartOptions: {
        responsive: true,
        scales: {
            ticks: {
                stepSize: 10,
                min: 0,
                max: 100,
            },
        },
      }
    },
  }
};
</script>

<template>
  <canvas id="mychart"></canvas>
</template>
