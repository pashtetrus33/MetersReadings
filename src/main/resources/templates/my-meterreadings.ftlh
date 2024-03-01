<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h4 style="text-align: center">История показаний</h4>
    <hr>

    <div id="graphic-metersconsume-container" style="width: auto; height: 400px;margin: auto"></div>
    <br>
    <div id="graphic-meters-container" style="width: auto; height: 400px;"></div>
    <br>
    <div id="graphic-electricity-container" style="width: auto; height: 400px;"></div>
    <br>
    <hr>


    <table id="dataTable" class="table table-hover table-dark"
           style="width:auto;word-break: break-all;table-layout: fixed; margin: auto; background-color: #03305c">
        <thead>
        <tr>
            <th scope="col" style="vertical-align: middle">Дата</th>
            <th scope="col" style="vertical-align: middle">Кухня(хол.)</th>
            <th scope="col" style="vertical-align: middle">Кухня(гор.)</th>
            <th scope="col" style="vertical-align: middle">Ванная(хол.)</th>
            <th scope="col" style="vertical-align: middle">Ванная(гор.)</th>
        </tr>
        </thead>
        <tbody>
        <#list meterReadings as meterReading>
            <tr>
                <th style="vertical-align: middle">${meterReading.dateOfCreatedString}</th>
                <th style="vertical-align: middle"><#if meterReading.kitchenCold != 0>${meterReading.kitchenCold?string("0.00")}<#else>--</#if></th>
                <th style="vertical-align: middle"><#if meterReading.kitchenHot != 0>${meterReading.kitchenHot?string("0.00")}<#else>--</#if></th>
                <th style="vertical-align: middle"><#if meterReading.toiletCold != 0>${meterReading.toiletCold?string("0.00")}<#else>--</#if></th>
                <th style="vertical-align: middle"><#if meterReading.toiletHot != 0>${meterReading.toiletHot?string("0.00")}<#else>--</#if></th>
            </tr>
        </#list>
        </tbody>
    </table>

    <br>

    <table id="electricityTable" class="table table-hover table-dark"
           style="width:auto;word-break: break-all;table-layout: fixed; margin: auto; background-color: #03305c">
        <thead>
        <tr>
            <th scope="col" style="vertical-align: middle">Дата</th>
            <th scope="col" style="vertical-align: middle">Электричество</th>
            <th scope="col" style="vertical-align: middle">Данные внес</th>
        </tr>
        </thead>
        <tbody>
        <#list electricityMeterReadings as electricityMeterReading>
            <tr>
                <th style="vertical-align: middle">${electricityMeterReading.getDateOfCreatedString()}</th>
                <th style="vertical-align: middle"><#if electricityMeterReading.electricity != 0>${electricityMeterReading.electricity?string("0.00")}<#else>--</#if></th>
                <th style="vertical-align: middle">${electricityMeterReading.dataProviderName}</th>
            </tr>
        </#list>
        </tbody>
    </table>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
           href="/profile" role="button">
            Назад
        </a>
    </div>

    <script>
        anychart.onDocumentReady(function () {
            var table = document.getElementById('electricityTable');
            //создаем массив для хранения данных
            var data = [];
            var previous = table.rows[1].cells[1].innerText.replace(",", ".");

            for (var i = 0; i < table.rows.length; i++) {
                var rowData = [];

                for (var j = 0; j < table.rows[i].cells.length; j++) {
                    let temp = table.rows[i].cells[j].innerText.replace(",", ".")
                    switch (j) {
                        case 0:
                            temp = table.rows[i].cells[j].innerText.slice(0, 10);
                            break;
                        case 1:
                            temp = table.rows[i].cells[j].innerText.replace(",", ".") - previous.replace(",", ".");
                            break;
                    }
                    rowData.push(temp);

                }
                previous = table.rows[i].cells[1].innerText.replace(",", ".");
                rowData.push("#5c7fd6");
                rowData.push(null);
                rowData.push({enabled: true});
                data.push(rowData);
            }

            var dataSet = anychart.data.set(data);

            // map the data
            var seriesData_1 = dataSet.mapAs({x: 0, value: 1, fill: 3, stroke: 4, label: 5});
            chart = anychart.column3d();
            // configure the z-angle of the chart
            chart.zAngle(20);
            var series1 = chart.column(seriesData_1);
            series1.name("Расход электричества")

            var title = chart.title();
            title.enabled(true);
            title.text('Расход электричества');

            // set the titles of the axes
            var xAxis = chart.xAxis();
            xAxis.title("Дата ввода показаний");

            var yAxis = chart.yAxis();
            yAxis.title("Объем, квт");

            // set the container id
            chart.container('graphic-electricity-container');

            // initiate drawing the chart
            chart.draw();
        });
    </script>

    <script>
        anychart.onDocumentReady(function () {
            var table = document.getElementById('dataTable');
            //создаем массив для хранения данных
            var data = [];

            for (var i = 0; i < table.rows.length; i++) {
                var rowData = [];

                for (var j = 0; j < table.rows[i].cells.length; j++) {
                    let temp = table.rows[i].cells[j].innerText.replace(",", ".");
                    switch (j){
                        case 0:
                            temp = table.rows[i].cells[j].innerText.slice(0, 10);
                            break;
                    }
                    rowData.push(temp);
                }
                rowData.push("#0151fd");
                rowData.push("#fd011e");
                rowData.push("#2301fd");
                rowData.push("#be01fd");
                rowData.push(null);
                rowData.push({enabled: true});
                data.push(rowData);
            }
            //создать набор данных
            var dataSet = anychart.data.set(data);

            // map the data
            var seriesData_1 = dataSet.mapAs({x: 0, value: 1, fill: 5, stroke: 9, label: 10});
            var seriesData_2 = dataSet.mapAs({x: 0, value: 2, fill: 6, stroke: 9, label: 10});
            var seriesData_3 = dataSet.mapAs({x: 0, value: 3, fill: 7, stroke: 9, label: 10});
            var seriesData_4 = dataSet.mapAs({x: 0, value: 4, fill: 8, stroke: 9, label: 10});

            chart = anychart.column3d();

            var series1 = chart.column(seriesData_1);
            series1.name("Кухня (хол.вода)");

            var series2 = chart.column(seriesData_2);
            series2.name("Кухня (гор.вода)");

            var series3 = chart.column(seriesData_3);
            series3.name("Ванная (хол.вода)");

            var series4 = chart.column(seriesData_4);
            series4.name("Ванная (гор.вода)");


            var title = chart.title();
            title.enabled(true);
            title.text('Показания счетчиков воды');

            // set the padding between columns
            chart.barsPadding(1);

            // set the padding between column groups
            chart.barGroupsPadding(3);

            // set the titles of the axes
            var xAxis = chart.xAxis();
            xAxis.title("Дата ввода показаний");

            var yAxis = chart.yAxis();
            yAxis.title("Объем, m3");
            chart.container('graphic-meters-container');
            chart.draw();
        });
    </script>

    <script>
        anychart.onDocumentReady(function () {
            var table = document.getElementById('dataTable');
            //создаем массив для хранения данных
            var data = [];
            var previous1 = table.rows[1].cells[1].innerText.replace(",", ".");
            var previous2 = table.rows[1].cells[2].innerText.replace(",", ".");
            var previous3 = table.rows[1].cells[3].innerText.replace(",", ".");
            var previous4 = table.rows[1].cells[4].innerText.replace(",", ".");

            for (var i = 0; i < table.rows.length; i++) {
                var rowData = [];

                for (var j = 0; j < table.rows[i].cells.length; j++) {
                    let temp = table.rows[i].cells[j].innerText.replace(",", ".")
                    temp = table.rows[i].cells[j].innerText.replace(",", ".")
                    switch (j) {
                        case 0:
                            temp = table.rows[i].cells[j].innerText.slice(0, 10);
                            break;
                        case 1:
                            temp = table.rows[i].cells[j].innerText.replace(",", ".") - previous1;
                            break;
                        case 2:
                            temp = table.rows[i].cells[j].innerText.replace(",", ".") - previous2;
                            break;
                        case 3:
                            temp = table.rows[i].cells[j].innerText.replace(",", ".") - previous3;
                            break;
                        case 4:
                            temp = table.rows[i].cells[j].innerText.replace(",", ".") - previous4;
                            break;
                    }
                    rowData.push(temp);
                }
                previous1 = table.rows[i].cells[1].innerText.replace(",", ".");
                previous2 = table.rows[i].cells[2].innerText.replace(",", ".");
                previous3 = table.rows[i].cells[3].innerText.replace(",", ".");
                previous4 = table.rows[i].cells[4].innerText.replace(",", ".");
                rowData.push("#0151fd");
                rowData.push("#fd011e");
                rowData.push("#2301fd");
                rowData.push("#be01fd");
                rowData.push(null);
                rowData.push({enabled: true});
                data.push(rowData);
            }
            //создать набор данных
            var dataSet = anychart.data.set(data);

            // map the data
            var seriesData_1 = dataSet.mapAs({x: 0, value: 1, fill: 5, stroke: 9, label: 10});
            var seriesData_2 = dataSet.mapAs({x: 0, value: 2, fill: 6, stroke: 9, label: 10});
            var seriesData_3 = dataSet.mapAs({x: 0, value: 3, fill: 7, stroke: 9, label: 10});
            var seriesData_4 = dataSet.mapAs({x: 0, value: 4, fill: 8, stroke: 9, label: 10});

            chart = anychart.column3d();

            var series1 = chart.column(seriesData_1);
            series1.name("Кухня (хол.вода)");

            var series2 = chart.column(seriesData_2);
            series2.name("Кухня (гор.вода)");

            var series3 = chart.column(seriesData_3);
            series3.name("Ванная (хол.вода)");

            var series4 = chart.column(seriesData_4);
            series4.name("Ванная (гор.вода)");


            var title = chart.title();
            title.enabled(true);
            title.text('Расход воды по счетчикам');

            // set the padding between columns
            chart.barsPadding(1);

            // set the padding between column groups
            chart.barGroupsPadding(3);

            // set the titles of the axes
            var xAxis = chart.xAxis();
            xAxis.title("Дата ввода показаний");

            var yAxis = chart.yAxis();
            yAxis.title("Объем, m3");
            chart.container('graphic-metersconsume-container');
            chart.draw();
        });
    </script>
</@t.template>