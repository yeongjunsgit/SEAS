export const data = {
  labels: ["Jan 01", "Jan 02", "Jan 03", "Jan 04", "Jan 05"],
  datasets: [
    {
      label: "카테고리",
      backgroundColor: "rgba(81,60,58, 1)",
      data: [10, 40, 39, 80, 40],
    },
  ],
};

export const options = {
  responsive: true,
  maintainAspectRatio: false,
  borderColor: "rgba(81,60,58, 1)",
  borderWidth: 2,
  scale: {
    beginAtZero: true,
    ticks: {
      // display: false,
      backdropColor: "rgba(81,60,58, 0)",
      color: "rgba(81,60,58, 1)",
      stepSize: 20,
      font: {
        size: 10,
      },
    },
  },
  plugins: {
    legend: {
      display: true,
      onClick: false,
      labels: {
        boxHeight: 0.1,
        color: "rgba(81,60,58, 1)",
      },
    },
  },
};
