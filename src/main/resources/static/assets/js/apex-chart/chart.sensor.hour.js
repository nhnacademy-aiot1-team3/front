'use strict'

let hourChart = null;
let hourSensorType = '';

let sensorTypeHour = document.getElementById("sensor-type").value;

if(sensorTypeHour ==='temperature'){
    hourSensorType = '온도';
}else if(sensorTypeHour === 'humidity'){
    hourSensorType = '습도';
}else if(sensorTypeHour === 'co2'){
    hourSensorType = 'CO2';
}
function drawHourChart(sequenceNumber) {
    var options = {

        series: [{
            name: hourSensorType,
            data: []
        }],
        chart: {
            height: 350,
            width: '100%',
            type: 'line',
            dropShadow: {
                enabled: true,
                color: '#000',
                top: 18,
                left: 7,
                blur: 10,
                opacity: 0.2
            },
            zoom: {
                enabled: false
            },
            toolbar: {
                show: false
            }
        },
        color: '#77B6EA',
        dataLabels: {
            enabled: true,
            formatter: function (val) {
                return val.toFixed(2); // 소수점 둘째 자리만 표시
            }
        },
        stroke: {
            curve: 'smooth'
        },
        title: {
            text: '최근 한시간 평균 ' + hourSensorType,
            align: 'left',
            style: {
                fontSize: '20px'
            }
        },
        grid: {
            borderColor: '#e7e7e7',
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        markers: {
            size: 1
        },
        xaxis: {
            type: 'category',
            title: {
                text: '(5분 단위)'
            }
        },
        yaxis: {
            decimalsInFloat: 1,
            title: {
                text: ''
            }
        },
        noData: {
            text: 'Loading...',
            align: 'center',
            verticalAlign: 'middle',
            offsetX: 0,
            offsetY: 0,
            style: {
                color: undefined,
                fontSize: '14px',
                fontFamily: undefined
            }
        },
        legend: {
            showForSingleSeries: true,
            position: 'top',
            horizontalAlign: 'right',
            floating: true,
            offsetY: -25,
            offsetX: -5
        }

    };

    hourChart = new ApexCharts(document.getElementById(sequenceNumber), options);
    hourChart.render();

    // 페이지 로딩 후 데이터 가져오기
    // fetchDataOfHourChart();

    // 1분마다 데이터 가져오기
    // setInterval(fetchDataOfHourChart, 60000); // 60000ms = 1분

}


function fetchDataOfHourChart(branchName, placeName, sensorName, sensorType, sequenceNumber) {
    const access_token = document.getElementById("access_token").value;

    const baseUrl = `http://databo3.live:8888/api/sensor/${sensorType}/fields/${sensorType}_mean/branches/${branchName}/places/${placeName}/sensors/${sensorName}/hour/mean`;

    let begin = dayjs().utc().subtract(1, 'hour').format();
    let end = dayjs().utc().format();


    const url = `${baseUrl}?begin=${begin}&end=${end}`;

    fetch(url, {
        headers:{
            Authorization:access_token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // 데이터가 존재하는지 확인
            if (data.data && Array.isArray(data.data)) {
                // 데이터 처리 및 그래프 업데이트
                const seriesData = data.data.map(item => {
                    const time = item.time.split('T')[1].split(':'); // 시간을 잘라내기
                    const formattedTime = `${time[0]}:${time[1]}`; // HH:mm 형식으로 변환
                    return {
                        x: formattedTime,
                        y: item.value
                    };
                });

                drawHourChart(sequenceNumber);

                hourChart.updateSeries([{
                    name: hourSensorType,
                    data: seriesData
                }]);

                console.log("변환된 데이터:", seriesData);
            } else {
                console.error("데이터가 올바른 형식이 아닙니다.");
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}
