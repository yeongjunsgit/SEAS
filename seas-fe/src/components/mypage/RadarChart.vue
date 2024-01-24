<script>
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from "chart.js";
import { Radar } from "vue-chartjs";
import * as chartConfig from "./chartConfig.js";

ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

export default {
  name: "RadarChart",
  components: {
    Radar,
  },
  data() {
    return chartConfig;
  },
};
</script>

<template>
  <Radar :data="data" :options="options" />
</template>
