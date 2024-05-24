'use strict'

let gaugeChart = null;
let symbol = '';
let gaugeSensorType = '';

let sensorTypeLast = document.getElementById("sensor-type").value;

if(sensorTypeLast ==='temperature'){
    symbol = '°C';
    gaugeSensorType = '온도';
}else if(sensorTypeLast === 'humidity'){
    symbol = '%';
    gaugeSensorType = '습도';
}else if(sensorTypeLast === 'co2'){
    symbol = 'ppm';
    gaugeSensorType = 'CO2';
}

$(document).ready(function() {
    'use strict'

    var options = {
        series: [0],
        chart: {
            height: 315,
            type: 'radialBar',
            toolbar: {
                show: true
            }
        },
        title: {
            text: '현재 ' + gaugeSensorType,
            align: 'left',
            style: {
                fontSize: '25px'
            }
        },
        plotOptions: {
            radialBar: {
                startAngle: -135,
                endAngle: 225,
                hollow: {
                    margin: 0,
                    size: '70%',
                    background: '#fff',
                    image: undefined,
                    imageOffsetX: 0,
                    imageOffsetY: 0,
                    position: 'front',
                    dropShadow: {
                        enabled: true,
                        top: 3,
                        left: 0,
                        blur: 4,
                        opacity: 0.24
                    }
                },
                track: {
                    background: '#fff',
                    strokeWidth: '67%',
                    margin: 0, // margin is in pixels
                    dropShadow: {
                        enabled: true,
                        top: -3,
                        left: 0,
                        blur: 4,
                        opacity: 0.35
                    }
                },

                dataLabels: {
                    show: true,
                    name: {
                        offsetY: -10,
                        show: true,
                        color: '#888',
                        fontSize: '17px'
                    },
                    value: {
                        formatter: function(val) {
                            return Number.isInteger(val) ? parseInt(val) : val.toFixed(1);
                        },
                        color: '#111',
                        fontSize: '36px',
                        show: true,
                    }
                }
            }
        },
        fill: {
            type: 'gradient',
            gradient: {
                shade: 'dark',
                type: 'horizontal',
                shadeIntensity: 0.5,
                gradientToColors: ['#ABE5A1'],
                inverseColors: true,
                opacityFrom: 1,
                opacityTo: 1,
                stops: [0, 100]
            }
        },
        stroke: {
            lineCap: 'round'
        },
        labels: [symbol],
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
        }
    };

    gaugeChart = new ApexCharts(document.querySelector("#realTimeGauge"), options);
    // gaugeChart.render();

    // fetchDataOfRealTime();

    // setInterval(await fetchDataOfRealTime, 10000, branchName, placeName, sensorName, sensorType);
});

function fetchDataOfRealTime(branchName, placeName, sensorName, sensorType) {
    console.log(branchName, placeName, sensorName, sensorType);
    const access_token = document.getElementById("access_token").value;
    const url = `http://localhost:8888/api/sensor/${sensorType}/fields/value/branches/${branchName}/places/${placeName}/sensors/${sensorName}/last`;

    fetch(url, {
        headers:{
            Authorization:access_token
        }
    })
        .then(async response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(async data => {
            const value = data.data.value;
            let time = dayjs().format('HH:mm:ss A');
            console.log("가져온 값 : "+value);

            gaugeChart.updateSeries([value]);

            document.getElementById("realTime").innerText = `${time}`;
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}
