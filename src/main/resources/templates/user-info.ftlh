<#import "blocks/template.ftlh" as t>
<@t.template userByPrincipal>
    <h4 style="text-align: center">Информация о пользователе</h4>
    <hr>
    <div style="text-align: center">
        <img src="/static/images/avatar.jpg"
             style="border:2px solid black; border-radius: 20px" height="120" alt="avatar">
        <br>
        <br>
        <h5><b>${user.name}</b></h5>
        <h5>${user.address}</h5>
        <h5>${user.email}</h5>

    </div>
    <hr>
    <br>
    <div id="graphic-metersconsume-container" style="width: auto; height: 400px;margin: auto"></div>
    <br>
    <div id="graphic-meters-container" style="width: auto; height: 400px;"></div>
    <br>
    <div id="graphic-electricity-container" style="width: auto; height: 400px;"></div>
    <br>
    <hr>
    <hr>
    <h4 style="text-align: center">История показаний счетчиков </h4>
    <br>

    <table id="dataTable" class="table table-hover table-dark"
           style="width:auto;word-break: break-all;table-layout: fixed; margin: auto; background-color: #03305c">
        <thead>
        <tr>
            <th scope="col" style="vertical-align: middle">Дата</th>
            <th scope="col" style="vertical-align: middle">Кухня(хол.)</th>
            <th scope="col" style="vertical-align: middle">Кухня(гор.)</th>
            <th scope="col" style="vertical-align: middle">Ванная(хол.)</th>
            <th scope="col" style="vertical-align: middle">Ванная(гор.)</th>
            <th scope="col" style="vertical-align: middle">Действие</th>
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
                <th style="vertical-align: middle"><#if userByPrincipal.isAdmin()>
                        <form action="/meterreading/delete/${meterReading.id?c}" method="post"
                              style="margin: 0 auto; text-align: center">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit" style="text-align: center" class="btn btn-danger mb-2">
                            Удалить запись
                        </button>
                        </form><#else>--</#if></th>
            </tr>
        </#list>
        </tbody>
    </table>

    <br>
    <br>
    <hr>
    <h4 style="text-align: center">История показаний счетчиков электричества </h4>

    <table id="electricityTable" class="table table-hover table-dark"
           style="width:auto;word-break: break-all;table-layout: fixed; margin: auto; background-color: #03305c">
        <thead>
        <tr>
            <th scope="col" style="vertical-align: middle">Дата</th>
            <th scope="col" style="vertical-align: middle">Электричество</th>
            <th scope="col" style="vertical-align: middle">Данные внес</th>
            <th scope="col" style="vertical-align: middle">Действие</th>
        </tr>
        </thead>
        <tbody>
        <#list electricityMeterReadings as electricityMeterReading>
            <tr>
                <th style="vertical-align: middle">${electricityMeterReading.getDateOfCreatedString()}</th>
                <th style="vertical-align: middle"><#if electricityMeterReading.electricity != 0>${electricityMeterReading.electricity?string("0.00")}<#else>--</#if></th>
                <th style="vertical-align: middle">${electricityMeterReading.dataProviderName}</th>
                <th style="vertical-align: middle"><#if userByPrincipal.isAdmin()>
                    <form action="/electricitymeterreading/delete/${electricityMeterReading.id?c}" method="post"
                          style="margin: 0 auto; text-align: center">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit" style="text-align: center" class="btn btn-danger mb-2">
                            Удалить запись
                        </button>
                        </form><#else>--</#if></th>
            </tr>
        </#list>
        </tbody>
    </table>

    <h4 style="text-align: center">История заявок </h4>
    <br>
    <div class="card-columns">
        <#list requests as request>
            <div class="card text-white bg-dark my-3 request">

                <div class="card-body">
                    <p class="card-title">Дата создания: <br>${request.dateOfCreatedString}</p>
                    <p class="card-text"> Адрес: <br>${user.address}</p>
                    <p class="card-text"> Описание: <br>${request.description}</p>
                    <p class="card-text"> Фото: <br><#list request.getImages() as image>
                        <a href="/images/${image.id}">Ссылка</a></#list></p>
                    <p class="card-text"> Статус: <br>${request.status}</p>
                    <p class="card-text"> Исполнитель: <br>${request.executor}</p>
                </div>
                <div class="card-footer">
                    ${request.user.name}
                </div>
                <div>
                    <#if userByPrincipal.isAdmin()>
                        <form action="/request/delete/${request.id?c}" method="post"
                              style="margin: 0 auto; text-align: center">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit" style="text-align: center" class="btn btn-danger mb-2">
                                Удалить запись
                            </button>
                        </form>
                    </#if>
                </div>
            </div>
        <#else>
            <h4 style="text-align: center">Не найдено</h4>
        </#list>
    </div>

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
            var seriesData_1 = dataSet.mapAs({x: 0, value: 1, fill: 4, stroke: 5, label: 6});
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
                    switch (j) {
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
            var seriesData_1 = dataSet.mapAs({x: 0, value: 1, fill: 6, stroke: 10, label: 11});
            var seriesData_2 = dataSet.mapAs({x: 0, value: 2, fill: 7, stroke: 10, label: 11});
            var seriesData_3 = dataSet.mapAs({x: 0, value: 3, fill: 8, stroke: 10, label: 11});
            var seriesData_4 = dataSet.mapAs({x: 0, value: 4, fill: 9, stroke: 10, label: 11});

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
            var seriesData_1 = dataSet.mapAs({x: 0, value: 1, fill: 6, stroke: 10, label: 11});
            var seriesData_2 = dataSet.mapAs({x: 0, value: 2, fill: 7, stroke: 10, label: 11});
            var seriesData_3 = dataSet.mapAs({x: 0, value: 3, fill: 8, stroke: 10, label: 11});
            var seriesData_4 = dataSet.mapAs({x: 0, value: 4, fill: 9, stroke: 10, label: 11});

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