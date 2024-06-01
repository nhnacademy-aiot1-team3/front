'use strict'

let weekChart = null;
let weekSensorType = '';

let sensorTypeWeek = document.getElementById("sensor-type").value;

if(sensorTypeWeek ==='temperature'){
    weekSensorType = '온도';
}else if(sensorTypeWeek === 'humidity'){
    weekSensorType = '습도';
}else if(sensorTypeWeek === 'co2'){
    weekSensorType = 'CO2';
}

function drawWeekChart(sequenceNumber) {
    var options = {
        series: [
            {
                name: "최저 " + weekSensorType,
                data: []
            },
            {
                name: "최고 " + weekSensorType,
                data: []
            }
        ],
        chart: {
            height: 350,
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
        colors: ['#77B6EA', '#EA0505FF'],
        dataLabels: {
            enabled: true,
            formatter: function (val) {
                return val.toFixed(1); // 소수점 첫째 자리만 표시
            }
        },
        stroke: {
            curve: 'smooth'
        },
        title: {
            text: '최근 일주일 최고 최저 ' + weekSensorType,
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
            labels: {
                formatter: function (val) {
                    return val;
                }
            }
        },
        yaxis: {
            decimalsInFloat: 2,
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

    weekChart = new ApexCharts(document.getElementById(sequenceNumber), options);
    weekChart.render();
}

function fetchDataOfWeekChart(branchName, placeName, sensorName, sensorType, sequenceNumber) {
    const access_token = document.getElementById("access_token").value;

    const minBaseUrl = `https://www.databo3.live/api/sensor/${sensorType}/fields/${sensorType}_min/branches/${branchName}/places/${placeName}/sensors/${sensorName}/week/min`;
    const maxBaseUrl = `https://www.databo3.live/api/sensor/${sensorType}/fields/${sensorType}_max/branches/${branchName}/places/${placeName}/sensors/${sensorName}/week/max`;


    let begin = dayjs().utc().subtract(8, 'day').format();
    let end = dayjs.utc().subtract(1,'day').format();

    const minUrl = `${minBaseUrl}?begin=${begin}&end=${end}`;

    fetch(minUrl, {
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
        .then(minData => {
            console.log("최저 데이터 : ", minData);

            const maxUrl = `${maxBaseUrl}?begin=${begin}&end=${end}`;

            fetch(maxUrl, {
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
                .then(maxData => {
                    console.log("최고 데이터 : ", maxData);

                    // 데이터가 존재하는지 확인
                    if (minData.data && Array.isArray(minData.data) && maxData.data && Array.isArray(maxData.data)) {
                        // 최저 데이터 처리
                        const minSeriesData = minData.data.map(item => {

                            //요일
                            const makeDate = new Date(item.time);
                            const weekday = ['일', '월', '화', '수', '목', '금', '토'][makeDate.getDay()];

                            //월-일
                            const date = item.time.split('T')[0].split('-'); // 시간을 잘라내기
                            const formattedDate = `${date[1]}/${date[2]}`
                            const roundedValue = Math.round(item.value * 100) / 100;
                            return {
                                x: `${formattedDate}` + "(" + `${weekday}` + ")",
                                y: roundedValue
                            };
                        });
                        console.log("변환된 최저 데이터 : ", JSON.stringify(minSeriesData));

                        // 최고 데이터 처리
                        const maxSeriesData = maxData.data.map(item => {
                            //요일
                            const makeDate = new Date(item.time);
                            const weekday = ['일', '월', '화', '수', '목', '금', '토'][makeDate.getDay()];

                            //월-일
                            const date = item.time.split('T')[0].split('-'); // 시간을 잘라내기
                            const formattedDate = `${date[1]}:${date[2]}`
                            const roundedValue = Math.round(item.value * 100) / 100;
                            return {
                                x: `${formattedDate}` + "(" + `${weekday}` + ")",
                                y: roundedValue
                            };
                        });
                        drawWeekChart(sequenceNumber);

                        weekChart.updateSeries([{
                            name: '최저',
                            type: 'line',
                            data: minSeriesData
                        }, {
                            name: '최고',
                            type: 'line',
                            data: maxSeriesData
                        }]);
                    } else {
                        console.error("데이터가 올바른 형식이 아닙니다.");
                    }
                })
                .catch(error => {
                    console.error('Fetch error:', error);
                });
        })
        .catch(error => {
            console.error('Fetch error:', error);
        })
}
