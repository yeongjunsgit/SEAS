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
      backgroundColor: "rgba(179,181,198,0.2)",
      borderColor: "rgba(179,181,198,1)",
      pointBackgroundColor: "rgba(179,181,198,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(179,181,198,1)",
      data: [65, 59, 90, 81, 56, 55],
    },
  ],
};

export const options = {
  responsive: true,
  maintainAspectRatio: false,
};
