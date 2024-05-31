'use strict'

let dayChart = null;
let daySensorType = '';

let sensorTypeGetDay = document.getElementById("sensor-type").value;

if(sensorTypeGetDay ==='temperature'){
    daySensorType = '온도';
}else if(sensorTypeGetDay === 'humidity'){
    daySensorType = '습도';
}else if(sensorTypeGetDay === 'co2'){
    daySensorType = 'CO2';
}

function drawDayChart(sequenceNumber) {
    var options = {
        series: [{
            name: '현재',
            type: 'column',
            data: []
        }, {
            name: '하루 전',
            type: 'line',
            data: []
        }],
        chart: {
            height: 350,
            width: '100%',
            type: 'line',
        },
        stroke: {
            width: [0, 4]
        },
        title: {
            text: '최근 24시간, 48시간 평균 ' + daySensorType + '(1시간 단위)',
            align: 'left',
            style: {
                fontSize: '20px'
            }
        },
        dataLabels: {
            enabled: true,
            enabledOnSeries: [1]
        },
        xaxis: {
            type: 'category',
            labels: {
                formatter: function (val) {
                    return val + '시';
                }
            }
        },
        yaxis: {
            title: {
                text: '',
            },
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
            position: 'top',
            horizontalAlign: 'right',
            floating: true,
            offsetY: -25,
            offsetX: -5
        }
    };

    dayChart = new ApexCharts(document.getElementById(sequenceNumber), options);
    dayChart.render();
}

function fetchDataAndUpdateChart(branchName, placeName, sensorName, sensorType, sequenceNumber) {
    const access_token = document.getElementById("access_token").value;

    const baseUrl = `https://databo3.live/api/sensor/${sensorType}/fields/${sensorType}_mean/branches/${branchName}/places/${placeName}/sensors/${sensorName}/day/mean`;
    // 현재
    let todayBegin = dayjs().utc().subtract(24, 'hour').format();
    let todayEnd = dayjs().utc().format();

    const todayUrl = `${baseUrl}?begin=${todayBegin}&end=${todayEnd}`;

    fetch(todayUrl,{
        headers:{
            Authorization: access_token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(todayData => {

            // 어제
            let yesterdayBegin = dayjs().utc().subtract(48, 'hour').format();
            let yesterdayEnd = dayjs().utc().subtract(24, 'hour').format();

            const yesterdayUrl = `${baseUrl}?begin=${yesterdayBegin}&end=${yesterdayEnd}`;

            fetch(yesterdayUrl, {
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
                .then(yesterdayData => {

                    // 데이터가 존재하는지 확인
                    if (todayData.data && Array.isArray(todayData.data) && yesterdayData.data && Array.isArray(yesterdayData.data)) {
                        // 오늘 데이터 처리
                        const todaySeriesData = todayData.data.map(item => {
                            const time = item.time.split('T')[1].split(':'); // 시간을 잘라내기
                            const formattedTime = `${time[0]}`; // HH:mm 형식으로 변환
                            const roundedValue = Math.round(item.value * 10) / 10;
                            return {
                                x: formattedTime,
                                y: roundedValue
                            };
                        });

                        // 어제 데이터 처리
                        const yesterdaySeriesData = yesterdayData.data.map(item => {
                            const time = item.time.split('T')[1].split(':'); // 시간을 잘라내기
                            const formattedTime = `${time[0]}`; // HH:mm 형식으로 변환
                            const roundedValue = Math.round(item.value * 10) / 10;
                            return {
                                x: formattedTime,
                                y: roundedValue
                            };
                        });

                        // 오늘과 어제 데이터를 동일한 x 값으로 조정
                        const alignedTodayData = [];
                        const alignedYesterdayData = [];

                        const todayTimes = todaySeriesData.map(item => item.x);

                        todayTimes.forEach(time => {
                            const todayItem = todaySeriesData.find(item => item.x === time) || { x: time, y: null };
                            const yesterdayItem = yesterdaySeriesData.find(item => item.x === time) || { x: time, y: null };

                            alignedTodayData.push(todayItem);
                            alignedYesterdayData.push(yesterdayItem);
                        });
                        drawDayChart(sequenceNumber);

                        dayChart.updateSeries([{
                            name: '현재',
                            type: 'column',
                            data: todaySeriesData
                        }, {
                            name: '하루 전',
                            type: 'line',
                            data: yesterdaySeriesData
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
        });
}