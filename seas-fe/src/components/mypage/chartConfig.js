export const data = {
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
      backgroundColor: "rgba(0,0,255,0.2)",
      borderColor: "rgba(0,0,255,0.4)",
      pointBackgroundColor: "rgba(0,0,255,0.4)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(179,181,198,1)",
      data: [65, 59, 90, 81, 56, 55],
    },
  ],
};

export const options = {
  responsive: true,
  maintainAspectRatio: true,
  borderColor: "rgba(54, 162, 235, 1)",
  borderWidth: 2,
  scales: {
    r: {
      beginAtZero: true,
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
        // display: false,
        backdropColor: "rgba(81,60,58, 0)",
        color: "rgba(81,60,58, 1)",
        stepSize: 20,
        font: {
          size: 10,
        },
      },
    },
  },
  plugins: {
    legend: {
      display: true,
      onClick: false,
      labels: {
        boxHeight: 1,
        color: "rgba(81,60,58, 1)",
      },
    },
  },
};
