'use strict'

let weekChartCo2 = null;

$(document).ready(function () {
   'use strict'

    var options = {
        series: [{
            name: '2주 전 데이터',
            data: []
        }, {
            name: '1주 전 데이터',
            data: []
        }],
        chart: {
            height: 350,
            type: 'area'
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'smooth'
        },
        title: {
            text: '최근 24시간, 48시간 평균 Co2(1시간 단위)',
            align: 'left',
            style: {
                fontSize: '25px'
            }
        },
        xaxis: {
            type: 'category',
            labels: {
                formatter: function (val) {
                    return val;
                }
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
        }

    };

    weekChartCo2 = new ApexCharts(document.querySelector(".weekCo2Chart"), options);
    weekChartCo2.render();

    // fetchDataOfWeekCo2Chart();

    // setInterval(fetchDataOfWeekCo2Chart, 86400000);

});


function fetchDataOfWeekCo2Chart(branchName, placeName, sensorName, sensorType) {
    const access_token = document.getElementById("access_token").value;

    let twoWeekBegin = dayjs().utc().subtract(2, 'week').format();
    let twoWeekEnd = dayjs().utc().subtract(1, 'week').format();

    let oneWeekBegin = dayjs().utc().subtract(1, 'week').format();
    let oneWeekEnd = dayjs().utc().format();

    const baseUrl = `http://localhost:8888/api/sensor/co2/fields/co2_mean/branches/${branchName}/places/${placeName}/sensors/${sensorName}/week/mean`;

    const twoWeekUrl = `${baseUrl}?begin=${twoWeekBegin}&end=${twoWeekEnd}`;
    const oneWeekUrl = `${baseUrl}?begin=${oneWeekBegin}&end=${oneWeekEnd}`;

    fetch(twoWeekUrl, {
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
        .then(twoWeekData => {
            console.log("2주 전 데이터 : ", twoWeekData);

            fetch(oneWeekUrl, {
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
                .then(oneWeekData => {
                    console.log("1주 전 데이터 : ", oneWeekData);

                    if (oneWeekData.data && Array.isArray(oneWeekData.data) && twoWeekData.data && Array.isArray(twoWeekData.data)) {

                        const twoWeekSeriesData = twoWeekData.data.map(item => {

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
                        console.log("변환된 2주 전 데이터 : ", JSON.stringify(twoWeekSeriesData));

                        const oneWeekSeriesData = oneWeekData.data.map(item => {
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
                        console.log("변환된 1주 전 데이터 : ", oneWeekSeriesData);

                        weekChartCo2.updateSeries([{
                            name: '2주 전 데이터',
                            type: 'line',
                            data: twoWeekSeriesData
                        }, {
                            name: '1주 전 데이터',
                            type: 'line',
                            data: oneWeekSeriesData
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