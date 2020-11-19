<script>
  //Importing Line class from the vue-chartjs wrapper
  import { Line } from 'vue-chartjs'

  //Exporting this so it can be used in other components
  export default {
    extends: Line,
    data () {
      return {
        datacollection: {
          //Data to be represented on x-axis
          labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
          datasets: [
            {
              label: 'Data One', // 그래프가 어떤 내용을 뜻하는지
              backgroundColor: '#f87979', // 그래프 안의 배경색
              pointBackgroundColor: 'white', // 각 점의 색
              borderWidth: 1,
              pointBorderColor: '#249EBF',
              //Data to be represented on y-axis
              data: [50, 50, 30, 50, 90, 50, 70, 40, 50, 70, 90, 100]
            }
          ]
        },
        //Chart.js options that controls the appearance of the chart
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true // 차트 y축 0부터 시작
              },
              gridLines: {
                display: true // 수치마다 가로선 유무
              }
            }],
            xAxes: [ {
              gridLines: {
                display: false // 수치마다 세로선 유무
              }
            }]
          },
          legend: {
            display: false
          },
          responsive: true, // 그래프넓이
          maintainAspectRatio: false,
        }
      }
    },
    mounted () {
      //renderChart function renders the chart with the datacollection and options object.
      this.renderChart(this.datacollection, this.options) // datacollection 과 options 객체를 파라미터로 보내서 차트를 render 하는 renderChart()함수를 부릅니다
    }
  }
</script>